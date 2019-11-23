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
@GenericGenerator(name = "image-uuid",strategy = "uuid")
public class Image {

    @Id
    @GeneratedValue(generator = "image-uuid")
    private String id;

//    @Column(name = "article_id")
//    private String articleId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "format")
    private String format;

    @Column(name = "size")
    private long size;

    @Column(name = "gmt_create")
    private Date createDate;

}
