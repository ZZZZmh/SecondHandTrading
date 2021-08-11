package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.model.CommentModel;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zmh
 * @title: CommentService
 * @projectName demo
 * @description: TODO
 * @date 2021/8/11 14:06
 */
public interface CommentService {


    // 查询某商品自己的评论
    public CommentModel queryOwnOne(String orderId);

    // 添加评论
    public void addcomment(CommentModel model, HttpSession session);

    // 上传评论图片
    public void updateCommentImg(String url,String commentId);

    // 点赞
    public int updateCommentLikenum(String commentId);

    // 取消点赞
    public void cancelLike(String commentId);

    // 查询商品所有评论
    public List<CommentModel> queryAllByCommodityId(String commodityId);

    // 删除评论
    public void deleteComment(String commentId);

}
