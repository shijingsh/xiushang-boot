package com.xiushang.entity.oauth;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * 授权code
 */
@Entity
@Table(name="oauth_code")
public class OauthCodeEntity implements java.io.Serializable{

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 授权code
     */
    private String code;

    private Blob authentication;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
