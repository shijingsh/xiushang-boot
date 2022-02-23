package com.xiushang.admin.index.controller;

import com.xiushang.common.service.OauthClientDetailsService;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.framework.log.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
public class GrantController {
    @Autowired
   private OauthClientDetailsService oauthClientDetailsService;


    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("grant");

        String clientId = authorizationRequest.getClientId();
        OauthClientDetailsEntity oauthClientDetailsEntity = oauthClientDetailsService.findByClientId(clientId);

        view.addObject("clientId", clientId);
        view.addObject("client", oauthClientDetailsEntity);

        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<>();
        if (scopes != null) {
            scopeList.addAll(scopes.keySet());
        }
        view.addObject("scopeList", scopeList);

        return view;
    }

    /**
     * 当用户没登录的时候，会经过这个请求，在这个请求中可以处理一些逻辑
     *
     * @param request  request
     * @param response response
     * @return ResultModel
     * @throws IOException IOException
     */
    @RequestMapping("/authentication/require")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public CommonResult<String> requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
            isAjaxRequest = true;
        }

        if (!isAjaxRequest) {
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if(savedRequest != null){
                String targetUrl = savedRequest.getRedirectUrl();
                log.info("引发跳转的请求是:" + targetUrl);
            }

            redirectStrategy.sendRedirect(request, response, "/oauthLogin");
        }
        //如果访问的是接口资源
        return CommonResult.error(401, "访问的服务需要身份认证，请引导用户到登录页");
    }

    @RequestMapping("/oauthLogin")
    public String login() {
        return "login";
    }
}
