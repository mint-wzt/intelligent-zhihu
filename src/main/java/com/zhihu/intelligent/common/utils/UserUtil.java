package com.zhihu.intelligent.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhihu.intelligent.common.constants.SecurityConstants;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class UserUtil {
    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper wrapper = new BeanWrapperImpl(source);
        return Stream.of(wrapper.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(property->wrapper.getPropertyValue(property) == null)
                .toArray(String[]::new);
    }

    // 获取用户角色
    public static List<SimpleGrantedAuthority> getPermissions(User user) {
        String roles = user.getRoles();
        String permissions = user.getPermissions();
        List<SimpleGrantedAuthority> rolesList = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role ->
                rolesList.add(new SimpleGrantedAuthority(role)));
        Arrays.stream(permissions.split(",")).forEach(permission ->
                rolesList.add(new SimpleGrantedAuthority("ROLE_" + permission)));
        return rolesList;
    }


    //获取当前认证的用户
    public static String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null || authentication.getPrincipal() != null){
            return (String) authentication.getPrincipal();
        }
        return null;
    }

//    // 获取与用户相关的GlobalResponse
//    public static GlobalResponse globalResponse(int code,String message,User user){
//        GlobalResponse globalResponse = new GlobalResponse(code,message);
//        JSONObject data = globalResponse.getData();
//        data.put("id",user.getId());
//        data.put("username", user.getUsername());
//        data.put("avtar_url",user.getAvatarUrl());
//        data.put("nickname", user.getNickName());
//        data.put("description", user.getDescription());//
//        data.put("industry", user.getIndustry());
//        data.put("career", user.getCareer());
//        data.put("education", user.getEducation());
//        data.put("name", user.getName());//
//        data.put("gender", user.getGender());
//        data.put("birthday", user.getBirthday());//
//        data.put("phone", user.getPhone());//
//        data.put("email", user.getEmail());
//        data.put("qq", user.getQq());//
//        data.put("role",user.getRoles());
//        return globalResponse;
//    }

    // 获取当前用户ID
    public static String getUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (authorization != null){
            String token = authorization.replace(SecurityConstants.TOKEN_PREFIX,"");
            return JwtTokenUtils.getTokenBody(token).getId();
        }
        return null;
    }


}
