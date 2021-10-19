package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.api.bean.cashflow.AdverBalanceQueryResponse;
import com.xiushang.marketing.oceanengine.support.code.AdverRole;
import com.xiushang.marketing.oceanengine.support.code.AdverStatus;
import lombok.Data;
import lombok.experimental.Accessors;


import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AdvertiserInfoResponse extends OceanEngineResponse<List<AdvertiserInfoResponse.AdvertiserInfo>> {


    @Data
    @Accessors(chain = true)
    public static class AdvertiserInfo {
        public static final AdvertiserInfo EMTPY = new AdvertiserInfo();
        private Long id;
        private String name;
        private String description;
        private String email;
        private String contacter;
        private String phonenumber;
        private AdverRole role;
        private AdverStatus status;
        private String telephone;
        private String address;
        private String license_url;
        private String license_no;
        private String license_province;
        private String license_city;
        private String company;
        private String brand;
        private String promotion_area;
        private String promotion_center_province;
        private String promotion_center_city;
        private String industry;
        private String reason;
        private BigDecimal balance;
        private String statusName;
        private Long agentid;
        private String agentName;
        private Long adverid;
        private String updateTime;
        private Boolean hasLoginInfo;
        private String userName;
        private String pwd;
        private String createTime;
        Boolean permission;
        private AdverBalanceQueryResponse.BalanceData balanceData;
    }
}
