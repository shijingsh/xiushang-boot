package com.xiushang.framework.log;

public class SecurityConstants {
    public static final String SIGNING_KEY = "spring-security-@Jwt!&Secret^#";

    public static final String TOKEN_PREFIX= "Bearer";
    public static final String AUTH_HEADER_STRING = "Authorization";
    public static final String USER_HEADER_STRING = "AccessToken";
    public static final long EXPIRATION_TIME = 36000000;
}
