package com.mg.common.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mg.common.components.SmsService;
import com.mg.common.entity.InstanceEntity;
import com.mg.common.entity.SystemParamEntity;
import com.mg.common.entity.UserEntity;
import com.mg.common.user.service.SystemParamService;
import com.mg.common.user.service.UserService;
import com.mg.common.user.vo.PhoneDecryptInfo;
import com.mg.common.user.vo.ThirdLoginVo;
import com.mg.common.user.vo.ThirdUserVo;
import com.mg.common.utils.AESGetPhoneNumber;
import com.mg.common.utils.HttpClientUtil;
import com.mg.common.utils.JsonUtils;
import com.mg.framework.log.CommonResult;
import com.mg.framework.sys.PropertyConfigurer;
import com.mg.framework.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户登录/退出
 */
@Api(tags = "用户管理")
@Controller
@RequestMapping(value = "/",
        produces = "application/json; charset=UTF-8")
public class LoginController {
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SystemParamService systemParamService;

    @ApiOperation(value = "手机号码登陆")
    @ResponseBody
    @RequestMapping("/login")
    public CommonResult<UserEntity> login(@RequestBody UserEntity userEntity) {

        if (StringUtils.isBlank(userEntity.getLoginName()) || StringUtils.isBlank(userEntity.getPassword())) {
            return CommonResult.error(100000, "用户名,密码不能为空。");
        }

        //Subject subject = SecurityUtils.getSubject();
        //判断是否启用多实例
        String userToken = getInstanceUserToken(userEntity);
        //subject.getSession().setAttribute(Constants.TENANT_ID, null);
        //切换数据库到默认实例
        InstanceEntity instanceEntity = null;
        if (StringUtils.isNotBlank(userToken)) {
           // instanceEntity = instanceService.findInstanceByToken(userToken);
        }
        if (instanceEntity != null) {
           // subject.getSession().setAttribute(Constants.TENANT_ID, instanceEntity.getId());
        }
        try {
           // UsernamePasswordToken token = new UsernamePasswordToken(userEntity.getLoginName(), MD5.GetMD5Code(userEntity.getPassword()));
            //subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(100000, e.getMessage());
        }
        UserEntity user = userService.getUser(userEntity.getLoginName());
        if(user!=null){
            user.setLastLoginPlatform(userEntity.getLastLoginPlatform());
            userService.updateUserLastLoginDate(user);
        }

        return CommonResult.success(user);
    }

    /**
     * 微信授权登陆APP
     * apple 授权登陆APP
     * @return
     */
    @ApiOperation(value = "第三方授权登陆")
    @ResponseBody
    @RequestMapping("/loginThird")
    public CommonResult<UserEntity> loginThird(@RequestBody ThirdUserVo thirdUserVo) {

        System.out.println("loginThird登录中：");
        System.out.println(JsonUtils.toJsonStr(thirdUserVo));
        if ((StringUtils.isBlank(thirdUserVo.getUnionId()) && StringUtils.isBlank(thirdUserVo.getAppleId()) )
                || StringUtils.isBlank(thirdUserVo.getAccessToken())) {
            return CommonResult.error(100000, "没有第三方授权信息。");
        }

        //Subject subject = SecurityUtils.getSubject();
        //判断是否启用多实例
        String userToken = thirdUserVo.getUserToken();
        //subject.getSession().setAttribute(Constants.TENANT_ID, null);
        //切换数据库到默认实例
        InstanceEntity instanceEntity = null;
        if (StringUtils.isNotBlank(userToken)) {
            //instanceEntity = instanceService.findInstanceByToken(userToken);
        }
        if (instanceEntity != null) {
            //subject.getSession().setAttribute(Constants.TENANT_ID, instanceEntity.getId());
        }
        UserEntity userEntity = null;
        try {
            if (StringUtils.isNotBlank(thirdUserVo.getMobile())) {
                UserEntity mobileUser = userService.getUserByMobile(thirdUserVo.getMobile());

                String code = thirdUserVo.getVerifyCode();
                if (StringUtils.isBlank(code)) {
                    return CommonResult.error(100002, "验证码不能为空。");
                }
                if(smsService.validateCode(thirdUserVo.getMobile(),code)){
                    if (mobileUser!=null) {
                        //直接关联已经存在的用户
                        userEntity = userService.saveThirdUser(thirdUserVo,mobileUser);
                    }else{
                        userEntity = userService.saveThirdUser(thirdUserVo);
                    }
                }else{
                    return CommonResult.error(100002, "验证码输入错误");
                }
            }else {
                userEntity = userService.getThirdUser(thirdUserVo);
                if(userEntity==null || StringUtils.isBlank(userEntity.getMobile())){
                    return CommonResult.error(100001, "手机号码不能为空。");
                }
            }

           // UsernamePasswordToken token = new UsernamePasswordToken(userEntity.getLoginName(), userEntity.getPassword());
           // subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(100000, e.getMessage());
        }
        if(userEntity!=null){
            userEntity.setLastLoginPlatform(thirdUserVo.getLastLoginPlatform());
            userService.updateUserLastLoginDate(userEntity);
        }

        return CommonResult.success(userEntity);
    }


