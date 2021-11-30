package com.xiushang.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiushang.framework.model.AuthorizationVo;
import com.xiushang.framework.utils.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="sys_user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity extends ExpandEntity {

    /**账号登录名 */
    @ApiModelProperty(notes = "账号登录名")
    private String loginName;
    /**unionId登录名 */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private String unionId;
    /**openId登录名 */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private String openId;
    /**appleId登录名 */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private String appleId;
    /**姓名、昵称 */
    @ApiModelProperty(notes = "姓名、昵称")
    private String name;
    /**手机号 */
    @ApiModelProperty(notes = "手机号")
    private String mobile;
    /**密码 */
    @JSONField(serialize = false, deserialize = false)
    private String password;
    /**邮箱 */
    @ApiModelProperty(notes = "邮箱")
    private String email;
    /**QQ */
    @ApiModelProperty(notes = "qq")
    private String qq;
    /**微信 */
    @ApiModelProperty(notes = "微信")
    private String weixin;
    /**支付宝 */
    @ApiModelProperty(notes = "支付宝")
    private String alipay;
    /**微博 */
    @ApiModelProperty(notes = "微博")
    private String weibo;
    /**职位 */
    @ApiModelProperty(notes = "职位")
    private String position;
    /**
     * 状态
     */
    @ApiModelProperty(notes = "状态 （0 无效  1 有效）")
    private int status = StatusEnum.STATUS_VALID;
    /**
     * 最后登录时间
     */
    @ApiModelProperty(notes = "最后登录时间")
    protected Date lastLoginDate;
    /**
     * 最后登录平台
     */
    @ApiModelProperty(notes = "最后登录平台")
    private String lastLoginPlatform;
    /**
     * 头像
     */
    @ApiModelProperty(notes = "用户头像")
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String headPortrait;

    /**
     * 纬度
     */
    @Column(name = "latitude",precision = 20,scale = 8)
    private BigDecimal latitude;
    /**
     * 经度
     */
    @Column(name = "longitude",precision = 20,scale = 8)
    private BigDecimal longitude;

    /**
     * 第三方登录token
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private String accessToken;

    /**
     * 第三方推送 客户ID
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private String clientId;

    /**
     * 是否管理员
     */
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isAdmin = Boolean.FALSE;

    /**
     * 是否已为用户配置推荐
     */
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private Boolean isInitRecommend = Boolean.FALSE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="sys_user_role",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private List<RoleEntity> roles;

    /**
     * 短信验证码
     */
    @Transient
    private String verifyCode;

    @Transient
    private AuthorizationVo authorization;

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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppleId() {
        return appleId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLastLoginPlatform() {
        return lastLoginPlatform;
    }

    public void setLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
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

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getInitRecommend() {
        return isInitRecommend;
    }

    public void setInitRecommend(Boolean initRecommend) {
        isInitRecommend = initRecommend;
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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthorizationVo getAuthorization() {
        return authorization;
    }

    public void setAuthorization(AuthorizationVo authorization) {
        this.authorization = authorization;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
