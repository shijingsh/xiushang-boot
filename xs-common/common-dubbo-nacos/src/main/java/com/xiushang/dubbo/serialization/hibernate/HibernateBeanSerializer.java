package com.xiushang.dubbo.serialization.hibernate;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.Serializer;
import org.hibernate.Hibernate;

import java.io.IOException;

public class HibernateBeanSerializer implements Serializer {
    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        boolean init = Hibernate.isInitialized(obj);

        out.writeObject(init ? obj : null);
        out.flush();
        return;
    }
}
