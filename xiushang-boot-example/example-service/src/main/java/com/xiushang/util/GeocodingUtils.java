package com.xiushang.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 物理地址逆编码
 * Created by liukefu on 2017/4/25.
 */
public class GeocodingUtils {
    private static final double EARTH_RADIUS = 6378.138;//地球半径
    private final static Logger logger = LoggerFactory.getLogger(GeocodingUtils.class);
    public static GpsLocation geocoding(String address){
         String url = "http://restapi.amap.com/v3/geocode/geo?key=ce1b956afb3803d85a37ec9c4819a2cc&address="+address;

         String json = HttpClientUtil.sendGetRequest(url);
         GpsLocation location = new GpsLocation();
         JSONObject res = JSON.parseObject(json);
         String status = res.getString("status");
         if ("1".equals(status)) {
             JSONArray result = res.getJSONArray("geocodes");
             if (result!=null && result.size()>0){
                 JSONObject jsonObject = result.getJSONObject(0);
                 if(jsonObject!=null){
                     String sLocation = jsonObject.getString("location");
                     System.err.println(sLocation);
                     if (StringUtils.isNotBlank(sLocation)) {
                         String arr[] = sLocation.split(",");
                         location.setLongitude(new BigDecimal(arr[0]));
                         location.setLatitude(new BigDecimal(arr[1]));
                     }
                     location.setProvince(jsonObject.getString("province"));
                     location.setCity(jsonObject.getString("city"));
                     location.setCitycode(jsonObject.getString("citycode"));
                     location.setDistrict(jsonObject.getString("district"));
                     location.setAdcode(jsonObject.getString("adcode"));
                     location.setFormatted_address(jsonObject.getString("formatted_address"));
                     location.setLevel(jsonObject.getString("level"));
                     location.setNumber(jsonObject.getString("number"));
                     location.setStreet(jsonObject.getString("street"));
                 }
             }
         }else {
             logger.info("获取gps失败！");
         }
        return location;
     }

    /**
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static String getDistanceName(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2)
    {
        if(lat1==null || lng1==null || lat2==null || lng2==null){
            return "";
        }

        double distance = Math.round(
                EARTH_RADIUS * 2 * Math.asin(
                        Math.sqrt(
                                Math.pow(
                                        Math.sin(
                                                (lat1.doubleValue() * Math.PI / 180- lat2.doubleValue() * Math.PI / 180) / 2
                                        ),2)
                                + Math.cos(lat1.doubleValue() * Math.PI / 180) *  Math.cos(lat2.doubleValue() * Math.PI / 180)
                                   * Math.pow(
                                        Math.sin(
                                                (lng1.doubleValue() * Math.PI / 180- lng2.doubleValue() * Math.PI / 180) / 2
                                        ),
                                        2)
                        )
                ) * 1000
        );

        if(distance < 1000 && distance >0){
            DecimalFormat df = new DecimalFormat("######0");
            return df.format(distance)+"m";
        }else if(distance >= 1000 && distance < 1000000){
            DecimalFormat df = new DecimalFormat("######0.00");
            distance = distance / 1000;
            return df.format(distance)+"km";
        }
        return "";
    }

     public static void main(String args[]){
        String str = "北京市朝阳区阜通东大街6号";
         GeocodingUtils.geocoding(str);
     }
}
