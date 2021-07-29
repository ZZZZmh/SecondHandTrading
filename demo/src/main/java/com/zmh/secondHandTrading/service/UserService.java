package com.zmh.secondHandTrading.service;

import com.zmh.secondHandTrading.entity.model.CertificationModel;
import com.zmh.secondHandTrading.entity.model.UpdateUserinfoModel;
import com.zmh.secondHandTrading.entity.pojo.Userinfo;

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
}
