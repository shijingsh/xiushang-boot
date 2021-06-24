package amy.marketing.job.task;

import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.AgentDTO;

import amy.marketing.service.BusinessAreaService;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商圈城市同步任务
 *
 */
@Component("businessAreaCityTask")
public class BusinessAreaCityTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BusinessAreaService businessAreaService;

	public void run(String params){
		logger.debug("businessAreaCityTask定时任务正在执行，参数为：{}", params);

		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setToken(AgentConfig.token());

		String param= JSONUtil.toJsonStr(agentDTO);
		String result=HttpUtil.post(AgentConfig.getUrl("/api-zcloud/zcloud/api/dictionary/businessAreaCity"),param);
		System.out.println(result);

		businessAreaService.saveAllAreaCity(result);
	}
}
