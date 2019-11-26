package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic,String> {
}
