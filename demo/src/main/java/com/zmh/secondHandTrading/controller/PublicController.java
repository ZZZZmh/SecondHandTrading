package com.zmh.secondHandTrading.controller;/**
 * @title: PublicController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/4 17:51
 */

import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.entity.resp.UserInfoResp;
import com.zmh.secondHandTrading.mapper.CommodityMapper;
import com.zmh.secondHandTrading.service.impl.PublicServiceImpl;
import com.zmh.secondHandTrading.util.CommonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName PublicController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/4 17:51
 *@Version 1.0
 */

@RestController
public class PublicController {

    @Autowired
    PublicServiceImpl publicService;

    // 按名称查找商品(默认价格升序)
    @GetMapping("/public/select/commodity/byName")
    public CommonResult serachCommodityByName(@RequestParam @NotNull String commodityName,String order,String clause){
        return CommonResult.success(publicService.serachCommodityByName(commodityName, order,clause));
    }

    // 按分类查找商品(默认价格升序)
    @GetMapping("/public/select/commodity/byLabel")
    public CommonResult serachCommodityByLabel(@RequestParam @NotNull String label,String order,String clause){
        return CommonResult.success(publicService.serachCommodityByLabel(label, order,clause));
    }


}
