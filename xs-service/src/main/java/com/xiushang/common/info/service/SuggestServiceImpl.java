package com.xiushang.common.info.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.components.SmsService;
import com.xiushang.common.info.dao.SuggestDao;
import com.xiushang.common.info.mapper.SuggestSaveMapper;
import com.xiushang.common.info.vo.SuggestVo;
import com.xiushang.common.user.vo.SmsVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.common.utils.LazyLoadUtil;
import com.xiushang.entity.info.QSuggestEntity;
import com.xiushang.entity.info.SuggestEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.framework.utils.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public PageTableVO findPageList(SearchPageVo param) {

        String shopId = userService.getCurrentShopId();
        QSuggestEntity entity = QSuggestEntity.suggestEntity;
        BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID);
        if(StringUtils.isNotBlank(shopId)){
            ex = ex.and(entity.belongShop.id.eq(shopId));
        }

        Page<SuggestEntity> page = findPageList(ex,param.createPageRequest());
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
        entity.setFromClient(clientId);
        suggestDao.save(entity);

        String templateParam = "{\"code\":\""+entity.getMobile()+"\"}";
        sendSmsTip(entity,templateParam);

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
