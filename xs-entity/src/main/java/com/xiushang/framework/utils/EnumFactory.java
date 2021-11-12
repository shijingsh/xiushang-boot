package com.xiushang.framework.utils;

import com.xiushang.framework.model.BaseInnerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 内部枚举的工厂类，仅提供给内部使用。
 * 通过本工厂，可以获取到枚举对象。通过枚举对象可以获得相应属性值，如Map、id、name等
 */
public final class EnumFactory {
    private static Logger logger = LoggerFactory.getLogger(EnumFactory.class);
    private static Map<Class<? extends BaseInnerEnum>, BaseInnerEnum> innerEnumMap = new HashMap<>();

    /**
     * 不可初始化，全部静态方法。
     */
    private EnumFactory(){

    }

    /**
     * 注册内部枚举类，一个注册一次就行了。重复注册只是替换之前的，没有任何意义。
     * @param baseInnerEnum 统中的内部枚举对象，如SexEnum等
     */
    public static void register(BaseInnerEnum baseInnerEnum) {
        innerEnumMap.put(baseInnerEnum.getClass(), baseInnerEnum);
    }

    /**
     * 根据class类型获取内部枚举对象，如果对象不存在，就new一个。
     * @param cls 从 BaseInnerEnum 衍生的子类
     * @return BaseInnerEnum 的内部枚举类对象
     */
    public static BaseInnerEnum getEnum(Class<? extends BaseInnerEnum> cls) {
        if (!innerEnumMap.containsKey(cls)) {
            try {
                innerEnumMap.put(cls, cls.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return innerEnumMap.get(cls);
    }

    //==========================================================================
    //以下是一些快捷方法

    /**
     * 根据class类型获取内部枚举map值，用于前端列出所有候选项
     * @param cls 从 BaseInnerEnum 衍生的子类
     * @return key为id或name（有些内部枚举类没有id只有name），value为name
     */
    public static Map<String, String> getEnumMap(Class<? extends BaseInnerEnum> cls) {
        return getEnum(cls).getEnumMap();
    }

    public static String getEnumName(Class<? extends BaseInnerEnum> cls, int id) {
        return getEnum(cls).getName(id);
    }

    public static int getEnumId(Class<? extends BaseInnerEnum> cls, String name) {
        return getEnum(cls).getId(name);
    }

    public static int putEnum(Class<? extends BaseInnerEnum> cls, int id, String name) {
        getEnum(cls).ENUM(id, name);
        return id;
    }


}
