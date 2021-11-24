package com.xiushang.security.authentication;


import com.xiushang.entity.UserEntity;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends UserEntity implements UserDetails, Serializable {

    @Setter
    private Boolean accountNonExpired=true;
    @Setter
    private Boolean accountNonLocked=true;
    @Setter
    private Boolean credentialsNonExpired=true;
    @Setter
    private Boolean enabled=true;
    public SecurityUser(UserEntity user) {
        if(user != null) {
            BeanUtils.copyProperties(user, this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        /*List<Role> userRoles = this.getRoles();
        if(userRoles != null){
            for (Role role : userRoles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
                authorities.add(authority);
            }
        }*/
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getLoginName();
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
