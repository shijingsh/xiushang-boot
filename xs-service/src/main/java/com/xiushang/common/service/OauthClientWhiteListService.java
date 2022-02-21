package com.xiushang.common.service;

import com.xiushang.common.user.mapper.OauthClientWhiteListSaveMapper;
import com.xiushang.common.user.vo.OauthClientWhiteListSaveVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.entity.oauth.OauthClientWhiteListEntity;
import com.xiushang.framework.log.MethodResult;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.jpa.repository.OauthClientWhiteListDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OauthClientWhiteListService extends BaseServiceImpl<OauthClientWhiteListEntity> {

    @Autowired
    private OauthClientWhiteListDao oauthClientWhiteListDao;
    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;

    public List<OauthClientWhiteListEntity> findByClientId(String clientId){
        List<OauthClientWhiteListEntity> list =  oauthClientWhiteListDao.findByClientId(clientId);

        return list;
    }

    public MethodResult<OauthClientWhiteListEntity> saveClientWhiteList(OauthClientWhiteListSaveVo clientWhiteListSaveVo) {
        String userId = userService.getCurrentUserId();

        OauthClientWhiteListEntity oauthClientWhiteListDb = null;
        OauthClientWhiteListEntity oauthClientWhiteListEntity = OauthClientWhiteListSaveMapper.INSTANCE.targetToSource(clientWhiteListSaveVo);

        OauthClientDetailsEntity clientDetails = oauthClientDetailsDao.findByClientId(clientWhiteListSaveVo.getClientId());

        if(StringUtils.isNotBlank(clientWhiteListSaveVo.getId())){
            oauthClientWhiteListDb = get(clientWhiteListSaveVo.getId());
            //修改客户端时，判断修改权限
            if(!clientDetails.getUserId().equals(userId)){
                return MethodResult.error("无权修改客户端信息！");
            }
            oauthClientWhiteListEntity.setClientId(oauthClientWhiteListDb.getClientId());
        }

        if(StringUtils.isBlank(clientWhiteListSaveVo.getClientId())){
            return MethodResult.error(" clientId 不能为空!");
        }
        if(clientWhiteListSaveVo.getType()==null || (clientWhiteListSaveVo.getType()!=1 && clientWhiteListSaveVo.getType()!=2)){
            return MethodResult.error(" 白名单类型 1为ip白名单 2 为域名白名单，请正确填写!");
        }

        oauthClientWhiteListEntity =  saveAndGet(oauthClientWhiteListEntity);

        return MethodResult.success(oauthClientWhiteListEntity);

    }
}
