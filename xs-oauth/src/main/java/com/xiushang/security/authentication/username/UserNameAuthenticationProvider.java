package com.xiushang.security.authentication.username;

import com.xiushang.common.utils.MD5;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.security.SecurityRole;
import com.xiushang.security.SecurityRoleVo;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class UserNameAuthenticationProvider  extends TenantProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication ) throws AuthenticationException {

		System.out.println("*********************");
        // [1] 获取 username 和 password
		String userName = (String) authentication.getPrincipal();
        String inputPassword = (String) authentication.getCredentials();

        // [2] 使用用户名从数据库读取用户信息
        SecurityUser securityUser = (SecurityUser) getUserDetailsService().loadUserByUsername(userName);

        // 判断账号是否被禁用
        /*if (null != userDetails && userDetails.getStatus() == 0){
            userDetails.setEnabled(false);
        }*/

     	// [3] 检查用户信息
        if(securityUser == null) {
            throw new UsernameNotFoundException(userName + " 用户不存在");
        } else if (!securityUser.isEnabled()){
            throw new DisabledException(userName + " 用户已被禁用，请联系管理员");
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(userName + " 账号已过期");
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(userName + " 账号已被锁定");
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new LockedException(userName + " 凭证已过期");
        }

        // [4] 数据库用户的密码，一般都是加密过的
        String encryptedPassword = securityUser.getPassword();
        // 根据加密算法加密用户输入的密码，然后和数据库中保存的密码进行比较
        /*if(!passwordEncoder.matches(inputPassword, encryptedPassword)) {
            throw new BadCredentialsException(userName + " 输入账号或密码不正确");
        }*/

        if(!StringUtils.equals(MD5.GetMD5Code(inputPassword),encryptedPassword)){
            throw new BadCredentialsException(userName + " 输入账号或密码不正确");
        }

        //设置租户信息
        String clientId = null;
        if(authentication.getDetails() instanceof  Map){
            Map<String, String> map = (Map<String, String>)authentication.getDetails();
            clientId = map.get("client_id");
            super.settingTenantId(securityUser, clientId);
        }else {
            //设置租户为自己
            String prex = PropertyConfigurer.getConfig("oauth.client.prex");
            /**
             * 默认为自己的Web管理后台客户端,如:xiushangWeb
             * xiushangWeb，xiushangApp 等为系统默认创建的客户端
             */
            clientId = prex + "Web"; //默认为自己的Web管理后台客户端,如:xiushangWeb
            super.settingTenantId(securityUser, clientId);
        }

        //设置附加权限
        List<SecurityRoleVo> list = new ArrayList<>();
        list.add(new SecurityRoleVo(SecurityRole.ROLE_USER));
        if(super.isAdminClient(clientId)){
            list.add(new SecurityRoleVo(SecurityRole.ROLE_CLIENT_MANAGE));
        }

        // [5] 成功登陆，把用户信息提交给 Spring Security
        // 把 userDetails 作为 principal 的好处是可以放自定义的 UserDetails，这样可以存储更多有用的信息，而不只是 username，
        // 默认只有 username，这里的密码使用数据库中保存的密码，而不是用户输入的明文密码，否则就暴露了密码的明文
        return new UsernamePasswordAuthenticationToken(securityUser, securityUser.getPassword(),
                securityUser.getAuthorities(list));
	}

	@Override
	public boolean supports( Class<?> authentication ) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
