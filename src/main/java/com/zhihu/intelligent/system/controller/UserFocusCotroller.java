package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.UserFocusService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_focus")
public class UserFocusCotroller {

    @Autowired
    private UserFocusService userFocusService;

    @ApiOperation(value = "判断是否已关注用户", notes = "判断是否已关注用户")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/hasme")
    public GlobalResponse hasMe(@RequestParam("posUid") String posUid,
                                @RequestParam("negUid") String negUid) {
        return userFocusService.hasMe(posUid, negUid);
    }

    @ApiOperation(value = "关注用户", notes = "关注用户")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/follow")
    public GlobalResponse follow(@RequestParam("posUid") String posUid,
                                 @RequestParam("negUid") String negUid) {
        return userFocusService.follow(posUid, negUid);
    }

    @ApiOperation(value = "取消关注用户", notes = "取消关注用户")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/unfollow")
    public GlobalResponse unfollow(@RequestParam("posUid") String posUid,
                                   @RequestParam("negUid") String negUid) {
        return userFocusService.unfollow(posUid, negUid);
    }

    @ApiOperation(value = "用户的关注用户列表", notes = "用户的关注用户列表")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public GlobalResponse getFocusUsers(@RequestParam("userId") String userId) {
        return userFocusService.getFocusUsers(userId);
    }

}
