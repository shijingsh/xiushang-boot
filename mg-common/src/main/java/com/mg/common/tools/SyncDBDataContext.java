package com.mg.common.tools;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 用来同步数据的上下文对象，存放大量中间变量

 */
public class SyncDBDataContext {
    public String localDBip;
    public String localDBSchema;// = properties.getProperty("connection.schema");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
    public String localDBUsername;// = properties.getProperty("connection.username"); //"root";
    public String localDBPassword;// = properties.getProperty("connection.password"); //"123456";
    public String sourceDBip;// = properties.getProperty("source.connection.ip");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
    public String sourceDBSchema;// = properties.getProperty("source.connection.schema");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
    public String sourceDBUsername;// = properties.getProperty("source.connection.username"); //"root";
    public String sourceDBPassword;// = properties.getProperty("source.connection.password"); //"123456";
    public String mysqldumpCmd;// = properties.getProperty("mysqldump.cmd");// ssh root@192.168.154.11 mysqldump
    public String mysqlCmd;// = properties.getProperty("mysql.cmd");// ssh root@192.168.154.11 mysqldump
    public String dumpPath;// = properties.getProperty("mysql.cmd");// ssh root@192.168.154.11 mysqldump
    public String dumpFilename;// = properties.getProperty("mysql.cmd");// ssh root@192.168.154.11 mysqldump


    public SyncDBDataContext(Properties properties) {
        localDBip = properties.getProperty("connection.ip");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
        localDBSchema = properties.getProperty("connection.schema");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
        localDBUsername = properties.getProperty("connection.username"); //"root";
        localDBPassword = properties.getProperty("connection.password"); //"123456";

        sourceDBip = properties.getProperty("source.connection.ip");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
        sourceDBSchema = properties.getProperty("source.connection.schema");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
        sourceDBUsername = properties.getProperty("source.connection.username"); //"root";
        sourceDBPassword = properties.getProperty("source.connection.password"); //"123456";

        mysqldumpCmd = properties.getProperty("mysqldump.cmd");// ssh root@192.168.154.11 mysqldump
        mysqlCmd = properties.getProperty("mysql.cmd");// ssh root@192.168.154.11 mysqldump
        if(StringUtils.isBlank(mysqldumpCmd))
            mysqldumpCmd = "mysqldump";

        dumpPath = properties.getProperty("dump.path");// ssh root@192.168.154.11 mysqldump
        if(StringUtils.isBlank(dumpPath))
            dumpPath = "/tmp";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        dumpFilename = String.format("%s-%s-%s.sql",
                sdf.format(new Date()),
                sourceDBip.replace(".", "-"),
                sourceDBSchema
        );
    }
}
