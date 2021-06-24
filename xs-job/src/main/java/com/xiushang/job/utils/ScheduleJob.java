package com.xiushang.job.utils;

import com.xiushang.entity.SysJobEntity;
import com.xiushang.entity.SysJobLogEntity;
import com.xiushang.framework.log.ContextLookup;
import com.xiushang.job.service.SysJobLogService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;


/**
 * 定时任务
 *
 */
public class ScheduleJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) {
        SysJobEntity scheduleJob = (SysJobEntity) context.getMergedJobDataMap()
                .get(SysJobEntity.JOB_PARAM_KEY);

        //获取spring bean
        SysJobLogService scheduleJobLogService = (SysJobLogService) ContextLookup.getBean("sysJobLogService");

        //数据库保存执行记录
        SysJobLogEntity log = new SysJobLogEntity();
        log.setJobId(scheduleJob.getId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setParams(scheduleJob.getParams());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            logger.debug("任务准备执行，任务ID：" + scheduleJob.getId());

            Object target = ContextLookup.getBean(scheduleJob.getBeanName());
            assert target != null;
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, scheduleJob.getParams());

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);
            //任务状态    0：成功    1：失败
            log.setStatus(0);

            logger.debug("任务执行完毕，任务ID：" + scheduleJob.getId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);

            //任务状态    0：成功    1：失败
            log.setStatus(1);
            log.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            assert scheduleJobLogService != null;
            scheduleJobLogService.save(log);
        }
    }

}
