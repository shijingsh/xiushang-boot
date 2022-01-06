package com.xiushang.entity.oauth;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * 刷新token
 */
@Entity
@Table(name="oauth_refresh_token")
public class OauthRefreshTokenEntity implements java.io.Serializable{

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;

    /**
     * token
     */
    private String tokenId;

    private Blob token;

    private Blob authentication;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Blob getToken() {
        return token;
    }

    public void setToken(Blob token) {
        this.token = token;
    }

    public Blob getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Blob authentication) {
        this.authentication = authentication;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
