package com.xiushang.common.info.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppVersionSaveVo implements java.io.Serializable {

    @ApiModelProperty(value = "客户端ID",required = true)
    private String clientId;
    @ApiModelProperty(value = "版本号 (默认1.0.0)")
    private String version;
    @ApiModelProperty(value = "js版本号 (默认1.0.0)")
    private String jsVersion;
    @ApiModelProperty(value = "更新下载地址")
    private String url;
    @ApiModelProperty(value = "js更新下载地址（热更新）")
    private String jsUrl;
    @ApiModelProperty(value = "更新版本说明",required = true)
    private String content;

}
