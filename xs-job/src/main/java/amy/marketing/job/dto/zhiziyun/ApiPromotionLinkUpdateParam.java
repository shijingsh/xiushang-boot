package amy.marketing.job.dto.zhiziyun;

import java.util.List;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 推广页创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPromotionLinkUpdateParam extends AgentDTO {
	/**异步点击检测链接  最大支持三个*/
  private List<String> asyncClickUrls ;
  /**异步曝光检测链接  最大支持三个*/
  private List<String> asyncMonitorUrls;
  /**应用直达链接  */
  private String deeplinkUrl = "";
  /**落地页链接 */
  private String landingPageUrl = "";
  /**第三方曝光检测链接 最大支持二个 */
  private List<String> monitorUrls ;
  /**推广链接名称 */
  private String name = "";
  /**点击链接  */
  private String targetUrl = "";
  
  /**推广页编号 必填 */
  private String promotionLinkId = "";
}
