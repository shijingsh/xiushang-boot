package amy.marketing.job.service.impl;

import amy.marketing.common.api.ZhiziyunResult;
import amy.marketing.dao.entity.*;
import amy.marketing.dao.mapper.AdvertiserMapper;
import amy.marketing.job.config.AgentConfig;
import amy.marketing.job.dto.zhiziyun.*;
import amy.marketing.job.service.AdvertiserSyncService;
import amy.marketing.job.service.SyncInfoCommonService;
import amy.marketing.job.utils.HttpPostZhiziyunUtils;
import amy.marketing.service.*;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class AdvertiserSyncServiceImpl extends ServiceImpl<AdvertiserMapper, AdvertiserEntity> implements AdvertiserSyncService {

    @Resource
    private AdvertiserQualificationService advertiserQualificationService;
    @Resource
    private AdvertiserService advertiserService;
    @Resource
    private SyncInfoDetailService syncInfoDetailService;
    @Resource
    private SyncInfoService syncInfoService;
    @Resource
    private SyncInfoCommonService syncInfoCommonService;
    @Resource
    private UserService userService;

    @Override
    public AdvertiserEntity syncAdvertiser(String id) {

        SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(id);
        syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);

        SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());

        //同步广告主
        String advertiserId = syncInfoEntity.getSysId();
        AdvertiserEntity advertiserEntity =null;

        int resultcode = 0;
        String resultmsg = "";
        try {
             advertiserEntity = this.getById(advertiserId);
            if(advertiserEntity== null || advertiserEntity.getDeleted()==1){
                //已删除
                return null;
            }

            String url = "/api/advertiser/add";
            String p = "";
            if(StringUtils.isNotBlank(advertiserEntity.getSiteId())){
                url = "/api/advertiser/update";
                ApiAdvertiserUpdateParam advertiserUpdateParam = new ApiAdvertiserUpdateParam();
                BeanUtils.copyProperties(advertiserEntity,advertiserUpdateParam,"userId");
                advertiserUpdateParam.setToken(AgentConfig.token());
                 p = JSONUtil.toJsonStr(advertiserUpdateParam);
            }else{
                SysUser sysUser = userService.getById(advertiserEntity.getUserId());

                ApiAdvertiserAddParam advertiserAddParam = new ApiAdvertiserAddParam();
                BeanUtils.copyProperties(advertiserEntity,advertiserAddParam,"userId");
                advertiserAddParam.setToken(AgentConfig.token());
                advertiserAddParam.setEmail(sysUser.getEmail());
                advertiserAddParam.setPassword("A12345678b");
                 p = JSONUtil.toJsonStr(advertiserAddParam);
            }

            syncInfoDetailEntity.setUrl(url);
            syncInfoDetailEntity.setParams(p);
            syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);

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
                    Object data = post.getData();
                    String siteId = ((JSONObject)data).getStr("siteId");

                    syncInfoEntity.setThirdId(siteId);

                    advertiserEntity.setSiteId(siteId);
                    advertiserService.saveOrUpdate(advertiserEntity);
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

        syncInfoCommonService.updateSyncInfo(syncInfoEntity);
        syncInfoCommonService.updateSyncInfoDetail(syncInfoDetailEntity);
        syncInfoCommonService.insertSyncInfoLog(syncInfoDetailEntity);

        return advertiserEntity;
    }


    public AdvertiserQualificationEntity syncAdvertiserQualification(String id) {


        SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(id);
        syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);

        SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());

        //同步广告主
        String advertiserId = syncInfoEntity.getSysId();
        AdvertiserQualificationEntity advertiserEntity =null;

        int resultcode = 0;
        String resultmsg = "";
        try {
            advertiserEntity = advertiserQualificationService.getById(advertiserId);
            if(advertiserEntity== null){
                //已删除
                return null;
            }

            String url = "/api/qualification/add";
            String p = "";
            if(syncInfoEntity.getSyncType().equals("delete") && StringUtils.isNotBlank(advertiserEntity.getQualificationId())){
                url = "/api/qualification/delete";
                ApiAdvertiserQualificationUpdateParam advertiserUpdateParam = new ApiAdvertiserQualificationUpdateParam();

                advertiserUpdateParam.setToken(AgentConfig.token());
                advertiserUpdateParam.setQualificationId(advertiserEntity.getQualificationId());
                p = JSONUtil.toJsonStr(advertiserUpdateParam);
            }else   if(StringUtils.isNotBlank(advertiserEntity.getQualificationId())){
                url = "/api/qualification/update";
                ApiAdvertiserQualificationUpdateParam advertiserUpdateParam = new ApiAdvertiserQualificationUpdateParam();

                advertiserUpdateParam.setToken(AgentConfig.token());
                advertiserUpdateParam.setQualificationId(advertiserEntity.getQualificationId());
                advertiserUpdateParam.setSiteId(advertiserEntity.getAdvertiserId());
                advertiserUpdateParam.setNetworkIds(advertiserEntity.getNetworkIds().split(","));
                p = JSONUtil.toJsonStr(advertiserUpdateParam);
            }else{

                ApiAdvertiserQualificationAddParam advertiserAddParam = new ApiAdvertiserQualificationAddParam();

                advertiserAddParam.setToken(AgentConfig.token());
                advertiserAddParam.setSiteId(advertiserEntity.getAdvertiserId());
                advertiserAddParam.setQualificationId(advertiserEntity.getQualificationId());
                advertiserAddParam.setQualificationContent(advertiserEntity.getQualificationContent());
                advertiserAddParam.setQualificationName(advertiserEntity.getQualificationName());
                advertiserAddParam.setQualificationTemplateId("1");
                advertiserAddParam.setQualificationType("BASIC");
                advertiserAddParam.setContentType("TEXT");
                advertiserAddParam.setNetworkIds(advertiserEntity.getNetworkIds().split(","));
                p = JSONUtil.toJsonStr(advertiserAddParam);
            }

            syncInfoDetailEntity.setUrl(url);
            syncInfoDetailEntity.setParams(p);
            syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);

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
                    Object data = post.getData();
                    String qualificationId = ((JSONObject)data).getStr("qualificationId");

                    syncInfoEntity.setThirdId(qualificationId);

                    advertiserEntity.setQualificationId(qualificationId);
                    advertiserQualificationService.saveOrUpdate(advertiserEntity);
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

        syncInfoCommonService.updateSyncInfo(syncInfoEntity);
        syncInfoCommonService.updateSyncInfoDetail(syncInfoDetailEntity);
        syncInfoCommonService.insertSyncInfoLog(syncInfoDetailEntity);

        return advertiserEntity;
    }

    public AdvertiserEntity syncAdvertiserStatus(String id) {

        SyncInfoDetailEntity syncInfoDetailEntity = syncInfoDetailService.getById(id);
        syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);

        SyncInfoEntity syncInfoEntity = syncInfoService.getById(syncInfoDetailEntity.getSyncId());

        //同步广告主
        String advertiserId = syncInfoEntity.getSysId();
        AdvertiserEntity advertiserEntity =null;

        int resultcode = 0;
        String resultmsg = "";
        try {
            advertiserEntity = this.getById(advertiserId);
            if(advertiserEntity== null || advertiserEntity.getDeleted()==1){
                //已删除
                return null;
            }

            String url = "/api/advertiser/status";

            ApiAdvertiserStatusParam advertiserStatusParam = new ApiAdvertiserStatusParam();
            advertiserStatusParam.setToken(AgentConfig.token());
            advertiserStatusParam.setSiteId(advertiserEntity.getSiteId());
            String p = JSONUtil.toJsonStr(advertiserStatusParam);

            syncInfoDetailEntity.setUrl(url);
            syncInfoDetailEntity.setParams(p);
            syncInfoDetailEntity.setSyncTimes(syncInfoDetailEntity.getSyncTimes()+1);

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
                    Object data = post.getData();
                    //String siteId = ((JSONObject)data).getStr("siteId");

                    //syncInfoEntity.setThirdId(siteId);

                    //advertiserEntity.setSiteId(siteId);
                    advertiserService.saveOrUpdate(advertiserEntity);
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

        syncInfoCommonService.updateSyncInfo(syncInfoEntity);
        syncInfoCommonService.updateSyncInfoDetail(syncInfoDetailEntity);
        syncInfoCommonService.insertSyncInfoLog(syncInfoDetailEntity);

        return advertiserEntity;
    }

}
