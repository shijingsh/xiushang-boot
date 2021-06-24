package amy.marketing.job.utils;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZKUI created by 2021-04-15 下午2:24
 */
@Slf4j
public class DESCoderUtil {
  private static final String DES = "DES";

  /**
   * Description 根据键值进行加密
   *
   * @param data 需加密字段
   * @param key  盐
   * @return Base64编码的加密字符串
   * @throws Exception
   */
  public static String encryptToBase64(String data, String key) {
    byte[] bt = new byte[0];
    try {
      bt = encrypt(data.getBytes(), key.getBytes());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return Base64Encoder.encode(bt);
  }

  /**
   * Description 根据键值进行加密
   *
   * @param data
   * @param key  加密键byte数组
   * @return
   * @throws Exception
   */
  private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    Key k = getKey(key);
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.ENCRYPT_MODE, k);
    return cipher.doFinal(data);
  }

  /**
   * 获取可用的KEY
   *
   * @param arrBTmp 盐的字节组
   * @return
   */
  private static Key getKey(byte[] arrBTmp) {
    byte[] arrB = new byte[8];
    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
      arrB[i] = arrBTmp[i];
    }
    Key key = new SecretKeySpec(arrB, "DES");
    return key;
  }

  /**
   * 密钥生成
   *
   * @param args
   * @throws Exception
   */
  private static String baseUrl = "http://mc-test.zhiziyun.com/api-zcloud/zcloud";
  public static void main(String[] args) throws Exception {
    String token = DESCoderUtil.encryptToBase64("13" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec22");
    System.out.println(token);
    JSONObject param = JSONUtil.createObj().set("agentId","13").set("userId","9575").set("token",token);
    System.out.println(JSONUtil.toJsonPrettyStr(param));
    getInterestcrowdList(param);
    //getAdvertisersList(param);
    //getAdvertisersStatus(param);
    //getIndustry(param);
  }

  public static void getInterestcrowdList(JSONObject param) {
    String url = baseUrl + "/api/account/dsp/detail";
    param.set("page", 1);
    param.set("rows", 10);
    param.set("siteId", "TR2rO0FzYzv");
    //param.set("tagType", "visitorTag");
    param.set("startDate", "2020-01-01");
    param.set("endDate", "2021-05-25");
//    param.set("networkId", "14");
    HttpRequest post = HttpUtil.createPost(url);
    post.body(JSONUtil.toJsonStr(param), ContentType.JSON.getValue());
    HttpResponse response = post.execute();
    if (HttpStatus.HTTP_OK == response.getStatus()){
      String responseBody = response.body();
      System.out.println(JSONUtil.toJsonPrettyStr(responseBody));
    }
  }

  public static void getAdvertisersList(JSONObject param) {
    String url = baseUrl + "/api/advertiser/list";
    param.set("page", 1);
    param.set("rows", 10);
    HttpRequest post = HttpUtil.createPost(url);
    post.body(JSONUtil.toJsonStr(param), ContentType.JSON.getValue());
    HttpResponse response = post.execute();
    if (HttpStatus.HTTP_OK == response.getStatus()){
      String responseBody = response.body();
      System.out.println(JSONUtil.toJsonPrettyStr(responseBody));
    }
  }

  public static void getAdvertisersStatus(JSONObject param) {
    String url = baseUrl + "/api/advertiser/status";
    String siteId = "TR2rO0FzYzv";
    param.set("siteId", siteId);
    HttpRequest post = HttpUtil.createPost(url);
    post.body(JSONUtil.toJsonStr(param), ContentType.JSON.getValue());
    HttpResponse response = post.execute();
    if (HttpStatus.HTTP_OK == response.getStatus()){
      String responseBody = response.body();
      System.out.println(JSONUtil.toJsonPrettyStr(responseBody));
    }
  }

  public static void getAdvertisersState(JSONObject param) {
    String url = baseUrl + "/api/dictionary/networkAdvertiserCategories";
    String siteJson = "";
  }



  //获取行业
  public static void getIndustry(JSONObject param) {
    String url = baseUrl + "/api/dictionary/networkAdvertiserCategories";
    String networkJson = "[{\"networkId\":\"14\",\"networkName\":\"baidu\",\"supportCreativeType\":[\"STATIC\",\"VIDEO\",\"MOBILE\",\"MOBILENATIVE\",\"LINKUNIT\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":true,\"supportPmpDeals\":true,\"supportAdSlotType\":true},{\"networkId\":\"2\",\"networkName\":\"秒针-灵集\",\"supportCreativeType\":[\"STATIC\",\"DYNAMIC\",\"VIDEO\",\"NATIVE\",\"MOBILE\",\"MOBILEDYNAMIC\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":true,\"supportAdSlotType\":true},{\"networkId\":\"24\",\"networkName\":\"智投优选媒体\",\"supportCreativeType\":[\"STATIC\",\"DYNAMIC\",\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"28\",\"networkName\":\"Adview快友\",\"supportCreativeType\":[\"MOBILE\",\"MOBILEDYNAMIC\",\"MOBILENATIVE\",\"MOBILEVIDEO\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":true},{\"networkId\":\"30\",\"networkName\":\"乐视\",\"supportCreativeType\":[\"STATIC\",\"VIDEO\",\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":true,\"supportAdSlotType\":false},{\"networkId\":\"31\",\"networkName\":\"广点通-调整中\",\"supportCreativeType\":[\"NATIVE\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"35\",\"networkName\":\"搜狐汇聚\",\"supportCreativeType\":[\"STATIC\",\"VIDEO\",\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":false,\"supportPmpDeals\":true,\"supportAdSlotType\":false},{\"networkId\":\"39\",\"networkName\":\"掌游\",\"supportCreativeType\":[\"MOBILE\",\"MOBILEDYNAMIC\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"44\",\"networkName\":\"爱奇艺\",\"supportCreativeType\":[\"STATIC\",\"VIDEO\",\"NATIVE\",\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":true,\"supportPmpDeals\":true,\"supportAdSlotType\":false},{\"networkId\":\"46\",\"networkName\":\"科大讯飞\",\"supportCreativeType\":[\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"5\",\"networkName\":\"google\",\"supportCreativeType\":[\"STATIC\",\"DYNAMIC\",\"MOBILE\",\"MOBILEDYNAMIC\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"9\",\"networkName\":\"优酷\",\"supportCreativeType\":[\"STATIC\",\"VIDEO\",\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":false,\"supportPmpDeals\":true,\"supportAdSlotType\":false},{\"networkId\":\"50\",\"networkName\":\"聚告\",\"supportCreativeType\":[\"STATIC\",\"MOBILE\",\"MOBILENATIVE\",\"MOBILEVIDEO\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":true,\"supportPmpDeals\":false,\"supportAdSlotType\":true},{\"networkId\":\"56\",\"networkName\":\"佳投\",\"supportCreativeType\":[\"STATIC\",\"DYNAMIC\",\"VIDEO\",\"MOBILE\",\"MOBILEDYNAMIC\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":true,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"61\",\"networkName\":\"企创\",\"supportCreativeType\":[\"STATIC\",\"DYNAMIC\",\"MOBILE\",\"MOBILEDYNAMIC\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":true,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"64\",\"networkName\":\"Smaato\",\"supportCreativeType\":[\"MOBILE\",\"MOBILEDYNAMIC\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":true,\"supportPmpDeals\":true,\"supportAdSlotType\":false},{\"networkId\":\"77\",\"networkName\":\"百度视频\",\"supportCreativeType\":[\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"78\",\"networkName\":\"网易有道\",\"supportCreativeType\":[\"NATIVE\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"79\",\"networkName\":\"咪咕\",\"supportCreativeType\":[\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":true,\"supportAdSlotType\":false},{\"networkId\":\"81\",\"networkName\":\"多盟-微博\",\"supportCreativeType\":[\"MOBILENATIVE\"],\"needAdvertiserVerification\":true,\"hasQualificationUploadApi\":true,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"84\",\"networkName\":\"猎豹\",\"supportCreativeType\":[\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false},{\"networkId\":\"94\",\"networkName\":\"百寻\",\"supportCreativeType\":[\"MOBILE\",\"MOBILEVIDEO\",\"MOBILENATIVE\"],\"needAdvertiserVerification\":false,\"hasQualificationUploadApi\":false,\"hasMediaType\":false,\"supportPmpDeals\":false,\"supportAdSlotType\":false}]";
    List<JSONObject> jsonList = JSONUtil.toList(networkJson, JSONObject.class);

    List<JSONObject> resultList = new ArrayList<>();

    HttpRequest post = HttpUtil.createPost(url);
    jsonList.forEach(json -> {
      String networkName = StrUtil.toString(json.get("networkName"));
      JSONObject resultObj = JSONUtil.createObj().set("平台", networkName);
      param.set("networkId", json.get("networkId"));
      post.body(JSONUtil.toJsonStr(param), ContentType.JSON.getValue());
      HttpResponse response = post.execute();
      if (HttpStatus.HTTP_OK == response.getStatus()){
        String responseBody = response.body();
        if (StrUtil.isNotBlank(responseBody)) {
          JSONObject responseJsonObj = JSONUtil.toBean(responseBody, JSONObject.class);
          resultObj.set("行业", responseJsonObj.get("data"));
        }
      }
      resultList.add(resultObj);
      System.out.println(StrUtil.format("获取[{}]的行业", networkName));
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    System.out.println(JSONUtil.toJsonPrettyStr(resultList));
  }
}
