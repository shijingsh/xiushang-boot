package com.xiushang.common.user.vo;

public class PhoneDecryptInfo {
    private String phoneNumber;
    private String purePhoneNumber;
    private int countryCode;
    private String weixinWaterMark;
    private WaterMark watermark;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getWeixinWaterMark() {
        return weixinWaterMark;
    }

    public void setWeixinWaterMark(String weixinWaterMark) {
        this.weixinWaterMark = weixinWaterMark;
    }

    public WaterMark getWatermark() {
        return watermark;
    }

    public void setWatermark(WaterMark watermark) {
        this.watermark = watermark;
    }
}

