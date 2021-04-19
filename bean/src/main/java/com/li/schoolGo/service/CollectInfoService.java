package com.li.schoolGo.service;

import com.li.schoolGo.bean.CollectVo;

import java.util.List;

public interface CollectInfoService {
    Boolean verifyCollect(String userId, String goodsId);

    Boolean doCollect(String userId, String goodsId);

    Boolean doCancelCollect(String userId, String goodsId);

    Boolean doUpdateCollect(String userId, String goodsId);

    List<CollectVo> getCollectList(String userId);
}
