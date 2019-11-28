package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author wzt
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    @Query(value = "select u from User u where u.username like concat('%',:username,'%')")
    List<User> findUsersWithPartOfUserName(@Param("username") String username);

    @Query(value = "select u from User u where u.roles like concat('%',:role,'%')")
    List<User> findUsersWithPartOfRoles(@Param("role") String role);

}
