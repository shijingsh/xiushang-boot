package com.xiushang.handler;//package com.xiushang.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @Auther: liukefu
// */
//public class RestAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    private final ObjectMapper mapper;
//
//    @Autowired
//    public RestAuthenticationSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
//        this.mapper = messageConverter.getObjectMapper();
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//        response.setStatus(HttpServletResponse.SC_OK);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        PrintWriter writer = response.getWriter();
//        mapper.writeValue(writer, userDetails);
//        writer.flush();
//        writer.close();
//    }
//}
