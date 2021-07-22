package com.zmh.secondHandTrading;/**
 * @title: Application
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/22 15:08
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *@ClassName Application
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/22 15:08
 *@Version 1.0
 */

@EnableTransactionManagement
@MapperScan("com.zmh.secondHandTrading.mapper")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
