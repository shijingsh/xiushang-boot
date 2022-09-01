package com.xiushang.dubbo.serialization;

import com.alibaba.com.caucho.hessian.io.HessianProtocolException;
import com.alibaba.com.caucho.hessian.io.Serializer;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;
import com.xiushang.dubbo.serialization.hibernate.HibernateBeanSerializer;
import com.xiushang.dubbo.serialization.hibernate.HibernateListSerializer;
import com.xiushang.dubbo.serialization.hibernate.HibernateMapSerializer;
import com.xiushang.dubbo.serialization.hibernate.HibernatePersistentListSerializer;
import org.hibernate.collection.internal.AbstractPersistentCollection;
import org.hibernate.collection.internal.PersistentMap;

import java.util.List;

public class PersistentSerializerFactory extends SerializerFactory{
    public static final SerializerFactory TESTSERIALIZER_FACTORY = new PersistentSerializerFactory();

    private HibernatePersistentListSerializer persistentListSerializer = new HibernatePersistentListSerializer();
    private HibernateMapSerializer mapSerializer = new HibernateMapSerializer();
    private HibernateBeanSerializer hibernateBeanSerializer = new HibernateBeanSerializer();

    private HibernateListSerializer listSerializer = new HibernateListSerializer();

    public PersistentSerializerFactory() {
    }

    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    @SuppressWarnings("rawtypes")
    //序列化时，判断persistent相关类型，添加持久化对象的序列化方式，如果不是，则走父类的序列化方法
    public Serializer getSerializer(Class cl) throws HessianProtocolException {

        if (PersistentMap.class.isAssignableFrom(cl)) {
            return mapSerializer;
        } else if (AbstractPersistentCollection.class.isAssignableFrom(cl)) {
            return persistentListSerializer;
        } else if (cl.getSimpleName().contains("_$$_javassist_")) {
            return hibernateBeanSerializer;
        } else if (List.class.isAssignableFrom(cl)) {
            return listSerializer;
        }
        return super.getSerializer(cl);
    }



}
