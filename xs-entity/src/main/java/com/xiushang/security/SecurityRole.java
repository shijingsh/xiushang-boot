package com.xiushang.security;

public class SecurityRole implements java.io.Serializable {

    /**
     * 系统预置角色
     * 代表当前会话为：客户端授权
     * 授权方式：authorization_code、client_credentials、implicit
     */
    public static  final String ROLE_CLIENT = "ROLE_CLIENT";
    /**
     * 系统预置角色
     * 代表当前会话为：用户授权
     */
    public static  final String ROLE_USER = "ROLE_USER";

    /**
     * 系统预置角色
     * 代表当前会话为：用户型管理员
     */
    public static  final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 系统预置角色
     * 代表当前会话为：客户端型管理员 （即表示为自己的后台系统客户端，其他表示租户的客户端）
     * 一部分接口，不开放，需要只能自己的客户端才能访问。
     */
    public static  final String ROLE_CLIENT_ADMIN = "ROLE_CLIENT_ADMIN";
}
