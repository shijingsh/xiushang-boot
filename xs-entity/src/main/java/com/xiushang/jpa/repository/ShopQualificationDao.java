package com.xiushang.jpa.repository;

import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopQualificationsEntity;

public interface ShopQualificationDao extends BaseDao<ShopQualificationsEntity> {

    ShopQualificationsEntity findByUser(UserEntity user);
}
