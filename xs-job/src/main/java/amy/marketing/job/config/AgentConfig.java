package amy.marketing.job.config;

import amy.marketing.job.utils.DESCoderUtil;
import lombok.Data;

/**
 * 考虑改成动态赋值
 * @author ZKUI created by 2021-06-01 2:38 下午
 */
@Data
public class AgentConfig {
	private static String agentId = "13";
  private static String userId = "9575";
  private static String key = "510be9ce-c796-4d2d-a8b6-9ca8a426ec22";
  public static String baseUrl = "http://mc-test.zhiziyun.com/api-zcloud/zcloud";

    public static String getUrl(String path){
        return baseUrl + path;
    }

  public static String token() {
    return DESCoderUtil.encryptToBase64(agentId + System.currentTimeMillis(), key);
  }

  public static void main(String args[]){
	  System.out.println("\"agentId\":13,");
	  System.out.println("\"userId\":9575,");
    System.out.println("\"token\":\""+AgentConfig.token()+"\",");
  }
}
