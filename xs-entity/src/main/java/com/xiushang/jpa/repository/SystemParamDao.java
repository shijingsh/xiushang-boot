package com.xiushang.jpa.repository;

import com.xiushang.entity.SystemParamEntity;

import java.util.List;

public interface SystemParamDao extends BaseDao<SystemParamEntity> {
    List<SystemParamEntity> findByShopIdAndParamName(String shopId, String paramName);
}
