package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
}
