package amy.marketing.job.dto.zhiziyun;

import java.util.List;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * PC静态素材创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiCreativeStatusParam extends AgentDTO {

	/**素材类型,STATIC:PC静态素材、VIDEO:PC视频素材、NATIVE:PC信息流素材、
	   * MOBILE:移动静态素材、MOBILEVIDEO:移动视频素材、MOBILENATIVE:移动信息流素材 */
	private String creativeType;	
  /**素材编号*/
  private String creativeId;
  /**多个素材编号 单次最多传100个*/
  private List<String> creativeIds;
}
