package com.xiushang;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;


@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@Slf4j
@EnableJpaRepositories(basePackages = "com.xiushang") //用于扫描Dao @Repository
@EntityScan("com.xiushang.entity") //用于扫描JPA实体类 @Entity
@EnableScheduling
@ServletComponentScan(basePackages = {"com.xiushang.filter"})
public class OrderProviderApplication extends SpringBootServletInitializer {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext application = SpringApplication.run(OrderProviderApplication.class, args);

    Environment env = application.getEnvironment();
    log.info("\n----------------------------------------------------------\n\t" +
            "Application '{}' is running! Access URLs:\n\t" +
            "Login: \thttp://{}:{}/login\n\t" +
            "Doc: \thttp://{}:{}/doc.html\n" +
            "----------------------------------------------------------",
        env.getProperty("spring.application.name"),
        InetAddress.getLocalHost().getHostAddress(),
        env.getProperty("server.port"),
        InetAddress.getLocalHost().getHostAddress(),
        env.getProperty("server.port"));
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(OrderProviderApplication.class);
  }

}
