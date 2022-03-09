package com.xiushang.common.info.dao;

import com.xiushang.entity.info.AppVersionEntity;
import com.xiushang.jpa.repository.BaseDao;

import java.util.List;

public interface AppVersionDao extends BaseDao<AppVersionEntity> {

    List<AppVersionEntity> findByShopIdAndClientId(String shopId, String clientId);
}
