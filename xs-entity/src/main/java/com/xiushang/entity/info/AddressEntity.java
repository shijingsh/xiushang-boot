package com.xiushang.entity.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 用户地址
 */
@Entity
@Table(name="t_user_address")
public class AddressEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(hidden = true)
    private UserEntity user;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "省")
    private String provinceName;
    @ApiModelProperty(value = "省编码")
    private String provinceCode;
    @ApiModelProperty(value = "市")
    private String cityName;
    @ApiModelProperty(value = "市编码")
    private String cityCode;
    @ApiModelProperty(value = "区")
    private String districtName;
    @ApiModelProperty(value = "区编码")
    private String districtCode;
    @Column(length=200)
    @ApiModelProperty(value = "地址")
    private String address;
    @Column(length=2000)
    @ApiModelProperty(value = "全地址")
    private String fullAddress;
    /**
     * 纬度
     */
    @Column(length=20,scale = 8)
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
    /**
     * 经度
     */
    @Column(length=20,scale = 8)
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @ApiModelProperty(value = "是否默认")
    private Boolean userDefault = false;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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

    public Boolean getUserDefault() {
        return userDefault;
    }

    public void setUserDefault(Boolean userDefault) {
        this.userDefault = userDefault;
    }

    public String createFullAddress(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.provinceName)
                .append(this.cityName)
                .append(this.districtName)
                .append(this.address)
                ;
        return sb.toString();
    }
}
