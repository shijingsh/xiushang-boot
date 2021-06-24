package amy.marketing;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import java.net.InetAddress;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author ZKUI created by 2021-04-02 下午4:54
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("amy.marketing.dao.mapper")
@Slf4j
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
}
