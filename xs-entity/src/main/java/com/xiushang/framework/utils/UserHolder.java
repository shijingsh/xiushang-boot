package com.xiushang.framework.utils;

import com.xiushang.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 提供会话中的一些主要常量，如登录者的用户ID、租户ID等.
 */
public class UserHolder {

    /**
     * 获得当前登录者User
     */
    public static SecurityUser get() {
        // 获取用户认证信息对象。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 认证信息可能为空，因此需要进行判断。
        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof SecurityUser) {
                SecurityUser user = (SecurityUser) principal;

                return user;
            }
        }
        return null;
    }

    /**
     * 判断是否为后台系统客户端
     */
    public static Boolean getClientAdmin() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        if (Objects.isNull(user)) {
            return false;
        }

        return user.getClientAdmin();
    }


    /**
     * 判断是否为管理员用户
     */
    public static Boolean getUserAdmin() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        if (Objects.isNull(user)) {
            return false;
        }

        return user.getUserAdmin();
    }

    /**
     * 判断是否为客户端授权
     */
    public static Boolean getClientAuth() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        if (Objects.isNull(user)) {
            return false;
        }

        return user.getClientAuth();
    }
    /**
     * 获得当前租户ID
     */
    public static String getTenantId() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        // 认证信息可能为空，因此需要进行判断。
        if (Objects.nonNull(user)) {
            return user.getTenantId();
        }
        return null;
    }

    /**
     * 获得当前客户端ID
     */
    public static String getClientId() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        // 认证信息可能为空，因此需要进行判断。
        if (Objects.nonNull(user)) {
            return user.getClientId();
        }
        return null;
    }

    /**
     * 获得当前用户ID
     */
    public static String getUserId() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
