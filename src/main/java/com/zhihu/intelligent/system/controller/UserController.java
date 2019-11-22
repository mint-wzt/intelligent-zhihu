package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息", notes = "根据id获取用户信息")
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public String getUser(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @ApiOperation(value = "更新用户", notes = "更新用户资料")
    @PostMapping("/users/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public String updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

}
