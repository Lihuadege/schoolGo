package com.li.schoolGo.controller;

import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.bean.SchoolInfo;
import com.li.schoolGo.service.SchoolInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class SchoolInfoController {

    @Autowired
    SchoolInfoService schoolInfoService;

    @RequestMapping("addSchool")
    @ResponseBody
    public ResponseBean addSchool(SchoolInfo schoolInfo) {
        log.debug("新增或者修改的学校信息: " + schoolInfo);
        Boolean res = schoolInfoService.addSchool(schoolInfo);
        if (res) {
            return ResponseBean.baseSuccess("成功");
        } else {
            return ResponseBean.baseError("失败");
        }
    }

    @RequestMapping("toAddSchool")
    public String toAddSchool(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("parent") == null) {
            List<SchoolInfo> schoolInfos = schoolInfoService.getParent();
            session.setAttribute("parent", schoolInfos);
        }

        if (id != null) {
            SchoolInfo schoolInfo = schoolInfoService.getSchoolInfo(id);
            request.setAttribute("school", schoolInfo);
        }

        return "extend/editSchool";
    }


    @RequestMapping("delSchool")
    @ResponseBody
    public ResponseBean delSchool(@RequestBody ArrayList<String> checkId) {
        if (checkId.size() < 1 || checkId == null) {
            return ResponseBean.baseError("删除失败");
        }
        Boolean res = schoolInfoService.delSchool(checkId);
        if (res) {
            return ResponseBean.baseSuccess("删除成功");
        } else {
            return ResponseBean.baseError("删除失败");
        }
    }

    /**
     * 获取所有学校列表
     * 这个功能好像没有用到
     *
     * @return
     */
    @RequestMapping("listSchool")
    @ResponseBody
    public Map<String, Object> listSchool(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                                          String name, String parentName) {

        if(name == null || name.isEmpty())name = null;
        if(parentName == null || parentName.isEmpty())parentName = null;

        Map<String, Object> resultMap = schoolInfoService.getSchoolInfoAndParentName(pageSize, pageNum, name, parentName);

        if (resultMap.get("data") != null) {
            resultMap.put("code", 0);
            resultMap.put("msg", "获取成功");
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "获取失败");
        }
        return resultMap;
    }

    /**
     * 简单页面转发，转发视图listSchool
     *
     * @return
     */
    @RequestMapping("toAreaList")
    public String toAreaList() {
        return "extend/listSchool";
    }

    /**
     * 获取所有的父地区，即所有省份
     *
     * @return
     */
    @RequestMapping("listParentId")
    @ResponseBody
    public ResponseBean getParent(HttpServletRequest request) {
        List<SchoolInfo> schoolInfos = schoolInfoService.getParent();
        return ResponseBean.baseSuccess("获取数据成功", schoolInfos);
    }

    /**
     * 根据地区查询其下学校列表
     *
     * @param parentId 地区id
     * @return
     */
    @RequestMapping("listSchools")
    @ResponseBody
    public ResponseBean getChildren(String parentId) {

        List<SchoolInfo> schoolInfos = schoolInfoService.getChildren(parentId);

        if (schoolInfos.size() > 0) {
            return ResponseBean.baseSuccess("获取数据成功", schoolInfos);
        }
        return ResponseBean.baseError("该地区下没有学校，请添加");
    }
}
