package com.xiushang.jpa.repository;

import com.xiushang.entity.SmsCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liukefu on 2017/6/24.
 */
public interface SmsCodeDao extends JpaRepository<SmsCodeEntity, String> {

    List<SmsCodeEntity> findByMobileAndSmsCodeOrderBySendTimeDesc(String mobile, String smsCode);
}
