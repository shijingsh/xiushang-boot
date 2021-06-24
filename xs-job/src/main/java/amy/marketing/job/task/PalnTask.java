package amy.marketing.job.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import amy.marketing.common.api.CommonResult;
import amy.marketing.common.api.ZhiziyunResult;
import amy.marketing.common.utils.DateUtils;
import amy.marketing.common.utils.StringUtil;
import amy.marketing.dao.entity.AdPlanCreativeEntity;
import amy.marketing.dao.entity.AdPlanCrowdEntity;
import amy.marketing.dao.entity.AdPlanEntity;
import amy.marketing.dao.entity.AdPlanNetworkEntity;
import amy.marketing.dao.entity.AdPlanRegionalEntity;
import amy.marketing.dao.entity.AdPlanTargetEntity;
import amy.marketing.dao.entity.AdPlanTimeEntity;
import amy.marketing.dao.entity.SyncInfoDetailEntity;
import amy.marketing.dao.entity.SyncInfoEntity;
import amy.marketing.dao.entity.SyncInfoLogEntity;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.zhiziyun.ApiPlanAddParam;
import amy.marketing.job.dto.zhiziyun.ApiPlanAddResultParam;
import amy.marketing.job.dto.zhiziyun.ApiPlanDeleteParam;
import amy.marketing.job.dto.zhiziyun.ApiPlanDetailResultParam;
import amy.marketing.job.dto.zhiziyun.ApiPlanUpdateCreativeStausParam;
import amy.marketing.job.dto.zhiziyun.ApiPlanUpdateParam;
import amy.marketing.job.dto.zhiziyun.ApiPlanUpdateStatusParam;
import amy.marketing.job.utils.HttpPostZhiziyunUtils;
import amy.marketing.service.AdPlanCreativeService;
import amy.marketing.service.AdPlanCrowdService;
import amy.marketing.service.AdPlanNetworkService;
import amy.marketing.service.AdPlanRegionalService;
import amy.marketing.service.AdPlanService;
import amy.marketing.service.AdPlanTargetService;
import amy.marketing.service.AdPlanTimeService;
import amy.marketing.service.SyncInfoDetailService;
import amy.marketing.service.SyncInfoLogService;
import amy.marketing.service.SyncInfoService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;

/**
 * 广告计划接口
 * testTask为spring bean的名称， 方法名称必须是run
 *
 * @author fan
 * @version V1.0
 * @date 2021年4月2日
 */
