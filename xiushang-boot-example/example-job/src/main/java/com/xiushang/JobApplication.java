package com.xiushang;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
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

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@Slf4j

@EnableJpaRepositories(basePackages = "com.xiushang") //用于扫描Dao @Repository
@EntityScan("com.xiushang.entity") //用于扫描JPA实体类 @Entity
@EnableScheduling
@ServletComponentScan(basePackages = {"com.xiushang.filter"})
public class JobApplication extends SpringBootServletInitializer {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext application = SpringApplication.run(JobApplication.class, args);

    Environment env = application.getEnvironment();
    log.info("\n----------------------------------------------------------\n\t" +
            "Application '{}' is running! \n" +
            "----------------------------------------------------------",
        env.getProperty("spring.application.name"));
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    //无端口方式启动
    return builder.sources(JobApplication.class).web(WebApplicationType.NONE);
  }
/*
  @Bean
  public BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }*/
}
