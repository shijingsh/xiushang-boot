package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiAdvertiserStatusParam extends AgentDTO {
    @ApiModelProperty(value = "广告主编号")
    private String  siteId;

}
