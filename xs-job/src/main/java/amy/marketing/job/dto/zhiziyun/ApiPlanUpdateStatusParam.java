package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 计划删除
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPlanUpdateStatusParam extends AgentDTO {

  /**计划状态,可用值:ONPROGRESS,PAUSED,FINISHED
   * ONPROGRESS:投放中、PAUSED：暂停；默认：PAUSED 
   * UNOPEND：未投放、ONPROGRESS:投放中、PAUSED：暂停、FINISHED：已结束
   * */
  private String deliveryStatus;
  
  /**计划编号 */
  private String planId;
  
}
