package com.xiushang.common.user.controller;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.components.SmsService;
import com.xiushang.common.upload.service.UploadService;
import com.xiushang.common.upload.vo.UploadBean;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.ResetPwdVo;
import com.xiushang.common.utils.MD5;
import com.xiushang.entity.UserEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.utils.StatusEnum;
import com.xiushang.framework.utils.UserHolder;
import com.xiushang.framework.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "用户管理")
@ApiSort(value = 1)
@Controller
@RequestMapping(value = "/user",
        produces = "application/json; charset=UTF-8")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "修改用户信息")
    @ResponseBody
    @PostMapping("/modify")
    public CommonResult<UserEntity> modify(@RequestBody UserEntity user) {

        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return CommonResult.error(10000, "修改员工信息出现异常");
        }

        return CommonResult.success(user);
    }

    @ResponseBody
    @GetMapping("/modifyPass")
    public CommonResult<UserEntity> modifyPass() {

        String pswOld = req.getParameter("oldPassword");
        String psw = req.getParameter("newPassword");
        String pswConfirm = req.getParameter("confirmPassword");
        if (StringUtils.isBlank(psw)) {
            return CommonResult.error(1, "请输入登录密码");
        }
        if (psw.length() < 6) {
            return CommonResult.error(1, "密码长度不能少于6位！");
        }
        String userId = UserHolder.getLoginName();
        if (StringUtils.isBlank(userId)) {
            return CommonResult.error(1, "登录超时，请重新登录！");
        }
        UserEntity userEntity = userService.getUserById(userId);
        //String oldPassMd5 = MD5.GetMD5Code(pswOld);
        if (!StringUtils.equals(pswOld, userEntity.getPassword())) {
            return CommonResult.error(1, "原密码不正确，请重新输入");
        }
        if (!StringUtils.equals(psw, pswConfirm)) {
            return CommonResult.error(1, "两次密码输入不一致，请重新输入");
        }
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

    @ApiOperation(value = "重置密码")
    @ResponseBody
    @PostMapping("/resetPassword")
    public CommonResult<UserEntity> resetPassword(@RequestBody ResetPwdVo resetPwdVo) {

        if (StringUtils.isBlank(resetPwdVo.getLoginName()) || StringUtils.isBlank(resetPwdVo.getPassword())) {
            return CommonResult.error(100000, "用户名,密码不能为空。");
        }
        if (StringUtils.isBlank(resetPwdVo.getCode())) {
            return CommonResult.error(100000, "验证码不能为空。");
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
    public CommonResult getPageList() {
        String jsonString = WebUtil.getJsonBody(req);
        PageTableVO param = JSON.parseObject(jsonString, PageTableVO.class);

        PageTableVO vo = userService.findPageList(param);

        return CommonResult.success(vo);
    }

    /**
     * 删除用户
     * @return
     */
    /*@ResponseBody
    @GetMapping("/delete")
    public CommonResult delete(String id) {

        userService.delete(id);

        return CommonResult.success(null);
    }*/

    @ApiOperation(value = "上传用户头像")
    @ResponseBody
    @PostMapping("/headPortrait")
    public CommonResult<UserEntity> headPortrait(HttpServletRequest request,String userPath) {

        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) (request);

        List<UploadBean> list = uploadService.upload(mulRequest,userPath);
        UploadBean bean = list!=null&&list.size()>0?list.get(0):new UploadBean();
        UserEntity userEntity = userService.getCurrentUser();

        if(userEntity!=null){
            if(StringUtils.isNotBlank(userEntity.getHeadPortrait())){
                uploadService.removeFile(userEntity.getHeadPortrait());
            }
            userEntity.setHeadPortrait(bean.getRelativePath());
            userService.updateUser(userEntity);
        }

        return CommonResult.success(userEntity);
    }

    /**
     * 获取用户
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @ResponseBody
    @GetMapping("/info")
    public CommonResult<UserEntity> info(HttpServletRequest request) {
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
    @ResponseBody
    @GetMapping("/userCancel")
    public CommonResult userCancel() {
        //Subject subject = SecurityUtils.getSubject();
        //subject.logout();
        UserEntity userEntity = userService.getCurrentUser();
        if(userEntity!=null){

            userEntity.setStatus(StatusEnum.STATUS_INVALID);
            userService.updateUser(userEntity);
        }else{
            return CommonResult.error("用户登录已失效，请重新登陆！");
        }
        return CommonResult.success();
    }
}
