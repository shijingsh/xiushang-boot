package com.xiushang.entity.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 商铺资质
 */
@Entity
@Table(name="t_shop_qualifications")
public class ShopQualificationsEntity extends BaseEntity {
    /**
     * 所属商铺
     */
    @ManyToOne
    @JoinColumn(name = "shop_id")
    @ApiModelProperty(notes = "所属商铺",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private ShopEntity belongShop;
    @Column(length = 50)
    @ApiModelProperty(notes = "真实姓名")
    private String realName;
    @Column(length = 50)
    @ApiModelProperty(notes = "身份证号码")
    private String idCard;
    @Column(length = 50)
    @ApiModelProperty(notes = "营业执照号码")
    private String businessLicense;
    @ApiModelProperty(notes = "身份证正面图片")
    private String imageIdCard;
    @ApiModelProperty(notes = "身份证反面图片")
    private String imageIdCardBack;
    @ApiModelProperty(notes = "营业执照图片")
    private String imageLicense;

    /**
     * 状态标记 -1删除 0 编辑 1 审核中 2 已实名认证  3 核驳回
     */
    private Integer status = 0;

    /**审核原因 */
    private String auditOption;


    public ShopEntity getBelongShop() {
        return belongShop;
    }

    public void setBelongShop(ShopEntity belongShop) {
        this.belongShop = belongShop;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getImageIdCard() {
        return imageIdCard;
    }

    public void setImageIdCard(String imageIdCard) {
        this.imageIdCard = imageIdCard;
    }

    public String getImageIdCardBack() {
        return imageIdCardBack;
    }

    public void setImageIdCardBack(String imageIdCardBack) {
        this.imageIdCardBack = imageIdCardBack;
    }

    public String getImageLicense() {
        return imageLicense;
    }

    public void setImageLicense(String imageLicense) {
        this.imageLicense = imageLicense;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditOption() {
        return auditOption;
    }

    public void setAuditOption(String auditOption) {
        this.auditOption = auditOption;
    }

}
