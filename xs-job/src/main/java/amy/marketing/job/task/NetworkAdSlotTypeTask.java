package amy.marketing.job.task;

import amy.marketing.dao.entity.SysDictDetailEntity;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.AgentDTO;
import amy.marketing.job.dto.bz.dict.NetworkAdAgentDTO;
import amy.marketing.job.dto.bz.dict.NetworkAdAgentParamDTO;

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
 * 平台广告位类型
 *
 */
@Component("networkAdSlotTypeTask")
public class NetworkAdSlotTypeTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private SysDictDetailService sysDictDetailService;

	public void run(String params){
		logger.debug("networkAdSlotTypeTask定时任务正在执行，参数为：{}", params);

		NetworkAdAgentParamDTO paramDTO = JSONUtil.toBean(params,NetworkAdAgentParamDTO.class);

		List<String> list = paramDTO.getNetworkId();
		if(list!=null && list.size()>0){
			for (String networkId:list){
				NetworkAdAgentDTO agentDTO = new NetworkAdAgentDTO();
				agentDTO.setToken(AgentConfig.token());
				agentDTO.setNetworkId(networkId);
				agentDTO.setMobile(paramDTO.isMobile());

				doOne(agentDTO);
			}
		}

	}

	private void doOne(NetworkAdAgentDTO agentDTO){
		String param= JSONUtil.toJsonStr(agentDTO);
		String result=HttpUtil.post(AgentConfig.getUrl("/api-zcloud/zcloud/api/dictionary/networkAdSlotTypes"),param);
		System.out.println(result);

		JSONObject object = JSONUtil.parseObj(result);
		JSONObject data = object.getJSONObject("data");
		if(data!= null){
			Set<String> set = data.keySet();

			Iterator<String> iterator = set.iterator();
			List<SysDictDetailEntity> list = new ArrayList<>();
			int index = 0;
			while (iterator.hasNext()) {
				String pid = "AD_SLOT_"+agentDTO.getNetworkId();
				if(agentDTO.isMobile()){
					pid = pid+"_1";
				}else {
					pid = pid+"_0";
				}
				String id = pid+index;
				//保存移动设备类型
				String key = iterator.next();
				String name = data.getStr(key);
				SysDictDetailEntity detailEntity = new SysDictDetailEntity();
				detailEntity.setDictName(name);
				detailEntity.setLabel(name);
				detailEntity.setValue(key);
				detailEntity.setDictId("5");
				detailEntity.setSort(index);
				detailEntity.setId(id);
				detailEntity.setPid(pid);
				detailEntity.setType("2");

				list.add(detailEntity);

				index++;
			}

			sysDictDetailService.saveOrUpdateBatch(list);
		}
	}
}
