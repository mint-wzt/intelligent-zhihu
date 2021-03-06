package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.entity.UserFocus;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.UserFocusRepository;
import com.zhihu.intelligent.system.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserFocusService {

    @Autowired
    private UserFocusRepository userFocusRepository;

    @Autowired
    private UserRepository userRepository;

    @Action(type = "JUDGE", operation = "判断用户是否已关注该用户")
    public GlobalResponse hasMe(String posUid, String negUid) {
        log.info("判断用户ID是否已关注该用户ID：" + posUid + "|" + negUid);
        UserFocus userFocus = userFocusRepository.findByPosUidAndNegUid(posUid, negUid);
        GlobalResponse response = new GlobalResponse(200, "查询成功");
        if (userFocus == null) {
            response.getData().put("hasMe", false);
        } else {
            response.getData().put("hasMe", true);
        }
        return response;
    }


    @Action(type = "CREATE", operation = "关注用户")
    public GlobalResponse follow(String posUid, String negUid) {
        log.info("判断用户ID关注该用户ID：" + posUid + "|" + negUid);
        // 保存关注用户记录
        UserFocus userFocus = new UserFocus();
        userFocus.setPosUid(posUid);
        userFocus.setNegUid(negUid);
        userFocus.setCreateAt(new Date());
        userFocusRepository.save(userFocus);

        // 添加用户关注数+1
        User posUser = userRepository.findById(posUid).get();
        posUser.setFollowers(posUser.getFollowers() + 1);
        userRepository.save(posUser);

        // 被关注者粉丝+1
        User negUser = userRepository.findById(negUid).get();
        negUser.setFans(negUser.getFans() + 1);
        userRepository.save(negUser);

        GlobalResponse response = new GlobalResponse(200, "关注成功");
        return response;
    }

    @Action(type = "DELETE", operation = "取消关注用户")
    @Transactional
    public GlobalResponse unfollow(String posUid, String negUid) {
        log.info("判断用户ID取消关注该用户ID：" + posUid + "|" + negUid);
        // 保存关注用户记录
        userFocusRepository.deleteByPosUidAndNegUid(posUid, negUid);

        // 减少用户关注数-1
        User posUser = userRepository.findById(posUid).get();
        posUser.setFollowers(posUser.getFollowers() - 1);
        userRepository.save(posUser);

        // 被关注者粉丝-1
        User negUser = userRepository.findById(negUid).get();
        negUser.setFans(negUser.getFans() - 1);
        userRepository.save(negUser);

        GlobalResponse response = new GlobalResponse(200, "取消关注成功");
        return response;
    }

    @Action(type = "READ", operation = "获取用户的关注用户列表")
    public GlobalResponse getFocusUsers(String userId) {
        log.info("用户ID获取用户的关注用户列表：" + userId);
        // 获取关注用户ID
        List<UserFocus> focusList = userFocusRepository.findByPosUid(userId);

        GlobalResponse response = new GlobalResponse(200, "获取用户列表成功");
        if (focusList != null) {
            List<String> userIdList = new ArrayList<>();
            for (UserFocus userFocus : focusList) {
                userIdList.add(userFocus.getNegUid());
            }
            List<User> users = userRepository.findAllById(userIdList);
            response.getData().put("users", users);
        }
        return response;
    }

}
