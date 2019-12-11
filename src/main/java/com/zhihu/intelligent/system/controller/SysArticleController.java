package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.SysArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/article")
public class SysArticleController {

    @Autowired
    private SysArticleService sysArticleService;

    @ApiOperation(value = "获取所有文章信息", notes = "获取所有文章信息")
    @GetMapping("/articles")
    @PreAuthorize("hasAnyRole('ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse getAllArticles() {
        return sysArticleService.getAllArticles();
    }

    @ApiOperation(value = "删除文章信息", notes = "删除文章信息")
    @DeleteMapping("/articles")
    @PreAuthorize("hasAnyRole('ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse deleteArticles(@RequestParam("articleId") String articleId) {
        return sysArticleService.delete(articleId);
    }

}
