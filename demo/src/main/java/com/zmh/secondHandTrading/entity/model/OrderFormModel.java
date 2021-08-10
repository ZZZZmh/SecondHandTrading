package com.zmh.secondHandTrading.entity.model;/**
 * @title: OrderFormModel
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/8 16:01
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 *@ClassName OrderFormModel
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/8 16:01
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderFormModel {
    private String orderId;
    private String commodityId;
    // 卖家id
    private String seller;
    // 购买商品量
    private int purchaseQuantity;
    @Length(max = 40, min = 2,message = "地址长度限制2~40字符")
    private String address;
}
