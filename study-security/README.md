# 集成Spring Security

### 内容参考 [江南一点雨的系列教程](https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI1NDY0MTkzNQ==&action=getalbum&album_id=1319828555819286528&scene=173)

- 可以修改接口名和username、password参数名

> - .loginProcessingUrl("/doLogin")
>- .usernameParameter("name")
>- .passwordParameter("passwd")

- defaultSuccessUrl:哪里跳登录，就调回哪里，直接登录就跳index
- successForwardUrl:无论哪里跳登录，都跳到index，跟defaultSuccessUrl二选一

````java
    @Override
protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login.html")
        .loginProcessingUrl("/doLogin")
        .usernameParameter("name")
        .passwordParameter("passwd")
        .defaultSuccessUrl("/index")
        .successForwardUrl("/index")
        .permitAll()
        .and()
        .csrf().disable();
        }
````

> - 注销登录的默认接口是 `/logout`，我们也可以配置

````java
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
        .logoutSuccessUrl("/index")
        .deleteCookies()
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .permitAll()
        .and()
````

> 1. 默认注销的 URL 是 /logout，是一个 GET 请求，我们可以通过 logoutUrl 方法来修改默认的注销 URL。
>1. logoutRequestMatcher 方法不仅可以修改注销 URL，还可以修改请求方式，实际项目中，这个方法和 logoutUrl 任意设置一个即可。
>1. logoutSuccessUrl 表示注销成功后要跳转的页面。
>1. deleteCookies 用来清除 cookie。
>1. clearAuthentication 和 invalidateHttpSession 分别表示清除认证信息和使 HttpSession 失效，默认可以不用配置，默认就会清除。

## 如何实现无状态

> - 首先客户端发送账户名/密码到服务端进行认证
>- 认证通过后，服务端将用户信息加密并且编码成一个 token，返回给客户端
>- 以后客户端每次发送请求，都需要携带认证的 token
>- 服务端对客户端发送来的 token 进行解密，判断是否有效，并且获取用户登录信息

> `successHandler` 的功能十分强大

````java
.successHandler((req,resp,authentication)->{
        Object principal=authentication.getPrincipal();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(principal));
        out.flush();
        out.close();
        })
````

> `successHandler` 方法的参数是一个 `AuthenticationSuccessHandler` 对象，这个对象中我们要实现的方法是 `onAuthenticationSuccess`。

> `onAuthenticationSuccess` 方法有三个参数，分别是：
>- HttpServletRequest
>- HttpServletResponse
>- Authentication

#### 登录失败类似同上

````java
.failureHandler((req,resp,e)->{
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        out.write(e.getMessage());
        out.flush();
        out.close();
        })
````

#### 未认证处理方案

````java
.csrf().disable().exceptionHandling()
        .authenticationEntryPoint((req,resp,authException)->{
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        out.write("尚未登录，请先登录");
        out.flush();
        out.close();
        }
        );
````

#### 注销登录

````java
.and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessHandler((req,resp,authentication)->{
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        out.write("注销成功");
        out.flush();
        out.close();
        })
        .permitAll()
        .and()
````

## 授权

> 所谓的授权，就是用户如果要访问某一个资源，我们要去检查用户是否具备这样的权限，如果具备就允许访问，如果不具备，则不允许访问。

### 测试用户

> 由于 Spring Security 支持多种数据源，例如内存、数据库、LDAP 等，这些不同来源的数据被共同封装成了一个 UserDetailService 接口，任何实现了该接口的对象都可以作为认证数据源。

> 因此我们还可以通过重写 `WebSecurityConfigurerAdapter` 中的 `userDetailsService` 方法来提供一个 `UserDetailService` 实例进而配置多个用户：

````java
@Override
@Bean
protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("christ").password("123").roles("admin").build());
        manager.createUser(User.withUsername("christli").password("123456").roles("user").build());
        return manager;
        }
````

### 将用户数据存入数据库

