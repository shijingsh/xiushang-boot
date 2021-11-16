package com.xiushang.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SearchUtil {

    public static String convertHightlighting(String pkid, String high_key, Map<String, Map<String, List<String>>> map) {
        List titleList = (List) ((Map) map.get(pkid)).get(high_key);
        return titleList != null ? (String) titleList.get(0) : null;
    }

    public static String getValue(Object o) {
        if (o.getClass().isAssignableFrom(ArrayList.class)) {
            o = ((List) o).get(0);
        }
        return o != null ? o.toString() : null;
    }

    public static int getIntegerValue(Object o) {
        if(o==null){
            return 0;
        }
        if (o.getClass().isAssignableFrom(ArrayList.class)) {
            o = ((List) o).get(0);
        }
        return Integer.parseInt(o != null ? o.toString() : "0");
    }

    public static float getFloatValue(Object o) {
        if(o==null){
            return 0;
        }
        if (o.getClass().isAssignableFrom(ArrayList.class)) {
            o = ((List) o).get(0);
        }
        return Float.parseFloat(o != null ? o.toString() : "0");
    }

    public static boolean getBooleanValue(Object o) {
        if(o==null){
            return false;
        }
        if (o.getClass().isAssignableFrom(ArrayList.class)) {
            o = ((List) o).get(0);
        }
        return Boolean.parseBoolean(o != null ? o.toString() : "0");
    }

    public static String getFirstValue(Object o) {
        return o != null ? String.valueOf(((List) o).get(0)) : null;
    }

    public static List<String> getStringList(Object o) {
        return (List) o;
    }

    public static Double getDoubleValue(Object o) {
        return Double.valueOf(Double.parseDouble(o != null ? o.toString() : "0"));
    }

    public static BigDecimal getBigDecimalValue(Object o) {
        if(o==null){
            return BigDecimal.ZERO;
        }
        if (o.getClass().isAssignableFrom(ArrayList.class)) {
            o = ((List) o).get(0);
        }
        if (o.getClass().isAssignableFrom(String.class)) {
            String  oStr = ((String) o).replaceAll("java.math.BigDecimal:","");
            return  new BigDecimal(oStr);
        }
        return  (BigDecimal)o;
    }

    public static BigDecimal getBigDecimalContains2Decimal(String o) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            String str = df.format(Double.parseDouble(o != null ? o : "0"));
            return new BigDecimal(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new BigDecimal("0.00");
    }

    public static BigDecimal getRegistered_capital(String o) {
        try {
            Double dou = Double.valueOf(Double.parseDouble(o != null ? o : "0"));
            int num = dou.intValue();
            if (dou.doubleValue() > num) {
                return new BigDecimal(new StringBuilder().append(dou).append("").toString());
            }
            return new BigDecimal(new StringBuilder().append(num).append("").toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new BigDecimal("0");
    }

    public static String convertSearchContent(String content) {
        if ((content != null) && (!content.equals("")) && (!content.equals("*"))) {
            content = content.trim().replaceAll(" ", ",");
            content = content.trim().replaceAll("\\^", ",");
            content = content.replaceAll("[\\pP]", "-");
            String[] arr = content.split("-");
            String strs = "";
            for (String art : arr) {
                if (!StringUtils.isBlank(art))
                    strs = new StringBuilder().append(strs).append("".equals(strs) ? "" : ",").append(art).toString();
            }
            strs = (strs != null) && (!strs.trim().equals("")) ? strs.trim() : "*";
            return strs;
        }
        return content;
    }

    public static boolean isChineseNumber(String str) {
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (!"一二三四五六七八九十".contains(new StringBuilder().append(str.charAt(i)).append("").toString()))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static void main(String[] s) {
        System.out.println(getRegistered_capital("2.0"));
        System.out.println(getRegistered_capital("2.00"));
        System.out.println("一二三四五六七八九十".indexOf("一"));
        System.out.println(isChineseNumber("一"));

    }

}
