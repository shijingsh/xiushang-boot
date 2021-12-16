package com.xiushang.jpa.repository;

import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OauthClientDetailsDao extends BaseDao<OauthClientDetailsEntity> {

    OauthClientDetailsEntity findByClientId(String clientId);

    List<OauthClientDetailsEntity> findByUserId(String userId);
}
