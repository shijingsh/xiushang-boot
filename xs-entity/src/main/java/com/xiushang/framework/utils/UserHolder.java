package com.xiushang.framework.utils;

import com.xiushang.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  提供会话中的一些主要常量，如登录者的用户ID等.
 *
 */
public class UserHolder {
    private static Logger logger = LoggerFactory.getLogger(UserHolder.class);

    /**
     * 获得当前登录者User
     */
    public static UserEntity getLoginUser() {

       /* Subject currentUser = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) currentUser.getPrincipal();
*/
        return null;
    }

    /**
     * 获得当前登录者的User ID
     */
    public static String getLoginUserId() {
        UserEntity user = getLoginUser();
        if (user == null) {
            return null;
        }
        return "user.getId()";
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
