package com.xiushang.security;

import lombok.Data;

@Data
public class AuthenticationBean implements java.io.Serializable{
    private String username;
    private String password;
}
