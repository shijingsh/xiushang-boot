package com.xiushang.dubbo.serialization.hibernate;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.Serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HibernateListSerializer implements Serializer {
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if (!out.addRef(obj)) {
            Collection list = (Collection)obj;
            Class cl = obj.getClass();
            boolean hasEnd;
            Iterator iter = list.iterator();
            // 如果是list或者是set则进入下面操作，默认 hessian只支持arrayList的序列化。会报java.util.List cannot be assigned from null 这个错误
            if (obj instanceof Set || obj instanceof List
                    || !Serializable.class.isAssignableFrom(cl)) {
                hasEnd = out.writeListBegin(list.size(), null);
            } else {
                hasEnd = out.writeListBegin(list.size(), obj.getClass().getName());
            }
            while(iter.hasNext()) {
                Object value = iter.next();
                out.writeObject(value);
            }

            if (hasEnd) {
                out.writeListEnd();
            }

        }
    }

}
