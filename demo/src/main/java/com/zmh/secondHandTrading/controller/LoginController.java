package com.zmh.secondHandTrading.controller;/**
 * @title: LoginController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 15:23
 */

import com.zmh.secondHandTrading.myException.EmailException;
import com.zmh.secondHandTrading.util.CommonResult;
import com.zmh.secondHandTrading.util.EmailTool;
import com.zmh.secondHandTrading.myEnum.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 *@ClassName LoginController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 15:23
 *@Version 1.0
 */
@RestController
public class LoginController {
    @Autowired
    private EmailTool emailTool;

    // 注册
    @PostMapping("/public/register")
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
    public CommonResult tologin(@NotNull @RequestParam String username, @NotNull @RequestParam String password){
        //获取当前输入的用户
        Subject currentUser = SecurityUtils.getSubject();
        //封装用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //登录，没有异常就说明登录成功
        try {
            currentUser.login(token);
            return CommonResult.success("登入成功");
        } catch (UnknownAccountException e) {
            return CommonResult.failed(ResultCode.USER_NOT_EXISTENT,"账号错误");
        }catch (IncorrectCredentialsException e){
            return CommonResult.failed(ResultCode.FAILED,"密码错误");
        }
    }

    // 发送邮件
    @PostMapping("/public/sendEmail")
    public CommonResult sendEmail(HttpSession session,@NotNull @RequestParam @Email String emailAddress){
        try {
            emailTool.contextComplexLoad(session,emailAddress);
        }  catch (MessagingException e) {
            e.printStackTrace();
        }
        return CommonResult.success(ResultCode.SUCCESS,"发送成功");
    }


    // 邮箱验证
    @GetMapping("/public/verifyEmail")
    public CommonResult verifyEmail(HttpSession session,@NotNull @RequestParam String code){
        String encode = (String) session.getAttribute("emailCode");
        if(code.equals(encode)){
            return CommonResult.success(ResultCode.SUCCESS,"成功验证");
        }
        return CommonResult.success(ResultCode.FAILED,"验证失败");
    }

    // 错误页面
    @GetMapping("/public/noauth")
    public CommonResult noauth(){
        return CommonResult.failed(ResultCode.UNAUTHORIZED,"未经授权");
    }

    // 注销退出
    @GetMapping("/public/logout")
    public CommonResult logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return CommonResult.success("退出登入成功");
    }


    // 电话登入==（发送短信）
    public String sendTelephone(){
        return null;
    }

    // 电话验证
    public String verifyTelephone(){
        return null;
    }

}
