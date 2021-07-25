package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.model.AccountLoginModel;
import com.zmh.secondHandTrading.entity.pojo.Account;

/**
 * @author zmh
 * @title: LoginService
 * @projectName demo
 * @description: TODO
 * @date 2021/7/23 14:45
 */
public interface LoginService {
    public Account login(String account);
    public AccountLoginModel emailLogin(String email);
}
