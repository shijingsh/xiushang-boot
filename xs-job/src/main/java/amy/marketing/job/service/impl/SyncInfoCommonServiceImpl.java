package amy.marketing.job.service.impl;

import amy.marketing.dao.entity.SyncInfoDetailEntity;
import amy.marketing.dao.entity.SyncInfoEntity;
import amy.marketing.dao.entity.SyncInfoLogEntity;
import amy.marketing.job.service.SyncInfoCommonService;
import amy.marketing.service.SyncInfoDetailService;
import amy.marketing.service.SyncInfoLogService;
import amy.marketing.service.SyncInfoService;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SyncInfoCommonServiceImpl implements SyncInfoCommonService {

    @Resource
    private SyncInfoLogService syncInfoLogService;
    @Resource
    private SyncInfoService syncInfoService;
    @Resource
    private SyncInfoDetailService syncInfoDetailService;

    public void insertSyncInfoLog(SyncInfoDetailEntity p){
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

    public void updateSyncInfo(SyncInfoEntity params){
        params.setSyncTime(new Date());
        syncInfoService.updateById(params);
    }

    public void updateSyncInfoDetail(SyncInfoDetailEntity syncInfoDetailEntity){
        syncInfoDetailService.updateById(syncInfoDetailEntity);
    }

}
