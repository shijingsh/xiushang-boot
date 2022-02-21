package com.xiushang.common.service;

import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.oauth.OauthClientWhiteListEntity;
import com.xiushang.jpa.repository.OauthClientWhiteListDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OauthClientWhiteListService extends BaseServiceImpl<OauthClientWhiteListEntity> {

    @Autowired
    private OauthClientWhiteListDao oauthClientWhiteListDao;


    public List<OauthClientWhiteListEntity> findByClientId(String clientId){
        List<OauthClientWhiteListEntity> list =  oauthClientWhiteListDao.findByClientId(clientId);

        return list;
    }

}
