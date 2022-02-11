package com.xiushang.common.user.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import com.xiushang.util.ClientTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OauthClientDetailsSaveVo extends BaseVO implements java.io.Serializable{

    @ApiModelProperty(notes = "资源id列表")
    private String resourceIds;

    @ApiModelProperty(notes = "客户端名称", required = true)
    private String clientName;

    @ApiModelProperty(notes = "客户端密钥 （新增客户端必填，修改时，为空则为不修改秘钥）")
    private String clientSecret;

    @ApiModelProperty(notes = "客户端类型 （CLIENT_TYPE_WX_MINI_APP，CLIENT_TYPE_APP，CLIENT_TYPE_APP，CLIENT_TYPE_WEB）")
    private ClientTypeEnum clientType = ClientTypeEnum.CLIENT_TYPE_WX_MINI_APP;

    @ApiModelProperty(notes = "域scope 默认 all")
    private String scope = "all";

    @ApiModelProperty(notes = "支持的授权方式 默认 authorization_code,refresh_token,password,client_credentials,implicit,sms_code,captcha,social_type,wechat")
    private String authorizedGrantTypes = "authorization_code,refresh_token,password,client_credentials,implicit,sms_code,captcha,social_type,wechat";

    @ApiModelProperty(notes = "回调地址 默认 https://www.xiushangsh.com")
    private String webServerRedirectUri = "https://www.xiushangsh.com";

    @ApiModelProperty(notes = "权限列表")
    private String authorities;

    @ApiModelProperty(notes = "认证令牌时效(单位秒默认7天 60 * 60 * 24 * 7)")
    private Integer accessTokenValidity;

    @ApiModelProperty(notes = "刷新令牌时效(单位秒默认30天 60 * 60 * 24 * 30)")
    private Integer refreshTokenValidity;

    @ApiModelProperty(notes = "扩展信息")
    private String additionalInformation;

    @ApiModelProperty(notes = "是否自动放行")
    private String autoapprove = "false";

    /**
     * 小程序APP_ID
     */
    @ApiModelProperty(notes = "appId")
    private String appId;
    /**
     * 小程序APP_SECRET
     */
    @ApiModelProperty(notes = "appSecret")
    private String secret;
}
