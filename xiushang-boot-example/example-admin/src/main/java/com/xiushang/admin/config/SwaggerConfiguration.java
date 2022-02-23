
package com.xiushang.admin.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.config.ApiVersion;
import com.xiushang.config.JWTIgnoreUrlsConfig;
import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.security.config.OAuth2UrlConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Autowired
    private JWTIgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private OAuth2UrlConfig oAuth2UrlConfig;

    @Bean
    public Docket defaultApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("1-全部接口")
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.xiushang.cart.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                //.globalOperationParameters(pars)
                ;
    }

    @Bean
    public Docket commonDefault(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("2-公共接口")
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.xiushang.common"))//controller路径
                .apis(RequestHandlerSelectors.withMethodAnnotation(XiushangApi.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket v1Default(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("3-V1版本")
                .select()
                .apis(input -> {
                    ApiVersion apiVersion = input.getHandlerMethod().getMethodAnnotation(ApiVersion.class);
                    if(apiVersion!=null){
                        //System.out.println("读取到版本信息："+apiVersion.value());
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
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket v2Default(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("4-V2版本")
                .select()
                .apis(input -> {
                    ApiVersion apiVersion = input.getHandlerMethod().getMethodAnnotation(ApiVersion.class);

                    if(apiVersion!=null){
                       //System.out.println("读取到版本信息："+apiVersion.value());
                    }
                    if(apiVersion!=null && Arrays.asList(apiVersion.value()).contains(2)){
                        return true;
                    }

                    // 方法所在的类是否标注
                    ApiVersion annotationOnClass = input.getHandlerMethod().getBeanType().getAnnotation(ApiVersion.class);
                    if (annotationOnClass != null) {
                        if (Arrays.asList(annotationOnClass.value()).contains(2)) {
                            return true;
                        }
                    }

                    return false;
                })//controller路径
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket web_api_prdt() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("公告管理")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/news/**"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo groupApiInfo(){
        return new ApiInfoBuilder()
                .title("秀上文档")
                .description("秀上 RESTful APIs")
                .termsOfServiceUrl("https://www.xiushangsh.com/")
                .contact(new Contact("秀上", "https://www.xiushangsh.com", "liukefu2020@sina.com"))
                .version("1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER.toString());
        return Collections.singletonList(apiKey);
    }

    private List<SecurityContext> securityContexts() {

        List<String> list = Arrays.asList("/oauth/token","/login","/logout","/captcha","/verifyCode","/register","/common","/public");
        //jwt 免登录的URL
        List<String> urls = ignoreUrlsConfig.getUrls();
        urls.addAll(list);
        StringBuilder builder = new StringBuilder();
        //"^((?!/captcha|/*/login/*|/abc).)*$";
        builder.append("^((?!");

        for(String url:urls){
            url = url.replaceAll("/\\*\\*","/\\*");
            url = url.replaceAll("\\*\\*","\\*");
            //url = url.replaceAll("/\\*\\*","");
            if("/".equals(url) || "/*".equals(url) || "*".equals(url)){
                continue;
            } else if(url.indexOf(".")!= -1){
                //过滤文件
                continue;
            }

            builder.append(url).append("|");
        }
        builder.deleteCharAt(builder.toString().length()-1);
        builder.append(").)*$");
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex(builder.toString()))
            .build());

        //设置需要授权认证的路径  非公共路径，都需要授权
        result.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(".*"+oAuth2UrlConfig.getUrl()+".*"))
                .build());

        return result;
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference(SecurityConstants.AUTH_HEADER_STRING, authorizationScopes));
        return result;
    }


    public static void main(String args[]){

        // ^((?!不想包含的字符串).)*$
        //^((?!666|zzz|abc).)*$
        String input = "/aaaa";
        String test = "^((?!/captcha|/*/login/*|/abc).)*$";

        //test = "^((?!/hede).)*$";
        System.out.println(input+":"+  input.matches(test));

        input = "/captcha";
        System.out.println(input+":"+  input.matches(test));


        input = "/captcha/aa";
        System.out.println(input+":"+  input.matches(test));

        input = "/login";
        System.out.println(input+":"+  input.matches(test));

        input = "/login/aa";
        System.out.println(input+":"+  input.matches(test));

        input = "/api/login/aa";
        System.out.println(input+":"+  input.matches(test));

    }

}
