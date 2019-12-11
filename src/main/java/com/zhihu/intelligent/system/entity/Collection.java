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
@GenericGenerator(name = "collection-uuid",strategy = "uuid")
public class Collection {

    @Id
    @GeneratedValue(generator = "collection-uuid")
    private String id;

    @Column(name = "article_id")
    private String articleId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_at")
    private Date createAt;

}
