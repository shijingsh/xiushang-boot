package com.xiushang.security;

public class SecurityRole implements java.io.Serializable {

    /**
     * 系统预置角色
     * 代表当前会话为：普通的租户客户端，即仅仅做了租户授权，没有用户授权
     * 授权方式：authorization_code、client_credentials、implicit
     */
    public static  final String ROLE_CLIENT = "ROLE_CLIENT";
    /**
     * 系统预置角色
     * 代表当前会话为：客户端授权 + 用户授权 双重授权
     */
    public static  final String ROLE_USER = "ROLE_USER";

    /**
     * 系统预置角色
     * 代表当前会话为：用户型管理员
     */
    public static  final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 系统预置角色
     * 代表当前会话为：管理客户端 （即表示为自己的后台系统客户端，其他表示租户的客户端）
     * 一部分接口，不开放，需要只能自己的客户端才能访问。
     *
     * #application.yml 中设置管理客户端前缀  oauth.client.prex 即以此字符开始的clientId 均为管理客户端
     * #管理客户端ID一般是写死在表中的 命名方式如： xiushangApp xiushangWeiApp xiushangWeb 等。
     * oauth:
     *   client:
     *     prex: xiushang
     */
    public static  final String ROLE_CLIENT_MANAGE = "ROLE_CLIENT_MANAGE";
}