> `UserDetailsService` 的几个能直接使用的实现类中，除了`InMemoryUserDetailsManager`之外，还有一个`JdbcUserDetailsManager`，
> 使用 JdbcUserDetailsManager 可以让我们通过 JDBC 的方式将数据库和 Spring Security 连接起来。
> - JdbcUserDetailsManager 自己提供了一个数据库模型，这个数据库模型保存在如下位置：
    `org/springframework/security/core/userdetails/jdbc/users.ddl`

````java
@Autowired
DataSource dataSource;
@Override
@Bean
protected UserDetailsService userDetailsService(){
        JdbcUserDetailsManager manager=new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        if(!manager.userExists("christ01")){
        manager.createUser(User.withUsername("christ01").password("123").roles("admin").build());
        }
        if(!manager.userExists("christ02")){
        manager.createUser(User.withUsername("christ02").password("123").roles("user").build());
        }
        return manager;
        }
````

> - 注意，重写`configure(AuthenticationManagerBuilder auth)`方法，会覆盖上述的userDetailsService方法；

### 使用jpa，比较简单，参考 [Spring Security+Spring Data Jpa](https://mp.weixin.qq.com/s/VWJvINbi1DB3fF-Mcx7mGg)

> 注意: 实体生成的表默认是latin，不支持中文，修改一下两个表字段的Collation

````sql
alter table t_user
    convert to character set utf8;
alter table t_role
    convert to character set utf8;
````

### 角色继承

> 上级可能具备下级的所有权限，如果使用角色继承，这个功能就很好实现，我们只需要在 SecurityConfig 中添加如下代码来配置角色继承关系即可：

````java
@Bean
RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy=new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
        }
````

> 注意，在配置时，需要给角色手动加上 ROLE_ 前缀。上面的配置表示 ROLE_admin 自动具备 ROLE_user 的权限。

### 四种常见的权限控制方式

> 1. 表达式控制 URL 路径权限
> 1. 表达式控制方法权限
> 1. 使用过滤注解
> 1. 动态权限
> - 可以参考 [Spring Security 中的四种权限控制方式](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247489063&idx=1&sn=4102eeb3af9ec768ba6254cad6312283&chksm=e9c34447deb4cd517f4f680c04de7bd683cf9116fe11e396672168ca567b07f7d2ec242fe8ed&scene=178&cur_album_id=1319828555819286528#rd)

## 实现自动登录功能

````java
@Override
protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .and()
        .rememberMe()
        .key("christli")
        .and()
        .csrf().disable();
        }
````

> 这里只需要添加一个 `.rememberMe()` 即可，自动登录功能就成功添加进来了。
>
> 如果没有设置 key，key 默认值是一个 UUID 字符串，这样会带来一个问题，就是如果服务端重启，这个 key 会变，
> 这样就导致之前派发出去的所有 remember-me 自动登录令牌失效，所以，我们可以指定这个 key。例如 `.key("christli")`

> remember-me令牌的生成核心处理方法在：`TokenBasedRememberMeServices#onLoginSuccess：`，逻辑如何走到这里，参考如下
> - `AbstractAuthenticationProcessingFilter#doFilter` ->
> - `AbstractAuthenticationProcessingFilter#successfulAuthentication` ->
> - `AbstractRememberMeServices#loginSuccess` ->
> - `TokenBasedRememberMeServices#onLoginSuccess`

> remember-me认证流程,在`RememberMeAuthenticationFilter` 的 doFilter 方法实现

### 自动登录的安全风险控制

- 内容比较简单，可以参考 [Spring Boot 自动登录，安全风险要怎么控制？](https://mp.weixin.qq.com/s/T6_PBRzIADE71af3yoKB6g)

> - 降低安全风险两个方面:
>1. 持久化令牌方案,增加series，如果token不同就重新登录，避免有人登陆过其他平台
>1. 二次校验，使用了自动登录功能，只可以做一些常规的不敏感操作，做敏感操作是需要再次登录

````java
@Override
protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests()
        .antMatchers("/remember-me").rememberMe()
        .antMatchers("/admin").fullyAuthenticated()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .and()
        .rememberMe()
        .key("christli")
        .tokenRepository(jdbcTokenRepository())
        .and()
        .csrf().disable();
        }
