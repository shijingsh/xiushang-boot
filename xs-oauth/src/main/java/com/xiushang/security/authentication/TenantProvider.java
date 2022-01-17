package com.xiushang.security.authentication;

import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.security.SecurityUser;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TenantProvider {

    private UserDetailsService userDetailsService;

    private OauthClientDetailsDao oauthClientDetailsDao;

    public String settingTenantId(SecurityUser securityUser, BaseAuthenticationToken authenticationToken){

        String clientId = authenticationToken.getClientId();
        return settingTenantId(securityUser,clientId);
    }

    public String settingTenantId(SecurityUser securityUser, String clientId){
        if(StringUtils.isNotBlank(clientId)){
            OauthClientDetailsEntity detailsEntity = oauthClientDetailsDao.findByClientId(clientId);
            if(detailsEntity == null){
                throw new InternalAuthenticationServiceException("无法获取租户账号信息");
            }

            String userId = detailsEntity.getUserId();
            SecurityUser user = (SecurityUser)((UserDetailsServiceImpl)userDetailsService).loadUserByUserId(userId);

            if(user == null ){
                throw new InternalAuthenticationServiceException("无法获取租户账号信息");
            }

            securityUser.setTenantId(userId);

            return userId;
        }
        return "";
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
