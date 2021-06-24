package amy.marketing.job.dto.zhiziyun;

import java.util.List;
import java.util.Map;

import amy.marketing.dao.entity.AdPlanRegionalEntity;
import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

/**
 * 广告活动添加
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class ApiPlanAddParam extends AgentDTO {
	/**活动编号*/
  private String activityId;
  /** 频率策略：受众每天投放频次 默认：-1（不限）*/
  private Long adDeliveryFrequencyPerDay;
  /** 频率策略：受众每小时投放频次 默认：-1（不限）*/
  private Long adDeliveryFrequencyPerHour;
  /** 频率策略：受众每月投放频次 默认：-1（不限） */
  private Long adDeliveryFrequencyPerMonth;
  /** 频率策略：受众每周投放频次 默认：-1（不限） */
  private Long adDeliveryFrequencyPerWeek;
  /**出价策略：自动优化  出价类型非CPM时必填[0.01, 20000000]*/
  private Boolean automaticOptimization;
  /** 竞价模式,RTB：RTB竞价、PDB：PDB竞价；目前仅开放：RTB；默认：RTB  */
  private String bidMode;
  /** 预算策略：计划总预算 */
  private String budget;
  /** 地域策略：商圈 */
  private List<String> businessAreaIds;
  /** 地域策略：自定义商圈 
   * 每个项目要包含latitude（纬度）、longitude（经度）、distance（半径）、name（名称）四个字段；
   * 例：[{'latitude':25.5666,'longitude':25.5666, 'distance':1000, 'name':'测试商圈名称'}] 
   * 与地域互斥，优先使用地域 */
  private List<AdPlanRegionalEntity> coordinates;
  /** 素材编号 有效的素材编号集合（过滤无效素材） */
  private List<String> creativeIds;
  /** 计划素材类型STATIC:PC静态素材、VIDEO:PC视频素材、NATIVE:PC信息流素材、
   * MOBILE:移动静态素材、MOBILEVIDEO:移动视频素材、MOBILENATIVE:移动信息流素材,
   * 可用值:STATIC,DYNAMIC,VIDEO,NATIVE,MOBILE,MOBILEDYNAMIC,MOBILEVIDEO,MOBILENATIVE,LINKUNIT */
  private String creativeType;
  /** 人群策略：最大有效期 人群类型为INTEREST、CUSTOM、PROBE时有效 */
  private Integer crowdMaxExpiry;
  /** 人群策略：最小有效期 人群类型为REDIRECT、INTEREST、CUSTOM、PROBE时有效 */
  private Integer crowdMinExpiry;
  /** 预算策略：每日点击上限 默认：-1（不限） */
  private Integer dailyClickCapping;
  /** 计划日预算 不小于10 */
  private String dailybudget;
  /** 结束时间 yyyy-MM-dd HH:mm:ss */
  private String endTime;
  /** 人群策略：排除人群编号 人群类型为CUSTOM、PROBE时有效 */
  private List<String> excludeCrowdList;
  /** 媒体策略：媒体类型排除 
   * {} 清空
   *  {'aa':{}} 定向aa以及aa下的所有子节点 
   *  {'aa':{'bb':[]}} 定向bb以及bb下的所有子节点 
   *  {'aa':{'bb':['cc']}} 定向cc  */
  private Map<String,Map<String,List<String>>> excludeMediaType;
  /** 媒体策略：媒体广告位排除
   * key：平台编号 value：媒体广告位编号列表 
   * 例:{'2':['aaaa','bbbb']} */
  private Map<String,List<String>> excludedAdSlots;
  /** 媒体策略：媒体ADX页面分类排除
   * key：平台编号 value：媒体ADX页面分类列表 
   * 例:{'2':['aaaa','bbb']} 与媒体ADX页面分类定向、微信公众号类别定向互斥，优先使用媒体ADX页面分类定向、微信公众号类别定向 */
  private Map<String,List<String>> excludedAdxPageCategories;
  /** 媒体策略：媒体ADX频道排除
   * key：平台编号 value：媒体ADX页面分类列表 
   * 例:{'2':['aaaa','bbb']} 与媒体ADX频道定向互斥，优先使用媒体ADX频道定向 */
  private Map<String,List<String>> excludedAdxVCCategories;
  /** 移动媒体策略：APP排除
   * key：平台编号 value：媒体ADX页面分类列表
   *  例:{'2':['aaaa','bbb']} 移动计划有效；与APP定向互斥，优先使用APP定向 */
  private Map<String,List<String>> excludedAppIds;
  /**PC媒体策略：媒体域名排除 
   * PC计划有效  */
  private List<String> excludedDomains;
  /** 设备策略：IP排除
   * 与IP定向互斥，优先使用IP定向 */
  private List<String> excludedIpsets;
  /** 媒体策略：媒体关键词排除 */
  private List<String> excludedKeyWords;
  /** 移动设备策略：移动设备品牌排除
   * 移动计划有效 */
  private List<String> excludedMobileDeviceMakes;
  /** 移动设备策略：移动设备型号排除
   * 移动计划有效；与移动设备型号定向互斥，优先使用移动设备型号定向 */
  private List<String> excludedMobileDeviceModels;
  /** 移动设备策略：移动设备操作系统排除
   * 移动计划有效；与移动设备操作系统定向互斥，优先使用移动设备操作系统定向 */
  private List<String> excludedMobileDeviceOss;
  /** 出价策略：出价浮动方式,可用值:FIXED,CLICK_RATE_FLOATING,ARRIVAL_RATE_FLOATING
   * FIXED：固定出价、CLICK_RATE_FLOATING：点击率浮动、ARRIVAL_RATE_FLOATING：到达率浮动；默认：FIXED；出价类型为CPM时必填 */
  private String floating;
  /** 人群策略：相似匹配
   * 人群类型为INTEREST、CUSTOM、PROBE时有效 */
  private Boolean fuzzyMatching;
  /**媒体策略：媒体类型定向
   * {} 清空 
   * {'aa':{}} 定向aa以及aa下的所有子节点 
   * {'aa':{'bb':[]}} 定向bb以及bb下的所有子节点 
   * {'aa':{'bb':['cc']}} 定向cc  */
  private Map<String,Map<String,List<String>>> includeMediaType;
  /**媒体策略：媒体广告位类型定向  */
  private List<String> includedAdslotTypes;
  /** 媒体策略：媒体ADX页面分类定向
   * key：平台编号 value：媒体ADX页面分类列表 、
   * 例:{'2':['aaaa','bbb']} 与媒体ADX页面分类排除互斥，优先使用媒体ADX页面分类定向 */
  private Map<String,List<String>> includedAdxPageCategories;
  /** 媒体策略：媒体ADX页面关键词定向 */
  private List<String> includedAdxPageKeywords;
  /** 媒体策略：媒体ADX频道定向
   * key：平台编号 value：媒体ADX页面分类列表 
   * 例:{'2':['aaaa','bbb']} 与媒体ADX频道排除互斥，优先使用媒体ADX频道定向 */
  private Map<String,List<String>> includedAdxVCCategories;
  /** 设备策略：IP定向
   * 与IP排除互斥，优先使用IP定向 */
  private List<String> includedIpsets;
  /** 媒体策略：广点通QQ定向 */
  private List<String> includedQQs;
  /**预算策略：计划周期内总点击数  默认：-1（不限）  */
  private Long lifeCycleClickCapping;
  /** 预算策略：计划周期内总曝光数 默认：-1（不限） */
  private Long lifeCycleImpressionCapping;
  /** 预算策略：每日曝光上限 默认：-1（不限） */
  private Long maxCPMCountPerDay;
  /** 出价策略：出价模型,可用值:ONEDIMENSIONAL,MULTIDIMENSIONAL
   * ONEDIMENSIONAL：单维模型、MULTIDIMENSIONAL：多维模型；默认：ONEDIMENSIONAL（暂时只支持默认值） */
  private String model;
  
  /** 移动设备策略：仅对有设备号的设备
   * 移动计划有效 */
  private Boolean mustHasDeviceId;
  /** 平台编号  */
  private List<String> networkIds;
  /** 人群策略：防作弊过滤等级,可用值:none,loose,general,strict
   * none：没有策略、loose：宽松策略、general：普通策略、strict：严格策略 */
  private String nhtFilterLevel;
  /** 出价策略：优化价格
   * 非自动优化时必填 */
  private String optimizePrice;
  /** 出价策略：消耗方式 SOON:尽快消耗、EVEN：均匀消耗；默认：EVEN */
  private String pacingType;
  /** PC设备策略：PC定向浏览器类型
   * PC计划有效 */
  private List<String> pcTargetBrowserTypes;
  /** PC设备策略：PC定向设备类型
   * PC计划有效 */
  private List<String> pcTargetDeviceTypes;
  /** 计划名称  */
  private String planName;
  /** 平台类型,PC：PC平台、MOBILE:移动平台 */
  private String platform;
  /** 出价价格 [0.01, 20000000] */
  private String price;
  /** 出价策略：出价类型,CPM：CPM出价、CPC：CPC出价、CPUV：CPUV出价；默认：CPM */
  private String pricingType;
  /** 出价策略：反量比例
   * 0-99；bidMode为PDB时必填 */
  private Integer rtnRate;

  /** 广告主编号*/
  private String siteId;
  /** 开始时间 yyyy-MM-dd HH:mm:ss */
  private String startTime;
  /** 人群策略：人群类型
   * ALL：全网智能优化、REDIRECT：重定向人群、ADX：ADX人群（暂不支持）、INTEREST：兴趣人群、
   * INDUSTRY：行业人群、CUSTOM：自定义人群（移动人群）、PROBE：探测人群（移动人群） */
  private String tagCrowdType;
  /** 人群策略：人群编号  */
  private List<String> tagIdsList;
  /** 媒体策略：媒体广告位定向
	key：平台编号 value：媒体广告位编号列表 例:{'2':['aaaa','bbbb']}
  */
  private Map<String,List<String>> targetAdSlots;
  /** 移动媒体策略：APP类型 */
  private String targetAppCategorys;
  /** 移动媒体策略：APP定向
   * key：平台编号 value：媒体ADX页面分类列表 例:{'2':['aaaa','bbb']} 移动计划有效；与APP排除互斥，优先使用APP定向 */
  private Map<String,List<String>> targetAppIds;
  /** 移动设备策略：运营商类型
   * 移动计划有效 */
  private List<String> targetAppMobileCarriers;
  /** 移动设备策略：联网方式
   * 移动计划有效  */
  private List<String> targetAppMobileConnectionTypes;
  /** 移动设备策略：移动设备类型
   * 移动计划有效 */
  private List<String> targetAppMobileDeviceTypes;
  /** PC媒体策略：媒体域名定向
   * PC计划有效 */
  private List<String> targetDomains;
  /** 定向时段
   * {星期（美式1-7）:['时段（0-23）']} 例：周一10点~12点 {'2':['10','11','12']} */
  private Map<String,List<String>> targetHours;
  /** 媒体策略：媒体关键词定向 */
  private List<String> targetKeyWords;
  /** 地域策略：地域
   * {省:['市-区']} 
   * 例：北京{'2':['*']} 甘肃省兰州市{'甘肃':['兰州']} 北京朝阳区{'北京':['北京-朝阳区']} 与商圈互斥，优先使用地域 */
  private Map<String,List<String>> targetLocations;
  /** PC媒体策略：媒体页面链接定向
   * PC计划有效 */
  private List<String> targetMediaPageUrls;
  /** 移动设备策略：移动设备品牌定向
   * 移动计划有效 */
  private List<String> targetMobileDeviceMakes;
  /** 移动设备策略：移动设备型号定向
   * 移动计划有效；与移动设备型号排除互斥，优先使用移动设备型号定向 */
  private List<String> targetMobileDeviceModels;
  /** 移动设备策略：移动设备操作系统定向
   * 移动计划有效；与移动设备操作系统排除互斥，优先使用移动设备操作系统定向 */
  private List<String> targetMobileDeviceOss;
  /** 移动媒体策略：微信公众号类别定向
   * 移动计划有效  */
  private List<String> targetWechatSubscriptionType;
  
}
