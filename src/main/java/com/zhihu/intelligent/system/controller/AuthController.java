package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.security.model.LoginUser;
import com.zhihu.intelligent.security.model.RegisterUser;
import com.zhihu.intelligent.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @Autowired
//    private HttpServletRequest request;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes = "用户注册")
    public ResponseEntity registerUser(@RequestBody RegisterUser registerUser) {
        userService.saveUser(registerUser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    public void login(@RequestBody LoginUser loginUser){ }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录",notes = "退出登录")
    public void logout(){ }

}
