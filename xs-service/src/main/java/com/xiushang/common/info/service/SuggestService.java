package com.xiushang.common.info.service;

import com.xiushang.common.info.vo.SuggestProcessVo;
import com.xiushang.common.info.vo.SuggestSearchVo;
import com.xiushang.common.info.vo.SuggestVo;
import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.info.SuggestEntity;
import com.xiushang.framework.entity.vo.PageTableVO;



public interface SuggestService extends BaseService<SuggestEntity> {

    PageTableVO findPageList(SuggestSearchVo param);

    SuggestEntity saveSuggest(SuggestVo suggestVo);

    SuggestEntity processing(String id);

    SuggestEntity process(SuggestProcessVo suggestProcessVo);
}
