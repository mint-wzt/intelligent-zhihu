package com.zhihu.intelligent.security.model;

import lombok.Data;


/**
 * @author wzt
 * 用户注册时，输入的个人信息封装类
 */
@Data
public class RegisterUser {

    private String username; // 用户名(必填)
    private String password; // 密码(必填)
    private String industry; // 所在行业(必填)
    private String career; // 职业
    private String education; // 教育经历（必填）
    private String gender; // 性别
    private String email; // 邮箱(必填)
}
