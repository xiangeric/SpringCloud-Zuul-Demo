package com.eric.zuul.demo.component;

import com.eric.zuul.demo.entity.ZuulRouteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertiesDao {

    @Autowired
    private JdbcTemplate template;

    private final static String SELECT_ZUUL_ROUTES_PROPERTIES = "SELECT * from zuul_route ";

    public List<ZuulRouteEntity> getProperties(){
        List<ZuulRouteEntity> properties = template.query(SELECT_ZUUL_ROUTES_PROPERTIES,
                new BeanPropertyRowMapper<>(ZuulRouteEntity.class));
        return properties;
    }
}
