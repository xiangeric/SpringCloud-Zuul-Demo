package com.eric.zuul.demo.groovy

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants

import javax.servlet.http.HttpServletRequest

class GroovyFilter extends ZuulFilter{

    @Override
    String filterType() {
        return FilterConstants.PRE_TYPE
    }

    @Override
    int filterOrder() {
        return 10
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.currentContext.request as HttpServletRequest
        Iterator  headerIt = request.getHeaderNames().iterator();
        while (headerIt.hasNext()){
            String name = (String)headerIt.next()
            String value = request.getHeader(name);
            println("header: "+name+" : "+value );
        }
        println("This is Groovy Filter!")
        return null
    }
}
