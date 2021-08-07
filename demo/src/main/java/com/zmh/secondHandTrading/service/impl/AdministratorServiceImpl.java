package com.zmh.secondHandTrading.service.impl;/**
 * @title: AdministratorServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/6 15:37
 */

import com.zmh.secondHandTrading.mapper.CommodityMapper;
import com.zmh.secondHandTrading.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName AdministratorServiceImpl
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/6 15:37
 *@Version 1.0
 */
@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    CommodityMapper commodityMapper;

    @Override
    public int auditCommodity(String commodityId,int status) {
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("commodityId",commodityId);
        commodityMapper.updateCommodity(map);
        return 0;
    }
}
