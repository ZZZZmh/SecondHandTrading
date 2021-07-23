package com.zmh.secondHandTrading.service.impl;/**
 * @title: LoginServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:46
 */

import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.mapper.LoginMapper;
import com.zmh.secondHandTrading.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *@ClassName LoginServiceImpl
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 14:46
 *@Version 1.0
 */
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public Account login(String account) {
        return loginMapper.selectAccount(account);
    }
}
