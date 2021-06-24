package amy.marketing.job.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import amy.marketing.common.api.ZhiziyunResult;
import amy.marketing.common.utils.StringUtil;
import amy.marketing.dao.entity.AdPromotionEntity;
import amy.marketing.dao.entity.SyncInfoDetailEntity;
import amy.marketing.dao.entity.SyncInfoEntity;
import amy.marketing.dao.entity.SyncInfoLogEntity;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.zhiziyun.ApiPromotionLinkAddParam;
import amy.marketing.job.dto.zhiziyun.ApiPromotionLinkAddResultParam;
import amy.marketing.job.dto.zhiziyun.ApiPromotionLinkUpdateParam;
import amy.marketing.job.utils.HttpPostZhiziyunUtils;
import amy.marketing.service.AdPromotionService;
import amy.marketing.service.SyncInfoDetailService;
import amy.marketing.service.SyncInfoLogService;
import amy.marketing.service.SyncInfoService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;

/**
 * 推广页接口&任务
 * testTask为spring bean的名称， 方法名称必须是run
 *
 * @author fan
 * @version V1.0
 * @date 2021年4月2日
 */
@Component("promotionLinkTask")
public class PromotionLinkTask implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private AdPromotionService adPromotionService;
    @Resource
    private SyncInfoLogService syncInfoLogService;
    @Resource
    private SyncInfoService syncInfoService;
    @Resource
    private SyncInfoDetailService syncInfoDetailService;
    
	public void run(String params){
		logger.debug("promotionLinkTask定时任务正在执行，参数为：{}", params);
	}
	
	private void insertSyncInfoLog(SyncInfoDetailEntity p){
		SyncInfoLogEntity byId = new SyncInfoLogEntity();
		
		byId.setId(IdUtil.fastSimpleUUID());
		byId.setCreateTime(new Date());
		byId.setSyncId(p.getSyncId());
		byId.setSyncType(p.getSyncType());
		byId.setTriggerType(p.getTriggerType());
		byId.setUrl(p.getUrl());
		byId.setTime(p.getTime());
		byId.setParams(p.getParams());
		byId.setResults(p.getResults());
		byId.setSyncStatus(p.getSyncStatus());
		syncInfoLogService.save(byId);
	}
	
	private void updateSyncInfo(SyncInfoEntity params){
		params.setSyncTime(new Date());
		syncInfoService.updateById(params);
	}

	private void updateSyncInfoDetail(SyncInfoDetailEntity syncInfoDetailEntity){
		syncInfoDetailService.updateById(syncInfoDetailEntity);
	}
	
	private String editApiPromotionLinkAddParam(String sysId){
		
		
		AdPromotionEntity adPromotionEntity = adPromotionService.getById(sysId);
		
		ApiPromotionLinkAddParam p = new ApiPromotionLinkAddParam();
		List<String> splitAsList = StringUtil.splitAsList(adPromotionEntity.getAsyncClickUrls(), ",");
		if(splitAsList.size()>3){
			p.setAsyncClickUrls(splitAsList.subList(0, 3));
		}else{
			p.setAsyncClickUrls(splitAsList);
		}
		
		splitAsList = StringUtil.splitAsList(adPromotionEntity.getAsyncMonitorUrls(), ",");
		if(splitAsList.size()>3){
			p.setAsyncMonitorUrls(splitAsList.subList(0, 3));
		}else{
			p.setAsyncMonitorUrls(splitAsList);
		}
		
		splitAsList = StringUtil.splitAsList(adPromotionEntity.getMonitorUrls(), ",");
		if(splitAsList.size()>2){
			p.setMonitorUrls(splitAsList.subList(0, 2));
		}else{
			p.setMonitorUrls(splitAsList);
		}
		
		p.setDeeplinkUrl(adPromotionEntity.getDeepLinkUrl());
		p.setLandingPageUrl(adPromotionEntity.getLandingPageUrl());
		p.setName(adPromotionEntity.getName());
		p.setSiteId(adPromotionEntity.getSiteId());
		p.setTargetUrl(adPromotionEntity.getTargetUrl());
		p.setToken(AgentConfig.token());
		
		return JSONUtil.toJsonStr(p);
	}
	
	
	private String editApiPromotionLinkUpdateParam(String sysId){
		
		
		AdPromotionEntity adPromotionEntity = adPromotionService.getById(sysId);
		
		ApiPromotionLinkUpdateParam p = new ApiPromotionLinkUpdateParam();
		List<String> splitAsList = StringUtil.splitAsList(adPromotionEntity.getAsyncClickUrls(), ",");
		if(splitAsList.size()>3){
			p.setAsyncClickUrls(splitAsList.subList(0, 3));
		}else{
			p.setAsyncClickUrls(splitAsList);
		}
		
		splitAsList = StringUtil.splitAsList(adPromotionEntity.getAsyncMonitorUrls(), ",");
		if(splitAsList.size()>3){
			p.setAsyncMonitorUrls(splitAsList.subList(0, 3));
		}else{
			p.setAsyncMonitorUrls(splitAsList);
		}
		
		splitAsList = StringUtil.splitAsList(adPromotionEntity.getMonitorUrls(), ",");
		if(splitAsList.size()>2){
			p.setMonitorUrls(splitAsList.subList(0, 2));
		}else{
			p.setMonitorUrls(splitAsList);
		}
		
		p.setDeeplinkUrl(adPromotionEntity.getDeepLinkUrl());
		p.setLandingPageUrl(adPromotionEntity.getLandingPageUrl());
		p.setName(adPromotionEntity.getName());
		p.setTargetUrl(adPromotionEntity.getTargetUrl());
		p.setToken(AgentConfig.token());
		p.setPromotionLinkId(adPromotionEntity.getPromotionId());
		return JSONUtil.toJsonStr(p);
	}

	/**
	 * 推广页创建
	 */
	public void add(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/promotion/add";
		
		String id = bean.getId();
			
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(id);
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//推广页表ad_promotion的主键
			
			String p = editApiPromotionLinkAddParam(sysId);
			
			syncInfoDetailEntity.setParams(p);

			ZhiziyunResult<?> post = HttpPostZhiziyunUtils.postzhiziyun(url, p);
			if(post==null){
				resultcode = -1;
				resultmsg = "系统异常";
			}else{
				resultmsg = JSONUtil.toJsonStr(post);
				
				syncInfoDetailEntity.setTime((int) post.getReTime());
				
				if(post.isStatus()){
					resultcode = 1;
					//同步成功需要反写
					ApiPromotionLinkAddResultParam data = JSONUtil.toBean(JSONUtil.toJsonStr(post.getData()), ApiPromotionLinkAddResultParam.class);
					
					syncInfoEntity.setThirdId(data.getPromotionLinkId());
					AdPromotionEntity adPromotionEntity = new AdPromotionEntity();
					adPromotionEntity.setId(sysId);
					adPromotionEntity.setPromotionId(data.getPromotionLinkId());
					adPromotionEntity.setUpdateTime(new Date());
					adPromotionService.updateById(adPromotionEntity);
				}else{
					resultcode = 2;
				}
				
			}
			
		} catch (Exception e) {
			resultcode = -1;
			resultmsg = "系统异常"+e.getMessage();
		}
		
		syncInfoEntity.setSyncStatus(resultcode);
		syncInfoDetailEntity.setSyncStatus(resultcode);
		syncInfoDetailEntity.setResults(resultmsg);
		
		this.updateSyncInfo(syncInfoEntity);
		this.updateSyncInfoDetail(syncInfoDetailEntity);
		this.insertSyncInfoLog(syncInfoDetailEntity);
		
	}
	
	
	/**
	 * 推广页创建
	 */
	public void update(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/promotion/update";
		
		String id = bean.getId();
			
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(id);
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//推广页表ad_promotion的主键
			
			String p = editApiPromotionLinkUpdateParam(sysId);
			
			syncInfoDetailEntity.setParams(p);

			ZhiziyunResult<?> post = HttpPostZhiziyunUtils.postzhiziyun(url, p);
			if(post==null){
				resultcode = -1;
				resultmsg = "系统异常";
			}else{
				resultmsg = JSONUtil.toJsonStr(post);
				
				syncInfoDetailEntity.setTime((int) post.getReTime());
				
				if(post.isStatus()){
					resultcode = 1;
					
				}else{
					resultcode = 2;
				}
				
			}
			
		} catch (Exception e) {
			resultcode = -1;
			resultmsg = "系统异常"+e.getMessage();
		}
		syncInfoEntity.setSyncType(syncInfoDetailEntity.getSyncType());
		syncInfoEntity.setSyncStatus(resultcode);
		syncInfoDetailEntity.setSyncStatus(resultcode);
		syncInfoDetailEntity.setResults(resultmsg);
		
		this.updateSyncInfo(syncInfoEntity);
		this.updateSyncInfoDetail(syncInfoDetailEntity);
		this.insertSyncInfoLog(syncInfoDetailEntity);
		
	}

	
	/**
	 * 推广页创建
	 */
	public void delete(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/promotion/delete";
		
		String id = bean.getId();
			
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(id);
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			
			ApiPromotionLinkUpdateParam d = new ApiPromotionLinkUpdateParam();
			d.setPromotionLinkId(syncInfoEntity.getThirdId());
			d.setToken(AgentConfig.token());
			String p = JSONUtil.toJsonStr(d);
			
			syncInfoDetailEntity.setParams(p);

			ZhiziyunResult<?> post = HttpPostZhiziyunUtils.postzhiziyun(url, p);
			if(post==null){
				resultcode = -1;
				resultmsg = "系统异常";
			}else{
				resultmsg = JSONUtil.toJsonStr(post);
				
				syncInfoDetailEntity.setTime((int) post.getReTime());
				
				if(post.isStatus()){
					resultcode = 1;
				}else{
					resultcode = 2;
				}
				
			}
			
		} catch (Exception e) {
			resultcode = -1;
			resultmsg = "系统异常"+e.getMessage();
			
		}
		syncInfoEntity.setSyncType(syncInfoDetailEntity.getSyncType());
		syncInfoEntity.setSyncStatus(resultcode);
		syncInfoDetailEntity.setSyncStatus(resultcode);
		syncInfoDetailEntity.setResults(resultmsg);
		
		this.updateSyncInfo(syncInfoEntity);
		this.updateSyncInfoDetail(syncInfoDetailEntity);
		this.insertSyncInfoLog(syncInfoDetailEntity);
		
	}
	
	@Override
	public void run(String... args) throws Exception {
 
		// SyncInfoDetailEntity byId = syncInfoDetailService.getById("fan");
		
		//add(JSONUtil.toJsonStr(byId));
	}
}
