package com.fhswar.configuration;

import com.fhswar.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 加了 @Configuration 注解的类起到提到 xml 配置文件的作用
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        // new 出豆子，进行配置
        FilterRegistrationBean filterFilterRegistrationBean = new FilterRegistrationBean();
        // 对谁进行配置呢，对 UserFilter 进行配置
        filterFilterRegistrationBean.setFilter(new UserFilter());
        // 指定 UserFilter 对哪些请求进行过滤
        filterFilterRegistrationBean.addUrlPatterns("/cart/*");
        return filterFilterRegistrationBean;
    }
}
