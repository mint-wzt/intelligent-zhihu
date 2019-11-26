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
@GenericGenerator(name = "focus-question-uuid",strategy = "uuid")
// 用户关注问题表
public class UserQuestion {

    @Id
    @GeneratedValue(generator = "focus-question-uuid")
    private String id;

    @Column(name = "question_id")
    private String questionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_at")
    private Date createAt;
}
