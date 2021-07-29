package com.zmh.secondHandTrading.service.impl;/**
 * @title: RegisterService
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 13:54
 */

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.zmh.secondHandTrading.entity.model.RegisterModel;
import com.zmh.secondHandTrading.mapper.RegisterMapper;
import com.zmh.secondHandTrading.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName RegisterService
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/25 13:54
 *@Version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    RegisterMapper registerMapper;

    @Override
    public int register(RegisterModel registerModel) {
        // 判断邮箱是否绑定
        if(registerMapper.translationEmail(registerModel.getEmail())>=1){
            return -1;
        }
        // 判断账号是否存在
        if(registerMapper.translationAccount(registerModel.getAccount())>=2){
            return -2;
        }
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        String user_id =String.valueOf(id);
        Map<String,Object> mapAccount = new HashMap<>();
        mapAccount.put("account",registerModel.getAccount());
        mapAccount.put("password",registerModel.getPassword());
        mapAccount.put("user_id",user_id);
        mapAccount.put("email",registerModel.getEmail());
        Map<String,Object> mapUserinfo = new HashMap<>();
        mapUserinfo.put("user_id",user_id);
        mapUserinfo.put("user_name",registerModel.getUser_name());
        // 注册账号
        int result = registerMapper.registerAccount(mapAccount);
        // 注册用户
        int result2 = registerMapper.registerUser(mapUserinfo);
        if(result == 1 && result2 == 1){
            return 1;
        }
        return 0;
    }
}
