package com.li.schoolGo.mapper;

import com.li.schoolGo.bean.GoodsImgInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsImgInfoMapper extends Mapper<GoodsImgInfo> {
    int insertImgList(@Param("imgUrl") List<String> imgUrl, @Param("goodsId") String goodsId);
}
