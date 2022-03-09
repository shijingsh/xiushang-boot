package com.xiushang.common.info.service;

import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.info.AppVersionEntity;
import com.xiushang.framework.entity.vo.PageTableVO;

public interface AppVersionService extends BaseService<AppVersionEntity> {

    PageTableVO findPageList(PageTableVO pageTableVO);

    AppVersionEntity findByShopIdAndClientId(String shopId, String clientId);
}
