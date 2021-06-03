package com.xiushang.common.user.dao;

import com.xiushang.common.entity.UserEntity;
import com.xiushang.common.utils.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<UserEntity> {
    UserEntity findByName(String name);
}
