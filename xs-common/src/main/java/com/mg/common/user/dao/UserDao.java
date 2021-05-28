package com.mg.common.user.dao;

import com.mg.common.entity.UserEntity;
import com.mg.common.utils.BaseDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends BaseDao<UserEntity> {
    UserEntity findByName(String name);
}
