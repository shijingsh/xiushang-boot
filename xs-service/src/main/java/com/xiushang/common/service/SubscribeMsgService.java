package com.xiushang.common.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.user.service.SystemParamService;
import com.xiushang.entity.QSubscribeMsgAppointEntity;
import com.xiushang.entity.SubscribeMsgAppointEntity;
import com.xiushang.entity.SystemParamEntity;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.job.service.DynamicTaskService;
import com.xiushang.jpa.repository.SysSubscribeMsgAppointDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class SubscribeMsgService {

  @Resource
  private SysSubscribeMsgAppointDao sysSubscribeMsgAppointDao;

  @Autowired
  private DynamicTaskService dynamicTaskService;

  @Autowired
  private SystemParamService systemParamService;
  /**
   * 发送订阅消息
   * @return
   */
  public boolean sendSubscribeMsg(String code){
    SubscribeMsgAppointEntity msgAppointEntity = sysSubscribeMsgAppointDao.getOne(code);
    if (null != msgAppointEntity) {
      if (msgAppointEntity.getStatus() == 0 || msgAppointEntity.getPullStatus()==1) {
        dynamicTaskService.deleteTask(msgAppointEntity.getId());
        return false;
      }

      CommonResult<String> result = sendSubscribeMsg(msgAppointEntity);
      if (null != result && result.getErrorCode()== CommonResult.SUCCESS) {
        //订阅消息发送成功,设置为已推送
        msgAppointEntity.setPullStatus(1);
        sysSubscribeMsgAppointDao.save(msgAppointEntity);

        log.info(StrUtil.format("{}-{}: 有人预约!推送成功 ", code, msgAppointEntity.getName()));
      }

      return true;
    }
    return false;
  }

  /**
   * 发送订阅消息
   * @return
   */
  public CommonResult<String> sendSubscribeMsg(SubscribeMsgAppointEntity subscribeMsgAppointEntity){

    JSONObject data = new JSONObject();
    JSONObject paramJsonObject =  subscribeMsgAppointEntity.getParamJsonObject();
    for(String str:paramJsonObject.keySet()){
      data.put(str, new JSONObject().put("value", paramJsonObject.get(str)));
    }

    JSONObject param = new JSONObject();
    param.put("touser", subscribeMsgAppointEntity.getOpenId());
    param.put("data", data);
    param.put("id", subscribeMsgAppointEntity.getId());

    try {

      String grant_type = "client_credential";
      String appid = "";
      String secret = "";
      String shopId = "";
      if(StringUtils.isNotBlank(shopId)){
        SystemParamEntity systemParam = systemParamService.findByName(shopId,shopId+"_weixin.appid");
        if(systemParam==null){
          appid = PropertyConfigurer.getConfig("weixin.appid");
        }else{
          appid = systemParam.getParamValue();
        }

        SystemParamEntity paramSecret = systemParamService.findByName(shopId,shopId+"_weixin.secret");
        if(paramSecret==null){
          secret = PropertyConfigurer.getConfig("weixin.secret");
        }else{
          secret = paramSecret.getParamValue();
        }
      }
      String urlToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+appid+"&secret="+secret;
      String json = HttpUtil.get(urlToken);
      //获取AccessToken
      String accessToken = "";
      JSONObject jsonObject = JSON.parseObject(json);
      String errcode = jsonObject.getString("errcode");
      if (StringUtils.isBlank(errcode)) {
        accessToken = jsonObject.getString("access_token");
      }
      log.info("sendSubscribeMsg accessToken=================== {}", accessToken);

      String params = JSONUtil.toJsonStr(param);
      log.info("Request Params =================== {}", params);

      String url = PropertyConfigurer.getConfig("weixin.subscribeSendUrl");
      if(StringUtils.isBlank(url)){
        url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
      }
      String subscribeMsgTemplateId = subscribeMsgAppointEntity.getSubscribeMsgTemplateId();
      String miniprogramState = PropertyConfigurer.getConfig("weixin.miniprogramState");
      if(StringUtils.isBlank(miniprogramState)){
        miniprogramState = "formal";
      }
      String page = subscribeMsgAppointEntity.getPage();

      param.put("access_token", accessToken);
      param.put("page", page);
      param.put("template_id", subscribeMsgTemplateId);
      param.put("miniprogram_state", miniprogramState);
      String jsonResult= HttpUtil.post(url,JSONUtil.toJsonStr(param));
      log.info("发送订阅消息，微信返回值:"+jsonResult);
      return CommonResult.success(jsonResult);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
