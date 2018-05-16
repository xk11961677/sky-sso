package com.zyk.sso.auth;

import com.zyk.sso.auth.common.util.ZykGsonHttpMessageConverter;
import com.zyk.sso.auth.filter.SsoServerFilter;
import com.zyk.sso.auth.util.RedisUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAutoConfiguration
public class SsoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SsoApplication.class);
    }

    @Bean
    public FilterRegistrationBean jwtFilter(RedisUtils redisUtils) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SsoServerFilter(redisUtils));
        registrationBean.addUrlPatterns("/sso/*");
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

    @Bean
    public ZykGsonHttpMessageConverter zykGsonHttpMessageConverter() {
        return new ZykGsonHttpMessageConverter();
    }

}
