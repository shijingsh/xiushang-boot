package com.xiushang.marketing.oceanengine.api.bean.cashflow;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CashRequest extends BaseModel {
    private Long advertiser_id;
    private BigDecimal amount;
}
