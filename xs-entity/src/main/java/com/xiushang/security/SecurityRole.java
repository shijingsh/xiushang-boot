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
     * 代表当前会话为：管理员
     */
    public static  final String ROLE_ADMIN = "ROLE_ADMIN";
}
