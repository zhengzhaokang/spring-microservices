package com.microservices.otmp.notice.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.notice.domain.FinancingRate;
import com.microservices.otmp.notice.mapper.EmailAttachMapper;
import com.microservices.otmp.notice.service.EmailAttachmentService;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.LogicalOperator;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.EmailMessageSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.Attachment;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.*;

@Service
public class EmailAttachmentServiceImpl implements EmailAttachmentService {

    private static final Logger log    = LoggerFactory.getLogger(EmailAttachmentServiceImpl.class);

    @Autowired
    private EmailAttachMapper emailAttachMapper;

    // QQ邮箱服务器地址和端口号
    @Value("${email.host}")
    private String host;

    // QQ邮箱授权码
    @Value("${email.authCode}")
    private String authCode;

    @Value("${email.username}")
    private String username;

    @Value("${email.file.path}")
    private String filePath;

    @Value("${email.file.start.label}")
    private String startLabel;

    @Value("${email.file.end.label}")
    private String endLabel;

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.password}")
    private String password;

    private final static String CUR_RATE_TYPE = "SOFR";

    private final static String DEFUALT_USER = "system";

    private final static String TMP_FILENAME = "tmp.csv";

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void analysisEmailAttachByExchange(Date curDate) {
        try {
            Date endDate = DateUtils.addDays(curDate, 1);
            List<EmailMessage> messages = readMailByEWS(username, password, host, curDate, endDate, emailFrom);

            String fileUrl = getTempFileUrl();
            for (EmailMessage message : messages) {
                log.info("###EmailAttachmentServiceImpl analysisEmailAttachByExchange Subject is {} ", message.getSubject());
                if (getAttachmentsFromEmail(message, fileUrl)) {
                    // 只处理有附件的，最近的，csv附件的一封邮件
                    break;
                }
            }

            // 读取临时文件，并入库，后删除
            writeAttachToDb(fileUrl);

        } catch (Exception e) {
            log.error("###EmailAttachmentServiceImpl analysisEmailAttachByExchange is ", e);
        }
    }

    private String getTempFileUrl() {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return filePath + TMP_FILENAME;
    }

    public List<EmailMessage> readMailByEWS(String username, String password, String emailServer,
                                 Date beginDate, Date endDate, String from){
        List<EmailMessage> messages = Lists.newArrayList();
        try {
            ExchangeService exService = getExchangeService(username, password, emailServer);
            //绑定邮箱
            microsoft.exchange.webservices.data.core.service.folder.Folder inbox = microsoft.exchange.webservices.data.core.
                    service.folder.Folder.bind(exService, WellKnownFolderName.Inbox);
            ItemView view = new ItemView(inbox.getTotalCount());
            view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
            view.setPropertySet(new PropertySet(ItemSchema.DateTimeReceived));
            //创建过滤器
            SearchFilter searchFilter = new SearchFilter.SearchFilterCollection(
                    LogicalOperator.And,
                    new SearchFilter.IsGreaterThanOrEqualTo(ItemSchema.DateTimeReceived, beginDate),
                    new SearchFilter.IsLessThanOrEqualTo(ItemSchema.DateTimeReceived, endDate)
            );
            FindItemsResults<Item> findResults = exService.findItems(inbox.getId(), searchFilter, view);
            if (findResults != null && findResults.getTotalCount() > 0) {
                exService.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
                for (Item item : findResults.getItems()) {
                    EmailMessage msg = EmailMessage.bind(exService, item.getId());
                    EmailAddress msgFrom = msg.getFrom();
                    if (msgFrom != null && StringUtils.equals(msgFrom.getAddress(), from)) {
                        messages.add(msg);
                    } else if (StringUtils.isBlank(from)) {
                        messages.add(msg);
                    }
                }
            }
        } catch (Exception e) {
            log.error("###EmailAttachmentServiceImpl readMailByEWS is ", e);
        }
        return messages;
    }

    public  ExchangeService getExchangeService(String username, String password, String emailServer){
        log.info("Begin to connect to server " + username);
        ExchangeService exService = null;
        try {
            exService = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
            //参数是⽤户名,密码,域
            ExchangeCredentials credentials = new WebCredentials(username, password);
            //exService.setTraceEnabled(true);
            exService.setCredentials(credentials);
            //给出Exchange Server的URL http://xxxxxxx
            exService.setUrl(new URI(emailServer));
        } catch (URISyntaxException e) {
            log.error("###EmailAttachmentServiceImpl getExchangeService is ", e);
        }
        return exService;
    }

    /**
     * 获取附件
     * @param mail
     * @throws Exception
     */
    public Boolean getAttachmentsFromEmail(EmailMessage mail, String path) throws Exception {
        List<Attachment> attachs = mail.getAttachments().getItems();
        try {
            //判断是否有附件
            if (mail.getHasAttachments()) {
                //迭代获取附件
                for (Attachment attach : attachs) {
                    if (attach instanceof FileAttachment && StringUtils.contains(attach.getName(), ".csv")) {
                        //接收邮件到临时目录
                        log.info("###EmailAttachmentServiceImpl Item attachment name is {} ", attach.getName());
                        ((FileAttachment) attach).load(path);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.error("###EmailAttachmentServiceImpl GetAttachmentsFromEmail Exception is", e);
        }
        return false;
    }

    @Override
    public void analysisEmailAttachInfo(Date curDate) {
        Store store = null;
        Folder folder = null;
        String url = null;
        try {
            // 获取Session对象
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getInstance(props, null);
            store = session.getStore("imaps");
            // 连接到邮箱服务器
            store.connect(host, username, authCode);
            // 打开收件箱文件夹
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            //获取邮件信息，限定某一天，某一个人的邮件
            Message[] messages = getMessages(folder, curDate);
            log.info("###EmailAttachmentServiceImpl 你一共有" + messages.length + "封邮件");
            if (messages.length < 1) {
                return;
            }
            List<Message> messageList = Arrays.asList(messages);
            //根据发件人当天的最后一个有附件的邮件，解析附件
            Collections.reverse(messageList);
            outer:
            for (Message message : messageList) {
                log.info("###EmailAttachmentServiceImpl 邮件主题" + message.getSubject());
                log.info("###EmailAttachmentServiceImpl 收件日期" + message.getReceivedDate());
                // 判断是否有附件
                if (!message.isMimeType("multipart/*")) {
                    continue;
                }
                Multipart multipart = (Multipart) message.getContent();
                    // 遍历所有附件
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    String fileName = MimeUtility.decodeText(bodyPart.getFileName());
                    // 判断是否为附件，判断文件格式是不是csv,加Log,输出文件名
                    log.info("###EmailAttachmentServiceImpl downloadAttach mimeBodyPart name is {} ", fileName); //test1.csv
                    boolean match = Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && StringUtils.contains(fileName, ".csv");
                    if (!match) {
                        continue;
                    }
                        //下载临时文件csv
                    downloadAttach((MimeBodyPart) bodyPart);
                    // 读取临时文件，后删除
                    writeAttachToDb(filePath);
                    //只处理一个文件
                    break outer;
                }
            }
        } catch (MessagingException e) {
            log.error("###EmailAttachmentServiceImpl analysisEmailAttachInfo MessagingException is ", e);
        } catch (ParseException e) {
            log.error("###EmailAttachmentServiceImpl analysisEmailAttachInfo ParseException is ", e);
        } catch (IOException e) {
            log.error("###EmailAttachmentServiceImpl analysisEmailAttachInfo IOException is ", e);
        } finally {
            if (folder != null) {
                try {
                    folder.close(false);
                } catch (MessagingException e) {
                    log.error("###EmailAttachmentServiceImpl analysisEmailAttachInfo MessagingException is ", e);
                }
            }
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    log.error("###EmailAttachmentServiceImpl analysisEmailAttachInfo MessagingException1 is ", e);
                }
            }
        }
    }

    private Message[] getMessages(Folder folder, Date curDate) throws ParseException, MessagingException {
        Date endDate = DateUtils.addDays(curDate, 1);
        String startDateStr = DateUtils.dateTime(curDate);
        String endDateStr = DateUtils.dateTime(endDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(startDateStr);
        Date date2 = simpleDateFormat.parse(endDateStr);
        SearchTerm comparisonTermGe = new ReceivedDateTerm(DateTerm.GE, date);
        SearchTerm comparisonTermLt = new ReceivedDateTerm(DateTerm.LT, date2);
        SearchTerm sender = new FromTerm(new InternetAddress(emailFrom));
        SearchTerm comparisonAndTerm = new AndTerm(new AndTerm(comparisonTermGe, comparisonTermLt), sender);
        return folder.search(comparisonAndTerm);
    }

    private void downloadAttach(MimeBodyPart mimeBodyPart) throws IOException, MessagingException {
        // 下载附件  创建输入流和输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = mimeBodyPart.getInputStream();
            outputStream = Files.newOutputStream(Paths.get(filePath));
            // 读取数据并写入文件中
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("###EmailAttachmentServiceImpl downloadAttach IOException is ", e);
        } catch (MessagingException e) {
            log.error("###EmailAttachmentServiceImpl downloadAttach MessagingException is ", e);
        } finally {
            // 关闭输入输出流
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private void writeAttachToDb(String url) {
        File file = new File(url);
        if (!file.exists()) {
            log.warn("###EmailAttachmentServiceImpl writeAttachToDb not file to handle");
            return;
        }
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
            lines = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            log.error("###EmailAttachmentServiceImpl writeAttachToDb IOException is ", e);
        }
        log.info("###writeAttachToDb lines is {}", JSON.toJSONString(lines));

        try {
            boolean flag = false;
            List<String> needLines = new ArrayList<>();
            for (String line : lines) {
                if (StringUtils.contains(line, endLabel)) {
                    break;
                }
                if (flag) {
                    needLines.add(line);
                }
                if (StringUtils.contains(line, startLabel)) {
                    flag = true;
                }
            }
            handleNeedLines(needLines);
        } catch (Exception e) {
            log.error("###EmailAttachmentServiceImpl handleNeedLines Exception is ", e);
        }
        // 删除文件
        file.delete();
    }

    private void handleNeedLines(List<String> needLines) {
        if (CollectionUtils.isEmpty(needLines)) {
            log.warn("###handleNeedLines needLines isEmpty");
            return;
        }
        List<FinancingRate> rates = Lists.newArrayList();
        for (String needLine : needLines) {
            String[] split = needLine.split(",");
            if (split == null || split.length != 5) {
                continue;
            }
            FinancingRate rate = new FinancingRate();
            rate.setRateType(CUR_RATE_TYPE); //静态变量CUR_RATE_TYPE
            String ratePeriod = split[0].replaceAll("\"", "");
            rate.setRatePeriod(ratePeriod);
            rate.setRate(new BigDecimal(split[3].trim()));
            rate.setCreateBy(DEFUALT_USER); //静态变量，枚举
            rate.setUpdateBy(DEFUALT_USER);
            rate.setDeleteFlag(false);
            // 处理rateDate
            String rateDateStr = split[4];
            rateDateStr = rateDateStr.replaceAll("\"", "");
            rate.setRateDate(DateUtils.dateTime("yyyy/MM/dd", rateDateStr));
            //获取雪花ID
            long snowFlakeId = SnowFlakeUtil.nextId();
            rate.setId(snowFlakeId);
            rates.add(rate);
        }
        if (CollectionUtils.isEmpty(rates)) {
            log.warn("###handleNeedLines rates isEmpty");
            return;
        }
        log.info("###EmailAttachmentServiceImpl handleNeedLines rates is {}", JSON.toJSONString(rates, SerializerFeature.IgnoreErrorGetter));
        RLock lock = redissonClient.getLock("sofr::rate:insert");
        try {
            boolean tryLock = lock.tryLock(5, 30, TimeUnit.SECONDS);
            if (!tryLock) {
                log.warn("###EmailAttachmentServiceImpl handleNeedLines tryLock failed");
                return;
            }
            for (FinancingRate rate : rates) {
                FinancingRate selectRate = emailAttachMapper.selectRate(rate);
                if (selectRate == null) {
                    emailAttachMapper.insert(rate);
                } else if (selectRate.getRate().compareTo(rate.getRate()) == 0) {
                    //汇率不变则不更新
                    log.info("###EmailAttachmentServiceImpl handleNeedLines rate not change");
                } else {
                    emailAttachMapper.updateRate(rate);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
