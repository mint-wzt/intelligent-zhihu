package com.zhihu.intelligent.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "user")
@GenericGenerator(name = "user-uuid", strategy = "uuid")
public class User{
    @Id
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "role")
    private String roles;

    @Column(name = "permission")
    private String permissions;

    @Column(name = "status")
    private int status;

    // Date包名？
    @Column(name = "gmt_create")
    private Date createDate;

    @Column(name = "gmt_modified")
    private Date modifiedDate;

    @Column(name = "modified_uid")
    private String modifiedID;

    @Column(name = "description")
    private String description;

    @Column(name = "industry")
    private String industry;

    @Column(name = "career")
    private String career;

    @Column(name = "education")
    private String education;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "qq")
    private String qq;


//    public List<SimpleGrantedAuthority> getPermissions() {
//        List<SimpleGrantedAuthority> rolesList = new ArrayList<>();
//        Arrays.stream(roles.split(",")).forEach(role ->
//                rolesList.add(new SimpleGrantedAuthority("ROLE_" + role)));
//        return rolesList;
//    }

//    public List<SimpleGrantedAuthority> getPermisstions() {
//        List<SimpleGrantedAuthority> permissionsList = new ArrayList<>();
//        Arrays.stream(permissions.split(",")).forEach(permission ->
//                permissionsList.add(new SimpleGrantedAuthority(permission)));
//        return permissionsList;
//    }
}
