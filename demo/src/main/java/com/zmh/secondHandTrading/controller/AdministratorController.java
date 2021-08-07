package com.zmh.secondHandTrading.controller;/**
 * @title: AdministratorController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/6 15:45
 */


import com.zmh.secondHandTrading.service.impl.AdministratorServiceImpl;
import com.zmh.secondHandTrading.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 *@ClassName AdministratorController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/6 15:45
 *@Version 1.0
 */
@RestController
public class AdministratorController {

    @Autowired
    AdministratorServiceImpl administratorService;

    // 查看所有商品（状态）

    // 查看所有用户（状态）

    // 管理用户

    // 查看所有商品

    // 管理商品

    // 审核商品
    @GetMapping("/administrator/auditCommodity")
    public CommonResult auditCommodity(@RequestParam @NotNull String commodityId, @RequestParam @NotNull int status){
        return CommonResult.success(administratorService.auditCommodity(commodityId,status));
    }


}
