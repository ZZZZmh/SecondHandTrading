package com.zmh.secondHandTrading.service.impl;/**
 * @title: UserServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:33
 */

import com.zmh.secondHandTrading.entity.model.CertificationModel;
import com.zmh.secondHandTrading.entity.model.UpdateUserinfoModel;
import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.mapper.UserMapper;
import com.zmh.secondHandTrading.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public int userInforUpate(UpdateUserinfoModel model) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("user_name",model.getUserName());
        map.put("signature",model.getSignature());
        return userMapper.updateUserInfo(map);
    }

    @Override
    public int updateEmail(String email) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("email",email);
        return userMapper.updateEmail(map);
    }

    @Override
    public int updatePassword(String password) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("password",password);
        return userMapper.updatePassword(map);
    }

    @Override
    public int updateHead(String imagUrl) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("head_portrait",imagUrl);
        return userMapper.updateHead(map);
    }

    @Override
    public int certification(CertificationModel model) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        // 权限为用户，则为已经认证过
        if(account.getPower()==2){
            return -1;
        }
        if(userMapper.serachIdcard(model.getIdCard())!=null){
            return -2;
        }
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("user_name",model.getRealName());
        map.put("id_card",model.getIdCard());
        map.put("real_name",model.getStudentId());
        map.put("student_id",model.getStudentId());
        map.put("power",2);
        userMapper.updatePermissions(map);
        return userMapper.updateCertification(map);
    }

    @Override
    public int logout() {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        return userMapper.logout(account.getUserId());
    }
}
