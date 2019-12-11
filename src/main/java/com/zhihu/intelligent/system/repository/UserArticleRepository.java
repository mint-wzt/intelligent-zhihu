package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.UserArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserArticleRepository extends JpaRepository<UserArticle, Long> {

    // 根据文章ID和用户ID查询
    UserArticle findByArticleIdAndUserId(String articleId, String userId);

    void deleteByArticleId(String articleId);
}
