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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    @Override
    public CommonPage<Commodity> serachCommodityByName(String commodityName, String order,String clause) {
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
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityMapper.selectCommodity(map));
        return CommonPage.restPage(pageInfo);
    }

    @Override
    public CommonPage<Commodity> serachCommodityByLabel(String lable, String order,String clause) {
        Map<String,Object> map = new HashMap();
        map.put("lable",lable);
        map.put("order",order);
        map.put("status",2);
        map.put("clause","price");
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityMapper.selectCommodity(map));
        return CommonPage.restPage(pageInfo);
    }

}
