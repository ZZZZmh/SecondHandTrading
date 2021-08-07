package com.zmh.secondHandTrading.service;

/**
 * @author zmh
 * @title: AdministratorService
 * @projectName demo
 * @description: TODO
 * @date 2021/8/6 15:30
 */
public interface AdministratorService {
    // 审核商品
    public int auditCommodity(String commodityId,int status);
}
