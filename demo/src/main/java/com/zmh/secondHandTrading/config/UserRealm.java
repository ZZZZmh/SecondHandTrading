package com.zmh.secondHandTrading.config;/**
 * @title: UserRealm
 * @projectName springBoot_shiro
 * @description: TODO
 * @author zmh
 * @date 2021/2/27 14:43
 */


import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.mapper.LoginMapper;
import com.zmh.secondHandTrading.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;


/**
 *@ClassName UserRealm
 *@Description TODO
 *@Author ASUS
 *@Date 2021/2/27 14:43
 *@Version 1.0
 */
public class UserRealm  extends AuthorizingRealm {

    @Autowired
    LoginMapper loginMapper;
    @Autowired
    UserServiceImpl userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=》授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到user对象
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        // 当前用户授权
        int power = account.getPower();
        if(power == 1){
            // 只有实名认证权限
            info.addStringPermission("user:Tourist");
            System.out.println("Tourist");
        }
        else if(power == 2){
            // 拥有实名认证权限
            info.addStringPermission("user:Tourist");
            info.addStringPermission("user:Certification");
            System.out.println("Certification");
        }
        else if(power ==3){
            // 管理员拥有所有权限
            info.addStringPermission("user:Tourist");
            info.addStringPermission("user:Certification");
            info.addStringPermission("user:Administrator");
            System.out.println("Administrator");
        }
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=》认证");
        //获取用户token
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        Account account = loginMapper.selectAccount(userToken.getUsername());
        // 用户名验证
        if(account == null){
            return null;
        }
        // 密码验证
        // shiro 安全框架负责验证  MD5密码加密  MD5言文加密
        // 第一个参数为返回当前用户（此处返回查询到的Account给doGetAuthorizationInfo授权）,放入subject中
        return new SimpleAuthenticationInfo(account,account.getPassword(),"");
    }
}
