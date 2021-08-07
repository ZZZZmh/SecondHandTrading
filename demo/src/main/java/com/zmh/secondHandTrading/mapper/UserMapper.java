package com.zmh.secondHandTrading.mapper;/**
 * @title: UserMapper
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:24
 */

import com.zmh.secondHandTrading.entity.pojo.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *@ClassName UserMapper
 *@Description TODO
 *@Author ASUS
 *@Date 2021/7/23 14:24
 *@Version 1.0
 */
@Repository("UserMapper")
@Mapper
public interface UserMapper {
    public Userinfo selectAllInformation(String userId);
    public String serachIdcard(String idcard);
    public int updateAccount(Map<String,Object> param);
    public int updateUserinfo(Map<String,Object> param);
}
