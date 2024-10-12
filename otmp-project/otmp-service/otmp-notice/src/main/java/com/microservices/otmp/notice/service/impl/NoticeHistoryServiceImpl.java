package com.microservices.otmp.notice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.notice.common.NoticeConstant;
import com.microservices.otmp.notice.domain.entity.NoticeDimension;
import com.microservices.otmp.notice.domain.entity.NoticeParamEntity;
import com.microservices.otmp.notice.domain.entity.NoticeSendKafka;
import com.microservices.otmp.notice.dto.NoticeDto;
import com.microservices.otmp.notice.dto.NoticeHistoryDto;
import com.microservices.otmp.notice.dto.NoticeHistoryManageDto;
import com.microservices.otmp.notice.dto.NoticeHistoryUserDto;
import com.microservices.otmp.notice.manager.NoticeHistoryManager;
import com.microservices.otmp.notice.mapper.NoticeHistoryMapper;
import com.microservices.otmp.notice.mapper.NoticeHistoryUserMapper;
import com.microservices.otmp.notice.mapper.NoticeMapper;
import com.microservices.otmp.notice.param.NoticeHistoryParam;
import com.microservices.otmp.notice.service.NoticeHistoryService;
import com.microservices.otmp.notice.vo.NoticeHistoryData;
import com.microservices.otmp.notice.vo.NoticeHistoryVO;
import com.microservices.otmp.notice.vo.NoticeSendVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import tk.mybatis.mapper.entity.Example;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.Comparator;
import java.util.stream.Collectors;

import static com.microservices.otmp.notice.common.ConstantEmail.*;

