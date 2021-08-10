package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.model.*;
import com.zmh.secondHandTrading.entity.pojo.Commodity;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.util.CommonPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author zmh
 * @title: UserService
 * @projectName demo
 * @description: TODO
 * @date 2021/7/23 14:27
 */
public interface UserService {

    // 查看用户所有信息
    public Userinfo userInformation(String userId);

    // 更新个人信息
    public int userInforUpate(UpdateUserinfoModel model);

    // 改绑邮箱
    public int updateEmail(String email);

    // 更改密码
    public int updatePassword(String password);

    // 更新头像
    public int updateHead(String imagUrl);

    // 实名认证
    public int certification(CertificationModel model);

    // 注销用户
    public int logout();

    // 上架商品
    public int addCommodity(CommodityAddModel model);

    // 上传商品图片
    public int updateCommodityImg(MultipartFile file,String commodityId) throws Exception;

    // 查询自己上架的商品
    public CommonPage<Commodity> selectOwnCommodity(Integer pageNo, Integer pageSize);

    // 更新自己上架的商品
    public int updateOwnCommodity(UpdateCommodityModel model) throws Exception;

    // 购买商品进消息队列
    public int enterQueue(String commodityId,int purchaseQuantity,String address);

    // 处理消息队列购买商品
    public int buyCommodity(String userid,String orderId) throws Exception;

}
