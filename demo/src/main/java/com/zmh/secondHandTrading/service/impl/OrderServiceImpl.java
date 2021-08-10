package com.zmh.secondHandTrading.service.impl;/**
 * @title: OrderServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/10 10:53
 */

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.zmh.secondHandTrading.entity.pojo.OrderForm;
import com.zmh.secondHandTrading.mapper.OrderFormMapper;
import com.zmh.secondHandTrading.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@ClassName OrderServiceImpl
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/10 10:53
 *@Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderFormMapper orderFormMapper;

    @Override
    public List<OrderForm> select(Map map) {
        return orderFormMapper.selectOrderForm(map);
    }

    @Override
    public int add(String commodityId,String userid,int purchaseQuantity,String address,String seller) {
        Map<String,Object> map = new HashMap<>();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        map.put("orderId",id);
        map.put("commodityId",commodityId);
        map.put("buyer",userid);
        map.put("seller",seller);
        map.put("purchaseQuantity",purchaseQuantity);
        map.put("address",address);
        map.put("status",1);
        map.put("createTime",new Date());
        return orderFormMapper.addOrderForm(map);
    }

    @Override
    public int update(Map map) {
        return orderFormMapper.updateOrderForm(map);
    }
}
