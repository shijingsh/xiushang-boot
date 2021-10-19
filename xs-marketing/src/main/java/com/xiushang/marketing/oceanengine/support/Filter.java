package com.xiushang.marketing.oceanengine.support;

import lombok.Data;


@Data
public class Filter {
    private String field;
    private Op operator;
    private Object value;

    enum Op {
        EQUAL, CONTAIN, LESS_EQUAL, LESS, GREATER_EQUAL, GREATER
    }
}
