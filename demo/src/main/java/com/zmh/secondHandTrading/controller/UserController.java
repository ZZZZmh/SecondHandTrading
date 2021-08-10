package com.zmh.secondHandTrading.controller;/**
 * @title: UserController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 15:52
 */

import com.github.pagehelper.PageInfo;
import com.zmh.secondHandTrading.entity.model.*;
import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.OrderForm;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.entity.resp.UserInfoResp;
import com.zmh.secondHandTrading.myEnum.ResultCode;
import com.zmh.secondHandTrading.service.impl.OrderServiceImpl;
import com.zmh.secondHandTrading.service.impl.PublicServiceImpl;
import com.zmh.secondHandTrading.service.impl.UserServiceImpl;
import com.zmh.secondHandTrading.util.CommonPage;
import com.zmh.secondHandTrading.util.CommonResult;
import com.zmh.secondHandTrading.util.ImageUtil;
import com.zmh.secondHandTrading.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName UserController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/25 15:52
 *@Version 1.0
 */
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    PublicServiceImpl publicService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    RedisUtil redisUtil;

    // 用户信息
    @PostMapping("/user/Tourist/userInfo")
    public CommonResult serachInfo(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Userinfo userinfo = userService.userInformation(account.getUserId());
        // 打包处理封装信息返回前端
        UserInfoResp userInfoResp = new UserInfoResp();
        userInfoResp.setUserName(userinfo.getUserName());
        userInfoResp.setSignature(userinfo.getSignature());
        userInfoResp.setRealName(userinfo.getRealName());
        userInfoResp.setHeadPortrait(userinfo.getHeadPortrait());
        userInfoResp.setStudentId(userinfo.getStudentId());
        session.setAttribute("userInfo",userInfoResp);
        return CommonResult.success(userInfoResp);
    }

    // 更新用户用户名，个性签名
    @PostMapping("/user/Tourist/upateUserInfo")
    public CommonResult userInforUpate(@RequestBody UpdateUserinfoModel model){
        if(userService.userInforUpate(model) == 1){
            return CommonResult.success("用户信息更新成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"用户信息更新失败");
    }



    // 改绑邮箱
    @PostMapping("/user/Tourist/updateEmail")
    public CommonResult updateEmail(@RequestParam @NotNull @Email String email){
        if(userService.updateEmail(email) == 1){
            return CommonResult.success("邮箱改绑成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"邮箱改绑失败");
    }


    // 更改密码
    @PostMapping("/user/Tourist/updatePassword")
    public CommonResult updatePassword(@RequestParam @NotNull @Email String password){
        if(userService.updatePassword(password) == 1){
            return CommonResult.success("密码更新成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"密码更新失败");
    }

    // 绑定真实信息
    @PostMapping("/user/Tourist/certification")
    public CommonResult certification(@RequestBody CertificationModel model){
        if(userService.certification(model) == -1){
            return CommonResult.failed(ResultCode.FAILED,"该账号已被绑定");
        }
        else if(userService.certification(model) == -2){
            return CommonResult.failed(ResultCode.FAILED,"该身份证已绑定其他账号");
        }
        else if(userService.certification(model) == 1){
            return CommonResult.success("模拟调用实名认证接口=》绑定成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"绑定失败");
    }

    // 上架商品
    @PostMapping("/user/commodity/add")
    public CommonResult addcommodity(HttpSession session,@RequestBody CommodityAddModel model){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        if (account.getCredibility()<80){
            return CommonResult.failed(ResultCode.FORBIDDEN,"信誉指过低，不允许上架");
        }
        if(userService.addCommodity(model) == 1){
            return CommonResult.success("商品添加成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"商品添加失败");
    }

    // 上传商品图片
    @PostMapping("/user/commodity/updateCommodityImg")
    public CommonResult updateCommodityImg(MultipartFile file,String commodityId){
        if(userService.updateCommodityImg(file,commodityId)==1){
            return CommonResult.success("商品添加成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"商品添加失败");
    }

    // 查看用户上架商品
    @GetMapping("/user/commodity/selectOwn")
    public CommonResult selectOwnCommodity(@RequestParam @NotNull Integer pageNo, @RequestParam @NotNull Integer pageSize){
        return CommonResult.success(userService.selectOwnCommodity(pageNo, pageSize));
    }

    // 更新商品信息
    @PostMapping("/user/commodity/updateOwnCommodity")
    public CommonResult updateOwnCommodity(@RequestBody UpdateCommodityModel model){
        try {
            if(userService.updateOwnCommodity(model)==1){
                return CommonResult.success("修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed(ResultCode.FAILED,"修改失败");
    }

    // 更新头像
    @PostMapping("/user/Tourist/updateHead")
    public CommonResult updateHead(MultipartFile file){
        String imgurl= ImageUtil.upload(file);
        if(userService.updateHead(imgurl) == 1){
            return CommonResult.success("头像更新成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"头像更新失败");
    }

    // 注销账号
    @PostMapping("/user/Tourist/logout")
    public CommonResult logout(){
        if(userService.logout() == 1){
            return CommonResult.success("账户已注销");
        }
        return CommonResult.failed(ResultCode.FAILED,"账户注销失败");
    }

    // 查看购物车

    // 购物车添加

    // 购物车删除

    // 增加订单
    @PostMapping("/user/commodity/addOrderForm")
    public CommonResult addOrderForm(@RequestBody OrderFormModel model){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        if (account.getCredibility()<80){
            return CommonResult.failed(ResultCode.FORBIDDEN,"信誉指过低，不允许购买");
        }
        int result = orderService.add(model.getCommodityId(),account.getUserId(), model.getPurchaseQuantity(), model.getAddress(), model.getSeller());
        if (result == 1){
            return CommonResult.success("添加成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"订单更新失败");
    }

    // 取消订单

    // 购买商品(redis)
    @PostMapping("/user/commodity/buy")
    public CommonResult buy(@RequestBody OrderFormModel model){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        if (account.getCredibility()<80){
            return CommonResult.failed(ResultCode.FORBIDDEN,"信誉指过低，不允许购买");
        }
        System.out.println("正在下单");
        // 入队
        int result = userService.enterQueue(model.getCommodityId(),model.getPurchaseQuantity(),model.getAddress());
        if(result == -1){
            System.out.println("请勿重复购买，正在帮您处理");
        }else if (result == -2){
            System.out.println("无法查询到该商品");
        }
        // 处理对应商品队列
        System.out.println("正在处理");
        publicService.handleQueue(model.getCommodityId(),model.getOrderId());
        // 查询结果，一秒查询一次，查询十秒，十秒后超时
        System.out.println("查询结果");
        int outcome = 0;
        for (int i = 0; i < 10; i++) {
            outcome = (Integer) redisUtil.hget(account.getUserId(),"result");
            if (outcome==1){
                redisUtil.hdel(account.getUserId(),"result");
                System.out.println("=======》扣除账户余额");
                return CommonResult.success("购买成功");
            }else if(outcome==-1){
                return CommonResult.failed(ResultCode.FAILED,"购买失败，订单错误");
            }else if(outcome==-2){
                return CommonResult.failed(ResultCode.FAILED,"购买失败，购买数量大与商品数量");
            }else if(outcome==-3){
                return CommonResult.failed(ResultCode.FAILED,"购买失败，商品已售尽");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return CommonResult.failed(ResultCode.FAILED,"超时！购买失败，查询无结果。请稍后再试");
    }

    // 更新收货地址
    @PostMapping("/user/commodity/updateAddress")
    public CommonResult updateAddress(@RequestParam String orderId,@RequestParam String address){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("address",address);
        if(orderService.update(map) == 1){
            return CommonResult.success("更改地址成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"更改地址失败");
    }

    // 拒签
    @PostMapping("/user/commodity/refusal")
    public CommonResult refusal(@RequestParam String orderId){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",4);
        if(orderService.update(map) == 1){
            return CommonResult.success("拒签成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"拒签失败");
    }

    // 申请退款
    @PostMapping("/user/commodity/refund")
    public CommonResult refund(@RequestParam String orderId){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",5);
        if(orderService.update(map) == 1){
            return CommonResult.success("申请退款成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"申请退款失败");
    }


    // 退款处理(商家)
    // 拒签
    @PostMapping("/user/commodity/merchantRefund")
    public CommonResult merchantRefund(@RequestParam String orderId){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",6);
        if(orderService.update(map) == 1){
            return CommonResult.success("退款成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"退款失败");
    }

    // 确认收货
    @PostMapping("/user/commodity/receipt")
    public CommonResult receipt(@RequestParam String orderId){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",3);
        if(orderService.update(map) == 1){
            return CommonResult.success("收货成功");
        }
        return CommonResult.failed(ResultCode.FAILED,"收货失败");
    }

    // 查看购买订单
    @PostMapping("/user/commodity/selectBuy")
    public CommonResult selectBuy(String param,String order,Integer pageStart,Integer pageSize){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("buyer",account.getUserId());
        // 创建时间降序
        if(param==null||param.equals("")){
            map.put("clause","create_time");
        }else {
            map.put("clause",param);
        }
        if(order==null||order.equals("")){
            map.put("order","DESC");
        }else {
            map.put("order",order);
        }
        // 默认分页第一页
        if(pageStart!=null && pageStart >=1 ){
            map.put("pageStart",pageStart-1);
        }else {
            map.put("pageStart",0);
        }
        // 默认页大小为5
        if(pageSize!=null && pageSize >=1 ){
            map.put("pageSize",pageSize);
        }else {
            map.put("pageSize",5);
        }
        PageInfo<OrderForm> pageInfo = new PageInfo<>(orderService.select(map));
        return CommonResult.success(CommonPage.restPage(pageInfo));
    }

    // 查看卖出订单
    @PostMapping("/user/commodity/selectSeller")
    public CommonResult selectSeller(String param,String order,Integer pageStart,Integer pageSize){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("seller",account.getUserId());
        // 创建时间降序
        if(param==null||param.equals("")){
            map.put("clause","create_time");
        }else {
            map.put("clause",param);
        }
        if(order==null||order.equals("")){
            map.put("order","DESC");
        }else {
            map.put("order",order);
        }
        // 默认分页第一页
        if(pageStart!=null && pageStart >=1 ){
            map.put("pageStart",pageStart-1);
        }else {
            map.put("pageStart",0);
        }
        // 默认页大小为5
        if(pageSize!=null && pageSize >=1 ){
            map.put("pageSize",pageSize);
        }else {
            map.put("pageSize",5);
        }
        PageInfo<OrderForm> pageInfo = new PageInfo<>(orderService.select(map));
        return CommonResult.success(CommonPage.restPage(pageInfo));
    }

    // 查看代发货订单
    @PostMapping("/user/commodity/selectSellerShip")
    public CommonResult selectSellerShip(String param,String order,Integer pageStart,Integer pageSize){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("seller",account.getUserId());
        // 创建时间降序
        if(param==null||param.equals("")){
            map.put("clause","create_time");
        }else {
            map.put("clause",param);
        }
        if(order==null||order.equals("")){
            map.put("order","DESC");
        }else {
            map.put("order",order);
        }
        // 默认分页第一页
        if(pageStart!=null && pageStart >=1 ){
            map.put("pageStart",pageStart-1);
        }else {
            map.put("pageStart",0);
        }
        // 默认页大小为5
        if(pageSize!=null && pageSize >=1 ){
            map.put("pageSize",pageSize);
        }else {
            map.put("pageSize",5);
        }
        map.put("status",1);
        PageInfo<OrderForm> pageInfo = new PageInfo<>(orderService.select(map));
        return CommonResult.success(CommonPage.restPage(pageInfo));
    }
    // 评论

    // 评论点赞（点踩）

    // 申请售后

}
