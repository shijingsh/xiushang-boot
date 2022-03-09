package com.xiushang.jpa.repository;

import com.xiushang.entity.SmsCodeEntity;

import java.util.List;

public interface SmsCodeDao extends BaseDao<SmsCodeEntity> {

    List<SmsCodeEntity> findByMobileAndSmsCodeOrderBySendTimeDesc(String mobile, String smsCode);
}
