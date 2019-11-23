package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Action(type = "CREATE",operation = "发布文章")
    public String sava(Article article){
        article.setCreateDate(new Date());
        Article art = articleRepository.save(article);
        GlobalResponse globalResponse = new GlobalResponse(200,"发布文章成功");
        globalResponse.getData().put("articleId",art.getId());
        return globalResponse.toString();
    }

}
