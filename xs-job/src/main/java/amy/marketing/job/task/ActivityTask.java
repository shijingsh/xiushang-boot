package amy.marketing.job.task;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import amy.marketing.common.api.CommonResult;
import amy.marketing.common.api.ZhiziyunResult;
import amy.marketing.dao.entity.AdActivityEntity;
import amy.marketing.dao.entity.SyncInfoDetailEntity;
import amy.marketing.dao.entity.SyncInfoEntity;
import amy.marketing.dao.entity.SyncInfoLogEntity;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.zhiziyun.ApiActivityAddParam;
import amy.marketing.job.dto.zhiziyun.ApiActivityAddResultParam;
import amy.marketing.job.dto.zhiziyun.ApiActivityDeleteParam;
import amy.marketing.job.dto.zhiziyun.ApiActivityDetailResultParam;
import amy.marketing.job.dto.zhiziyun.ApiActivityUpdateParam;
import amy.marketing.job.dto.zhiziyun.ApiActivityUpdateStatusParam;
import amy.marketing.job.utils.HttpPostZhiziyunUtils;
import amy.marketing.service.AdActivityService;
import amy.marketing.service.AdCreativeStatusService;
import amy.marketing.service.AdPromotionService;
import amy.marketing.service.SyncInfoDetailService;
import amy.marketing.service.SyncInfoLogService;
import amy.marketing.service.SyncInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;

/**
 * 广告活动
 * testTask为spring bean的名称， 方法名称必须是run
 *
 * @author fan
 * @version V1.0
 * @date 2021年4月2日
 */
@Component("activityTask")
public class ActivityTask implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private AdActivityService adActivityService;
    @Resource
    private AdCreativeStatusService adCreativeStatusService;
    @Resource
    private SyncInfoLogService syncInfoLogService;
    @Resource
    private SyncInfoService syncInfoService;
    @Resource
    private SyncInfoDetailService syncInfoDetailService;
    @Resource
    private AdPromotionService adPromotionService;
    
	public void run(String params){
		logger.debug("activityTask定时任务正在执行，参数为：{}", params);
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
	
	private String editApiActivityAddParam(AdActivityEntity byId){
				
		ApiActivityAddParam p = new ApiActivityAddParam();
		
		p.setDailybudget(byId.getDailybudget().toString());	
		p.setBudget(byId.getBudget().toString());
		
		p.setDeliveryStatus(byId.getDeliveryStatus());
		p.setEndTime(DateUtil.formatDateTime(byId.getEndTime()));
		p.setStartTime(DateUtil.formatDateTime(byId.getStartTime()));
		p.setLifeCycleClickCapping(byId.getLifeCycleClickCapping().longValue());
		p.setLifeCycleImpressionCapping(byId.getLifeCycleImpressionCapping().longValue());
		
		p.setName(byId.getName());
		p.setSiteId(byId.getSiteId());
		p.setToken(AgentConfig.token());
		
		return JSONUtil.toJsonStr(p);
	}
		
	private String editApiActivityUpdateParam(AdActivityEntity byId){

		ApiActivityUpdateParam p = new ApiActivityUpdateParam();
		
		p.setActivityId(byId.getAdvertiserId());
		p.setDailybudget(byId.getDailybudget().toString());	
		p.setBudget(byId.getBudget().toString());
		
		p.setDeliveryStatus(byId.getDeliveryStatus());
		p.setEndTime(DateUtil.formatDateTime(byId.getEndTime()));
		p.setStartTime(DateUtil.formatDateTime(byId.getStartTime()));
		p.setLifeCycleClickCapping(byId.getLifeCycleClickCapping().intValue());
		p.setLifeCycleImpressionCapping(byId.getLifeCycleImpressionCapping().intValue());
		
		p.setName(byId.getName());
		p.setSiteId(byId.getSiteId());
		p.setToken(AgentConfig.token());
		
		return JSONUtil.toJsonStr(p);
	}
	
	/**
	 * 广告活动添加
	 */
	public void add(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "";
		String p = "";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告活动ad_activity的主键
			
			AdActivityEntity byId = adActivityService.getById(sysId);
			
			url = "/api/activity/add";//广告活动添加
			p = editApiActivityAddParam(byId);
			
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
					ApiActivityAddResultParam data = JSONUtil.toBean(JSONUtil.toJsonStr(post.getData()), ApiActivityAddResultParam.class);
					
					syncInfoEntity.setThirdId(data.getActivityId());
					AdActivityEntity adPromotionEntity = new AdActivityEntity();
					adPromotionEntity.setId(sysId);
					adPromotionEntity.setActivityId(data.getActivityId());
					adPromotionEntity.setUpdateTime(new Date());
					adActivityService.updateById(adPromotionEntity);
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
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		this.updateSyncInfo(syncInfoEntity);
		this.updateSyncInfoDetail(syncInfoDetailEntity);
		this.insertSyncInfoLog(syncInfoDetailEntity);
		
	}
	
	
	/**
	 * 广告活动修改
	 */
	public void update(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "";
		String p = "";
			
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告活动ad_activity的主键
			
			AdActivityEntity byId = adActivityService.getById(sysId);
			
			url = "/api/activity/update";
			p = editApiActivityUpdateParam(byId);
			
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
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		this.updateSyncInfo(syncInfoEntity);
		this.updateSyncInfoDetail(syncInfoDetailEntity);
		this.insertSyncInfoLog(syncInfoDetailEntity);
		
	}

	
	/**
	 * 删除
	 */
	public void delete(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/activity/delete";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告活动ad_activity的主键
			
			AdActivityEntity byId = adActivityService.getById(sysId);
			
			ApiActivityDeleteParam d = new ApiActivityDeleteParam();
 
			d.setActivityIds(Arrays.asList(byId.getActivityId()));
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

	/**
	 * 广告活动开启/暂停
	 */
	public void updateStatus(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/activity/updateStatus";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告活动ad_activity的主键
			
			AdActivityEntity byId = adActivityService.getById(sysId);
			
			ApiActivityUpdateStatusParam d = new ApiActivityUpdateStatusParam();
			d.setActivityId(byId.getActivityId());
			d.setDeliveryStatus(byId.getDeliveryStatus());
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
	
	/**
	 * 广告活动详情
	 */
	public CommonResult detail(String activityId){
		 
		String resultmsg = "";
		String url = "";
		String p = "";
		try {
  
			url = "/api/activity/detail";//广告活动添加
	 
			ApiActivityAddResultParam ds = new ApiActivityAddResultParam();
			ds.setActivityId(activityId);
			p = JSONUtil.toJsonStr(ds);
			 
			ZhiziyunResult<?> post = HttpPostZhiziyunUtils.postzhiziyun(url, p);
			if(post==null){
				resultmsg = "系统异常";
				return CommonResult.failed(resultmsg);
			}else{
				resultmsg = JSONUtil.toJsonStr(post);
 
				if(post.isStatus()){
					//同步成功需要反写
					ApiActivityDetailResultParam data = JSONUtil.toBean(JSONUtil.toJsonStr(post.getData()), ApiActivityDetailResultParam.class);
					
					return CommonResult.success(data);
					
				}else{
					return CommonResult.failed(resultmsg);
				}
				
			}
			
		} catch (Exception e) {
			resultmsg = "系统异常"+e.getMessage();
			return CommonResult.failed(resultmsg);
		}
		
	}
	
	
	@Override
	public void run(String... args) throws Exception {
 
		// SyncInfoDetailEntity byId = syncInfoDetailService.getById("jaxdelete");
		
		// delete(JSONUtil.toJsonStr(byId));
	}
}
