package com.xiushang.marketing.oceanengine.support;

import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class Filter {
    private String field;
    private Op operator;
    private Object value;

    enum Op {
        EQUAL, CONTAIN, LESS_EQUAL, LESS, GREATER_EQUAL, GREATER
    }
}
