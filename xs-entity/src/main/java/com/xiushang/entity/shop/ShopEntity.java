package com.xiushang.entity.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.shop.util.ShopStatusEnum;
import com.xiushang.shop.vo.ShopProjectSnapshot;
import com.xiushang.util.ImageUrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺
 * Created by liukefu on 2017/1/1.
 */
@Entity
@Table(name="app_shop")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShopEntity extends BaseEntity {
    /**
     *  小店名称
     */
    private String name;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 地址
     */
    private String address;

    /**
     *  小店编码
     *  自动生成
     */
    @Column(length = 20)
    private String code;
    /**
     * 联系人号码
     */
    private String mobile;
    /**
     * 联系人
     */
    private String contactsName;
    /**
     * 简介
     */
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
    private String coverUrl;
    /**
     * 存放富文本介绍
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String h5Content;

    /**
     * 小店介绍
     */
    private String h5Url;

    /**
     * 店铺实景图
     */
    @ElementCollection
    @CollectionTable(name="images_shop", joinColumns=@JoinColumn(name="shop_id"))
    @Column(name="images")
    private List<String> images = new ArrayList<>();

    /**
     * 服务资质
     */
    @ManyToOne
    @JoinColumn(name = "qualification")
    private ShopQualificationsEntity shopQualifications;
    /**
     * 店铺状态
     */
    @Enumerated(EnumType.STRING)
    private ShopStatusEnum shopStatus = ShopStatusEnum.SHOP_NONE;
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
     * 服务距离,默认全部，单位 km
     */
    private Integer serviceDistance;

    /**
     * 微信二维码
     */
    private String wxQrcode;

    /**
     * QQ二维码
     */
    private String qqQrcode;


    /**
     * 小程序二维码
     */
    private String weiappQrcode;

    /**
     * 二维码富文本
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String h5QrcodeContent;

    /**
     * 开店第几步
     */
    private Integer step = 1;
    /**
     * 是否是免审店
     */
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isOutAudit= Boolean.FALSE;
    /**
     * 店铺动态设置
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @JSONField(serialize = false, deserialize = false)
    private String settingJson;

    /**
     * 产品推荐列表
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @JSONField(serialize = false, deserialize = false)
    private String projectRecommendJson;

    /**tag */
    private String tag;

    /**审核原因 */
    private String auditOption;

    /**
     * 店铺歇业公告
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String closeOption;
    /**
     * 距离
     */
    @Transient
    private Double distance;
    /**
     * 距离名称
     */
    @Transient
    private String distanceName;
    @Transient
    private String shopStatusStr;
    @Transient
    private long projectTotalNum = 0;
    @Transient
    private String ownerUserId;
    @Transient
    private String shopQualificationsId;
    @Transient
    private JSONObject settingJsonObject = null;
    @Transient
    private List<ShopProjectSnapshot> projectRecommendList = new ArrayList<>();
    @Transient
    private List<String> tags = new ArrayList<>();
    @Transient
    private String userHeadPortrait;
    //-----------------------------系统推荐--------------------------------------------
    /** 标识 */
    @Transient
    private int index;

    /** 评分得分 */
    @Transient
    private float score;

    public ShopQualificationsEntity getShopQualifications() {
        return shopQualifications;
    }

    public void setShopQualifications(ShopQualificationsEntity shopQualifications) {
        this.shopQualifications = shopQualifications;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDistanceName() {
        return distanceName;
    }

    public void setDistanceName(String distanceName) {
        this.distanceName = distanceName;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
        if(this.ownerUser==null){
            this.ownerUser = new UserEntity();
            this.ownerUser.setId(ownerUserId);
        }
    }

    public String getShopQualificationsId() {
        return shopQualificationsId;
    }

    public void setShopQualificationsId(String shopQualificationsId) {
        this.shopQualificationsId = shopQualificationsId;
        if(this.shopQualifications==null){
            this.shopQualifications = new ShopQualificationsEntity();
            this.shopQualifications.setId(shopQualificationsId);
        }
    }

    public String getH5Content() {

        return h5Content;
    }

    public void setH5Content(String h5Content) {
        this.h5Content = h5Content;
    }

    public String getShopStatusStr() {
        return shopStatusStr;
    }

    public Integer getServiceDistance() {
        return serviceDistance;
    }

    public void setServiceDistance(Integer serviceDistance) {
        this.serviceDistance = serviceDistance;
    }

    public String getWxQrcode() {
        return wxQrcode;
    }

    public void setWxQrcode(String wxQrcode) {
        this.wxQrcode = wxQrcode;
    }

    public String getQqQrcode() {
        return qqQrcode;
    }

    public void setQqQrcode(String qqQrcode) {
        this.qqQrcode = qqQrcode;
    }

    public void setShopStatusStr(String shopStatusStr) {
        this.shopStatusStr = shopStatusStr;
        if(ShopStatusEnum.SHOP_NONE.name().equals(shopStatusStr)){
            this.shopStatus = ShopStatusEnum.SHOP_NONE;
        }else if(ShopStatusEnum.SHOP_BASE.name().equals(shopStatusStr)){
            this.shopStatus = ShopStatusEnum.SHOP_BASE;
        }else if(ShopStatusEnum.SHOP_OPENED.name().equals(shopStatusStr)){
            this.shopStatus = ShopStatusEnum.SHOP_OPENED;
        }else if(ShopStatusEnum.SHOP_CLOSE.name().equals(shopStatusStr)){
            this.shopStatus = ShopStatusEnum.SHOP_CLOSE;
        }
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getWeiappQrcode() {
        return weiappQrcode;
    }

    public void setWeiappQrcode(String weiappQrcode) {
        this.weiappQrcode = weiappQrcode;
    }

    public String getH5QrcodeContent() {
        return h5QrcodeContent;
    }

    public void setH5QrcodeContent(String h5QrcodeContent) {
        this.h5QrcodeContent = h5QrcodeContent;
    }

    public String getSettingJson() {
        return settingJson;
    }

    public void setSettingJson(String settingJson) {
        this.settingJson = settingJson;
    }

    public JSONObject getSettingJsonObject() {
        if(settingJsonObject!=null){
            return settingJsonObject;
        }
        if(StringUtils.isNotBlank(this.settingJson)){
            return  JSONObject.parseObject(this.settingJson);
        }
        return new JSONObject();
    }

    public void setSettingJsonObject(JSONObject settingJsonObject) {
        this.settingJsonObject = settingJsonObject;
    }

    public String getProjectRecommendJson() {
        return projectRecommendJson;
    }

    public void setProjectRecommendJson(String projectRecommendJson) {
        this.projectRecommendJson = projectRecommendJson;
    }

    public List<ShopProjectSnapshot> getProjectRecommendList() {
        if(StringUtils.isNotBlank(this.projectRecommendJson)){
            return  JSON.parseArray(this.projectRecommendJson,ShopProjectSnapshot.class);
        }
        return projectRecommendList;
    }

    public void setProjectRecommendList(List<ShopProjectSnapshot> projectRecommendList) {
        this.projectRecommendList = projectRecommendList;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getTags() {
        tags.clear();
        if(StringUtils.isNotBlank(tag)){
            String[] arr = tag.split(" ");
            for (String tag:arr){
                if(StringUtils.isNotBlank(tag)){
                    tags.add(tag);
                }
            }
        }
        return tags;
    }

    public Boolean getOutAudit() {
        if(isOutAudit==null){
            return false;
        }
        return isOutAudit;
    }

    public void setOutAudit(Boolean outAudit) {
        isOutAudit = outAudit;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCloseOption() {
        return closeOption;
    }

    public void setCloseOption(String closeOption) {
        this.closeOption = closeOption;
    }

    public String getAuditOption() {
        return auditOption;
    }

    public void setAuditOption(String auditOption) {
        this.auditOption = auditOption;
    }

    public long getProjectTotalNum() {
        return projectTotalNum;
    }

    public void setProjectTotalNum(long projectTotalNum) {
        this.projectTotalNum = projectTotalNum;
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

    public String getUserHeadPortrait() {
        if(StringUtils.isNotBlank(userHeadPortrait)){
            return userHeadPortrait;
        }
        if(ownerUser!=null){
            return ownerUser.getHeadPortrait();
        }

        return ImageUrlUtil.HEAD_Default;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public void setUserHeadPortrait(String userHeadPortrait) {
        this.userHeadPortrait = userHeadPortrait;
    }
}
