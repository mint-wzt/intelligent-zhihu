package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Topic;
import com.zhihu.intelligent.system.exception.FormatException;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class SysTopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Action(type = "CREATE",operation = "添加话题")
    public GlobalResponse add(String name,String introduction,String createUid){
        try {
            if (StringUtils.isEmpty(name.trim())||StringUtils.isEmpty(introduction.trim())||StringUtils.isEmpty(createUid.trim())){
                throw new FormatException("添加的话题格式错误");
            }
        }catch (Exception e){
            log.error("添加的话题格式错误");
            throw new FormatException("添加的话题格式错误");
        }
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
