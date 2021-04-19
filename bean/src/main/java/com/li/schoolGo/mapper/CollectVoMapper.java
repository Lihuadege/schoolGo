package com.li.schoolGo.mapper;

import com.li.schoolGo.bean.CollectVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CollectVoMapper extends Mapper<CollectVo> {

    List<CollectVo> getCollectList(@Param(value = "userId") String userId);

}
