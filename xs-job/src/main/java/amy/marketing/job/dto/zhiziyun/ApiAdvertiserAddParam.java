package amy.marketing.job.dto.zhiziyun;

import amy.marketing.job.dto.AgentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApiAdvertiserAddParam extends AgentDTO {

    @ApiModelProperty(value = "广告主名称/公司名称")
    private String company;

    @ApiModelProperty(value = "广告主主体资质名称")
    private String advertiserName;

    @ApiModelProperty(value = "公司网站(网址)")
    private String domain;

    @ApiModelProperty(value = "联系人")
    private String contactName;

    @ApiModelProperty(value = "联系电话")
    private String contactTel;

    @ApiModelProperty(value = "通讯地址")
    private String address;

    @ApiModelProperty(value = "广告主所属行业类别编码")
    private String advertiserCategory;

    @ApiModelProperty(value = "结算方式(CPC：CPC单价；CPM：CPM固定单价；CSF：CSF服务费)")
    private String costMode;

    @ApiModelProperty(value = "PC端CPC单价")
    private BigDecimal cpcPrice;

    @ApiModelProperty(value = "移动端CPC固定单价")
    private BigDecimal mcpcPrice;

    @ApiModelProperty(value = "PC端CPM固定单价")
    private BigDecimal cpmFixedPrice;

    @ApiModelProperty(value = "移动端CPM固定单价")
    private BigDecimal mcpmFixedPrice;

    @ApiModelProperty(value = "CSF服务费")
    private BigDecimal csfServiceFee;

    @ApiModelProperty(value = "是否允许透支(0.不允许；1.允许；默认：1)")
    private Integer allowOverDraw;

    @ApiModelProperty(value = "增值设备号单价(mac转化单价)")
    private BigDecimal macPrice;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌LOGO链接")
    private String brandImgUrl;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "password")
    private String password;


    @ApiModelProperty(value = "seatPrice")
    private BigDecimal seatPrice = BigDecimal.ZERO;

    @ApiModelProperty(value = "smsPrice")
    private BigDecimal smsPrice = BigDecimal.ZERO;

    @ApiModelProperty(value = "transTaskPrice")
    private BigDecimal transTaskPrice = BigDecimal.ZERO;

    @ApiModelProperty(value = "fmsPrice")
    private BigDecimal fmsPrice = BigDecimal.ZERO;

    @ApiModelProperty(value = "callTimePrice")
    private BigDecimal callTimePrice = BigDecimal.ZERO;

    private String icpPreparedUrl = "https://www.xiushangsh.com/null/2021/03/18/mg1616043489050106528_100x100.png";

    private String businessLicenseUrl = "https://www.xiushangsh.com/null/2021/03/18/mg1616043489050106528_100x100.png";

}
