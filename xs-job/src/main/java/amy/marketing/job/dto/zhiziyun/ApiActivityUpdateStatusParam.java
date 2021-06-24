package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 活动删除
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiActivityUpdateStatusParam extends AgentDTO {

  /**活动编号集合 */
  private String activityId;
  
  
  /** 活动投放状态,PAUSED：暂停、ONPROGRESS：开启；默认：未投放*/
  private String deliveryStatus;
  
}
