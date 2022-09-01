package com.xiushang.dubbo.serialization.hibernate;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.CollectionSerializer;
import com.alibaba.com.caucho.hessian.io.Serializer;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class HibernatePersistentListSerializer implements Serializer {
    private CollectionSerializer delegate = new CollectionSerializer();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if (Hibernate.isInitialized(obj)) {
            delegate.writeObject(new ArrayList((Collection) obj), out);
        } else {
            delegate.writeObject(new ArrayList(), out);
        }
    }

}
