package com.xiushang.jpa.repository;

import com.xiushang.entity.SubscribeMsgEntity;
import java.util.List;

public interface SysSubscribeMsgDao extends BaseDao<SubscribeMsgEntity> {

    List<SubscribeMsgEntity> findBySubscribeObjectId(String subscribeObjectId);
}
