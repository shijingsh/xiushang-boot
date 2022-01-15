package com.xiushang.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SecurityRoleVo implements GrantedAuthority {
    /**角色名称 */
    @ApiModelProperty(notes = "角色CODE")
    private String code;
    /**角色名称 */
    @ApiModelProperty(notes = "角色名称")
    private String name;

    public SecurityRoleVo() {
    }

    public SecurityRoleVo(String code) {
        this.code = code;
        this.name = code;
    }

    public SecurityRoleVo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return code;
    }
}
