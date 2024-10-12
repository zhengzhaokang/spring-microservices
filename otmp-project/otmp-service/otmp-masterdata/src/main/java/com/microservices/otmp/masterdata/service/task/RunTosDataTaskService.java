package com.microservices.otmp.masterdata.service.task;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseTos;
import com.microservices.otmp.masterdata.domain.dto.TsIIntegrationStatusSdmsDTO;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.service.IBizBaseTosService;
import com.microservices.otmp.masterdata.service.ITsIIntegrationStatusSdmsService;
import com.microservices.otmp.masterdata.service.ITsITosBpcService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RunTosDataTaskService {

    private static final Logger log = LoggerFactory.getLogger(RunTosDataTaskService.class);
    @Autowired
    private ITsIIntegrationStatusSdmsService iTsIIntegrationStatusSdmsService;
    @Autowired
    private ITsITosBpcService tsITosBpcService;
    @Autowired
    private IBizBaseTosService bizBaseTosService;

    public void runTos(BizBaseTos bizBaseTos) {
        Date date ;
        if(bizBaseTos.getRunTime()==null){
              date = DateUtils.getNowDate();
        }else{
            date=bizBaseTos.getRunTime();
        }

        BizBaseTos bizBaseTo = new BizBaseTos();
        bizBaseTo.setStatus("Y");
        List<BizBaseTos> tos = bizBaseTosService.selectBizBaseTosList(bizBaseTo);
        Set<String> memberIdSet = tos.stream().map(BizBaseTos::getMemberId).collect(Collectors.toSet());
        Map<String, BizBaseTos>memberIdToMap = tos.stream().collect(Collectors.toMap(BizBaseTos::getMemberId, Function.identity(), (e1, e2) -> e1));

        TsIIntegrationStatusSdmsDTO tsIntegrationStatusSdmsDto = new TsIIntegrationStatusSdmsDTO();
        tsIntegrationStatusSdmsDto.setStatus(2);
        tsIntegrationStatusSdmsDto.setTargetTable("ts_i_TOS_BPC");

        tsIntegrationStatusSdmsDto.setVersionDate(date);
        log.info("tsIntegrationStatusSdmsDto.getVersionDate() = {}", tsIntegrationStatusSdmsDto.getVersionDate());
        List<TsIIntegrationStatusSdmsDTO> tsIntegrationStatusSdmsDTOS = iTsIIntegrationStatusSdmsService.selectTsIIntegrationStatusSdmsList(tsIntegrationStatusSdmsDto);
        if (CollectionUtils.isNotEmpty(tsIntegrationStatusSdmsDTOS)) {
            TsITosBpcDTO tsTos = new TsITosBpcDTO();
            tsTos.setCreateTime(date);
            tsTos.setParentH1("PRODVAR");
            List<TsITosBpcDTO> dtos = tsITosBpcService.selectTsITosBpcList(tsTos);
            if (CollectionUtils.isNotEmpty(dtos)) {

                List<TsITosBpcDTO> updateData = dtos.stream().filter(t -> memberIdSet.contains(t.getMemberId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(updateData)) {
                    Date finalDate = date;
                    updateData.forEach(update->{
                            BizBaseTos baseTos = memberIdToMap.get(update.getMemberId());
                            baseTos.setEvDescription(update.getEvDescription());
                            baseTos.setUpdateBy("sys");
                            baseTos.setUpdateTime(finalDate);
                            bizBaseTosService.updateBizBaseTos(bizBaseTos);
                        });
                }
                List<TsITosBpcDTO> addData = dtos.stream().filter(t -> !memberIdSet.contains(t.getMemberId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(addData)) {
                    addData.forEach(add -> {
                        BizBaseTos bizBaseTosDb = new BizBaseTos();
                        BeanUtils.copyProperties(add, bizBaseTosDb);
                        bizBaseTosDb.setStatus("Y");
                        bizBaseTosDb.setCreateBy("sys");
                        bizBaseTosDb.setUpdateBy("sys");
                        bizBaseTosDb.setCreateTime(DateUtils.getNowDate());
                        bizBaseTosDb.setUpdateTime(DateUtils.getNowDate());
                        bizBaseTosService.insertBizBaseTos(bizBaseTosDb);
                    });
                }
            }
        }

    }
}
