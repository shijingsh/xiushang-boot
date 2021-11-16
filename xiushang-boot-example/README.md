### 简介
 Xiushang boot 使用实例.

 基础库为API接口项目开发，完成API通用功能：用户模块、Api授权、短信、定时任务、上传文件、文档管理、微信相关接口。
 主要技术栈：springboot+jpa+jwt

 链接：https://github.com/shijingsh/xiushang-boot
 
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

### 预览

```
Home: 	http://192.168.15.153:8787/
Login: 	http://192.168.15.153:8787/login
Doc: 	http://192.168.15.153:8787/doc.html
```

### todo

- wx pay、alipay
- api auth
- ads

### 官网
- www.xiushangsh.com
