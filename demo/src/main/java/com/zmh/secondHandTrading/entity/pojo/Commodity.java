package com.zmh.secondHandTrading.entity.pojo;/**
 * @title: Commodity
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/4 10:55
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 *@ClassName Commodity
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/4 10:55
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Commodity {
    private String commodityId;
    private String userId;
    private String commodityName;
    private double price;
    private String image;
    private String commodityNumber;
    private String Introduction;
    private String address;
    private String Label;
    private String contact;
    private int version;
    private Date createTime;
}
