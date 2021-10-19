package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class CreativeGetDetailResponse extends OceanEngineResponse<CreativeGetDetailResponse.CreativeInfos> {

    @Data
    public static class CreativeInfos extends CreativeInfoBase {
        String modify_time;
        Long adverid;
        Long adid;
        Long agentid;
        String updateTime;
    }
}
