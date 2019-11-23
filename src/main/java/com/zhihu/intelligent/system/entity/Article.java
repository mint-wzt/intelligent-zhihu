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
@GenericGenerator(name = "article-uuid",strategy = "uuid")
public class Article {

    @Id
    @GeneratedValue(generator = "article-uuid")
    @Column(name = "id")
    private String id;

//    @Column(name = "topic_id")
//    private String topicId;

    @Column(name = "is_article")
    private int isArticle;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "author")
    private String author;

    @Column(name = "author_id")
    private String authorId;

//    @Column(name = "article_url")
//    private String articleUrl;

    @Column(name = "content")
    private String content;


    @Column(name = "status")
    private String status;

    @Column(name = "thumbs")
    private int thumbs;

    @Column(name = "browsed_nums")
    private int browsedNums;

    @Column(name = "shared_nums")
    private int sharedNums;

    @Column(name = "collection_nums")
    private int collectionNums;

    @Column(name = "gmt_create")
    private Date createDate;

    @Column(name = "gmt_modified")
    private Date modifiedDate;

    @Column(name = "modified_uid")
    private String modifiedId;

}
