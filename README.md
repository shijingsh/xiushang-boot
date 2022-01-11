### 简介
 Xiushang boot .
 使用springboot + dubbo +rocketMQ + jpa实现的轻量级微服务架构。
 为API接口项目开发，完成API通用功能：用户模块、Api授权、短信、定时任务、上传文件、文档管理、微信相关接口。
 主要技术栈：springboot dubbo rocketMQ jpa oauth2 solr job pay marketingApi knife4j Swagger2  
 
  实例：https://github.com/shijingsh/xiushang-boot/tree/master/xiushang-boot-example
  
### 数据库脚本

   application.yml 文件中 修改数据库连接为你的数据库
    
```
spring:
  datasource:
    username: proxy1
    password: proxy1
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.29.11:3306/mg_xiushang?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2b8
    type: com.alibaba.druid.pool.DruidDataSource
```
    
   项目依赖的数据库脚本，由hibernate自动生成。需要创建空数据库。
   application.yml 文件中 确保ddl-auto 选项设置为create
   
```
     jpa:
       properties:
         hibernate:
           dialect: org.hibernate.dialect.MySQL5InnoDBDialect
       hibernate:
         ddl-auto: update # Hibernate ddl auto (create, create-drop, validate, update)
```

   注意:数据库生成后，请务必修改ddl-auto 选项设置为update，以免数据被清空。
### 实体类

jpa对于简单的业务非常方便，为了实现简约的代码风格。约定所有实体类不使用一对多
实体类尽量少于对象级联。


jpa自动生成类找不到问题: Q***Entity is not found
运行如下命令解决

```
mvn clean 
mvn install
```

### 使用


安装你需要的依赖
```xml
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-core</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-entity</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-service</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-jwt-security</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-common</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-dubbo</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-rocketmq</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-marketing</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-solr</artifactId>
            <version>1.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-job</artifactId>
            <version>1.5.0</version>
        </dependency>
```


### api 多版本控制

- 方法上增加注解 @ApiVersion
- mapping中增加 {version}
    如：@GetMapping("/{version}/get")

-  类增加注解@ApiVersion 代表默认版本号1
  （注意mapping仍需要增加mapping中增加 {version}）
  
```java
@ApiVersion
@Api(tags = "常用接口")
@Controller
@RequestMapping(value = "/api/news",
        produces = "application/json; charset=UTF-8")
public class NewsController {

    @ApiOperation(value = "获取公告详情")
    @ResponseBody
    @GetMapping("/{version}/get")
    public CommonResult<NewsEntity> get(String id) {

        return CommonResult.success();
    }

    @ApiVersion(2)
    @ApiOperation(value = "获取公告详情V2")
    @ResponseBody
    @GetMapping("/{version}/get")
    public CommonResult<NewsEntity> get(String id) {

        return CommonResult.success();
    }

}
```
-  swagger2 配置多版本
```java
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    public Docket v1Default(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("1-V1版本")
                .select()
                .apis(input -> {
                    ApiVersion apiVersion = input.getHandlerMethod().getMethodAnnotation(ApiVersion.class);
                    if(apiVersion!=null){
                        System.out.println("读取到版本信息："+apiVersion.value());
                    }
                    if(apiVersion!=null && Arrays.asList(apiVersion.value()).contains(1)){
                        return true;
                    }
                    // 方法所在的类是否标注
                    ApiVersion annotationOnClass = input.getHandlerMethod().getBeanType().getAnnotation(ApiVersion.class);
                    if (annotationOnClass != null) {
                        if (Arrays.asList(annotationOnClass.value()).contains(1)) {
                            return true;
                        }
                    }
                    return false;
                })//controller路径
                .paths(PathSelectors.any())
                .build();
    }
}
```

### api 入参校验

   [validation文档](xs-validation/README.md)
     
### redis的使用

```java
@Api(tags = "redis 缓存")
@Controller
@RequestMapping(value = "/redis",
        produces = "application/json; charset=UTF-8")
public class RedisController {
    @Resource(name = "jsonRedisClient")
    private JsonRedisClient jsonRedisClient;

    @ApiOperation(value = "获取redis数据")
    @ResponseBody
    @GetMapping("/myRedis")
    public CommonResult<String> myRedis(String type) {
        
        jsonRedisClient.set("key","value", new Long(24 * 60 * 60 * 60));
        jsonRedisClient.remove("key");
        jsonRedisClient.getValue("key",String.class);
        
        return CommonResult.success();
    }
}
```


