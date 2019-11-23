package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,String> {

    //通过文章id来删除图片
    void deleteImageByUserId(String id);
}
