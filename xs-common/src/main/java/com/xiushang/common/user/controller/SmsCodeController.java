package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.components.SmsService;
import com.xiushang.common.user.service.SmsCodeService;
import com.xiushang.common.user.vo.SmsCustomVo;
import com.xiushang.common.user.vo.SmsVo;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.sys.PropertyConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Api(tags = "用户管理")
@ApiSort(value = 1)
@Controller
@RequestMapping(value = "/api/user",
		produces = "application/json; charset=UTF-8")
public class SmsCodeController {
	@Autowired
	private SmsService smsService;

	/**
	 * 发送短信
	 * @param mobile
     * @return
     */
	@ApiOperation(value = "获取验证码")
	@ResponseBody
    @GetMapping("/verifyCode")
    public CommonResult verifyCode(@ApiParam(value = "接收手机号码",required = true) String mobile) {
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
			String templateCode = PropertyConfigurer.getConfig("sms.templateCode");

			SmsVo smsVo = new SmsVo();
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

	/**
	 * 发送短信
	 * @param smsCustomVo
	 * @return
	 */
	@ApiOperation(value = "发送自定义验证码")
	@ResponseBody
	@PostMapping("/verifyCodeCustom")
	public CommonResult verifyCodeCustom(@RequestBody SmsCustomVo smsCustomVo) {
		if(StringUtils.isBlank(smsCustomVo.getShopId())) {
			return CommonResult.error(100000, "自定义验证码，shopId不能为空！");
		}
		if(StringUtils.isBlank(smsCustomVo.getMobile())) {
			return CommonResult.error(100000, "请输入手机号码");
		}
		if(smsCustomVo.getMobile().length()!=11) {
			return CommonResult.error(100000, "电话号码长度应为11位");
		}

		try {
			//发送短信验证码并保存验证码数据
			String randomStr = getRandomStr();

			SmsVo smsVo = new SmsVo(smsCustomVo.getShopId(),smsCustomVo.getMobile(),randomStr,smsCustomVo.getTemplateParam(),smsCustomVo.getTemplateCode());
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
