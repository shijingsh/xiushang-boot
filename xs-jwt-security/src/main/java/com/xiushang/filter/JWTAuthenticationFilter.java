package com.xiushang.filter;

import com.xiushang.exception.TokenException;
import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.service.impl.GrantedAuthorityImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 自定义JWT认证过滤器
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token == null || token.isEmpty()) {
            throw new TokenException("Token为空");
        }
        // parse the token.
        String user = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SIGNING_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody();
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
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            throw new TokenException("Token已过期");
        } catch (UnsupportedJwtException e) {
            logger.error("Token格式错误: {} " + e);
            throw new TokenException("Token格式错误");
        } catch (MalformedJwtException e) {
            logger.error("Token没有被正确构造: {} " + e);
            throw new TokenException("Token没有被正确构造");
        } catch (SignatureException e) {
            logger.error("签名失败: {} " + e);
            throw new TokenException("签名失败");
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            throw new TokenException("非法参数异常");
        }
        return null;
    }

}
