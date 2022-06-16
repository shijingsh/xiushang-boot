package com.xiushang.framework.sys;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.internal.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class JPAImprovedNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public String propertyToColumnName(String propertyName) {
        return addUnderscores(StringHelper.unqualify(propertyName));
    }

    protected static String addUnderscores(String name) {
        StringBuilder buf = new StringBuilder(name.replace('.', '_'));

        for(int i = 1; i < buf.length() - 1; ++i) {
            if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i)) && Character.isLowerCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }

        return buf.toString().toLowerCase(Locale.ROOT);
    }

    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String columnName = propertyToColumnName(name.getText());
        return Identifier.toIdentifier(columnName);
    }

    public static void main(String args[]){
        System.out.println(addUnderscores("userId"));
        System.out.println(StringHelper.unqualify("userId"));
    }
}
