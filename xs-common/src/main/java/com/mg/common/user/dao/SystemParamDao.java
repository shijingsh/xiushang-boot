package com.mg.common.user.dao;

import com.mg.common.entity.SmsCodeEntity;
import com.mg.common.entity.SystemParamEntity;
import com.mg.common.utils.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemParamDao extends BaseDao<SystemParamEntity> {
    List<SystemParamEntity> findByShopIdAndParamName(String mobile, String smsCode);
}
