package com.xiushang.common.user.service;

import com.xiushang.common.user.vo.SystemParamSaveVo;
import com.xiushang.entity.SystemParamEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;

public interface SystemParamService {

    SystemParamEntity getOrSaveParam(String paramName);

    SystemParamEntity getOrSaveParam(String paramName, String defaultValue);

    SystemParamEntity getOrSaveParam(String paramName, String defaultValue, String remark);

    SystemParamEntity saveParam(SystemParamSaveVo paramVo);

    PageTableVO findPageList(SearchPageVo param);
}
