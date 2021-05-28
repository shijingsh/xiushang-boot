package com.mg.common.user.service;

import com.mg.common.entity.SmsCodeEntity;
import com.mg.common.user.dao.SmsCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by liukefu on 2017/6/24.
 */
@Service
public class SmsCodeServiceImpl  implements SmsCodeService {
    @Autowired
    private SmsCodeDao smsCodeDao;
    @Override
    public SmsCodeEntity findByMobileAndSmsCode(String mobile, String smsCode) {
        List<SmsCodeEntity> list = smsCodeDao.findByMobileAndSmsCodeOrderBySendTimeDesc(mobile,smsCode);
        if(list!=null && list.size()>0){
            //验证码不能超过10分钟
            SmsCodeEntity smsCodeEntity = list.get(0);
            long distance = (new Date().getTime() - smsCodeEntity.getSendTime().getTime()) / 1000 /60;
            if(distance > 10){
                return null;
            }
            return list.get(0);
        }
        return null;
    }

    @Override
    public SmsCodeEntity save(SmsCodeEntity smsCodeEntity) {
       return smsCodeDao.save(smsCodeEntity);
    }
}
