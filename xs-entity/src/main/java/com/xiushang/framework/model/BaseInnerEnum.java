package com.xiushang.framework.model;

import com.xiushang.framework.utils.EnumFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 内部枚举基类（试点）.
 * 基本写了很多底层方法，具体使用的时候，继承本类即可。继承后的写法相对比较简单，也容
 * 易理解，具体可以参考SexEnum和NationEnum两种写法。
 * 1）id为数字，则直接调用ENUM(int, String)方法即可，保留数字
 * 2）id不关注，则直接调用ENUM(String)方法即可，数字为顺序增长，但对外返回的map里，
 * key即为name，保持最后赋值的一致性。
 *
 * 另外，本类不可被实例化（构造方法都是protected），防止误操作。
 *
 */
public abstract class BaseInnerEnum {
    private static Logger logger = LoggerFactory.getLogger(BaseInnerEnum.class);

//    public

    /**
     * 主键，即key值
     */
    protected int id;

    /**
     * 显示名称，即value值
     */
    protected String name;

    /**
     * 保存key-value的map。一个类一个map。
     */
    private Map<String, String> enumStringMap = new LinkedHashMap<>();
    private Map<Integer, String> enumOrginMapKeyName = new LinkedHashMap<>();
    private Map<String, Integer> enumOrginMapNameKey = new LinkedHashMap<>();

//    /**
//     * 保存对象列表。一个类一个List
//     */
//    private List<BaseInnerEnum> enumList = new LinkedList<BaseInnerEnum>();

    /**
     * 快捷构造枚举的静态方法.
     * 传入参数既是key值，也是value值
     * @param name 传入的枚举显示名称，例如民族中的“汉族”
     */
    public void ENUM(String name) {
        Map<String, String> enumMap = getEnumMap();

        enumMap.put(name, name);
    }

    /**
     * 快速构造枚举的静态方法.
     * 传入参数分别是数值类型的key和字符串类型的value
     * @param id key
     * @param name value，显示名称
     */
    public void ENUM(int id, String name) {
//        Map<String, String> enumMap = getEnumMap();
//        List<BaseInnerEnum> enumList = getEnumList();

//        BaseInnerEnum e = new BaseInnerEnum(id, name);
//        e.setId(id);
//        e.setName(name);
//        enumList.add(e);
        enumStringMap.put(id+"", name);
        enumOrginMapKeyName.put(id, name);
        enumOrginMapNameKey.put(name, id);
    }

    /**
     * 根据数字类型的key获得枚举对象
     * @param id key，对于显示名称就是key的枚举来说，这里传入的是顺序值
     * @return 枚举对象。如果没有找到则会抛出RuntimeException。
     */
    public BaseInnerEnum getEnum(int id) {
//        List<BaseInnerEnum> enumList = getEnumList();
//        for (BaseInnerEnum e : enumList) {
//            if (e.getId() == id) {
//                return e;
//            }
//        }
        throw new RuntimeException("没有找到ID值：" + id);
    }

    /**
     * 根据枚举显示名称获取枚举对象
     * @param name 显示名称。注：显示名称一般不会重复
     * @return 枚举对象。如果没有找到则会抛出RuntimeException。
     */
    public BaseInnerEnum getEnum(String name) {
//        List<BaseInnerEnum> enumList = getEnumList();
//        for (BaseInnerEnum e : enumList) {
//            if (StringUtils.equals(name, e.getName())) {
//                return e;
//            }
//        }
        throw new RuntimeException("没有找到name值：" + name);
    }

    /**
     * 获得枚举类的所有枚举值map
     * @return key-value组成的map，前端可直接根据key-value组成选择项
     */
    public Map<String, String> getEnumMap() {
        return enumStringMap;
    }

//    protected List<BaseInnerEnum> getEnumList() {
//        return enumList;
//    }

    /**
     * 根据数字类型的key获得枚举显示名称
     * @param id key，对于显示名称就是key的枚举来说，这里传入的是顺序值
     * @return 枚举名称。如果没有找到则会抛出RuntimeException。
     */
    public String getName(int id) {
        return enumOrginMapKeyName.get(id);
//        BaseInnerEnum e = getEnum(id);
//        return e.getName();
    }

    public int getId(String name) {
        return enumOrginMapNameKey.get(name);
    }

    /**
     * 构造方法.
     * 如果只写BaseInnerEnum(int id, String name)，继承类会报错。另外，采用
     * protected是因为本类不可外部实例化。
     */
    protected BaseInnerEnum() {
        EnumFactory.register(this);
    }

    /**
     * 构造方法.
     * @param id key
     * @param name value，显示名称
     */
    protected BaseInnerEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Map<Integer, String> getEnumOrginMapKeyName() {
		return enumOrginMapKeyName;
	}

	public Map<String, Integer> getEnumOrginMapNameKey() {
		return enumOrginMapNameKey;
	}
}
