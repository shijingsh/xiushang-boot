package com.xiushang.job.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.xiushang.entity.SubscribeMsgEntity;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.jpa.repository.SysSubscribeMsgAppointDao;
import com.xiushang.jpa.repository.SysSubscribeMsgDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class SubscribeMsgService {


  @Resource
  private SysSubscribeMsgDao mapper;

  @Resource
  private SysSubscribeMsgAppointDao liveBroadcastAppointMapper;

  @Autowired
  private DynamicTaskService dynamicTaskService;

  /**
   * 发送订阅消息
   * @return
   */
  public boolean sendSubscribeMsg(String code){
    SubscribeMsgEntity liveBroadcast = mapper.getOne(code);
    if (null != liveBroadcast) {
      if (liveBroadcast.getStatus() >= 4) {
        dynamicTaskService.deleteTask(liveBroadcast.getId());
        return false;
      }
      String dateTimeStr = DateUtil.formatDate(DateUtil.date()).concat(" ").concat(liveBroadcast.getStart()+"");
      Date dateTime = DateUtil.parseDateTime(dateTimeStr);
     /* LiveBroadcastAppointExample example = new LiveBroadcastAppointExample();
      example.createCriteria().andDeletedEqualTo(0).andExhibitorLiveBroadcastCodeEqualTo(code);
      List<LiveBroadcastAppoint> liveBroadcastAppointList = liveBroadcastAppointMapper
          .selectByExample(example);
      if (CollectionUtil.isNotEmpty(liveBroadcastAppointList)) {
        log.info(StrUtil.format("{}-{}: 有人预约! ", code, liveBroadcast.getName()));
        List<String> liveBroadcastAppointCodeList = new ArrayList<>();
        liveBroadcastAppointList.forEach(live -> {
          //发送订阅消息（用户从微信小程序订阅一次接收一次，所以发完要删除预约记录）
          CommonResult<String> result = sendSubscribeMsg(code, live.getOpenId(), liveBroadcast.getName(), dateTime);
          if (null != result && result.getCode().equals(ResultCode.SUCCESS.getErrorCode())) {
            //记录发送成功的预约主键
            liveBroadcastAppointCodeList.add(live.getLiveBroadcastAppointCode());
          }
        });
        if (CollectionUtil.isNotEmpty(liveBroadcastAppointCodeList)) {
          example.clear();
          example.createCriteria().andLiveBroadcastAppointCodeIn(liveBroadcastAppointCodeList);
          //逻辑删除订阅消息发送成功的
          LiveBroadcastAppoint liveBroadcastAppoint = new LiveBroadcastAppoint();
          liveBroadcastAppoint.setDeleted(1);
          liveBroadcastAppointMapper.updateByExampleSelective(liveBroadcastAppoint, example);
        }
      } else {
        //log.warn(StrUtil.format("{}-{}: 没有人预约! ", code, liveBroadcast.getName()));
      }*/
      return true;
    }
    return false;
  }

  /**
   * 发送订阅消息
   * @return
   */
  public CommonResult<String> sendSubscribeMsg(String code, String openId, String title, Date time){
    //从third转发出去
    JSONObject param = new JSONObject();
    param.put("touser", openId);
    JSONObject data = new JSONObject();
    data.put("thing1", new JSONObject().put("value", title));
    data.put("time2", new JSONObject().put("value", DateUtil.formatDateTime(time)));
    param.put("data", data);
    param.put("id", code);
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("token", "amyyy");
    headerMap.put("Content-Type", "application/json;charset=UTF-8");
    try {
      String params = JSONUtil.toJsonStr(param);
      log.info("Request Params =================== {}", params);
     /* CommonResult<String> post = OkHttpUtils.post(thirdurl+"/wx/subscribe/msg", params, headerMap, CommonResult.class);
      if(post.getCode().equals(CommonResult.success().getCode())){
        String data2 = post.getData();
        log.info("sendSubscribeMsg =================== {}", data2);
      }
      return post;*/

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
