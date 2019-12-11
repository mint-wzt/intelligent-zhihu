package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SysArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Action(type = "READ",operation = "管理员获取所有文章")
    public GlobalResponse getAllArticles(){

        List<Article> articles = articleRepository.findByOrderByBrowsedNumsDesc();
        GlobalResponse response = new GlobalResponse(200,"获取文章成功");
        response.getData().put("articles",articles);
        return response;
    }

    @Action(type = "DELETE",operation = "管理员删除文章")
    @Transactional
    public GlobalResponse delete(String articleId){
        articleRepository.deleteById(articleId);

        GlobalResponse response = new GlobalResponse(200,"删除文章成功");
        return response;
    }
}
