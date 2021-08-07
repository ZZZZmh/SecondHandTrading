package com.zmh.secondHandTrading.controller;/**
 * @title: UserController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/25 15:52
 */

import com.zmh.secondHandTrading.entity.model.CertificationModel;
import com.zmh.secondHandTrading.entity.model.CommodityAddModel;
import com.zmh.secondHandTrading.entity.model.UpdateCommodityModel;
import com.zmh.secondHandTrading.entity.model.UpdateUserinfoModel;
import com.zmh.secondHandTrading.entity.pojo.Account;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import com.zmh.secondHandTrading.entity.resp.UserInfoResp;
import com.zmh.secondHandTrading.myEnum.ResultCode;
import com.zmh.secondHandTrading.service.impl.UserServiceImpl;
import com.zmh.secondHandTrading.util.CommonResult;
import com.zmh.secondHandTrading.util.ImageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.net.HttpCookie;

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
    public CommonResult selectOwnCommodity(@RequestParam @NotNull int pageNo, @RequestParam @NotNull int pageSize){
        return CommonResult.success(userService.selectOwnCommodity(pageNo, pageSize));
    }



    // 更新商品信息
    @PostMapping("/user/commodity/updateOwnCommodity")
    public CommonResult updateOwnCommodity(@RequestBody UpdateCommodityModel model){
        if(userService.updateOwnCommodity(model)==1){
            return CommonResult.success("修改成功");
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
