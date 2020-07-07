package com.eric.zuul.demo.component;

import com.eric.zuul.demo.entity.ZuulRouteEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.*;

public class DynamicDBRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    @Autowired
    private PropertiesDao propertiesDao;

    @Autowired
    private ZuulProperties properties;


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
                if(StringUtils.isNotBlank(zuulRouteEntity.getZuulSensitiveHeaders())){
                    String[] headers = zuulRouteEntity.getZuulSensitiveHeaders().split(",");
                    Set<String> headerSets = new HashSet<>(headers.length);
                    for(String header:headers){
                        headerSets.add(header);
                    }
                    zuulRoute.setSensitiveHeaders(headerSets);
                }
                String path = zuulRoute.getPath();
                if(StringUtils.isNotBlank(this.properties.getPrefix())){
                    path = this.properties.getPrefix() + path;
                }
                routesMap.put(path,zuulRoute);
            });
            return routesMap;
        }
        return Collections.EMPTY_MAP;
    }
}
