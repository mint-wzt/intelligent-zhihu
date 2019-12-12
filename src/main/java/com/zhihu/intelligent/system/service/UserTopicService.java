package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Topic;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.entity.UserTopic;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.TopicRepository;
import com.zhihu.intelligent.system.repository.UserRepository;
import com.zhihu.intelligent.system.repository.UserTopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserTopicService {

    @Autowired
    private UserTopicRepository userTopicRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Action(type = "JUDGE", operation = "判断用户是否关注话题")
    public GlobalResponse hasMe(String topicId, String userId) {
        log.info("判断用户ID是否关注话题ID:" + userId + "|" + topicId);
        UserTopic topic = userTopicRepository.findByTopicIdAndUserId(topicId, userId);
        GlobalResponse response = new GlobalResponse(200);
        if (topic == null) {
            response.setMessage("用户未关注话题");
            response.getData().put("hasMe", false);
        } else {
            response.setMessage("用户已关注话题");
            response.getData().put("hasMe", true);
        }
        return response;
    }

    @Action(type = "CREATE", operation = "关注话题")
    public GlobalResponse followTopic(String topicId, String name, String userId) {
        log.info("用户ID关注话题ID:" + userId + "|" + name + "|" + topicId);
        // 保存话题关注记录
        UserTopic userTopic = new UserTopic();
        userTopic.setTopicId(topicId);
        userTopic.setName(name);
        userTopic.setUserId(userId);
        userTopic.setCreateAt(new Date());
        userTopicRepository.save(userTopic);

        // 添加话题关注数
        Topic topic = topicRepository.findById(topicId).get();
        topic.setFollowNums(topic.getFollowNums() + 1);
        topicRepository.save(topic);

        // 用户关注话题 + 1
        User user = userRepository.findById(userId).get();
        user.setTopics(user.getTopics() + 1);
        userRepository.save(user);
        // 响应
        GlobalResponse response = new GlobalResponse(200, "关注成功");
        response.getData().put("topicInfo", topic);
        return response;
    }

    @Action(type = "DELETE", operation = "取消关注话题")
    @Transactional
    public GlobalResponse unfollowTopic(String topicId, String userId) {
        log.info("用户ID关注话题ID:" + userId + "|" + topicId);
        // 删除话题关注
        userTopicRepository.deleteByTopicIdAndUserId(topicId, userId);

        // 减少话题关注数
        Topic topic = topicRepository.findById(topicId).get();
        topic.setFollowNums(topic.getFollowNums() - 1);
        topicRepository.save(topic);

        // 用户关注话题-1
        User user = userRepository.findById(userId).get();
        user.setTopics(user.getTopics() - 1);
        userRepository.save(user);

        GlobalResponse response = new GlobalResponse(200, "取消关注成功");
        return response;
    }

    @Action(type = "READ", operation = "查看话题关注记录")
    public GlobalResponse getTopics(String userId) {
        log.info("用户ID查看话题关注记录:" + userId);
        // 获取关注话题记录
        List<UserTopic> topicList = userTopicRepository.findByUserId(userId);
        GlobalResponse response = new GlobalResponse(200, "查看话题关注记录成功");
        if (topicList != null) {
            List<String> topicIdList = new ArrayList<>();
            for (UserTopic userTopic : topicList) {
                topicIdList.add(userTopic.getTopicId());
            }
            List<Topic> topics = topicRepository.findAllById(topicIdList);
            response.getData().put("topicList", topics);
        }
        return response;
    }
}
