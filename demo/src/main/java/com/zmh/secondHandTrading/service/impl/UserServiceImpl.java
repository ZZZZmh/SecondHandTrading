package com.zmh.secondHandTrading.service.impl;/**
 * @title: UserServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:33
 */

import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.mapper.UserMapper;
import com.zmh.secondHandTrading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@ClassName UserServiceImpl
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 14:33
 *@Version 1.0
 */
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public Userinfo userInformation(String userId) {
        Userinfo user = userMapper.selectAllInformation(userId);
        return user;
    }
}
