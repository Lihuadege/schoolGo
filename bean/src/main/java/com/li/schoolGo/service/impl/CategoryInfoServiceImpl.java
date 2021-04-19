package com.li.schoolGo.service.impl;

import com.li.schoolGo.bean.CategoryInfo;
import com.li.schoolGo.mapper.CategoryInfoMapper;
import com.li.schoolGo.service.CategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryInfoServiceImpl implements CategoryInfoService {

    @Autowired
    CategoryInfoMapper categoryInfoMapper;

    @Override
    public List<CategoryInfo> getAll() {
        List<CategoryInfo> categoryInfoList = categoryInfoMapper.selectAll();
        return categoryInfoList;
    }
}
