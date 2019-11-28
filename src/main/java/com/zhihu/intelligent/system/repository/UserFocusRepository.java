package com.zhihu.intelligent.system.repository;

import com.zhihu.intelligent.system.entity.UserFocus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFocusRepository extends JpaRepository<UserFocus, String> {

    // 查询关注者与被关注者
    UserFocus findByPosUidAndNegUid(String posUid, String negUid);

    void deleteByPosUidAndNegUid(String posUid, String negUid);

    // 获取关注列表
    List<UserFocus> findByPosUid(String userId);

    // 获取关注的人数
//    int countByPosUid(String posUid);

    // 获取粉丝数
//    int countByNegUid(String negUid);
}
