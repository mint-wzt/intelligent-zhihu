package com.zhihu.intelligent.security.entity;

import lombok.Data;


/**
 * @author wzt
 * 用户登录的封装类
 */
@Data
public class LoginUser {

    private String username;
    private String password;
    private Boolean rememberMe;

}
