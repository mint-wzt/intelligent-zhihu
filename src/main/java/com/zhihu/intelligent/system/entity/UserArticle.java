package com.zhihu.intelligent.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
// 用户文章点赞表
public class UserArticle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "article_id")
    private String articleId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_date")
    private Date createDate;
}
