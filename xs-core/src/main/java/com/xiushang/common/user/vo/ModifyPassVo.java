package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class ModifyPassVo implements java.io.Serializable {

    @ApiModelProperty(notes = "旧密码")
    private String oldPassword;
    @ApiModelProperty(notes = "新密码")
    private String newPassword;
    @ApiModelProperty(notes = "确认新密码")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
