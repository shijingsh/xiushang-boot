package com.xiushang.jpa.repository;

import com.xiushang.entity.UserSocialEntity;
import com.xiushang.util.SocialTypeEnum;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSocialDao extends BaseDao<UserSocialEntity> {
    UserSocialEntity findBySocialTypeAndSocialId(SocialTypeEnum socialType, String socialId);
}
