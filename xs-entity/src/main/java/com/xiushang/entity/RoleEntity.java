package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sys_role")
public class RoleEntity extends BaseEntity implements GrantedAuthority {

    public RoleEntity() {
    }

    public RoleEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**角色名称 */
    @ApiModelProperty(notes = "角色CODE")
    private String code;
    /**角色名称 */
    @ApiModelProperty(notes = "角色名称")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return code;
    }
}
