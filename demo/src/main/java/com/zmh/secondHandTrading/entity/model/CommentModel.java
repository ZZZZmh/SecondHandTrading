package com.zmh.secondHandTrading.entity.model;/**
 * @title: CommentModel
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/11 11:56
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 *@ClassName CommentModel
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/11 11:56
 *@Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {
    private String id;
    private String orderId;
    private String userId;
    private String commodityId;
    @Length(max = 30, min = 2,message = "评论不能少于2字符且超过30字符")
    private String content;
    private String userName;
    private Date createTime;
    private int likenum;
    // 1-正常 2-违法
    private int state;
}
