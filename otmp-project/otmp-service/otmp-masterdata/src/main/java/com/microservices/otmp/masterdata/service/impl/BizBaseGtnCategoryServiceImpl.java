package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseGtnCategoryDO;
import com.microservices.otmp.masterdata.manager.IBizBaseGtnCategoryManager;
import com.microservices.otmp.masterdata.mapper.BizBaseGtnCategoryMapper;
import com.microservices.otmp.masterdata.service.IBizBaseGtnCategoryService;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * gtnCategoryService业务层处理
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
@Service
public class BizBaseGtnCategoryServiceImpl implements IBizBaseGtnCategoryService
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseGtnCategoryServiceImpl.class);

    @Autowired
    private IBizBaseGtnCategoryManager bizBaseGtnCategoryManager;

    @Autowired
    private BizBaseGtnCategoryMapper bizBaseGtnCategoryMapper;

    /**
     * 查询gtnCategory
     * 
     * @param id gtnCategory主键
     * @return gtnCategoryDTO
     */
    @Override
    public BizBaseGtnCategoryDTO selectBizBaseGtnCategoryById(Long id)
    {
         BizBaseGtnCategoryDO bizBaseGtnCategoryDO =  bizBaseGtnCategoryManager.selectBizBaseGtnCategoryById(id);
         BizBaseGtnCategoryDTO bizBaseGtnCategoryDTO = new BizBaseGtnCategoryDTO();
         BeanUtils.copyProperties(bizBaseGtnCategoryDO, bizBaseGtnCategoryDTO);
        return bizBaseGtnCategoryDTO;
    }

    /**
     * 查询gtnCategory列表
     *
     * @param bizBaseGtnCategory gtnCategory
     * @return gtnCategoryDTO
     */
    @Override
    public List<BizBaseGtnCategoryDTO> selectBizBaseGtnCategoryList(BizBaseGtnCategoryDTO bizBaseGtnCategory)
    {

        List<BizBaseGtnCategoryDO> bizBaseGtnCategoryDOS =
                    bizBaseGtnCategoryManager.selectBizBaseGtnCategoryList(bizBaseGtnCategory);
        List<BizBaseGtnCategoryDTO> bizBaseGtnCategoryList = new ArrayList<>(bizBaseGtnCategoryDOS.size());
        BeanUtils.copyListProperties(bizBaseGtnCategoryDOS, bizBaseGtnCategoryList, BizBaseGtnCategoryDTO.class);
        return bizBaseGtnCategoryList;

    }

        /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseGtnType集合
     */
    @Override
    public List<BizBaseGtnCategoryDTO> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {

        CommonUtils.stringIsEmpty(bizBaseDropDownCondition.getTempField(),"bizBaseDropDownCondition tempField is null!");
       /* if(bizBaseDropDownCondition.getTempField().equals("gtnCategoryL1")){
            CommonUtils.stringIsEmpty(bizBaseDropDownCondition.getGtnCategoryCode(),"bizBaseDropDownCondition gtnCategoryCode is null!");
        }*/

        List<BizBaseGtnCategoryDO> list = new ArrayList<>();
        if(bizBaseDropDownCondition.getTempField().equals("orderReason")){
            list = bizBaseGtnCategoryMapper.getDropDownOrderReasonList(bizBaseDropDownCondition);
        }else{
            list = bizBaseGtnCategoryMapper.getDropDownl1List(bizBaseDropDownCondition);
        }
        //根据需要的下拉框label去重

        if (bizBaseDropDownCondition.getTempField().equals("gtnCategoryL0")) {
            list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getGtnCategoryL0())).filter(CommonUtils.distinctByKey(BizBaseGtnCategoryDO::getGtnCategoryL0)).collect(Collectors.toList());
        } else if (bizBaseDropDownCondition.getTempField().equals("gtnCategoryL1")) {
            list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getGtnCategoryL1())).filter(CommonUtils.distinctByKey(BizBaseGtnCategoryDO::getGtnCategoryL1)).collect(Collectors.toList());
        } else if (bizBaseDropDownCondition.getTempField().equals("orderReason")) {
            list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getGtnCategoryL1())).filter(CommonUtils.distinctByKey(BizBaseGtnCategoryDO::getOrderReasonForCn)).collect(Collectors.toList());
        }else {
            throw new OtmpException("bizBaseDropDownCondition tempField is not gtnCategoryL0 or gtnCategoryL1 or orderReason!");
        }
        List<BizBaseGtnCategoryDTO> bizBaseGtnCategoryList = new ArrayList<>(list.size());
        BeanUtils.copyListProperties(list, bizBaseGtnCategoryList, BizBaseGtnCategoryDTO.class);
        return bizBaseGtnCategoryList;
    }

}