````

> - /remember-me 接口是需要 rememberMe 才能访问。
> - /admin 是需要 fullyAuthenticated，fullyAuthenticated 不同于 authenticated，fullyAuthenticated 不包含自动登录的形式，而 authenticated 包含自动登录的形式。
> - 最后剩余的接口都是 authenticated 就能访问。

### 如何优雅添加验证码

> 1. 可以参考 [SpringSecurity 自定义认证逻辑的两种方式(高级玩法)](https://mp.weixin.qq.com/s/LeiwIJVevaU5C1Fn5nNEeg)
> 1. 可以参考 [Spring Security 中如何快速查看登录用户 IP 地址等信息？](https://mp.weixin.qq.com/s/pSX9XnPNQPyLWGc6oWR3hA)
> 1. 可以参考 [Spring Security 如何添加登录验证码？](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247487976&idx=1&sn=2556bc786f89e62bc7a52bc437eaab31&chksm=e9c34388deb4ca9e98c779845d4f02f78414857636d75e9b1c189bc8c9758147f661c2d7303a&scene=178&cur_album_id=1319828555819286528#rd)

#### 如何查看ip

````java

@Service
public class HelloService {
    public void hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println(details.getRemoteAddress());
        System.out.println(details.getSessionId());
    }
}
````

### 只允许一个登录

> - 可以参考 [Spring Security 自动踢掉前一个登录用户，一个配置搞定！](https://mp.weixin.qq.com/s/9f2e4Ua2_fxEd-S9Y7DDtA)
> - 可以参考 [Spring Boot + Vue 前后端分离项目，如何踢掉已登录用户？](https://mp.weixin.qq.com/s/nfqFDaLDH8UJVx7mqqgHmQ)

### 登录流程

> 在 Spring Security 中，认证与授权的相关校验都是在一系列的过滤器链中完成的，
> 在这一系列的过滤器链中，和认证相关的过滤器就是 `UsernamePasswordAuthenticationFilter`;
>
> `UsernamePasswordAuthenticationFilter` 的父类 `AbstractAuthenticationProcessingFilter` 中，
> 这个类我们经常会见到，因为很多时候当我们想要在 Spring Security 自定义一个登录验证码或者将登录参数改为 JSON 的时候，我们都需自定义过滤器继承自 `AbstractAuthenticationProcessingFilter` ，
> 毫无疑问，`UsernamePasswordAuthenticationFilter#attemptAuthentication` 方法就是在 `AbstractAuthenticationProcessingFilter` 类的 `doFilter` 方法中被触发的：
> - 可以参考 [捋一遍 Spring Security 登录流程](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488026&idx=2&sn=3bd96d91e822abf753a8e91142e036be)

> 1. SecurityContextPersistenceFilter 继承自 GenericFilterBean，而 GenericFilterBean 则是 Filter 的实现，所以 SecurityContextPersistenceFilter 作为一个过滤器，它里边最重要的方法就是 doFilter 了。
> 1. 在 doFilter 方法中，它首先会从 repo 中读取一个 SecurityContext 出来，这里的 repo 实际上就是 HttpSessionSecurityContextRepository，
     > 读取 SecurityContext 的操作会进入到 readSecurityContextFromSession 方法中， 核心方法 `Object contextFromSession = httpSession.getAttribute(springSecurityContextKey);`，
     > 这里的 springSecurityContextKey 对象的值就是 SPRING_SECURITY_CONTEXT，读取出来的对象最终会被转为一个 SecurityContext 对象。
> 1. SecurityContext 是一个接口，它有一个唯一的实现类 SecurityContextImpl，这个实现类其实就是用户信息在 session 中保存的 value。
> 1. 在拿到 SecurityContext 之后，通过 SecurityContextHolder.setContext 方法将这个 SecurityContext 设置到 ThreadLocal 中去，
     > 在当前请求中，Spring Security 的后续操作，我们都可以直接从 SecurityContextHolder 中获取到用户信息了。
> 1. 接下来，通过 chain.doFilter 让请求继续向下走（这个时候就会进入到 UsernamePasswordAuthenticationFilter 过滤器中了）。
> 1. 在过滤器链走完之后，数据响应给前端之后，finally 中还有一步收尾操作，这一步很关键。这里从 SecurityContextHolder 中获取到 SecurityContext，
     > 获取到之后，会把 SecurityContextHolder 清空，然后调用 repo.saveContext 方法将获取到的 SecurityContext 存入 session 中。

> 不想走 Spring Security 过滤器链，我们一般可以通过如下方式配置：

````java
@Override
public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico","/verifyCode");
        }
````

> 正常这样配置是没有问题的。 但是，不能把登录请求地址放进来了。
> 虽然登录请求可以被所有人访问，但是不能放在这里（而应该通过允许匿名访问的方式来给请求放行）。
> 如果放在这里，登录请求将不走 `SecurityContextPersistenceFilter` 过滤器，也就意味着不会将登录用户信息存入 session，进而导致后续请求无法获取到登录用户信息。

### 使用redis共享session

> 直接配置redis的依赖就行，`SecurityConfig`，配置一下`sessionRegistry`

````java

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    FindByIndexNameSessionRepository sessionRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest()
                ...
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry());
    }

    @Bean
    SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }
}
````

