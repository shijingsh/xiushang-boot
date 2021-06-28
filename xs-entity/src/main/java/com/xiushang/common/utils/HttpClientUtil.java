package com.xiushang.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class HttpClientUtil
{

  public static String sendGetRequest(String reqURL)
  {
    String respContent = "通信失败";
    HttpClient httpClient = new DefaultHttpClient();

    httpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(10000));
    httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(20000));
    HttpGet httpGet = new HttpGet(reqURL);
    try {
      HttpResponse response = httpClient.execute(httpGet);
      if (response.getStatusLine().getStatusCode() != 200) {
        httpGet.abort();
      }
      HttpEntity entity = response.getEntity();
      if (null != entity)
      {
        Charset respCharset = ContentType.getOrDefault(entity).getCharset();
        respContent = EntityUtils.toString(entity, respCharset);

        EntityUtils.consume(entity);
      }
      System.out.println("-------------------------------------------------------------------------------------------");
      StringBuilder respHeaderDatas = new StringBuilder();
      for (Header header : response.getAllHeaders()) {
        respHeaderDatas.append(header.toString()).append("\r\n");
      }
      String respStatusLine = response.getStatusLine().toString();
      String respHeaderMsg = respHeaderDatas.toString().trim();
      String respBodyMsg = respContent;
      System.out.println(new StringBuilder().append("HTTP应答完整报文=[").append(respStatusLine).append("\r\n").append(respHeaderMsg).append("\r\n\r\n").append(respBodyMsg).append("]").toString());
      System.out.println("-------------------------------------------------------------------------------------------");
      System.out.println("url："+reqURL);
    }
    catch (ConnectTimeoutException cte) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时连接超时,堆栈轨迹如下").toString(), cte);
    } catch (SocketTimeoutException ste) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时读取超时,堆栈轨迹如下").toString(), ste);
    }
    catch (ClientProtocolException cpe) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时协议异常,堆栈轨迹如下").toString(), cpe);
    } catch (ParseException pe) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时解析异常,堆栈轨迹如下").toString(), pe);
    }
    catch (IOException ioe) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时网络异常,堆栈轨迹如下").toString(), ioe);
    } catch (Exception e) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时偶遇异常,堆栈轨迹如下").toString(), e);
    }
    finally {
      httpClient.getConnectionManager().shutdown();
    }
    return respContent;
  }

  public static String sendPostRequest(String reqURL, Map<String, String> reqData, String contentType, String encodeCharset)
  {
    String reseContent = "通信失败";
    HttpClient httpClient = new DefaultHttpClient();
    httpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(10000));
    httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(20000));
    HttpPost httpPost = new HttpPost(reqURL);

    if (!contentType.isEmpty())
      httpPost.setHeader("Content-Type", new StringBuilder().append(contentType).append("; charset=").append(encodeCharset).toString());
    else
      httpPost.setHeader("Content-Type", new StringBuilder().append("application/x-www-form-urlencoded; charset=").append(encodeCharset).toString());
    try
    {
      UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(assembleRequestParams(reqData), encodeCharset);
      httpPost.setEntity(uefEntity);
      HttpResponse response = httpClient.execute(httpPost);
      if (response.getStatusLine().getStatusCode() != 200) {
        httpPost.abort();
      }
      System.out.println("-------------------------------------------------------------------------------------------");
      System.out.println("url："+reqURL);
      HttpEntity entity = response.getEntity();
      if (null != entity) {
        reseContent = EntityUtils.toString(entity, encodeCharset);
        EntityUtils.consume(entity);
      }
    } catch (ConnectTimeoutException cte) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时连接超时,堆栈轨迹如下").toString(), cte);
    } catch (SocketTimeoutException ste) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时读取超时,堆栈轨迹如下").toString(), ste);
    } catch (Exception e) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时偶遇异常,堆栈轨迹如下").toString(), e);
    } finally {
      httpClient.getConnectionManager().shutdown();
    }
    return reseContent;
  }

  public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset)
    throws Exception
  {
    String responseContent = "通信失败";
    HttpClient httpClient = new DefaultHttpClient();
    httpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(10000));
    httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(20000));

    X509TrustManager trustManager = new X509TrustManager() {
      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      }
      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      }
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }
    };
    X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
      public void verify(String host, SSLSocket ssl) throws IOException {
      }
      public void verify(String host, X509Certificate cert) throws SSLException {
      }
      public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
      }
      public boolean verify(String arg0, SSLSession arg1) {
        return true;
      }
    };
    try {
      System.out.println("-------------------------------------------------------------------------------------------");
      System.out.println("url："+reqURL);
      SSLContext sslContext = SSLContext.getInstance("TLS");

      sslContext.init(null, new TrustManager[] { trustManager }, null);

      SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext, hostnameVerifier);

      httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

      HttpPost httpPost = new HttpPost(reqURL);

      if (null != params) {
        List formParams = new ArrayList();
        for (Entry entry : params.entrySet()) {
          formParams.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset));
      }
      HttpResponse response = httpClient.execute(httpPost);
      if (response.getStatusLine().getStatusCode() != 200) {
        httpPost.abort();
      }
      HttpEntity entity = response.getEntity();
      if (null != entity) {
        responseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
        EntityUtils.consume(entity);
      }
    }
    catch (ConnectTimeoutException cte) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时连接超时,堆栈轨迹如下").toString(), cte);
    }
    catch (SocketTimeoutException ste) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时读取超时,堆栈轨迹如下").toString(), ste);
    }
    catch (Exception e) {
      log.error(new StringBuilder().append("请求通信[").append(reqURL).append("]时偶遇异常,堆栈轨迹如下").toString(), e);
    } finally {
      httpClient.getConnectionManager().shutdown();
    }
    return responseContent;
  }

  private String setRefund() throws Exception {
    Map params = new HashMap();
    params.put("inputCharset", "1");
    params.put("version", "v2.0");
    params.put("signType", "4");
    params.put("orderId", "123456789");
    params.put("pid", "987654321");
    params.put("seqId", "55555555");
    params.put("returnAllAmount", "100");
    params.put("returnTime", "20150803211512");
    params.put("ext1", "");
    params.put("ext2", "");
    params.put("returnDetail", "aaaaaaaaa");
    params.put("signMsg", "");
    String url = params.toString();
    return url;
  }

  private static synchronized List<NameValuePair> assembleRequestParams(Map<String, String> data) {
    List nameValueList = new ArrayList();

    Iterator it = data.entrySet().iterator();
    while (it.hasNext()) {
      Entry entry = (Entry)it.next();
      nameValueList.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
    }

    return nameValueList;
  }

  public static void main(String[] args) {
    Map params = new HashMap();
    params.put("inputCharset", "1");
    params.put("version", "v2.0");
    params.put("signType", "4");
    params.put("orderId", "123456789");
    params.put("pid", "987654321");
    params.put("seqId", "55555555");
    params.put("returnAllAmount", "100");
    params.put("returnTime", "20150803211512");
    params.put("ext1", "");
    params.put("ext2", "");
    params.put("returnDetail", "aaaaaaaaa");
    params.put("signMsg", "TW68SFCetpukg/MElTba+M6cz5hKGAayc+/x+fyPzt/owPRTYkoNtcwVBt+ofjHNpxNScLOqSMbS\nqT9a+s1z9PJBjabb+Xu0aYpoWZ/N/70yQkIKGpLW3+OjWvkx1TMEs28ZVst5kKCvqrAdHjvHt9P4\nQ0kuCiAbu+oen0m3kjM=");
    try {
      System.out.println(sendPostSSLRequest("https://sandbox.99bill.com/msgateway/recvMerchantRefundAction.htm", params, "UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
