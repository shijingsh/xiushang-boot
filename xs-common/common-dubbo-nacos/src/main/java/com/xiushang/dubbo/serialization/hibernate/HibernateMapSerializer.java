package com.xiushang.dubbo.serialization.hibernate;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.MapSerializer;
import com.alibaba.com.caucho.hessian.io.Serializer;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HibernateMapSerializer implements Serializer {
    private MapSerializer delegate = new MapSerializer();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if (Hibernate.isInitialized(obj)) {
            delegate.writeObject(new HashMap((Map) obj), out);
        } else {
            delegate.writeObject(new HashMap(), out);
        }
    }
}
