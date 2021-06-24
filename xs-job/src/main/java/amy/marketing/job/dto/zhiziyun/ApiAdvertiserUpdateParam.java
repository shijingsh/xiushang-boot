package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiAdvertiserUpdateParam extends AgentDTO {
    @ApiModelProperty(value = "广告主编号")
    private String  siteId;

    @ApiModelProperty(value = "广告主名称/公司名称")
    private String company;

    @ApiModelProperty(value = "公司网站(网址)")
    private String domain;

    @ApiModelProperty(value = "联系人")
    private String contactName;

    @ApiModelProperty(value = "联系电话")
    private String contactTel;

    @ApiModelProperty(value = "通讯地址")
    private String address;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌LOGO链接")
    private String brandImgUrl;
}
