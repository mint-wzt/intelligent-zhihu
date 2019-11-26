package com.zhihu.intelligent.system.service;

import com.alibaba.fastjson.JSON;
import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.entity.Collection;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.ArticleRepository;
import com.zhihu.intelligent.system.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Action(type = "CREATE", operation = "添加收藏文章")
    public GlobalResponse collect(String articleId, String userId) {
        Collection collection = new Collection();
        collection.setArticleId(articleId);
        collection.setUserId(userId);
        collection.setCreateAt(new Date());
        collectionRepository.save(collection);

        GlobalResponse response = new GlobalResponse(200, "收藏成功");
        return response;
    }

    @Action(type = "JUDGE", operation = "判断是否已收藏文章")
    public GlobalResponse hasMe(String articleId, String userId) {
        Collection collection = collectionRepository.findByArticleIdAndUserId(articleId, userId);
        GlobalResponse response = new GlobalResponse(200, "操作成功");
        if (collection == null) {
            response.getData().put("hasMe", false);
        } else {
            response.getData().put("hasMe", true);
        }
        return response;
    }

    @Action(type = "DELETE", operation = "取消收藏文章")
    @Transactional
    public GlobalResponse delete(String articleId, String userId){
        collectionRepository.deleteByArticleIdAndUserId(articleId,userId);

        GlobalResponse response = new GlobalResponse(200,"操作成功");
        return response;
    }

    @Action(type = "READ",operation = "获取收藏文章")
    public GlobalResponse getArticleList(String userId){
        // 获取收藏文章ID
        List<Collection> collections = collectionRepository.findByUserId(userId);

        List<String> articleIdList = new ArrayList<>();
        GlobalResponse globalResponse = new GlobalResponse(200,"操作成功");
        if (collections != null){
            collections.forEach(collection -> articleIdList.add(collection.getArticleId()));
            List<Article> articles = articleRepository.findAllById(articleIdList);
            globalResponse.getData().put("articles",articles);
        }
        return globalResponse;
    }
}
