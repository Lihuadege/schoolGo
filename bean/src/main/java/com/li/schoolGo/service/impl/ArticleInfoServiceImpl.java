package com.li.schoolGo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.ArticleInfo;
import com.li.schoolGo.mapper.ArticleInfoMapper;
import com.li.schoolGo.service.ArticleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Autowired
    ArticleInfoMapper articleInfoMapper;

    @Override
    public List<ArticleInfo> getArticleList(Integer page, String schoolId) {
        PageHelper.startPage(page,10);
        List<ArticleInfo> articleList = articleInfoMapper.getArticleList(schoolId);
        return articleList;
    }

    @Override
    public ArticleInfo getArticleById(String id) {
        ArticleInfo articleInfo = articleInfoMapper.getArticleById(id);
        return articleInfo;
    }

    @Override
    public Boolean delArticleById(String checkId) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(checkId);
        articleInfo.setStatus(0);
        int i = articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
        if(i == 1){
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getAll(Integer pageNum, Integer pageSize, String userNickName) {
        Example example = new Example(ArticleInfo.class);

        if (!StringUtils.isEmpty(userNickName)) {
            example.createCriteria().andLike("userNickName", "%" + userNickName + "%");
        }

        example.createCriteria().andEqualTo("status", 1);

        HashMap<String, Object> retMap = new HashMap<>();
        try {
            PageHelper.startPage(pageNum, pageSize);

            List<ArticleInfo> articleInfos = articleInfoMapper.selectByExample(example);

            PageInfo<ArticleInfo> info = new PageInfo<>(articleInfos);
            retMap.put("count", info.getTotal());
            retMap.put("data", articleInfos);
            retMap.put("msg", "获取成功");
        } catch (Exception e) {
            retMap.put("msg", "出错了");
        }

        return retMap;
    }
}
