package com.zmh.secondHandTrading.entity.pojo;/**
 * @title: OrderForm
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/10 11:07
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 *@ClassName OrderForm
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/10 11:07
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderForm {
    private String orderId;
    private String commodityId;
    private String buyer;
    private String seller;
    private Integer purchaseQuantity;
    private String address;
    private int status;
    private Date createTime;
    private Date endTime;
}
