package com.zhihu.intelligent.security.config;

import com.zhihu.intelligent.common.filter.JWTAuthenticationFilter;
import com.zhihu.intelligent.common.filter.JWTAuthorizationFilter;
import com.zhihu.intelligent.security.exception.JWTAccessDeniedHandler;
import com.zhihu.intelligent.security.exception.JWTAuthenticationEntryPoint;
import com.zhihu.intelligent.system.service.LogService;
import com.zhihu.intelligent.system.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private LogService logService;

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/auth/login").permitAll()
                // 指定路径下的资源需要验证了的用户才能访问
                //.antMatchers("/api/**").authenticated()
                //.antMatchers(HttpMethod.DELETE,"/api/**").hasAnyRole("ADMIN","ROOT")
                .anyRequest().permitAll()
                .and()
                .logout().logoutUrl("/auth/logout").permitAll()
                .and()
                //添加自定义Filter
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),logService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 授权异常处理
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(new JWTAccessDeniedHandler());

        // 防止H2 web 页面的Frame 被拦截
        http.headers().frameOptions().disable();
    }


}
