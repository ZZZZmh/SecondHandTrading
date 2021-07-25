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

import javax.validation.constraints.Email;

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
    private String account;
    private String password;
    private String user_name;
    @Email
    private String email;
}
