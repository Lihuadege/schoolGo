package com.li.schoolGo.service.impl;

import com.li.schoolGo.bean.CollectInfo;
import com.li.schoolGo.bean.CollectVo;
import com.li.schoolGo.mapper.CollectInfoMapper;
import com.li.schoolGo.mapper.CollectVoMapper;
import com.li.schoolGo.service.CollectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CollectInfoServiceImpl implements CollectInfoService {

    @Autowired
    CollectInfoMapper collectInfoMapper;

    @Autowired
    CollectVoMapper collectVoMapper;

    @Override
    public Boolean doUpdateCollect(String userId, String goodsId) {
        Example example = new Example(CollectInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("goodsId", goodsId);
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setStatus(1);
        int i = collectInfoMapper.updateByExampleSelective(collectInfo, example);
        if (i>0) {
            return true;
        }
        return false;
    }

    @Override
    public List<CollectVo> getCollectList(String userId) {
        List<CollectVo> collectList = collectVoMapper.getCollectList(userId);
        return collectList;
    }

    @Override
    public Boolean doCollect(String userId, String goodsId) {
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setStatus(1);
        collectInfo.setGoodsId(goodsId);
        collectInfo.setUserId(userId);
        collectInfo.setCreateTime(new Date());
        int i = collectInfoMapper.insertSelective(collectInfo);
        if (i>0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean doCancelCollect(String userId, String goodsId) {
        Example example = new Example(CollectInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("goodsId", goodsId);
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setStatus(0);
        int i = collectInfoMapper.updateByExampleSelective(collectInfo, example);
        if (i>0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean verifyCollect(String userId, String goodsId) {
        Example example = new Example(CollectInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("goodsId", goodsId);
        CollectInfo collectInfo = collectInfoMapper.selectOneByExample(example);
        if (collectInfo != null) {
            return true;
        }
        return false;
    }
}
