package com.zmh.secondHandTrading.entity.model;/**
 * @title: RegisterRequest
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 13:21
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 *@ClassName RegisterRequest
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/25 13:21
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterModel {
    @NotNull
    @Length(max = 16, min = 4,message = "账号长度限制4~16字符")
    private String account;
    @Length(max = 16, min = 4,message = "密码长度限制4~16字符")
    private String password;
    @NotNull
    @Length(max = 6, min = 2,message = "姓名长度限制2~6字符")
    private String user_name;
    @Email
    private String email;
}
