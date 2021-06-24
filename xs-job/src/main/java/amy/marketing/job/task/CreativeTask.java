package amy.marketing.job.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import amy.marketing.common.api.ZhiziyunResult;
import amy.marketing.dao.entity.AdCreativeEntity;
import amy.marketing.dao.entity.AdCreativeStatusEntity;
import amy.marketing.dao.entity.AdPromotionEntity;
import amy.marketing.dao.entity.SyncInfoDetailEntity;
import amy.marketing.dao.entity.SyncInfoEntity;
import amy.marketing.dao.entity.SyncInfoLogEntity;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.zhiziyun.ApiCreativeStatusParam;
import amy.marketing.job.dto.zhiziyun.ApiCreativeStatusResultParam;
import amy.marketing.job.dto.zhiziyun.ApiMobileStaticCreativeAddParam;
import amy.marketing.job.dto.zhiziyun.ApiMobileStaticCreativeUpdateParam;
import amy.marketing.job.dto.zhiziyun.ApiPcStaticCreativeAddParam;
import amy.marketing.job.dto.zhiziyun.ApiPcStaticCreativeResultParam;
import amy.marketing.job.dto.zhiziyun.ApiPcStaticCreativeUpdateParam;
import amy.marketing.job.utils.HttpPostZhiziyunUtils;
import amy.marketing.service.AdCreativeService;
import amy.marketing.service.AdCreativeStatusService;
import amy.marketing.service.AdPromotionService;
import amy.marketing.service.SyncInfoDetailService;
import amy.marketing.service.SyncInfoLogService;
import amy.marketing.service.SyncInfoService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;

/**
 * 静态素材接口&任务
 * testTask为spring bean的名称， 方法名称必须是run
 *
 * @author fan
 * @version V1.0
 * @date 2021年4月2日
 */
