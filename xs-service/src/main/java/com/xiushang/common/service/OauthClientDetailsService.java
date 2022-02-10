package com.xiushang.common.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.user.mapper.OauthClientDetailsSaveMapper;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.OauthClientDetailsSaveVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.common.utils.LazyLoadUtil;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.entity.oauth.QOauthClientDetailsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.log.MethodResult;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OauthClientDetailsService extends BaseServiceImpl<OauthClientDetailsEntity> {

    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;
    @Autowired
    private UserService userService;

    public OauthClientDetailsEntity findByClientId(String clientId){
        return  oauthClientDetailsDao.findByClientId(clientId);
    }

    public MethodResult<OauthClientDetailsEntity> saveClient(OauthClientDetailsSaveVo clientDetailsSaveVo) {
        String userId = userService.getCurrentUserId();

        OauthClientDetailsEntity clientDetailsDb = null;
        OauthClientDetailsEntity oauthClientDetailsEntity = OauthClientDetailsSaveMapper.INSTANCE.targetToSource(clientDetailsSaveVo);

        if(StringUtils.isNotBlank(clientDetailsSaveVo.getId())){
            clientDetailsDb = get(clientDetailsSaveVo.getId());
            //修改客户端时，判断修改权限
            if(!clientDetailsDb.getUserId().equals(userId)){
                return   MethodResult.error("无权修改客户端信息！");
            }
            oauthClientDetailsEntity.setClientId(clientDetailsDb.getClientId());
            oauthClientDetailsEntity.setClientSecret(clientDetailsDb.getClientSecret());
            oauthClientDetailsEntity.setCreateTime(clientDetailsDb.getCreateTime());
        }else {
            oauthClientDetailsEntity.setClientId(UUID.randomUUID().toString());
            oauthClientDetailsEntity.setClientSecret(clientDetailsDb.getClientSecret());
            oauthClientDetailsEntity.setCreateTime(clientDetailsDb.getCreateTime());
        }
        oauthClientDetailsEntity.setUserId(userId);
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
