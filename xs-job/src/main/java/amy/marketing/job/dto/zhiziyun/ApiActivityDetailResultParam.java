package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.PageDTO;
import lombok.Data;

/**
 * 推广页创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiActivityDetailResultParam extends PageDTO {

	/**总预算 单位：元；[-1, 20000000]；默认：-1（不限）*/
	  private Long budget;
	  /** 日预算 单位：元；[10, 20000000] 必填*/
	  private Long dailybudget;
	  /** 活动投放状态,PAUSED：暂停、ONPROGRESS：开启；默认：未投放*/
	  private String deliveryStatus;
	  /** 投放开始时间 yyyy-MM-dd HH:mm:ss*/
	  private String startTime;
	  /** 投放结束时间  格式为yyyy-MM-dd HH:mm:ss*/
	  private String endTime;
	  /**活动周期内总点击次数上限 [-1, 2000000000]；默认：-1（不限）*/
	  private Long lifeCycleClickCapping;
	  /** 活动周期内总曝光次数上限 [-1, 2000000000]；默认：-1（不限）  */
	  private Long lifeCycleImpressionCapping;
	  /** 活动名称 */
	  private String activityName;
	  /** 广告主编号*/
	  private String activityId;
	  
}
