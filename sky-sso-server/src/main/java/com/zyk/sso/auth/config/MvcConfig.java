//package com.zyk.sso.auth.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * @author sky
// * @description
// * @create 2018-03-21 下午7:54
// **/
//@Configuration
//public class MvcConfig extends WebMvcConfigurerAdapter {
//
//    @Bean
//    public LoginInterceptor loginInterceptor() {
//        return new LoginInterceptor();
//    }
//
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(new String[]{"/","/error","/user/**"});
//    }
//}
