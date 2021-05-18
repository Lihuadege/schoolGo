package com.li.schoolGo.controller;

import com.li.schoolGo.bean.BannerInfo;
import com.li.schoolGo.bean.GoodsInfo;
import com.li.schoolGo.bean.NoticeInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.BannerInfoService;
import com.li.schoolGo.service.GoodsInfoService;
import com.li.schoolGo.service.NoticeInfoService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    BannerInfoService bannerInfoService;

    @Autowired
    NoticeInfoService noticeInfoService;

    @Autowired
    GoodsInfoService goodsInfoService;

    @RequestMapping("getBanners")
    public ResponseBean getBanners() {
        List<BannerInfo> allBanner = bannerInfoService.getAllBanner();
        return new ResponseBean().baseSuccess("获取成功", allBanner);
    }

    @RequestMapping("getNotice")
    public ResponseBean getNotice() {
        List<NoticeInfo> allNotice = noticeInfoService.getAllNotice();
        return ResponseBean.baseSuccess("获取成功", allNotice);
    }

    @RequestMapping("listGoods")
    public Map<String, Object> listGoods(@RequestParam(value = "page", defaultValue = "1")Integer page, String schoolId){
        Map<String, Object> retMap = goodsInfoService.getGoodsInfo(page,schoolId);
        return retMap;
    }


    /**
     * 公告详情页数据获取，太简单，不值得新建一个类，所以放在这里
     * @param id
     * @return
     */
    @RequestMapping("toDetailNotice")
    public ResponseBean toDetailNotice(@RequestParam(value = "id", required = true) String id) {
        NoticeInfo notice = noticeInfoService.getOneNoticeById(id);
        return ResponseBean.baseSuccess("获取成功", notice);
    }

}
