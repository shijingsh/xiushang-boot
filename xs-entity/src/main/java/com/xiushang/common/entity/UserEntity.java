package com.xiushang.common.entity;

import com.xiushang.framework.utils.StatusEnum;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="sys_user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity extends ExpandEntity {

    /**默认密码*/
    public static String DEFAULT_PASSWORD = "123456";
    /**登录名 */
    private String loginName;
    /**登录名 */
    private String unionId;
    /**登录名 */
    private String appleId;
    /**姓名 */
    private String name;
    /**手机号 */
    private String mobile;
    /**密码 */
    private String password;
    /**邮箱 */
    private String email;
    /**QQ */
    private String qq;
    /**微信 */
    private String weixin;
    /**支付宝 */
    private String alipay;
    /**微博 */
    private String weibo;
    /**
     * 状态
     */
    private int status = StatusEnum.STATUS_VALID;
    /**
     * 最后登录时间
     */
    protected Date lastLoginDate;
    /**
     * 最后登录平台
     */
    private String lastLoginPlatform;
    /**
     * 头像
     */
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
    private String accessToken;

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
    private Boolean isInitRecommend = Boolean.FALSE;
    /**
     * 用户的公司实例标识
     */
    @Transient
    private String userToken;

    /**
     * 短信验证码
     */
    @Transient
    private String verifyCode;

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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
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
}
