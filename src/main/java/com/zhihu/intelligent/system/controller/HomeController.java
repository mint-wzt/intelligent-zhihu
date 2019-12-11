package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.HomeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @ApiOperation(value = "推荐文章", notes = "推荐文章")
    @GetMapping("/article/recommend")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse recommend(@RequestParam(value = "userId", defaultValue = "") String userId) {
        return homeService.recommend(userId);
    }


    @ApiOperation(value = "关注", notes = "关注")
    @GetMapping("/article/follow")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse follow(@RequestParam(value = "userId", defaultValue = "") String userId) {
        return homeService.follow(userId);
    }


    @ApiOperation(value = "热榜", notes = "热榜")
    @GetMapping("/question/hot")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse hot() {
        return homeService.hot();
    }

    @ApiOperation(value = "搜索", notes = "搜索")
    @GetMapping("/article/search")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse search(@RequestParam("keyWord") String keyWord) {
        return homeService.search(keyWord);
    }


}
