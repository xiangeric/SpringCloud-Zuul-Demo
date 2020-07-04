package com.eric.zuul.demo.groovy;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GroovyRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        MonitoringHelper.initMocks();
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        try{
            //设置过滤器，只扫描.groovy 文件
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            //每隔20秒扫描c:/improve目录
            FilterFileManager.init(20,"C:/improve");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
