package com.erp.studentInformationSystem.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public FilterRegistrationBean<UserContextFilter> registerUserContextFilter() {
        FilterRegistrationBean<UserContextFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new UserContextFilter());
        bean.setOrder(1); // very important: runs early
        return bean;
    }
}
