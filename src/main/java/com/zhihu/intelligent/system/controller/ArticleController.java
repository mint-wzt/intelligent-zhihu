package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "发布文章",notes = "发布文章")
    @PostMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CREATE')")
    public String publisArticle(@RequestBody Article article){
        return articleService.sava(article);
    }




}
