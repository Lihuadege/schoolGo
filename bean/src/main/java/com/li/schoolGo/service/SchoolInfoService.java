package com.li.schoolGo.service;

import com.li.schoolGo.bean.SchoolInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface SchoolInfoService {
    SchoolInfo getParentId(Integer userAreaId);

    List<SchoolInfo> getParent();

    List<SchoolInfo> getChildren(String parentId);

    List<SchoolInfo> getSchoolInfoListByUserAreaId(Integer userAreaId);

    Map<String, Object> getSchoolInfoAndParentName(Integer pageSize, Integer pageNum, String name, String parentName);

    SchoolInfo getSchoolInfo(String id);

    Boolean delSchool(ArrayList<String> checkId);

    Boolean addSchool(SchoolInfo schoolInfo);
}
