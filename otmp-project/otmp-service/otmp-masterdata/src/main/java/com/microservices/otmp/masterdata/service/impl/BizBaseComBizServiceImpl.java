package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseComBiz;
import com.microservices.otmp.masterdata.mapper.BizBaseComBizMapper;
import com.microservices.otmp.masterdata.service.IBizBaseComBizService;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteDictService;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseComBiz;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteDictService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * baseComBizService业务层处理
 *
 * @author sdms
 * @date 2022-01-17
 */
@Service
public class BizBaseComBizServiceImpl implements IBizBaseComBizService {
    @Autowired
    private BizBaseComBizMapper bizBaseComBizMapper;
    @Autowired
    private RemoteDictService remoteDictService;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";
    public void setSingleToRedis(BizBaseComBiz bizBaseComBiz, String tableName, String key) {
        //BW BU      biz_base_bw_bu
        //BPC BU   biz_base_bpc_bu
        //biz_base_customer_group
        bizBaseComBiz.setBizTable(tableName);
        List<String> bwBuList = this.selectBizBaseComBizList(bizBaseComBiz).stream().map(BizBaseComBiz::getBizCode).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX + key, bwBuList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询baseComBiz
     *
     * @param id baseComBizID
     * @return baseComBiz
     */
    @Override
    public BizBaseComBiz selectBizBaseComBizById(Integer id) {
        return bizBaseComBizMapper.selectBizBaseComBizById(id);
    }

    /**
     * 查询baseComBiz列表
     *
     * @param bizBaseComBiz baseComBiz
     * @return baseComBiz
     */
    @Override
    public List<BizBaseComBiz> selectBizBaseComBizList(BizBaseComBiz bizBaseComBiz) {


        List<BizBaseComBiz> bizs = bizBaseComBizMapper.selectBizBaseDataList(bizBaseComBiz);
        List<SysDictData> dictSelect = remoteDictService.getDictSelect("com_biz_table");
        Map<String, String> labelMap = dictSelect.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel, (e1, e2) -> e1));
        bizs.stream().forEach(biz->
            biz.setBizTable(labelMap.get(biz.getBizTable()))
        );
        return bizs;
    }

    @Override
    public List<BizBaseComBiz> getComBiz() {
        return bizBaseComBizMapper.getComBiz();
    }

    /**
     * 新增baseComBiz
     *
     * @param bizBaseComBiz baseComBiz
     * @return 结果
     */
    @Override
    public int insertBizBaseComBiz(BizBaseComBiz bizBaseComBiz) {
        bizBaseComBiz.setCreateTime(DateUtils.getNowDate());
        CommonUtils.isNull(bizBaseComBiz.getBizTable(), "Business Type is empty");
        int i=bizBaseComBizMapper.insertBizBaseData(bizBaseComBiz.getBizTable(),bizBaseComBiz);

        setDataToRedis(bizBaseComBiz.getBizTable());
        return i;
    }

    /**
     * 修改baseComBiz
     *
     * @param bizBaseComBiz baseComBiz
     * @return 结果
     */
    @Override
    public int updateBizBaseComBiz(BizBaseComBiz bizBaseComBiz) {
        bizBaseComBiz.setUpdateTime(DateUtils.getNowDate());
        List<BizBaseComBiz> bizBaseComBizList = bizBaseComBizMapper.selectBizBaseDataListCheck(bizBaseComBiz.getBizTable(),bizBaseComBiz);
        if (CollectionUtils.isNotEmpty(bizBaseComBizList)) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.MASTER_DATA_REPEAT, ""), DefaultErrorMessage.MASTER_DATA_REPEAT.intValue());
        }
        int i= bizBaseComBizMapper.updateBizBaseData(bizBaseComBiz.getBizTable(),bizBaseComBiz);
        setDataToRedis(bizBaseComBiz.getBizTable());
        return i;
    }

    private void setDataToRedis(String bizTable) {
        if ("biz_base_bw_bu".equals(bizTable)) {
            setSingleToRedis(new BizBaseComBiz(), "biz_base_bw_bu", "bw_bu");

        } else if ("biz_base_bpc_bu".equals(bizTable)) {
            setSingleToRedis(new BizBaseComBiz(), "biz_base_bpc_bu", "bpc_bu");
        } else if ("biz_base_customer_group".equals(bizTable)) {
            setSingleToRedis(new BizBaseComBiz(), "biz_base_customer_group", "customer_group");
        } else if ("biz_base_bu".equals(bizTable)) {
            setSingleToRedis(new BizBaseComBiz(), "biz_base_bu", "base_bu");
        }
    }

    /**
     * 删除baseComBiz对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizBaseComBizByIds(String loginName,String bizTable,String ids) {
        int i= bizBaseComBizMapper.deleteBizBaseComBizByIds(loginName,bizTable, Convert.toStrArray(ids));
        setDataToRedis(bizTable);
        return i;
    }

    /**
     * 删除baseComBiz信息
     *
     * @param id baseComBizID
     * @return 结果
     */
    @Override
    public int deleteBizBaseComBizById(Integer id) {
        return bizBaseComBizMapper.deleteBizBaseComBizById(id);
    }

    @Override
    public String importExcel(List<BizBaseComBiz> bizs, String bizTable, String name) {

        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseComBiz bizBaseComBiz : bizs) {
            try {

                bizBaseComBiz.setGeoCode(bizBaseComBiz.getGeoCode());
                bizBaseComBiz.setBizTable(bizBaseComBiz.getBizTable());
                bizBaseComBiz.setBizCode(bizBaseComBiz.getBizCode());
                List<BizBaseComBiz> buList = bizBaseComBizMapper.selectBizBaseDataList(bizBaseComBiz);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseComBiz.setId(buList.get(0).getId());
                    bizBaseComBiz.setUpdateTime(new Date());
                    bizBaseComBizMapper.updateBizBaseData(bizTable,bizBaseComBiz);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseComBiz.setCreateBy(name);
                bizBaseComBiz.setCreateTime(DateUtils.getNowDate());
                bizBaseComBiz.setCreateTime(DateUtils.getNowDate());
                bizBaseComBiz.setUpdateTime(DateUtils.getNowDate());
                bizBaseComBiz.setCreateBy(name);
                bizBaseComBiz.setStatus("Y");
                bizBaseComBizMapper.insertBizBaseData(bizTable,bizBaseComBiz);

                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "MasterData导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum.intValue() > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();

    }
}
