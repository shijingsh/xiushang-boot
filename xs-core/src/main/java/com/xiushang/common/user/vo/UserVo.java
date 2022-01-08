package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class UserVo implements java.io.Serializable {

    /**姓名、昵称 */
    @ApiModelProperty(notes = "用户姓名、昵称",position = 1)
    private String name;
    /**手机号 */
    @ApiModelProperty(notes = "手机号",position = 2)
    private String mobile;
    /**邮箱 */
    @ApiModelProperty(notes = "邮箱",position = 3)
    private String email;

    /**职位 */
    @ApiModelProperty(notes = "职位",position = 4)
    private String position;
    /**
     * 头像
     */
    @ApiModelProperty(notes = "头像",position = 5)
    private String headPortrait;

    /**
     * 纬度
     */
    @ApiModelProperty(notes = "纬度",position = 6)
    private BigDecimal latitude;
    /**
     * 经度
     */
    @ApiModelProperty(notes = "经度",position = 7)
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
