package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.CollectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @ApiOperation(value = "是否收藏文章", notes = "是否收藏文章")
    @PostMapping("/collections/hasme")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse hasMe(@RequestParam("articleId") String articleId,
                                     @RequestParam("userId") String userId) {
        return collectionService.hasMe(articleId,userId);
    }

    @ApiOperation(value = "收藏文章", notes = "收藏文章")
    @PostMapping("/collections")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse collect(@RequestParam("articleId") String articleId,
                                  @RequestParam("userId") String userId) {
        return collectionService.collect(articleId,userId);
    }

    @ApiOperation(value = "取消收藏文章", notes = "取消收藏文章")
    @DeleteMapping("/collections")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse delete(@RequestParam("articleId") String articleId,
                                  @RequestParam("userId") String userId) {
        return collectionService.delete(articleId,userId);
    }

    @ApiOperation(value = "获取收藏文章", notes = "获取收藏文章")
    @GetMapping("/collections")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse getArticles(@RequestParam("userId") String userId) {
        return collectionService.getArticleList(userId);
    }

}
