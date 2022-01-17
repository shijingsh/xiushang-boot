package com.xiushang.security.authentication.wechat;

import com.xiushang.common.user.vo.WxLoginVo;
import com.xiushang.security.authentication.BaseAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;


public class WechatAuthenticationToken extends BaseAuthenticationToken {
    private static final long serialVersionUID = 550L;
    private final Object principal;

    private WxLoginVo wxLoginVo;
    /**
     * 微信小程序登录
     */
    public WechatAuthenticationToken(WxLoginVo wxLoginVo) {
        super(null,wxLoginVo.getClientId());
        this.principal = wxLoginVo.getCode();
        this.wxLoginVo = wxLoginVo;

        setAuthenticated(false);
    }

    /**
     * 账号校验成功之后的token构建
     *
     * @param principal
     * @param authorities
     */
    public WechatAuthenticationToken(String clientId,Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities,clientId);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(isAuthenticated == false, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public WxLoginVo getWxLoginVo() {
        return wxLoginVo;
    }

    public void setWxLoginVo(WxLoginVo wxLoginVo) {
        this.wxLoginVo = wxLoginVo;
    }
}
