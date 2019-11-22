package com.zhihu.intelligent.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Log {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "login_ip")
    private String loginIP;

    @Column(name = "type")
    private String type;

    @Column(name = "operation")
    private String operation;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "remark")
    private String remark;
}
