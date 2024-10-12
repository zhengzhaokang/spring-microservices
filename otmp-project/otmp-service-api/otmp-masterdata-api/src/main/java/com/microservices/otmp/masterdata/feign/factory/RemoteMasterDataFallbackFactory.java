package com.microservices.otmp.masterdata.feign.factory;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.masterdata.domain.RemoteBizBaseExchangeRate;
import com.microservices.otmp.masterdata.domain.dto.*;
import com.microservices.otmp.masterdata.feign.RemoteMasterDataService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class RemoteMasterDataFallbackFactory implements FallbackFactory<RemoteMasterDataService> {

    @Override
    public RemoteMasterDataService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteMasterDataService() {

            @Override
            public RemoteResponse<RemoteBizBaseExchangeRate> exchangeRate(String transactionCurrency, String targetCurrency) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseOrgOfficeDTO> getOrgAndOffice(String businessGroup, String geoCode, String regionMarketCode, String countryCode) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseOrgOfficeDTO> getCompanyCode(BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseExchangeRateDTO> getExchangeRate(Date rateDate, String fromCurrency, String toCurrency) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseCurrencyDTO> getByCode(String currencyCode) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseCurrencyDTO>> getListByCodeList(List<String> currencyCodeList) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseCurrencyDTO>> getList(BizBaseCurrencyDTO bizBaseCurrency) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseRegionMarketDTO> getListByRegionMarket(String geoCode, String businessGroup) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseSalesOrgDTO> getListBySalesOrg(String geoCode, String businessGroup, String regionMarketCode, String tempField) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseSalesOrgDTO> getOneSalesOrg(String geoCode, String businessGroup, String salesOrgCode) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseSalesOfficeDTO> getListBySalesOffice(String businessGroup, String regionMarketCode, String salesOrgCode) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseSegmentDTO> getListBySegment(String geoCode, String businessGroup) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseVendorDTO>> getVendor(String vendorCode, String bankAccount, String customerId) {
                throw new OtmpException((throwable.getMessage()));
            }

            @Override
            public RemoteResponse<BizBaseVendorBankDTO> getListByVendor(String vendorCode, String customerId) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseCustomerDTO> getListByCustomer(BizBaseCustomerDTO bizBaseCustomerDTO) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseDcDivisionMappingDTO> getListByDistributionChannel(String salesOrgCode, String salesOfficeCode, String dcCode) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public BizBaseCustomerDTO getCustomer(BizBaseCustomerDTO bizBaseCustomer) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseProfitCenterDTO>> selectProfitCenterList(BizBaseProfitCenterDTO bizBaseProfitCenterDTO) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseSegmentDTO> getSegment(String businessGroup, String segmentCode, String level) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseRegionMarketDTO> getRegionMarket(String geoCode, String businessGroup, String regionMarketCode) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseGtnCategoryDTO> getGtnCategoryByType(String gtnCategoryL1) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

//            @Override
//            public RemoteResponse<BizBaseGtnTypeDTO> remoteGetGtnType(String gtnType, String tempField) {
//                throw new OtmpException(throwable.getLocalizedMessage());
//            }

            @Override
            public String getCustomerNameByNumber(String customerNumber) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBaseApcDTO> bizBaseApcList(BizBaseApcDTO bizBaseApcDTO) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public String getEndCustomerName(String endCustomerId) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseExchangeRateDTO>> getExchangeRateList(List<ExchangeRateDTO> exchangeRateDTOList) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseMbgCustomerDrmTomsTofiDTO> getByCustomerNumber(String customerNumber, String businessGroup) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseOrgOfficeDTO> getByOrgOffice(String salesOrgCode, String salesOfficeCode) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public RemoteResponse<BizBasemicroservicesEntityDTO> bizBasemicroservicesEntityList(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<BizBaseOrgOfficeDTO> getOrgOfficeByOrgAndBg(String salesOrgCode, String businessGroup) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseSelfInvoiceDTO>> getSelfInvoice(String sellerCountry, String gtnCategoryL1, String crmId, String status) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseVendorAndPerferBankDTO>> getVendorAndPerferBank(List<String> customerIds) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<BizBaseParty3rdVendorDTO>> getParty3rdVendor(BizBaseParty3rdVendorDTO bizBaseParty3rdVendorDTO) {
                throw new OtmpException(throwable.getLocalizedMessage());
            }
        };
    }
}
