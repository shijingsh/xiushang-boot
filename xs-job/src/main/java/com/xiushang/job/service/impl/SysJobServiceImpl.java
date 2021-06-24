package com.xiushang.job.service.impl;

import com.xiushang.common.exception.ServiceException;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.SysJobEntity;
import com.xiushang.job.service.SysJobService;
import com.xiushang.job.utils.ScheduleUtils;
import com.xiushang.jpa.repository.SysJobDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务 服务类
 *
 */
@Service("sysJobService")
public class SysJobServiceImpl extends BaseServiceImpl<SysJobEntity> implements SysJobService {
    @Resource
    private SysJobDao sysJobMapper;

    @Override
    public void saveJob(SysJobEntity sysJob) {
        sysJob.setStatus(ScheduleUtils.SCHEDULER_STATUS_NORMAL);
        this.save(sysJob);
    }

    @Override
    public void updateJobById(SysJobEntity sysJob) {
        SysJobEntity sysJobEntity = this.get(sysJob.getId());
        if (sysJobEntity == null) {
            throw new ServiceException("获取定时任务异常");
        }
        sysJob.setStatus(sysJobEntity.getStatus());

        this.save(sysJob);
    }

    @Override
    public void delete(List<String> ids) {
        //sysJobMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(List<String> ids) {
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(List<String> ids) {
        updateBatch(ids, ScheduleUtils.SCHEDULER_STATUS_PAUSE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(List<String> ids) {
        updateBatch(ids, ScheduleUtils.SCHEDULER_STATUS_NORMAL);
    }

    @Override
    public void updateBatch(List<String> ids, int status) {
        for (String id:ids){
            SysJobEntity sysJobEntity = new SysJobEntity();
            sysJobEntity.setId(id);
            sysJobEntity.setStatus(status);
            sysJobMapper.save(sysJobEntity);
        }
    }
}
