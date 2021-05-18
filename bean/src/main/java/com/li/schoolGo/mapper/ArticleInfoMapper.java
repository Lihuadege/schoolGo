package com.li.schoolGo.mapper;

import com.li.schoolGo.bean.ArticleInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleInfoMapper extends Mapper<ArticleInfo> {
    ArticleInfo getArticleById(String id);

    List<ArticleInfo> getArticleList(@Param("schoolId") String schoolId);
}
