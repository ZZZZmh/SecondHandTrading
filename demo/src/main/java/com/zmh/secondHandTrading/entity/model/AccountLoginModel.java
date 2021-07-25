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
    private String account;
    private String password;
}
