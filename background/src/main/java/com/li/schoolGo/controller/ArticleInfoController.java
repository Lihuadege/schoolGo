package com.li.schoolGo.controller;

import com.li.schoolGo.bean.ArticleInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.ArticleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ArticleInfoController {

    @Autowired
    ArticleInfoService articleInfoService;

    @RequestMapping("toArticleList")
    public String toArticleList(){
        return "content/list";
    }

    @RequestMapping("toArticleDetail")
    public String toArticleDetail(String id, HttpServletRequest request){
        ArticleInfo articleInfo = articleInfoService.getArticleById(id);

        if(articleInfo == null){
            return "tips/error";
        }

        request.setAttribute("articleInfo",articleInfo);

        return "content/detail";
    }

    @RequestMapping("listArticle")
    @ResponseBody
    public Map<String, Object> getAll(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                                      String userNickName) {

        Map<String, Object> resultMap = articleInfoService.getAll(pageNum, pageSize, userNickName);

        resultMap.put("code", 0);
        return resultMap;
    }

    @RequestMapping("delArticle")
    @ResponseBody
    public ResponseBean delArticle(String checkId) {
        Boolean res = articleInfoService.delArticleById(checkId);

        if(res){
            return ResponseBean.baseSuccess("删除成功");
        }else {
            return ResponseBean.baseError("删除失败");
        }
    }
}
