package amy.marketing.job.service;

import amy.marketing.dao.entity.SyncInfoDetailEntity;
import amy.marketing.dao.entity.SyncInfoEntity;

public interface SyncInfoCommonService {

    void insertSyncInfoLog(SyncInfoDetailEntity p);
    void updateSyncInfo(SyncInfoEntity params);
    void updateSyncInfoDetail(SyncInfoDetailEntity syncInfoDetailEntity);
}
