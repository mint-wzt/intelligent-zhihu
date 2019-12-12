package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.exception.FormatException;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysUserService {


    @Autowired
    private UserRepository userRepository;

    @Action(type = "READ", operation = "搜索用户信息")
    public GlobalResponse search(String username) {
        log.info("输入的用户名为:" + username);
        try {
            if (StringUtils.isEmpty(username.trim())) {
                throw new FormatException("输入的用户名为空");
            }
        } catch (Exception e) {
            log.error("输入的用户名为空");
            throw new FormatException("输入的用户名为空");
        }
        // 获取包含该username的所有用户
        List<User> users = userRepository.findUsersWithPartOfUserName(username);
        GlobalResponse response = new GlobalResponse(200, "获取用户信息成功");
        response.getData().put("users", users);
        return response;
    }


    @Action(type = "READ", operation = "获取所有用户信息")
    public GlobalResponse getAll() {
        // 获取所有用户
        List<User> users = userRepository.findAll();
        GlobalResponse response = new GlobalResponse(200, "获取用户信息成功");
        response.getData().put("users", users);
        return response;
    }

    @Action(type = "CREATE", operation = "添加角色")
    public GlobalResponse addRole(String userId, String role) {
        log.info("用戶ID添加角色:" + userId + "|" + role);
        try {
            if (StringUtils.isEmpty(userId.trim()) || StringUtils.isEmpty(role.trim())) {
                throw new FormatException("添加角色信息不能为空");
            }
        } catch (Exception e) {
            log.error("添加角色信息不能为空");
            throw new FormatException("添加角色信息不能为空");
        }
        User user = userRepository.findById(userId).get();
        user.setRoles(user.getRoles() + ",ROLE_" + role);
        userRepository.save(user);

        GlobalResponse response = new GlobalResponse(200, "操作成功");
        return response;
    }

    @Action(type = "DELETE", operation = "删除用户的管理员角色")
    public GlobalResponse deleteAdminRole(String userId) {
        log.info("用户ID删除用户的管理员角色：" + userId);
        // 删除管理员角色
        User user = userRepository.findById(userId).get();
        List<String> roleList = new ArrayList<>();
        Arrays.stream(user.getRoles().split(",")).forEach(role -> roleList.add(role));
        roleList.remove("ROLE_ADMIN");
        user.setRoles(roleList.stream().collect(Collectors.joining(",")));
        userRepository.save(user);

        GlobalResponse response = new GlobalResponse(200, "操作成功");
        return response;
    }

    @Action(type = "READ", operation = "获取所有管理员用户")
    public GlobalResponse getAdmin() {
        List<User> admins = userRepository.findUsersWithPartOfRoles("ADMIN");
        GlobalResponse response = new GlobalResponse(200, "操作成功");
        response.getData().put("admins", admins);
        return response;
    }

}
