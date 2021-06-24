package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiAdvertiserQualificationUpdateParam extends AgentDTO {
    @ApiModelProperty(value = "广告主编号")
    private String  siteId;

    @ApiModelProperty(value = "智子云资质编号")
    private String qualificationId;

    @ApiModelProperty(value = "资质适用广告平台编号逗号隔开")
    private String[] networkIds;
}
