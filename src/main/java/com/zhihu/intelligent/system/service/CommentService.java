package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Comment;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.CommentRepository;
import com.zhihu.intelligent.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Action(type = "CREATE", operation = "发表文章评论")
    public GlobalResponse save(String articleId, String userId, String content, String commentPid) {
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCommentPid(commentPid);
        Date date = new Date();
        comment.setCreateDate(date);
        commentRepository.save(comment);
        GlobalResponse globalResponse = new GlobalResponse(201,"评论成功");
        User user = userRepository.findById(userId).get();
        globalResponse.getData().put("nickname",user.getNickName());
        globalResponse.getData().put("content",content);
        globalResponse.getData().put("create_at",date);
        return globalResponse;
    }

}
