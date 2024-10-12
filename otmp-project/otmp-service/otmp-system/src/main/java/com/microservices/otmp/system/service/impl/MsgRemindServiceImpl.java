package com.microservices.otmp.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.system.common.KafkaFactory;
import com.microservices.otmp.system.common.KafkaGroup;
import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.domain.entity.MsgRemindDO;
import com.microservices.otmp.system.manager.IMsgRemindManager;
import com.microservices.otmp.system.service.IMsgRemindService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.microservices.otmp.common.constant.RedisConstants.REDIS_EXPIRE_TIME_ONE_WEEK;


/**
 * 消息通知记录Service业务层处理
 *
 * @author lovefamily
 * @date 2022-10-26
 */
@Service
public class MsgRemindServiceImpl implements IMsgRemindService {
    private static final Logger log = LoggerFactory.getLogger(MsgRemindServiceImpl.class);

    private static final String KAFKA_LISTENER_ID = "saveMsg";

    @Autowired
    private RedisUtils redisUtils;

    @Value("${spring.profiles.active}")
    private String environment;

    @Autowired
    private KafkaSend kafkaSend;

    @Autowired
    private IMsgRemindManager msgRemindManager;

    @Value("${kafka.topic.msg-remind}")
    private String msgRemindTopic;

    /**
     * 查询消息通知记录
     *
     * @param id 消息通知记录主键
     * @return 消息通知记录DTO
     */
    @Override
    public MsgRemindDTO selectMsgRemindById(Long id) {
        MsgRemindDO msgRemindDO = msgRemindManager.selectMsgRemindById(id);
        MsgRemindDTO msgRemindDTO = new MsgRemindDTO();
        BeanUtils.copyProperties(msgRemindDO, msgRemindDTO);
        return msgRemindDTO;
    }

    /**
     * 查询消息通知记录列表
     *
     * @param msgRemind 消息通知记录
     * @return 消息通知记录DTO
     */
    @Override
    public List<MsgRemindDTO> selectMsgRemindList(MsgRemindDTO msgRemind) {
        CommonUtils.stringIsEmpty(msgRemind.getItCodes(), "itcode is empty!");
        List<MsgRemindDO> msgRemindDOS =
                msgRemindManager.selectMsgRemindList(msgRemind);
        List<MsgRemindDTO> msgRemindList = new ArrayList<>(msgRemindDOS.size());

        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(msgRemindDOS, msgRemindList, MsgRemindDTO.class);
        return msgRemindList;
    }

    /**
     * 新增消息通知记录
     *
     * @param msgRemind 消息通知记录
     */
    @Override
    public void insertMsgRemind(MsgRemindDTO msgRemind) {

        validateMsgUuid(msgRemind);

        CommonUtils.stringIsEmpty(msgRemind.getModule(), DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.MODULE_IS_EMPTY));
        CommonUtils.stringIsEmpty(msgRemind.getItCodes(), DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ITCODES_IS_EMPTY));
        CommonUtils.stringIsEmpty(msgRemind.getMsgInfo(), DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.MSGINFO_IS_EMPTY));
        CommonUtils.stringIsEmpty(msgRemind.getMsgTopic(), DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.MSGTOPIC_IS_EMPTY));
        CommonUtils.stringIsEmpty(msgRemind.getMsgType(), DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.MSGTYPE_IS_EMPTY));


        msgRemind.setCreateTime(DateUtils.getNowDate());
        msgRemind.setCreateBy(msgRemind.getModule());
        msgRemind.setUpdateTime(DateUtils.getNowDate());
        msgRemind.setUpdateBy(msgRemind.getModule());
        sendkafka(msgRemind);
    }


    private void sendkafka(MsgRemindDTO msgRemindDTO) {
        String bizSdmsPoolDtoStr = JSON.toJSONString(msgRemindDTO);
        log.info("msg remind 发送参数：{}", bizSdmsPoolDtoStr);
        kafkaSend.asynSend(msgRemindTopic, String.valueOf(msgRemindDTO.getItCodes()), bizSdmsPoolDtoStr, KafkaFactory.MSG_KAFKA_PRODUCER_FACTORY, new KafkaCallback(msgRemindTopic), false);
    }


