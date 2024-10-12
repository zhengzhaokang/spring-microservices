package com.microservices.otmp.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.entity.LimitDashboardDo;
import com.microservices.otmp.analysis.domain.entity.LimitStatisticDo;
import com.microservices.otmp.analysis.domain.param.LimitDashboardParam;
import com.microservices.otmp.analysis.domain.vo.LimitDashboardTableVo;
import com.microservices.otmp.analysis.domain.vo.LimitDashboardVo;
import com.microservices.otmp.analysis.mapper.LimitStatisticMapper;
import com.microservices.otmp.analysis.service.LimitDashBoardService;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LimitDashBoardServiceImpl extends ServiceImpl<LimitStatisticMapper, LimitStatisticDo> implements LimitDashBoardService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private LimitStatisticMapper limitStatisticMapper;
    private static final String UNDER_LINE = "_";

    @Override
    public List<LimitDashboardVo> limitDashBoardValues(String bankId, Long entityId, String startTime, String endTime, boolean useMilli) {
        log.info("limitDashBoardValues,bankId:{},entityId:{},startTime:{},endTime:{}", bankId, entityId, startTime, endTime);
        Date start;
        Date end;
        try {
            start = DateUtils.dateParse(startTime, DateUtils.DATE_PATTERN);
            end = DateUtils.dateParse(endTime, DateUtils.DATE_PATTERN);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            end = calendar.getTime();
        } catch (ParseException e) {
            log.info("limitDashBoardValues,parse time error,startTime:{},endTime:{}", startTime, endTime);
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR));
        }

        List<LimitDashboardDo> bankLimitValues = limitStatisticMapper.selectByTimeRange(LimitStatisticDo.TYPE_BANK_LIMIT, bankId, entityId, start, end, null, null);
        List<LimitDashboardDo> outstandingValues = limitStatisticMapper.selectByTimeRange(LimitStatisticDo.TYPE_OUTSTANDING, bankId, entityId, start, end, null, null);
        List<LimitDashboardVo> result = getLimitDashboardVos(bankLimitValues, outstandingValues, useMilli);
        log.info("limitDashBoardValues,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private List<LimitDashboardVo> getLimitDashboardVos(List<LimitDashboardDo> bankLimitValues, List<LimitDashboardDo> outstandingValues, boolean userMilli) {
        Map<String, BigDecimal> outstandingValueMap = new HashMap<>();
        log.info("getLimitDashboardVos,bankLimitValues:{},outstandingValues:{}", JsonUtil.toJSON(bankLimitValues), JsonUtil.toJSON(outstandingValues));
        if (CollectionUtils.isNotEmpty(outstandingValues)) {
            outstandingValueMap = outstandingValues.parallelStream().collect(Collectors.toMap(LimitDashboardDo::getDate, t -> new BigDecimal(t.getAmount() == null ? "0" : String.valueOf(t.getAmount()))));
        }
        List<LimitDashboardVo> result = new ArrayList<>();
        for (LimitDashboardDo bankLimitValue : bankLimitValues) {
            LimitDashboardVo item = new LimitDashboardVo();
            item.setDate(bankLimitValue.getDate());
            BigDecimal limitValue = new BigDecimal(bankLimitValue.getAmount() == null ? "0.00" : String.valueOf(bankLimitValue.getAmount()));
            BigDecimal outstanding = outstandingValueMap.get(item.getDate());
            outstanding = outstanding == null ? new BigDecimal("0.00") : outstanding;
            if (userMilli) {
                limitValue = limitValue.divide(new BigDecimal(1000000),2, RoundingMode.HALF_UP);
                outstanding = outstanding.divide(new BigDecimal(1000000),2, RoundingMode.HALF_UP);
            }
            item.setLimitAmount(limitValue.toPlainString());
            item.setOutstandingAmount(outstanding.toPlainString());
            result.add(item);
        }
        // 空值补数
        if(CollectionUtils.isEmpty(result)){
            LimitDashboardVo fakeData = new LimitDashboardVo();
            fakeData.setLimitAmount("0.00");
            fakeData.setOutstandingAmount("0.00");
            Date date = DateUtils.getNowDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            String dateStr = DateUtils.dateFormat(calendar.getTime(), DateUtils.DATE_PATTERN);
            fakeData.setDate(dateStr);
            result.add(fakeData);
        }
        return result;
    }

    private String createKey(String bankId, Long entityId, String date) {
        return bankId + UNDER_LINE + entityId + UNDER_LINE + date;
    }

    @Override
    public PageInfo<LimitDashboardTableVo> limitDashBoardTable(LimitDashboardParam param) {
        log.info("limitDashBoardChart,param:{}", JsonUtil.toJSON(param));
        Date start;
        Date end;
        try {
            start = DateUtils.dateParse(param.getStartTime(), DateUtils.DATE_PATTERN);
            end = DateUtils.dateParse(param.getEndTime(), DateUtils.DATE_PATTERN);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            end = calendar.getTime();
        } catch (ParseException e) {
            log.info("limitDashBoardValues,parse time error,startTime:{},endTime:{}", param.getStartTime(), param.getEndTime());
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR));
        }
        int pageNum = (param.getPageNum() == null || param.getPageNum() <= 0) ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = (param.getPageSize() == null || param.getPageSize() <= 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        int startNum = (pageNum - 1) * pageSize;

        log.info("limitDashBoardTable,pageNum:{},pageSize:{},startNum:{}", pageNum, pageSize, startNum);
        String bankId = param.getBankId();
        Long entityId = param.getEntityId();
        String startStr = DateUtils.dateFormat(start,DateUtils.DATE_PATTERN);
        String endStr = DateUtils.dateFormat(end,DateUtils.DATE_PATTERN);
        PageInfo<LimitDashboardTableVo> result = new PageInfo<>();
        List<LimitStatisticDo> limitValues = limitStatisticMapper.selectAmount(LimitStatisticDo.TYPE_BANK_LIMIT, bankId, entityId, startStr, endStr, pageSize, startNum);
        if (CollectionUtils.isEmpty(limitValues)) {
            log.info("limitDashBoardTable,limitValues is empty");
            result.setTotal(0);
            result.setList(new ArrayList<>());
            return result;
        }
        RMap<Object, Object> bankMap = redissonClient.getMap(RedisConstants.INFO_ALL_BANK);
        log.info("limitDashBoardTable,bankMap:{}", JsonUtil.toJSON(bankMap));
        RMap<Object, Object> entityMap = redissonClient.getMap(RedisConstants.INFO_ALL_ENTITY);
        log.info("limitDashBoardTable,entityMap:{}", JsonUtil.toJSON(entityMap));

        List<LimitStatisticDo> outstandingList = limitStatisticMapper.selectAmount(LimitStatisticDo.TYPE_OUTSTANDING, bankId, entityId, startStr, endStr, null, null);
        Map<String, LimitStatisticDo> outstandingMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(outstandingList)) {
            outstandingMap = outstandingList.parallelStream().collect(Collectors.toMap(t -> createKey(t.getBankId(), t.getEntityId(), t.getStatisticDate()), t -> t));
        }
        log.info("limitDashBoardTable,outstandingMap:{}", JsonUtil.toJSON(outstandingMap));
        List<LimitDashboardTableVo> resultList = new ArrayList<>(limitValues.size());
        for (LimitStatisticDo limitValue : limitValues) {
            String key = createKey(limitValue.getBankId(), limitValue.getEntityId(), limitValue.getStatisticDate());
            LimitDashboardTableVo item = new LimitDashboardTableVo();
            setNames(limitValue, bankMap, entityMap, item);
            item.setDate(limitValue.getStatisticDate());
            item.setLimit(limitValue.getAmount() == null ? "0" : limitValue.getAmount());
            LimitStatisticDo limitStatisticDo = outstandingMap.get(key);
            item.setOutstanding(limitStatisticDo == null ? "0" : limitStatisticDo.getAmount());
            resultList.add(item);
        }
        Integer total = limitStatisticMapper.selectAmountCount(LimitStatisticDo.TYPE_BANK_LIMIT, bankId, entityId, startStr, endStr);
        result.setTotal(total);
        result.setList(resultList);
        log.info("limitDashBoardTable,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private static void setNames(LimitStatisticDo limitValue, RMap<Object, Object> bankMap, RMap<Object, Object> entityMap, LimitDashboardTableVo item) {
        Object bankObj = bankMap.get(limitValue.getBankId());
        Map<?, ?> bankObjMap = JsonUtil.toMap(JsonUtil.toJSON(bankObj));
        String bankName = null;
        if (bankObjMap != null) {
            Object bankNameObj = bankObjMap.get("bankName");
            bankName = bankNameObj == null ? null : String.valueOf(bankNameObj);
        }
        Object entityObj = entityMap.get(limitValue.getEntityId());
        Map<?, ?> entityObjMap = JsonUtil.toMap(JsonUtil.toJSON(entityObj));
        String entityName = null;
        if (entityObjMap != null) {
            Object entityNameObj = entityObjMap.get("entityName");
            entityName = entityNameObj == null ? null : String.valueOf(entityNameObj);
        }
        item.setBankName(bankName);
        item.setEntityName(entityName);
    }

    private boolean compareLast(LimitDashboardVo last, LimitDashboardVo item) {
        log.info("compareLast,last:{},item:{}", JsonUtil.toJSON(last), JsonUtil.toJSON(item));
        return last.getLimitAmount() != null
                && last.getOutstandingAmount() != null
                && item.getLimitAmount() != null
                && item.getOutstandingAmount() != null
                && (!item.getLimitAmount().equals(last.getLimitAmount())
                || !item.getOutstandingAmount().equals(last.getOutstandingAmount()));
    }

}
