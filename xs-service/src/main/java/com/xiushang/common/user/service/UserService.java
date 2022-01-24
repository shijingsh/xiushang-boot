package com.xiushang.common.user.service;

import com.xiushang.common.user.vo.UserSearchVo;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.entity.vo.PageTableVO;

import java.util.List;

public interface UserService {


    /**
     * 根据用户名和密码,获取用户信息
     */
    UserEntity getUser(String loginName, String password);


    /**
     * 根据用户名,获取用户信息
     */
    UserEntity getUser(String loginName);


    /**
     * 根据用户主键获取用户对象
     */
    UserEntity getUserById(String id);

    /**
     * 分页查询用户登录帐号
     */
    public PageTableVO findPageList(UserSearchVo searchVo);

    /**
     * 查询用户ID列表查询所有用户集
     */
    public List<UserEntity> getUsersByIds(List<String> userIds);

    /**
     * 获取当前登陆用户
     */
    public UserEntity getCurrentUser();

    /**
     * 获取当前登陆用户ID
     */
    public String getCurrentUserId();
    /**
     * 获取当前登陆用户商铺
     */
    public ShopEntity getCurrentUserShop();

    /**
     * 获取当前登陆用户商铺ID
     */
    public String getCurrentUserShopId();

    /**
     * 获取当前租户商铺
     */
    public ShopEntity getCurrentTenantShop();

    /**
     * 获取当前租户商铺ID
     */
    public String getCurrentTenantShopId();
    /**
     * 获取当前客户端ID
     */
    public String getCurrentClientId();

    public void registerUser(UserEntity userEntity);
}
