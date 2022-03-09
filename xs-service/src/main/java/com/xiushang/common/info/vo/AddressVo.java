package com.xiushang.common.info.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressVo extends BaseVO {


    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    @ApiModelProperty(value = "手机号码",required = true)
    private String mobile;

    @ApiModelProperty(value = "省",required = true)
    private String provinceName;
    @ApiModelProperty(value = "省编码")
    private String provinceCode;
    @ApiModelProperty(value = "市",required = true)
    private String cityName;
    @ApiModelProperty(value = "市编码")
    private String cityCode;
    @ApiModelProperty(value = "区",required = true)
    private String districtName;
    @ApiModelProperty(value = "区编码")
    private String districtCode;
    @ApiModelProperty(value = "地址",required = true)
    private String address;
    @ApiModelProperty(value = "全地址",hidden = true)
    private String fullAddress;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "是否默认")
    private Boolean userDefault = false;

}