@Component("palnTask")
public class PalnTask implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private SyncInfoLogService syncInfoLogService;
    @Resource
    private SyncInfoService syncInfoService;
    @Resource
    private SyncInfoDetailService syncInfoDetailService;
    @Resource
    private AdPlanRegionalService adPlanRegionalService;
    @Resource
    private AdPlanCreativeService adPlanCreativeService;
    @Resource
    private AdPlanCrowdService adPlanCrowdService;
    @Resource
    private AdPlanNetworkService adPlanNetworkService;
    @Resource
    private AdPlanTargetService adPlanTargetService;
    @Resource
    private AdPlanTimeService adPlanTimeService;
    @Resource
    private AdPlanService adPlanService;
    
	public void run(String params){
		logger.debug("palnTask定时任务正在执行，参数为：{}", params);
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
	
	private ApiPlanAddParam editApiPlanAddParam(AdPlanEntity byId){
				
		ApiPlanAddParam p = new ApiPlanAddParam();
		
		//活动编号*/
		p.setActivityId(byId.getActivityId());
		  // 频率策略：受众每天投放频次 默认：-1（不限）*/
		p.setAdDeliveryFrequencyPerDay(byId.getAdDeliveryFrequencyPerDay());
		  // 频率策略：受众每小时投放频次 默认：-1（不限）*/
		p.setAdDeliveryFrequencyPerHour(byId.getAdDeliveryFrequencyPerHour());  
		  // 频率策略：受众每月投放频次 默认：-1（不限） */
		p.setAdDeliveryFrequencyPerMonth(byId.getAdDeliveryFrequencyPerMonth());
		  // 频率策略：受众每周投放频次 默认：-1（不限） */
		p.setAdDeliveryFrequencyPerWeek(byId.getAdDeliveryFrequencyPerWeek());
		  //出价策略：自动优化  出价类型非CPM时必填[0.01, 20000000]*/
		if(byId.getAutomaticOptimization()!=null){
			if(byId.getAutomaticOptimization()==1){
				p.setAutomaticOptimization(true);
			}else{
				p.setAutomaticOptimization(false);
			}
		}
		  // 竞价模式,RTB：RTB竞价、PDB：PDB竞价；目前仅开放：RTB；默认：RTB  */
		p.setBidMode(byId.getBidMode());
		  // 预算策略：计划总预算 */
		p.setBudget(byId.getBudget().toString());
		  // 地域策略：商圈 */
		Map<String,Object> planIdMap = new HashMap<String,Object>();
		planIdMap.put("plan_id", byId.getId());
		List<AdPlanRegionalEntity> listAdPlanRegionalEntity = 
					adPlanRegionalService.listByMap(planIdMap);
		List<AdPlanRegionalEntity> coordinates = new ArrayList<AdPlanRegionalEntity>();//自定义商圈 
		List<String> businessAreaIds = new ArrayList<String>();//商圈ID
		
		Map<String,List<String>> targetLocations = new HashMap<String,List<String>>();//省市区
		
		for (AdPlanRegionalEntity adPlanRegionalEntity : listAdPlanRegionalEntity) {
			Integer type = adPlanRegionalEntity.getType();//已选类型：0.省；1.市；2.商圈；3.海外地区
			if(type==2){
				//如果是商圈
				String baId = adPlanRegionalEntity.getBaId();
				if(StringUtil.isBlank(baId)){
					//商圈编号(为空时是自定义商圈)
					coordinates.add(adPlanRegionalEntity);
				}else{
					//商圈编号
					businessAreaIds.add(baId);
				}
			}else if(type==0){
				//0.省
				String province = adPlanRegionalEntity.getProvince();
				List<String> l1 = null;
				if(!targetLocations.containsKey(province)){
					l1 = new ArrayList<String>();
				}else{
					l1 = targetLocations.get(province);
				}
				l1.add("*");
				targetLocations.put(province, l1);
				
			}else if(type==1){
				//1.市
				String province = adPlanRegionalEntity.getProvince();
				String city = adPlanRegionalEntity.getCity();
				String district = adPlanRegionalEntity.getDistrict();
				if(StringUtil.isNotBlank(district)){
					city = city +"-"+ district;
				}
				List<String> l1 = null;
				if(!targetLocations.containsKey(province)){
					l1 = new ArrayList<String>();
				}else{
					l1 = targetLocations.get(province);
				}
				l1.add(city);
				targetLocations.put(province, l1);
				
			}else if(type==3){
				//3.海外地区
				String district = adPlanRegionalEntity.getDistrict();
				List<String> l1 = null;
				if(!targetLocations.containsKey(district)){
					l1 = new ArrayList<String>();
				}else{
					l1 = targetLocations.get(district);
				}
				l1.add("*");
				targetLocations.put(district, l1);
			}
		}
		p.setBusinessAreaIds(businessAreaIds);
		  // 地域策略：自定义商圈 
		  //每个项目要包含latitude（纬度）、longitude（经度）、distance（半径）、name（名称）四个字段；
		  //例：[{'latitude':25.5666,'longitude':25.5666, 'distance':1000, 'name':'测试商圈名称'}] 
		  //与地域互斥，优先使用地域 */
		p.setCoordinates(coordinates);
		  // 素材编号 有效的素材编号集合（过滤无效素材） */
		List<AdPlanCreativeEntity> listByMap = adPlanCreativeService.listByMap(planIdMap);
		List<String> creativeIds = new ArrayList<String>();
		for (AdPlanCreativeEntity adPlanCreativeEntity : listByMap) {
			creativeIds.add(adPlanCreativeEntity.getCreativeId());
		}
		p.setCreativeIds(creativeIds);
		  // 计划素材类型STATIC:PC静态素材、VIDEO:PC视频素材、NATIVE:PC信息流素材、
		  //MOBILE:移动静态素材、MOBILEVIDEO:移动视频素材、MOBILENATIVE:移动信息流素材,
		  //可用值:STATIC,DYNAMIC,VIDEO,NATIVE,MOBILE,MOBILEDYNAMIC,MOBILEVIDEO,MOBILENATIVE,LINKUNIT */
		p.setCreativeType(byId.getCreativeType());
		  // 人群策略：最大有效期 人群类型为INTEREST、CUSTOM、PROBE时有效 */
		p.setCrowdMaxExpiry(byId.getCrowdMaxExpiry());
		  // 人群策略：最小有效期 人群类型为REDIRECT、INTEREST、CUSTOM、PROBE时有效 */
		p.setCrowdMinExpiry(byId.getCrowdMinExpiry());
		  // 预算策略：每日点击上限 默认：-1（不限） */
		if(byId.getDailyClickCapping()==null){
			p.setDailyClickCapping(-1);
		}else{
			p.setDailyClickCapping(byId.getDailyClickCapping().intValue());		
		}
		  // 计划日预算 不小于10 */
		p.setDailybudget(byId.getDailybudget().toString());
		  // 结束时间 yyyy-MM-dd HH:mm:ss */
		p.setEndTime(DateUtils.format(byId.getEndTime(), DateUtils.DATE_TIME_PATTERN));
		  // 人群策略：排除人群编号 人群类型为CUSTOM、PROBE时有效 */
		List<AdPlanCrowdEntity> listByMapAdPlanCrowdEntity = adPlanCrowdService.listByMap(planIdMap);
		List<String> tagIdsList = new ArrayList<String>();
		List<String> excludeCrowdList = new ArrayList<String>();
		for (AdPlanCrowdEntity adPlanCrowdEntity : listByMapAdPlanCrowdEntity) {
			Integer exclude = adPlanCrowdEntity.getExclude();
			if(exclude==1){
				excludeCrowdList.add(adPlanCrowdEntity.getTagId());
			}else{
				tagIdsList.add(adPlanCrowdEntity.getTagId());
			}
		}
		p.setExcludeCrowdList(excludeCrowdList);

		  // 设备策略：IP排除
		  //与IP定向互斥，优先使用IP定向 */
		planIdMap.put("type", 7);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
		List<AdPlanTargetEntity> listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
		
		List<String> includedIpsets = new ArrayList<String>();
		List<String> excludedIpsets = new ArrayList<String>();
		for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
			Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
			if(exclude==1){
				excludedIpsets.add(adPlanTargetEntity.getTargetId());
			}else{
				includedIpsets.add(adPlanTargetEntity.getTargetId());
			}
		}
		p.setExcludedIpsets(excludedIpsets);

		  // 移动设备策略：移动设备品牌排除
		  //移动计划有效 */
		planIdMap.put("type", 4);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
		listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
		List<String> mobileDeviceMakes = new ArrayList<String>();
		List<String> excludedMobileDeviceMakes = new ArrayList<String>();
		for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
			Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
			if(exclude==1){
				excludedMobileDeviceMakes.add(adPlanTargetEntity.getTargetId());
			}else{
				mobileDeviceMakes.add(adPlanTargetEntity.getTargetId());
			}
		}
		
		p.setExcludedMobileDeviceMakes(excludedMobileDeviceMakes);
		  // 移动设备策略：移动设备型号排除
		  //移动计划有效；与移动设备型号定向互斥，优先使用移动设备型号定向 */
		planIdMap.put("type", 5);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
		listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
		List<String> mobileDeviceModels = new ArrayList<String>();
		List<String> excludedMobileDeviceModels = new ArrayList<String>();
		for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
			Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
			if(exclude==1){
				excludedMobileDeviceModels.add(adPlanTargetEntity.getTargetId());
			}else{
				mobileDeviceModels.add(adPlanTargetEntity.getTargetId());
			}
		}
		p.setExcludedMobileDeviceModels(excludedMobileDeviceModels);
		  // 移动设备策略：移动设备操作系统排除
		  //移动计划有效；与移动设备操作系统定向互斥，优先使用移动设备操作系统定向 */
		planIdMap.put("type", 6);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
		listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
		List<String> mobileDeviceOss = new ArrayList<String>();
		List<String> excludedMobileDeviceOss = new ArrayList<String>();
		for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
			Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
			if(exclude==1){
				excludedMobileDeviceOss.add(adPlanTargetEntity.getTargetId());
			}else{
				mobileDeviceOss.add(adPlanTargetEntity.getTargetId());
			}
		}
		p.setExcludedMobileDeviceOss(excludedMobileDeviceOss);
		
		  // 出价策略：出价浮动方式,可用值:FIXED,CLICK_RATE_FLOATING,ARRIVAL_RATE_FLOATING
		  //FIXED：固定出价、CLICK_RATE_FLOATING：点击率浮动、ARRIVAL_RATE_FLOATING：到达率浮动；默认：FIXED；出价类型为CPM时必填 */
		p.setFloating(byId.getFloating());
		  // 人群策略：相似匹配
		  //人群类型为INTEREST、CUSTOM、PROBE时有效 */
		if(byId.getFuzzyMatching()!=null && byId.getFuzzyMatching()==1){
			p.setFuzzyMatching(true);;
		}else{
			p.setFuzzyMatching(false);;
		}
		
		  // 设备策略：IP定向
		  //与IP排除互斥，优先使用IP定向 */
		p.setIncludedIpsets(includedIpsets);		
		  //预算策略：计划周期内总点击数  默认：-1（不限）  */
		p.setLifeCycleClickCapping(byId.getLifeCycleClickCapping());
		  // 预算策略：计划周期内总曝光数 默认：-1（不限） */
		p.setLifeCycleImpressionCapping(byId.getLifeCycleImpressionCapping());
		  // 预算策略：每日曝光上限 默认：-1（不限） */
		p.setMaxCPMCountPerDay(byId.getMaxCpmCountPerDay());
		  // 出价策略：出价模型,可用值:ONEDIMENSIONAL,MULTIDIMENSIONAL
		  //ONEDIMENSIONAL：单维模型、MULTIDIMENSIONAL：多维模型；默认：ONEDIMENSIONAL（暂时只支持默认值） */
		p.setModel(byId.getModel());
		  
		  // 移动设备策略：仅对有设备号的设备
		  //移动计划有效 */
		if(byId.getMustHasDeviceId()!=null && byId.getMustHasDeviceId()==1){
			p.setMustHasDeviceId(true);
		}else{
			p.setMustHasDeviceId(false);;
		}
		
		  // 平台编号  */
		List<AdPlanNetworkEntity> listByMapAdPlanNetworkEntity = 
				adPlanNetworkService.listByMap(planIdMap);
		List<String> networkIds = new ArrayList<String>();
		for (AdPlanNetworkEntity adPlanNetworkEntity : listByMapAdPlanNetworkEntity) {
			networkIds.add(adPlanNetworkEntity.getNetworkId());
		}
		p.setNetworkIds(networkIds);
		
		  // 人群策略：防作弊过滤等级,可用值:none,loose,general,strict
		  //none：没有策略、loose：宽松策略、general：普通策略、strict：严格策略 */
		p.setNhtFilterLevel(byId.getNhtFilterLevel());
		
		  // 出价策略：优化价格
		  //非自动优化时必填 */
		p.setOptimizePrice(byId.getOptimizePrice().toPlainString());
		  // 出价策略：消耗方式 SOON:尽快消耗、EVEN：均匀消耗；默认：EVEN */
		p.setPacingType(byId.getPacingType());
		  // PC设备策略：PC定向浏览器类型
		  //PC计划有效 */
		planIdMap.put("type", 1);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
		listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
		List<String> pcTargetBrowserTypes = new ArrayList<String>();
		List<String> excludedBrowserTypes = new ArrayList<String>();
		for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
			Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
			if(exclude==1){
				excludedBrowserTypes.add(adPlanTargetEntity.getTargetId());
			}else{
				pcTargetBrowserTypes.add(adPlanTargetEntity.getTargetId());
			}
		}
		p.setPcTargetBrowserTypes(pcTargetBrowserTypes);
		  // PC设备策略：PC定向设备类型
		  //PC计划有效 */
		planIdMap.put("type", 0);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
		listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
		List<String> pcTargetDeviceTypes = new ArrayList<String>();
		List<String> excludedpcTargetDeviceTypes = new ArrayList<String>();
		for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
			Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
			if(exclude==1){
				excludedpcTargetDeviceTypes.add(adPlanTargetEntity.getTargetId());
			}else{
				pcTargetDeviceTypes.add(adPlanTargetEntity.getTargetId());
			}
		}
		p.setPcTargetDeviceTypes(excludedpcTargetDeviceTypes);
		  // 计划名称  */
		p.setPlanName(byId.getName());
		  // 平台类型,PC：PC平台、MOBILE:移动平台 */
		p.setPlatform(byId.getPlatform());;
		  // 出价价格 [0.01, 20000000] */
		p.setPrice(byId.getPrice().toPlainString());;
		  // 出价策略：出价类型,CPM：CPM出价、CPC：CPC出价、CPUV：CPUV出价；默认：CPM */
		p.setPricingType(byId.getPricingType());
		  // 出价策略：反量比例
		  //0-99；bidMode为PDB时必填 */
		p.setRtnRate(byId.getRtnRate());

		  // 广告主编号*/
		p.setSiteId(byId.getSiteId());
		  // 开始时间 yyyy-MM-dd HH:mm:ss */
		p.setStartTime(DateUtils.format(byId.getStartTime(), DateUtils.DATE_TIME_PATTERN));
		  // 人群策略：人群类型
		  //ALL：全网智能优化、REDIRECT：重定向人群、ADX：ADX人群（暂不支持）、INTEREST：兴趣人群、
		  //INDUSTRY：行业人群、CUSTOM：自定义人群（移动人群）、PROBE：探测人群（移动人群） */
		  p.setTagCrowdType(byId.getTagCrowdType());;
		  // 人群策略：人群编号  */
		  p.setTagIdsList(tagIdsList);
		  // 移动设备策略：运营商类型
		  //移动计划有效 */
		  planIdMap.put("type", 3);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
			listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
			List<String> targetAppMobileCarriers = new ArrayList<String>();
			List<String> excludedtargetAppMobileCarriers = new ArrayList<String>();
			for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
				Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
				if(exclude==1){
					excludedtargetAppMobileCarriers.add(adPlanTargetEntity.getTargetId());
				}else{
					targetAppMobileCarriers.add(adPlanTargetEntity.getTargetId());
				}
			}
			p.setTargetAppMobileCarriers(excludedtargetAppMobileCarriers);
		  // 移动设备策略：联网方式
		  //移动计划有效  */
		planIdMap.put("type", 2);//定向设备类型：0.设备类型；1.浏览器类型；2.联网方式；3.运营商类型；4.设备品牌；5.设备型号；6.操作系统；7.IP
		listByMapAdPlanTargetEntity = adPlanTargetService.listByMap(planIdMap);
		List<String> targetAppMobileConnectionTypes = new ArrayList<String>();
		List<String> excludedtargetAppMobileConnectionTypes = new ArrayList<String>();
		for (AdPlanTargetEntity adPlanTargetEntity : listByMapAdPlanTargetEntity) {
			Integer exclude = adPlanTargetEntity.getExclude();//是否排除IP(1.是；0.否；默认：0)
			if(exclude==1){
				excludedtargetAppMobileConnectionTypes.add(adPlanTargetEntity.getTargetId());
			}else{
				targetAppMobileConnectionTypes.add(adPlanTargetEntity.getTargetId());
			}
		}
		p.setTargetAppMobileConnectionTypes(excludedtargetAppMobileConnectionTypes);
		  // 移动设备策略：移动设备类型
		  //移动计划有效 */
		p.setTargetAppMobileDeviceTypes(pcTargetDeviceTypes);
		  // 定向时段
		  //{星期（美式1-7）:['时段（0-23）']} 例：周一10点~12点 {'2':['10','11','12']} */
		Map<String,List<String>> targetHours = new HashMap<String,List<String>>();
		
		List<AdPlanTimeEntity> listByMap2 = adPlanTimeService.listByMap(planIdMap);
		for (AdPlanTimeEntity adPlanTimeEntity : listByMap2) {
			Integer day = adPlanTimeEntity.getDay();
			String hours = adPlanTimeEntity.getHours();
			List<String> list = JSONUtil.toList(hours, String.class);
			targetHours.put(day.toString(), list);
		}
		
		p.setTargetHours(targetHours);
		
		  // 地域策略：地域
		  //{省:['市-区']} 
		  //例：北京{'2':['*']} 甘肃省兰州市{'甘肃':['兰州']} 北京朝阳区{'北京':['北京-朝阳区']} 与商圈互斥，优先使用地域 */
		p.setTargetLocations(targetLocations);;
		  // 移动设备策略：移动设备品牌定向
		  //移动计划有效 */
		  p.setTargetMobileDeviceMakes(mobileDeviceMakes);
		  // 移动设备策略：移动设备型号定向
		  //移动计划有效；与移动设备型号排除互斥，优先使用移动设备型号定向 */
		  p.setTargetMobileDeviceModels(mobileDeviceModels);
		  // 移动设备策略：移动设备操作系统定向
		  //移动计划有效；与移动设备操作系统排除互斥，优先使用移动设备操作系统定向 */
		  p.setTargetMobileDeviceOss(mobileDeviceOss);
		  
		p.setToken(AgentConfig.token());
		
		return p;
	}
		
	private String editApiPlanUpdateParam(AdPlanEntity byId){

		ApiPlanUpdateParam p =(ApiPlanUpdateParam)editApiPlanAddParam(byId);
		
		p.setPlanId(byId.getPlanId());
				 
		p.setToken(AgentConfig.token());
		
		return JSONUtil.toJsonStr(p);
	}
	
	/**
	 * 广告计划添加
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
			String sysId = syncInfoEntity.getSysId();//广告计划ad_activity的主键
			
			AdPlanEntity byId = adPlanService.getById(sysId);
			
			url = "/api/plan/add";//广告计划添加
			p = JSONUtil.toJsonStr(editApiPlanAddParam(byId));
			
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
					ApiPlanAddResultParam data = JSONUtil.toBean(JSONUtil.toJsonStr(post.getData()), ApiPlanAddResultParam.class);
					
					syncInfoEntity.setThirdId(data.getPlanId());
					AdPlanEntity adPlanEntity = new AdPlanEntity();
					adPlanEntity.setId(sysId);
					adPlanEntity.setPlanId(data.getPlanId());
					adPlanEntity.setUpdateTime(new Date());
					adPlanService.updateById(adPlanEntity);
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
	 * 广告计划修改
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
			String sysId = syncInfoEntity.getSysId();//广告计划ad_activity的主键
			
			AdPlanEntity byId = adPlanService.getById(sysId);
			
			url = "/api/plan/update";
			p = editApiPlanUpdateParam(byId);
			
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
		String url = "/api/plan/delete";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告计划ad_activity的主键
			
			AdPlanEntity byId = adPlanService.getById(sysId);
			
			ApiPlanDeleteParam d = new ApiPlanDeleteParam();
 
			d.setPlanIds(Arrays.asList(byId.getPlanId()));
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
	 * 广告计划开启/暂停
	 */
	public void updatePlanStatus(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/plan/updatePlanStatus";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告计划ad_activity的主键
			
			AdPlanEntity byId = adPlanService.getById(sysId);
			
			ApiPlanUpdateStatusParam d = new ApiPlanUpdateStatusParam();
			d.setPlanId(byId.getPlanId());
			d.setDeliveryStatus(byId.getDeliveryStatus());//ONPROGRESS:投放中、PAUSED：暂停；默认：PAUSED
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
	 * 广告计划创意开启/暂停
	 */
	public void updateCreativeStatus(String params){
		
		SyncInfoDetailEntity bean = JSONUtil.toBean(params, SyncInfoDetailEntity.class);
		int resultcode = 0;
		String resultmsg = "";
		String url = "/api/plan/updateCreativeStatus";
		
		SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(bean.getId());
			
		syncInfoDetailEntity.setUrl(url);
		syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);
		
		SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());
		
		try {
			String sysId = syncInfoEntity.getSysId();//广告计划ad_activity的主键
			
			AdPlanCreativeEntity byId = adPlanCreativeService.getById(sysId);
			
			ApiPlanUpdateCreativeStausParam d = new ApiPlanUpdateCreativeStausParam();
			d.setPlanId(byId.getPlanId());
			d.setCreativeId(byId.getCreativeId());
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
	 * 广告计划详情
	 */
	public CommonResult detail(String planId){
		 
		String resultmsg = "";
		String url = "";
		String p = "";
		try {
  
			url = "/api/plan/detail";//广告计划添加
	 
			ApiPlanAddResultParam ds = new ApiPlanAddResultParam();
			ds.setPlanId(planId);
			p = JSONUtil.toJsonStr(ds);
			 
			ZhiziyunResult<?> post = HttpPostZhiziyunUtils.postzhiziyun(url, p);
			if(post==null){
				resultmsg = "系统异常";
				return CommonResult.failed(resultmsg);
			}else{
				resultmsg = JSONUtil.toJsonStr(post);
 
				if(post.isStatus()){
					//同步成功需要反写
					ApiPlanDetailResultParam data = JSONUtil.toBean(JSONUtil.toJsonStr(post.getData()), ApiPlanDetailResultParam.class);
					
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
