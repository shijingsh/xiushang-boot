package com.xiushang.common.user.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OauthClientWhiteListSaveVo extends BaseVO {

    @ApiModelProperty(notes = "客户端ID",required = true)
    private String clientId;

    @ApiModelProperty(notes = "白名单类型 1 ip白名单 2 域名白名单",required = true)
    private Integer type;

    @ApiModelProperty(notes = "ip地址或域名",required = true)
    private String ipOrDomain;
}
