package com.xiushang.security.metadatasource;


import com.google.common.collect.Sets;
import com.xiushang.config.JWTIgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

@Component
@Slf4j
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
   /* @Resource
    private MenuRightMapper menuRightMapper;*/
    @Autowired
    private JWTIgnoreUrlsConfig ignoreUrlsConfig;

    /**
     * 当前激活的配置文件
     */
    @Value("${spring.profiles.active}")
    private String env;

    AntPathRequestMatcher antPathMatcher = null;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        // 开发环境不启用权限验证
        // if (env.contains("dev")) {
        //     return null;
        // }

        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();

        // 检查是否为放行的请求
        if (checkIgnores(request)){
            return null;
        }

        // 获取用户ID
        /*Long userId = SecurityUtils.getLoginUserId();
        if (null != userId && userId != -1){
            // 获取系统所有权限
            List<MenuDTO> menuDTOS = menuRightMapper.getAllMenus();
            for (MenuDTO menu : menuDTOS) {
                antPathMatcher = new AntPathRequestMatcher(menu.getUrl(), menu.getMethod());
                if (antPathMatcher.matches(request) && menu.getRoles().size() > 0) {
                    List<Role> roles = menu.getRoles();
                    int size = roles.size();
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        values[i] = "ROLE_" + roles.get(i).getName();
                    }
                    return SecurityConfig.createList(values);
                }
            }
        }*/
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (null == httpMethod) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = Sets.newHashSet();
        ignores.addAll(ignoreUrlsConfig.getUrls());

        if (!ignores.isEmpty()) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }
}
