package amy.marketing.job.dto.zhiziyun;

import java.util.List;

import amy.marketing.dao.entity.AdCreativeStatusEntity;
import amy.marketing.job.dto.PageDTO;
import lombok.Data;

/**
 * 推广页创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiCreativeStatusResultParam extends PageDTO {

  /**	素材编号  */
  private String creativeId;
  /**	推广页编号  */
  private String pretrialMsg;
  /**	推广页编号  */
  private String pretrialStatus;
  
  /**	各平台审核状态集合  */
  private List<AdCreativeStatusEntity> dataStatus;
}
