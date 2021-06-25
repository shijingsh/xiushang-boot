package com.xiushang.job.service;


import cn.hutool.core.date.DateUtil;
import com.xiushang.entity.SubscribeMsgEntity;
import com.xiushang.job.task.DynamicTask;
import com.xiushang.jpa.repository.SysSubscribeMsgDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Slf4j
public class DynamicTaskService {
  @Resource
  private SysSubscribeMsgDao mapper;

  @Autowired
  private DynamicTask dynamicTask;

  public void saveOrUpdateTask(SubscribeMsgEntity liveBroadcast) {
    Date currentDate = new Date();
    Date dateTime = liveBroadcast.getStart();
    if (currentDate.getTime() <= dateTime.getTime()) {
      List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
      addTodayTask(taskConstants, liveBroadcast.getId(), liveBroadcast.getStart()+"");
    }
  }

  public void deleteTask(String id) {
    List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
    DynamicTask.TaskConstant constant = new DynamicTask.TaskConstant();
    constant.setTaskId(id);
    // 如果已经存在当前任务，先删除再添加
    if (taskConstants.indexOf(constant) > 0) {
      taskConstants.remove(constant);
      //log.info(StrUtil.format("当前预约观看消息推送任务 Remove：{}", id));
    }
  }

  public void refreshTodayTask() {
    List<DynamicTask.TaskConstant> taskConstants = dynamicTask.getTaskConstants();
    taskConstants.clear();
   /* Date currentDate = DateUtil.parseDate(DateUtil.formatDate(DateUtil.date()));
    ExhibitorLiveBroadcastExample example = new ExhibitorLiveBroadcastExample();
    example.createCriteria().andDeletedEqualTo(0).andStatusLessThan(4)
        .andDateAtLessThanOrEqualTo(currentDate)
        .andDateEndGreaterThanOrEqualTo(currentDate);
    //获取今天会开播的直播
    List<ExhibitorLiveBroadcast> liveBroadcasts = mapper.selectByExample(example);
    if (CollectionUtil.isNotEmpty(liveBroadcasts)) {
      liveBroadcasts.forEach(live -> {
        addTodayTask(taskConstants, live.getExhibitorLiveBroadcastCode(), live.getTimeAt());
      });
    }*/
    log.info("刷新今天的直播预约观看推送消息任务！");
  }

  private void addTodayTask(List<DynamicTask.TaskConstant> taskConstants, String id, String time) {
    DynamicTask.TaskConstant constant = new DynamicTask.TaskConstant();
    constant.setTaskId(id);
    // 如果已经存在当前任务，先删除再添加
    if (taskConstants.indexOf(constant) > 0) {
      taskConstants.remove(constant);
      //log.info(StrUtil.format("当前预约观看消息推送任务 Remove：{}", id));
    }
    String cron = DateUtil.format(new Date(), "ss mm HH dd MM ?");

    DynamicTask.TaskConstant taskConstant = new DynamicTask.TaskConstant();
    //taskConstant.setCron("0 0/1 * * * ?");//测试
    taskConstant.setCron(cron);
    taskConstant.setTaskId(id);
    taskConstants.add(taskConstant);
    //log.info(StrUtil.format("当前预约观看消息推送任务：{}", JSONUtil.toJsonStr(taskConstants)));
  }

}
