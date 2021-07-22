package com.zmh.secondHandTrading.config;/**
 * @title: DruidDataSourceConfig
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/22 15:58
 */

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName DruidDataSourceConfig
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/22 15:58
 *@Version 1.0
 */
@Configuration
public class DruidDataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource dataSource(){
        return new DruidDataSource();
    }

    //后台监控 (http://localhost:8080/druid/ 即可进入druid监控页面)
    //springboot 内置了servlet容器，所以没有web.xml，替代方法：ServletRegistrationBean
    @Bean
    public ServletRegistrationBean statViewServlet(){
        //一般路径都为：/druid/*
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //后台需要有人登入，账号密码配置
        HashMap<String,String> initParameters = new HashMap<>();
        //增加配置
        initParameters.put("loginUsername","root");//登入key  键值对 loginUsername loginPassword是肯定的键值对，不能改
        initParameters.put("loginPassword","root");
        //允许谁能访问
        initParameters.put("allow","");
        bean.setInitParameters(initParameters);
        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        //可以过滤那些请求呢？
        Map<String,String> initParameters = new HashMap<>();
        //这些东西不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
