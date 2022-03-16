package com.itmk.config.security.handler;

import lombok.Data;

@Data
public class LoginResult {
    private int code;
    private String token;
    //token过期时间
    private Long expireTime;
    private Long id;
}
