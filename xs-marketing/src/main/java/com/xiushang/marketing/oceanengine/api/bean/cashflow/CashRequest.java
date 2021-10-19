package com.xiushang.marketing.oceanengine.api.bean.cashflow;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class CashRequest extends BaseModel {
    private Long advertiser_id;
    private BigDecimal amount;
}
