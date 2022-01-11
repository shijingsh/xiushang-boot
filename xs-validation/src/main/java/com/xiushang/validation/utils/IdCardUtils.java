package com.xiushang.validation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class IdCardUtils {

    private static final Logger logger = LoggerFactory.getLogger(IdCardUtils.class);

    /** 中国公民身份证号码最小长度。 */
    private static final int CHINA_ID_MIN_LENGTH = 15;

    /** 中国公民身份证号码最大长度。 */
    private static final int CHINA_ID_MAX_LENGTH = 18;

    /** 每位加权因子 */
    private static final int[] POWER = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };
    /** 第18位校检码 */
    private static final String[] VERIFY_CODE = {
            "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"
    };
    /** 最低年限 */
    private static final int MIN = 1930;
    /** 城市对应码 */
    private static Map<String, String> cityCodes = new HashMap<>();

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");
    private static final Pattern TW_ID_PATTERN = Pattern.compile("^[a-zA-Z][0-9]{9}$");
    private static final Pattern HK_ID_PATTERN = Pattern.compile("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$");
    private static final Pattern AM_ID_PATTERN = Pattern.compile("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$");

    static {
        cityCodes.put("11", "北京");
        cityCodes.put("12", "天津");
        cityCodes.put("13", "河北");
        cityCodes.put("14", "山西");
        cityCodes.put("15", "内蒙古");
        cityCodes.put("21", "辽宁");
        cityCodes.put("22", "吉林");
        cityCodes.put("23", "黑龙江");
        cityCodes.put("31", "上海");
        cityCodes.put("32", "江苏");
        cityCodes.put("33", "浙江");
        cityCodes.put("34", "安徽");
        cityCodes.put("35", "福建");
        cityCodes.put("36", "江西");
        cityCodes.put("37", "山东");
        cityCodes.put("41", "河南");
        cityCodes.put("42", "湖北");
        cityCodes.put("43", "湖南");
        cityCodes.put("44", "广东");
        cityCodes.put("45", "广西");
        cityCodes.put("46", "海南");
        cityCodes.put("50", "重庆");
        cityCodes.put("51", "四川");
        cityCodes.put("52", "贵州");
        cityCodes.put("53", "云南");
        cityCodes.put("54", "西藏");
        cityCodes.put("61", "陕西");
        cityCodes.put("62", "甘肃");
        cityCodes.put("63", "青海");
        cityCodes.put("64", "宁夏");
        cityCodes.put("65", "新疆");
        cityCodes.put("71", "台湾");
        cityCodes.put("81", "香港");
        cityCodes.put("82", "澳门");
        cityCodes.put("91", "国外");
    }

    /**
     * 验证身份证是否合法
     * <p>自动验证：18位身份证，15位身份证，香港，台湾的身份证</p>
     */
    public static boolean validateCard(String idCard) {
        String card = idCard.trim();
        if (validateIdCard18(card)) {
            return true;
        }
        if (validateIdCard15(card)) {
            return true;
        }
        if (validateIdCard10(card)) {
            return true;
        }
        return false;
    }

    /**
     * 验证18位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 是否合法
     */
    private static boolean validateIdCard18(String idCard) {
        boolean bTrue = false;
        if (idCard.length() == CHINA_ID_MAX_LENGTH) {
            // 前17位
            String code17 = idCard.substring(0, 17);
            // 第18位
            String code18 = idCard.substring(17, CHINA_ID_MAX_LENGTH);
            if (isNumberString(code17)) {
                char[] cArr = code17.toCharArray();
                if (cArr != null) {
                    int[] iCard = charToInt(cArr);
                    int iSum17 = getPowerSum(iCard);
                    // 获取校验位
                    String val = getCheckCode18(iSum17);
                    if (val.length() > 0) {
                        if (val.equalsIgnoreCase(code18)) {
                            bTrue = true;
                        }
                    }
                }
            }
        }
        return bTrue;
    }

    /**
     * 验证15位身份编码是否合法
     *
     * @param idCard
     *            身份编码
     * @return 是否合法
     */
    private static boolean validateIdCard15(String idCard) {
        if (idCard.length() != CHINA_ID_MIN_LENGTH || !isNumberString(idCard)) {
            return false;
        }

        String proCode = idCard.substring(0, 2);
        if (Objects.isNull(cityCodes.get(proCode))) {
            return false;
        }
        String birthCode = idCard.substring(6, 12);
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yy").parse(birthCode.substring(0, 2));
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthDate);
            if (!validDate(cal.get(Calendar.YEAR),
                    Integer.parseInt(birthCode.substring(2, 4)),
                    Integer.parseInt(birthCode.substring(4, 6)))) {
                return false;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 验证10位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 验证结果
     */
    private static boolean validateIdCard10(String idCard) {
        if (idCard.length() < 8 || idCard.length() > 10) {
            return false;
        }
        if (TW_ID_PATTERN.matcher(idCard).matches()){
            return true;
        }
        if (HK_ID_PATTERN.matcher(idCard).matches()){
            return true;
        }
        if (AM_ID_PATTERN.matcher(idCard).matches()){
            return true;
        }
        return false;
    }

    /**
     * 将字符数组转换成数字数组
     *
     * @param ca
     *            字符数组
     * @return 数字数组
     */
    private static int[] charToInt(char[] ca) {
        int len = ca.length;
        int[] iArr = new int[len];
        try {
            for (int i = 0; i < len; i++) {
                iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
            }
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(),e);
        }
        return iArr;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param iArr
     * @return 身份证编码。
     */
    private static int getPowerSum(int[] iArr) {
        int iSum = 0;
        if (POWER.length == iArr.length) {
            for (int i = 0; i < iArr.length; i++) {
                for (int j = 0; j < POWER.length; j++) {
                    if (i == j) {
                        iSum = iSum + iArr[i] * POWER[j];
                    }
                }
            }
        }
        return iSum;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     *
     * @param iSum
     * @return 校验位
     */
    private static String getCheckCode18(int iSum) {
        return VERIFY_CODE[iSum % VERIFY_CODE.length];
    }

    /**
     * 数字验证
     *
     * @param val
     * @return 提取的数字。
     */
    private static boolean isNumberString(String val) {
        return Objects.nonNull(val) && NUMBER_PATTERN.matcher(val).matches();
    }

    /**
     * 验证小于当前日期 是否有效
     *
     * @param iYear
     *            待验证日期(年)
     * @param iMonth
     *            待验证日期(月 1-12)
     * @param iDate
     *            待验证日期(日)
     * @return 是否有效
     */
    private static boolean validDate(int iYear, int iMonth, int iDate) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int datePerMonth;
        if (iYear < MIN || iYear >= year) {
            return false;
        }
        if (iMonth < 1 || iMonth > 12) {
            return false;
        }
        switch (iMonth) {
            case 4:
            case 6:
            case 9:
            case 11:
                datePerMonth = 30;
                break;
            case 2:
                datePerMonth = (iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0) ? 29 : 28;
                break;
            default:
                datePerMonth = 31;
        }
        return (iDate >= 1) && (iDate <= datePerMonth);
    }
}


