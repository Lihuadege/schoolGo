package com.li.schoolGo.service.impl;

import com.li.schoolGo.bean.GoodsImgInfo;
import com.li.schoolGo.mapper.GoodsImgInfoMapper;
import com.li.schoolGo.service.GoodsImgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsImgInfoServiceImpl implements GoodsImgInfoService {

    @Autowired
    GoodsImgInfoMapper goodsImgInfoMapper;

    @Override
    @Transactional
    public Boolean insertBatch(String newId, List<String> imgUrl) {
        int i = goodsImgInfoMapper.insertImgList(imgUrl, newId);
        if(i > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean delGoodsImg(GoodsImgInfo goodsImgInfo) {
        int i = goodsImgInfoMapper.delete(goodsImgInfo);
        if(i == 1){
            return true;
        }
        return false;
    }
}