### auth2 授权配置

application.yml中配置oauth2 授权路径 （默认为： /api/）
```
oauth:
  path:
    url: /api/
```

#### 接口请求header 参数

- Authorization：请求受保护接口必填

#### 基于角色的权限控制
- 首先在“ApplicationSecurityConfig”类中使用“@EnableGlobalMethodSecurity(prePostEnabled = true)”注解
@EnableGlobalMethodSecurity(prePostEnabled = true)

- 然后在权限控制的方法上添加角色权限
@Secured({RoleConst.ROLE_CLIENT, RoleConst.ROLE_ADMIN})
@PreAuthorize("hasAnyRole('ROLE_USER')")


####  授权接入说明

 [OAUTH2文档](xs-jwt-security/OAUTH2.md)
 
#### OAuth2 数据库表
https://github.com/spring-projects/spring-security-oauth/blob/main/spring-security-oauth2/src/test/resources/schema.sql

表结构说明：https://blog.csdn.net/qq_34997906/article/details/89609297


### dubbo 微服务集成

添加依赖
```xml
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-dubbo</artifactId>
            <version>1.5.0</version>
            <type>jar</type>
        </dependency>
```

#### dubbo 接口
```java
package com.xiushang.dubbo.service;

import com.xiushang.framework.log.CommonResult;

public interface OrderDubboService {
    CommonResult<String> getHelloWord();
}

```

#### dubbo 接口provider
```java
package com.xiushang.dubbo.service;


import com.xiushang.framework.log.CommonResult;
import org.apache.dubbo.config.annotation.DubboService;


@DubboService()
public class OrderDubboSericeImpl implements OrderDubboService {

    @Override
    public CommonResult<String> getHelloWord() {
        return CommonResult.success("hello world");
    }
}

```

#### dubbo 接口使用

```java
package com.xiushang.dubbo.controller;

import com.xiushang.dubbo.service.OrderDubboService;
import com.xiushang.framework.log.CommonResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/consumer")
public class ConsumerController {
    @DubboReference
    OrderDubboService orderDubboService;

    @GetMapping("getOrder")
    public CommonResult<String> getOrder() {
        return orderDubboService.getHelloWord();
    }
}

```

#### dubbo 配置相关
```
server:
  port: 7000
spring:
  application:
    name: order-consumer
# dubbo 相关配置
dubbo:
  application:
    # 应用名称
    name: order-provider
  scan:
  # 接口实现者（服务实现）包
   base-packages: com.xiushang.dubbo.service
  # 注册中心信息
  registry:
    address: zookeeper://192.168.29.12:2181
  protocol:
    # 协议名称
    name: dubbo
    # 协议端口
    port: 20880

```
    
### 约束


#### 接口命名
和Java命名规范一样，好的、统一的接口命名规范，不仅可以增强其可读性，而且还会减少很多不必要的口头/书面上的解释。

可结合【接口路径规范】、【版本控制规范】，外加具体接口命名(路径中可包含请求数据，如：id等)，建议具体接口命名也要规范些，可使用"驼峰命名法"按照实现接口的业务类型、业务场景等命名，有必要时可采取多级目录命名，但目录不宜过长，两级目录较为适宜。
    

一、前缀

    通用前缀api，如
    /api/xxxx
    
    预留作用域pubic 和 oauth
    
    指定作用域pubic,代表不需要登录、不需要权限的公共数据。
    私有重要数据，不要放在该域下面。
    /api/pubic/xxxx
    
    完整实例：
    /api/public/good/detail
    /api/作用域（选填）/模块名/功能名
    
    指定作用域oauth,代表需要授权的私密数据
    /api/oauth/user/all
二、接口路径规范

    作为接口路径，为了方便清晰的区分来自不同的模块，可以采用不同系统/模块名作为接口路径前缀。

    格式规范如下：

    支付模块  /pay/xx
    订单模块  /order/xx
 

三、版本控制规范

    为了便于后期接口的升级和维护，建议在接口路径中加入版本号，便于管理，实现接口多版本的可维护性。如果你细心留意过的话，你会发现好多框架对外提供的API接口中(如：Eureka)，都带有版本号的。如：接口路径中添加类似"v1"、"v2"等版本号。

    格式规范如下：
    
          /news/v1/xx
    
    更新版本后可以使用v2、v3等、依次递加。

### 官网
- www.xiushangsh.com
