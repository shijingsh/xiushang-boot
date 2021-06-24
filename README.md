### summary

基于自己的业务系统，整理一套共用框架代码，方便以后集成和开发。
Based on their own business system, sort out a set of common framework code to facilitate future integration and development.

Mainly use technology stack：springboot+jpa+jwt

demo：https://github.com/shijingsh/xiushang-boot-example
### create db
   创建空数据库即可，依赖表结构，将会自动生成
   
    You can create an empty database. Depending on the table structure, it will be generated automatically

### about entity

jpa对于简单的业务非常方便，为了实现简约的代码风格。约定所有实体类不使用一对多
实体类尽量少于对象级联。

JPA is very convenient for simple business, in order to achieve simple code style. It is agreed that all entity classes do not use one to many
Entity classes should be less than object cascades.


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
            <version>1.0.0</version>
            <type>pom</type>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-entity</artifactId>
            <version>1.0.0</version>
            <type>jar</type>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-common</artifactId>
            <version>1.0.0</version>
            <type>jar</type>
        </dependency>
        <dependency>
            <groupId>com.github.shijingsh</groupId>
            <artifactId>xs-job</artifactId>
            <version>1.0.0</version>
            <type>jar</type>
        </dependency>
```

### todo

- JenkinsFile
- Subscribe to message
- pay
- api auth

### official website
- www.xiushangsh.com