@Component("creativeTask")
public class CreativeTask implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private AdCreativeService adCreativeService;
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
		logger.debug("creativeLinkTask定时任务正在执行，参数为：{}", params);
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
	
	private String editApiPcStaticCreativeAddParam(AdCreativeEntity byId){
				
		ApiPcStaticCreativeAddParam p = new ApiPcStaticCreativeAddParam();
		
		if(byId.getAddWaterMark()!=null && byId.getAddWaterMark()==1){
			p.setAddWaterMark(true);			
		}

		
		p.setCreativeResourceUrl(byId.getCreativeResourceUrl());

		AdPromotionEntity byId2 = adPromotionService.getById(byId.getPromotionId());
		p.setPromotionLinkId(byId2.getPromotionId());

		p.setName(byId.getName());
		p.setSiteId(byId.getSiteId());
		p.setToken(AgentConfig.token());
		
		return JSONUtil.toJsonStr(p);
	}
	
	private String editApiMobileStaticCreativeAddParam(AdCreativeEntity byId){
		
		ApiMobileStaticCreativeAddParam p = new ApiMobileStaticCreativeAddParam();
		
		p.setCreativeResourceUrl(byId.getCreativeResourceUrl());
		p.setMobileClickType(byId.getMobileClickType());
		p.setName(byId.getName());
		
		AdPromotionEntity byId2 = adPromotionService.getById(byId.getPromotionId());
		p.setPromotionLinkId(byId2.getPromotionId());
		
		p.setSiteId(byId.getSiteId());
		
		if(byId.getAddWaterMark()!=null && byId.getAddWaterMark()==1){
			p.setAddWaterMark(true);			
		}
		
		p.setAppDesc(byId.getAppDesc());
		p.setAppName(byId.getAppName());
		p.setAppPackageSize(byId.getAppPackageSize());
		p.setCvsFormType(byId.getCvsFormType());
		p.setMobileCbundle(byId.getMobileCbundle());
		p.setMobileFlowType(byId.getMobileFlowType());
		p.setToken(AgentConfig.token());
		
		
		return JSONUtil.toJsonStr(p);
	}
	
	
	private String editApiCreativeUpdateParam(AdCreativeEntity byId){

		ApiPcStaticCreativeUpdateParam p = new ApiPcStaticCreativeUpdateParam();
		
		p.setCreativeId(byId.getCreativeId());
		p.setName(byId.getName());
		AdPromotionEntity byId2 = adPromotionService.getById(byId.getPromotionId());
		p.setPromotionLinkId(byId2.getPromotionId());
		
		p.setToken(AgentConfig.token());
		
		return JSONUtil.toJsonStr(p);
	}
	
	private String editApiMobileStaticCreativeUpdateParam(AdCreativeEntity byId){

		ApiMobileStaticCreativeUpdateParam p = new ApiMobileStaticCreativeUpdateParam();
		
		p.setCreativeId(byId.getCreativeId());
		p.setCreativeResourceUrl(byId.getCreativeResourceUrl());
		p.setMobileClickType(byId.getMobileClickType());
		p.setName(byId.getName());
		AdPromotionEntity byId2 = adPromotionService.getById(byId.getPromotionId());
		p.setPromotionLinkId(byId2.getPromotionId());
		p.setAppDesc(byId.getAppDesc());
		p.setAppName(byId.getAppName());
		p.setAppPackageSize(byId.getAppPackageSize());
		p.setCvsFormType(byId.getCvsFormType());
		p.setMobileCbundle(byId.getMobileCbundle());
		p.setToken(AgentConfig.token());
		
		return JSONUtil.toJsonStr(p);
	}

	/**
	 * PC静态素材创建 OR 移动静态素材创建
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
			String sysId = syncInfoEntity.getSysId();//广告素材ad_creative的主键
			
			AdCreativeEntity byId = adCreativeService.getById(sysId);
			
			String type = byId.getType();//素材类型(STATIC：PC静态；MOBILE：移动静态)
			if("STATIC".equals(type)){
				url = "/api/creative/pc/static/add";//PC静态素材创建
				p = editApiPcStaticCreativeAddParam(byId);
			}else{
				url = "/api/creative/mobile/static/add";//移动静态素材创建
				p = editApiMobileStaticCreativeAddParam(byId);
			}
			
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
					ApiPcStaticCreativeResultParam data = JSONUtil.toBean(JSONUtil.toJsonStr(post.getData()), ApiPcStaticCreativeResultParam.class);
					
					syncInfoEntity.setThirdId(data.getCreativeId());
					AdCreativeEntity adPromotionEntity = new AdCreativeEntity();
					adPromotionEntity.setId(sysId);
					adPromotionEntity.setCreativeId(data.getCreativeId());
					adPromotionEntity.setUpdateTime(new Date());
					adCreativeService.updateById(adPromotionEntity);
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
	 * PC静态素材更新  OR 移动静态素材创建
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
			String sysId = syncInfoEntity.getSysId();//广告素材ad_creative的主键
			
			AdCreativeEntity byId = adCreativeService.getById(sysId);
			
			String type = byId.getType();//素材类型(STATIC：PC静态；MOBILE：移动静态)
			
			if("STATIC".equals(type)){
				url = "/api/creative/pc/static/update";//PC静态素材
				p = editApiCreativeUpdateParam(byId);
			}else{
				url = "/api/creative/mobile/static/update";//移动静态素材
				p = editApiMobileStaticCreativeUpdateParam(byId);
			}
			
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
		String url = "/api/creative/delete";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告素材ad_creative的主键
			
			AdCreativeEntity byId = adCreativeService.getById(sysId);
			
			ApiPcStaticCreativeUpdateParam d = new ApiPcStaticCreativeUpdateParam();
			d.setCreativeId(byId.getCreativeId());
			d.setCreativeType(byId.getType());
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
	 * 素材审核状态
	 * 取得最新状态后需要反写数据
	 */
	public void status(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/creative/status";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告素材ad_creative的主键
			
			AdCreativeEntity byId = adCreativeService.getById(sysId);
			
			ApiCreativeStatusParam d = new ApiCreativeStatusParam();
			d.setCreativeId(byId.getCreativeId());
			d.setCreativeType(byId.getType());
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
					//同步成功需要反写
					ApiCreativeStatusResultParam data = JSONUtil.toBean(JSONUtil.toJsonStr(post.getData()), ApiCreativeStatusResultParam.class);
					
					
					AdCreativeEntity adCreativeEntity = new AdCreativeEntity();
					adCreativeEntity.setId(sysId);
					adCreativeEntity.setCreativeId(data.getCreativeId());
					adCreativeEntity.setPretrialStatus(data.getPretrialStatus());
					adCreativeEntity.setPretrialMsg(data.getPretrialMsg());
					adCreativeEntity.setUpdateTime(new Date());
					adCreativeService.updateById(adCreativeEntity);
					
					//先删除
					AdCreativeStatusEntity deleteSql = new AdCreativeStatusEntity();
					deleteSql.setCreativeId(adCreativeEntity.getCreativeId());
					adCreativeStatusService.remove(new QueryWrapper<AdCreativeStatusEntity>(deleteSql));
					
					//再插入
					List<AdCreativeStatusEntity> dataStatus = data.getDataStatus();
					if(dataStatus!=null && dataStatus.size()>0){
						
						for (AdCreativeStatusEntity adCreativeStatusEntity : dataStatus) {
							adCreativeStatusEntity.setCreativeId(adCreativeEntity.getCreativeId());
							adCreativeStatusEntity.setId(IdUtil.fastSimpleUUID());
							adCreativeStatusEntity.setCreateId("Job");
							adCreativeStatusEntity.setCreateTime(new Date());
							adCreativeStatusService.save(adCreativeStatusEntity);
							
						}
					}
					
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
 
		 //SyncInfoDetailEntity byId = syncInfoDetailService.getById("jaxdelete");
		
		// delete(JSONUtil.toJsonStr(byId));
	}
}
