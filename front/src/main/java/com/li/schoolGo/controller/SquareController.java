package com.li.schoolGo.controller;

import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.ArticleInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.ArticleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class SquareController {

    @Autowired
    ArticleInfoService articleInfoService;

    @RequestMapping("listArticle")
    public ResponseBean listArticle(@RequestParam(value = "page", defaultValue = "1") Integer page, String schoolId) {
        HashMap<String, Object> ret = new HashMap<>();
        try {
            List<ArticleInfo> articleList = articleInfoService.getArticleList(page, schoolId);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<>(articleList);
            ret.put("total", pageInfo.getTotal());
            ret.put("articleList", articleList);
        } catch (Exception e) {
            return ResponseBean.baseError("出错了，请重试");
        }
        return ResponseBean.baseSuccess("获取成功", ret);
    }
}