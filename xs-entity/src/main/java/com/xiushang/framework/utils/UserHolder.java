package com.xiushang.framework.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 *  提供会话中的一些主要常量，如登录者的用户ID等.
 *
 */
public class UserHolder {

    /**
     * 获得当前登录者User
     */
    public static String getLoginName() {
        // 获取用户认证信息对象。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 认证信息可能为空，因此需要进行判断。
        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();

            String userName = (String) principal;
            String loginName = userName.split("-")[0];

            return loginName;
        }
        return null;
    }

    /**
     * 获得当前登录者的User instanceId
     */
    public static String getLoginUserTenantId() {
        return  "";
    }

}
