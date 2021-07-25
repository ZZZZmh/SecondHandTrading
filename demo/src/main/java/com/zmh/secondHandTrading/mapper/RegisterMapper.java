package com.zmh.secondHandTrading.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zmh
 * @title: RegisterMapper
 * @projectName demo
 * @description: TODO
 * @date 2021/7/25 13:32
 */
@Repository("RegisterMapper")
@Mapper
public interface RegisterMapper {

    public int registerAccount(Map<String,Object> param);
    public int registerUser(Map<String,Object> param);
    public int translationEmail(String email);
}
