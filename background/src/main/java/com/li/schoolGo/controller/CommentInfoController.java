package com.li.schoolGo.controller;

import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class CommentInfoController {

    @Autowired
    CommentInfoService commentInfoService;

    @RequestMapping("toCommentList")
    public String toCommentList(){
        return "content/listComment";
    }

    @RequestMapping("delComment")
    @ResponseBody
    public ResponseBean delComment(String checkId){
        if(checkId != null){
            Boolean ret = commentInfoService.delComment(checkId);
            if(ret){
                return ResponseBean.baseSuccess("删除成功");
            }else {
                return ResponseBean.baseError("删除失败");
            }
        }
        return ResponseBean.baseSuccess("出错了");
    }

    @RequestMapping("listComment")
    @ResponseBody
    public Map<String, Object> listComment(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                           String userNickName, String articleTitle){
        Map<String, Object> retMap = commentInfoService.getAllComment(pageNum,pageSize,userNickName,articleTitle);

        retMap.put("code",0);

        return retMap;
    }

}
