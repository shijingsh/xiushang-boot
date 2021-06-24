package amy.marketing.job.task;

import amy.marketing.job.service.AdvertiserSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 智子云广告主信息同步
 *
 */
@Component("advertiserTask")
public class AdvertiserTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private AdvertiserSyncService advertiserSyncService;

	public void run(String params){
		logger.debug("mobileOperators定时任务正在执行，参数为：{}", params);

		advertiserSyncService.syncAdvertiser("liu");
	}
}
