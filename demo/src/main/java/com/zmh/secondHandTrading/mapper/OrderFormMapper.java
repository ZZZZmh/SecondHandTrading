package com.zmh.secondHandTrading.mapper;

import com.zmh.secondHandTrading.entity.model.OrderFormModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zmh
 * @title: OrderFormMapper
 * @projectName demo
 * @description: TODO
 * @date 2021/8/8 15:16
 */

@Repository("OrderFormMapper")
@Mapper
public interface OrderFormMapper {
    public int addOrderForm(Map map);
}
