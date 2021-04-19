package com.li.schoolGo.controller;

import com.li.schoolGo.bean.DetailGoods;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailController {

    @Autowired
    GoodsInfoService goodsInfoService;

    @RequestMapping("getGoodsById")
    public ResponseBean getGoodsById(String goodsId){
        if(goodsId == null || !goodsId.isEmpty()){
            DetailGoods detailGoods = goodsInfoService.getGoodsInfoByGoodsId(goodsId);
            return ResponseBean.baseSuccess("获取成功",detailGoods);
        }else {
            return ResponseBean.baseError("获取商品信息失败");
        }
    }

}
