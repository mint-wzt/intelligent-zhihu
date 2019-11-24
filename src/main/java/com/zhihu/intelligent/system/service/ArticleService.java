package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.entity.ArticleThumbs;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.repository.ArticleRepository;
import com.zhihu.intelligent.system.repository.ArticleThumbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleThumbRepository articleThumbRepository;

    @Action(type = "CREATE",operation = "发布文章")
    public GlobalResponse sava(Article article){
        article.setCreateDate(new Date());
        Article art = articleRepository.save(article);
        GlobalResponse globalResponse = new GlobalResponse(200,"发布文章成功");
        globalResponse.getData().put("articleInfo",art);
        return globalResponse;
    }

    @Action(type = "READ", operation = "判断用户是否点赞了文章")
    public GlobalResponse hasMe(String articleId,String userId){
        ArticleThumbs thumb = articleThumbRepository.findByArticleIdAndUserId(articleId,userId);
        GlobalResponse globalResponse = new GlobalResponse();
        globalResponse.setCode(200);
        if (thumb == null){
            globalResponse.setMessage("用户未点赞");
            globalResponse.getData().put("hasme",false);
        }else {
            globalResponse.setMessage("用户已点赞");
            globalResponse.getData().put("hasme",true);
        }
        return globalResponse;
    }

    @Action(type = "CREATE",operation = "点赞")
    public GlobalResponse thumb(String articleId,String userId){

        // 记录用点赞记录
        ArticleThumbs thumb = new ArticleThumbs();
        thumb.setArticleId(articleId);
        thumb.setUserId(userId);
        thumb.setCreateDate(new Date());
        articleThumbRepository.save(thumb);

        // 添加点赞数
        Article article = articleRepository.findById(articleId).get();
        article.setThumbs(article.getThumbs()+1);
        articleRepository.save(article);

        GlobalResponse response = new GlobalResponse(200,"点赞成功");
        return response;
    }

}
