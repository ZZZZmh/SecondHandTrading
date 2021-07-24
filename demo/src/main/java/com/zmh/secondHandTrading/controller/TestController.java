package com.zmh.secondHandTrading.controller;/**
 * @title: testController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/22 15:47
 */

import com.alibaba.druid.pool.DruidDataSource;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zmh.secondHandTrading.util.ImageUtil;
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
    public void testAdministrator(){
        System.out.println("权限测试");
    }

    @GetMapping("/administrator/image")
    public void testimage(){
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = "h0-z4g_5rx2d0yoRZ7itl8LduRW5nAmaByzxrbyE";      //AccessKey的值
        String secretKey = "****************************************";      //SecretKey的值
        String bucket = "zmh-s-h-t";                                  //存储空间名
        String localFilePath = "C:\\Users\\ASUS\\Desktop\\91281061_p0.png";       //上传图片路径

        String key = "test.png";                                               //在七牛云中图片的命名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
