package com.zmh.secondHandTrading.entity.pojo;
/**
 * @title: Login
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:40
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 *@ClassName Login
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 14:40
 *@Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    private String account;
    private String password;
    private String userId;
    private String email;
    private String telephone;
    private int credibility;
    private int power;
}
