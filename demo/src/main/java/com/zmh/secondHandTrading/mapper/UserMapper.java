package com.zmh.secondHandTrading.mapper;/**
 * @title: UserMapper
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/7/23 14:24
 */

import com.zmh.secondHandTrading.entity.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
    public User selectAllInformation(String userId);
}
