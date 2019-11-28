package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    List<Article> findByOrderByBrowsedNumsDesc();

    //通过用户名查找文章
    List<Article> findArticlesByAuthor(String author);


//    List<Article> findByTitleLikeOrderByCreateDateDesc(String keyWord);

    @Query(value = "select art from Article art where art.title like concat('%',:keyWord,'%') order by browsed_nums desc ")
    List<Article> findArticlesWithPartOfTitle(@Param("keyWord") String keyWord);

//    @Query(value = "select art from article art where art.author_id in(?1) order by art.gmt_create desc ")
//    List<Article> findAuthor(String[] authorId);

    List<Article> findByAuthorIdInOrderByCreateDateDesc(String[] authorId);

    @Query(value = "select art from Article art where art.type like concat('%',:type,'%') order by browsed_nums desc ")
    List<Article> findArticlesWithPartOfType(@Param("type") String type);

//    List<Article> findByTypeLikeOrderByBrowsedNumsDesc(String type);

//    // 获取回答数
//    int countByAuthorIdAndQuestionIdNull(String authorId);

//    // 获取文章数
//    int countByAuthorIdAndQuestionIdNotNull(String authorId);

}
