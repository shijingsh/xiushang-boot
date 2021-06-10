package com.xiushang.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 数据访问层基类
 * Created by kf on 2016/11/7.
 */
@NoRepositoryBean
public interface BaseDao<T> extends
        JpaRepository<T, String>,
        QuerydslPredicateExecutor<T> {
}
