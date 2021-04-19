package com.li.schoolGo.controller;

import com.li.schoolGo.bean.NoticeInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.NoticeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class NoticeInfoController {

    @Autowired
    NoticeInfoService noticeInfoService;

    @RequestMapping("toNoticeList")
    public String toNoticeList(){
        return "extend/listNotice";
    }

    @RequestMapping("listNotice")
    @ResponseBody
    public ResponseBean listNotice(){
        List<NoticeInfo> noticeInfoList = noticeInfoService.getAllNotice();
        return ResponseBean.baseSuccess("获取成功",noticeInfoList);
    }

    @RequestMapping("toAddNotice")
    public String toAddNotice(String id, HttpServletRequest request){
        if(id != null){
            NoticeInfo noticeInfo = noticeInfoService.getOneNoticeById(id);
            if(noticeInfo != null){
                request.setAttribute("noticeInfo",noticeInfo);
            }
        }
        return "extend/editNotice";
    }

    @RequestMapping("doAddNotice")
    @ResponseBody
    public ResponseBean doAddNotice(NoticeInfo noticeInfo){
        if(noticeInfo.getNoticeContent() == null){
            return ResponseBean.baseError("请输入内容再保存");
        }

        Boolean res = noticeInfoService.addNotice(noticeInfo);
        if(res){
            return ResponseBean.baseSuccess("成功");
        }else {
            return ResponseBean.baseError("失败");
        }
    }

    @RequestMapping("delNotice")
    @ResponseBody
    public ResponseBean delNotice(@RequestBody ArrayList<String> checkId){
        if(checkId.size()<1 || checkId == null){
            return ResponseBean.baseError("删除失败");
        }
        Boolean res = noticeInfoService.delNotice(checkId);
        if (res) {
            return ResponseBean.baseSuccess("删除成功");
        }else {
            return ResponseBean.baseError("删除失败");
        }
    }

}
