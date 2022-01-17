package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.components.SmsService;
import com.xiushang.common.upload.service.UploadService;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.*;
import com.xiushang.common.utils.MD5;
import com.xiushang.entity.UserEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.utils.DeleteEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "用户管理")
@ApiSort(value = 2)
@Controller
@RequestMapping(value = "/api/user",
        produces = "application/json; charset=UTF-8")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "修改个人信息")
    @ApiOperationSupport(order=4)
    @ResponseBody
    @PostMapping("/modify")
    public CommonResult<UserEntity> modify(@RequestBody UserVo user) {

        UserEntity userEntity = userService.getCurrentUser();
        if (userEntity==null) {
            return CommonResult.error(1, "登录超时，请重新登录！");
        }
        try {
            if(StringUtils.isNotBlank(user.getName())){
                userEntity.setName(user.getName());
            }
            if(StringUtils.isNotBlank(user.getMobile())){
                userEntity.setMobile(user.getMobile());
            }
            if(StringUtils.isNotBlank(user.getEmail())){
                userEntity.setEmail(user.getEmail());
            }
            if(StringUtils.isNotBlank(user.getPosition())){
                userEntity.setPosition(user.getPosition());
            }
            if(StringUtils.isNotBlank(user.getHeadPortrait())){
                userEntity.setHeadPortrait(user.getHeadPortrait());
            }
            if(user.getLatitude() != null){
                userEntity.setLatitude(user.getLatitude());
            }
            if(user.getLongitude() != null){
                userEntity.setLatitude(user.getLongitude());
            }
            userService.updateUser(userEntity);
        } catch (Exception e) {
            return CommonResult.error(10000, "修改用户信息出现异常");
        }

        return CommonResult.success(userEntity);
    }

    @ApiOperation(value = "修改个人密码")
    @ApiOperationSupport(order=6)
    @ResponseBody
    @PostMapping("/modifyPass")
    public CommonResult<UserEntity> modifyPass(@Valid @RequestBody ModifyPassVo modifyPassVo) {

        UserEntity userEntity = userService.getCurrentUser();
        if (userEntity==null) {
            return CommonResult.error(1, "登录超时，请重新登录！");
        }
        String pswOld =  modifyPassVo.getOldPassword();
        String psw = modifyPassVo.getNewPassword();
        if (StringUtils.isBlank(psw)) {
            return CommonResult.error(1, "请输入登录密码");
        }

        if (!isValidPassword(psw)) {
            return CommonResult.error(1, "密码为8-20位包含大小写字母和数字的组合！");
        }


        //String oldPassMd5 = MD5.GetMD5Code(pswOld);
        if (!StringUtils.equals(pswOld, userEntity.getPassword())) {
            return CommonResult.error(1, "原密码不正确，请重新输入");
        }
        /*if (!StringUtils.equals(psw, pswConfirm)) {
            //return CommonResult.error(1, "两次密码输入不一致，请重新输入");
        }*/
        userEntity.setPassword(MD5.GetMD5Code(psw));
        try {
            userService.updateUser(userEntity);
        } catch (Exception e) {
            return CommonResult.error(10000, "修改密码失败！");
        }
        //Session session = SecurityUtils.getSubject().getSession();
        //session.setAttribute(Constants.CURRENT_USER, userEntity);
        return CommonResult.success(userEntity);
    }

    @ApiOperation(value = "重置密码（短信验证码方式）")
    @ApiOperationSupport(order=7)
    @ResponseBody
    @PostMapping("/resetPassword")
    public CommonResult<UserEntity> resetPassword(@Valid  @RequestBody ResetPwdVo resetPwdVo) {

        if (StringUtils.isBlank(resetPwdVo.getLoginName()) || StringUtils.isBlank(resetPwdVo.getPassword())) {
            return CommonResult.error(100000, "用户名,密码不能为空。");
        }
        if (StringUtils.isBlank(resetPwdVo.getCode())) {
            return CommonResult.error(100000, "验证码不能为空。");
        }

        if (!isValidPassword(resetPwdVo.getPassword())) {
            return CommonResult.error(1, "密码为8-20位包含大小写字母和数字的组合！");
        }

        UserEntity user = userService.getUser(resetPwdVo.getLoginName());
        if (user == null) {
            return CommonResult.error(100000, "用户尚未注册");
        }
        if(StringUtils.isBlank(user.getMobile())){
            user.setMobile(user.getLoginName());
        }
        String code = resetPwdVo.getCode().trim();
        if(smsService.validateCode(user.getMobile(),code)){
            user.setPassword(MD5.GetMD5Code(resetPwdVo.getPassword()));
            userService.updateUser(user);
        }else{
            return CommonResult.error(100000, "验证码输入错误");
        }
        return CommonResult.success(user);
    }

    /**
     * 分页查询用户登录帐号
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/pageList")
    public CommonResult getPageList(@RequestBody UserSearchVo searchVo) {

        PageTableVO vo = userService.findPageList(searchVo);

        return CommonResult.success(vo);
    }


    @ApiOperation(value = "修改用户头像")
    @ApiOperationSupport(order=5)
    @ResponseBody
    @PostMapping("/modifyHeadPortrait")
    public CommonResult<UserEntity> modifyHeadPortrait(@Valid @RequestBody UserHeadPortraitVo userHeadPortraitVo) {

        UserEntity userEntity = userService.getCurrentUser();

        if(userEntity!=null && StringUtils.isNotBlank(userHeadPortraitVo.getHeadPortrait())){
            if(StringUtils.isNotBlank(userEntity.getHeadPortrait())){
                uploadService.removeFile(userEntity.getHeadPortrait());
            }
            userEntity.setHeadPortrait(userHeadPortraitVo.getHeadPortrait());
            userService.updateUser(userEntity);
        }

        return CommonResult.success(userEntity);
    }

    @ApiOperation(value = "手机号码注册")
    @ApiOperationSupport(order=2)
    @ResponseBody
    @PostMapping("/register")
    public CommonResult<UserEntity> register(@Valid @RequestBody RegisterVo registerVo) {
        if (StringUtils.isBlank(registerVo.getLoginName()) || StringUtils.isBlank(registerVo.getPassword())) {
            return CommonResult.error(100000, "用户名,密码不能为空。");
        }

        if (!isValidPassword(registerVo.getPassword())) {
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
                userService.updateUser(user);
                return CommonResult.success(user);
            }else{
                UserEntity userEntity = new UserEntity();
                userEntity.setLoginName(registerVo.getLoginName());
                userEntity.setName(registerVo.getName());
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
     * 获取用户
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @ApiOperationSupport(order=3)
    @ResponseBody
    @GetMapping("/info")
    public CommonResult<UserEntity> info() {
        UserEntity userEntity = userService.getCurrentUser();

        return CommonResult.success(userEntity);
    }

    /**
     * 获取用户
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public CommonResult<UserEntity> get(String id) {
        UserEntity userEntity = userService.getUserById(id);

        return CommonResult.success(userEntity);
    }

    @ApiOperation(value = "注销账号")
    @ApiOperationSupport(order=9)
    @ResponseBody
    @GetMapping("/cancel")
    public CommonResult userCancel() {

        UserEntity userEntity = userService.getCurrentUser();
        if(userEntity!=null){

            userEntity.setDeleted(DeleteEnum.INVALID);
            userService.updateUser(userEntity);
        }else{
            return CommonResult.error("用户登录已失效，请重新登陆！");
        }
        return CommonResult.success();
    }

    private boolean isValidPassword(String password){
        String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(password);
        return  m.matches();
    }

    public static void main(String args[]){
        String str = "abc12345";
        String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }
}
