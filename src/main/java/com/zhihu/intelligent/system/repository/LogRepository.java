package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.Log;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log,Long> {

}
