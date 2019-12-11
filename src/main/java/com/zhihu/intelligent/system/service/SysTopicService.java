package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Topic;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class SysTopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Action(type = "CREATE",operation = "添加话题")
    public GlobalResponse add(String name,String introduction,String createUid){
        // 添加话题
        Topic topic = new Topic();
        topic.setName(name);
        topic.setIntroduction(introduction);
        topic.setCreateUid(createUid);
        topic.setCreateAt(new Date());
        topicRepository.save(topic);

        GlobalResponse response = new GlobalResponse(200,"添加话题成功");
        return response;
    }

    @Action(type = "READ",operation = "获取所有话题")
    public GlobalResponse getAllTopics(){
        List<Topic> topics = topicRepository.findAll();
        GlobalResponse response = new GlobalResponse(200,"操作成功");
        response.getData().put("topics",topics);
        return response;
    }


}
