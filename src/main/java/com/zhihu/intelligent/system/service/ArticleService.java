package com.zhihu.intelligent.system.service;

import com.alibaba.fastjson.JSON;
import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.*;
import com.zhihu.intelligent.system.exception.DeleteFailedException;
import com.zhihu.intelligent.system.exception.FormatException;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserArticleRepository userArticleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Action(type = "CREATE", operation = "发布文章")
    public GlobalResponse save(String authorId, String author, String questionId,
                               String title, String type, String content, int status) {
        log.info("发布文章: authorId:" + authorId + "| author:" + author + "|questionId:" + questionId + "|title:" + title + "|type:" + type + "|content:" + content + "|status:" + status);
        try {
            if (StringUtils.isEmpty(title.trim()) || StringUtils.isEmpty(type.trim()) || StringUtils.isEmpty(content.trim())) {
                throw new FormatException("文章数据不能为空");
            }
        } catch (Exception e) {
            log.error("文章数据不能为空");
            throw new FormatException("文章数据不能为空");
        }
        Article article = new Article();
        article.setAuthorId(authorId);
        article.setAuthor(author);
        if (questionId == "") {
            questionId = null;
        }
        article.setQuestionId(questionId);
        article.setTitle(title);
        article.setType(type);
        article.setContent(content);
        article.setStatus(status);
        article.setCreateDate(new Date());
        Article art = articleRepository.save(article);

        // 添加文章数或者回答数
        User user = userRepository.findById(authorId).get();
        if (questionId == null) {
            user.setArticles(user.getArticles() + 1);
        } else {
            user.setAnswers(user.getAnswers() + 1);
        }
        userRepository.save(user);

        GlobalResponse globalResponse = new GlobalResponse(200, "发布文章成功");
        globalResponse.getData().put("articleInfo", art);
        return globalResponse;
    }

    @Action(type = "UPDATE", operation = "修改文章")
    public GlobalResponse update(String articleId, String userId, int status, String title, String content) {
        log.info("修改文章: articleId:" + articleId + "| userId:" + userId + "|title:" + title + "|content:" + content + "|status:" + status);
        try {
            if (StringUtils.isEmpty(articleId.trim()) || StringUtils.isEmpty(userId.trim()) || StringUtils.isEmpty(title.trim()) || StringUtils.isEmpty(content.trim())) {
                throw new FormatException("修改文章信息不能为空");
            }
        } catch (Exception e) {
            log.error("修改文章信息不能为空");
            throw new FormatException("修改文章信息不能为空");
        }

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

    @Action(type = "READ", operation = "查看文章")
    public GlobalResponse getArticle(String articleId) {
        log.info("查看文章ID:" + articleId);
        Article article = articleRepository.findById(articleId).get();

        // 浏览量+1
        article.setBrowsedNums(article.getBrowsedNums() + 1);
        articleRepository.save(article);

        // 热度+1
        if (article.getQuestionId() == "") {
            Question question = questionRepository.findById(article.getQuestionId()).get();
            question.setHotNums(question.getHotNums() + 1);
            questionRepository.save(question);
        }

        GlobalResponse response = new GlobalResponse(200, "获取文章信息成功");
        response.getData().put("articleInfo", article);
        return response;
    }

    @Action(type = "DELETE", operation = "删除文章")
    @Transactional
    public GlobalResponse delete(String articleId) {
        log.info("删除文章ID:" + articleId);
        try {
            // 删除文章表里的文章
            articleRepository.deleteById(articleId);

            // 删除点赞表里的点赞记录
            userArticleRepository.deleteByArticleId(articleId);

            // 删除评论表里的评论记录
            commentRepository.deleteByArticleId(articleId);

            // 文章数或回答数-1
            Article article = articleRepository.findById(articleId).get();
            User user = userRepository.findById(article.getAuthorId()).get();
            if (article.getQuestionId() == null) {
                user.setArticles(user.getArticles() - 1);
            } else {
                user.setAnswers(user.getAnswers() - 1);
            }

        } catch (Exception e) {
            log.error("删除文章出错");
            throw new DeleteFailedException("删除文章出错");
        }


        GlobalResponse response = new GlobalResponse(200, "删除成功");
        return response;
    }

    @Action(type = "READ", operation = "判断用户是否点赞了文章")
    public GlobalResponse hasMe(String articleId, String userId) {
        log.info("判断用户是否点赞了文章:" + userId + "|" + articleId);
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
        log.info("用户ID点赞文章ID:" + userId + "|" + articleId);
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

    @Action(type = "READ", operation = "查看所有文章")
    public GlobalResponse getAllArticles(String userId) {
        log.info("用户ID查看所有文章:" + userId);

        // 获取所有文章
        List<Article> articles = articleRepository.findByAuthorIdOrderByCreateDateDesc(userId);

        GlobalResponse response = new GlobalResponse(200, "获取所有文章成功");
        response.getData().put("articles", articles);
        return response;
    }

    @Action(type = "READ", operation = "获取文章所有评论")
    public GlobalResponse getAllComments(String articleId) {
        log.info("获取文章ID所有评论:" + articleId);

        // 获取文章所有评论
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        comments.forEach(comment -> {
            User user = userRepository.findById(comment.getUserId()).get();
            comment.setNickName(user.getNickName());
        });

        GlobalResponse response = new GlobalResponse(200, "获取文章所有评论成功");
        response.getData().put("comments", comments);
        return response;
    }
}
