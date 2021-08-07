package com.zmh.secondHandTrading.service.impl;/**
 * @title: UserServiceImpl
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:33
 */

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmh.secondHandTrading.entity.model.CertificationModel;
import com.zmh.secondHandTrading.entity.model.CommodityAddModel;
import com.zmh.secondHandTrading.entity.model.UpdateCommodityModel;
import com.zmh.secondHandTrading.entity.model.UpdateUserinfoModel;
import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.Commodity;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.mapper.CommodityMapper;
import com.zmh.secondHandTrading.mapper.UserMapper;
import com.zmh.secondHandTrading.service.UserService;
import com.zmh.secondHandTrading.util.CommonPage;
import com.zmh.secondHandTrading.util.ImageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout = 30)
public class UserServiceImpl  implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommodityMapper commodityMapper;

    @Override
    public Userinfo userInformation(String userId) {
        Userinfo user = userMapper.selectAllInformation(userId);
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
        map.put("real_name",model.getStudentId());
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
    public int updateCommodityImg(MultipartFile file,String commodityId) {
        String url = ImageUtil.upload(file);
        Map<String,Object> map = new HashMap<>();
        map.put("url",url);
        map.put("commodityId",commodityId);
        return commodityMapper.updateCommodity(map);
    }

    @Override
    public CommonPage<Commodity> selectOwnCommodity(int pageNo, int pageSize) {
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("userId",account.getUserId());
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityMapper.selectCommodity(map));
        return CommonPage.restPage(pageInfo);
    }

    @Override
    public int updateOwnCommodity(UpdateCommodityModel model) {
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
}
