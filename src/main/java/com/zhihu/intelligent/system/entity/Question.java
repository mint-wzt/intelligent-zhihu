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
@GenericGenerator(name = "question-uuid",strategy = "uuid")
public class Question {

    @Id
    @GeneratedValue(generator = "question-uuid")
    private String id;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "answer_nums")
    private int answerNums;

    @Column(name = "follow_nums")
    private int followNums;

    @Column(name = "hot_nums")
    private int hotNums;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @Column(name = "modified_uid")
    private String modifiedId;
}
