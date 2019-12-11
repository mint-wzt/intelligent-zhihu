package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "管理员搜索用户信息", notes = "管理员搜索用户信息")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse search(@RequestParam("username") String username) {
        return sysUserService.search(username);
    }

    @ApiOperation(value = "管理员获取所有用户信息", notes = "管理员获取所有用户信息")
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse getAll() {
        return sysUserService.getAll();
    }

    @ApiOperation(value = "添加用户角色", notes = "添加用户角色")
    @PostMapping("/users")
    @PreAuthorize("hasRole('ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse addAdminRole(@RequestParam("userId") String userId,
                                  @RequestParam("role") String role) {
        return sysUserService.addRole(userId, role);
    }

    @ApiOperation(value = "删除用户管理员角色", notes = "删除添加用户角色")
    @DeleteMapping("/users")
    @PreAuthorize("hasRole('ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse deleteRole(@RequestParam("userId") String userId) {
        return sysUserService.deleteAdminRole(userId);
    }

    @ApiOperation(value = "获取管理员用户信息", notes = "获取管理员用户信息")
    @GetMapping("/users/admin")
    @PreAuthorize("hasRole('ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse getAdminRoles() {
        return sysUserService.getAdmin();
    }


}