> - 可以参考 [集群化部署，Spring Security 要如何处理 session 共享？](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488621&idx=1&sn=cb2f66b79fe3779f8d6f7ede91e37605&chksm=e9c3460ddeb4cf1bbb3d0ef40f9fae1bc4ce7833200b82819a33f8a380d190990629faa586d6&scene=178&cur_album_id=1319828555819286528#rd)

### 防御CSRF攻击

> - 可以参考 [松哥手把手教你在 SpringBoot 中防御 CSRF 攻击！](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488656&idx=2&sn=f00c9c9d51caf76caa76a813961ba38a&chksm=e9c346f0deb4cfe67ba94962a156f3c3820a0fc58e6b9e8ad815e7fd2cab3575c4720d6e2ca3&scene=178&cur_album_id=1319828555819286528#rd)
> - 可以参考 [Spring Security 中 CSRF 防御源码解析](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488680&idx=1&sn=dbadb73a552619aa42d10f4ac13ece6d&chksm=e9c346c8deb4cfde09b9e7967090e032b4a3c79e0c2cbc52dba3e2598b80c20d4e4a3183bc7e&scene=178&cur_album_id=1319828555819286528#rd)

### 密码

> - 可以自定义一个 PasswordEncoder：

````java

@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
    }
}
````

> 也可以使用`BCryptPasswordEncoder` 加密.
> 创建 BCryptPasswordEncoder 时传入的参数 10 就是 strength，即密钥的迭代次数（也可以不配置，默认为 10）。

````java
@Bean
PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
        }

````

> 一般情况下，用户信息是存储在数据库中的，因此需要在用户注册时对密码进行加密处理，

````java

@Service
public class RegService {
    public int reg(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String encodePassword = encoder.encode(password);
        return saveToDb(username, encodePassword);
    }
}
````

> PasswordEncoder 中的 encode 方法，是我们在用户注册的时候手动调用。

> matches 方法，则是由系统调用，默认是在 DaoAuthenticationProvider#additionalAuthenticationChecks 方法中调用的。

