package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.entity.UserArticle;
import com.zhihu.intelligent.system.exception.DeleteFailedException;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.ArticleRepository;
import com.zhihu.intelligent.system.repository.UserArticleRepository;
import com.zhihu.intelligent.system.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserArticleRepository userArticleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Action(type = "CREATE", operation = "发布文章")
    public GlobalResponse save(String authorId, String author, String questionId,
                               String title, String type, String content, int status) {
        Article article = new Article();
        article.setAuthorId(authorId);
        article.setAuthor(author);
        article.setQuestionId(questionId);
        article.setTitle(title);
        article.setType(type);
        article.setContent(content);
        article.setStatus(status);
        article.setCreateDate(new Date());
        Article art = articleRepository.save(article);

        GlobalResponse globalResponse = new GlobalResponse(200, "发布文章成功");
        globalResponse.getData().put("articleInfo", art);
        return globalResponse;
    }

    @Action(type = "UPDATE", operation = "修改文章")
    public GlobalResponse update(String articleId, String userId, int status, String title, String content) {
        // 保存修改文章
        Article article = articleRepository.findById(articleId).get();
        article.setModifiedId(userId);
        article.setStatus(status);
        article.setTitle(title);
        article.setContent(content);
        article.setModifiedDate(new Date());
        articleRepository.save(article);

        // 返回响应
        GlobalResponse response = new GlobalResponse(200, "修改成功");
        response.getData().put("articleInfo", article);
        return response;
    }

    @Action(type = "READ",operation = "查看文章")
    public GlobalResponse getArticle(String articleId){
        Article article = articleRepository.findById(articleId).get();

        // 浏览量+1
        article.setBrowsedNums(article.getBrowsedNums()+1);
        articleRepository.save(article);

        GlobalResponse response = new GlobalResponse(200,"获取文章信息成功");
        response.getData().put("articleInfo",article);
        return response;
    }

    @Action(type = "DELETE", operation = "删除文章")
    @Transactional
    public GlobalResponse delete(String articleId) {
        try {
            // 删除文章表里的文章
            articleRepository.deleteById(articleId);

            // 删除点赞表里的点赞记录
            userArticleRepository.deleteByArticleId(articleId);

            // 删除评论表里的评论记录
            commentRepository.deleteByArticleId(articleId);
        } catch (Exception e) {
            throw new DeleteFailedException("删除出错");
        }


        GlobalResponse response = new GlobalResponse(200, "删除成功");
        return response;
    }

    @Action(type = "READ", operation = "判断用户是否点赞了文章")
    public GlobalResponse hasMe(String articleId, String userId) {
        UserArticle thumb = userArticleRepository.findByArticleIdAndUserId(articleId, userId);
        GlobalResponse globalResponse = new GlobalResponse();
        globalResponse.setCode(200);
        if (thumb == null) {
            globalResponse.setMessage("用户未点赞");
            globalResponse.getData().put("hasme", false);
        } else {
            globalResponse.setMessage("用户已点赞");
            globalResponse.getData().put("hasme", true);
        }
        return globalResponse;
    }

    @Action(type = "CREATE", operation = "点赞")
    public GlobalResponse thumb(String articleId, String userId) {

        // 记录用点赞记录
        UserArticle thumb = new UserArticle();
        thumb.setArticleId(articleId);
        thumb.setUserId(userId);
        thumb.setCreateDate(new Date());
        userArticleRepository.save(thumb);

        // 添加点赞数
        Article article = articleRepository.findById(articleId).get();
        article.setThumbs(article.getThumbs() + 1);
        articleRepository.save(article);

        GlobalResponse response = new GlobalResponse(200, "点赞成功");
        return response;
    }

}
