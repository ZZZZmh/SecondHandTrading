package com.zmh.secondHandTrading.entity.model;/**
 * @title: UpateUserinfoModel
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/26 14:20
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *@ClassName UpateUserinfoModel
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/26 14:20
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserinfoModel {
    @NotNull(message = "姓名不能为空")
    @Length(max = 6, min = 2,message = "姓名长度限制2~6字符")
    private String userName;
    @Length(max = 15,message = "个性签名长度限制15字符")
    private String signature;
}
