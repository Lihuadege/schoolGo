package com.li.schoolGo.service;

import com.li.schoolGo.bean.ArticleInfo;

import java.util.Map;

public interface ArticleInfoService {

    Map<String, Object> getAll(Integer pageNum, Integer pageSize, String userNickName);

    Boolean delArticleById(String checkId);

    ArticleInfo getArticleById(String id);
}
