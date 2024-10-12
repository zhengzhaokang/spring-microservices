package com.microservices.otmp.analysis.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.analysis.domain.dto.FinanceBatchInvoiceApDTO;
import com.microservices.otmp.analysis.domain.entity.FinanceBatchDO;
import com.microservices.otmp.analysis.domain.entity.FinanceBatchInvoiceApDO;
import com.microservices.otmp.analysis.manager.IFinanceBatchManager;
import com.microservices.otmp.analysis.service.IFinanceBatchService;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.MapUtil;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Finance Batch InfoService业务层处理
 *
 * @author lovefamily
 * @date 2023-11-10
 */
@Service
public class FinanceBatchServiceImpl implements IFinanceBatchService {
    private static final Logger log = LoggerFactory.getLogger(FinanceBatchServiceImpl.class);

    @Autowired
    private IFinanceBatchManager financeBatchManager;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 查询Finance Batch Info
     *
     * @param id Finance Batch Info主键
     * @return Finance Batch InfoDTO
     */
    @Override
    public FinanceBatchDTO selectFinanceBatchById(Long id) {
        FinanceBatchDO financeBatchDO = financeBatchManager.selectFinanceBatchById(id);
        FinanceBatchDTO financeBatchDTO = new FinanceBatchDTO();
        BeanUtils.copyProperties(financeBatchDO, financeBatchDTO);
        return financeBatchDTO;
    }

    /**
     * 查询Finance Batch Info列表
     *
     * @param financeBatch Finance Batch Info
     * @return Finance Batch InfoDTO
     */
    @Override
    public List<FinanceBatchDTO> selectFinanceBatchList(FinanceBatchDTO financeBatch) {

        List<FinanceBatchDO> financeBatchDOS =
                financeBatchManager.selectFinanceBatchList(financeBatch);
        List<FinanceBatchDTO> financeBatchList = new ArrayList<>(financeBatchDOS.size());
        BeanUtils.copyListProperties(financeBatchDOS, financeBatchList, FinanceBatchDTO.class);
        buildNameData(financeBatchList);
        return financeBatchList;

    }
    @Override
    public PageInfo<FinanceBatchDTO> selectFinanceBatchPageList(FinanceBatchDTO financeBatch) {
        List<FinanceBatchDO> financeBatchDOS =
                financeBatchManager.selectFinanceBatchList(financeBatch);
        PageInfo<FinanceBatchDO> pageInfo = new PageInfo<>(financeBatchDOS);

        List<FinanceBatchDTO> financeBatchList = new ArrayList<>(financeBatchDOS.size());
        BeanUtils.copyListProperties(financeBatchDOS, financeBatchList, FinanceBatchDTO.class);
        buildNameData(financeBatchList);
        PageInfo<FinanceBatchDTO> resultPageInfo = new PageInfo<>(financeBatchList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;

    }


    private void buildNameData(List<FinanceBatchDTO> financeBatchList) {
        Map<String, Object> bankCache = new HashMap<>();
        Map<String, Object> supplierCache = new HashMap<>();
        Map<String, Object> entityCache = new HashMap<>();
        bankCache = redissonClient.getMap(RedisConstants.INFO_ALL_BANK);
        supplierCache = redissonClient.getMap(RedisConstants.INFO_ALL_SUPPLIER);
        entityCache = redissonClient.getMap(RedisConstants.INFO_ALL_ENTITY);

        Map<String, Object> finalBankCache = bankCache;
        Map<String, Object> finalSupplierCache = supplierCache;
        Map<String, Object> finalEntityCache = entityCache;
        financeBatchList.forEach(item -> {
            if (!MapUtil.isEmpty(finalBankCache)) {
                JSONObject bankObj = (JSONObject) finalBankCache.get(item.getBankId());
                if (!Objects.isNull(bankObj)) {
                    item.setBankName(bankObj.getString("bankName"));
                }
            }
            if (!MapUtil.isEmpty(finalSupplierCache)) {
                JSONObject supplierObj = (JSONObject) finalSupplierCache.get(item.getSupplierId());
                if (!Objects.isNull(supplierObj)) {
                    item.setSupplierName(supplierObj.getString("supplierName"));
                }
            }
            if (!MapUtil.isEmpty(finalEntityCache)) {
                JSONObject entityObj = (JSONObject) finalEntityCache.get(item.getEntityId());
                if (!Objects.isNull(entityObj)) {
                    item.setEntityName(entityObj.getString("entityName"));
                }
            }

            String status = item.getStatus();
            if (InvoiceStatusEnum.FINANCING.code.equals(status)) {
                item.setStatus(BatchStatusEnum.SUBMITTED.code);
            }
            if (InvoiceStatusEnum.FINANCED.code.equals(status)) {
                item.setStatus(BatchStatusEnum.ACCEPTED.code);
            }
        });
    }

    /**
     * 查询Finance BatchInvoiceAp Info列表
     *
     * @param financeBatchInvoiceApDTO Finance Batch Info
     * @return Finance Batch InvoiceApDTO集合
     */
    @Override
    public List<FinanceBatchInvoiceApDTO> findFinanceBatchInvoiceApList(FinanceBatchInvoiceApDTO financeBatchInvoiceApDTO) {

        List<FinanceBatchInvoiceApDO> financeBatchInvoiceApDOS =
                financeBatchManager.findFinanceBatchInvoiceApList(financeBatchInvoiceApDTO);
        List<FinanceBatchInvoiceApDTO> financeBatchInvoiceApDTOList = new ArrayList<>(financeBatchInvoiceApDOS.size());
        BeanUtils.copyListProperties(financeBatchInvoiceApDOS, financeBatchInvoiceApDTOList, FinanceBatchInvoiceApDTO.class);
        return financeBatchInvoiceApDTOList;
    }

    /**
     * 新增Finance Batch Info
     *
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    @Override
    public int insertFinanceBatch(FinanceBatchDTO financeBatch) {
        financeBatch.setCreateTime(DateUtils.getNowDate());
        FinanceBatchDO financeBatchDO = new FinanceBatchDO();
        BeanUtils.copyProperties(financeBatch, financeBatchDO);
        return financeBatchManager.insertFinanceBatch(financeBatchDO);
    }

    /**
     * 修改Finance Batch Info
     *
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    @Override
    public int updateFinanceBatch(FinanceBatchDTO financeBatch) {
        financeBatch.setUpdateTime(DateUtils.getNowDate());
        FinanceBatchDO financeBatchDO = new FinanceBatchDO();
        BeanUtils.copyProperties(financeBatch, financeBatchDO);
        return financeBatchManager.updateFinanceBatch(financeBatchDO);
    }

    /**
     * 批量删除Finance Batch Info
     *
     * @param ids 需要删除的Finance Batch Info主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchByIds(Long[] ids) {
        return financeBatchManager.deleteFinanceBatchByIds(ids);
    }

    /**
     * 删除Finance Batch Info信息
     *
     * @param id Finance Batch Info主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchById(Long id) {
        return financeBatchManager.deleteFinanceBatchById(id);
    }
}
