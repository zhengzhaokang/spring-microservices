package com.microservices.otmp.masterdata.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseTosMapper;
import com.microservices.otmp.masterdata.domain.BizBaseTos;
import com.microservices.otmp.masterdata.service.IBizBaseTosService;

/**
 * BaseTosService业务层处理
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
@Service
public class BizBaseTosServiceImpl implements IBizBaseTosService
{
    @Autowired
    private BizBaseTosMapper bizBaseTosMapper;

    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";
    public void setTosToRedis() {
        List<String> bizBaseTos = this.selectBizBaseTosList(new BizBaseTos()).stream().map(BizBaseTos::getMemberId).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX+"tos",bizBaseTos, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BaseTos
     * 
     * @param id BaseTos主键
     * @return BaseTos
     */
    @Override
    public BizBaseTos selectBizBaseTosById(Long id)
    {
        return bizBaseTosMapper.selectBizBaseTosById(id);
    }

    /**
     * 查询BaseTos列表
     * 
     * @param bizBaseTos BaseTos
     * @return BaseTos
     */
    @Override
    public List<BizBaseTos> selectBizBaseTosList(BizBaseTos bizBaseTos)
    {
        return bizBaseTosMapper.selectBizBaseTosList(bizBaseTos);
    }

    /**
     * 新增BaseTos
     * 
     * @param bizBaseTos BaseTos
     * @return 结果
     */
    @Override
    public int insertBizBaseTos(BizBaseTos bizBaseTos)
    {
        bizBaseTos.setCreateTime(DateUtils.getNowDate());
        int i= bizBaseTosMapper.insertBizBaseTos(bizBaseTos);
        setTosToRedis();
        return  i;
    }

    /**
     * 修改BaseTos
     * 
     * @param bizBaseTos BaseTos
     * @return 结果
     */
    @Override
    public int updateBizBaseTos(BizBaseTos bizBaseTos)
    {
        bizBaseTos.setUpdateTime(DateUtils.getNowDate());
        int i= bizBaseTosMapper.updateBizBaseTos(bizBaseTos);
        setTosToRedis();
        return  i;
    }

    /**
     * 批量删除BaseTos
     * 
     * @param ids 需要删除的BaseTos主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseTosByIds(Long[] ids)
    {
        int i= bizBaseTosMapper.deleteBizBaseTosByIds(ids);
        setTosToRedis();
        return  i;
    }


    /**
     * 删除BaseTos信息
     * 
     * @param id BaseTos主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseTosById(Long id)
    {
        int i= bizBaseTosMapper.deleteBizBaseTosById(id);
        setTosToRedis();
        return  i;
    }

    @Override
    public String importExcel(List<BizBaseTos> bizs, String loginName) {
        return null;
    }
}
