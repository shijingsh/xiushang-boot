package com.mg.common.user.dao;

import com.mg.common.entity.SmsCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liukefu on 2017/6/24.
 */
@Repository
public interface SmsCodeDao extends JpaRepository<SmsCodeEntity, String> {

    List<SmsCodeEntity> findByMobileAndSmsCodeOrderBySendTimeDesc(String mobile, String smsCode);
}
