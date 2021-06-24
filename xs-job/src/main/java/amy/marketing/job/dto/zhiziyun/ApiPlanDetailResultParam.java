package amy.marketing.job.dto.zhiziyun;

import lombok.Data;

/**
 * 广告活动添加
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPlanDetailResultParam extends ApiPlanAddParam {
	
	/**计划编号*/
  private String planId;
  
  /**计划状态,可用值:ONPROGRESS,PAUSED,FINISHED
   * ONPROGRESS:投放中、PAUSED：暂停；默认：PAUSED 
   * UNOPEND：未投放、ONPROGRESS:投放中、PAUSED：暂停、FINISHED：已结束
   * */
  private String deliveryStatus;
}
