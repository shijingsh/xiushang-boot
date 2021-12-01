package com.xiushang.admin.news.controller;

import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.log.CommonResult;
import lombok.extern.slf4j.Slf4j;
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


    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    //@Autowired
    //private PlatformService platformService;

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("grant");
        view.addObject("clientId", authorizationRequest.getClientId());
        //PlatformDO platformDO = platformService.getById(Long.parseLong(authorizationRequest.getClientId()));
        view.addObject("clientName", "天和");

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

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是:" + targetUrl);
            redirectStrategy.sendRedirect(request, response, "/oauthLogin");
        }
        //如果访问的是接口资源
        return CommonResult.error(401, "访问的服务需要身份认证，请引导用户到登录页");
    }

    @RequestMapping("/oauthLogin")
    public String login() {
        return "/login.html";
    }
}
