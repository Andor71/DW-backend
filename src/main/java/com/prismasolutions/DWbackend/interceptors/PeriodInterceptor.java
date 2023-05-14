package com.prismasolutions.DWbackend.interceptors;

import com.prismasolutions.DWbackend.annotations.PeriodDateDependent;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.service.YearService;
import com.prismasolutions.DWbackend.util.Utility;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class PeriodInterceptor extends HandlerInterceptorAdapter {

    private final YearService yearService;
    private final PeriodRepository periodRepository;
    private final Utility utility;

    public PeriodInterceptor(YearService yearService, PeriodRepository periodRepository, Utility utility) {
        this.yearService = yearService;
        this.periodRepository = periodRepository;
        this.utility = utility;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        Date now = new Date();

        boolean accepted = true;

        if (!(handler instanceof HandlerMethod)){
            return accepted;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(PeriodDateDependent.class) != null) {
            accepted = utility.requestAccepted(handlerMethod.getMethodAnnotation(PeriodDateDependent.class).key());
        }

        if(!accepted){
            response.setStatus(412);
        }
        return accepted;
    }

}
