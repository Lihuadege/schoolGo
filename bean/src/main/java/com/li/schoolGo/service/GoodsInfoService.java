package com.li.schoolGo.service;

import com.li.schoolGo.bean.DetailGoods;
import com.li.schoolGo.bean.GoodsInfo;

import java.util.List;
import java.util.Map;

public interface GoodsInfoService {


    List<GoodsInfo> getGoodsInfoByCriteria(String userId, Integer pageNum, Integer pageSize, String title, String detailDesc, String categoryId, String status);

    Boolean removeGoods(List<String> checkId);

    boolean delGoodsById(String id);

    GoodsInfo getGoodsInfoById(String id, String sysUserId);

    Boolean updateGoodsWithoutImg(GoodsInfo goodsInfo);

    Boolean updateGoods(GoodsInfo goodsInfo, List<String> imgUrl);

    Map<String, Object> insertGoodsInfo(GoodsInfo goodsInfo);

    Map<String, Object> getGoodsInfo(Integer page);

    Map<String, Object> getGoodsInfoByCategoryId(Integer page, String categoryId);

    DetailGoods getGoodsInfoByGoodsId(String goodsId);
}
