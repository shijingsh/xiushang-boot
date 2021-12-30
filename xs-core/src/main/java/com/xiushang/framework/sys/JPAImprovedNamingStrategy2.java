package com.xiushang.framework.sys;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPAImprovedNamingStrategy2 extends ImprovedNamingStrategy {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //表名的前缀 和 后缀
    private String tablePrefix = "t_";
    private String tableSuffix = "";

    //外键的 前缀 和 后缀
    private String foreignKeyColumnPrefix = "fk_";
    private String foreignKeyColumnSuffix = "";

    private boolean isRemoveLastWord = true;

    @Override
    public String classToTableName(String className) {
        String rawTableName = super.classToTableName(className);

        if (isRemoveLastWord) {
            int pos = StringUtils.lastIndexOf(rawTableName, '_');
            return tablePrefix + StringUtils.substring(rawTableName, 0, pos) + tableSuffix;
        }

        return tablePrefix + rawTableName + tableSuffix;
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return super.propertyToColumnName(propertyName);
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        return super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable, propertyName);
    }


    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        String columnName = foreignKeyColumnPrefix + super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
        return columnName;
    }
}

