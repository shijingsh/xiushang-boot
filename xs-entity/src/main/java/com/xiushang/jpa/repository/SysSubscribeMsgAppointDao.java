package com.xiushang.jpa.repository;

import com.xiushang.entity.SubscribeMsgAppointEntity;

import java.util.List;

public interface SysSubscribeMsgAppointDao extends BaseDao<SubscribeMsgAppointEntity> {

    List<SubscribeMsgAppointEntity> findBySubscribeObjectIdAndUserId(String subscribeObjectId, String id);
}
