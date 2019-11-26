package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.SysTopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/topic")
public class SysTopicController {

    @Autowired
    private SysTopicService topicService;

    @ApiOperation(value = "添加话题", notes = "添加话题")
    @PostMapping("/topics")
    @PreAuthorize("hasAnyRole('ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse add(@RequestParam("name")String name,
                              @RequestParam("introduction") String introduction,
                              @RequestParam("create_uid") String createUid){
        return topicService.add(name,introduction,createUid);
    }
}
