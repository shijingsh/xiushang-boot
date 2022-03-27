package com.xiushang.common.info.service;

import com.xiushang.common.info.vo.HelpVo;
import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.info.HelpEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;


public interface HelpService extends BaseService<HelpEntity> {

    PageTableVO findPageList(SearchPageVo searchPageVo);

    PageTableVO findMyPageList(SearchPageVo searchPageVo);

    HelpEntity saveHelp(HelpVo helpVo);

}
