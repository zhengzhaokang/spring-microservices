package com.microservices.otmp.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.erp.common.WordBaseData;
import com.microservices.otmp.erp.domain.SysWording;
import com.microservices.otmp.erp.mapper.SysWordingMapper;
import com.microservices.otmp.erp.service.ISysWordingService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * wordService业务层处理
 * 
 * @author shirui3
 * @date 2022-05-07
 */
@Service
public class SysWordingServiceImpl implements ISysWordingService
{

    public static final String REDIS_NAME_PREFIX = "word:";
    public static final String REDIS_WORD = "REDIS_WORD";

    @Autowired
    private RedisUtils redis;

    @Autowired
    private SysWordingMapper sysWordingMapper;

    /**
     * 查询word
     * 
     * @param id word主键
     * @return word
     */
    @Override
    public SysWording selectSysWordingById(Long id)
    {
        return sysWordingMapper.selectSysWordingById(id);
    }

    /**
     * 从redis获取word提示语
     * @return word
     */
    @Override
    public SysWording getWordFromRedis(Long wordingKey){
        String jsonWord = redis.get(WordBaseData.REDIS_WORD,String.class);
        if (StringUtils.isEmpty(jsonWord)) {
            insertWordIntoRedis(null);
            jsonWord = redis.get(WordBaseData.REDIS_WORD,String.class);
        }
        if (!StringUtils.isEmpty(jsonWord)) {
            JSONArray sysWordJson = JSON.parseArray(jsonWord);
            List<SysWording> sysWordings = sysWordJson.toJavaList(SysWording.class);
            for (SysWording sysWording : sysWordings) {
                if (wordingKey.equals(sysWording.getWordingKey())) {
                    return sysWording;
                }
            }
        }
        return null;
    }

    /**
     * 从redis获取word提示语 json
     * @return word
     */
    @Override
    public String json(){
        String jsonWord = redis.get(WordBaseData.REDIS_WORD,String.class);
        if (StringUtils.isEmpty(jsonWord))
        {
            insertWordIntoRedis(null);
            jsonWord = redis.get(WordBaseData.REDIS_WORD,String.class);
        }
        return jsonWord;
    }

    /**
     * 查询word列表
     * 
     * @param sysWording word
     * @return word
     */
    @Override
    public List<SysWording> selectSysWordingList(SysWording sysWording)
    {
        return sysWordingMapper.selectSysWordingList(sysWording);
    }

    /**
     * 新增word
     * 
     * @param sysWording word
     * @return 结果
     */
    @Override
    public int insertSysWording(SysWording sysWording)
    {
        SysWording sw = new SysWording();
        sw.setWordingCode(sysWording.getWordingCode());
        sw.setWordingKey(sysWording.getWordingKey());
        List<SysWording> sy = sysWordingMapper.selectSysWordingListForCheck(sw);
        if(!CommonUtils.isNullList(sy)){
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.WORD_CODE_REPEAT, ""), DefaultErrorMessage.WORD_CODE_REPEAT.intValue());
        }
        int res;
        sysWording.setStatus(WordBaseData.ENABLE_Y);
        res = sysWordingMapper.insertSysWording(sysWording);
        if(res==1){
            insertWordIntoRedis(sysWording);
        }
        return res;
    }

    /**
     * 修改word
     * 
     * @param sysWording word
     * @return 结果
     */
    @Override
    public int updateSysWording(SysWording sysWording)
    {
        int res;
        res = sysWordingMapper.updateSysWording(sysWording);
        if(res==1){
            insertWordIntoRedis(sysWording);
        }
        return res;
    }

    /**
     * 批量删除word
     * 
     * @param ids 需要删除的word主键
     * @return 结果
     */
    @Override
    public int deleteSysWordingByIds(Long[] ids)
    {
        return sysWordingMapper.deleteSysWordingByIds(ids);
    }

    /**
     * 删除word信息
     * 
     * @param id word主键
     * @return 结果
     */
    @Override
    public int deleteSysWordingById(Long id)
    {
        return sysWordingMapper.deleteSysWordingById(id);
    }

    /**
     * word提示语插入到redis
     */
    @Override
    public void insertWordIntoRedis(SysWording sysWording){
        List<SysWording> sysWordingList = sysWordingMapper.selectSysWordingListForRedis(null);

        //更新redis
        redis.set(WordBaseData.REDIS_WORD, sysWordingList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        if(null!=sysWording){
            redis.set(REDIS_NAME_PREFIX+sysWording.getWordingKey(), sysWording, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        }
    }

    @Override
    public void refreshWordIntoRedis(){
        List<SysWording> sysWordingList = sysWordingMapper.selectSysWordingListForRedis(null);

        //更新redis
        redis.set(WordBaseData.REDIS_WORD, sysWordingList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        for (SysWording sw :sysWordingList) {
            redis.set(REDIS_NAME_PREFIX+sw.getWordingKey(), sw, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        }
    }
    @Override
    public String importExcel(List<SysWording> wordings, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(wordings)) {
            throw new ServiceException("导入提示语 数据不能为空！");
        }
        for (SysWording word : wordings) {
            try {

                List<SysWording> buList = sysWordingMapper.selectSysWordingListForCheck(word);
                if (CollectionUtils.isNotEmpty(buList)) {
                    word.setId(buList.get(0).getId());
                    word.setUpdateTime(DateUtils.getNowDate());
                    sysWordingMapper.updateSysWording(word);
                    successNum.getAndIncrement();
                    continue;
                }
                word.setCreateTime(DateUtils.getNowDate());
                word.setUpdateTime(DateUtils.getNowDate());
                word.setCreateBy(loginName);
                word.setStatus("Y");
                sysWordingMapper.insertSysWording(word);

                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "提示语导入失败：";
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
