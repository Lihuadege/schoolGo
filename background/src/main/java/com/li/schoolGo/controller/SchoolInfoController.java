package com.li.schoolGo.controller;

import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.bean.SchoolInfo;
import com.li.schoolGo.service.SchoolInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolInfoController {

    @Autowired
    SchoolInfoService schoolInfoService;

    @RequestMapping("listParentId")
    public ResponseBean getParent(){
        List<SchoolInfo> schoolInfos = schoolInfoService.getParent();
        return ResponseBean.baseSuccess("获取数据成功",schoolInfos);
    }

    @RequestMapping("listSchools")
    public ResponseBean getChildren(String parentId){

        List<SchoolInfo> schoolInfos = schoolInfoService.getChildren(parentId);

        if(schoolInfos.size() > 0){
            return ResponseBean.baseSuccess("获取数据成功",schoolInfos);
        }
        return ResponseBean.baseError("子项目获取失败，请重试");
    }
}
