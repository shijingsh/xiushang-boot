package com.xiushang.common.user.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAdminVo extends BaseVO {

    /**姓名、昵称 */
    @ApiModelProperty(notes = "用户姓名、昵称",position = 1,required = true)
    private String name;
    /**手机号 */
    @ApiModelProperty(notes = "手机号",position = 2,required = true)
    private String mobile;
    /**邮箱 */
    @ApiModelProperty(notes = "邮箱",position = 3)
    private String email;

    /**密码 */
    @ApiModelProperty(notes = "密码",position = 4,required = true)
    private String password;

    @ApiModelProperty(notes = "是否已删除 （0 未删除  1 已删除）")
    private Integer deleted;
}
