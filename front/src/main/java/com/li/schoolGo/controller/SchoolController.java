package com.li.schoolGo.controller;

import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.bean.SchoolInfo;
import com.li.schoolGo.service.SchoolInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class SchoolController {

    @Autowired
    SchoolInfoService schoolInfoService;

    @RequestMapping("getChildren")
    public ResponseBean getChildren(String parentId){
        if(parentId == null || parentId == ""){
            return ResponseBean.baseError("发生未知错误");
        }
        List<SchoolInfo> schoolInfoList = schoolInfoService.getChildren(parentId);

        if (schoolInfoList.size() > 0) {
            return ResponseBean.baseSuccess("获取成功",schoolInfoList);
        }else {
            return ResponseBean.base(1,"该地区无学校",null);
        }
    }


}
