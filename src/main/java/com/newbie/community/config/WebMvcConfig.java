package com.newbie.community.config;

import com.newbie.community.controller.interceptor.AutoLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration

public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AutoLoginInterceptor autoLoginInterceptor;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoLoginInterceptor).addPathPatterns("/**");
    }
}
