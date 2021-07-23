package com.zmh.secondHandTrading.controller;/**
 * @title: testController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/22 15:47
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 *@ClassName testController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/22 15:47
 *@Version 1.0
 */
@RestController
public class TestController {

    @Autowired
    private DruidDataSource dataSource;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test/hello")
    public String hello(){
        return "hello";
    }


    @RequestMapping("/test/druid")
    public String test(){
        System.out.println(dataSource);
        System.out.println(dataSource.isEnable());
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "test";
    }

    @RequestMapping("/test/redis/add")
    public void testAdd(){
        redisTemplate.opsForGeo().add("cityGeoKey",new Point(116.40528,39.90498),"北京");
        redisTemplate.opsForGeo().add("cityGeoKey",new Point(121.48941,31.40527),"上海");
        redisTemplate.opsForGeo().add("cityGeoKey",new Point(113.88308,22.55329),"深圳");
    }

    @GetMapping("/administrator/test")
    public void String(){
        System.out.println("权限测试");
    }
}
