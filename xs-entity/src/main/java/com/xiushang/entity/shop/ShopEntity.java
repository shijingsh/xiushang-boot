package com.xiushang.entity.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.shop.util.ShopStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商铺
 */
@Entity
@Table(name="t_shop")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShopEntity extends BaseEntity {
    /**
     * 账号类型
     * 1 个人
     * 2 企业
     */
    @ApiModelProperty(notes = "账号类型 （1 个人 2 企业）",required = true)
    private Integer accountType = 1;
    /**
     *  小店名称
     */
    @ApiModelProperty(notes = "小店名称",required = true)
    private String name;
    /**
     * 公司名称
     */
    @ApiModelProperty(notes = "公司名称")
    private String companyName;
    /**
     * 地址
     */
    @ApiModelProperty(notes = "地址")
    private String address;

    /**
     *  小店编码
     *  自动生成
     */
    @ApiModelProperty(notes = "小店编码（自动生成）")
    @Column(length = 20)
    private String code;
    /**
     * 联系人号码
     */
    @ApiModelProperty(notes = "联系人号码",required = true)
    private String mobile;
    /**
     * 联系人
     */
    @ApiModelProperty(notes = "联系人名称",required = true)
    private String contactsName;
    /**
     * 简介
     */
    @ApiModelProperty(notes = "简介")
    private String briefInfo;

    /**
     * 拥有者
     */
    @ManyToOne
    @JoinColumn(name = "ownerUser")
    private UserEntity ownerUser;
    /**
     * 海报
     */
    @ApiModelProperty(notes = "小店海报")
    private String coverUrl;
    /**
     * 存放富文本介绍
     */
    @ApiModelProperty(notes = "存放富文本介绍")
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String h5Content;

    /**
     * 小店介绍
     */
    @ApiModelProperty(notes = "小店介绍URL")
    private String h5Url;

    /**
     * 商铺实景图
     */
    @ApiModelProperty(notes = "商铺实景图")
    @ElementCollection
    @CollectionTable(name="rel_images_shop", joinColumns=@JoinColumn(name="shop_id"))
    @Column(name="images")
    private List<String> images = new ArrayList<>();

    /**
     * 商铺状态
     */
    @ApiModelProperty(notes = "商铺状态(SHOP_BASE 编辑中  SHOP_OPENED 营业中  SHOP_CLOSE 歇业中)")
    @Enumerated(EnumType.STRING)
    private ShopStatusEnum shopStatus = ShopStatusEnum.SHOP_BASE;
    /**
     * 纬度
     */
    @ApiModelProperty(notes = "纬度")
    @Column(name = "latitude",precision = 20,scale = 8)
    private BigDecimal latitude;
    /**
     * 经度
     */
    @ApiModelProperty(notes = "经度")
    @Column(name = "longitude",precision = 20,scale = 8)
    private BigDecimal longitude;

    /**
     * 服务距离,默认全部，单位 km
     */
    @ApiModelProperty(notes = "服务距离,默认全部，单位 km")
    private Integer serviceDistance;

    /**标签 */
    @ApiModelProperty(notes = "标签（多个逗号分隔）")
    private String tag;

    /**
     * 商铺歇业公告
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "商铺歇业公告")
    private String closeOption;

    //-----------------------------系统推荐--------------------------------------------
    /** 标识 */
    @Transient
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "标识",hidden = true)
    private int index;

    /** 评分得分 */
    @Transient
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "评分得分",hidden = true)
    private float score;

    public Integer getAccountType() {
        if(accountType==null){
            return 1;
        }
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public UserEntity getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(UserEntity ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ShopStatusEnum getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(ShopStatusEnum shopStatus) {
        this.shopStatus = shopStatus;
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


    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }


    public String getH5Content() {

        return h5Content;
    }

    public void setH5Content(String h5Content) {
        this.h5Content = h5Content;
    }


    public Integer getServiceDistance() {
        return serviceDistance;
    }

    public void setServiceDistance(Integer serviceDistance) {
        this.serviceDistance = serviceDistance;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCloseOption() {
        return closeOption;
    }

    public void setCloseOption(String closeOption) {
        this.closeOption = closeOption;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
