package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class UserVo implements java.io.Serializable {

    /**姓名、昵称 */
    @ApiModelProperty(notes = "用户姓名、昵称")
    private String name;
    /**手机号 */
    @ApiModelProperty(notes = "手机号")
    private String mobile;
    /**邮箱 */
    @ApiModelProperty(notes = "邮箱")
    private String email;
    /**QQ */
    @ApiModelProperty(notes = "QQ")
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
     * 头像
     */
    @ApiModelProperty(notes = "头像")
    private String headPortrait;

    /**
     * 纬度
     */
    @ApiModelProperty(notes = "纬度")
    private BigDecimal latitude;
    /**
     * 经度
     */
    @ApiModelProperty(notes = "经度")
    private BigDecimal longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
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
}
