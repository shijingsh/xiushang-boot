package com.xiushang.common.user.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.xiushang.common.user.vo.UserSearchVo;
import com.xiushang.entity.QUserEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.entity.shop.ShopQualificationsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.utils.DeleteEnum;
import com.xiushang.framework.utils.OrderUtil;
import com.xiushang.framework.utils.UserHolder;
import com.xiushang.jpa.repository.ShopDao;
import com.xiushang.jpa.repository.ShopQualificationDao;
import com.xiushang.jpa.repository.UserDao;
import com.xiushang.shop.util.ShopStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopQualificationDao qualificationDao;

    public UserEntity getUser(String loginName) {

        List<String> names = new ArrayList<>();
        names.add(loginName);

        QUserEntity qUserEntity = QUserEntity.userEntity;
        JPAQuery query = getQuery();

        query.from(qUserEntity);
        query.where(
                qUserEntity.loginName.in(names).and(qUserEntity.deleted.eq(DeleteEnum.VALID))
        );
        List<UserEntity> userEntityList= query.fetch();

        if(userEntityList!=null && userEntityList.size()>0){
            return userEntityList.get(0);
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


    @Transactional
    public void registerUser(UserEntity userEntity) {

        userDao.save(userEntity);

        ShopEntity shopEntity = shopDao.findByOwnerUser(userEntity);
        if(shopEntity==null){

            //创建默认店铺
            shopEntity = new ShopEntity();
            shopEntity.setName(userEntity.getName()+"的小店");
            shopEntity.setContactsName(userEntity.getName());
            shopEntity.setMobile(userEntity.getMobile());
            shopEntity.setOwnerUser(userEntity);
            shopEntity.setShopStatus(ShopStatusEnum.SHOP_BASE);

            shopEntity.setCode(OrderUtil.genCode("SP"));

            shopDao.save(shopEntity);

            //创建店铺资质
            ShopQualificationsEntity qualificationsEntity = new ShopQualificationsEntity();
            qualificationsEntity.setRealName(userEntity.getName());
            qualificationsEntity.setBelongShop(shopEntity);
            qualificationDao.save(qualificationsEntity);
        }

    }


    //-----------------------------------------------------获取当前会话信息-----------------------------------------------------

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

    public String getCurrentUserId() {
        UserEntity userEntity = getCurrentUser();
        if(userEntity != null){
            return userEntity.getId();
        }

        return "";
    }

    public ShopEntity getCurrentTenantShop() {

        String tenantId = UserHolder.getTenantId();
        UserEntity userEntity = getUserById(tenantId);

        ShopEntity shopEntity = shopDao.findByOwnerUser(userEntity);

        return shopEntity;
    }


    public ShopEntity getCurrentUserShop() {

        UserEntity userEntity = getCurrentUser();

        ShopEntity shopEntity = shopDao.findByOwnerUser(userEntity);

        return shopEntity;
    }

    public String getCurrentTenantShopId() {

        String tenantId = UserHolder.getTenantId();
        UserEntity userEntity = getUserById(tenantId);

        ShopEntity shopEntity = shopDao.findByOwnerUser(userEntity);
        if(shopEntity!=null){
            return shopEntity.getId();
        }

        return "";
    }


    public String getCurrentUserShopId() {
        ShopEntity shopEntity = getCurrentUserShop();
        if(shopEntity!=null){
            return shopEntity.getId();
        }

        return "";
    }

    public String getCurrentClientId() {

        return UserHolder.getClientId();
    }

}
