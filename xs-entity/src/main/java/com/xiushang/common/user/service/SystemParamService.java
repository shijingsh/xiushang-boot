package com.xiushang.common.user.service;

import com.xiushang.entity.SystemParamEntity;

public interface SystemParamService {

    SystemParamEntity findByName(String shopId, String paramName);
}
