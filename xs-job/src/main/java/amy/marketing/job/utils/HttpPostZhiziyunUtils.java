package amy.marketing.job.utils;

import amy.marketing.common.api.ZhiziyunResult;
import amy.marketing.job.config.AgentConfig;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liefqin
 * @date 2019/10/24
 */
@Slf4j
public class HttpPostZhiziyunUtils {
 

  public static ZhiziyunResult<?> postzhiziyun(
          String url, String json) {
    try {
    	long startselectMktList = System.currentTimeMillis();
    	
    	//String resultStr  = "{\"status\":true,\"data\":{\"promotionLinkId\":\"108CyZ0CQg48\"}}";//HttpUtil.post(AgentConfig.baseUrl+url, json);;
    	String resultStr  = HttpUtil.post(AgentConfig.baseUrl+url, json);;
    	
    	long endselectMktList = System.currentTimeMillis();
    	
    	ZhiziyunResult<?> result = JSONUtil.toBean(resultStr, ZhiziyunResult.class);
    	result.setReTime(endselectMktList - startselectMktList);
      
    	return result;
    } catch (Exception e) {
    	return null;
    }
	
  }
  
  public static <T> T post(
          String url, String json, Class<T> responseClass) {
    try {
      String resultStr  = HttpUtil.post(AgentConfig.baseUrl+url, json);;
      
      T result = JSONUtil.toBean(resultStr, responseClass);
      return result;
    } catch (Exception e) {
    	return null;
    }
	
  }
  

}
