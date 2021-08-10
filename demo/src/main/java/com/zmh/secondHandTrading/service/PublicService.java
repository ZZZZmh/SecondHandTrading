package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.pojo.Commodity;
import com.zmh.secondHandTrading.util.CommonPage;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zmh
 * @title: PublicService
 * @projectName demo
 * @description: TODO
 * @date 2021/8/6 15:17
 */

public interface PublicService {

    // 按名称模糊查找
    public CommonPage<Commodity> serachCommodityByName(String commodityName,String order,String clause,Integer pageStart,Integer pageSize);

    // 按标签模糊查找
    public CommonPage<Commodity> serachCommodityByLabel(String lable,String order,String clause,Integer pageStart, Integer pageSize);

    // 处理redis消息队列
    public void handleQueue(String commodityId,String orderId);
}
