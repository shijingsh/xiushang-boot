package com.xiushang.security;

import com.xiushang.entity.RoleEntity;
import com.xiushang.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser extends UserEntity implements UserDetails {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 客户端ID
     */
    private String clientId;

    public SecurityUser(UserEntity user) {
        if (user != null) {
            BeanUtils.copyProperties(user, this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getDefault();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(SecurityRoleVo securityRoleVo) {

        List<SecurityRoleVo> list = getDefault();
        if (securityRoleVo != null ) {
            list.add(securityRoleVo);
        }
        return list;
    }

    private List<SecurityRoleVo> getDefault(){
        List<SecurityRoleVo> list = new ArrayList<>();
        for (RoleEntity role : getRoles()){
            SecurityRoleVo roleVo = new SecurityRoleVo(role.getCode(),role.getName());
            list.add(roleVo);
        }

        list.add(new SecurityRoleVo(SecurityRole.ROLE_CLIENT));
        return list;
    }


    @Override
    public String getUsername() {
        return getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getDeleted() == 0;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
