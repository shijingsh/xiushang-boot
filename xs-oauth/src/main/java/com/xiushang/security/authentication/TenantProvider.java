package com.xiushang.security.authentication;

import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.security.SecurityUser;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TenantProvider {

    private UserDetailsService userDetailsService;

    private OauthClientDetailsDao oauthClientDetailsDao;

    public String settingTenantId(SecurityUser securityUser, String clientId){
        if(StringUtils.isNotBlank(clientId)){
            OauthClientDetailsEntity detailsEntity = oauthClientDetailsDao.findByClientId(clientId);
            if(detailsEntity == null){
                throw new InternalAuthenticationServiceException("无法获取租户信息!");
            }

            String userId = detailsEntity.getUserId();
            SecurityUser clientUser = (SecurityUser)((UserDetailsServiceImpl)userDetailsService).loadUserByUserId(userId);

            if(clientUser == null ){
                throw new InternalAuthenticationServiceException("无法获取租户账号信息!");
            }

            securityUser.setTenantId(userId);
            securityUser.setClientId(clientId);

            return userId;
        }
        return "";
    }

    public boolean isAdminClient(String clientId){
        if(StringUtils.isBlank(clientId)){
            return false;
        }
        String prex = PropertyConfigurer.getConfig("oauth.client.prex");
        if(StringUtils.isBlank(prex)){
            prex = "";
        }
        if(clientId.startsWith(prex)){
            return true;
        }
        return false;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public OauthClientDetailsDao getOauthClientDetailsDao() {
        return oauthClientDetailsDao;
    }

    public void setOauthClientDetailsDao(OauthClientDetailsDao oauthClientDetailsDao) {
        this.oauthClientDetailsDao = oauthClientDetailsDao;
    }
}
