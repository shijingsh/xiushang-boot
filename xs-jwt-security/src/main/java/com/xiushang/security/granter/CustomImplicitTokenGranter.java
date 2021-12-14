package com.xiushang.security.granter;


import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.implicit.ImplicitGrantService;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.Assert;

/**
 * 自定义隐式授权模式
 *
 * @author Dave Syer
 *
 */
public class CustomImplicitTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "implicit";

    private UserDetailsService userDetailsService;

    private OauthClientDetailsDao oauthClientDetailsDao;

    public CustomImplicitTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,
                                      UserDetailsService userDetailsService,OauthClientDetailsDao oauthClientDetailsDao) {
        this(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);

        this.userDetailsService = userDetailsService;
        this.oauthClientDetailsDao = oauthClientDetailsDao;
    }

    protected CustomImplicitTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                         OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest clientToken) {

        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        if (userAuth==null || !userAuth.isAuthenticated()) {
            throw new InsufficientAuthenticationException("There is no currently logged in user");
        }
        Assert.state(clientToken instanceof ImplicitTokenRequest, "An ImplicitTokenRequest is required here. Caller needs to wrap the TokenRequest.");

        SecurityUser securityUser = ((SecurityUser)userAuth.getPrincipal());

        TenantProvider tenantProvider = new TenantProvider();
        tenantProvider.setUserDetailsService(userDetailsService);
        tenantProvider.setOauthClientDetailsDao(oauthClientDetailsDao);

        tenantProvider.settingTenantId(securityUser,client.getClientId());

        OAuth2Request requestForStorage = ((ImplicitTokenRequest)clientToken).getOAuth2Request();

        return new OAuth2Authentication(requestForStorage, userAuth);

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

