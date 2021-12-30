package com.xiushang.common.utils;

import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyLoadFilter implements PropertyFilter {
    private static Logger logger = LoggerFactory.getLogger(LazyLoadFilter.class);
    protected String excludeObjectNames[] = null;

    public LazyLoadFilter() {
    }

    /**
     * 传入需要过滤的类和属性
     *
     * @param inputFilterPropertyNames class.preoperty或直接propertyName，property看开始，class看末尾，如ItemEntity.belong
     */
    public LazyLoadFilter(String inputFilterPropertyNames) {
        excludeObjectNames = inputFilterPropertyNames.split(";");
    }

    /**
     * 对于没有加载的延迟加载，立刻加载。必须用在同一个SESSION里。
     *
     * @param object 属性所在的对象
     * @param name   属性名
     * @param value  属性值
     * @return 返回false属性将被忽略，ture属性将被保留
     */
    @Override
    public boolean apply(Object object, String name, Object value) {
        if (isMatchExcludeObjectAndName(object, name))
            return false;
        if (value instanceof HibernateProxy) {//hibernate代理对象
            LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
            if (initializer.isUninitialized()) {
                initializer.initialize();
                return true;
            }
        } else if (value instanceof PersistentCollection) {//实体关联集合一对多等
            PersistentCollection collection = (PersistentCollection) value;
            if (!collection.wasInitialized()) {
                collection.forceInitialization();
//                return false;
                return true;
            }
            Object val = collection.getValue();
            if (val == null) {
                return false;
            }
        }
//        if (name.startsWith("belong"))
//            return false;
        return true;
    }

    protected boolean isMatchExcludeObjectAndName(Object object, String name) {
        //先判断是否有不需要转换的属性
        if (excludeObjectNames != null) {
//            logger.debug("has exclude object names!");
            //有需要额外判断的
            for (String propertyName : excludeObjectNames) {
//                logger.debug("check {}.{} for: {}", object.getClass().toString(), name, propertyName);

                //如果是空的，那就跳过继续找下面的
                if (StringUtils.isBlank(propertyName)) {
//                    logger.debug("property name is blank.");
                    continue;
                }

                if (propertyName.indexOf(".") > 0) {
                    //带有class名字了
                    String s[] = propertyName.split("\\.");
//                    logger.debug("check {}.{} for obj name {} and property name {}", object.getClass().toString(), name, s[0], s[1]);
                    if (object.getClass().toString().endsWith(s[0]) && name.startsWith(s[1])) {
//                        logger.debug("match obj name {} and property name {}...............", s[0], s[1]);
//                        logger.debug("matching !!!!!!!");
                        return true;
                    }
                } else {
                    //仅仅是属性名字
                    if (name.startsWith(propertyName)) {
//                        logger.debug("match property name {}", propertyName);
 //                       logger.debug("matching !!!!!!!");
                        return true;
                    }
                }
            }
//            logger.debug("nothing is match");
        }
        return false;
    }

}
