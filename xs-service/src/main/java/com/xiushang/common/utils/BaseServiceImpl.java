package com.xiushang.common.utils;

import com.querydsl.core.types.Predicate;
import com.xiushang.common.exception.ServiceException;
import com.xiushang.common.user.service.UserService;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.BaseUserEntity;
import com.xiushang.framework.utils.DeleteEnum;
import com.xiushang.framework.utils.UserHolder;
import com.xiushang.jpa.repository.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * service基类
 * Created by kf on 2016/11/8.
 */

public abstract class BaseServiceImpl<T> {
    @Autowired
    private BaseDao<T> baseDao;

    @Autowired
    public UserService userService;

    @PersistenceContext
    public EntityManager entityManager;

    public void save(T t) {
        baseDao.save(t);
    }

    public T saveAndGet(T t) {
        T tSaved = baseDao.save(t);
        if (t instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) tSaved;
            String id = baseEntity.getId();
            return get(id);
        }

        return tSaved;
    }

    public void saveAndFlush(T t) {
        baseDao.saveAndFlush(t);
    }


    public T saveAndGetFlush(T t) {

        T tSaved = baseDao.saveAndFlush(t);
        if (t instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) tSaved;
            String id = baseEntity.getId();
            return get(id);
        }

        return tSaved;
    }

    public T get(String id) {
        Optional<T> optional = baseDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public T getFull(String id) {
        T t = get(id);
        if (t != null) {
            LazyLoadUtil.fullLoad(t);
        }
        return t;
    }

    public void delete(String id) {
        //删除权限验证
        String userId = userService.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            throw new ServiceException("登陆超时，请重新登陆！");
        }
        T t = get(id);
        if (t instanceof BaseEntity) {
            //存在删除标志位的表，逻辑删除数据
            BaseEntity baseEntity = (BaseEntity) t;
            if (UserHolder.getUserAdmin() || baseEntity.getCreatedById().equals(userId)) {
                baseEntity.setDeleted(DeleteEnum.INVALID);
                baseDao.save(t);
            } else {
                throw new ServiceException("抱歉，只能删除自己添加的数据！");
            }
        } else if (t instanceof BaseUserEntity) {
            //没有删除标志位的表，直接删除数据
            BaseUserEntity baseEntity = (BaseUserEntity) t;
            if (UserHolder.getUserAdmin() || baseEntity.getUserId().equals(userId)) {
                baseDao.deleteById(id);
            } else {
                throw new ServiceException("抱歉，只能删除自己添加的数据！");
            }
        } else {
            // do nothing
            throw new ServiceException("抱歉，不能删除该数据！");
        }
    }

    public Page<T> findPageList(Predicate predicate, Pageable pageable) {
        return baseDao.findAll(predicate, pageable);
    }
}
