package com.xiushang.common.utils;

import com.querydsl.core.types.Predicate;
import com.xiushang.jpa.repository.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * service基类
 * Created by kf on 2016/11/8.
 */

public abstract class BaseServiceImpl<T> {
    @Autowired
    private BaseDao<T> baseDao;
    @Autowired
    public HttpServletRequest request;

    @Autowired
    public HttpServletResponse response;

    public T save(T t){
       return baseDao.save(t);
    }

    public T saveAndFlush(T t){
        return baseDao.saveAndFlush(t);
    }

    public T get(String id){
        Optional<T> optional = baseDao.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void delete(String id){
         baseDao.deleteById(id);
    }

    public void delete(T t){
        baseDao.delete(t);
    }

    public Page<T> findPageList(Predicate predicate, Pageable pageable){
        return baseDao.findAll(predicate, pageable);
    }
}