package com.li.schoolGo.mapper;

import com.li.schoolGo.bean.GoodsInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsInfoMapper extends Mapper<GoodsInfo> {
    List<GoodsInfo> getAllByCriteria(@Param("userId") String userId, @Param("title") String title, @Param("detailDesc") String detailDesc, @Param("categoryId") String categoryId, @Param("status") String status);

    GoodsInfo getGoodsInfoById(@Param("sysUserId") String sysUserId, @Param("id") String id);

    GoodsInfo getGoodsInfoByGoodsId(String goodsId);
}
