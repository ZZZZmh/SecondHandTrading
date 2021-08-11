package com.zmh.secondHandTrading.util;/**
 * @title: RedisThread
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/8 20:12
 */

import com.zmh.secondHandTrading.service.impl.UserServiceImpl;
import redis.clients.jedis.Jedis;


import java.util.List;

/**
 *@ClassName RedisThread
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/8 20:12
 *@Version 1.0
 */

public class RedisThread implements  Runnable{

    private Jedis jedis;
    private String commodityId;
    private List<String> lists;
    private int result;
    private String userid;
    private UserServiceImpl userService;
    private RedisUtil redisUtil;
    private String orderId;

    public RedisThread(){
        jedis = new Jedis("**************", 6379);
        jedis.auth("********************");
        jedis.select(1);
    }

    public void setCommodityId(String commodityId){
        this.commodityId = commodityId;
    }

    public void setUserService(UserServiceImpl userService){
        this.userService = userService;
    }

    public void setRedisUtil(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }


    @Override
    public void run() {
        System.out.println("进入线程");
        while(true) {
            // 阻塞式brpop，List中无数据时阻塞
            // brpop(int timeout, String key)
            // 2分钟无数据就断开链接
            try {
                lists =  jedis.brpop(120,commodityId);
            }catch (Exception e){
                return;
            }
            try {
                // 更新redis用户的购买信息
                // 获取商品消息队列信息
                // [1424371257062854656, 1]
                userid = lists.get(1);
                result = userService.buyCommodity(userid,orderId);
                redisUtil.hset(userid,"result",result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        jedis.close();
    }
}
