package com.xiushang.common.info.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.info.dao.HelpDao;
import com.xiushang.common.info.mapper.HelpSaveMapper;
import com.xiushang.common.info.vo.HelpVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.info.HelpEntity;
import com.xiushang.entity.info.QHelpEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.utils.DeleteEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HelpServiceImpl extends BaseServiceImpl<HelpEntity> implements HelpService {
    @Autowired
    private HelpDao helpDao;

    @Transactional(readOnly=true)
    public PageTableVO findPageList(SearchPageVo searchPageVo) {

        String shopId  = userService.getCurrentShopId();

        return findPageList(searchPageVo,shopId);
    }

    @Transactional(readOnly=true)
    public PageTableVO findShopPageList(SearchPageVo searchPageVo) {
        String shopId = userService.getCurrentTenantShopId();


        return findPageList(searchPageVo,shopId);
    }

    private PageTableVO findPageList(SearchPageVo searchPageVo,String shopId) {

        QHelpEntity entity = QHelpEntity.helpEntity;
        BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID);
        String searchKey = searchPageVo.getSearchKey();
        if(StringUtils.isNotBlank(shopId)){
            ex = ex.and(entity.belongShop.id.eq(shopId));
        }
        if(StringUtils.isNotBlank(searchKey)){
            ex = ex.and(entity.title.like("%" + searchKey + "%").or(entity.content.like("%" + searchKey + "%")));
        }

        Page<HelpEntity> page = findPageList(ex,searchPageVo.createPageRequest(new Sort.Order(Sort.Direction.ASC,"displayOrder")));
        PageTableVO vo = new PageTableVO(page,searchPageVo);

        return vo;
    }

    public HelpEntity saveHelp(HelpVo helpVo) {

        ShopEntity shopEntity = userService.getCurrentShop();

        HelpEntity entity =  HelpSaveMapper.INSTANCE.targetToSource(helpVo);
        entity.setBelongShop(shopEntity);

        return helpDao.save(entity);
    }

}
