package com.zmh.secondHandTrading.service.impl;/**
 * @title: PublicServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/6 19:53
 */

import com.github.pagehelper.PageInfo;
import com.zmh.secondHandTrading.entity.pojo.Commodity;
import com.zmh.secondHandTrading.mapper.CommodityMapper;
import com.zmh.secondHandTrading.service.PublicService;
import com.zmh.secondHandTrading.util.CommonPage;
import com.zmh.secondHandTrading.util.RedisThread;
import com.zmh.secondHandTrading.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName PublicServiceImpl
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/6 19:53
 *@Version 1.0
 */
@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public CommonPage<Commodity> serachCommodityByName(@RequestParam String commodityName, String order, String clause,Integer pageStart, Integer pageSize) {
        Map<String,Object> map = new HashMap();
        map.put("commodityName",commodityName);
        map.put("status",2);
        // 默认按金钱排序
        if(clause==null || clause.equals("")){
            map.put("clause","price");
        }else {
            map.put("clause",clause);
        }
        // 默认升序
        if(order==null || order.equals("")){
            map.put("order","ASC");
        }else {
            map.put("order",order);
        }
        // 默认分页第一页
        if(pageStart!=null && pageStart >=1 ){
            map.put("pageStart",pageStart-1);
        }else {
            map.put("pageStart",0);
        }
        // 默认页大小为5
        if(pageSize!=null && pageSize >=1 ){
            map.put("pageSize",pageSize);
        }else {
            map.put("pageSize",5);
        }
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityMapper.selectCommodity(map));
        return CommonPage.restPage(pageInfo);
    }

    @Override
    public CommonPage<Commodity> serachCommodityByLabel(@RequestParam String lable, String order,String clause,Integer pageStart, Integer pageSize) {
        Map<String,Object> map = new HashMap();
        map.put("lable",lable);
        map.put("status",2);
        // 默认按金钱排序
        if(clause==null || clause.equals("")){
            map.put("clause","price");
        }else {
            map.put("clause",clause);
        }
        // 默认升序
        if(order==null || order.equals("")){
            map.put("order","ASC");
        }else {
            map.put("order",order);
        }
        // 默认分页第一页
        if(pageStart!=null && pageStart >=1 ){
            map.put("pageStart",pageStart-1);
        }else {
            map.put("pageStart",0);
        }
        // 默认页大小为5
        if(pageSize!=null && pageSize >=1 ){
            map.put("pageSize",pageSize);
        }else {
            map.put("pageSize",5);
        }
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityMapper.selectCommodity(map));
        return CommonPage.restPage(pageInfo);
    }

    @Override
    @Async
    public void handleQueue(String commodityId,String orderId) {
        RedisThread redisThread = new RedisThread();
        redisThread.setUserService(userService);
        redisThread.setRedisUtil(redisUtil);
        redisThread.setCommodityId(commodityId);
        redisThread.setOrderId(orderId);
        Thread thread = new Thread(redisThread);
        thread.start();
    }

}
