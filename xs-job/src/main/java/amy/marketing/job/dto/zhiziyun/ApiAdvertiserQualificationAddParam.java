package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiAdvertiserQualificationAddParam extends AgentDTO {


    @ApiModelProperty(value = "广告主id")
    private String siteId;

    @ApiModelProperty(value = "智子云资质编号")
    private String qualificationId;

    @ApiModelProperty(value = "资质名称")
    private String qualificationName;

    @ApiModelProperty(value = "资质文件内容")
    private String qualificationContent;

    @ApiModelProperty(value = "资质模板编号")
    private String qualificationTemplateId;

    @ApiModelProperty(value = "资质类型,可用值:BASIC,SPECIAL")
    private String qualificationType;

    @ApiModelProperty(value = "资质内容类型,可用值:URL,TEXT")
    private String contentType;

    @ApiModelProperty(value = "资质适用广告平台编号逗号隔开")
    private String[] networkIds;

}
