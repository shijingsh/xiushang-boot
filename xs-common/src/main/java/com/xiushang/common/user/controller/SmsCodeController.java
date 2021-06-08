package com.xiushang.common.user.controller;

import com.xiushang.common.components.SmsService;
import com.xiushang.entity.SmsCodeEntity;
import com.xiushang.common.user.service.SmsCodeService;

import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Random;

@Api(tags = "用户管理")
@Controller
@RequestMapping(value = "/",
		produces = "application/json; charset=UTF-8")
public class SmsCodeController {
	@Autowired
	private SmsService smsService;
	@Autowired
	private SmsCodeService smsCodeService;

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 发送短信
	 * @param mobile
     * @return
     */
	@ApiOperation(value = "获取验证码")
	@ResponseBody
    @RequestMapping("verifyCode")
    public CommonResult verifyCode(String mobile) {
    	if(StringUtils.isBlank(mobile)) {
			return CommonResult.error(100000, "请输入手机号码");
    	}
    	if(mobile.length()!=11) {
			return CommonResult.error(100000, "电话号码长度应为11位");
    	}
    	try {
        	//发送短信验证码并保存验证码数据
        	String randomStr = getRandomStr();
    		int flag = smsService.sendSmsAliyunForVerificationCode(mobile, randomStr);
        	System.out.println("短信验证码为："+randomStr);
			if(flag==1){
				System.out.println("短信验证码发送成功！");
			}else {
				System.out.println("短信验证码发送失败！");
			}
			SmsCodeEntity smsCodeEntity = new SmsCodeEntity();
			smsCodeEntity.setMobile(mobile);
			smsCodeEntity.setSmsCode(randomStr);
			smsCodeEntity.setSendTime(new Date());
			smsCodeService.save(smsCodeEntity);
    	} catch (ServiceException e) {
    		e.printStackTrace();
			return CommonResult.error(100000, e.getMessage());
    	} catch (Exception e) {
    		e.printStackTrace();
			return CommonResult.error(100000, "发送失败，请稍后重试！");
    	}
		return CommonResult.success();
    }

	private static String getRandomStr() {
		Random random = new Random();
		StringBuffer buf = new StringBuffer(16);
		for (int i = 0; i < 6; i++) {
			int charOrNum = random.nextInt(10);
			buf.append(charOrNum);
		}
		return buf.toString();
	}


}
