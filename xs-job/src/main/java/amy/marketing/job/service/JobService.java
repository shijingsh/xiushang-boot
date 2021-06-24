package amy.marketing.job.service;

import amy.marketing.dao.entity.SysJobEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 定时任务 服务类
 *
 * @author ZKUI
 * @version V1.0
 * @date 2021年4月2日
 */
public interface JobService extends IService<SysJobEntity> {

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

