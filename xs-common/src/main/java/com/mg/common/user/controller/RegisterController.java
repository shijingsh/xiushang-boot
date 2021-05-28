package com.mg.common.user.controller;

import com.mg.common.components.SmsService;
import com.mg.common.entity.UserEntity;
import com.mg.common.user.service.UserService;
import com.mg.common.user.vo.RegisterVo;
import com.mg.common.utils.MD5;
import com.mg.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户注册
 * Created by kf on 2016/11/11.
 */
@Api(tags = "用户管理")
@Controller
@RequestMapping(value = "/",
        produces = "application/json; charset=UTF-8")
public class RegisterController {
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "手机号码注册")
    @ResponseBody
    @RequestMapping("/register")
    public CommonResult<UserEntity> register(@RequestBody RegisterVo registerVo) {
        if (StringUtils.isBlank(registerVo.getLoginName()) || StringUtils.isBlank(registerVo.getPassword())) {
            return CommonResult.error(100000, "用户名,密码不能为空。");
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
                userService.updateUser(user);
                return CommonResult.success(user);
            }else{
                UserEntity userEntity = new UserEntity();
                userEntity.setLoginName(registerVo.getLoginName());
                userEntity.setName(registerVo.getName());
                userEntity.setMobile(mobile);
                userEntity.setPassword(MD5.GetMD5Code(registerVo.getPassword()));
                userService.updateUser(userEntity);

                return CommonResult.success(userEntity);
            }
        }else{
            return CommonResult.error(100000, "验证码输入错误");
        }
    }
}
