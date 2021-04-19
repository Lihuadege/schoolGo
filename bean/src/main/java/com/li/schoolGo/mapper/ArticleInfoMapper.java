package com.li.schoolGo.mapper;

import com.li.schoolGo.bean.ArticleInfo;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleInfoMapper extends Mapper<ArticleInfo> {
    ArticleInfo getArticleById(String id);
}
