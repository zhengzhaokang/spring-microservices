package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseCountry;
import com.microservices.otmp.masterdata.mapper.BizBaseCountryMapper;
import com.microservices.otmp.masterdata.service.IBizBaseCountryService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BaseCountryService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-24
 */
@Service
public class BizBaseCountryServiceImpl implements IBizBaseCountryService {
    @Autowired
    private BizBaseCountryMapper bizBaseCountryMapper;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setCountryToRedis() {
        List<BizBaseCountry> bizBaseCountries = this.selectBizBaseCountryList(new BizBaseCountry());
        redisUtils.set(REDIS_NAME_PREFIX + "bpc_country", bizBaseCountries, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BaseCountry
     *
     * @param id BaseCountry主键
     * @return BaseCountry
     */
    @Override
    public BizBaseCountry selectBizBaseCountryById(Long id) {
        return bizBaseCountryMapper.selectBizBaseCountryById(id);
    }

    /**
     * 查询BaseCountry列表
     *
     * @param bizBaseCountry BaseCountry
     * @return BaseCountry
     */
    @Override
    public List<BizBaseCountry> selectBizBaseCountryList(BizBaseCountry bizBaseCountry) {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseCountry.getIds());
        bizBaseCountry.setIdsList(longIdsList);
        return bizBaseCountryMapper.selectBizBaseCountryList(bizBaseCountry);
    }

    /**
     * 查询BaseCountry列表 去重
     *
     * @param bizBaseCountry BaseCountry
     * @return BaseCountry集合
     */
    @Override
    public List<BizBaseCountry> selectCountrylist(BizBaseCountry bizBaseCountry) {
        return bizBaseCountryMapper.selectCountrylist(bizBaseCountry);
    }

    /**
     * 新增BaseCountry
     *
     * @param bizBaseCountry BaseCountry
     * @return 结果
     */
    @Override
    public int insertBizBaseCountry(BizBaseCountry bizBaseCountry) {
        bizBaseCountry.setCreateTime(DateUtils.getNowDate());
        int i = bizBaseCountryMapper.insertBizBaseCountry(bizBaseCountry);
        setCountryToRedis();
        return i;
    }

    /**
     * 修改BaseCountry
     *
     * @param bizBaseCountry BaseCountry
     * @return 结果
     */
    @Override
    public int updateBizBaseCountry(BizBaseCountry bizBaseCountry) {
        bizBaseCountry.setUpdateTime(DateUtils.getNowDate());
        int i = bizBaseCountryMapper.updateBizBaseCountry(bizBaseCountry);
        setCountryToRedis();
        return i;
    }

    /**
     * 批量删除BaseCountry
     *
     * @param ids 需要删除的BaseCountry主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCountryByIds(Long[] ids) {
        int i = bizBaseCountryMapper.updateBizBaseCountryByIds(ids);
        setCountryToRedis();
        return i;
    }

    /**
     * 删除BaseCountry信息
     *
     * @param id BaseCountry主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCountryById(Long id) {
        int i = bizBaseCountryMapper.deleteBizBaseCountryById(id);
        setCountryToRedis();
        return i;
    }

    @Override
    public String importExcel(List<BizBaseCountry> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseCountry bizBaseCountry : bizs) {
            try {

                List<BizBaseCountry> buList = bizBaseCountryMapper.selectBizBaseCountryListCheck(bizBaseCountry);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseCountry.setId(buList.get(0).getId());
                    bizBaseCountry.setUpdateTime(DateUtils.getNowDate());
                    bizBaseCountryMapper.updateBizBaseCountry(bizBaseCountry);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseCountry.setCreateTime(DateUtils.getNowDate());
                bizBaseCountry.setUpdateTime(DateUtils.getNowDate());
                bizBaseCountry.setCreateBy(loginName);
                bizBaseCountry.setStatus("Y");
                bizBaseCountryMapper.insertBizBaseCountry(bizBaseCountry);

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

    @Override
    public List<BizBaseCountry> countrySelect(BizBaseCountry bizBaseCountry) {
        return bizBaseCountryMapper.countrySelect(bizBaseCountry);
    }

}
