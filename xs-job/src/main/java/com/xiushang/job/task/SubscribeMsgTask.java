package com.xiushang.job.task;


import com.xiushang.common.service.DynamicTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 读取当天DynamicTask
 * 处理 type==0 的订阅 系统默认处理方式
 * 自定义处理类，需自定义task
 */
@Component
public class SubscribeMsgTask {

    private final DynamicTaskService dynamicTaskService;

    public SubscribeMsgTask(DynamicTaskService dynamicTaskService) {
        this.dynamicTaskService = dynamicTaskService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public synchronized void initTask() {
        try{
            dynamicTaskService.refreshTodayTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
