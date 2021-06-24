package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 计划删除
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPlanUpdateCreativeStausParam extends AgentDTO {

  /**素材编号 */
  private String creativeId;
  
  /**计划投放状态,可用值:ONPROGRESS,PAUSED,FINISHED
   * PAUSED：暂停、ONPROGRESS：开启；默认：未投放 */
  private String deliveryStatus;
  
  /**计划编号 */
  private String planId;
  
}
