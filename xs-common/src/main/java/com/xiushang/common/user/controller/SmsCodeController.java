package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.components.SmsService;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.SmsCustomVo;
import com.xiushang.common.user.vo.SmsVo;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.sys.PropertyConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Random;

@Api(tags = "用户管理")
@ApiSort(value = 2)
@Controller
@RequestMapping(value = "/api/user",
		produces = "application/json; charset=UTF-8")
public class SmsCodeController {
	@Autowired
	private SmsService smsService;
	@Autowired
	private UserService userService;
	/**
	 * 发送短信
     * @return
     */
	@ApiOperation(value = "获取验证码")
	@ApiOperationSupport(order=1)
	@ResponseBody
    @GetMapping("/verifyCode")
    public CommonResult verifyCode(@Valid @RequestBody SmsCustomVo smsCustomVo) {
		ShopEntity shopEntity = userService.getCurrentShop();
		if(shopEntity==null ) {
			return CommonResult.error(100000, "商铺不存在！");
		}
		String mobile = smsCustomVo.getMobile();
    	if(StringUtils.isBlank(mobile)) {
			return CommonResult.error(100000, "请输入手机号码");
    	}
    	if(mobile.length()!=11) {
			return CommonResult.error(100000, "电话号码长度应为11位");
    	}
    	try {
        	//发送短信验证码并保存验证码数据
        	String randomStr = getRandomStr();

			//模板代码
			String templateParam = "{\"code\":\""+randomStr+"\"}";
			String templateCode = smsCustomVo.getTemplateCode();
			if(StringUtils.isBlank(templateCode)){
				templateCode = PropertyConfigurer.getConfig("sms.templateCode");
			}

			SmsVo smsVo = new SmsVo();
			smsVo.setShopId(shopEntity.getId());
			smsVo.setMobile(mobile);
			smsVo.setTemplateCode(templateCode);
			smsVo.setTemplateParam(templateParam);
			smsVo.setSmsCode(randomStr);

    		int flag = smsService.sendSmsAliyunForVerificationCode(smsVo);
        	System.out.println("短信验证码为："+randomStr);
			if(flag==1){
				System.out.println("短信验证码发送成功！");
			}else {
				System.out.println("短信验证码发送失败！");
			}

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
