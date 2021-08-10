package com.zmh.secondHandTrading.service.impl;/**
 * @title: UserServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:33
 */

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.zmh.secondHandTrading.entity.model.*;
import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.Commodity;
import com.zmh.secondHandTrading.entity.pojo.OrderForm;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.mapper.CommodityMapper;
import com.zmh.secondHandTrading.mapper.OrderFormMapper;
import com.zmh.secondHandTrading.mapper.UserMapper;
import com.zmh.secondHandTrading.service.UserService;
import com.zmh.secondHandTrading.util.CommonPage;
import com.zmh.secondHandTrading.util.ImageUtil;
import com.zmh.secondHandTrading.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@ClassName UserServiceImpl
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 14:33
 *@Version 1.0
 */
@Service("userServiceImpl")
@Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout = 30,rollbackFor=Exception.class)
public class UserServiceImpl  implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    OrderFormMapper orderFormMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Userinfo userInformation(String userId) {
        Userinfo user = userMapper.selectAllInformation(userId);
        user.toString();
        return user;
    }

    @Override
    public int userInforUpate(UpdateUserinfoModel model) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("user_name",model.getUserName());
        map.put("signature",model.getSignature());
        return userMapper.updateUserinfo(map);
    }

    @Override
    public int updateEmail(String email) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("email",email);
        return userMapper.updateAccount(map);
    }

    @Override
    public int updatePassword(String password) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("password",password);
        return userMapper.updateAccount(map);
    }

    @Override
    public int updateHead(String imagUrl) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("head_portrait",imagUrl);
        return userMapper.updateUserinfo(map);
    }

    @Override
    public int certification(CertificationModel model) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        // 权限为用户，则为已经认证过
        if(account.getPower()==2){
            return -1;
        }
        if(userMapper.serachIdcard(model.getIdCard())!=null){
            return -2;
        }
        Map<String,Object> map = new HashMap();
        map.put("id", account.getUserId());
        map.put("user_name",model.getRealName());
        map.put("id_card",model.getIdCard());
        map.put("real_name",model.getRealName());
        map.put("student_id",model.getStudentId());
        map.put("power",2);
        userMapper.updateAccount(map);
        return userMapper.updateUserinfo(map);
    }

    @Override
    public int logout() {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("id", account.getUserId());
        map.put("status",3);
        return userMapper.updateAccount(map);
    }

    @Override
    public int addCommodity(CommodityAddModel model) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long commodityId = snowflake.nextId();
        map.put("commodity_id",commodityId);
        map.put("user_id",account.getUserId());
        map.put("commodity_name",model.getCommodityName());
        map.put("price",model.getPrice());
        map.put("commodity_number",model.getCommodityNumber());
        map.put("Introduction",model.getIntroduction());
        map.put("address",model.getAddress());
        map.put("Label",model.getLabel());
        map.put("contact",model.getContact());
        return commodityMapper.addCommodity(map);
    }

    @Override
    public int updateCommodityImg(MultipartFile file,String commodityId){
        String url = ImageUtil.upload(file);
        Map<String,Object> map = new HashMap<>();
        map.put("url",url);
        map.put("commodityId",commodityId);
        return commodityMapper.updateCommodity(map);
    }

    @Override
    public CommonPage<Commodity> selectOwnCommodity(Integer pageNo, Integer pageSize) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("userId",account.getUserId());
        map.put("clause","create_time");
        map.put("order","DESC");
        if(pageNo!=null && pageNo >=1 ){
            map.put("pageStart",pageNo-1);
        }else {
            map.put("pageStart",0);
        }
        // 默认页大小为5
        if(pageSize!=null && pageSize >=1 ){
            map.put("pageSize",pageSize);
        }else {
            map.put("pageSize",5);
        }
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityMapper.selectCommodity(map));
        return CommonPage.restPage(pageInfo);
    }

    @Override
    public int updateOwnCommodity(UpdateCommodityModel model) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("commodityId",model.getCommodityId());
        // 组装
        if (model.getAddress()!=null && model.getAddress().isEmpty()==false){
            map.put("address",model.getAddress());
        }
        if (model.getCommodityName()!=null && model.getCommodityName().isEmpty()==false){
            map.put("commodityName",model.getCommodityName());
        }
        if (model.getPrice()>=0){
            map.put("price",model.getPrice());
        }
        if (model.getCommodityNumber()>=1){
            map.put("commodityNumber",model.getCommodityNumber());
        }
        if (model.getIntroduction()!=null && model.getIntroduction().isEmpty()==false){
            map.put("Introduction",model.getIntroduction());
        }
        if (model.getLabel()!=null && model.getLabel().isEmpty()==false){
            map.put("label",model.getLabel());
        }
        if (model.getContact()!=null && model.getContact().isEmpty()==false){
            map.put("contact",model.getContact());
        }
        return commodityMapper.updateCommodity(map);
    }

    @Override
    public int enterQueue(String commodityId,int purchaseQuantity,String address) {
        // key:商品id item:数量  value:商品数量
        // 商品信息是否已进redis
        if(!redisUtil.hHasKey(commodityId,"commodityNumber")){
            // 不存在则加入
            // 获取商品信息
            Map<String,Object> map = new HashMap();
            map.put("commodityId",commodityId);
            List<Commodity> list = commodityMapper.selectCommodity(map);
            if(list.isEmpty()){
                return -2;
            }
            // 添加
            redisUtil.hset(commodityId, "commodityNumber",list.get(0).getCommodityNumber());
            redisUtil.hset(commodityId, "commodityName",list.get(0).getCommodityName());
            redisUtil.hset(commodityId, "seller",list.get(0).getUserId());
            redisUtil.hset(commodityId, "price",list.get(0).getPrice());
            // 有效时间30分钟
            redisUtil.expire(commodityId,30*60);
        }
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        // 入队
        // 选择redis数据库
        Jedis jedis = new Jedis("****************", 6379);
        jedis.auth("**************");
        jedis.select(1);
        // 开启事务
        Transaction multi = jedis.multi();
        try {
            // 将用户排到对应的商品id的数组里面
            if(redisUtil.hHasKey(account.getUserId(),"commodityId")){
                // 用户已经下订单了
                return -1;
            }
            multi.lpush(commodityId,account.getUserId());
            // 执行事务
            multi.exec();
        }catch (Exception e){
            // 放弃事务
            multi.discard();
        }
        // 添加购买信息到redis
        redisUtil.hset(account.getUserId(),"commodityId",commodityId);
        redisUtil.hset(account.getUserId(),"purchaseQuantity",purchaseQuantity);
        redisUtil.hset(account.getUserId(),"address",address);
        redisUtil.hset(account.getUserId(),"result",0);
        // 购买信息有效时间(15分钟)
        redisUtil.expire(account.getUserId(),15*60);
        return 1;
    }

    @Override
    public int buyCommodity(String userid,String orderId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        String commodityId = (String) redisUtil.hget(userid,"commodityId");
        Integer purchaseQuantity = (Integer) redisUtil.hget(userid,"purchaseQuantity");
        // 被购买的商品id
        map.put("commodityId",commodityId);
        // 买家
        map.put("buyer",userid);
        map.put("seller",redisUtil.hget(commodityId,"seller"));
        // 从redis中查询剩余商品数量
        Integer number = (Integer) redisUtil.hget(commodityId,"commodityNumber");
        // 购买失败，商品已售尽
        if(number==0){
            // 更新商品状态
            map.put("status",4);
            commodityMapper.updateCommodity(map);
            // 清除异常订单
            redisUtil.hdel(userid,"commodityId");
            redisUtil.hdel(userid,"purchaseQuantity");
            redisUtil.hdel(userid,"address");
            redisUtil.hdel(userid,"result");
            return -3;
        }
        // 购买失败：购买数量大与商品数量
        if(number < purchaseQuantity){
            // 清除异常订单
            redisUtil.hdel(userid,"commodityId");
            redisUtil.hdel(userid,"purchaseQuantity");
            redisUtil.hdel(userid,"address");
            redisUtil.hdel(userid,"result");
            return -2;
        }
        // redis更新商品信息
        redisUtil.hset(commodityId, "commodityNumber", number-purchaseQuantity);
        // 商品数量减去购买数量
        map.put("commodityNumber",number-purchaseQuantity);
        // 更新商品数量
        if (commodityMapper.updateCommodity(map) == 1) {
            // 之前有订单
            if(orderId!=null && !orderId.equals("")){
                map.put("orderId",orderId);
                map.put("status", 2);
                if(orderFormMapper.updateOrderForm(map)==1){
                    // 清除在redis中的订单(数据库已生成标准订单，已经可在数据库中查询)
                    redisUtil.hdel(userid,"commodityId");
                    redisUtil.hdel(userid,"purchaseQuantity");
                    redisUtil.hdel(userid,"address");
                    redisUtil.hdel(userid,"result");
                    return  1;
                }else {
                    return -1;
                }
            }
            // 之前没有订单则生成
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            long id = snowflake.nextId();
            map.put("orderId", id);
            map.put("purchaseQuantity", purchaseQuantity);
            map.put("address", redisUtil.hget(userid, "address"));
            // 已付款
            map.put("status", 2);
            map.put("createTime", new Date());
            //  添加订单
            if(orderFormMapper.addOrderForm(map)==1){
                // 清除在redis中的订单(数据库已生成标准订单，已经可在数据库中查询)
                redisUtil.hdel(userid,"commodityId");
                redisUtil.hdel(userid,"purchaseQuantity");
                redisUtil.hdel(userid,"address");
                redisUtil.hdel(userid,"result");
                return 1;
            }else{
                return -1;
            }
        }
        return -1;
    }

}
