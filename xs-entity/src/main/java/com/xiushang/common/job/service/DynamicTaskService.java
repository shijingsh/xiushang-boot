package com.xiushang.common.job.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.entity.QSubscribeMsgEntity;
import com.xiushang.entity.SubscribeMsgEntity;
import com.xiushang.common.job.task.DynamicTask;
import com.xiushang.jpa.repository.SysSubscribeMsgDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class DynamicTaskService {
  @Resource
  private SysSubscribeMsgDao subscribeMsgDao;

  @Autowired
  private DynamicTask dynamicTask;

  public void saveOrUpdateTask(SubscribeMsgEntity subscribeMsgEntity) {
    Date currentDate = DateUtil.date();
    Date dateTime = subscribeMsgEntity.getStart();
    if (currentDate.getTime() <= dateTime.getTime()) {
      List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
      addTodayTask(taskConstants, subscribeMsgEntity.getId(), subscribeMsgEntity.getStart());
    }
  }

  public void deleteTask(String id) {
    List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
    DynamicTask.TaskConstant constant = new DynamicTask.TaskConstant();
    constant.setTaskId(id);
    // 如果已经存在当前任务，先删除
    if (taskConstants.indexOf(constant) > 0) {
      taskConstants.remove(constant);
      log.info(StrUtil.format("当前预约消息推送任务 Remove：{}", id));
    }
  }

  public void refreshTodayTask() {
    List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
    taskConstants.clear();

    QSubscribeMsgEntity entity = QSubscribeMsgEntity.subscribeMsgEntity;
    //当前时间
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND,0);
    calendar.set(Calendar.SECOND,0);
    calendar.set(Calendar.MINUTE,0);
    calendar.set(Calendar.HOUR_OF_DAY,0);
    Date startTime = calendar.getTime();
    calendar.set(Calendar.HOUR_OF_DAY,24);
    Date endTime = calendar.getTime();

    //计算今天
    BooleanExpression ex =  entity.status.lt(4);
    ex = ex.and(entity.start.after(startTime));
    ex = ex.and(entity.start.before(endTime));


    //获取今天的
    Iterable<SubscribeMsgEntity> iterable =  subscribeMsgDao.findAll(ex);
    Iterator<SubscribeMsgEntity> it = iterable.iterator();
    while (it.hasNext()){
      SubscribeMsgEntity subscribeMsgEntity = it.next();
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

}
