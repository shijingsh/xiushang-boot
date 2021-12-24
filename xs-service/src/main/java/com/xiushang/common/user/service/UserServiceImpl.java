package com.xiushang.common.user.service;

import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.xiushang.common.user.vo.UserSearchVo;
import com.xiushang.common.utils.MD5;
import com.xiushang.entity.QUserEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.log.Constants;
import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.framework.utils.DeleteEnum;
import com.xiushang.framework.utils.UserHolder;
import com.xiushang.jpa.repository.ShopDao;
import com.xiushang.jpa.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Resource
    private HttpServletRequest request;

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShopDao shopDao;

    public UserEntity getUser(String loginName) {

        List<String> names = new ArrayList<>();
        names.add(loginName);
        List<UserEntity> userEntityList= getUsersByNames(names);
        if(userEntityList!=null && userEntityList.size()>0){
            return userEntityList.get(0);
        }
        return null;
    }

    public UserEntity getUserByMobile(String mobile) {

        JPAQuery query = getQuery();
        QUserEntity qUserEntity = QUserEntity.userEntity;
        query.from(qUserEntity);

        query.where(qUserEntity.deleted.eq(DeleteEnum.VALID).and(qUserEntity.loginName.eq(mobile).or(qUserEntity.mobile.eq(mobile))));
        List<UserEntity> users = query.fetch();
        if (users != null && users.size()>0) {
            return users.get(0);
        }
        return null;
    }

    public UserEntity getUser(String loginName, String password) {

        JPAQuery query = getQuery();
        QUserEntity qUserEntity = QUserEntity.userEntity;
        query.from(qUserEntity);

        query.where(
                qUserEntity.loginName.eq(loginName).and(qUserEntity.deleted.eq(DeleteEnum.VALID)).and(qUserEntity.password.eq(password))
        );
        List<UserEntity> users = query.fetch();

        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Transactional
    public UserEntity saveInitUserPassWord(String userId) {

        UserEntity userEntity = userDao.getOne(userId);
        userEntity.setPassword(MD5.GetMD5Code("123456"));

        userDao.save(userEntity);
        return userEntity;
    }

    @Transactional
    public void delete(String userId) {

        UserEntity userEntity = userDao.getOne(userId);
        userDao.delete(userEntity);
    }
    public Long findCount(UserSearchVo searchVo) {
        QUserEntity entity = QUserEntity.userEntity;

        BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID);
        if(searchVo!= null){
            if(StringUtils.isNotBlank(searchVo.getLoginName())){
                ex = ex.and(entity.loginName.like("%" + searchVo.getLoginName() + "%"));
            }else if(StringUtils.isNotBlank(searchVo.getName())){
                ex = ex.and(entity.name.like("%"+searchVo.getName()+"%"));
            }
        }

        JPAQuery query = new JPAQuery(entityManager);
        query.from(entity).where(
                ex
        );
        Long totalNum = query.fetchCount();

        return totalNum;
    }

    public PageTableVO findPageList(UserSearchVo searchVo) {
        QUserEntity entity = QUserEntity.userEntity;
        Integer limit = searchVo.getPageSize();
        Integer offset = searchVo.getOffset();
        if(limit==null || limit <=0){
            limit = 15;
        }

        BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID);
        if(searchVo!= null){
            if(StringUtils.isNotBlank(searchVo.getLoginName())){
                ex = ex.and(entity.loginName.like("%" + searchVo.getLoginName() + "%"));
            }else if(StringUtils.isNotBlank(searchVo.getName())){
                ex = ex.and(entity.name.like("%"+searchVo.getName()+"%"));
            }
        }

        JPAQuery query = new JPAQuery(entityManager);
        query.from(entity)
                .where(
                        ex
                ).offset(offset).limit(limit);
        List<UserEntity> list = query.fetch();
        Long totalCount = findCount(searchVo);
        PageTableVO vo = new PageTableVO();
        vo.setRowData(list);
        vo.setTotalCount(totalCount);
        vo.setPageNo(searchVo.getPageNo());
        vo.setPageSize(searchVo.getPageSize());
        return vo;
    }

    @Transactional
    public List<UserEntity> insertUsers(List<String> userNames) {

        List<UserEntity> users = new ArrayList<>();
        for (String userName : userNames) {

            UserEntity user = getUser(userName);

            if (user != null) { //已经在系统中
                users.add(user);
                continue;
            }
            users.add(insertUser(userName, null));
        }
        return users;
    }


    public List<UserEntity> getUsers(List<String> userNames) {
        QUserEntity user = QUserEntity.userEntity;


        JPAQuery query = new JPAQuery(entityManager);
        query.from(user)
                .where(
                        user.name.in(userNames).and(user.deleted.eq(DeleteEnum.VALID))
                );
        return query.fetch();

    }

    public UserEntity getUserById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        Optional<UserEntity> userEntity= userDao.findById(id);
        if(userEntity.isPresent()){
            return userEntity.get();
        }
        return null;
     }

    @Transactional
    public UserEntity insertUser(String userName, String password) {

        UserEntity user = getUser(userName);

        if (user != null) {
            return user;
        }

        UserEntity userEntity = new UserEntity(userName, StringUtils.isBlank(password) ? Constants.DEFAULT_PASSWORD : password);

        entityManager.persist(userEntity);

        return userEntity;
    }

    public String getUserMarkName(String userId, int flag) {
        if(StringUtils.isBlank(userId)){
            return "";
        }
        String[] userIds = userId.split(";");
        List<UserEntity> list = getUsersByIds(Arrays.asList(userIds));
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<list.size();i++){
            UserEntity user = list.get(i);
            sb.append(user.getName());
            if(i!=list.size()-1){
                sb.append(";");
            }
        }
        return sb.toString();
    }

    @Transactional
    public void updateUser(UserEntity user) {
        userDao.save(user);
    }

    @Transactional
    public void updateUserLastLoginDate(UserEntity user) {
        user.setLastLoginDate(new Date());
        userDao.save(user);
    }

    public List<UserEntity> getUsers(String name) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        JPAQuery query = getQuery();

        query.from(qUserEntity);
        if(StringUtils.isNotBlank(name)){
            query.where(
                    qUserEntity.name.like("%" + name + "%").and(qUserEntity.deleted.eq(DeleteEnum.VALID))
            );
        }
        return query.fetch();
    }

    public List<UserEntity> getUsersByNames(List<String> userNames) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        JPAQuery query = getQuery();

        query.from(qUserEntity);
        query.where(
                qUserEntity.loginName.in(userNames).and(qUserEntity.deleted.eq(DeleteEnum.VALID))
        );
        return query.fetch();
    }

    public List<UserEntity> getUsersByIds(List<String> userIds) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        JPAQuery query = getQuery();

        query.from(qUserEntity);
        query.where(qUserEntity.id.in(userIds));
        return query.fetch();
    }

    protected JPAQuery getQuery() {
        return new JPAQuery(entityManager);
    }

    public UserEntity getCurrentUser() {

        String loginName = UserHolder.getLoginName();
        //log.info("获取当前用户LoginName：{}。",loginName);
        UserEntity userEntity = null;
        if(StringUtils.isNotBlank(loginName)){
            userEntity = getUser(loginName);
        }
        if(userEntity != null){
            return userEntity;
        }else {
           // log.info("getCurrentUser：没有当前用户信息。");
        }

        return null;
    }

    public ShopEntity getCurrentShop() {

        String tenantId = UserHolder.getTenantId();
        UserEntity userEntity = getUserById(tenantId);

        ShopEntity shopEntity = shopDao.findByOwnerUser(userEntity);

        return shopEntity;
    }

    public String getCurrentShopId() {

        String tenantId = UserHolder.getTenantId();
        UserEntity userEntity = getUserById(tenantId);

        ShopEntity shopEntity = shopDao.findByOwnerUser(userEntity);
        if(shopEntity!=null){
            return shopEntity.getId();
        }

        return "";
    }
}
