package com.xiushang.common.user.service;

import com.xiushang.common.entity.SystemParamEntity;

public interface SystemParamService {

    SystemParamEntity findByName(String shopId, String paramName);
}
