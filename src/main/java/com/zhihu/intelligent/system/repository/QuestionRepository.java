package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Article;
import com.zhihu.intelligent.system.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

}
