package com.zmh.secondHandTrading.controller;/**
 * @title: PublicController
 * @projectName demo
 * @description: TODO
 * @author zmh
 * @date 2021/8/4 17:51
 */


import com.github.pagehelper.PageHelper;
import com.zmh.secondHandTrading.entity.model.CommentModel;
import com.zmh.secondHandTrading.service.impl.CommentServiceImpl;
import com.zmh.secondHandTrading.service.impl.PublicServiceImpl;
import com.zmh.secondHandTrading.util.CommonPage;
import com.zmh.secondHandTrading.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 *@ClassName PublicController
 *@Description TODO
 *@Author ASUS
 *@Date 2021/8/4 17:51
 *@Version 1.0
 */

@RestController
public class PublicController {

    @Autowired
    PublicServiceImpl publicService;
    @Autowired
    CommentServiceImpl commentService;

    // 按名称查找商品(默认价格升序)
    @GetMapping("/public/select/commodity/byName")
    public CommonResult serachCommodityByName(@RequestParam @NotNull String commodityName,String order,String clause,Integer pageStart,Integer size){
        return CommonResult.success(publicService.serachCommodityByName(commodityName, order,clause,pageStart,size));
    }

    // 按分类查找商品(默认价格升序)
    @GetMapping("/public/select/commodity/byLabel")
    public CommonResult serachCommodityByLabel(@RequestParam @NotNull String label,String order,String clause,Integer pageStart,Integer size){
        return CommonResult.success(publicService.serachCommodityByLabel(label, order,clause,pageStart,size));
    }

    // 查看商品的所有评论
    @PostMapping("/public/queryAllByCommodityId")
    public CommonResult queryAllByCommodityId(@RequestParam String commodityId,Integer pageStart){
        if ( pageStart==null ||pageStart<=0){
            pageStart = 1;
        }
        PageHelper.startPage(pageStart,5);
        List<CommentModel> commentModels = commentService.queryAllByCommodityId(commodityId);
        return CommonResult.success(CommonPage.restPage(commentModels));
    }

}
