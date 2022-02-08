package com.xiushang.jpa.repository;

import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.entity.shop.ShopQualificationsEntity;

public interface ShopQualificationDao extends BaseDao<ShopQualificationsEntity> {
    ShopQualificationsEntity findByBelongShop(ShopEntity shopEntity);
}
