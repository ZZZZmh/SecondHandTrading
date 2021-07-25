package com.zmh.secondHandTrading.controller;/**
 * @title: UserController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 15:52
 */

import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.entity.resp.UserInfoResp;
import com.zmh.secondHandTrading.service.impl.UserServiceImpl;
import com.zmh.secondHandTrading.util.CommonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName UserController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/25 15:52
 *@Version 1.0
 */
@RestController("")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/user/Tourist/userInfo")
    public CommonResult serachInfo(){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        System.out.println(account.getUserId());
        Userinfo userinfo = userService.userInformation(account.getUserId());
        // 打包处理封装信息返回前端
        UserInfoResp userInfoResp = new UserInfoResp();
        userInfoResp.setUserName(userinfo.getUserName());
        userInfoResp.setSignature(userinfo.getSignature());
        userInfoResp.setRealName(userinfo.getRealName());
        userInfoResp.setHeadPortrait(userinfo.getHeadPortrait());
        userInfoResp.setStudentId(userinfo.getStudentId());
        if(userinfo.getStatus()==1){
            userInfoResp.setStatus("正常用户");
        }else if(userinfo.getStatus()==2){
            userInfoResp.setStatus("封禁用户");
        }else {
            userInfoResp.setStatus("用户已注销");
        }
        userInfoResp.setCredibility("当前信誉指"+userinfo.getCredibility());
        return CommonResult.success(userInfoResp);
    }
}
