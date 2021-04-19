package com.li.schoolGo.service;

import com.li.schoolGo.bean.GoodsImgInfo;

import java.util.List;

public interface GoodsImgInfoService {
    Boolean insertBatch(String newId, List<String> imgUrl);

    Boolean delGoodsImg(GoodsImgInfo goodsImgInfo);
}