//    @KafkaListener(containerFactory = KafkaFactory.MSG_KAFKA_FACTORY, topics = {"${kafka.topic.msg-remind}"}, groupId = KafkaGroup.OTMP_SVC, id = KAFKA_LISTENER_ID)
    public void listen(ConsumerRecord<?, ?> consumerRecord) {
        String value = (String) consumerRecord.value();
        log.info("msg remind kafka接收参数：{}", value);
        List<MsgRemindDO> msgRemindDOS = JSON.parseObject(getObjects(value), new TypeReference<List<MsgRemindDO>>() {
        });
        for (MsgRemindDO msgRemindDO : msgRemindDOS) {

            if (StringUtils.isEmpty(msgRemindDO.getModule()) ||
                    StringUtils.isEmpty(msgRemindDO.getMsgTopic()) ||
                    StringUtils.isEmpty(msgRemindDO.getMsgInfo()) ||
                    StringUtils.isEmpty(msgRemindDO.getSysCatalog()) ||
                    StringUtils.isEmpty(msgRemindDO.getItCodes())) {
                log.error("msgRemindDO some info is empty:{}", JSON.toJSON(msgRemindDO));
                continue;
            }

            if (!CommonUtils.isNullObject(msgRemindDO.getIsUpdate()) && msgRemindDO.getIsUpdate() == 1) {
                msgRemindManager.updateMsgRemind(msgRemindDO);
            } else {
                msgRemindManager.insertMsgRemind(msgRemindDO);
            }

        }
    }

    /**
     * 修改消息通知记录
     *
     * @param msgRemindDTO 消息通知记录
     * @return 结果
     */
    @Override
    public int updateMsgRemind(MsgRemindDTO msgRemindDTO) {
        if (StringUtils.isEmpty(msgRemindDTO.getItCodes())) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ITCODES_IS_EMPTY), DefaultErrorMessage.ITCODES_IS_EMPTY.intValue());
        }
        if (msgRemindDTO.getItCodes().contains(",")) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ITCODES_IS_NOT_ONE), DefaultErrorMessage.ITCODES_IS_NOT_ONE.intValue());
        }
        if (null == msgRemindDTO.getId()) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ID_IS_EMPTY), DefaultErrorMessage.ID_IS_EMPTY.intValue());
        }
        throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.HAD_READ_FLAG_ERROR), DefaultErrorMessage.HAD_READ_FLAG_ERROR.intValue());

    }


    private void validateMsgUuid(MsgRemindDTO msgRemind) {
        CommonUtils.stringIsEmpty(msgRemind.getMsgUuid(), "msg UUID is empty");

        String redisKey = RedisConstants.generateRedisKey(RedisConstants.MSG_UUID_PREFIX, String.valueOf(Constants.SEPARATE_COLON),
                String.valueOf(msgRemind.getMsgUuid()));
        boolean res = redisUtils.setNx(redisKey, JSON.toJSONString(msgRemind), REDIS_EXPIRE_TIME_ONE_WEEK);
        if (!res) {
            throw new OtmpException("repeat msg! the msg exist!");
        }
    }

    /**
     * 批量删除消息通知记录
     *
     * @param ids 需要删除的消息通知记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgRemindByIds(Long[] ids) {
        return msgRemindManager.deleteMsgRemindByIds(ids);
    }

    /**
     * 删除消息通知记录信息
     *
     * @param id 消息通知记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgRemindById(Long id) {
        return msgRemindManager.deleteMsgRemindById(id);
    }

    @Override
    public int batchUpdateMsgRemind(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ID_IS_EMPTY), DefaultErrorMessage.ID_IS_EMPTY.intValue());
        }
        msgRemindManager.batchUpdateMsgRemindRead(ids, 1);
        return ids.size();
    }

    public static String getObjects(String value) {
        if (value.startsWith("{")) {
            value = "[" + value + "]";
        }
        return value;
    }
}
