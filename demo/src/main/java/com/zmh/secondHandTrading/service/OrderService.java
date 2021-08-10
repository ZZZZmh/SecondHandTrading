package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.pojo.OrderForm;

import java.util.List;
import java.util.Map;

/**
 * @author zmh
 * @title: OrderService
 * @projectName demo
 * @description: TODO
 * @date 2021/8/10 10:36
 */
public interface OrderService {

    // 查询订单
    public List<OrderForm> select(Map map);

    // 添加订单
    public int add(String commodityId,String userid,int purchaseQuantity,String address,String seller);

    // 更新订单
    public int update(Map map);
}
