package com.li.schoolGo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.SchoolInfo;
import com.li.schoolGo.mapper.SchoolInfoMapper;
import com.li.schoolGo.service.SchoolInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SchoolInfoServiceImpl implements SchoolInfoService {

    @Autowired
    SchoolInfoMapper schoolInfoMapper;

    @Override
    public Boolean addSchool(SchoolInfo schoolInfo) {
        int i = 0;
        if (schoolInfo.getId() == null || schoolInfo.getId().isEmpty()) {
            //id 为空，则是新增
            schoolInfo.setId(null);
            i = schoolInfoMapper.insert(schoolInfo);
        } else {
            //id不为空，修改
            i = schoolInfoMapper.updateByPrimaryKeySelective(schoolInfo);
        }
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean delSchool(ArrayList<String> checkId) {
        Example example = new Example(SchoolInfo.class);
        example.createCriteria().andIn("id", checkId);
        int i = schoolInfoMapper.deleteByExample(example);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<SchoolInfo> getChildren(String parentId) {
        Example example = new Example(SchoolInfo.class);
        example.createCriteria().andEqualTo("parentId", parentId);
        List<SchoolInfo> schoolInfos = schoolInfoMapper.selectByExample(example);
        return schoolInfos;
    }

    @Override
    public SchoolInfo getParentId(Integer userAreaId) {
        Example example = new Example(SchoolInfo.class);
        example.createCriteria().andEqualTo("id", userAreaId);
        SchoolInfo schoolInfo = schoolInfoMapper.selectOneByExample(example);
        return schoolInfo;
    }

    @Override
    public SchoolInfo getSchoolInfo(String id) {
        SchoolInfo school = new SchoolInfo();
        school.setId(id);
        SchoolInfo schoolInfo = schoolInfoMapper.selectByPrimaryKey(school);
        return schoolInfo;
    }

    @Override
    public Map<String, Object> getSchoolInfoAndParentName(Integer pageSize, Integer pageNum, String name, String parentName) {
        PageHelper.startPage(pageNum,pageSize);

        List<SchoolInfo> schoolInfoList = schoolInfoMapper.getSchoolInfoAndParentName(parentName,name);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("data",schoolInfoList);
        PageInfo<SchoolInfo> page = new PageInfo<>(schoolInfoList);
        long total = page.getTotal();
        retMap.put("count",total);
        return retMap;
    }

    @Override
    public List<SchoolInfo> getSchoolInfoListByUserAreaId(Integer userAreaId) {
        return schoolInfoMapper.getSchoolInfoListByUserAreaId(userAreaId);
    }

    @Override
    public List<SchoolInfo> getParent() {

        Example example = new Example(SchoolInfo.class);

        example.createCriteria().andEqualTo("parentId", 0);

        List<SchoolInfo> schoolInfos = schoolInfoMapper.selectByExample(example);

        return schoolInfos;
    }
}
