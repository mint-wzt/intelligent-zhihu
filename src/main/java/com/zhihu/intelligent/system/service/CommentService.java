package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.entity.Comment;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.exception.FormatException;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.ArticleRepository;
import com.zhihu.intelligent.system.repository.CommentRepository;
import com.zhihu.intelligent.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Action(type = "CREATE", operation = "发表文章评论")
    public GlobalResponse save(String articleId, String userId, String content, String commentPid) {

        try {
            if (StringUtils.isEmpty(content.trim())) {
                throw new FormatException("评论内容不能为空");
            }
        } catch (Exception e) {
            throw new FormatException("评论内容不能为空");
        }

        // 保存评论
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCommentPid(commentPid);
        Date date = new Date();
        comment.setCreateDate(date);
        commentRepository.save(comment);

        // 给文章添加评论数
        Article article = articleRepository.findById(articleId).get();
        article.setCommentNums(article.getCommentNums() + 1);
        articleRepository.save(article);

        // 返回响应
        GlobalResponse globalResponse = new GlobalResponse(201, "评论成功");
        User user = userRepository.findById(userId).get();
        globalResponse.getData().put("nickname", user.getNickName());
        globalResponse.getData().put("content", content);
        globalResponse.getData().put("create_at", date);
        return globalResponse;
    }

}
