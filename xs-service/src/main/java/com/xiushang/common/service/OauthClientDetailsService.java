package com.xiushang.common.service;

import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OauthClientDetailsService {

    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;

    public OauthClientDetailsEntity findByClientId(String clientId){
        return  oauthClientDetailsDao.findByClientId(clientId);
    }

    public List<OauthClientDetailsEntity> findByUserId(String userId){
        return  oauthClientDetailsDao.findByUserId(userId);
    }
}