package com.xiushang.common.info.service;

import com.xiushang.common.info.vo.HelpSearchVo;
import com.xiushang.common.info.vo.HelpVo;
import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.info.HelpEntity;
import com.xiushang.framework.entity.vo.PageTableVO;


public interface HelpService extends BaseService<HelpEntity> {

    PageTableVO findPageList(HelpSearchVo helpSearchVo);

    HelpEntity saveHelp(HelpVo helpVo);

}
