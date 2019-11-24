package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.ArticleThumbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleThumbRepository extends JpaRepository<ArticleThumbs, Long> {

    // 根据文章ID和用户ID查询
    ArticleThumbs findByArticleIdAndUserId(String articleId, String userId);
}
