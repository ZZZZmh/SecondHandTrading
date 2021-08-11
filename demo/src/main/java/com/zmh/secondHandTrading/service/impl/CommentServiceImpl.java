package com.zmh.secondHandTrading.service.impl;/**
 * @title: CommentServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/11 14:08
 */

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.zmh.secondHandTrading.entity.model.CommentModel;
import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.resp.UserInfoResp;
import com.zmh.secondHandTrading.service.CommentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;


/**
 *@ClassName CommentServiceImpl
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/11 14:08
 *@Version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public CommentModel queryOwnOne(String orderId) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Query query = new Query(Criteria.where("userId").is(account.getUserId()).and("orderId").is(orderId));
        CommentModel comment = mongoTemplate.findOne(query,CommentModel.class,"comment");
        return comment;
    }

    @Override
    public void addcomment(CommentModel model, HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        UserInfoResp userinfo = (UserInfoResp) session.getAttribute("userInfo");
        long id = snowflake.nextId();
        String commentId =String.valueOf(id);
        model.setId(commentId);
        model.setUserId(account.getUserId());
        model.setUserName(userinfo.getUserName());
        model.setCreateTime(new Date(System.currentTimeMillis()));
        model.setLikenum(0);
        model.setState(1);
        mongoTemplate.save(model,"comment");
    }

    @Override
    public void updateCommentImg(String url,String commentId) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Query query=Query.query(Criteria.where("_id").is(commentId).and("userId").is(account.getUserId()));
        Update update=new Update();
        update.addToSet("img",url);
        mongoTemplate.updateFirst(query,update,"comment");
    }

    @Override
    public int updateCommentLikenum(String commentId) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        // 查询是否已经点赞过
        Query query1=Query.query(Criteria.where("_id").is(commentId).and("likeId").is(account.getUserId()));
        if(mongoTemplate.findOne(query1,CommentModel.class,"comment")!=null){
            return -1;
        }
        Query query2=Query.query(Criteria.where("_id").is(commentId));
        Update update=new Update();
        update.inc("likenum");
        update.addToSet("likeId",account.getUserId());
        mongoTemplate.updateFirst(query2,update,"comment");
        return 1;
    }

    @Override
    public void cancelLike(String commentId) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Update update=new Update();
        // 更新点赞数
        update.set("likenum",mongoTemplate.findById(commentId,CommentModel.class,"comment").getLikenum()-1);
        // 取消用户点赞
        update.pull("likeId",account.getUserId());
        Query query=Query.query(Criteria.where("_id").is(commentId));
        mongoTemplate.updateFirst(query,update,"comment");
    }

    @Override
    public List<CommentModel> queryAllByCommodityId(String commodityId) {
        Query query = new Query(Criteria.where("commodityId").is(commodityId));
        return mongoTemplate.find(query,CommentModel.class,"comment");
    }

    @Override
    public void deleteComment(String commentId) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Query query = new Query(Criteria.where("_id").is(commentId).and("userId").is(account.getUserId()));
        mongoTemplate.remove(query,CommentModel.class,"comment");
    }
}
