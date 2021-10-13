package com.xiushang.entity.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;

import javax.persistence.*;

/**
 * 店铺资质
 * Created by liukefu on 2017/2/20.
 */
@Entity
@Table(name="app_shop_qualifications")
public class ShopQualificationsEntity extends BaseEntity {
    /**
     * 所属店铺
     */
    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JSONField(serialize = false, deserialize = false)
    private ShopEntity belongShop;
    @Column(length = 50)
    private String realName;
    @Column(length = 50)
    private String IdCard;
    @Column(length = 50)
    private String businessLicense;
    private String imageIdCard;
    private String imageIdCardBack;
    private String imageLicense;

    /**
     * 状态标记 -1删除 0 编辑 1 审核中 2 已实名认证  3 核驳回
     */
    private Integer status = 0;

    /**审核原因 */
    private String auditOption;

    @Transient
    private String shopId;
    @Transient
    private String type;

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
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
