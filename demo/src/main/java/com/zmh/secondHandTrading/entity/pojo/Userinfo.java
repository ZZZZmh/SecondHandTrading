package com.zmh.secondHandTrading.entity.pojo;/**
 * @title: User
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:05
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 *@ClassName User
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 14:05
 *@Version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Userinfo {
    private String userId;
    private String userName;
    private String signature;
    private String realName;
    private String headPortrait;
    private String idCard;
    private String studentId;
    private Date create_time;
    private Date Logout_time;
}
