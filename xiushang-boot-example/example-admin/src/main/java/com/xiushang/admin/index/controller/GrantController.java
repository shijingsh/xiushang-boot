package com.xiushang.admin.index.controller;

import com.xiushang.common.service.OauthClientDetailsService;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 自定义授权页和登录页
 */
@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
public class GrantController {
    @Autowired
   private OauthClientDetailsService oauthClientDetailsService;

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

    @RequestMapping("/oauthLogin")
    public String login() {
        return "login";
    }
}
