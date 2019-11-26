package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Topic;
import com.zhihu.intelligent.system.entity.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTopicRepository extends JpaRepository<UserTopic, String> {

    // 通过topicID和userId查找
    UserTopic findByTopicIdAndUserId(String topicId, String userId);

    // 删除话题关注
    void deleteByTopicIdAndUserId(String topicId, String userId);

    // 查找关注话题记录
    List<UserTopic> findByUserId(String userId);

}
