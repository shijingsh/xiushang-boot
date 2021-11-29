### todo list

auth2账号登录 应该是存储在session 之中，解决集群问题 
auth2登录 使用fiter 怎么生成swagger文档
token 过期处理逻辑
auth2 获取授权的账号

### oauth2 文章
AuthorizationEndpoint  oauth/authorize路径处理器

TokenEndpoint   /oauth/token

https://blog.csdn.net/czh8487888/article/details/111578200  Spring Security Oauth2关于自定义登录的几种解决方案
https://zhuanlan.zhihu.com/p/166420642  我扒了半天源码，终于找到了Oauth2自定义处理结果的最佳方案！

https://www.cnblogs.com/javammc/p/14219006.html


https://cloud.tencent.com/developer/article/1377637  自定义AuthorizationEndpoint

### oauth2 表结构
找到了一篇《spring-security-oauth2的mysql数据表》，网址如下：

https://blog.csdn.net/qq_27384769/article/details/79440449

这里有相关表字段的详细说明：

http://andaily.com/spring-oauth-server/db_table_description.html

###两层授权
因为需要首先租户授权，再进行用户授权。
因此分为两层授权，实现思路如下：

- 第一次认证租户认证（手机短信验证码模式）生成会话信息返给tokenA给客户端
- 第二次拿到tokenA+用户密码进行用户认证（类似验证码模式）获取tokenB （其中拿tokenA判断是否有会话信息，tokenB包含租户和用户信息）

###核心源码解读
https://blog.csdn.net/weixin_42271016/article/details/104212326
https://www.bianchengquan.com/article/566420.html

TokenEndpoint#postAccessToken(...) 主入口
```
OAuth2AccessToken token = 
getTokenGranter().grant(tokenRequest.getGrantType(), 
tokenRequest);
```


CompositeTokenGranter#grant(String grantType,TokenRequest tokenRequest ) 负责从所有的TokenGranter中根据授权类型找到具体的TokenGranter

```
public class CompositeTokenGranter implements TokenGranter {
    private final List<TokenGranter> tokenGranters;
 ...
 public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
  for (TokenGranter granter : tokenGranters) {
   OAuth2AccessToken grant = granter.grant(grantType, tokenRequest);
   if (grant!=null) {
    return grant;
   }
  }
  return null;
 }
 ...
}
```


那么这里的tokenGranters又是从哪来的呢？答案是oauth2认证服务器端点配置类 AuthorizationServerEndpointsConfigurer
```
public final class AuthorizationServerEndpointsConfigurer {
 ...
 private TokenGranter tokenGranter;
 public TokenGranter getTokenGranter() {
  return tokenGranter();
 }

 //默认的四种授权模式+Refresh令牌模式
 private List<TokenGranter> getDefaultTokenGranters() {
  ClientDetailsService clientDetails = clientDetailsService();
  AuthorizationServerTokenServices tokenServices = tokenServices();
  AuthorizationCodeServices authorizationCodeServices = authorizationCodeServices();
  OAuth2RequestFactory requestFactory = requestFactory();

  List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
  tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails,
    requestFactory));
  tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
  ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
  tokenGranters.add(implicit);
  tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
  if (authenticationManager != null) {
   tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
     clientDetails, requestFactory));
  }
  return tokenGranters;
 }

 private TokenGranter tokenGranter() {
  if (tokenGranter == null) {
   tokenGranter = new TokenGranter() {
    private CompositeTokenGranter delegate;

    @Override
    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
     if (delegate == null) {
      delegate = new CompositeTokenGranter(getDefaultTokenGranters());
     }
     return delegate.grant(grantType, tokenRequest);
    }
   };
  }
  return tokenGranter;
 }
 ...
}
```
可以看到Spring已经把把默认的四种授权模式+刷新令牌的模式的配置在代码中写死了，那么如何让Spring能识别我们自定义的授权模式呢？


我们可以通过配置类覆盖TokenGranter，在里面注入我们自定义的授权模式！

ProviderManager#authenticate(Authentication authentication)这个类是提供了认证的实现逻辑和流程，他负责从所有的AuthenticationProvider中找出具体的Provider进行认证

```
public class ProviderManager implements AuthenticationManager, MessageSourceAware,
  InitializingBean {
 ...
 public Authentication authenticate(Authentication authentication)
   throws AuthenticationException {
  Class<? extends Authentication> toTest = authentication.getClass();
  AuthenticationException lastException = null;
  AuthenticationException parentException = null;
  Authentication result = null;
  Authentication parentResult = null;
  boolean debug = logger.isDebugEnabled();
  //遍历所有的providers使用supports方法判断该provider是否支持当前的认证类型
  for (AuthenticationProvider provider : getProviders()) {
   if (!provider.supports(toTest)) {
    continue;
   }

   try {
   //找到具体的provider进行认证
    result = provider.authenticate(authentication);
    if (result != null) {
     copyDetails(authentication, result);
     break;
    }
   }
   catch (AccountStatusException | InternalAuthenticationServiceException e) {
    prepareException(e, authentication);
    throw e;
   } catch (AuthenticationException e) {
    lastException = e;
   }
  }
  throw lastException;
 }
 ...
}
```
