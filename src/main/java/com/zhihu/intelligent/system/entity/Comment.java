package com.zhihu.intelligent.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
@GenericGenerator(name = "comment-uuid",strategy = "uuid")
public class Comment {

    @Id
    @GeneratedValue(generator = "comment-uuid")
    private String id;

    @Column(name = "article_id")
    private String articleId;

    @Column(name = "comment_pid")
    private String commentPid;

    @Column(name = "content")
    private String content;

    @Column(name = "display_name")
    private String nickName;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "thumbs")
    private int thumbs;

    @Column(name = "gmt_create")
    private Date createDate;

    @Column(name = "modified_uid")
    private String modifiedUid;

    @Column(name = "gmt_modified")
    private Date modifiedDate;

}
