package com.xiushang.common.info.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.components.SmsService;
import com.xiushang.common.info.dao.SuggestDao;
import com.xiushang.common.info.mapper.SuggestSaveMapper;
import com.xiushang.common.info.vo.SuggestProcessVo;
import com.xiushang.common.info.vo.SuggestSearchVo;
import com.xiushang.common.info.vo.SuggestVo;
import com.xiushang.common.user.vo.SmsVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.common.utils.LazyLoadUtil;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.info.QSuggestEntity;
import com.xiushang.entity.info.SuggestEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.framework.utils.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户反馈
 */
@Service
@Slf4j
public class SuggestServiceImpl extends BaseServiceImpl<SuggestEntity> implements SuggestService {
    @Autowired
    private SuggestDao suggestDao;
    @Autowired
    private SmsService smsService;

    @Transactional(readOnly=true)
    public PageTableVO findPageList(SuggestSearchVo param) {

        String shopId = userService.getCurrentShopId();
        QSuggestEntity entity = QSuggestEntity.suggestEntity;
        BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID);
        if(StringUtils.isNotBlank(shopId)){
            ex = ex.and(entity.belongShop.id.eq(shopId));
        }
        if(param.getType()==null){
            ex = ex.and(entity.type.eq(0));
        }else {
            ex = ex.and(entity.type.eq(param.getType()));
        }

        if(param.getStatus()!=null){
            ex = ex.and(entity.status.eq(param.getStatus()));
        }

        if(StringUtils.isNotBlank(param.getName())){
            ex = ex.and(entity.name.like("%" + param.getName() + "%"));
        }
        if(StringUtils.isNotBlank(param.getMobile())){
            ex = ex.and(entity.mobile.like("%" + param.getMobile() + "%"));
        }
        Page<SuggestEntity> page = findPageList(ex,param.createPageRequest(new Sort.Order(Sort.Direction.DESC,"createdDate")));
        LazyLoadUtil.fullLoad(page);
        PageTableVO vo = new PageTableVO(page,param);

        return vo;
    }

    @Transactional
    public SuggestEntity saveSuggest(SuggestVo suggestVo) {
        ShopEntity shopEntity = userService.getCurrentTenantShop();
        String clientId = userService.getCurrentClientId();
        SuggestEntity entity = SuggestSaveMapper.INSTANCE.targetToSource(suggestVo);

        if(shopEntity != null){
            entity.setBelongShop(shopEntity);
        }
        if(StringUtils.isBlank(suggestVo.getId()) && entity.getType()==null){
            entity.setType(0);
        }
        if(StringUtils.isBlank(suggestVo.getId()) && entity.getStatus()==null){
            entity.setStatus(0);
        }
        entity.setFromClient(clientId);
        suggestDao.save(entity);

        String templateParam = "{\"code\":\""+entity.getMobile()+"\"}";
        sendSmsTip(entity,templateParam);

        return entity;
    }

    public SuggestEntity processing(String id) {

        UserEntity userEntity = userService.getCurrentUser();
        SuggestEntity entity = get(id);

        entity.setHandlerUser(userEntity);
        entity.setStatus(1);
        suggestDao.save(entity);

        return entity;
    }

    public SuggestEntity process(SuggestProcessVo suggestProcessVo) {
        UserEntity userEntity = userService.getCurrentUser();
        SuggestEntity entity = get(suggestProcessVo.getId());

        entity.setHandlerUser(userEntity);
        entity.setHandlerContent(suggestProcessVo.getHandlerContent());

        if(StringUtils.isNotBlank(suggestProcessVo.getName())) {
            entity.setName(suggestProcessVo.getName());
        }
        if(StringUtils.isNotBlank(suggestProcessVo.getEmail())) {
            entity.setEmail(suggestProcessVo.getEmail());
        }
        if(entity.getType()==1 && StringUtils.isNotBlank(suggestProcessVo.getContent())) {
            entity.setEmail(suggestProcessVo.getContent());
        }

        entity.setStatus(2);
        suggestDao.save(entity);

        return entity;
    }

    private void sendSmsTip(SuggestEntity entity, String templateParam){
        try {
            String templateCode = PropertyConfigurer.getConfig("sms.templateCodeTip");
            String adminCode = PropertyConfigurer.getConfig("sms.adminCode");
            if(entity!=null && entity.getBelongShop()!=null
                    && StringUtils.isNotBlank(entity.getBelongShop().getMobile())){
                adminCode = entity.getBelongShop().getMobile();
            }
            SmsVo smsVo = new SmsVo(adminCode,templateParam,templateCode);
            int flag = smsService.sendSms(smsVo);
            log.info("客户留言提醒："+templateCode);
            if(flag==1){
                log.info("短信发送成功！");
            }else {
                log.info("短信发送失败！");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
