package com.eric.zuul.demo.component;

import com.eric.zuul.demo.entity.ZuulRouteEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicDBRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    @Autowired
    private PropertiesDao propertiesDao;

    public DynamicDBRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        List<ZuulRouteEntity> properties = propertiesDao.getProperties();
        if(properties!=null){
            Map<String, ZuulProperties.ZuulRoute> routesMap = new HashMap<>(properties.size());
            properties.forEach(zuulRouteEntity -> {
                ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
                BeanUtils.copyProperties(zuulRouteEntity,zuulRoute);
                routesMap.put(zuulRoute.getPath(),zuulRoute);
            });
            return routesMap;
        }
        return Collections.EMPTY_MAP;
    }
}
