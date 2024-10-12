package com.microservices.otmp.erp.service;

import com.microservices.otmp.erp.domain.S4ApiItem;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;
import com.microservices.otmp.erp.domain.request.SupplierFinancingActivationRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ISupplierDataQueryService {

    List<SupplierInvoiceInfo> getSupplierData(Map<String, Object> map);

    Object supplierDataSync(HashMap<String, Object> map);

    Object s4ApiCallBack(List<S4ApiItem> s4ApiItems);

    List<S4ApiItem> searchS4ApiItem();

    void saveSupplierData(List<SupplierInvoiceInfo> suppliers);

    void saveFinalSupplierData(List<SupplierInvoiceInfo> suppliers);

    Object supplierFinancingActivation(List<SupplierFinancingActivationRequest> request);

    void updateFeedStatus(List<S4ApiItem> updateFeeds);
}
