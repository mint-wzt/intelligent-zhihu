package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.entity.Question;
import com.zhihu.intelligent.system.entity.UserFocus;
import com.zhihu.intelligent.system.entity.UserTopic;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.ArticleRepository;
import com.zhihu.intelligent.system.repository.QuestionRepository;
import com.zhihu.intelligent.system.repository.UserFocusRepository;
import com.zhihu.intelligent.system.repository.UserTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserFocusRepository userFocusRepository;

    @Autowired
    private UserTopicRepository userTopicRepository;

     // 推荐
    public GlobalResponse recommend(String userId) {
        GlobalResponse globalResponse = new GlobalResponse(200, "操作成功");
        List<Article> articles = new ArrayList<>();
        if (userId.length() == 0 || userId == null) {
            // 推荐 浏览量多的文章
            articles = articleRepository.findAll(Sort.by("browsedNums").descending());
        } else {
            // 如果有关注的话题，则把包含这些话题的文章都找出来
            List<UserTopic> userTopics = userTopicRepository.findByUserId(userId);

            if (userTopics != null) {
                for (UserTopic userTopic : userTopics){
                    System.out.println(userTopic.getName());
                    List<Article> articleList = articleRepository.findArticlesWithPartOfType(userTopic.getName());
                    if (articleList != null){
                        articles.addAll(articleList);
                    }
                }
            }else {
                articles.addAll(articleRepository.findAll(Sort.by("browsedNums").descending()));
            }
        }
        globalResponse.getData().put("articles",articles);
        return globalResponse;
    }

    // 关注
    public GlobalResponse follow(String userId) {
        GlobalResponse response = new GlobalResponse(200, "操作成功");
        if (userId.length() == 0 || userId == null) {
            // 推荐 浏览量多的文章
            List<Article> articles = articleRepository.findAll(Sort.by("browsedNums").descending());
            response.getData().put("articles", articles);
        } else {
            // 获取用户的关注用户发表的文章
            List<UserFocus> focusList = userFocusRepository.findByPosUid(userId);
            List<Article> articleList;
            if (focusList != null) {
                List<String> usersId = new ArrayList<>();
                focusList.forEach(userFocus -> usersId.add(userFocus.getNegUid()));
//                String articles = StringUtils.collectionToDelimitedString(usersId, ",");
                String[] articles = usersId.toArray(new String[usersId.size()]);
                articleList = articleRepository.findByAuthorIdInOrderByCreateDateDesc(articles);

            }else {
                articleList = articleRepository.findAll(Sort.by("browsedNums").descending());
            }
            response.getData().put("articles", articleList);
        }
        return response;
    }

    // 热点
    public GlobalResponse hot() {
        List<Question> questions = questionRepository.findAll(Sort.by("hotNums").descending());

        GlobalResponse response = new GlobalResponse(200, "操作成功 ");
        if (questions != null) {
            response.getData().put("questions", questions);
        }
        return response;
    }


    // 搜索
    public GlobalResponse search(String keyWord) {

        List<Article> articles = articleRepository.findArticlesWithPartOfTitle(keyWord);
        GlobalResponse response = new GlobalResponse(200, "操作成功");
        System.out.println(articles == null);
        if (articles.size() != 0) {
            response.getData().put("articles", articles);
        }
        return response;
    }


}
