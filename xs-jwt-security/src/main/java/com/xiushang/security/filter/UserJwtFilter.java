package com.xiushang.security.filter;

import com.xiushang.config.JWTIgnoreUrlsConfig;
import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.security.exception.ValidateException;
import com.xiushang.service.impl.GrantedAuthorityImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户认证
 * 废弃
 * spring security oauth2 认证流程大体也就分成两步，先认证客户端再认证用户，做多租户公司的项目就是把租户ID和用户名放到一个jwt
 *
 *         final Map<String, Object> additionalInfo = new HashMap<>();
 *
 *         additionalInfo.put("userId", "userId");
 *         additionalInfo.put("openId","openId");
 *         additionalInfo.put("tenantId","tenantId");
 *         // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
 *         //additionalInfo.put("authorities", user.getAuthorities());
 *
 *         ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
 */
// @Component
public class UserJwtFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserJwtFilter.class);

    @Autowired
    private JWTIgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private FilterConfig config;

    @Override
    public void destroy() {

    }
    /**
     * 解决oauth2认证跨域
     * @param req
     * @param resp
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;

        long start = System.currentTimeMillis();

        List<String> list = ignoreUrlsConfig.getUrls();

        //免登陆URL
        boolean action = false;
        for (String url : list) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        if(action){
            //无需登陆的URL
            chain.doFilter(request, response);
            return;
        }

        String headerToken = request.getHeader(SecurityConstants.USER_HEADER_STRING);
        if (headerToken == null || !headerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {

            authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("Token为空！"));
            return;
        }

        // parse the token.
        String user = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SIGNING_KEY).parseClaimsJws(headerToken.replace("Bearer ", "")).getBody();
            // token签发时间
            long issuedAt = claims.getIssuedAt().getTime();
            // 当前时间
            long currentTimeMillis = System.currentTimeMillis();
            // token过期时间
            long expirationTime = claims.getExpiration().getTime();
            // 1. 签发时间 < 当前时间 < (签发时间+((token过期时间-token签发时间)/2)) 不刷新token
            // 2. (签发时间+((token过期时间-token签发时间)/2)) < 当前时间 < token过期时间 刷新token并返回给前端
            // 3. tokne过期时间 < 当前时间 跳转登录，重新登录获取token
            // 验证token时间有效性
            if ((issuedAt + ((expirationTime - issuedAt) / 2)) < currentTimeMillis && currentTimeMillis < expirationTime) {

                // 重新生成token start
                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                // 设置签发时间
                calendar.setTime(new Date());
                // 设置过期时间
                calendar.add(Calendar.MINUTE, 5);// 5分钟
                Date time = calendar.getTime();
                String refreshToken = Jwts.builder()
                        .setSubject(claims.getSubject())
                        .setIssuedAt(now)//签发时间
                        .setExpiration(time)//过期时间
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                        .compact();
                // 重新生成token end

                // 主动刷新token，并返回给前端
                response.addHeader("refreshToken", refreshToken);
            }
            long end = System.currentTimeMillis();
            //logger.info("执行时间: {}", (end - start) + " 毫秒");
            user = claims.getSubject();
            if (user != null) {
                String[] split = user.split("-")[1].split(",");
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                for (int i=0; i < split.length; i++) {
                    authorities.add(new GrantedAuthorityImpl(split[i]));
                }
                // return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("Token已过期！"));
            return;
        } catch (UnsupportedJwtException e) {
            logger.error("Token格式错误: {} " + e);
            authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("Token格式错误！"));
            return;
        } catch (MalformedJwtException e) {
            logger.error("Token没有被正确构造: {} " + e);
            authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("Token没有被正确构造！"));
            return;
        } catch (SignatureException e) {
            logger.error("签名失败: {} " + e);
            authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("Token签名失败！"));
            return;
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("Token非法参数异常！"));
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }
}