    /**
     * 小程序登陆
     * @return
     */
    @ApiOperation(value = "小程序登陆")
    @ResponseBody
    @RequestMapping("/weixinLogin")
    public CommonResult<UserEntity> weixinLogin(@RequestBody ThirdLoginVo loginVo) {

        System.out.println("weixinLogin登录中：");
        System.out.println(JsonUtils.toJsonStr(loginVo));
        String code = loginVo.getCode();
        String userToken = loginVo.getUserToken();
        String shopId = loginVo.getShopId();
        String nickName = loginVo.getNickName();
        String avatarUrl = loginVo.getAvatarUrl();
        String gender = loginVo.getGender();
        String email = loginVo.getEmail();
        if (StringUtils.isBlank(code)) {
            return CommonResult.error(100000, "微信code不能为空。");
        }
        String appid = null;
        String secret = null;
        if (StringUtils.isNotBlank(code)){
            if(StringUtils.isNotBlank(shopId)){
                SystemParamEntity param = systemParamService.findByName(shopId,shopId+"_weixin.appid");
                if(param==null){
                    appid = PropertyConfigurer.getConfig("weixin.appid");
                }else{
                    appid = param.getParamValue();
                }

                SystemParamEntity paramSecret = systemParamService.findByName(shopId,shopId+"_weixin.secret");
                if(paramSecret==null){
                    secret = PropertyConfigurer.getConfig("weixin.secret");
                }else{
                    secret = paramSecret.getParamValue();
                }
            }

            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code=" + code + "&grant_type=authorization_code";

            String json = HttpClientUtil.sendGetRequest(url);
            System.out.println("jscode2session返回：");
            System.out.println(json);
            JSONObject jsonObject = JSON.parseObject(json);
            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isBlank(errcode)) {
                //Subject subject = SecurityUtils.getSubject();
                //判断是否启用多实例
                //subject.getSession().setAttribute(Constants.TENANT_ID, null);
                //切换数据库到默认实例
                InstanceEntity instanceEntity = null;
                if (StringUtils.isNotBlank(userToken)) {
                   // instanceEntity = instanceService.findInstanceByToken(userToken);
                }
                if (instanceEntity != null) {
                   // subject.getSession().setAttribute(Constants.TENANT_ID, instanceEntity.getId());
                }
                UserEntity userEntity = null;
                try {
                    String unionId = jsonObject.getString("unionid");
                    System.out.println("weixinLogin返回unionid："+unionId);
                    if (StringUtils.isBlank(unionId)){
                        unionId =  jsonObject.getString("openid");
                        System.out.println("weixinLogin返回openid："+unionId);
                    }

                    String mobile = null;
                    String sessionKey = jsonObject.getString("session_key");
                    if(StringUtils.isNotBlank(loginVo.getEncryptedData()) && StringUtils.isNotBlank(loginVo.getIv())){
                        AESGetPhoneNumber aes = new AESGetPhoneNumber(loginVo.getEncryptedData(),sessionKey,loginVo.getIv());
                        PhoneDecryptInfo info = aes.decrypt();
                        if (null==info){
                            System.out.println("error");
                            return CommonResult.error(100003, "解密微信手机号发生错误，会话超时。");
                        }else {
                            System.out.println("======================解密微信手机号========================");
                            System.out.println(JsonUtils.toJsonStr(info));
                        }
                        if(info!=null && StringUtils.isNotBlank(info.getPhoneNumber())){
                            mobile = info.getPhoneNumber();
                        }
                    }

                    ThirdUserVo thirdUserVo = new ThirdUserVo();
                    thirdUserVo.setUnionId(unionId);
                    thirdUserVo.setLoginName(mobile);
                    thirdUserVo.setAccessToken(sessionKey);
                    thirdUserVo.setUserAvatar(avatarUrl);
                    thirdUserVo.setUserName(nickName);
                    thirdUserVo.setUserGender(gender);
                    thirdUserVo.setMobile(mobile);
                    thirdUserVo.setEmail(email);

                    if(StringUtils.isNotBlank(mobile)){
                        UserEntity mobileUser = userService.getUserByMobile(mobile);
                        if (mobileUser!=null) {
                            //直接关联已经存在的用户
                            userEntity = userService.saveThirdUser(thirdUserVo,mobileUser);
                        }else{
                            userEntity = userService.getUserByUnionId(unionId);
                            //根据unionId找到了，则修改，没有找到则新增用户
                            if(userEntity!=null){
                                userEntity = userService.saveThirdUser(thirdUserVo,userEntity);
                            }else {
                                userEntity = userService.saveThirdUser(thirdUserVo);
                            }
                        }
                    }else{
                        //没有传手机号，并且根据unionId没有找到
                        userEntity = userService.getUserByUnionId(unionId);
                        if (userEntity==null || StringUtils.isBlank(userEntity.getMobile())) {
                            return CommonResult.error(100001, "手机号码不能为空。");
                        }
                        userEntity = userService.saveThirdUser(thirdUserVo,userEntity);
                    }


                    //UsernamePasswordToken token = new UsernamePasswordToken(userEntity.getLoginName(), userEntity.getPassword());
                    //subject.login(token);
                } catch (Exception e) {
                    e.printStackTrace();
                    return CommonResult.error(100000, e.getMessage());
                }
                if(userEntity!=null){
                    userEntity.setLastLoginPlatform(loginVo.getLastLoginPlatform());
                    userService.updateUserLastLoginDate(userEntity);
                }

                return CommonResult.success(userEntity);
            }
        }

