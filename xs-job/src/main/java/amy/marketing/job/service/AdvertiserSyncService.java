package amy.marketing.job.service;

import amy.marketing.dao.entity.AdvertiserEntity;
import amy.marketing.dao.entity.AdvertiserQualificationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 广告主
 *
 */
public interface AdvertiserSyncService extends IService<AdvertiserEntity> {

    /**
     * 同步广告主信息
     * 到智子云
     * @return
     */
    AdvertiserEntity syncAdvertiser(String id);
    /**
     * 同步广告主资质信息
     * 到智子云
     * @return
     */
    AdvertiserQualificationEntity syncAdvertiserQualification(String id);
    /**
     * 同步广告主状态信息
     *
     * @return
     */
    AdvertiserEntity syncAdvertiserStatus(String id);
}

