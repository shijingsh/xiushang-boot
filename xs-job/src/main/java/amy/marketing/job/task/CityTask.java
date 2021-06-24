package amy.marketing.job.task;

import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.bz.city.CityAgentDTO;

import amy.marketing.service.SysRegionalService;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 城市同步任务
 *  0 0/10 * * * ?
 *  0 15 23 ? * *
 */
@Component("cityTask")
public class CityTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysRegionalService sysRegionalService;

	public void run(String params){
		logger.debug("TestTask定时任务正在执行，参数为：{}", params);

		CityAgentDTO agentDTO = new CityAgentDTO();
		agentDTO.setToken(AgentConfig.token());

		String param= JSONUtil.toJsonStr(agentDTO);
		//Request URL: http://mc-test.zhiziyun.com/api-zcloud/zcloud/api/dictionary/cities
		String result=HttpUtil.post(AgentConfig.getUrl("/api-zcloud/zcloud/api/dictionary/cities"),param);
		System.out.println(result);

		sysRegionalService.saveAllRegional(result);
	}
}
