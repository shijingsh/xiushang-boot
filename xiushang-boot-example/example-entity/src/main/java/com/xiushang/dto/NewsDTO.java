package com.xiushang.dto;

import lombok.Data;

@Data
public class NewsDTO implements java.io.Serializable{

    private String id;
    private String content;
    private String  userId;
}
