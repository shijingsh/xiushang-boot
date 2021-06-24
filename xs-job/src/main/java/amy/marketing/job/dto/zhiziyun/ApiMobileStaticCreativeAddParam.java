package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 移动静态素材创建
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiMobileStaticCreativeAddParam extends AgentDTO {

  /** 素材资源链接 必填*/
  private String creativeResourceUrl;
  /** 移动点击类型 必填 可用值:Webview,Browser,Download,AppStore,DetailPage,Sendmail,Sms,Dial,Openmap,Other*/
  private String mobileClickType;
  /**素材名称 必填*/
  private String name;
  /**推广链接编号  必填*/
  private String promotionLinkId;
  /**广告主编号 必填*/
  private String siteId;
  
	/**添加水印*/
  private boolean addWaterMark;
  /** APP描述*/
  private String appDesc;
  /** APP名称*/
  private String appName;
  /** APP大小*/
  private String appPackageSize;
  /** 第三方效果检测类型 可选值:talkingdata、appsflyer、ledou、reyun、zzy、tencent、hulai、qudongrensheng、adjust、xinlan、borui、coinads、empty；默认：empty*/
  private String cvsFormType;
  /** APP下载包名 mobileClickType：Download时必填*/
  private String mobileCbundle;
  /** 移动流量类型 0：不限、1：APP ONLY、2：WAP ONLY；默认：0*/
  private Integer mobileFlowType;
  
}
