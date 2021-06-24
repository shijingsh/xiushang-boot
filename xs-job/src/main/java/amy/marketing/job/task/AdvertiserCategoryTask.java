package amy.marketing.job.task;

import amy.marketing.dao.entity.SysDictDetailEntity;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.AgentDTO;

import amy.marketing.service.SysDictDetailService;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 智子云广告主行业
 *
 */
@Component("advertiserCategoryTask")
public class AdvertiserCategoryTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private SysDictDetailService sysDictDetailService;

	public void run(String params){
		logger.debug("mobileOperators定时任务正在执行，参数为：{}", params);

		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setToken(AgentConfig.token());

		String param= JSONUtil.toJsonStr(agentDTO);
		String result=HttpUtil.post(AgentConfig.getUrl("/api-zcloud/zcloud/api/dictionary/zzyAdvertiserCategories"),param);
		System.out.println(result);
		//AD_CATEGORIES
		JSONObject object = JSONUtil.parseObj(result);
		JSONObject data = object.getJSONObject("data");
		Set<String> set = data.keySet();

		Iterator<String> iterator = set.iterator();
		List<SysDictDetailEntity> list = new ArrayList<>();
		int index = 0;
		while (iterator.hasNext()) {
			//保存移动设备类型
			String key = iterator.next();
			String name = data.getStr(key);
			SysDictDetailEntity detailEntity = new SysDictDetailEntity();
			detailEntity.setDictName(name);
			detailEntity.setLabel(name);
			detailEntity.setValue(key);
			detailEntity.setDictId("6");
			detailEntity.setSort(index);
			detailEntity.setId("AD_CATEGORIES"+index);
			detailEntity.setType("2");

			list.add(detailEntity);

			index++;
		}

		sysDictDetailService.saveOrUpdateBatch(list);
	}
}
