package com.microservices.otmp.finance.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.finance.manager.IFinanceInvoiceApManager;
import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import com.microservices.otmp.finance.domain.entity.FinanceInvoiceApDO;
import com.microservices.otmp.finance.service.IFinanceInvoiceApService;

/**
 * Finance Invoice ApService业务层处理
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Service
public class FinanceInvoiceApServiceImpl implements IFinanceInvoiceApService {
    private static final Logger log = LoggerFactory.getLogger(FinanceInvoiceApServiceImpl.class);

    @Autowired
    private IFinanceInvoiceApManager financeInvoiceApManager;

    /**
     * 查询Finance Invoice Ap
     *
     * @param id Finance Invoice Ap主键
     * @return Finance Invoice ApDTO
     */
    @Override
    public FinanceInvoiceApDTO selectFinanceInvoiceApById(Long id) {
        FinanceInvoiceApDO financeInvoiceApDO = financeInvoiceApManager.selectFinanceInvoiceApById(id);
        FinanceInvoiceApDTO financeInvoiceApDTO = new FinanceInvoiceApDTO();
        BeanUtils.copyProperties(financeInvoiceApDO, financeInvoiceApDTO);
        return financeInvoiceApDTO;
    }

    /**
     * 查询Finance Invoice Ap列表
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return Finance Invoice ApDTO
     */
    @Override
    public List<FinanceInvoiceApDTO> selectFinanceInvoiceApList(FinanceInvoiceApDTO financeInvoiceAp) {

        List<FinanceInvoiceApDO> financeInvoiceApDOS =
                financeInvoiceApManager.selectFinanceInvoiceApList(financeInvoiceAp);
        List<FinanceInvoiceApDTO> financeInvoiceApList = new ArrayList<>(financeInvoiceApDOS.size());
        BeanUtils.copyListProperties(financeInvoiceApDOS, financeInvoiceApList, FinanceInvoiceApDTO.class);
        return financeInvoiceApList;

    }

    /**
     * 新增Finance Invoice Ap
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return 结果
     */
    @Override
    public int insertFinanceInvoiceAp(FinanceInvoiceApDTO financeInvoiceAp) {
        financeInvoiceAp.setCreateTime(DateUtils.getNowDate());
        FinanceInvoiceApDO financeInvoiceApDO = new FinanceInvoiceApDO();
        BeanUtils.copyProperties(financeInvoiceAp, financeInvoiceApDO);
        return financeInvoiceApManager.insertFinanceInvoiceAp(financeInvoiceApDO);
    }

    /**
     * 修改Finance Invoice Ap
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return 结果
     */
    @Override
    public int updateFinanceInvoiceAp(FinanceInvoiceApDTO financeInvoiceAp) {
        financeInvoiceAp.setUpdateTime(DateUtils.getNowDate());
        FinanceInvoiceApDO financeInvoiceApDO = new FinanceInvoiceApDO();
        BeanUtils.copyProperties(financeInvoiceAp, financeInvoiceApDO);
        return financeInvoiceApManager.updateFinanceInvoiceAp(financeInvoiceApDO);
    }

    @Override
    public int updateFinanceInvoiceApOfBatch(List<FinanceInvoiceApDTO> financeInvoiceApDTOS, FinanceInvoiceApDTO financeInvoiceAp) {
        int resultNum = 0;
        for (FinanceInvoiceApDTO tempFinanceInvoiceApDTO : financeInvoiceApDTOS) {
            FinanceInvoiceApDO financeInvoiceApDO = new FinanceInvoiceApDO();
            BeanUtils.copyProperties(tempFinanceInvoiceApDTO, financeInvoiceApDO);
            financeInvoiceApDO.setUpdateTime(DateUtils.getNowDate());
            financeInvoiceApDO.setStatusUpdateDate(financeInvoiceAp.getStatusUpdateDate());
            financeInvoiceApDO.setStatusDescription(financeInvoiceAp.getStatusDescription());
            financeInvoiceApDO.setStatus(financeInvoiceAp.getStatus());
            financeInvoiceApDO.setInterestRate(financeInvoiceAp.getInterestRate());
            resultNum += financeInvoiceApManager.updateFinanceInvoiceAp(financeInvoiceApDO);
        }
        return resultNum;
    }

    /**
     * 批量删除Finance Invoice Ap
     *
     * @param ids 需要删除的Finance Invoice Ap主键
     * @return 结果
     */
    @Override
    public int deleteFinanceInvoiceApByIds(Long[] ids) {
        return financeInvoiceApManager.deleteFinanceInvoiceApByIds(ids);
    }

    /**
     * 删除Finance Invoice Ap信息
     *
     * @param id Finance Invoice Ap主键
     * @return 结果
     */
    @Override
    public int deleteFinanceInvoiceApById(Long id) {
        return financeInvoiceApManager.deleteFinanceInvoiceApById(id);
    }
}
