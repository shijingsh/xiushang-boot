### 简介
 Xiushang boot .

 基础库为API接口项目开发，完成API通用功能：用户模块、Api授权、短信、定时任务、上传文件、文档管理、微信相关接口。
 主要技术栈：springboot+jpa+jwt
 
  demo：https://github.com/shijingsh/xiushang-boot-example
  
### 数据库脚本
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

### 实体类

jpa对于简单的业务非常方便，为了实现简约的代码风格。约定所有实体类不使用一对多
实体类尽量少于对象级联。


if you found error: Q***Entity is not found
please run mvn clean and than mvn install

```
mvn clean 
mvn install
```

### usage

Select the library you need to install

安装你需要的依赖
```xml
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xiushang-boot</artifactId>
            <version>1.3.1</version>
            <type>pom</type>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-entity</artifactId>
            <version>1.3.1</version>
            <type>jar</type>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-common</artifactId>
            <version>1.3.1</version>
            <type>jar</type>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-job</artifactId>
            <version>1.3.1</version>
            <type>jar</type>
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

### todo

- pay
- api auth

### 官网
- www.xiushangsh.com
