package com.xiushang.common.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.user.mapper.OauthClientDetailsSaveMapper;
import com.xiushang.common.user.vo.OauthClientDetailsSaveVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.common.utils.LazyLoadUtil;
import com.xiushang.common.utils.ValidPassword;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.entity.oauth.QOauthClientDetailsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.log.MethodResult;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.util.ClientTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OauthClientDetailsService extends BaseServiceImpl<OauthClientDetailsEntity> {

    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public OauthClientDetailsEntity findByClientId(String clientId){
        OauthClientDetailsEntity clientDetailsEntity =  oauthClientDetailsDao.findByClientId(clientId);

        return clientDetailsEntity;
    }

    public MethodResult<OauthClientDetailsEntity> saveClient(OauthClientDetailsSaveVo clientDetailsSaveVo) {
        String userId = userService.getCurrentUserId();

        OauthClientDetailsEntity clientDetailsDb = null;
        OauthClientDetailsEntity oauthClientDetailsEntity = OauthClientDetailsSaveMapper.INSTANCE.targetToSource(clientDetailsSaveVo);

        if(StringUtils.isNotBlank(clientDetailsSaveVo.getId())){
            clientDetailsDb = get(clientDetailsSaveVo.getId());
            //修改客户端时，判断修改权限
            if(!clientDetailsDb.getUserId().equals(userId)){
                return MethodResult.error("无权修改客户端信息！");
            }
            oauthClientDetailsEntity.setClientId(clientDetailsDb.getClientId());
            oauthClientDetailsEntity.setClientSecret(clientDetailsDb.getClientSecret());
            oauthClientDetailsEntity.setCreateTime(clientDetailsDb.getCreateTime());
        }else {

            if(StringUtils.isBlank(clientDetailsSaveVo.getClientSecret())){
                return MethodResult.error("客户端秘钥必填！");
            }
            if (!ValidPassword.isValidPassword(clientDetailsSaveVo.getClientSecret())) {
                return MethodResult.error("客户端秘钥为8-20位包含大小写字母和数字的组合！");
            }
            String clientId =  UUID.randomUUID().toString().replace("-", "");
            oauthClientDetailsEntity.setClientId(clientId);
            //设置客户端秘钥
            if(StringUtils.isNotBlank(clientDetailsSaveVo.getClientSecret())){
                oauthClientDetailsEntity.setClientSecret(passwordEncoder.encode(clientDetailsSaveVo.getClientSecret()));
            }
            oauthClientDetailsEntity.setCreateTime(new Date());
        }

        oauthClientDetailsEntity.setUserId(userId);
        //设置默认值
        if(oauthClientDetailsEntity.getClientType()==null){
            oauthClientDetailsEntity.setClientType(ClientTypeEnum.CLIENT_TYPE_WX_MINI_APP);
        }
        if(StringUtils.isBlank(oauthClientDetailsEntity.getScope())){
            oauthClientDetailsEntity.setScope("all");
        }
        if(StringUtils.isBlank(oauthClientDetailsEntity.getAuthorizedGrantTypes())){
            oauthClientDetailsEntity.setAuthorizedGrantTypes("authorization_code,refresh_token,password,client_credentials,implicit,sms_code,captcha,social_type,wechat");
        }
        //if(StringUtils.isBlank(oauthClientDetailsEntity.getWebServerRedirectUri())){
        //    oauthClientDetailsEntity.setWebServerRedirectUri("https://www.xiushangsh.com");
        //}
        if(StringUtils.isBlank(oauthClientDetailsEntity.getAutoapprove())){
            oauthClientDetailsEntity.setAutoapprove("false");
        }
        oauthClientDetailsEntity = saveAndGet(oauthClientDetailsEntity);

        return MethodResult.success(oauthClientDetailsEntity);
    }

    public PageTableVO findPageList(SearchPageVo param) {
        String userId = userService.getCurrentUserId();
        QOauthClientDetailsEntity entity = QOauthClientDetailsEntity.oauthClientDetailsEntity;
        BooleanExpression ex = entity.userId.eq(userId);

        if(StringUtils.isNotBlank(param.getSearchKey())){
            ex = ex.and(entity.clientName.like("%" + param.getSearchKey() + "%"));
        }
        Page<OauthClientDetailsEntity> page = findPageList(ex,param.createPageRequest());
        LazyLoadUtil.fullLoad(page);
        PageTableVO vo = new PageTableVO(page,param);

        return vo;
    }
}
