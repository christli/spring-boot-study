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
>1. 可以参考 [Spring Security 中如何快速查看登录用户 IP 地址等信息？](https://mp.weixin.qq.com/s/pSX9XnPNQPyLWGc6oWR3hA)

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
 