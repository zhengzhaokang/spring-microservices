package com.microservices.otmp.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.erp.service.ICallWso2Service;
import org.apache.commons.net.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

@Service
public class CallWso2ServiceImpl implements ICallWso2Service {

    private static Logger logger = LoggerFactory.getLogger(CallWso2ServiceImpl.class);

    @Value("${otfp.access.key}")
    String taxAccessKey;
    @Value("${otfp.access.maxTotal}")
    private int maxTotal;
    @Value("${otfp.access.defaultMaxPerRoute}")
    private int defaultMaxPerRoute;
    @Value("${otfp.token.url}")
    String tokenUrl;


    @Override
    public String getToken() throws Exception {
        String tooken;
        HttpClient client = HttpClients.custom().setConnectionManager(cancelSSL()).build();
        String url = tokenUrl;//生产地址
        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;
        String accessKey=taxAccessKey;
        Base64 base64 = new Base64();
        String accessKey64 = base64.encodeToString(accessKey.getBytes("UTF-8"));
        try {
            StringEntity s = new StringEntity("grant_type=client_credentials");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/x-www-form-urlencoded");
            post.setEntity(s);
            post.addHeader("test", "text/xml");
            post.addHeader("Authorization", "Basic "+accessKey64);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());
                jsonObject = JSONObject.parseObject(result);
            }
            tooken=jsonObject.getString("access_token");
        }catch (Exception e) {
            throw new Exception("Cannot connect to LGTP system");
        }
        return tooken;
    }

    /**
     * 取消SSL验证
     * @return
     */
    private PoolingHttpClientConnectionManager cancelSSL(){
        PoolingHttpClientConnectionManager manager = null;
        try {
            X509TrustManager xtm = new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            //获取TLS安全协议上下文
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{xtm}, null);
            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", scsf).build();
            manager = new PoolingHttpClientConnectionManager(sfr);
            // 设置最大连接数
            manager.setMaxTotal(maxTotal);
            // 设置每个主机地址的并发数
            manager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        } catch (Exception e) {
            logger.error("get instance of PoolingHttpClientConnectionManager unsuccessfully : " + e.getLocalizedMessage());
        }
        return manager;
    }

    @Override
    public JSONObject callWso2Post(String jaString, String requestUrl) {
        logger.info("call LGTP for callLgtpWhtByCustomer detail: {} ", jaString);
        HttpClient client = HttpClients.custom().setConnectionManager(cancelSSL()).build();
        HttpPost post = new HttpPost(requestUrl);
        JSONObject jsonObject = null;
        try {
            StringEntity s = new StringEntity(jaString);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());
                logger.info("callLgtpWhtByCustomer result of call LGTP result: {} ", result);
                try {
                    jsonObject = JSONObject.parseObject(result);
                } catch (Exception e) {
                    logger.info("callLgtpWhtByCustomer call LGTP for tax detail: {} ", e);
                    throw new Exception("callLgtpWhtByCustomer Cannot get correct Tax infor from LGTP system");
                }
            }else {
                throw new Exception("callLgtpWhtByCustomer Cannot connect to LGTP system");
            }
        } catch (Exception e) {
            logger.error("callLgtpWhtByCustomer Cannot connect to LGTP system",e);
        }
        return jsonObject;
    }
}
