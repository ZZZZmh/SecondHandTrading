package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.pojo.Userinfo;

/**
 * @author zmh
 * @title: UserService
 * @projectName demo
 * @description: TODO
 * @date 2021/7/23 14:27
 */
public interface UserService {

    // 查看用户所有信息
    public Userinfo userInformation(String userId);
}
