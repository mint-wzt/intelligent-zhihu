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
@GenericGenerator(name = "topic-uuid",strategy = "uuid")
public class Topic {

    @Id
    @GeneratedValue(generator = "topic-uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "follow_nums")
    private int followNums;

    @Column(name = "discuss_nums")
    private int discussNums;

    @Column(name = "create_uid")
    private String createUid;

    @Column(name = "create_at")
    private Date createAt;

}
