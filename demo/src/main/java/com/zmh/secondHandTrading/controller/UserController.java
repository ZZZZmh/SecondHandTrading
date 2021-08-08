package com.zmh.secondHandTrading.controller;/**
 * @title: UserController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 15:52
 */

import com.zmh.secondHandTrading.entity.model.*;
import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.entity.resp.UserInfoResp;
import com.zmh.secondHandTrading.myEnum.ResultCode;
import com.zmh.secondHandTrading.service.impl.PublicServiceImpl;
import com.zmh.secondHandTrading.service.impl.UserServiceImpl;
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
        userInfoResp.setCredibility("当前信誉指:"+userinfo.getCredibility());
        // 放入session
        session.setAttribute("user",userInfoResp);
        session.setAttribute("credibility",userinfo.getCredibility());
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


    // 购买商品(redis)
    @PostMapping("/user/commodity/buy")
    public CommonResult buy(@RequestBody OrderFormModel model){
        Subject subject = SecurityUtils.getSubject();
        Account account= (Account) subject.getPrincipal();
        System.out.println("正在下单");
        // 入队
        int result = userService.enterQueue(model.getCommodityId(), model.getCommodityNumber(),model.getPurchaseQuantity(),model.getAddress());
        if(result == -1){
            System.out.println("已有订单，请勿重复下单，正在帮您处理");
        }
        // 处理对应商品队列
        System.out.println("正在处理");
        publicService.handleQueue(model.getCommodityId());
        // 查询结果，一秒查询一次，查询十秒
        int outcome = 0;
        for (int i = 0; i < 10; i++) {
            outcome = (Integer) redisUtil.hget(account.getUserId(),"result");
            if (outcome==1){
                System.out.println("=======》扣除账户余额");
                // 清除在redis中的订单(数据库已生成标准订单，已经可在数据库中查询)
                redisUtil.hdel(account.getUserId(),"commodityId");
                return CommonResult.success("购买成功");
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


    // 查看购物车

    // 购物车添加

    // 购物车删除

    // 结算

    // 确认收货

    // 申请退款

    // 查看订单(用户，商家)

    // 取消订单

    // 上架商品
    @PostMapping("/user/commodity/add")
    public CommonResult addcommodity(HttpSession session,@RequestBody CommodityAddModel model){
        int credibility = (int) session.getAttribute("credibility");
        if (credibility<80){
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

    // 退款处理

    // 更新订单

    // 查看代发货订单

    // 评论

    // 评论点赞（点踩）

    // 申请售后

}
