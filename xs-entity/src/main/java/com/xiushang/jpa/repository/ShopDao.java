package com.xiushang.jpa.repository;

import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopEntity;

import java.util.List;

public interface ShopDao extends BaseDao<ShopEntity> {

    ShopEntity findByOwnerUser(UserEntity ownerUser);

    ShopEntity findByCode(String code);

    List<ShopEntity> findByCompanyName(String companyName);
}
