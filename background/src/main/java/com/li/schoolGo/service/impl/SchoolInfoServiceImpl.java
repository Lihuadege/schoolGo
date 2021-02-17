package com.li.schoolGo.service.impl;

import com.li.schoolGo.bean.SchoolInfo;
import com.li.schoolGo.mapper.SchoolInfoMapper;
import com.li.schoolGo.service.SchoolInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Slf4j
public class SchoolInfoServiceImpl implements SchoolInfoService {

    @Autowired
    SchoolInfoMapper schoolInfoMapper;

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
