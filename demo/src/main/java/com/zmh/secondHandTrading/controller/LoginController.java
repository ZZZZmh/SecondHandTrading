package com.zmh.secondHandTrading.controller;/**
 * @title: LoginController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 15:23
 */

import com.zmh.secondHandTrading.entity.model.AccountLoginModel;
import com.zmh.secondHandTrading.entity.model.RegisterModel;
import com.zmh.secondHandTrading.service.LoginService;
import com.zmh.secondHandTrading.service.RegisterService;
import com.zmh.secondHandTrading.util.CommonResult;
import com.zmh.secondHandTrading.util.EmailTool;
import com.zmh.secondHandTrading.myEnum.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;

    // 注册
    @PostMapping("/public/register")
    public CommonResult CommonResult(@RequestBody RegisterModel registerModel){
        if(registerService.register(registerModel)== -1){
            return CommonResult.failed(ResultCode.FAILED,"邮箱已绑定");
        }
        if(registerService.register(registerModel)== -2){
            return CommonResult.failed(ResultCode.FAILED,"账号已存在");
        }
        else if(registerService.register(registerModel)== 1){
            return CommonResult.success("注册成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"注册失败");
    }


    // 模拟登入页面
    @GetMapping("/public/Login")
    public String login(){
        return "假装是一个登入页面";
    }

    // 账号密码登入
    @PostMapping("/public/tologin")
    public CommonResult tologin(@RequestBody AccountLoginModel accountLoginModel){
        //获取当前输入的用户
        Subject currentUser = SecurityUtils.getSubject();
        //封装用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(accountLoginModel.getAccount(),accountLoginModel.getPassword());
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

    //邮箱登入
    @PostMapping("/public/emailLogin")
    public CommonResult emailLogin(@NotNull @RequestParam @Email String emailAddress){
        AccountLoginModel model = loginService.emailLogin(emailAddress);
        //获取当前输入的用户
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(model.getAccount(),model.getPassword());
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
    @PostMapping("/public/verifyEmail")
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
        return CommonResult.failed(ResultCode.UNAUTHORIZED,"权限不足");
    }

    // 注销退出
    @GetMapping("/public/logout")
    public CommonResult logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return CommonResult.success("退出登入");
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
