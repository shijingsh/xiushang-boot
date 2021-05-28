package com.mg.common.instance.dao;

import com.mg.common.entity.InstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * 实例数据层
 */
public interface InstanceDao extends
        JpaRepository<InstanceEntity, String>,
        QuerydslPredicateExecutor<InstanceEntity> {

    List<InstanceEntity> findInstanceByName(String name);

    List<InstanceEntity> findInstanceByToken(String token);
}
