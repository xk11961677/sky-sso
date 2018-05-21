package com.zyk.sso.client;

import com.zyk.sso.client.common.util.ZykGsonHttpMessageConverter;
import com.zyk.sso.client.filter.SsoClientFilter;
import com.zyk.sso.client.filter.SsoClientSignOutFilter;
import com.zyk.sso.client.listener.SingleSignOutHttpSessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAutoConfiguration
public class SsoClientApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SsoClientApplication.class);
    }

    @Bean
    public FilterRegistrationBean SsoClientFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SsoClientFilter());
        registrationBean.addUrlPatterns("/resource/*");
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean SsoClientListner() {
        final ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        SingleSignOutHttpSessionListener listener = new SingleSignOutHttpSessionListener();
        registrationBean.setListener(listener);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean signOutFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SsoClientSignOutFilter signOutFilter = new SsoClientSignOutFilter();
        registrationBean.setFilter(signOutFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClientApplication.class, args);
    }

    @Bean
    public ZykGsonHttpMessageConverter zykGsonHttpMessageConverter() {
        return new ZykGsonHttpMessageConverter();
    }



}
