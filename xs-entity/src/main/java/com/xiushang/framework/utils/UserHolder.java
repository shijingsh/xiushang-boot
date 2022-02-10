package com.xiushang.framework.utils;

import com.xiushang.entity.UserEntity;
import com.xiushang.security.SecurityRole;
import com.xiushang.security.SecurityRoleVo;
import com.xiushang.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;

/**
 *  提供会话中的一些主要常量，如登录者的用户ID、租户ID等.
 *
 */
public class UserHolder {

    /**
     * 获得当前登录者User
     */
    public static UserEntity getUser() {
        return get();
    }
    /**
     * 获得当前登录者User
     */
    private static SecurityUser get() {
        // 获取用户认证信息对象。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 认证信息可能为空，因此需要进行判断。
        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();

            if(principal instanceof SecurityUser){
                SecurityUser user = (SecurityUser) principal;

                return user;
            }
        }
        return null;
    }

    /**
     * 获得当前登录者是否是管理员
     */
    public static Boolean isAdmin() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        if (Objects.nonNull(user)) {
            return false;
        }
        List<SecurityRoleVo> list = (List<SecurityRoleVo>) user.getAuthorities();
        for (SecurityRoleVo roleVo:list){
            if(roleVo.getCode() == SecurityRole.ROLE_ADMIN){
                return true;
            }
        }
        return false;
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
     * 获得当前用户名
     */
    public static String getLoginName() {
        // 获取用户认证信息对象。
        SecurityUser user = get();
        if(user != null){
            return user.getLoginName();
        }
        return null;
    }
}
