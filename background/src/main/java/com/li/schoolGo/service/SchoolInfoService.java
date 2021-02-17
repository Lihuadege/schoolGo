package com.li.schoolGo.service;

import com.li.schoolGo.bean.SchoolInfo;

import java.util.List;

public interface SchoolInfoService {
    SchoolInfo getParentId(Integer userAreaId);

    List<SchoolInfo> getParent();

    List<SchoolInfo> getChildren(String parentId);

    List<SchoolInfo> getSchoolInfoListByUserAreaId(Integer userAreaId);
}
