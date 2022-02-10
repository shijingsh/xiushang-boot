package com.xiushang.entity;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class BaseLazy implements java.io.Serializable {

    /**
     * 判断对象是否是延迟加载的
     * @param value
     * @return
     */
    protected boolean isLazy(Object value) {
        if (value instanceof HibernateProxy) {//hibernate代理对象
            LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
            if (initializer.isUninitialized()) {
                return true;
            }
        } else if (value instanceof PersistentCollection) {//实体关联集合一对多等
            PersistentCollection collection = (PersistentCollection) value;
            if (!collection.wasInitialized()) {
                return true;
            }
            Object val = collection.getValue();
            if (val == null) {
                return true;
            }
        }
        return false;
    }
}
