package com.xiushang.common.user.service;

import com.alibaba.fastjson.JSONObject;
import com.xiushang.constant.ConstantKey;
import com.xiushang.entity.QUserEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.jpa.repository.UserDao;
import com.xiushang.common.user.vo.ThirdUserVo;
import com.xiushang.common.utils.MD5;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.utils.StatusEnum;
import com.xiushang.framework.utils.UserHolder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

        query.where(
                qUserEntity.loginName.eq(mobile).or(qUserEntity.mobile.eq(mobile))
        );
        List<UserEntity> users = query.fetch();
        if (users != null && users.size()>0) {
            return users.get(0);
        }
        return null;
    }


    public UserEntity getUserByUnionId(String unionId) {

        JPAQuery query = getQuery();
        QUserEntity qUserEntity = QUserEntity.userEntity;
        query.from(qUserEntity);

        query.where(
                qUserEntity.unionId.eq(unionId)
        );
        List<UserEntity> users = query.fetch();
        if (users != null && users.size()>0) {
            return users.get(0);
        }
        return null;
    }


    public UserEntity getUserByAppleId(String appleId) {

        JPAQuery query = getQuery();
        QUserEntity qUserEntity = QUserEntity.userEntity;
        query.from(qUserEntity);

        query.where(
                qUserEntity.appleId.eq(appleId)
        );
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
                qUserEntity.loginName.eq(loginName).and(qUserEntity.password.eq(password))
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
    public Long findCount(PageTableVO pageTableVO) {
        QUserEntity entity = QUserEntity.userEntity;

        JSONObject paramObject = (JSONObject)pageTableVO.getExtendData();
        UserEntity userEntity = JSONObject.toJavaObject(paramObject, UserEntity.class);

        BooleanExpression ex = entity.status.eq(StatusEnum.STATUS_VALID);
        if(userEntity!= null){
            if(StringUtils.isNotBlank(userEntity.getLoginName())){
                ex = ex.and(entity.loginName.like("%" + userEntity.getLoginName() + "%"));
            }else if(StringUtils.isNotBlank(userEntity.getName())){
                ex = ex.and(entity.name.like("%"+userEntity.getName()+"%"));
            }
        }

        JPAQuery query = new JPAQuery(entityManager);
        query.from(entity).where(
                ex
        );
        Long totalNum = query.fetchCount();

        return totalNum;
    }

    public PageTableVO findPageList(PageTableVO pageTableVO) {
        QUserEntity entity = QUserEntity.userEntity;
        Integer limit = pageTableVO.getPageSize();
        Integer offset = pageTableVO.getOffset();
        if(limit==null || limit <=0){
            limit = 15;
        }
        JSONObject paramObject = (JSONObject)pageTableVO.getExtendData();
        UserEntity userEntity = JSONObject.toJavaObject(paramObject,UserEntity.class);

        BooleanExpression ex = entity.status.eq(StatusEnum.STATUS_VALID);
        if(userEntity!= null){
            if(StringUtils.isNotBlank(userEntity.getLoginName())){
                ex = ex.and(entity.loginName.like("%" + userEntity.getLoginName() + "%"));
            }else if(StringUtils.isNotBlank(userEntity.getName())){
                ex = ex.and(entity.name.like("%"+userEntity.getName()+"%"));
            }
        }

        JPAQuery query = new JPAQuery(entityManager);
        query.from(entity)
                .where(
                        ex
                ).offset(offset).limit(limit);
        List<UserEntity> list = query.fetch();
        Long totalCount = findCount(pageTableVO);
        PageTableVO vo = new PageTableVO();
        vo.setRowData(list);
        vo.setTotalCount(totalCount);
        vo.setPageNo(pageTableVO.getPageNo());
        vo.setPageSize(pageTableVO.getPageSize());
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
                        user.name.in(userNames)
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

        UserEntity userEntity = new UserEntity(userName, StringUtils.isBlank(password) ? UserEntity.DEFAULT_PASSWORD : password);

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
                    qUserEntity.name.like("%" + name + "%")
            );
        }
        return query.fetch();
    }

    public List<UserEntity> getUsersByNames(List<String> userNames) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        JPAQuery query = getQuery();

        query.from(qUserEntity);
        query.where(
                qUserEntity.loginName.in(userNames).and(qUserEntity.status.eq(StatusEnum.STATUS_VALID))
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
        if(StringUtils.isBlank(loginName) || "anonymousUser".equals(loginName)){
            //获取token
            String token = request.getHeader(ConstantKey.ACCESS_TOKEN);
            if(StringUtils.isNotBlank(token)){
                Claims claims = Jwts.parser().setSigningKey(ConstantKey.SIGNING_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody();
                String user = claims.getSubject();
                if (user != null) {
                    String tmpLoginName = user.split("-")[0];
                    if(StringUtils.isNotBlank(tmpLoginName) && !"anonymousUser".equals(tmpLoginName)){
                        loginName = tmpLoginName;
                    }
                }
            }
        }
        log.info("获取当前用户LoginName：{}。",loginName);
        UserEntity userEntity = null;
        if(StringUtils.isNotBlank(loginName)){
            userEntity = getUser(loginName);
        }
        if(userEntity != null){
            return userEntity;
        }else {
           // log.info("getCurrentUser：没有当前用户信息。");
        }

        /*String userId = request.getParameter("userId");
        if(StringUtils.isNotBlank(userId)){
            UserEntity tempUser = getUserById(userId);
            if(tempUser != null){
                return tempUser;
            }
        }*/
        return null;
    }

    public UserEntity getThirdUser(ThirdUserVo thirdUserVo) {
        UserEntity userEntity =  null;
        if(StringUtils.isNotBlank(thirdUserVo.getUnionId())){
            userEntity =  getUserByUnionId(thirdUserVo.getUnionId());
        }
        if(StringUtils.isNotBlank(thirdUserVo.getAppleId())){
            userEntity =  getUserByAppleId(thirdUserVo.getAppleId());
        }
        /*if(userEntity==null && StringUtils.isNotBlank(thirdUserVo.getLoginName())){
            userEntity =  getUser(thirdUserVo.getLoginName());
        }*/

        return userEntity;
    }


    @Transactional
    public UserEntity saveThirdUser(ThirdUserVo thirdUserVo) {

        //没有这创建帐户
        UserEntity userEntity = new UserEntity();
        if(StringUtils.isNotBlank(thirdUserVo.getLoginName())){
            userEntity.setLoginName(thirdUserVo.getLoginName());
        }else{
            userEntity.setLoginName(thirdUserVo.getMobile());
        }
        userEntity.setAppleId(thirdUserVo.getAppleId());
        userEntity.setUnionId(thirdUserVo.getUnionId());
        if(StringUtils.isNotBlank(thirdUserVo.getUserName())){
            userEntity.setName(thirdUserVo.getUserName());
        }else{
            userEntity.setName(thirdUserVo.getMobile());
        }

        userEntity.setHeadPortrait(thirdUserVo.getUserAvatar());
        userEntity.setPassword(MD5.GetMD5Code(UserEntity.DEFAULT_PASSWORD));
        userEntity.setAccessToken(thirdUserVo.getAccessToken());
        if(StringUtils.isNotBlank(thirdUserVo.getMobile())){
            userEntity.setMobile(thirdUserVo.getMobile());
        }
        if(StringUtils.isNotBlank(thirdUserVo.getEmail())){
            userEntity.setEmail(thirdUserVo.getEmail());
        }


        userDao.save(userEntity);

        return userEntity;
    }

    @Transactional
    public UserEntity saveThirdUser(ThirdUserVo thirdUserVo,UserEntity userEntity) {

        //直接关联现有帐户
        if(StringUtils.isNotBlank(thirdUserVo.getLoginName())){
            userEntity.setLoginName(thirdUserVo.getLoginName());
        }

        if(StringUtils.isNotBlank(thirdUserVo.getAppleId())){
            userEntity.setAppleId(thirdUserVo.getAppleId());
        }
        if(StringUtils.isNotBlank(thirdUserVo.getUnionId())){
            userEntity.setUnionId(thirdUserVo.getUnionId());
        }

        if(StringUtils.isNotBlank(thirdUserVo.getUserName())){
            userEntity.setName(thirdUserVo.getUserName());
        }
        if(StringUtils.isNotBlank(thirdUserVo.getUserAvatar())){
            userEntity.setHeadPortrait(thirdUserVo.getUserAvatar());
        }
        if(StringUtils.isBlank(userEntity.getPassword())){
            userEntity.setPassword(MD5.GetMD5Code(UserEntity.DEFAULT_PASSWORD));
        }

        userEntity.setAccessToken(thirdUserVo.getAccessToken());
        if(StringUtils.isNotBlank(thirdUserVo.getMobile())){
            userEntity.setMobile(thirdUserVo.getMobile());
        }
        if(StringUtils.isNotBlank(thirdUserVo.getEmail())){
            userEntity.setEmail(thirdUserVo.getEmail());
        }

        userDao.save(userEntity);

        return userEntity;
    }
}