> - 具体代码逻辑，可以参考 [Spring Security 多种加密方案共存，老破旧系统整合利器！](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247489069&idx=1&sn=c3120b71d8fd46748c4b6f85bb6dc738&chksm=e9c3444ddeb4cd5b2d73933b37e1418c8ae8097e786c70ae8c97f24eb553a9aa3393ae1dc635&scene=178&cur_album_id=1319828555819286528#rd)

### 统一登录，SpringBoot + CAS

> - 可以参考 [松哥手把手教你入门 Spring Boot + CAS 单点登录](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488872&idx=1&sn=3ac483e2e4b58b9940e1aa5458baddd8&chksm=e9c34708deb4ce1eab17c6b9a43d8058558708890a7cfaa053b7effd7f593dd112290d4fed34&scene=178&cur_album_id=1319828555819286528#rd)
> - 可以参考 [Spring Boot 实现单点登录的第三种方案！](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488913&idx=1&sn=605b35708ddf3b0e6e32a170cd1aea57&chksm=e9c347f1deb4cee795228ba6eb56c928b826e2ff1356f182b6dce2a14c2c0cb209d0a3936b98&scene=178&cur_album_id=1319828555819286528#rd)
> - 可以参考 [Spring Boot+CAS 单点登录，如何对接数据库？](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488924&idx=2&sn=6b0d567181dd4d6b52c56894071ced1e&chksm=e9c347fcdeb4ceea60b873522c43626b2eb1669fe3eed3850e8c25b0f3a89a9f516b5a3e465e&scene=178&cur_album_id=1319828555819286528#rd)
> - 可以参考 [Spring Boot+CAS 默认登录页面太丑了，怎么办？](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488952&idx=2&sn=f5a16f45ef22ee28f37e41d08e6fecd5&chksm=e9c347d8deb4cecedc190b5476e35750e270754c978b818895923f9c69670ac01157d4b2181f&scene=178&cur_album_id=1319828555819286528#rd)

### OAuth2,让swagger自动带上Token

> - 可以参考 [用 Swagger 测试接口，怎么在请求头中携带 Token？](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488969&idx=1&sn=97edbfe4621c3b65d9b6af27158cb88c&chksm=e9c347a9deb4cebf2151774685bc9f913b0e04fdfeb772c82028d97feaa8689a1353c39dbb7f&scene=178&cur_album_id=1319828555819286528#rd)

### 解决跨域问题

> - 可以参考 [Spring Boot 中三种跨域场景总结](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488989&idx=1&sn=00881de1a77c2e4027ee948164644485&chksm=e9c347bddeb4ceabd4d50d0fc5e5f28e9d3fe5fbdd19a81207c3a42cd3b0202cdb5fc35aa5e9&scene=178&cur_album_id=1319828555819286528#rd)

### 异常处理

> Spring Security 中的异常可以分为两大类，认证异常`AuthenticationException` 和 授权异常`AccessDeniedException`。
> - 可以参考 [一文搞定 Spring Security 异常处理机制！](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247489179&idx=1&sn=01aae04306638e68d9ea483e508d56ac&chksm=e9c344fbdeb4cded021f3e125f39cb4f5fb25ca5e903a621dbb5bd76264c76a1b90bbc7e6756&scene=178&cur_album_id=1319828555819286528#rd)

### 过滤链

> - Spring Security 中的 Filter 我们可以称之为 Security Filter ,
> - Spring Security Filter 并不是直接嵌入到 Web Filter 中的，而是通过 `FilterChainProxy` 来统一管理 Spring Security Filter，
> - `FilterChainProxy` 本身则通过 Spring 提供的 `DelegatingFilterProxy` 代理过滤器嵌入到 Web Filter 之中。
> - 可以参考 [Spring Security 竟然可以同时存在多个过滤器链？](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247489302&idx=2&sn=032f8ccc6a9955799702d6c2766ca6eb&chksm=e9c34576deb4cc60d1787dbd82ab13ca86665f9a93626004bd35aa3a24b7b5fb34c2e73cf88b&scene=178&cur_album_id=1319828555819286528#rd)
> - 可以参考 [深入理解 FilterChainProxy【源码篇】](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247489390&idx=2&sn=c42833e530d4d258a236a07deb922229&chksm=e9c3450edeb4cc18ae5e8f3309019112dd08dab8db5d11fc110dff62b554720d1f38eecffcda&scene=178&cur_album_id=1319828555819286528#rd)