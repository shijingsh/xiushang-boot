package com.xiushang.common.notice.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.notice.mapper.NoticeSaveMapper;
import com.xiushang.common.notice.vo.NoticeVo;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.notice.NoticeEntity;
import com.xiushang.entity.notice.QNoticeEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.utils.DeleteEnum;
import com.xiushang.jpa.repository.NoticeDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * 公告
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeEntity> implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public PageTableVO findPageList(SearchPageVo searchVo) {

        String shopId  = userService.getCurrentTenantShopId();

        return findPageList(searchVo,shopId);
    }

    @Transactional(readOnly = true)
    public PageTableVO findMyPageList(SearchPageVo searchPageVo){

        String shopId  = userService.getCurrentShopId();

        return findPageList(searchPageVo,shopId);
    }

    private PageTableVO findPageList(SearchPageVo searchVo,String shopId) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        //有效的公告
        QNoticeEntity entity = QNoticeEntity.noticeEntity;
        BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID).and(entity.validDate.after(c.getTime()));

        String searchKey = searchVo.getSearchKey();
        if (StringUtils.isNotBlank(shopId)) {
            ex = ex.and(entity.belongShop.id.eq(shopId));
        }
        if (StringUtils.isNotBlank(searchKey)) {
            ex = ex.and(entity.title.like("%" + searchKey + "%").or(entity.content.like("%" + searchKey + "%")));
        }

        Page<NoticeEntity> page = findPageList(ex, searchVo.createPageRequest(
                new Sort.Order(Sort.Direction.DESC,"updatedDate")));
        PageTableVO vo = new PageTableVO(page, searchVo);

        return vo;
    }


    public NoticeEntity saveNotice(NoticeVo noticeVo) {

        ShopEntity shopEntity = userService.getCurrentShop();

        NoticeEntity noticeEntity = NoticeSaveMapper.INSTANCE.targetToSource(noticeVo);
        noticeEntity.setBelongShop(shopEntity);

        return noticeDao.save(noticeEntity);
    }

    public NoticeEntity deleteNotice(String id) {
        noticeDao.deleteById(id);

        return null;
    }
}
