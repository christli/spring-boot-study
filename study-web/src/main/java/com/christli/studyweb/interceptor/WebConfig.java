package com.christli.studyweb.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * 可定义多个拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 定义过滤拦截的url名称，拦截/api/user/users
        registry.addInterceptor(new MyHttpInterceptor()).addPathPatterns("/api/user/users");
//		registry.addInterceptor(其他拦截器).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}