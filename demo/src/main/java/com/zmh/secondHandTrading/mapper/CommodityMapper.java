package com.zmh.secondHandTrading.mapper;

import com.zmh.secondHandTrading.entity.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zmh
 * @title: CommodityMapper
 * @projectName demo
 * @description: TODO
 * @date 2021/8/4 11:03
 */
@Repository("CommodityMapper")
@Mapper
public interface CommodityMapper {
    public int addCommodity(Map map);
    public List<Commodity> selectCommodity(Map map);
    public int updateCommodity(Map map);
}
