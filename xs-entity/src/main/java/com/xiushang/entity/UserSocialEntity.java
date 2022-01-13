package com.xiushang.entity;

import com.xiushang.util.SocialTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 用戶绑定的社交账号
 */
@Entity
@Table(name="sys_user_social")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSocialEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(notes = "用户ID")
    private String userId;

    /**
     * 用户ID
     */
    @ApiModelProperty(notes = "客户端ID")
    private String clientId;
    /**
     * 社交账号类型
     * socialType
     */
    @ApiModelProperty(notes = "socialType")
    @Enumerated(EnumType.STRING)
    private SocialTypeEnum socialType = SocialTypeEnum.SOCIAL_TYPE_OPEN_ID;

    /**
     * 社交账号类型socialType 对应的值
     */
    @ApiModelProperty(notes = "socialId")
    private String socialId;

    @ApiModelProperty(notes = "昵称")
    private String nickName;
    @ApiModelProperty(notes = "头像")
    private String avatarUrl;
    @ApiModelProperty(notes = "性别")
    private String gender;
    @ApiModelProperty(notes = "email")
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public SocialTypeEnum getSocialType() {
        return socialType;
    }

    public void setSocialType(SocialTypeEnum socialType) {
        this.socialType = socialType;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
