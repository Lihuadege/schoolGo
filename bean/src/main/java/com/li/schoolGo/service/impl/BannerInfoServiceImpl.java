package com.li.schoolGo.service.impl;

import com.li.schoolGo.bean.BannerInfo;
import com.li.schoolGo.mapper.BannerInfoMapper;
import com.li.schoolGo.service.BannerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class BannerInfoServiceImpl implements BannerInfoService {
    @Autowired
    BannerInfoMapper bannerInfoMapper;

    @Override
    @Transactional
    public Boolean insertBannerInfo(BannerInfo bannerInfo) {
        int res = bannerInfoMapper.insertSelective(bannerInfo);
        if(res == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean delBanner(ArrayList<String> checkId) {
        Example example = new Example(BannerInfo.class);
        example.createCriteria().andIn("id", checkId);
        BannerInfo bannerInfo = new BannerInfo();
        bannerInfo.setStatus(0);
        bannerInfo.setDeleteTime(new Date(System.currentTimeMillis()));
        int resNum = bannerInfoMapper.updateByExampleSelective(bannerInfo, example);
        if (resNum > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<BannerInfo> getAllBanner() {
        Example example = new Example(BannerInfo.class);
        example.createCriteria().andEqualTo("status", 1);
        List<BannerInfo> bannerInfoList = bannerInfoMapper.selectByExample(example);
        return bannerInfoList;
    }


}
