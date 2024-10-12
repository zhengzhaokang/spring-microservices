package com.microservices.otmp.masterdata.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import com.microservices.otmp.masterdata.domain.BizBaseApcDO;
import com.microservices.otmp.masterdata.domain.BizBaseTos;
import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;
import com.microservices.otmp.masterdata.manager.BizBaseApcManager;
import com.microservices.otmp.masterdata.service.IBizBaseApcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BaseApcService业务层处理
 * 
 * @author lovefamily
 * @date 2022-07-15
 */
@Service
public class BizBaseApcServiceImpl implements IBizBaseApcService
{
    @Autowired
    private BizBaseApcManager bizBaseApcManager;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";


    public void setMtmToRedis(List<BizBaseApcDTO> bizBaseApcList) {
        List<String> mtmList = bizBaseApcList.stream().map(BizBaseApcDTO::getMtm).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX+"mtm",mtmList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setApcToRedis(List<BizBaseApcDTO> bizBaseApcList) {
        List<String> apcCodeList = bizBaseApcList.stream().map(BizBaseApcDTO::getApcCode).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX+"apc_code",apcCodeList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }


    /**
     * 查询BaseApc
     * 
     * @param id BaseApc主键
     * @return BaseApc
     */
    @Override
    public BizBaseApcDTO selectBizBaseApcById(Long id)
    {
        BizBaseApcDO bizBaseApcDO = bizBaseApcManager.selectBizBaseApcById(id);
        BizBaseApcDTO bizBaseApcDTO = new BizBaseApcDTO();
        BeanUtils.copyProperties(bizBaseApcDO,bizBaseApcDTO);
        return bizBaseApcDTO;
    }

    /**
     * 查询BaseApc列表
     * 
     * @param bizBaseApc BaseApc
     * @return BaseApc
     */
    @Override
    public List<BizBaseApcDTO> selectBizBaseApcList(BizBaseApcDTO bizBaseApc)
    {
        BizBaseApcDO bizBaseApcDO = new BizBaseApcDO();
        BeanUtils.copyProperties(bizBaseApc, bizBaseApcDO);
        List<BizBaseApcDO> bizBaseApcDOList = bizBaseApcManager.selectBizBaseApcList(bizBaseApcDO);
        ArrayList<BizBaseApcDTO> resultList = Lists.newArrayList();
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseApcDOList, resultList, BizBaseApcDTO.class);
        return resultList;
    }

    @Override
    public PageInfo<BizBaseApcDTO> selectBizBaseApcListByPage(BizBaseApcDTO bizBaseApc)
    {
        BizBaseApcDO bizBaseApcDO = new BizBaseApcDO();
        BeanUtils.copyProperties(bizBaseApc, bizBaseApcDO);
        List<BizBaseApcDO> bizBaseApcDOList = bizBaseApcManager.selectBizBaseApcList(bizBaseApcDO);

        PageInfo<BizBaseApcDO> pageInfo = new PageInfo<>(bizBaseApcDOList);
        List<BizBaseApcDTO> resultList = new ArrayList<>();
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseApcDOList, resultList,BizBaseApcDTO.class);
        PageInfo<BizBaseApcDTO> resultPageInfo = new PageInfo<>(resultList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    /**
     * 新增BaseApc
     * 
     * @param bizBaseApc BaseApc
     * @return 结果
     */
    @Override
    public int insertBizBaseApc(BizBaseApcDTO bizBaseApc)
    {
        bizBaseApc.setCreateTime(DateUtils.getNowDate());
        BizBaseApcDO bizBaseApcDO = new BizBaseApcDO();
        BeanUtils.copyProperties(bizBaseApc, bizBaseApcDO);
        int  i= bizBaseApcManager.insertBizBaseApc(bizBaseApcDO);
        List<BizBaseApcDTO> bizBaseApcList = this.selectBizBaseApcList(new BizBaseApcDTO());
      setMtmToRedis(bizBaseApcList);

       setApcToRedis(bizBaseApcList) ;
       return i;
    }

    /**
     * 修改BaseApc
     * 
     * @param bizBaseApc BaseApc
     * @return 结果
     */
    @Override
    public int updateBizBaseApc(BizBaseApcDTO bizBaseApc)
    {
        bizBaseApc.setUpdateTime(DateUtils.getNowDate());
        BizBaseApcDO bizBaseApcDO = new BizBaseApcDO();
        BeanUtils.copyProperties(bizBaseApc, bizBaseApcDO);

        int i= bizBaseApcManager.updateBizBaseApc(bizBaseApcDO);
         List<BizBaseApcDTO> bizBaseApcList = this.selectBizBaseApcList(new BizBaseApcDTO());
        setMtmToRedis(bizBaseApcList);

        setApcToRedis(bizBaseApcList) ;
        return i;
    }

    /**
     * 批量删除BaseApc
     * 
     * @param ids 需要删除的BaseApc主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseApcByIds(Long[] ids)
    {
        int i= bizBaseApcManager.deleteBizBaseApcByIds(ids);
         List<BizBaseApcDTO> bizBaseApcList = this.selectBizBaseApcList(new BizBaseApcDTO());
        setMtmToRedis(bizBaseApcList);

        setApcToRedis(bizBaseApcList) ;
        return i;
    }

    /**
     * 删除BaseApc信息
     * 
     * @param id BaseApc主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseApcById(Long id)
    {
        int i= bizBaseApcManager.deleteBizBaseApcById(id);
         List<BizBaseApcDTO> bizBaseApcList = this.selectBizBaseApcList(new BizBaseApcDTO());
        setMtmToRedis(bizBaseApcList);

        setApcToRedis(bizBaseApcList) ;
        return i;
    }

}
