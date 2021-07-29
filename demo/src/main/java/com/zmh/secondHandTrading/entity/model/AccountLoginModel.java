package com.zmh.secondHandTrading.entity.model;/**
 * @title: AccountLoginModel
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 15:12
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 *@ClassName AccountLoginModel
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/25 15:12
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountLoginModel {
    @Length(max = 16, min = 4,message = "账号长度限制4~16字符")
    private String account;
    @Length(max = 16, min = 4,message = "密码长度限制4~16字符")
    private String password;
}
