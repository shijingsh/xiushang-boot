package com.xiushang.framework.log;

public class SecurityConstants {
    public static final String SIGNING_KEY = "spring-security-@xs!&Secret^#";
    public static final String VALIDATE_CODE_PREFIX = "VALIDATE_CODE:";

    public static final String TEST_CLIENT_ID = "client";

    public static final String TOKEN_PREFIX= "Bearer";
    public static final String AUTH_HEADER_STRING = "Authorization";
    /**
     * Basic认证前缀
     */
    public static final String AUTH_HEADER_BASIC_PREFIX = "Basic ";

    public static final String AUTH_USER_ID_PARAM = "userId";
    public static final String AUTH_TENANT_ID_PARAM = "tenantId";
    public static final String AUTH_CLIENT_ID_PARAM = "clientId";

    public static final String AUTH_CLIENT_ADMIN_PARAM = "clientAdmin";
    public static final String AUTH_CLIENT_AUTH_PARAM = "clientAuth";
    public static final String AUTH_USER_ADMIN_PARAM = "userAdmin";

    public static final long EXPIRATION_TIME = 36000000;

    public static final String USER_KEY = "com_xiushang_global_user_key";
}
