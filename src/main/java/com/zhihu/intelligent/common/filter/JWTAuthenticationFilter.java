package com.zhihu.intelligent.common.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhihu.intelligent.common.constants.SecurityConstants;
import com.zhihu.intelligent.common.utils.JwtTokenUtils;
import com.zhihu.intelligent.common.utils.LogUtil;
import com.zhihu.intelligent.common.utils.UserUtil;
import com.zhihu.intelligent.security.model.JwtUser;
import com.zhihu.intelligent.security.model.LoginUser;
import com.zhihu.intelligent.system.entity.Log;
import com.zhihu.intelligent.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 如果用户名和密码正确，那么过滤器将创建一个JWT Token 并在HTTP Response 的header中返回它，格式：token: "Bearer +具体token值"
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private AuthenticationManager authenticationManager;
    private LogService logService;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,LogService logService){
        this.authenticationManager = authenticationManager;
        this.logService = logService;
        // 设置URL，以确定是否需要身份验证
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 从输入流中获取到登录的信息
            LoginUser loginUser = objectMapper.readValue(request.getInputStream(),LoginUser.class);
            rememberMe.set(loginUser.getRememberMe());
            // 这部分和attemptAuthentication方法中的源码是一样的，
            // 只不过由于这个方法源码的是把用户名和密码这些参数的名字是死的，所以我们重写了一下
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginUser.getUsername(),loginUser.getPassword());
            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 如果验证成功，就生成token并返回
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        List<String> roles = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Log log = LogUtil.createLog(request,"LOGIN","用户登录成功",null);
        if (jwtUser != null){
            log.setUserId(jwtUser.getId());
            log.setUsername(jwtUser.getUsername());
        }
        logService.save(log);

        //创建Token
        String token = JwtTokenUtils.createToken(jwtUser,roles,rememberMe.get());
        // Http Response Header 中返回 Token
        response.setHeader(SecurityConstants.TOKEN_HEADER,token);
    }

    /**
     * 验证失败调用
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Log log = LogUtil.createLog(request,"LOGIN","用户登录失败",null);
        logService.save(log);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,failed.getMessage());
    }
}

