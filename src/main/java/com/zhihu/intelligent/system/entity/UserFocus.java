package com.zhihu.intelligent.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@GenericGenerator(name = "user-focus-uuid",strategy = "uuid")
public class UserFocus {

    @Id
    @GeneratedValue(generator = "user-focus-uuid")
    private String id;

    @Column(name = "pos_uid")
    private String posUid;

    @Column(name = "ned_uid")
    private String negUid;

    @Column(name = "create_at")
    private Date createAt;
}
