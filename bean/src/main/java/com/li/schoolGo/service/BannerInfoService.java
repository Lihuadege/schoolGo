package com.li.schoolGo.service;

import com.li.schoolGo.bean.BannerInfo;

import java.util.ArrayList;
import java.util.List;

public interface BannerInfoService {
    List<BannerInfo> getAllBanner();

    Boolean delBanner(ArrayList<String> checkId);

    Boolean insertBannerInfo(BannerInfo bannerInfo);
}
