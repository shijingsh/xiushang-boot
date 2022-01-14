package com.xiushang.security;

import com.xiushang.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser extends UserEntity implements UserDetails {

    /**
     * 租户ID
     */
    private String tenantId;

    public SecurityUser(UserEntity user) {
        if (user != null) {
            BeanUtils.copyProperties(user, this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SecurityRoleVo> list = SecurityRoleMapper.INSTANCE.sourceToTarget(getRoles());
        return list;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<SecurityRoleVo> addList) {

        List<SecurityRoleVo> list = SecurityRoleMapper.INSTANCE.sourceToTarget(getRoles());
        if (addList != null && addList.size() > 0) {
            list.addAll(addList);
        }
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
}
