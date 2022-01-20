package com.xiushang.common.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.job.DynamicTask;
import com.xiushang.common.job.vo.AppointItemVo;
import com.xiushang.common.job.vo.SubscribeMsgAppointVo;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.utils.JsonUtils;
import com.xiushang.entity.QSubscribeMsgAppointEntity;
import com.xiushang.entity.SubscribeMsgAppointEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.jpa.repository.SysSubscribeMsgAppointDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class DynamicTaskService   {

  @Resource
  private SysSubscribeMsgAppointDao sysSubscribeMsgAppointDao;

  @Autowired
  private DynamicTask dynamicTask;
  @Autowired
  public HttpServletRequest request;
  @Autowired
  private UserService userService;

  public void deleteTask(String id) {
    List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
    DynamicTask.TaskConstant constant = new DynamicTask.TaskConstant();
    constant.setTaskId(id);

    // 删除
    if (taskConstants.indexOf(constant) > 0) {
      taskConstants.remove(constant);
      log.info(StrUtil.format("当前预约消息推送任务 Remove：{}", id));
    }
  }

  public void refreshTodayTask() {
    List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
    taskConstants.clear();

    QSubscribeMsgAppointEntity entity = QSubscribeMsgAppointEntity.subscribeMsgAppointEntity;
    //当前时间
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND,0);
    calendar.set(Calendar.SECOND,0);
    calendar.set(Calendar.MINUTE,0);
    calendar.set(Calendar.HOUR_OF_DAY,0);
    Date startTime = calendar.getTime();
    calendar.set(Calendar.HOUR_OF_DAY,24);
    Date endTime = calendar.getTime();

    //计算今天  只处理 type==0 的推送
    BooleanExpression ex =  entity.pullStatus.eq(0);
    ex = ex.and(entity.type.eq(0));
    ex = ex.and(entity.start.after(startTime));
    ex = ex.and(entity.start.before(endTime));

    //获取今天的
    Iterable<SubscribeMsgAppointEntity> iterable =  sysSubscribeMsgAppointDao.findAll(ex);
    Iterator<SubscribeMsgAppointEntity> it = iterable.iterator();
    while (it.hasNext()){
      SubscribeMsgAppointEntity subscribeMsgEntity = it.next();
      addTodayTask(taskConstants, subscribeMsgEntity.getId(), subscribeMsgEntity.getStart());
    }

    log.info("刷新今天的预约推送消息任务！");
  }

  private void addTodayTask(List<DynamicTask.TaskConstant> taskConstants, String id, Date dateTime) {
    DynamicTask.TaskConstant constant = new DynamicTask.TaskConstant();
    constant.setTaskId(id);
    // 如果已经存在当前任务，先删除再添加
    if (taskConstants.indexOf(constant) > 0) {
      taskConstants.remove(constant);
      log.info(StrUtil.format("当前预约消息推送任务 Remove：{}", id));
    }
    String cron = DateUtil.format(dateTime, "ss mm HH dd MM ?");

    DynamicTask.TaskConstant taskConstant = new DynamicTask.TaskConstant();
    //taskConstant.setCron("0 0/1 * * * ?");//测试
    taskConstant.setCron(cron);
    taskConstant.setTaskId(id);
    taskConstants.add(taskConstant);
    log.info(StrUtil.format("当前预约消息推送任务：{}", JSONUtil.toJsonStr(taskConstants)));
  }

  public Integer getSubscribeStatus(String subscribeObjectId) {
    UserEntity userEntity = userService.getCurrentUser();

    List<SubscribeMsgAppointEntity> list = sysSubscribeMsgAppointDao.findBySubscribeObjectIdAndUserId(subscribeObjectId,userEntity.getId());
    if(list!=null && list.size()>0){
      return 1;
    }

    return 0;
  }

  @Transactional
  public Boolean appoint(SubscribeMsgAppointVo appointVo) {
    UserEntity userEntity = userService.getCurrentUser();
    ShopEntity shopEntity = userService.getCurrentTenantShop();

    List<SubscribeMsgAppointEntity> dbList = sysSubscribeMsgAppointDao.findBySubscribeObjectIdAndUserId(appointVo.getSubscribeObjectId(),userEntity.getId());
    if(dbList!=null && dbList.size()>0){
      //已订阅
      return false;
    }

    List<AppointItemVo> list = appointVo.getList();
    List<SubscribeMsgAppointEntity> saveList = new ArrayList<>();
    if(list!= null && list.size()>0){
      for (AppointItemVo appointItemVo:list){

        SubscribeMsgAppointEntity appointEntity =  new SubscribeMsgAppointEntity();
        appointEntity.setUserId(userEntity.getId());
        appointEntity.setShopId(shopEntity.getId());
        appointEntity.setName(appointVo.getName());
        appointEntity.setOpenId(appointVo.getOpenId());
        appointEntity.setPullStatus(0);
        appointEntity.setType(appointItemVo.getType());
        appointEntity.setStart(appointItemVo.getStart());
        appointEntity.setEnd(appointItemVo.getEnd());
        appointEntity.setSubscribeObjectId(appointVo.getSubscribeObjectId());
        appointEntity.setSubscribeMsgTemplateId(appointItemVo.getSubscribeMsgTemplateId());
        appointEntity.setPage(appointItemVo.getPage());

        if(appointItemVo.getParamJsonObject()!=null){
          appointEntity.setParamJson(JsonUtils.toJsonStr(appointItemVo.getParamJsonObject()));
        }

        saveList.add(appointEntity);
      }
    }

    sysSubscribeMsgAppointDao.saveAll(saveList);

    //判断是否达到推送条件
    Date currentDate = DateUtil.date();
    for (SubscribeMsgAppointEntity appointEntity:saveList){
      Date dateTime = appointEntity.getStart();
      //type 0 默认  DynamicTask 来处理
      if (appointEntity.getType()==0 && currentDate.getTime() <= dateTime.getTime()) {
        List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
        addTodayTask(taskConstants, appointEntity.getId(), appointEntity.getStart());
      }
    }

    return true;
  }

  @Transactional
  public void appointCancel(String subscribeObjectId) {

    UserEntity userEntity = userService.getCurrentUser();
    List<SubscribeMsgAppointEntity> list = sysSubscribeMsgAppointDao.findBySubscribeObjectIdAndUserId(subscribeObjectId,userEntity.getId());
    if(list!=null && list.size()>0){
      for (SubscribeMsgAppointEntity msgAppointEntity:list){
        //取消订阅，直接删除
        sysSubscribeMsgAppointDao.delete(msgAppointEntity);
      }
    }

  }
}
