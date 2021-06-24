package com.xiushang.job.service.impl;

import com.xiushang.common.exception.ServiceException;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.SysJobEntity;
import com.xiushang.job.service.SysJobService;
import com.xiushang.job.utils.ScheduleUtils;
import com.xiushang.jpa.repository.SysJobDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务 服务类
 *
 */
@Service("sysJobService")
@Slf4j
public class SysJobServiceImpl extends BaseServiceImpl<SysJobEntity> implements SysJobService {
    @Resource
    private Scheduler scheduler;
    @Resource
    private SysJobDao sysJobDao;
    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<SysJobEntity> scheduleJobList = sysJobDao.findAll();
        log.info("加载定时任务：{}个",scheduleJobList.size());
        for (SysJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public void saveJob(SysJobEntity sysJob) {
        sysJob.setStatus(ScheduleUtils.SCHEDULER_STATUS_NORMAL);
        this.save(sysJob);

        ScheduleUtils.createScheduleJob(scheduler, sysJob);
    }

    @Override
    public void updateJobById(SysJobEntity sysJob) {
        SysJobEntity sysJobEntity = sysJobDao.getOne(sysJob.getId());
        if (sysJobEntity == null) {
            throw new ServiceException("获取定时任务异常");
        }
        sysJob.setStatus(sysJobEntity.getStatus());
        ScheduleUtils.updateScheduleJob(scheduler, sysJob);

        sysJobDao.save(sysJob);
    }

    @Override
    public void delete(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
            sysJobDao.deleteById(jobId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.run(scheduler, sysJobDao.getOne(jobId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(ids, ScheduleUtils.SCHEDULER_STATUS_PAUSE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(ids, ScheduleUtils.SCHEDULER_STATUS_NORMAL);
    }

    @Override
    public void updateBatch(List<String> ids, int status) {
        ids.parallelStream().forEach(id -> {
            SysJobEntity sysJobEntity = new SysJobEntity();
            sysJobEntity.setId(id);
            sysJobEntity.setStatus(status);
            sysJobDao.save(sysJobEntity);
        });
    }
}
