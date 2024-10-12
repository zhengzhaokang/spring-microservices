package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseProductHierarchyByLevel;
import com.microservices.otmp.masterdata.mapper.BizBaseProductHierarchyByLevelMapper;
import com.microservices.otmp.masterdata.service.IBizBaseProductHierarchyByLevelService;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseProductHierarchyByLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BaseProductHierarchyByLevelService业务层处理
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
@Service
public class BizBaseProductHierarchyByLevelServiceImpl implements IBizBaseProductHierarchyByLevelService
{
    public static final String PH_LEVEL_1 = "ph_level1";
    public static final String PH_LEVEL_3 = "ph_level3";
    public static final String PH_LEVEL_4 = "ph_level4";
    @Autowired
    private BizBaseProductHierarchyByLevelMapper bizBaseProductHierarchyByLevelMapper;


    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";
    public void setPhLevelToRedis(List<BizBaseProductHierarchyByLevel> hierarchyByLevels, Integer s, String phLevel1) {
        List<String> byLevel1s = hierarchyByLevels.stream().filter(t -> t.getPhLevel().equals(s)).map(BizBaseProductHierarchyByLevel::getProductHierarchyCode).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX + phLevel1, byLevel1s, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }
    /**
     * 查询BaseProductHierarchyByLevel
     * 
     * @param id BaseProductHierarchyByLevel主键
     * @return BaseProductHierarchyByLevel
     */
    @Override
    public BizBaseProductHierarchyByLevel selectBizBaseProductHierarchyByLevelById(Long id)
    {
        return bizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelById(id);
    }

    /**
     * 查询BaseProductHierarchyByLevel列表
     * 
     * @param bizBaseProductHierarchyByLevel BaseProductHierarchyByLevel
     * @return BaseProductHierarchyByLevel
     */
    @Override
    public List<BizBaseProductHierarchyByLevel> selectBizBaseProductHierarchyByLevelList(BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel)
    {
        return bizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(bizBaseProductHierarchyByLevel);
    }

    /**
     * 新增BaseProductHierarchyByLevel
     * 
     * @param bizBaseProductHierarchyByLevel BaseProductHierarchyByLevel
     * @return 结果
     */
    @Override
    public int insertBizBaseProductHierarchyByLevel(BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel)
    {
        bizBaseProductHierarchyByLevel.setCreateTime(DateUtils.getNowDate());
        int  i= bizBaseProductHierarchyByLevelMapper.insertBizBaseProductHierarchyByLevel(bizBaseProductHierarchyByLevel);
        List<BizBaseProductHierarchyByLevel> hierarchyByLevels = this.selectBizBaseProductHierarchyByLevelList(new BizBaseProductHierarchyByLevel());
            setPhLevelToRedis(hierarchyByLevels, 1, PH_LEVEL_1);
            setPhLevelToRedis(hierarchyByLevels, 3, PH_LEVEL_3);
            setPhLevelToRedis(hierarchyByLevels, 4, PH_LEVEL_4);
            setPhLevelToRedis(hierarchyByLevels, 2, "ph_level2");
            setPhLevelToRedis(hierarchyByLevels, 5, "ph_level5");
            setPhLevelToRedis(hierarchyByLevels, 6, "ph_level6");
        return i;
    }

    /**
     * 修改BaseProductHierarchyByLevel
     * 
     * @param bizBaseProductHierarchyByLevel BaseProductHierarchyByLevel
     * @return 结果
     */
    @Override
    public int updateBizBaseProductHierarchyByLevel(BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel)
    {
        bizBaseProductHierarchyByLevel.setUpdateTime(DateUtils.getNowDate());
        int i= bizBaseProductHierarchyByLevelMapper.updateBizBaseProductHierarchyByLevel(bizBaseProductHierarchyByLevel);
        List<BizBaseProductHierarchyByLevel> hierarchyByLevels = this.selectBizBaseProductHierarchyByLevelList(new BizBaseProductHierarchyByLevel());

        setPhLevelToRedis(hierarchyByLevels, 1, PH_LEVEL_1);
        setPhLevelToRedis(hierarchyByLevels, 3, PH_LEVEL_3);
        setPhLevelToRedis(hierarchyByLevels, 4, PH_LEVEL_4);
        return i;
    }

    /**
     * 批量删除BaseProductHierarchyByLevel
     * 
     * @param ids 需要删除的BaseProductHierarchyByLevel主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseProductHierarchyByLevelByIds(Long[] ids)
    {
        int  i= bizBaseProductHierarchyByLevelMapper.deleteBizBaseProductHierarchyByLevelByIds(ids);
        List<BizBaseProductHierarchyByLevel> hierarchyByLevels = this.selectBizBaseProductHierarchyByLevelList(new BizBaseProductHierarchyByLevel());

        setPhLevelToRedis(hierarchyByLevels, 1, PH_LEVEL_1);
        setPhLevelToRedis(hierarchyByLevels, 3, PH_LEVEL_3);
        setPhLevelToRedis(hierarchyByLevels, 4, PH_LEVEL_4);
        return i;
    }

    /**
     * 删除BaseProductHierarchyByLevel信息
     * 
     * @param id BaseProductHierarchyByLevel主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseProductHierarchyByLevelById(Long id)
    {
        int i= bizBaseProductHierarchyByLevelMapper.deleteBizBaseProductHierarchyByLevelById(id);
        List<BizBaseProductHierarchyByLevel> hierarchyByLevels = this.selectBizBaseProductHierarchyByLevelList(new BizBaseProductHierarchyByLevel());

        setPhLevelToRedis(hierarchyByLevels, 1, PH_LEVEL_1);
        setPhLevelToRedis(hierarchyByLevels, 3, PH_LEVEL_3);
        setPhLevelToRedis(hierarchyByLevels, 4, PH_LEVEL_4);
        return i;
    }

    @Override
    public String importExcel(List<BizBaseProductHierarchyByLevel> bizs, String loginName) {
        return null;
    }
}
