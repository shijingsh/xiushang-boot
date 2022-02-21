package com.xiushang.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiushang.security.AuthenticationBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * AuthenticationFilter that supports rest login(json login) and form login.
 *
 * @author chenhuanming
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // attempt Authentication when Content-Type is json
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            // use jackson to deserialize json
            UsernamePasswordAuthenticationToken authRequest = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                InputStream is = request.getInputStream();

                AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }

        } else {
            // If not,transmit it to UsernamePasswordAuthenticationFilter
            return super.attemptAuthentication(request, response);
        }
    }


}
