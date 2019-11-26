package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuestionRepository extends JpaRepository<UserQuestion,String> {

    // 查找问题关注记录
    UserQuestion findByQuestionIdAndUserId(String questionId, String userId);

    // 根据问题ID和用户ID 删除记录
    void deleteByQuestionIdAndUserId(String questionId,String userId);

    // 根据问题ID删除记录
    void deleteByQuestionId(String questionId);

    // 查询用户关注的所有问题
    List<UserQuestion> findByUserId(String userId);
}
