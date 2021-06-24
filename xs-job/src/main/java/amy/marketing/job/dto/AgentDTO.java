package amy.marketing.job.dto;

import lombok.Data;

/**
 * 考虑改成动态赋值
 * 所有请求智子云接口的数据都要继承该类
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class AgentDTO {
  private String agentId = "13";
  private String userId = "9575";
  private String token = "";
}
