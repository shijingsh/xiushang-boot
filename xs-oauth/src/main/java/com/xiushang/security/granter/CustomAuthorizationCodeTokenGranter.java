package com.xiushang.security.granter;

import java.util.HashMap;
import java.util.Map;

import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 自定义 AuthorizationCodeTokenGranter
 *
 * @author Dave Syer
 *
 */
public class CustomAuthorizationCodeTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "authorization_code";

    private final AuthorizationCodeServices authorizationCodeServices;

    private UserDetailsService userDetailsService;

    private OauthClientDetailsDao oauthClientDetailsDao;

    public CustomAuthorizationCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                                               AuthorizationCodeServices authorizationCodeServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,
                                               UserDetailsService userDetailsService,OauthClientDetailsDao oauthClientDetailsDao) {
        this(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
        this.oauthClientDetailsDao = oauthClientDetailsDao;
    }

    protected CustomAuthorizationCodeTokenGranter(AuthorizationServerTokenServices tokenServices, AuthorizationCodeServices authorizationCodeServices,
                                                  ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authorizationCodeServices = authorizationCodeServices;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = tokenRequest.getRequestParameters();
        String authorizationCode = parameters.get("code");
        String redirectUri = parameters.get(OAuth2Utils.REDIRECT_URI);

        if (authorizationCode == null) {
            throw new InvalidRequestException("An authorization code must be supplied.");
        }

        OAuth2Authentication storedAuth = authorizationCodeServices.consumeAuthorizationCode(authorizationCode);
        if (storedAuth == null) {
            throw new InvalidGrantException("Invalid authorization code: " + authorizationCode);
        }

        OAuth2Request pendingOAuth2Request = storedAuth.getOAuth2Request();
        // https://jira.springsource.org/browse/SECOAUTH-333
        // This might be null, if the authorization was done without the redirect_uri parameter
        String redirectUriApprovalParameter = pendingOAuth2Request.getRequestParameters().get(
                OAuth2Utils.REDIRECT_URI);

        if ((redirectUri != null || redirectUriApprovalParameter != null)
                && !pendingOAuth2Request.getRedirectUri().equals(redirectUri)) {
            throw new RedirectMismatchException("Redirect URI mismatch.");
        }

        String pendingClientId = pendingOAuth2Request.getClientId();
        String clientId = tokenRequest.getClientId();
        if (clientId != null && !clientId.equals(pendingClientId)) {
            // just a sanity check.
            throw new InvalidClientException("Client ID mismatch");
        }

        // Secret is not required in the authorization request, so it won't be available
        // in the pendingAuthorizationRequest. We do want to check that a secret is provided
        // in the token request, but that happens elsewhere.

        Map<String, String> combinedParameters = new HashMap<String, String>(pendingOAuth2Request
                .getRequestParameters());
        // Combine the parameters adding the new ones last so they override if there are any clashes
        combinedParameters.putAll(parameters);

        // Make a new stored request with the combined parameters
        OAuth2Request finalStoredOAuth2Request = pendingOAuth2Request.createOAuth2Request(combinedParameters);

        Authentication userAuth = storedAuth.getUserAuthentication();
        SecurityUser securityUser = ((SecurityUser)userAuth.getPrincipal());

        TenantProvider tenantProvider = new TenantProvider();
        tenantProvider.setUserDetailsService(userDetailsService);
        tenantProvider.setOauthClientDetailsDao(oauthClientDetailsDao);

        tenantProvider.settingTenantId(securityUser,clientId);

        securityUser.setClientAuth(true);

        return new OAuth2Authentication(finalStoredOAuth2Request, userAuth);

    }

}
