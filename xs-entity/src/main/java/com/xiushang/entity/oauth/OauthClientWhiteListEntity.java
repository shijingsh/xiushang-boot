package com.xiushang.entity.oauth;

import com.xiushang.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * web类型客户端白名单设置
 * 限制能够使用客户端的IP或域名
 * 保证：clientId 和 clientSecret 安全性
 *
 */
@Entity
@Table(name="oauth_client_whitelist")
public class OauthClientWhiteListEntity extends BaseEntity{

    @ApiModelProperty(notes = "客户端ID")
    private String clientId;

    @ApiModelProperty(notes = "白名单类型 1 ip白名单 2 域名白名单")
    private Integer type;

    @ApiModelProperty(notes = "ip地址或域名")
    private String ipOrDomain;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getType() {
        if(type==null){
            return 1;
        }
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIpOrDomain() {
        if(ipOrDomain==null){
            return "";
        }
        return ipOrDomain;
    }

    public void setIpOrDomain(String ipOrDomain) {
        this.ipOrDomain = ipOrDomain;
    }
}
