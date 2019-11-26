package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "提出问题", notes = "提出问题")
    @PostMapping("/questions")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse publish(@RequestParam("userId") String userId,
                                  @RequestParam("content") String content,
                                  @RequestParam("type") String type) {
        return questionService.save(userId, content, type);
    }

    @ApiOperation(value = "是否关注问题", notes = "是否关注问题")
    @PostMapping("/questions/hasme")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse focusHasMe(@RequestParam("questionId") String questionId,
                                     @RequestParam("userId") String userId) {
        return questionService.focusHasMe(questionId, userId);
    }

    @ApiOperation(value = "关注问题", notes = "关注问题")
    @PostMapping("/questions/follow")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse follow(@RequestParam("questionId") String questionId,
                                 @RequestParam("userId") String userId) {
        return questionService.follow(questionId, userId);
    }


    @ApiOperation(value = "取消关注问题", notes = "取消关注问题")
    @DeleteMapping("/questions/unfollow")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse unfollow(@RequestParam("questionId") String questionId,
                                 @RequestParam("userId") String userId) {
        return questionService.unfollow(questionId, userId);
    }

    @ApiOperation(value = "删除问题", notes = "删除问题")
    @DeleteMapping("/questions")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse delete(@RequestParam("questionId") String questionId) {
        return questionService.delete(questionId);
    }

    @ApiOperation(value = "获取用户关注的问题", notes = "获取用户关注的问题")
    @GetMapping("/questions")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse getQusetions(@RequestParam("userId") String userId){
        return questionService.getFocusQuestionList(userId);
    }


}
