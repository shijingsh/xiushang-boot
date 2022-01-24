package com.xiushang.common.utils;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T> {
    void save(T t);
    T saveAndGet(T t);
    void saveAndFlush(T t);
    T saveAndGetFlush(T t);
    T get(String id);
    T getFull(String id);
    void delete(String id);
    void delete(T t);
    Page<T> findPageList(Predicate predicate, Pageable pageable);
}
