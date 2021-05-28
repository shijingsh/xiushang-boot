package com.mg.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;
import java.util.Properties;

/**
 * 将数据库中的数据相互同步.

 */
public class SyncDBData {
    private static Logger logger = LoggerFactory.getLogger(SyncDBData.class);
    private static String sh = isWindows()?"cmd.exe":"sh";

    public static void main(String args[]) throws IOException {
        SyncDBData syncDBData = new SyncDBData();
        SyncDBDataContext context  = syncDBData.init();
        syncDBData.dumpSource(context);
        syncDBData.restoreDump(context);
        System.exit(0);
    }

    public SyncDBDataContext init() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Properties properties = PropertiesLoaderUtils.loadProperties(resolver.getResource("/db-connection.properties"));
        SyncDBDataContext context = new SyncDBDataContext(properties);
        return context;
    }

    public void dumpSource(SyncDBDataContext context) {
        //1. dump source
        String cmd = String.format("%s -h %s -u %s -p%s -R %s > %s%s%s",
                context.mysqldumpCmd,
                context.sourceDBip,
                context.sourceDBUsername,
                context.sourceDBPassword,
                context.sourceDBSchema,
                context.dumpPath,
                File.separator,
                context.dumpFilename
        );
        runCmd(cmd);
    }

        //2. backup local
    public void backupLocal(SyncDBDataContext context) {
        String cmd = String.format("%s -h %s -u %s -p%s -R %s > %s%s%s",
                context.mysqldumpCmd,
                context.localDBip,
                context.localDBUsername,
                context.localDBPassword,
                context.localDBSchema,
                context.dumpPath,
                File.separator,
                "local-dump.sql"
        );
        runCmd(cmd);
    }


    public void restoreDump(SyncDBDataContext context) {
        //
        String cmd = String.format("echo \"drop schema if exists %s; CREATE DATABASE %s DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;\" > %s%s%s",
                context.localDBSchema,
                context.localDBSchema,
                context.dumpPath,
                File.separator,
                "local_schema_create_drop.sql"
        );
        runCmd(cmd);

        //4. import source
        cmd = String.format("%s -h %s -u %s -p%s < %s%s%s",
                context.mysqlCmd,
                context.localDBip,
                context.localDBUsername,
                context.localDBPassword,
                context.dumpPath,
                File.separator,
                "local_schema_create_drop.sql"
        );
        runCmd(cmd);
        cmd = String.format("%s -h %s -u %s -p%s %s < %s%s%s",
                context.mysqlCmd,
                context.localDBip,
                context.localDBUsername,
                context.localDBPassword,
                context.localDBSchema,
                context.dumpPath,
                File.separator,
                context.dumpFilename
        );
        runCmd(cmd);

        //4. opt. restore local
//        cmd = String.format("%s -h %s -u %s -p%s %s < %s%s%s",
//                mysqlCmd,
//                localDBip,
//                localDBUsername,
//                localDBPassword,
//                localDBSchema,
//                dumpPath,
//                File.separator,
//                "local-dump.sql"
//        );
//        runCmd(cmd);
    }

    protected void runCmd(String cmd) {
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        try {
            System.out.println(cmd);
            Process p = run.exec(new String[]{sh, "-c", cmd});// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader( in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            inBr.close();
            in.close();

            in = new BufferedInputStream(p.getErrorStream());
            inBr = new BufferedReader(new InputStreamReader( in));
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                    System.err.println("命令执行失败!");
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isWindows(){
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }
}
