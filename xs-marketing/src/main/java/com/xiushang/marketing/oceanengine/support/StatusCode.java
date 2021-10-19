package com.xiushang.marketing.oceanengine.support;

import java.util.Arrays;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public enum StatusCode {
    UNKOWN(-1, "uknown", "未知"),
    OK(0, "OK", "成功"),
    COMM_1(40001, "具体msg见返回信息", "参数错误"),
    COMM_2(40002, "PERMISSION_ERROR", "没有权限进行相关操作"),
    COMM_3(40003, "SQL_FILTER_FIELD_ERROR", "过滤条件的field字段错误"),

    AUTH_0(40100, "REQUEST_TOO_FREQUENT", "请求过于频繁 "),
    AUTH_1(40101, "ERROR_CODE_INVALID_PARTNER", "不合法的接入用户"),
    AUTH_2(40102, "ACCESS_TOKEN_EXPIRE", "access token过期 "),
    AUTH_3(40103, "REFRESH_TOKEN_EXPIRE", "refresh token过期"),
    AUTH_4(40104, "EMPTY_ACCESS_TOKEN", "access token为空 "),
    AUTH_5(40105, "INVALID_ACCESS_TOKEN", "access token错误"),
    AUTH_6(40106, "INVALID_CORE_USER", "账户登录异常"),
    AUTH_7(40107, "INVALID_REFRESH_TOKEN", "refresh token错误"),
    AUTH_8(40108, "INVALID_GRANT_TYPE", "授权类型错误 "),
    AUTH_9(40109, "ERROR_AES_DECIPHER", "密码AES加密错误"),

    F_0(40200, "RECHARGE_AMOUNT_TOO_SMALL", "充值金额太少"),
    F_1(40201, "BALANCE_NOT_ENOUGH", "账户余额不足"),


    ADVER_0(40300, "ADVERTISER_IS_NOT_ENABLE", "广告主状态不可用"),
    ADVER_1(40301, "ADVERTISER_IS_IN_BLACK_LIST", "	广告主在黑名单中"),
    ADVER_2(40302, "PASSWORD_TOO_SIMPLE	", "密码过于简单"),
    ADVER_3(40303, "EMAIL_EXISTED", "邮箱已存在"),
    ADVER_4(40304, "INVALID_EMAIL", "邮箱不合法"),
    ADVER_5(40305, "NAME_EXISTED", "名字已存在"),
    //

    SIGN(40900, "SIGNATURE_ERROR", "文件签名错误"),
    SYS(50000, "SYS_ERROR", "系统错误");

    int code;
    String msg;
    String desc;


    StatusCode(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public static StatusCode lookup(int code) {
        return Arrays.stream(values()).filter(e -> e.code == code).findAny().orElse(UNKOWN);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
