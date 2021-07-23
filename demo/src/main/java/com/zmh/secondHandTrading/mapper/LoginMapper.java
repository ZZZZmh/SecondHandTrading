package com.zmh.secondHandTrading.mapper;

import com.zmh.secondHandTrading.entity.pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zmh
 * @title: LoginMapper
 * @projectName demo
 * @description: TODO
 * @date 2021/7/23 14:44
 */
@Repository("LoginMapper")
@Mapper
public interface LoginMapper {
    public Account selectAccount(String account);
}
