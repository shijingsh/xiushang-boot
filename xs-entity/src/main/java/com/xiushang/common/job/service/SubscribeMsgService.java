package com.xiushang.common.job.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.user.service.SystemParamService;
import com.xiushang.entity.QSubscribeMsgAppointEntity;
import com.xiushang.entity.SubscribeMsgAppointEntity;
import com.xiushang.entity.SubscribeMsgEntity;
import com.xiushang.entity.SystemParamEntity;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.jpa.repository.SysSubscribeMsgAppointDao;
import com.xiushang.jpa.repository.SysSubscribeMsgDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class SubscribeMsgService {


  @Resource
  private SysSubscribeMsgDao subscribeMsgDao;

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
    SubscribeMsgEntity subscribeMsgEntity = subscribeMsgDao.getOne(code);
    if (null != subscribeMsgEntity) {
      if (subscribeMsgEntity.getStatus() >= 4) {
        dynamicTaskService.deleteTask(subscribeMsgEntity.getId());
        return false;
      }
      Date dateTime = subscribeMsgEntity.getStart();

      QSubscribeMsgAppointEntity entity = QSubscribeMsgAppointEntity.subscribeMsgAppointEntity;
      BooleanExpression ex =  entity.status.eq(1);
      ex = ex.and(entity.subscribeObjectId.eq(code));
      Iterable<SubscribeMsgAppointEntity> iterable =  sysSubscribeMsgAppointDao.findAll(ex);
      Iterator<SubscribeMsgAppointEntity> it = iterable.iterator();
      List<SubscribeMsgAppointEntity> appointCodeList = new ArrayList<>();
      while (it.hasNext()){

        SubscribeMsgAppointEntity subscribeMsgAppointEntity = it.next();
        //发送订阅消息（用户从微信小程序订阅一次接收一次，所以发完要删除预约记录）
        CommonResult<String> result = sendSubscribeMsg(code, subscribeMsgAppointEntity.getOpenId(), subscribeMsgEntity.getName(), dateTime);
        if (null != result && result.getErrorCode()== CommonResult.SUCCESS) {
          //记录发送成功的预约主键
          appointCodeList.add(subscribeMsgAppointEntity);
        }
      }
      if (CollectionUtil.isNotEmpty(appointCodeList)) {
        for (SubscribeMsgAppointEntity appointEntity:appointCodeList){
          //逻辑删除订阅消息发送成功的
          appointEntity.setStatus(0);
          sysSubscribeMsgAppointDao.save(appointEntity);
        }
        log.info(StrUtil.format("{}-{}: 有人预约!成功推送：{}条 ", code, subscribeMsgEntity.getName(),appointCodeList.size()));
      }

      return true;
    }
    return false;
  }

  /**
   * 发送订阅消息
   * @return
   */
  public CommonResult<String> sendSubscribeMsg(String code, String openId, String title, Date time){

    JSONObject data = new JSONObject();
    data.put("thing", new JSONObject().put("value", title));
    data.put("time", new JSONObject().put("value", DateUtil.formatDateTime(time)));

    JSONObject param = new JSONObject();
    param.put("touser", openId);
    param.put("data", data);
    param.put("id", code);
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("token", "amyyy");
    headerMap.put("Content-Type", "application/json;charset=UTF-8");
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
      String subscribeMsgTemplateId = PropertyConfigurer.getConfig("weixin.subscribeMsgTemplateId");;
      String miniprogramState = PropertyConfigurer.getConfig("weixin.miniprogramState");
      if(StringUtils.isBlank(miniprogramState)){
        miniprogramState = "formal";
      }
      String page = "/pages/index/index?switch=switch2&tabindex=2";
      if (null != param.get("id") && StrUtil.isNotBlank(param.get("id").toString())) {
        page= page + "&id=" + param.get("id");
      }
      param.put("access_token", accessToken);
      param.put("page", page);
      param.put("template_id", subscribeMsgTemplateId);
      param.put("miniprogram_state", miniprogramState);
      String jsonResult= HttpUtil.post(url,JSONUtil.toJsonStr(param));
      log.info("微信返回值:"+jsonResult);
      return CommonResult.success(jsonResult);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
