package com.microservices.otmp.masterdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsGtnTypeDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsGtnTypeVo;
import com.microservices.otmp.masterdata.manager.BizBaseGtnTypeManager;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseGtnTypeMapper;
import com.microservices.otmp.masterdata.domain.BizBaseGtnType;
import com.microservices.otmp.masterdata.service.IBizBaseGtnTypeService;

/**
 * BaseGtnTypeService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-24
 */
@Service
public class BizBaseGtnTypeServiceImpl implements IBizBaseGtnTypeService
{
    @Autowired
    private BizBaseGtnTypeMapper bizBaseGtnTypeMapper;

    @Autowired
    private BizBaseGtnTypeManager bizBaseGtnTypeManager;


    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";
    public void setGtnTypeToRedis() {
        List<BizBaseGtnType> list = this.getDropDownList(new BizBaseDropDownCondition());
        List<BizBaseGtnType> allList = this.getAllList(new BizBaseDropDownCondition());
        List<String> bizBaseGtnTypes = list.stream().map(BizBaseGtnType::getGtnTypeCode).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX+"gtn_type",bizBaseGtnTypes, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        redisUtils.set(REDIS_NAME_PREFIX+"biz_gtn_type",allList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }


    /**
     * 查询BaseGtnType
     *
     * @param id BaseGtnType主键
     * @return BaseGtnType
     */
    @Override
    public BizBaseGtnType selectBizBaseGtnTypeById(Long id)
    {
        return bizBaseGtnTypeMapper.selectBizBaseGtnTypeById(id);
    }

    /**
     * 查询BaseGtnType列表
     *
     * @param bizBaseGtnType BaseGtnType
     * @return BaseGtnType
     */
    @Override
    public List<BizBaseGtnType> selectBizBaseGtnTypeList(BizBaseGtnType bizBaseGtnType)
    {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseGtnType.getIds());
        bizBaseGtnType.setIdsList(longIdsList);
        return bizBaseGtnTypeMapper.selectBizBaseGtnTypeList(bizBaseGtnType);
    }

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseGtnType集合
     */
    @Override
    public List<BizBaseGtnType> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {

        List<BizBaseGtnType> list = bizBaseGtnTypeMapper.getDropDownList(bizBaseDropDownCondition);
        //根据需要的下拉框label去重
        if(CommonUtils.stringIsNotEmpty(bizBaseDropDownCondition.getTempField())){
            if (bizBaseDropDownCondition.getTempField().equals("orderReason")){
                list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getCndnOrderReason())).filter(CommonUtils.distinctByKey(BizBaseGtnType::getCndnOrderReason)).collect(Collectors.toList());
            } else {
                list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getGtnTypeCode())).filter(CommonUtils.distinctByKey(BizBaseGtnType::getGtnTypeCode)).collect(Collectors.toList());
            }
        }else{
            list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getGtnTypeCode())).filter(CommonUtils.distinctByKey(BizBaseGtnType::getGtnTypeCode)).collect(Collectors.toList());
        }
        return  list;
    }

    @Override
    public List<BizBaseGtnType> getAllList(BizBaseDropDownCondition bizBaseDropDownCondition) {
        List<BizBaseGtnType> list = bizBaseGtnTypeMapper.getDropDownList(bizBaseDropDownCondition);
        if (null == list || list.size() <= 0) {
            return list;
        }
        Map<String, BizBaseGtnType> map = new HashMap<>();
        for (BizBaseGtnType bizBaseGtnType : list) {
            String bg = bizBaseGtnType.getBusinessGroup();
            String geo = bizBaseGtnType.getGeoCode();
            String gtnTypeCode = bizBaseGtnType.getGtnTypeCode();
            if (StrUtil.isBlank(bg) || StrUtil.isBlank(geo) || StrUtil.isBlank(gtnTypeCode)) {
                continue;
            }
            map.put(bg + geo + gtnTypeCode, bizBaseGtnType);
        }
        if (map.size() > 0) {
            return new ArrayList<>(map.values());
        }
        return list;
    }

    /**
     * 新增BaseGtnType
     *
     * @param bizBaseGtnType BaseGtnType
     * @return 结果
     */
    @Override
    public int insertBizBaseGtnType(BizBaseGtnType bizBaseGtnType)
    {
        bizBaseGtnType.setCreateTime(DateUtils.getNowDate());
        int  i= bizBaseGtnTypeMapper.insertBizBaseGtnType(bizBaseGtnType);
        setGtnTypeToRedis();
        return  i;
    }

    /**
     * 修改BaseGtnType
     *
     * @param bizBaseGtnType BaseGtnType
     * @return 结果
     */
    @Override
    public int updateBizBaseGtnType(BizBaseGtnType bizBaseGtnType)
    {
        bizBaseGtnType.setUpdateTime(DateUtils.getNowDate());
        int  i=  bizBaseGtnTypeMapper.updateBizBaseGtnType(bizBaseGtnType);
        setGtnTypeToRedis();
        return  i;
    }

    /**
     * 批量删除BaseGtnType
     *
     * @param ids 需要删除的BaseGtnType主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseGtnTypeByIds(Long[] ids)
    {
        int  i=  bizBaseGtnTypeMapper.updateBizBaseGtnTypeIds(ids);
        setGtnTypeToRedis();
        return  i;
    }

    /**
     * 删除BaseGtnType信息
     *
     * @param id BaseGtnType主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseGtnTypeById(Long id)
    {
        int  i=  bizBaseGtnTypeMapper.deleteBizBaseGtnTypeById(id);
        setGtnTypeToRedis();
        return  i;
    }

    @Override
    public String importExcel(List<BizBaseGtnType> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseGtnType bizBaseGtnType : bizs) {
            try {
                bizBaseGtnType.setStatus("Y");
                List<BizBaseGtnType> buList = bizBaseGtnTypeMapper.selectBizBaseGtnTypeListCheck(bizBaseGtnType);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseGtnType.setId(buList.get(0).getId());
                    bizBaseGtnType.setUpdateTime(DateUtils.getNowDate());
                    bizBaseGtnTypeMapper.updateBizBaseGtnType(bizBaseGtnType);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseGtnType.setCreateTime(DateUtils.getNowDate());
                bizBaseGtnType.setUpdateTime(DateUtils.getNowDate());
                bizBaseGtnType.setCreateBy(loginName);
                bizBaseGtnTypeMapper.insertBizBaseGtnType(bizBaseGtnType);

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
    public List<MsGtnTypeVo> toMsGtnTypeList(ToMsGtnTypeDTO toMsGtnTypeDTO) {
        return bizBaseGtnTypeManager.toMsGtnTypeList(toMsGtnTypeDTO);
    }
}
