package com.mg.common.tools;

import com.mg.framework.sys.JPAImprovedNamingStrategy;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
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
import java.util.EnumSet;

/**
 * ddl generator
 * @author Tan Liang (Bred Tan)
 * @since 2015/2/26
 */
public class HibernateDDLGenerator {
    private static Logger logger = LoggerFactory.getLogger(HibernateDDLGenerator.class);


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "com/mg/**/*Entity.class");

//        Properties properties = PropertiesLoaderUtils.loadProperties(resolver.getResource("/db-connection.properties"));
//        String url = properties.getProperty("connection.url");//"jdbc:mysql://172.16.90.114:3306/shaoxing?useUnicode=true&characterEncoding=utf-8";
//        String username = properties.getProperty("connection.username"); //"root";
//        String password = properties.getProperty("connection.password"); //"123456";

        String url = "jdbc:mysql://localhost:3306/mg_system?useUnicode=true&characterEncoding=utf-8";
        String username = "root";
        String password = "123456";

        Configuration configuration = new Configuration();
//        configuration.setProperty(Environment.CONNECTION_PROVIDER, "");
        configuration.setProperty(Environment.URL, url);
        configuration.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        configuration.setProperty(Environment.USER, username);
        configuration.setProperty(Environment.PASS, password);
        configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty(Environment.JTA_PLATFORM, "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty(Environment.GLOBALLY_QUOTED_IDENTIFIERS, "true");
        Class.forName("com.mysql.jdbc.Driver");

        for (Resource res : resources) {
            //通过 MetadataReader得到ClassMeta信息,打印类名
//            logger.debug("{}", res.getFilename());
            MetadataReader meta = factory.getMetadataReader(res);
            //System.out.println(meta.getClassMetadata().getClassName());
            configuration.addAnnotatedClass(Class.forName(meta.getClassMetadata().getClassName()));
        }
        configuration.setPhysicalNamingStrategy(new JPAImprovedNamingStrategy());
        StandardServiceRegistry standardServiceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        Metadata metadata = new MetadataSources(standardServiceRegistry).buildMetadata();
        SchemaUpdate schemaUpdate = new SchemaUpdate();
        schemaUpdate.execute(EnumSet.of(TargetType.DATABASE),metadata,standardServiceRegistry);
    }


}
