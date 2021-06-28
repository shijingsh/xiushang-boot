package com.xiushang.job.task;


import com.xiushang.common.job.service.DynamicTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 读取当天DynamicTask
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
