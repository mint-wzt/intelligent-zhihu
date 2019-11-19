package com.zhihu.intelligent.security.entity;

import com.zhihu.intelligent.common.utils.UserUtil;
import com.zhihu.intelligent.system.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author wzt
 * 包含个人信息以及角色权限，与身份验证、权限验证有关
 */
@Data
public class JwtUser implements UserDetails {

    private String id;
    private String username;
    private String password;
    private String nickName;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 通过 user 对象创建jwtUser
     */
    public JwtUser(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        authorities = UserUtil.getRoles(user);

        // 添加用户权限

        nickName = user.getNickName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickName + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
