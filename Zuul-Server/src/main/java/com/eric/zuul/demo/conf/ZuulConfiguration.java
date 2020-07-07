package com.eric.zuul.demo.conf;

import com.eric.zuul.demo.component.DynamicDBRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfiguration {

    @Bean
    public SimpleRouteLocator simpleRouteLocator(ServerProperties server,ZuulProperties zuulProperties){
        return new DynamicDBRouteLocator(server.getServlet().getContextPath(), zuulProperties);
    }
}
