package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.entity.UserQuestion;
import com.zhihu.intelligent.system.entity.Question;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.UserQuestionRepository;
import com.zhihu.intelligent.system.repository.QuestionRepository;
import com.zhihu.intelligent.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserQuestionRepository focusRepository;

    @Autowired
    private UserRepository userRepository;

    @Action(type = "CREATE", operation = "提出问题")
    public GlobalResponse save(String userId, String content, String type) {
        // 保存问题
        Question question = new Question();
        question.setUserId(userId);
        question.setContent(content);
        question.setType(type);
        question.setCreateAt(new Date());
        questionRepository.save(question);

        // 推送消息


        // 用户问题提出数+1
        User user = userRepository.findById(userId).get();
        user.setQuestions(user.getQuestions() + 1);
        userRepository.save(user);


        // 返回相应
        GlobalResponse response = new GlobalResponse(200, "问题发布成功");
        response.getData().put("questionInfo", question);
        return response;
    }

    @Action(type = "JUDGE", operation = "判断是否已关注问题")
    public GlobalResponse focusHasMe(String questionId, String userId) {

        UserQuestion userQuestion = focusRepository.findByQuestionIdAndUserId(questionId, userId);
        GlobalResponse response = new GlobalResponse(200);
        if (userQuestion == null) {
            response.setMessage("未关注问题");
            response.getData().put("hasMe", false);
        } else {
            response.setMessage("已关注问题");
            response.getData().put("hasMe", true);
        }
        return response;
    }

    @Action(type = "FOCUS", operation = "关注问题")
    public GlobalResponse follow(String questionId, String userId) {
        // 问题关注数+1
        Question question = questionRepository.findById(questionId).get();
        question.setFollowNums(question.getFollowNums() + 1);
        questionRepository.save(question);

        // 记录用户问题关注
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setQuestionId(questionId);
        userQuestion.setUserId(userId);
        userQuestion.setCreateAt(new Date());
        focusRepository.save(userQuestion);

        // 用户关注问题数+1
        User user = userRepository.findById(userId).get();
        user.setFollowQuestions(user.getFollowQuestions() + 1);
        userRepository.save(user);

        // 响应
        GlobalResponse response = new GlobalResponse(200, "关注问题成功");
        return response;
    }

    @Action(type = "DELETE", operation = "取消关注问题")
    @Transactional
    public GlobalResponse unfollow(String questionId, String userId) {
        // 问题关注数-1
        Question question = questionRepository.findById(questionId).get();
        question.setFollowNums(question.getFollowNums() - 1);
        questionRepository.save(question);

        // 删除用户问题关注记录
        focusRepository.deleteByQuestionIdAndUserId(questionId, userId);

        // 取消消息推送

        // 用户关注问题数-1
        User user = userRepository.findById(userId).get();
        user.setFollowQuestions(user.getFollowQuestions() - 1);
        userRepository.save(user);

        // 响应
        GlobalResponse response = new GlobalResponse(200, "取消关注问题成功");
        return response;
    }

    @Transactional
    @Action(type = "DELETE", operation = "删除问题")
    public GlobalResponse delete(String questionId,String userId) {
        // 删除问题
        questionRepository.deleteById(questionId);

        // 删除用户问题关注
        focusRepository.deleteByQuestionId(questionId);

        // 用户问题提出数-1
        User user = userRepository.findById(userId).get();
        user.setQuestions(user.getQuestions() + 1);
        userRepository.save(user);

        // 响应
        GlobalResponse response = new GlobalResponse(200, "删除问题成功");
        return response;
    }

    @Action(type = "READ",operation = "获取用户关注所有的问题")
    public GlobalResponse getFocusQuestionList(String userId) {
        // 获取用户关注的问题ID
        List<UserQuestion> userQuestionList = focusRepository.findByUserId(userId);
        GlobalResponse response = new GlobalResponse(200,"获取成功");
        if (userQuestionList != null) {
            // 获取问题ID集合
            List<String> questionIdList = new ArrayList<>();
            for (UserQuestion question : userQuestionList) {
                questionIdList.add(question.getQuestionId());
            }

            // 根据问题ID 查询问题信息
            List<Question> questions = questionRepository.findAllById(questionIdList);

            response.getData().put("questions",questions);
        }

        return response;
    }

}
