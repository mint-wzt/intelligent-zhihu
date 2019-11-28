package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.ArticleService;
import com.zhihu.intelligent.system.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "发布文章", notes = "发布文章")
    @PostMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CREATE')")
    public GlobalResponse publishArticle(@RequestParam("authorId") String authorId,
                                         @RequestParam("author") String author,
                                         @RequestParam(value = "questionId", defaultValue = "") String questionId,
                                         @RequestParam("title") String title,
                                         @RequestParam("type") String type,
                                         @RequestParam("content") String content,
                                         @RequestParam("status") int status) {
        return articleService.save(authorId, author, questionId, title, type, content, status);
    }

    @ApiOperation(value = "修改文章", notes = "修改文章")
    @PutMapping("/articles")
    @PreAuthorize("hasRole('CREATE')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse editArticle(@RequestParam("articleId") String articleId,
                                      @RequestParam("userId") String userId,
                                      @RequestParam("status") int status,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content) {
        return articleService.update(articleId, userId, status, title, content);
    }
    @ApiOperation(value = "获取文章", notes = "获取文章")
    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse getArticle(@RequestParam("articleId") String articleId){
        return articleService.getArticle(articleId);
    }

    @ApiOperation(value = "用户删除文章", notes = "用户删除文章")
    @DeleteMapping("/articles")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse deleteArticle(@RequestParam("articleId") String articleId) {
        return articleService.delete(articleId);
    }

    @ApiOperation(value = "判断用户是否对文章点赞", notes = "判断用户是否对文章点赞")
    @PostMapping("/thumbs/hasme")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    public GlobalResponse thumbHasMe(@RequestParam("articleId") String articleId,
                                     @RequestParam("userId") String userId) {
        return articleService.hasMe(articleId, userId);
    }

    @ApiOperation(value = "点赞", notes = "点赞")
    @PostMapping("/thumbs")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    public GlobalResponse thumb(@RequestParam("articleId") String articleId,
                                @RequestParam("userId") String userId) {
        return articleService.thumb(articleId, userId);
    }

    @ApiOperation(value = "文章评论", notes = "文章评论")
    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    public GlobalResponse comment(@RequestParam("articleId") String articleId,
                                  @RequestParam("userId") String userId,
                                  @RequestParam("content") String content,
                                  @RequestParam(value = "commentPid", defaultValue = "") String commentPid) {
        return commentService.save(articleId, userId, content, commentPid);
    }


}
