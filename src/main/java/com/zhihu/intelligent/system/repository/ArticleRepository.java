package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,String>{
    //通过用户名查找文章
    List<Article> findArticlesByAuthor(String author);


}
