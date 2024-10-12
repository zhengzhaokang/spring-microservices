package com.microservices.otmp.finance.manager.impl;

import java.util.List;

import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.finance.mapper.FinanceInvoiceApMapper;
import com.microservices.otmp.finance.domain.entity.FinanceInvoiceApDO;
import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import com.microservices.otmp.finance.manager.IFinanceInvoiceApManager;

/**
 * Finance Invoice ApManager业务层处理
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Service
public class FinanceInvoiceApManagerImpl implements IFinanceInvoiceApManager {
    private static final Logger log = LoggerFactory.getLogger(FinanceInvoiceApManagerImpl.class);

    @Autowired
    private FinanceInvoiceApMapper financeInvoiceApMapper;

    /**
     * 查询Finance Invoice Ap
     *
     * @param id Finance Invoice Ap主键
     * @return Finance Invoice ApDO
     */
    @Override
    public FinanceInvoiceApDO selectFinanceInvoiceApById(Long id) {
        return financeInvoiceApMapper.selectFinanceInvoiceApById(id);
    }

    /**
     * 查询Finance Invoice Ap列表
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return Finance Invoice ApDO
     */
    @Override
    public List<FinanceInvoiceApDO> selectFinanceInvoiceApList(FinanceInvoiceApDTO financeInvoiceAp) {
        return financeInvoiceApMapper.selectFinanceInvoiceApList(financeInvoiceAp);
    }

    /**
     * 新增Finance Invoice Ap
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return 结果
     */
    @Override
    public int insertFinanceInvoiceAp(FinanceInvoiceApDO financeInvoiceAp) {
        financeInvoiceAp.setCreateTime(DateUtils.getNowDate());
        return financeInvoiceApMapper.insertFinanceInvoiceAp(financeInvoiceAp);
    }

    /**
     * 修改Finance Invoice Ap
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return 结果
     */
    @Override
    public int updateFinanceInvoiceAp(FinanceInvoiceApDO financeInvoiceAp) {
        financeInvoiceAp.setUpdateTime(DateUtils.getNowDate());
        return financeInvoiceApMapper.updateFinanceInvoiceAp(financeInvoiceAp);
    }

    /**
     * 批量删除Finance Invoice Ap
     *
     * @param ids 需要删除的Finance Invoice Ap主键
     * @return 结果
     */
    @Override
    public int deleteFinanceInvoiceApByIds(Long[] ids) {
        return financeInvoiceApMapper.deleteFinanceInvoiceApByIds(ids);
    }

    /**
     * 删除Finance Invoice Ap信息
     *
     * @param id Finance Invoice Ap主键
     * @return 结果
     */
    @Override
    public int deleteFinanceInvoiceApById(Long id) {
        return financeInvoiceApMapper.deleteFinanceInvoiceApById(id);
    }
}
