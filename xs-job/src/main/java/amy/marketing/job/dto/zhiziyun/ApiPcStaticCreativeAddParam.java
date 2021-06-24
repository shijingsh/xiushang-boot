package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * PC静态素材创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPcStaticCreativeAddParam extends AgentDTO {
	/**添加水印*/
  private boolean addWaterMark;
  /** 素材资源链接 必填*/
  private String creativeResourceUrl;
  /**推广链接编号  必填*/
  private String promotionLinkId;
  /**素材名称 必填*/
  private String name;
  /**广告主编号 必填*/
  private String siteId;
}
