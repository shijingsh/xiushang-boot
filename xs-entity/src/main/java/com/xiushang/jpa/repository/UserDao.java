package com.xiushang.jpa.repository;

import com.xiushang.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<UserEntity> {
    UserEntity findByName(String name);
    UserEntity findByLoginName(String loginName);
}