        return CommonResult.success(null);
    }

    /**
     * 获取公司实例
     *
     * @param userEntity
     * @return
     */
    protected String getInstanceUserToken(UserEntity userEntity) {
        if (StringUtils.isNotBlank(userEntity.getUserToken())) {
            return userEntity.getUserToken();
        }

        return null;
    }

    /**
     * 退出
     */
    @ApiOperation(value = "退出登陆")
    @ResponseBody
    @RequestMapping("/loginOut")
    public CommonResult loginOut() {
        //Subject subject = SecurityUtils.getSubject();
        //subject.logout();
        return CommonResult.success();
    }

    @ApiOperation(value = "获取微信Token")
    @ResponseBody
    @RequestMapping("/weixinToken")
    public CommonResult<String> weixinToken(String grant_type,String appid,String secret) {

        if (StringUtils.isNotBlank(grant_type)){

            if(StringUtils.isBlank(appid)){
                appid = PropertyConfigurer.getConfig("weixin.appid");
            }
            if(StringUtils.isBlank(secret)) {
                secret = PropertyConfigurer.getConfig("weixin.secret");
            }
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+appid+"&secret="+secret;

            String json = HttpClientUtil.sendGetRequest(url);
            JSONObject jsonObject = JSON.parseObject(json);
            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isBlank(errcode)) {
                String access_token = jsonObject.getString("access_token");
                return CommonResult.success(access_token);
            }
        }

        return CommonResult.success(null);
    }

    /**
     * 解密并且获取用户手机号码
     */
    @ResponseBody
    @RequestMapping("/deciphering")
    public  CommonResult<PhoneDecryptInfo> deciphering(String encryptedData,
                                            String iv, String sessionKey,
                                            HttpServletRequest request) {

        System.out.println("加密的敏感数据:" + encryptedData);
        System.out.println("初始向量:" + iv);
        System.out.println("会话密钥:" + sessionKey);
        //String appId = "XXXXXXXXX";
        AESGetPhoneNumber aes = new AESGetPhoneNumber(encryptedData,sessionKey,iv);
        PhoneDecryptInfo info = aes.decrypt();
        if (null==info){
            System.out.println("error");
        }else {
            System.out.println(info.toString());
            //if (!info.getWatermark().getAppId().equals(appId)){
            //    System.out.println("wrong appId");
            //}
        }

        if(info!=null && StringUtils.isNotBlank(info.getPhoneNumber())){
            UserEntity user = userService.getUserByRequest(request);
            user.setMobile(info.getPhoneNumber());
            user.setLastLoginDate(new Date());
            userService.updateUser(user);
        }


        return CommonResult.success(info);
    }


    @ResponseBody
    @RequestMapping("/bindingMobile")
    public CommonResult<UserEntity> bindingMobile() {

        String jsonString = WebUtil.getJsonBody(req);
        UserEntity userEntity = JSON.parseObject(jsonString, UserEntity.class);
        if (StringUtils.isBlank(userEntity.getLoginName())) {
            return CommonResult.error(100000, "用户名不能为空。");
        }
        if (StringUtils.isBlank(userEntity.getMobile())) {
            return CommonResult.error(100001, "手机号码不能为空。");
        }
        if (StringUtils.isBlank(userEntity.getVerifyCode())) {
            return CommonResult.error(100002, "验证码不能为空。");
        }
        UserEntity user = userService.getUser(userEntity.getLoginName());
        if (user == null) {
            return CommonResult.error(100003, "用户尚未注册");
        }
        UserEntity userMobile = userService.getUserByMobile(userEntity.getMobile());
        if (userMobile != null) {
            return CommonResult.error(100003, "手机号码已被其他用户占用，请更换");
        }
        String code = userEntity.getVerifyCode();
        if(smsService.validateCode(userEntity.getMobile(),code)){
            user.setMobile(userEntity.getMobile());
            if(StringUtils.isBlank(user.getLoginName())){
                user.setLoginName(userEntity.getMobile());
            }
            if(StringUtils.isBlank(user.getName())){
                user.setName(userEntity.getMobile());
            }
            userService.updateUser(user);
        }else{
            return CommonResult.error(100004, "验证码输入错误");
        }
        return CommonResult.success(user);
    }

}
