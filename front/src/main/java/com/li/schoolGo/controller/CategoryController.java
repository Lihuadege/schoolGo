package com.li.schoolGo.controller;

import com.li.schoolGo.bean.CategoryInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.CategoryInfoService;
import com.li.schoolGo.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.style.ValueStyler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    CategoryInfoService categoryInfoService;

    @Autowired
    GoodsInfoService goodsInfoService;

    @RequestMapping("getCategory")
    public ResponseBean getCategory(){
        List<CategoryInfo> category = categoryInfoService.getAll();
        return ResponseBean.baseSuccess("获取成功", category);
    }

    /**
     * 时间原因，不考虑太复杂的业务逻辑了，包括：非空校验，异常处理等等
     * @param page 当前页面
     * @param categoryId 商品所属分类id
     * @return
     */
    @RequestMapping("getGoodsList")
    public Map<String, Object> getGoodsList(@RequestParam(value = "page", defaultValue = "1") Integer page, String categoryId){
        Map<String, Object> retMap = goodsInfoService.getGoodsInfoByCategoryId(page, categoryId);
        return retMap;
    }

}
