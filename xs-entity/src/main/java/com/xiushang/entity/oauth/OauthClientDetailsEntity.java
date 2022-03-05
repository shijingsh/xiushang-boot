package com.xiushang.entity.oauth;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseUserEntity;
import com.xiushang.util.ClientTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户端
 */
@Entity
@Table(name = "oauth_client_details")
public class OauthClientDetailsEntity extends BaseUserEntity {

    @ApiModelProperty(notes = "资源id列表",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String resourceIds = "oauth2";

    @ApiModelProperty(notes = "客户端ID")
    private String clientId;
    @ApiModelProperty(notes = "客户端名称")
    private String clientName;
    @ApiModelProperty(notes = "客户端密钥", hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String clientSecret;

    @ApiModelProperty(notes = "客户端类型 （CLIENT_TYPE_WX_MINI_APP，CLIENT_TYPE_APP，CLIENT_TYPE_APP，CLIENT_TYPE_WEB）")
    @Enumerated(EnumType.STRING)
    private ClientTypeEnum clientType = ClientTypeEnum.CLIENT_TYPE_WX_MINI_APP;

    @ApiModelProperty(notes = "域scope 默认 all", hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String scope = "all";

    @ApiModelProperty(notes = "支持的授权方式 默认 authorization_code,refresh_token,password,client_credentials,implicit,sms_code,captcha,social_type,wechat",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String authorizedGrantTypes = "authorization_code,refresh_token,password,client_credentials,implicit,sms_code,captcha,social_type,wechat";

    @ApiModelProperty(notes = "回调地址")
    private String webServerRedirectUri;

    @ApiModelProperty(notes = "权限列表",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String authorities;

    @ApiModelProperty(notes = "认证令牌时效(单位秒默认7天 60 * 60 * 24 * 7)")
    private Integer accessTokenValidity;
    @ApiModelProperty(notes = "刷新令牌时效(单位秒默认30天 60 * 60 * 24 * 30)")
    private Integer refreshTokenValidity;

    @Column(length = 1000)
    @ApiModelProperty(notes = "扩展信息")
    private String additionalInformation;

    @ApiModelProperty(notes = "是否自动放行",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String autoapprove = "false";

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

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

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        if (accessTokenValidity == null || accessTokenValidity <= 0) {
            this.accessTokenValidity = null;
        } else {
            this.accessTokenValidity = accessTokenValidity;
        }
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        if (refreshTokenValidity == null || refreshTokenValidity <= 0) {
            this.refreshTokenValidity = null;
        } else {
            this.refreshTokenValidity = refreshTokenValidity;
        }
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ClientTypeEnum getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEnum clientType) {
        this.clientType = clientType;
    }

    public String getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
