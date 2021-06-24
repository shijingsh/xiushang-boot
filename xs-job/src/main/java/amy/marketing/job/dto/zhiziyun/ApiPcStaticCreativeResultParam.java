package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.PageDTO;
import lombok.Data;

/**
 * PC静态素材创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPcStaticCreativeResultParam extends PageDTO {

  /**	推广页编号  */
  private String creativeId;
}
