package com.zmh.secondHandTrading.controller;/**
 * @title: LoginController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 15:23
 */

import com.zmh.secondHandTrading.until.CommonResult;
import com.zmh.secondHandTrading.until.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName LoginController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 15:23
 *@Version 1.0
 */
@RestController
public class LoginController {

    // 注册
    public String register(){
        return null;
    }


    // 模拟登入页面
    @GetMapping("/public/Login")
    public String login(){
        return "假装是一个登入页面";
    }

    // 账号密码登入
    @PostMapping("/public/tologin")
    public CommonResult tologin(String username, String password){
        //获取当前输入的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //登录，没有异常就说明登录成功
        try {
            subject.login(token);
            return CommonResult.success("登入成功");
        } catch (UnknownAccountException e) {
            return CommonResult.failed(ResultCode.FAILED,"账号错误");
        }catch (IncorrectCredentialsException e){
            return CommonResult.failed(ResultCode.FAILED,"密码错误");
        }
    }

    // 电话登入（发送短信）
    public String telephoneLogin(){
        return null;
    }

    // 邮箱登入（发送邮件）
    public String emailLogin(){
        return null;
    }

    // 错误页面
    @GetMapping("/public/noauth")
    public CommonResult noauth(){
        return CommonResult.failed(ResultCode.UNAUTHORIZED,"未经授权");
    }

}
