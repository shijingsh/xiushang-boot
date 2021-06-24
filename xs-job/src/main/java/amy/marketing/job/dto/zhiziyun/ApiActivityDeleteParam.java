package amy.marketing.job.dto.zhiziyun;

import java.util.List;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 活动删除
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiActivityDeleteParam extends AgentDTO {

  /**活动编号集合 */
  private List<String> activityIds;
  
}
