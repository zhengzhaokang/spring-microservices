package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDcDivisionMapping;
import com.microservices.otmp.masterdata.mapper.BizBaseDcDivisionMappingMapper;
import com.microservices.otmp.masterdata.service.IBizBaseDcDivisionMappingService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDcDivisionMapping;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * BaseDcDivisionMappingService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-25
 */
@Service
public class BizBaseDcDivisionMappingServiceImpl implements IBizBaseDcDivisionMappingService {
    @Autowired
    private BizBaseDcDivisionMappingMapper bizBaseDcDivisionMappingMapper;

    /**
     * 查询BaseDcDivisionMapping
     *
     * @param id BaseDcDivisionMapping主键
     * @return BaseDcDivisionMapping
     */
    @Override
    public BizBaseDcDivisionMapping selectBizBaseDcDivisionMappingById(Long id) {
        return bizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingById(id);
    }

    /**
     * 查询BaseDcDivisionMapping列表
     *
     * @param bizBaseDcDivisionMapping BaseDcDivisionMapping
     * @return BaseDcDivisionMapping
     */
    @Override
    public List<BizBaseDcDivisionMapping> selectBizBaseDcDivisionMappingList(BizBaseDcDivisionMapping bizBaseDcDivisionMapping) {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseDcDivisionMapping.getIds());
        bizBaseDcDivisionMapping.setIdsList(longIdsList);
        return bizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingList(bizBaseDcDivisionMapping);
    }

    /**
     * 新增BaseDcDivisionMapping
     *
     * @param bizBaseDcDivisionMapping BaseDcDivisionMapping
     * @return 结果
     */
    @Override
    public int insertBizBaseDcDivisionMapping(BizBaseDcDivisionMapping bizBaseDcDivisionMapping) {
        bizBaseDcDivisionMapping.setCreateTime(DateUtils.getNowDate());
        return bizBaseDcDivisionMappingMapper.insertBizBaseDcDivisionMapping(bizBaseDcDivisionMapping);
    }

    /**
     * 修改BaseDcDivisionMapping
     *
     * @param bizBaseDcDivisionMapping BaseDcDivisionMapping
     * @return 结果
     */
    @Override
    public int updateBizBaseDcDivisionMapping(BizBaseDcDivisionMapping bizBaseDcDivisionMapping) {
        bizBaseDcDivisionMapping.setUpdateTime(DateUtils.getNowDate());
        return bizBaseDcDivisionMappingMapper.updateBizBaseDcDivisionMapping(bizBaseDcDivisionMapping);
    }

    /**
     * 批量删除BaseDcDivisionMapping
     *
     * @param ids 需要删除的BaseDcDivisionMapping主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseDcDivisionMappingByIds(Long[] ids) {
        return bizBaseDcDivisionMappingMapper.updateBizBaseDcDivisionMappingByIds(ids);
    }

    /**
     * 删除BaseDcDivisionMapping信息
     *
     * @param id BaseDcDivisionMapping主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseDcDivisionMappingById(Long id) {
        return bizBaseDcDivisionMappingMapper.deleteBizBaseDcDivisionMappingById(id);
    }

    @Override
    public String importExcel(List<BizBaseDcDivisionMapping> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        BizBaseDcDivisionMapping mapping = new BizBaseDcDivisionMapping();
        for (BizBaseDcDivisionMapping baseDcDivisionMapping : bizs) {
            try {
                mapping.setSalesOrgCode(baseDcDivisionMapping.getSalesOrgCode());
                mapping.setSalesOfficeCode(baseDcDivisionMapping.getSalesOfficeCode());
                mapping.setDcCode(baseDcDivisionMapping.getDcCode());
                mapping.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
                List<BizBaseDcDivisionMapping> buList = bizBaseDcDivisionMappingMapper.selectBizBaseProfitCenterListCheck(mapping);
                if (CollectionUtils.isNotEmpty(buList)) {
                    baseDcDivisionMapping.setId(buList.get(0).getId());
                    baseDcDivisionMapping.setUpdateTime(DateUtils.getNowDate());
                    bizBaseDcDivisionMappingMapper.updateBizBaseDcDivisionMapping(baseDcDivisionMapping);
                    successNum.getAndIncrement();
                    continue;
                }
                baseDcDivisionMapping.setCreateTime(DateUtils.getNowDate());
                baseDcDivisionMapping.setUpdateTime(DateUtils.getNowDate());
                baseDcDivisionMapping.setCreateBy(loginName);
                baseDcDivisionMapping.setStatus(BooleanStatusEnum.TRUE_Y.getCode());

                bizBaseDcDivisionMappingMapper.insertBizBaseDcDivisionMapping(baseDcDivisionMapping);

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
    public List<BizBaseDcDivisionMapping> dropDownList(BizBaseDcDivisionMapping bizBaseDcDivisionMapping) {
        List<BizBaseDcDivisionMapping> bizBaseDcDivisionMappings = this.selectBizBaseDcDivisionMappingList(bizBaseDcDivisionMapping);

        return bizBaseDcDivisionMappings.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BizBaseDcDivisionMapping::getDcCode))), ArrayList::new)
        );
    }

}
