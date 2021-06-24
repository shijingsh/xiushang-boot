package com.xiushang.job.service;

import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.SysJobEntity;

import java.util.List;

/**
 * 定时任务 服务类
 *
 */
public interface SysJobService extends BaseService<SysJobEntity> {

    /**
     * 保存job
     *
     * @param sysJob sysJob
     */
    void saveJob(SysJobEntity sysJob);

    /**
     * 更新job
     *
     * @param sysJob sysJob
     */
    void updateJobById(SysJobEntity sysJob);

    /**
     * 删除job
     *
     * @param ids ids
     */
    void delete(List<String> ids);

    /**
     * 运行一次job
     *
     * @param ids ids
     */
    void run(List<String> ids);

    /**
     * 暂停job
     *
     * @param ids ids
     */
    void pause(List<String> ids);

    /**
     * 恢复job
     *
     * @param ids ids
     */
    void resume(List<String> ids);

    /**
     * 批量更新状态
     *
     * @param ids    ids
     * @param status status
     */
    void updateBatch(List<String> ids, int status);
}

