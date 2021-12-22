package com.xiushang.config;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();

    //Pattern.compile("\\{(.*?)\\}");  表达式与swagger 中/v2/api-docs接口冲突，因此此处写死{version}
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("\\{version\\}");
    @Override
    protected RequestMappingInfo createRequestMappingInfo(
            RequestMapping requestMapping, RequestCondition<?> customCondition) {

        // 如果Controller的方法上RequestMapping没有指定Method，则只支持GET和POST
        /*RequestMethod[] methods = { RequestMethod.GET, RequestMethod.POST };
        if(requestMapping.method().length != 0){
            methods = requestMapping.method();
        }*/

        String[] path = requestMapping.path();
        boolean hasVersion = false;
        for (int i=0;i<path.length;i++){
            Matcher m = VERSION_PREFIX_PATTERN.matcher(path[i]);
            if(m.find()){
                hasVersion = true;
                //System.out.println("woo: " + m.group());
                //System.out.println("ReplaceAll:" + m.replaceAll("v1"));
                if(customCondition==null){
                    //默认v1版本
                    path[i] = m.replaceAll("v1");
                }else  if(customCondition instanceof ApiVersionCondition){
                    ApiVersionCondition apiVersionCondition = (ApiVersionCondition)customCondition;
                    path[i] = m.replaceAll("v"+apiVersionCondition.getApiVersion());
                }
            }
        }
        if(hasVersion){
            return RequestMappingInfo
                    .paths(resolveEmbeddedValuesInPatterns(path))
                    .methods(requestMapping.method())
                    .params(requestMapping.params())
                    .headers(requestMapping.headers())
                    .consumes(requestMapping.consumes())
                    .produces(requestMapping.produces())
                    .mappingName(requestMapping.name())
                    .customCondition(customCondition)
                    .options(this.config)
                    .build();
        }

        return super.createRequestMappingInfo(requestMapping,customCondition);
    }

	@Override
    protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
    }

    public static void main(String args[]){
        Matcher m = VERSION_PREFIX_PATTERN.matcher("/{version}/test1");
        if(m.find()){
            System.out.println("woo: " + m.group());

            System.out.println("ReplaceAll:" + m.replaceAll("v1"));

        }
        m.reset();
        // 匹配方式
        Pattern p = Pattern.compile("\\{(.*?)\\}");
        // 匹配】
        Matcher matcher = p.matcher("/{version}/test1");
        // 处理匹配到的值
        while (matcher.find()) {
            System.out.println("woo: " + matcher.group());
        }

        String s = "$v1";
         m = VERSION_PREFIX_PATTERN.matcher(s);
        if(m.find()){
            System.out.println("$v1: " + m.group());
        }
    }
}
