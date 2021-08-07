package com.zmh.secondHandTrading.entity.model;/**
 * @title: CommodityAddModel
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/4 11:25
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 *@ClassName CommodityAddModel
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/4 11:25
 *@Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommodityAddModel {
    @Length(max = 6, min = 2,message = "商品名长度限制2~6字符")
    private String commodityName;
    private double price;
    private int commodityNumber;
    @Length(max = 50, min = 2,message = "简介长度限制2~50字符")
    private String Introduction;
    @Length(max = 40, min = 2,message = "地址长度限制2~40字符")
    private String address;
    @Length(max = 6, min = 2,message = "标签长度限制2~6字符")
    private String Label;
    @Length(max = 15, min = 2,message = "联系方式长度限制2~15字符")
    private String contact;
}
