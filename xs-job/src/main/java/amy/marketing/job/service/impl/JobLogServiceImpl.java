package amy.marketing.job.service.impl;

import amy.marketing.dao.entity.SysJobLogEntity;
import amy.marketing.dao.mapper.SysJobLogMapper;
import amy.marketing.service.SysJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 定时任务 服务类
 *
 * @author ZKUI
 * @version V1.0
 * @date 2021年4月2日
 */
@Service
public class JobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLogEntity> implements SysJobLogService {


}