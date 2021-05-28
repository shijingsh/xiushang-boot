package com.mg.common.user.service;

import com.mg.common.entity.SystemParamEntity;

public interface SystemParamService {

    SystemParamEntity findByName(String shopId, String paramName);
}
