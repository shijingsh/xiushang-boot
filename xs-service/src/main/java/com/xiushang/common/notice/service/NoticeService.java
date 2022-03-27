package com.xiushang.common.notice.service;

import com.xiushang.common.notice.vo.NoticeVo;
import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.notice.NoticeEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;


public interface NoticeService extends BaseService<NoticeEntity> {

    PageTableVO findPageList(SearchPageVo searchPageVo);

    PageTableVO findShopPageList(SearchPageVo searchPageVo);

    NoticeEntity saveNotice(NoticeVo noticeVo);

    NoticeEntity deleteNotice(String id);
}
