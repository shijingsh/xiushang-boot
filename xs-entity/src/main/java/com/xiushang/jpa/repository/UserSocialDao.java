package com.xiushang.jpa.repository;

import com.xiushang.entity.UserSocialEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSocialDao extends BaseDao<UserSocialEntity> {
    UserSocialEntity findBySocialTypeAndSocialId(String socialType,String socialId);
}
