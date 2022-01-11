package com.xiushang.validation.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

public class EnumProperties {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 枚举名称
     */
    private String name;

    /**
     * 枚举详情
     */
    @NestedConfigurationProperty
    private List<Item> items;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item implements Comparable<Item> {
        /**
         * 枚举项值
         */
        private Object value;
        /**
         * 枚举项名称
         */
        private String name;
        /**
         * 排序
         */
        private int sort;
        /**
         * 启用
         */
        private boolean enabled = true;

        @Override
        public int compareTo(Item o) {
            return this.getSort() - o.getSort();
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}
