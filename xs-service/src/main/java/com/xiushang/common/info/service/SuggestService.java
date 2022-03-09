package com.xiushang.common.info.service;

import com.xiushang.common.info.vo.SuggestVo;
import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.info.SuggestEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;



public interface SuggestService extends BaseService<SuggestEntity> {

    PageTableVO findPageList(SearchPageVo param);

    SuggestEntity saveSuggest(SuggestVo suggestVo);
}
