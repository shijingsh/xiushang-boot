package com.xiushang.common.utils;

import java.math.BigDecimal;

/**
 * gps位置
 * Created by liukefu on 2017/4/26.
 */
public class GpsLocation implements java.io.Serializable{
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 结构化地址信息 省+市+区+街道+门牌号
     */
    private String formatted_address;
    /**
     * 地址所在的省份名 例如：北京市
     */
    private String  province;
    /**
     * 地址所在的城市名例如：北京市
     */
    private String city;

    /**
     * 城市编码 例如：010
     */
    private String citycode;

    /**
     * 地址所在的区 例如：朝阳区
     */
    private String district;

    /**
     * 地址所在的乡镇
     */
    private String  township;

    /**
     * 街道 例如：阜通东大街
     */
    private String street;
    /**
     * 门牌 例如：6号
     */
    private String number;

    /**
     * 区域编码 例如：110101
     */
    private String adcode;

    /**
     * 匹配级别
     */
    private String  level;

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

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
