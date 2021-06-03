package com.xiushang.common.user.dao;

import com.xiushang.common.entity.SystemParamEntity;
import com.xiushang.common.utils.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemParamDao extends BaseDao<SystemParamEntity> {
    List<SystemParamEntity> findByShopIdAndParamName(String mobile, String smsCode);
}
