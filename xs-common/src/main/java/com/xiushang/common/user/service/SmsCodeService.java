package com.xiushang.common.user.service;

import com.xiushang.common.entity.SmsCodeEntity;

/**
 * Created by liukefu on 2017/6/24.
 */
public interface SmsCodeService  {

    SmsCodeEntity findByMobileAndSmsCode(String mobile, String smsCode);

    SmsCodeEntity save(SmsCodeEntity smsCodeEntity);
}
