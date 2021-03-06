package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.security.model.LoginUser;
import com.zhihu.intelligent.security.model.RegisterUser;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author wzt
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public GlobalResponse registerUser(@RequestBody RegisterUser registerUser, HttpServletRequest request) {
        return userService.saveUser(registerUser, request);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public void login(@RequestBody LoginUser loginUser) {
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public void logout() {
    }

}
