package com.zhihu.intelligent.common.utils;

import com.zhihu.intelligent.security.model.JwtUser;
import com.zhihu.intelligent.system.entity.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static List<SimpleGrantedAuthority> getRoles(User user) {
        String roles = user.getRoles();
        List<SimpleGrantedAuthority> rolesList = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role ->
                rolesList.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return rolesList;
    }

    //获取当前认证的用户
    public static JwtUser getJwtUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null || authentication.getPrincipal() != null){
            return (JwtUser) authentication.getPrincipal();
        }
        return null;
    }
}
