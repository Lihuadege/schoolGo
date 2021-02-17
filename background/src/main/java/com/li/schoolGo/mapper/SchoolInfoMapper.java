package com.li.schoolGo.mapper;

import com.li.schoolGo.bean.SchoolInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SchoolInfoMapper extends Mapper<SchoolInfo> {

    List<SchoolInfo> getSchoolInfoListByUserAreaId(Integer userAreaId);
//    SELECT a.id, a.parent_id, a.`name`
//    FROM school_info a JOIN (SELECT parent_id AS bid from school_info WHERE id=36) b
//    on b.bid = a.parent_id

}
