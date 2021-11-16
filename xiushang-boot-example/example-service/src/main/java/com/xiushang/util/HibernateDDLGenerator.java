package com.xiushang.util;

import com.xiushang.framework.sys.JPAImprovedNamingStrategy;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.EnumSet;

/**
 * ddl generator
 */
public class HibernateDDLGenerator {

    public static void main(String args[]) throws ClassNotFoundException, IOException {

        MetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "com/mg/**/*Entity.class");

        String url = "jdbc:mysql://localhost:3306/mg_xiushang?useUnicode=true&characterEncoding=utf-8";
        String username = "root";
        String password = "380177";

        Configuration configuration = new Configuration();
        configuration.setProperty(Environment.URL, url);
        configuration.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        configuration.setProperty(Environment.USER, username);
        configuration.setProperty(Environment.PASS, password);
        configuration.setProperty(Environment.SHOW_SQL,"true");
        configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

        configuration.setProperty(Environment.GLOBALLY_QUOTED_IDENTIFIERS, "true");
        Class.forName("com.mysql.jdbc.Driver");

        try {
            Connection con = DriverManager.getConnection(url, username, password);

            configuration.setPhysicalNamingStrategy(new JPAImprovedNamingStrategy());
            for (Resource res : resources) {
                //通过 MetadataReader得到ClassMeta信息,打印类名
//            logger.debug("{}", res.getFilename());
                MetadataReader meta = factory.getMetadataReader(res);
                //logger.info(meta.getClassMetadata().getClassName());
                configuration.addAnnotatedClass(Class.forName(meta.getClassMetadata().getClassName()));
            }

            //AnnotationConfiguration cfg = new AnnotationConfiguration();
            //cfg.addAnnotatedClass(com.mygogo.grade.user.entity.User.class);


            StandardServiceRegistry service1 = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            //ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            //StandardServiceRegistry standardServiceRegistry = configuration.getStandardServiceRegistryBuilder().build();
            Metadata metadata = new MetadataSources(service1).buildMetadata();
            //生产并输出SQL到当前目录和数据库。
            SchemaExport schemaExport = new SchemaExport();
            schemaExport.setDelimiter(";");
            schemaExport.setFormat(true);
            schemaExport.setOutputFile("D:\\work\\shshijing\\mg.sql");
            //创建表结构，第一个true表示在控制台打印SQL语句，第二个true表示导入SQL到数据库
            //schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);
            //schemaExport.create(EnumSet.of(TargetType.SCRIPT), metadata);
            //StringBuilder sb = new StringBuilder();
            //TextWriter output = new StringWriter(sb);
            schemaExport.execute(EnumSet.of(TargetType.SCRIPT),SchemaExport.Action.BOTH, metadata,service1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
