package com.xiushang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * @author ZKUI created by 2021-04-02 下午4:54
 */
@SpringBootApplication
@Slf4j
public class MqApplication extends SpringBootServletInitializer {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext application = SpringApplication.run(MqApplication.class, args);

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
    return builder.sources(MqApplication.class);
  }

}
