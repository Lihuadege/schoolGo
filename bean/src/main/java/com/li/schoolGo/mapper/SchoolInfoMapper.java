package com.li.schoolGo.mapper;

import com.li.schoolGo.bean.SchoolInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SchoolInfoMapper extends Mapper<SchoolInfo> {

    List<SchoolInfo> getSchoolInfoListByUserAreaId(Integer userAreaId);

    List<SchoolInfo> getSchoolInfoAndParentName(@Param("parentName") String parentName, @Param("name") String name);

}
