package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.components.SmsService;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.RegisterVo;
import com.xiushang.common.user.vo.SmsCustomVo;
import com.xiushang.common.user.vo.SmsVo;
import com.xiushang.common.utils.MD5;
import com.xiushang.common.utils.ValidPassword;
import com.xiushang.entity.UserEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.security.SecurityRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Random;

@Api(tags = "用户管理")
@ApiSort(value = 2)
@Controller
public class RegisterController {
	@Autowired
	private SmsService smsService;
	@Autowired
	private UserService userService;
	/**
	 * 发送短信
     * @return
     */
	@ApiOperation(value = "获取验证码")
	@XiushangApi
	@ApiOperationSupport(order=10)
	@ResponseBody
    @PostMapping("/verifyCode")
    public CommonResult verifyCode(@Valid @RequestBody SmsCustomVo smsCustomVo) {
		//ShopEntity shopEntity = userService.getCurrentTenantShop();
		//if(shopEntity==null ) {
		//	return CommonResult.error(100000, "当前租户，尚未开通商铺！");
		//}
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
			//smsVo.setShopId(shopEntity.getId());
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


	@ApiOperation(value = "手机号码注册")
	@XiushangApi
	@ApiOperationSupport(order=12)
	@ResponseBody
	@PostMapping("/register")
	public CommonResult<UserEntity> register(@Valid @RequestBody RegisterVo registerVo) {
		if (StringUtils.isBlank(registerVo.getLoginName()) || StringUtils.isBlank(registerVo.getPassword())) {
			return CommonResult.error(100000, "用户名,密码不能为空。");
		}

		if (!ValidPassword.isValidPassword(registerVo.getPassword())) {
			return CommonResult.error(1, "密码为8-20位包含大小写字母和数字的组合！");
		}

		String code = registerVo.getVerifyCode();
		if(StringUtils.isNotBlank(code)){
			code = code.trim();
		}
		String mobile = registerVo.getLoginName();
		if(smsService.validateCode(mobile,code)){
			UserEntity user = userService.getUser(mobile);
			if (user != null) {
				//直接修改已经存在的账号;
				user.setLoginName(registerVo.getLoginName());
				user.setPassword(MD5.GetMD5Code(registerVo.getPassword()));
				if(StringUtils.isNotBlank(registerVo.getName())){
					user.setName(registerVo.getName());
				}
				userService.registerUser(user);
				return CommonResult.success(user);
			}else{
				UserEntity userEntity = new UserEntity();
				userEntity.setLoginName(registerVo.getLoginName());

				if(StringUtils.isNotBlank(registerVo.getName())){
					userEntity.setName(registerVo.getName());
				}else {
					userEntity.setName(registerVo.getLoginName());
				}
				userEntity.setMobile(mobile);
				userEntity.setPassword(MD5.GetMD5Code(registerVo.getPassword()));

				userService.registerUser(userEntity);

				return CommonResult.success(userEntity);
			}
		}else{
			return CommonResult.error(100000, "验证码输入错误");
		}
	}


	/**
	 * 分页查询短信列表
	 *
	 * @return
	 */
	@ResponseBody
	@PostMapping("/api/sms/listPage")
	@Secured(SecurityRole.ROLE_ADMIN)
	public CommonResult getPageList(@RequestBody SearchPageVo searchVo) {

		PageTableVO vo = smsService.findPageList(searchVo);

		return CommonResult.success(vo);
	}
}
