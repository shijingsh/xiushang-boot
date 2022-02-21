package com.xiushang.jpa.repository;

import com.xiushang.entity.oauth.OauthClientWhiteListEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OauthClientWhiteListDao extends BaseDao<OauthClientWhiteListEntity> {

    List<OauthClientWhiteListEntity> findByClientId(String clientId);

}
