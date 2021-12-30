package com.xiushang.framework.entity.vo;

/**
 * 非常简单的数据传输，只有两个属性：ID和NAME
 */
public class SimpleDataVO {
    private String id;
    private String name;

    public SimpleDataVO() {
    }

    public SimpleDataVO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleDataVO(int id, String name) {
        this.id = id+"";
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
