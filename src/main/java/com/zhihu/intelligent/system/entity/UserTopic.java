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
@GenericGenerator(name = "user-topic-uuid",strategy = "uuid")
// 用户关注话题表
public class UserTopic {

    @Id
    @GeneratedValue(generator = "user-topic-uuid")
    private String id;

    @Column(name = "topic_id")
    private String topicId;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_at")
    private Date createAt;

}
