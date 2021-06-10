package com.xiushang.framework.utils;

import com.xiushang.entity.UserEntity;
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
    public static UserEntity getLoginUser() {
        UserEntity user = null;
        // 获取用户认证信息对象。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 认证信息可能为空，因此需要进行判断。
        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserEntity) {
                user = (UserEntity) principal;
            }
        }
        return user;
    }

    /**
     * 获得当前登录者的User ID
     */
    public static String getLoginUserId() {
        UserEntity user = getLoginUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    /**
     * 获得当前登录者的User Name
     */
    public static String getLoginUserName() {
        UserEntity user = getLoginUser();
        if (user == null) {
            return null;
        }
        return user.getName();
    }

    /**
     * 获得当前登录者的User instanceId
     */
    public static String getLoginUserTenantId() {
        return  "";
    }

}
