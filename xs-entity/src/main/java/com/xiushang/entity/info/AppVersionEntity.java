package com.xiushang.entity.info;

import com.xiushang.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 版本管理
 */
@Entity
@Table(name="t_app_version")
public class AppVersionEntity extends BaseEntity {
    /**
     * 商铺ID，可以实现配置多个APP
     */
    @ApiModelProperty(value = "商铺ID",hidden = true)
    private String shopId;
    /**
     * 客戶端ID
     */
    @ApiModelProperty(value = "客戶端ID",hidden = true)
    private String clientId;

    @ApiModelProperty(value = "版本号")
    private String version;
    @ApiModelProperty(value = "js版本号（热更新）")
    private String jsVersion;
    @ApiModelProperty(value = "更新下载地址")
    private String url;
    @ApiModelProperty(value = "js更新下载地址（热更新）")
    private String jsUrl;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "更新版本说明")
    private String content;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsVersion() {
        return jsVersion;
    }

    public void setJsVersion(String jsVersion) {
        this.jsVersion = jsVersion;
    }

    public String getJsUrl() {
        return jsUrl;
    }

    public void setJsUrl(String jsUrl) {
        this.jsUrl = jsUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
