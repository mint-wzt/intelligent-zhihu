package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {

    // 查询记录
    Collection findByArticleIdAndUserId(String articleId, String userId);

    void deleteByArticleIdAndUserId(String articleId, String userId);

    List<Collection> findByUserId(String userId);
}
