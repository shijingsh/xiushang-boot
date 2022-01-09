package com.xiushang.common.user.service;

import com.xiushang.common.user.vo.UserSearchVo;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.entity.vo.PageTableVO;

import java.util.List;

public interface UserService {


    /**
     * 根据用户名和密码,获取用户信息
     * @param loginName
     *        用户名
     * @param password
     *        密码
     * @return 返回验证通过后返回User对象，若无返回null
     */
    UserEntity getUser(String loginName, String password);
    /**
     * 保持用户信息，名称在系统中不存在，就初始化数据； 或者相反
     * @param userNames 员工名称集合
     */
    List<UserEntity> insertUsers(List<String> userNames);


    /**
     * 插入用户信息
     * @param userName   单个用户名称
     * @return   插入user实体类
     */
    UserEntity insertUser(String userName, String password);


    /**
     * 根据用户名,获取用户信息
     * @param loginName
     * @return
     */
    UserEntity getUser(String loginName);


    /**
     * 根据用户主键获取用户对象
     * @param id 用户ID
     * @return 用户信息 ID＝＝NULL 返回NULL
     */
    UserEntity getUserById(String id);

    /**
     * 获取某一用户特殊权限上的别名， 若不存在该权限， 则返回用户名称
     * @param userId 用户ID
     * @param flag  权限标示
     * @return 用户特殊权限所对应别名
     */
    String getUserMarkName(String userId, int flag);
    /**
     * 修改员工信息
     * @param user
     */
    void updateUser(UserEntity user);

    /**
     * 修改员工信息最后登录时间
     */
    void updateUserLastLoginDate(UserEntity user);

    /**
     * 模糊检索人员列表
     * @param name 名称，为空是所有人员
     * @return  返回符合条件的人员信息
     */
    List<UserEntity> getUsers(String name);

    /**
     * 删除用户
     * @param userId
     */
    public void delete(String userId);
    /**
     * 分页查询用户登录帐号
     * @param searchVo
     * @return
     */
    public PageTableVO findPageList(UserSearchVo searchVo);

    /**
     * 根据用户id,初始化登录密码
     * @param userId
     * @return
     */
    public UserEntity saveInitUserPassWord(String userId);

    /**
     * 查询用户名称在集合userNames中的所有用户集
     * @param userNames 用户名称组，
     * @return 满足name in userNames && 状态标示位为有效的用户
     */
    List<UserEntity> getUsersByNames(List<String> userNames);

    /**
     * 查询用户名称在集合userNames中的所有用户集
     * @param userIds  用户名称组，
     * @return   满足name in userNames && 状态标示位为有效的用户
     */
    public List<UserEntity> getUsersByIds(List<String> userIds);

    public UserEntity getCurrentUser();

    public ShopEntity getCurrentShop();

    public String getCurrentShopId();

    public void registerUser(UserEntity userEntity);
}
