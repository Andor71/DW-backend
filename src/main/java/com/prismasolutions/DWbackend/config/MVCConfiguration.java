package com.prismasolutions.DWbackend.config;

import com.prismasolutions.DWbackend.interceptors.PeriodInterceptor;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.service.YearService;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@AllArgsConstructor
public class MVCConfiguration extends WebMvcConfigurerAdapter {

    private final YearService yearService;

    private final PeriodRepository periodRepository;
    private final Utility utility;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PeriodInterceptor(yearService,periodRepository, utility));
    }
}
