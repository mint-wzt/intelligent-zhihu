package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.UserTopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/topic")
public class UserTopicController {

    @Autowired
    private UserTopicService userTopicService;

    @ApiOperation(value = "判断话题关注", notes = "判断话题关注")
    @PostMapping("/topics/hasme")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponse hasMe(@RequestParam("topic_id") String topicId,
                                @RequestParam("user_id") String userId) {
        return userTopicService.hasMe(topicId, userId);
    }

    @ApiOperation(value = "关注话题", notes = "关注话题")
    @PostMapping("/topics")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponse followTopic(@RequestParam("topic_id") String topicId,
                                      @RequestParam("name") String name,
                                      @RequestParam("user_id") String userId) {
        return userTopicService.followTopic(topicId, name, userId);
    }

    @ApiOperation(value = "取消关注话题", notes = "取消关注话题")
    @DeleteMapping("/topics")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponse unfollowTopic(@RequestParam("topic_id") String topicId,
                                        @RequestParam("user_id") String userId) {
        return userTopicService.unfollowTopic(topicId, userId);
    }

    @ApiOperation(value = "查看话题关注记录", notes = "查看话题关注记录")
    @GetMapping("/topics")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponse getTopics(@RequestParam("user_id") String userId) {
        return userTopicService.getTopics(userId);
    }

}
