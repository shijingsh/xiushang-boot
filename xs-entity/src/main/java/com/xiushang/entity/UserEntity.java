package com.xiushang.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="sys_user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity extends ExpandEntity {

    /**账号登录名（手机号码）  涉密数据不返回*/
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "账号登录名（手机号码）",hidden = true)
    @Column(updatable=false)
    private String loginName;

    /**姓名、昵称 */
    @ApiModelProperty(notes = "姓名、昵称")
    private String name;

    /**头像 */
    @ApiModelProperty(notes = "用户头像")
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String headPortrait;

    /**手机号 联系方式*/
    @ApiModelProperty(notes = "手机号")
    private String mobile;
    /**密码  涉密数据不返回*/
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "密码",hidden = true)
    private String password;
    /**邮箱 */
    @ApiModelProperty(notes = "邮箱")
    private String email;

    /**职位 */
    @ApiModelProperty(notes = "职位")
    private String position;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(notes = "最后登录时间")
    protected Date lastLoginDate;
    /**
     * 最后登录平台
     * 客户端ID
     */
    @ApiModelProperty(notes = "最后登录客户端",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String lastLoginClient;

    /**
     * 纬度
     */
    @Column(name = "latitude",precision = 20,scale = 8)
    @ApiModelProperty(notes = "latitude",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private BigDecimal latitude;
    /**
     * 经度
     */
    @Column(name = "longitude",precision = 20,scale = 8)
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "longitude",hidden = true)
    private BigDecimal longitude;

    /**
     * 是否已为用户配置推荐
     */
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "是否已为用户配置推荐",hidden = true)
    private Boolean initRecommend = Boolean.FALSE;

    /**
     * 用户角色
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="sys_user_role",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "用户角色",hidden = true)
    private List<RoleEntity> roles = new ArrayList<>();


    public UserEntity(){}

    public UserEntity(String _name, String _password) {
        this.name = _name;
        this.password = _password;
    }

    public UserEntity(String _name) {
        this.name = _name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLastLoginClient() {
        return lastLoginClient;
    }

    public void setLastLoginClient(String lastLoginClient) {
        this.lastLoginClient = lastLoginClient;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Boolean getInitRecommend() {
        return initRecommend;
    }

    public void setInitRecommend(Boolean initRecommend) {
        this.initRecommend = initRecommend;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
