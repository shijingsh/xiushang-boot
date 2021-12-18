package com.xiushang.jpa.repository;

import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopEntity;

public interface ShopDao extends BaseDao<ShopEntity> {

    ShopEntity findByOwnerUser(UserEntity ownerUser);
}
