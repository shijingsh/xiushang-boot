package amy.marketing.job.task;

import amy.marketing.dao.entity.SysDictDetailEntity;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.AgentDTO;

import amy.marketing.service.SysDictDetailService;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 智子云媒体类型
 *
 */
@Component("mediaCategoryTask")
public class MediaCategoryTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private SysDictDetailService sysDictDetailService;

	public void run(String params){
		logger.debug("mediaCategoryTask定时任务正在执行，参数为：{}", params);

		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setToken(AgentConfig.token());

		String param= JSONUtil.toJsonStr(agentDTO);
		String result=HttpUtil.post(AgentConfig.getUrl("/api-zcloud/zcloud/api/dictionary/zzyMediaCategories"),param);
		System.out.println(result);

		JSONObject object = JSONUtil.parseObj(result);
		JSONObject data = object.getJSONObject("data");
		Set<String> set = data.keySet();

		Iterator<String> iterator = set.iterator();
		List<SysDictDetailEntity> list = new ArrayList<>();

		AtomicInteger id = new AtomicInteger(0);
		while (iterator.hasNext()) {
			//保存媒体类型
			String key = iterator.next();
			JSONObject objectLevel1 = data.getJSONObject(key);
			//一级媒体类型
			SysDictDetailEntity detailEntity = new SysDictDetailEntity();
			detailEntity.setDictName(key);
			detailEntity.setLabel(key);
			detailEntity.setValue(key);
			detailEntity.setDictId("7");
			detailEntity.setSort(id.get());
			detailEntity.setId("MEDIA_TYPES"+id.get());
			detailEntity.setType("2");
			detailEntity.setPid("0");

			list.add(detailEntity);

			List<SysDictDetailEntity> pList = getLevel2(objectLevel1,id.get(), id);
			list.addAll(pList);
		}

		sysDictDetailService.saveOrUpdateBatch(list);
	}

	private List<SysDictDetailEntity> getLevel2(JSONObject objectLevel1,Integer pId, AtomicInteger id) {
		List<SysDictDetailEntity> list = new ArrayList<>();

		Set<String> set = objectLevel1.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			//保存二级
			String key = iterator.next();
			id.getAndAdd(1);

			SysDictDetailEntity detailEntity = new SysDictDetailEntity();
			detailEntity.setDictName(key);
			detailEntity.setLabel(key);
			detailEntity.setValue(key);
			detailEntity.setDictId("7");
			detailEntity.setSort(id.get());
			detailEntity.setId("MEDIA_TYPES"+id.get());
			detailEntity.setType("2");
			detailEntity.setPid("MEDIA_TYPES"+pId);
			list.add(detailEntity);

			JSONArray city = objectLevel1.getJSONArray(key);
			List<SysDictDetailEntity> pList = getLevel3(city,id.get(), id);
			list.addAll(pList);

		}
		return list;
	}

	private List<SysDictDetailEntity> getLevel3(JSONArray city, Integer pId, AtomicInteger id) {
		List<SysDictDetailEntity> list = new ArrayList<>();

		for(int i=0;i<city.size();i++) {
			//保存三级
			String key = city.getStr(i);
			id.getAndAdd(1);

			SysDictDetailEntity detailEntity = new SysDictDetailEntity();
			detailEntity.setDictName(key);
			detailEntity.setLabel(key);
			detailEntity.setValue(key);
			detailEntity.setDictId("7");
			detailEntity.setSort(id.get());
			detailEntity.setId("MEDIA_TYPES"+id.get());
			detailEntity.setType("2");
			detailEntity.setPid("MEDIA_TYPES"+pId);
			list.add(detailEntity);

			list.add(detailEntity);
		}

		return list;
	}
}
