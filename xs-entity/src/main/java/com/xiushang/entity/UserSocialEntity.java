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
    @ApiParam("userId")
    private String userId;

    /**
     * 社交账号类型
     * socialType
     */
    @ApiModelProperty(notes = "socialType")
    @ApiParam("socialType")
    @Enumerated(EnumType.STRING)
    private SocialTypeEnum socialType = SocialTypeEnum.SOCIAL_TYPE_OPEN_ID;

    /**
     * 社交账号类型socialType 对应的值
     */
    @ApiModelProperty(notes = "socialId")
    @ApiParam("socialId")
    private String socialId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
