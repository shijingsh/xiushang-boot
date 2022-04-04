package com.xiushang.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.RoleEntity;
import com.xiushang.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
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
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "租户ID",hidden = true)
    private String tenantId;

    /**
     * 客户端ID
     */
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(notes = "客户端ID",hidden = true)
    private String clientId;
    /**
     * 客户端管理员
     */
    @ApiModelProperty(notes = "客户端管理员")
    private Boolean clientAdmin = false;
    /**
     * 管理员用户
     */
    @ApiModelProperty(notes = "管理员用户")
    private Boolean userAdmin = false;

    /**
     * 客户端授权
     */
    @ApiModelProperty(notes = "客户端授权")
    private Boolean clientAuth = false;

    public SecurityUser(UserEntity user) {
        if (user != null) {
            BeanUtils.copyProperties(user, this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getDefault();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<SecurityRoleVo> addList ) {

        List<SecurityRoleVo> list = getDefault();
        if (addList != null ) {
            for (SecurityRoleVo securityRoleVo:addList){
                if(securityRoleVo != null) {
                    if(SecurityRole.ROLE_CLIENT_MANAGE.equals(securityRoleVo.getCode())){
                        this.setClientAdmin(true);
                    }
                    list.add(securityRoleVo);
                }
            }
        }
        return list;
    }

    private List<SecurityRoleVo> getDefault(){
        List<SecurityRoleVo> list = new ArrayList<>();
        for (RoleEntity role : getRoles()){
            SecurityRoleVo roleVo = new SecurityRoleVo(role.getCode(),role.getName());
            if(SecurityRole.ROLE_ADMIN.equals(roleVo.getCode())){
                this.setUserAdmin(true);
            }
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

    public Boolean getClientAdmin() {
        return clientAdmin;
    }

    public void setClientAdmin(Boolean clientAdmin) {
        this.clientAdmin = clientAdmin;
    }

    public Boolean getClientAuth() {
        return clientAuth;
    }

    public void setClientAuth(Boolean clientAuth) {
        this.clientAuth = clientAuth;
    }

    public Boolean getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(Boolean userAdmin) {
        this.userAdmin = userAdmin;
    }
}
