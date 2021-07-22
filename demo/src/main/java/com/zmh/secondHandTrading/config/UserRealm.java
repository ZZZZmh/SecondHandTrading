package com.zmh.secondHandTrading.config;/**
 * @title: UserRealm
 * @projectName springBoot_shiro
 * @description: TODO
 * @author zmh
 * @date 2021/2/27 14:43
 */


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 *@ClassName UserRealm
 *@Description TODO
 *@Author ASUS
 *@Date 2021/2/27 14:43
 *@Version 1.0
 */
public class UserRealm  extends AuthorizingRealm {

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=》授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=》认证");
        return new SimpleAuthenticationInfo();

    }
}
