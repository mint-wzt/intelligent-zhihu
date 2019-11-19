package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author wzt
 */
@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername( String username);

}
