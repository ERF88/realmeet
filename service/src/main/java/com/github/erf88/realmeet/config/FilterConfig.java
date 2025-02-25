package com.github.erf88.realmeet.config;

import com.github.erf88.realmeet.domain.repository.ClientRepository;
import com.github.erf88.realmeet.filter.VerifyApiKeyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<VerifyApiKeyFilter> verifyApiKeyFilter(ClientRepository clientRepository) {
        FilterRegistrationBean<VerifyApiKeyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new VerifyApiKeyFilter(clientRepository));
        filterRegistrationBean.addUrlPatterns("/rooms/*", "/allocations/*");
        return filterRegistrationBean;
    }
}
