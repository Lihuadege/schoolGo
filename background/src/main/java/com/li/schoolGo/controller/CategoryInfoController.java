package com.li.schoolGo.controller;

import com.li.schoolGo.bean.CategoryInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.CategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryInfoController {

    @Autowired
    CategoryInfoService categoryInfoService;

//    @RequestMapping("listCategory")
//    public ResponseBean listCategory(){
//        List<CategoryInfo> categoryInfoList;
//    }

}
