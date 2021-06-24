package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 推广页创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPcStaticCreativeUpdateParam extends AgentDTO {

  /**素材名称 */
  private String name;
  /**素材编号 必填  */
  private String creativeId;
  
  /**推广页编号 */
  private String promotionLinkId;
  
  /**素材类型,STATIC :PC静态素材、VIDEO:PC视频素材、NATIVE:PC信息流素材、
   * MOBILE:移动静态素材、MOBILEVIDEO:移动视频素材、MOBILENATIVE:移动信息流素材 */
  private String creativeType;	
  
}
