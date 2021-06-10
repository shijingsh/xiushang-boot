package com.xiushang.handler;//package com.xiushang.handler;
//
//import org.apache.log4j.Logger;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Auther: liukefu
// */
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//
//    public static final Logger LOG = Logger.getLogger(CustomAccessDeniedHandler.class);
//
//    @Override
//    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            LOG.warn("User: " + auth.getName() + " attempted to access the protected URL: " + httpServletRequest.getRequestURI());
//        }
//        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/accessDenied");
//    }
//}