@Service
@Slf4j
public class NoticeHistoryServiceImpl implements NoticeHistoryService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeHistoryMapper noticeHistoryMapper;

    @Autowired
    private NoticeHistoryManager noticeHistoryManager;

    @Autowired
    private NoticeHistoryUserMapper noticeHistoryUserMapper;

    public static final String MINUTE_PATTERN = "MM-dd HH:mm";

    @Override
    public Integer insertNoticeHistory(NoticeSendVO noticeSendVO) {
        //1.校验是否重复通知
        Example example = new Example(NoticeHistoryDto.class);
        example.createCriteria().andEqualTo("traceId", noticeSendVO.getTraceId());
        int count = noticeHistoryMapper.selectCountByExample(example);
        if (count > 0) {
            log.warn("###NoticeHistoryServiceImpl insertNoticeHistory traceID is repeat count is {} > 0 ", count);
            return 0;
        }
        // 2.组装数据
        Map<String, List<String>> sendInfo = new HashMap<>();
        sendInfo.put(NoticeConstant.SEND_TO_LIST, noticeSendVO.getSendTo() );
        sendInfo.put(NoticeConstant.SEND_CC, noticeSendVO.getSendCc() );

        Map<String,String> noticeInfo = new HashMap<>();
        noticeInfo.put(NoticeConstant.CREATE_BY, noticeSendVO.getCreateBy());
        noticeInfo.put(NoticeConstant.TRACE_ID, noticeSendVO.getTraceId());
        noticeInfo.put(NoticeConstant.REMARK, noticeSendVO.getRemark());

        Map<String,String> noticeDimension = new HashMap<>();
        noticeDimension.put(NoticeConstant.BUSINESS_TYPE,noticeSendVO.getNoticeType());

        JSONObject normalParam = JSON.parseObject(noticeSendVO.getBean());
        JSONObject titleBean = JSON.parseObject(noticeSendVO.getTitleBean());

        return sendPackageNoticeInfo(noticeDimension,normalParam,titleBean,noticeInfo,sendInfo);
    }

    @Override
    public NoticeHistoryData selectNoticeHistory(NoticeHistoryParam param) {
        setPageParamInfo(param);
        NoticeHistoryData noticeHistoryData = new NoticeHistoryData();

        Integer count = noticeHistoryMapper.selectNoticeHistoryCountByUserId(param);
        if (count == null || count < 1) {
            noticeHistoryData.setCount(0);
            noticeHistoryData.setData(Lists.newArrayList());
            return noticeHistoryData;
        }

        List<NoticeHistoryDto> noticeHistoryDtos = noticeHistoryMapper.selectNoticeHistoryListByUserId(param);
        if (CollectionUtils.isEmpty(noticeHistoryDtos)) {
            noticeHistoryData.setCount(0);
            noticeHistoryData.setData(Lists.newArrayList());
            return noticeHistoryData;
        }

        List<NoticeHistoryVO> noticeHistoryVOS = Lists.newArrayList();
        BeanUtils.copyListProperties(noticeHistoryDtos, noticeHistoryVOS, NoticeHistoryVO.class);
        handleResultInfo(noticeHistoryVOS, MINUTE_PATTERN);
        noticeHistoryData.setData(noticeHistoryVOS);
        noticeHistoryData.setCount(count);

        return noticeHistoryData;
    }

    @Override
    public NoticeHistoryData selectManageNoticeHistory(NoticeHistoryParam param) {
        setPageParamInfo(param);
        NoticeHistoryData noticeHistoryData = new NoticeHistoryData();
        Integer count = noticeHistoryMapper.selectManageNoticeHistoryCount(param);
        if (count == null || count < 1) {
            noticeHistoryData.setCount(0);
            noticeHistoryData.setData(Lists.newArrayList());
            return noticeHistoryData;
        }
        List<NoticeHistoryManageDto> noticeHistoryDtos = noticeHistoryMapper.selectManageNoticeHistoryList(param);
        if (CollectionUtils.isEmpty(noticeHistoryDtos)) {
            noticeHistoryData.setCount(0);
            noticeHistoryData.setData(Lists.newArrayList());
            return noticeHistoryData;
        }

        List<NoticeHistoryVO> noticeHistoryVOS = Lists.newArrayList();
        BeanUtils.copyListProperties(noticeHistoryDtos, noticeHistoryVOS, NoticeHistoryVO.class);
        handleResultInfo(noticeHistoryVOS, DateUtils.DATE_TIME_PATTERN);
        noticeHistoryData.setData(noticeHistoryVOS);
        noticeHistoryData.setCount(count);
        return noticeHistoryData;
    }

    @Override
    public void updateNoticeHistoryStatus(NoticeHistoryParam param) {
        Example example = new Example(NoticeHistoryUserDto.class);
        example.createCriteria().andEqualTo("noticeHistoryId",param.getNoticeHistoryId()).andEqualTo("userId",param.getUserId());
        NoticeHistoryUserDto record = new NoticeHistoryUserDto();
        record.setStatus(NoticeConstant.STATUS_DISABLE);
        noticeHistoryUserMapper.updateByExampleSelective(record, example);
    }

    @Override
    public void batchUpdateNoticeHistoryStatus(NoticeHistoryParam param) {
        Example example = new Example(NoticeHistoryUserDto.class);
        example.createCriteria().andEqualTo("userId",param.getUserId());
        NoticeHistoryUserDto record = new NoticeHistoryUserDto();
        record.setStatus(NoticeConstant.STATUS_DISABLE);
        noticeHistoryUserMapper.updateByExampleSelective(record, example);
    }

    private void handleResultInfo(List<NoticeHistoryVO> noticeHistoryVOS, String format) {
        try {
            for (NoticeHistoryVO noticeHistoryVO : noticeHistoryVOS) {
                if (noticeHistoryVO.getCreateTime() != null) {
                    noticeHistoryVO.setCreateTimeF(DateUtils.dateFormat(noticeHistoryVO.getCreateTime(), format));
                }
                if (noticeHistoryVO.getUpdateTime() != null) {
                    noticeHistoryVO.setUpdateTimeF(DateUtils.dateFormat(noticeHistoryVO.getUpdateTime(), format));
                }
            }
        } catch (Exception e) {
            log.error("### NoticeHistoryServiceImpl handleResultInfo exception is ", e);
        }
    }

    private void setPageParamInfo(NoticeHistoryParam param) {
        param.setNoticePageNum((param.getNoticePageNum() == null || param.getNoticePageNum() < 1) ? Constants.DEFAULT_PAGE_NUM : param.getNoticePageNum());
        param.setNoticePageSize((param.getNoticePageSize() == null || param.getNoticePageSize() < 1) ? Constants.DEFAULT_PAGE_SIZE : param.getNoticePageSize());
        param.setOffset((param.getNoticePageNum() - 1) * param.getNoticePageSize());
        param.setLimit(param.getNoticePageSize());
    }

    private Integer sendPackageNoticeInfo(Map<String, String> noticeDimension, JSONObject normalParams, JSONObject titleBean,
                                          Map<String, String> noticeInfo, Map<String, List<String>> sendInfo) {
        NoticeSendKafka noticeSendKafka = new NoticeSendKafka();
        try {
            CommonUtils.isNull(noticeInfo, "emailInfo is empty");
            CommonUtils.isNull(noticeInfo.get(NoticeConstant.TRACE_ID), "traceId is empty");
            CommonUtils.isNull(noticeInfo.get(NoticeConstant.CREATE_BY), "createBy is empty");
            CommonUtils.isNull(sendInfo.get(NoticeConstant.SEND_TO_LIST), "sendToList is empty");

            NoticeDimension ed=  this.generateEmailDimension(noticeDimension);

            NoticeParamEntity noticeParamEntity = generateNoticeInfoByBean(ed,  normalParams, titleBean);

            CommonUtils.isNull(noticeParamEntity, "The generated emailParamEntity is empty");

            noticeSendKafka.setNoticeDimension(noticeDimension);
            noticeSendKafka.setNoticeInfo(noticeInfo);
            noticeSendKafka.setNoticeParamEntity(noticeParamEntity);
            noticeSendKafka.setSendInfo(sendInfo);
            noticeSendKafka.setNormalParams(normalParams);
            noticeSendKafka.setTitleBean(titleBean);

            //将需要处理的数据，入库
            noticeHistoryManager.noticeHistoryToDb(noticeSendKafka);

        } catch (Exception e) {
            log.error("notice校验不通过" + e.getMessage(), e);
        }
        return 1;
    }

    private NoticeParamEntity generateNoticeInfoByBean(NoticeDimension ed, JSONObject params, JSONObject titleBean) throws DocumentException, SAXException {
        Example example = new Example(NoticeDto.class);
        example.createCriteria().andEqualTo("noticeType", ed.getBusinessType());
        example.createCriteria().andEqualTo("status", ed.getStatus());
        List<NoticeDto> noticeDtos = noticeMapper.selectByExample(example);
        CommonUtils.collectIsEmpty(noticeDtos, "notice template is empty");

        String template = noticeDtos.get(0).getNoticeContent();
        String title = noticeDtos.get(0).getNoticeTitle();
        NoticeParamEntity message = new NoticeParamEntity();

        String flagStart = "#{{";
        String flagEnd = "}}";
        String flagStartFortitle = "#{{";
        String flagEndFortitle = "}}";
        List<String> parms =   getParams(template,flagStart,flagEnd);
        List<String> titleparms =   getParams(title,flagStartFortitle,flagEndFortitle);

        template = replaceNormalParams(params, template, flagStart, flagEnd, parms);
        title = repalceTitleParms(params, titleBean, title, flagStartFortitle, flagEndFortitle, titleparms);

        //加头尾
        template = handleHtmlDoctype(template);


        SAXReader reader = new SAXReader(false);
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);


        Reader stringReader = new StringReader(template);
        Document doc = reader.read(stringReader);

        //匹配出表格里的参数 都是 >approver</td> 这种的, 用正则匹配出来 之后 对于 没传参数的 也就是无值的 就进行替换成></td>
        List<String> noValue = new ArrayList<>();

        noValue= noValue.stream().distinct().collect(Collectors.toList());
        for (String nv:noValue) {
            template = template.replace(nv,"></td>");
        }

        //最后过滤一遍干扰字符
        template= template.replace("]><html", "<html");

        message.setContent(template);
        message.setSubject(title);

        return message;

    }

    private static String handleHtmlDoctype(String template) {
        if(!template.contains(HTML_DOCTYPE)){
            template = HTML_BEGIN + template +HTML_END;
        }
        return template;
    }

    private static String repalceTitleParms(JSONObject params, JSONObject mailTitleBean, String mailTitle, String flagStartFortitle, String flagEndFortitle, List<String> titleparms) {
        if(null!= mailTitleBean){
            for (String par : titleparms){
                assert params != null;
                if(null!= mailTitleBean.getString(par)){
                    mailTitle = mailTitle.replace(flagStartFortitle +par+ flagEndFortitle, "null".equals(mailTitleBean.getString(par))?"-----": mailTitleBean.getString(par));
                }else{
                    throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TITLE_PARAMS_NO_MATCH, par), DefaultErrorMessage.EMAIL_TITLE_PARAMS_NO_MATCH.intValue());
                }

            }
        }
        return mailTitle;
    }

    private static String replaceNormalParams(JSONObject params, String template, String flagStart, String flagEnd, List<String> parms) {
        if(null!= params){
            for (String par : parms){
                if(null!= params.getString(par)){
                    template = template.replace(flagStart +par+ flagEnd, "null".equals(params.getString(par))?"-----": params.getString(par));
                }else{
                    throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TEMPLATE_PARAMS_NO_MATCH, par), DefaultErrorMessage.EMAIL_TEMPLATE_PARAMS_NO_MATCH.intValue());
                }

            }
        }
        return template;
    }

    private static List<String> getParams(String template, String flagStart,String flagEnd ){
        List<String> re = new ArrayList<>();
        int count = getCount(template,flagStart);
        for (int i = 0; i < count; i++) {
            String quStr=template.substring(template.indexOf(flagStart)+3,template.indexOf(flagEnd));  //progr a
            re.add(quStr);
            template=template.replaceFirst("\\#\\{\\{"+quStr+"\\}\\}","");
        }
        return re;
    }

    public static int getCount(String str, String key) {
        if (str == null || key == null || "".equals(str.trim()) || "".equals(key.trim())) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }

    private NoticeDimension generateEmailDimension(Map<String, String> noticeDimension) {
        CommonUtils.isNull(noticeDimension.get(NoticeConstant.BUSINESS_TYPE), "notice type is empty");
        return NoticeDimension.builder()
                .businessType(noticeDimension.get(NoticeConstant.BUSINESS_TYPE))
                .status(NoticeConstant.STATUS_ENABLE)
                .build();
    }
}
