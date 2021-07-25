package com.zmh.secondHandTrading.entity.resp;/**
 * @title: UserInfoResp
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 16:39
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 *@ClassName UserInfoResp
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/25 16:39
 *@Version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoResp {
    private String userName;
    private String signature;
    private String realName;
    private String headPortrait;
    private String studentId;
    private String status;
    private String credibility;
}
