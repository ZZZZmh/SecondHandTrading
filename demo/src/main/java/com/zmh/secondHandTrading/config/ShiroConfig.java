package com.zmh.secondHandTrading.config;/**
 * @title: ShiroConfig
 * @projectName springBoot_shiro
 * @description: TODO
 * @author zmh
 * @date 2021/2/27 14:35
 */

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *@ClassName ShiroConfig
 *@Description TODO
 *@Author ASUS
 *@Date 2021/2/27 14:35
 *@Version 1.0
 */
@Configuration
public class ShiroConfig {

    // ShiroFilterFactorBean :3
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        /*
            anon: 无需认证就能访问
            authc: 必须认证了才能访问
            user: 必须拥有 记住我 功能才能用
            perms：拥有某个资源的权限才能访问
            role：拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 如果没有指定权限，则拦截
        // 公共模块
        filterMap.put("/piblic/**","anon");
        // 游客模块
        filterMap.put("/user/tourist/**","perms[user:Tourist]");
        // 用户模块(实名认证)
        filterMap.put("/user/certification/**","perms[user:Certification]");
        //管理员模块
        filterMap.put("/administrator/**","perms[user:Administrator]");

        // 无认证则跳转到登入界面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 未经授权则跳转到错误界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");

        // 设置权限集
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    // DefaulitWebSecurityManager :2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") com.zmh.secondHandTrading.config.UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    // 自定义realm对象 :1
    @Bean
    public com.zmh.secondHandTrading.config.UserRealm userRealm(){
        return new com.zmh.secondHandTrading.config.UserRealm();
    }
}
