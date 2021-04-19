package com.li.schoolGo.controller;

import com.li.schoolGo.bean.CollectVo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.CollectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectController {

    @Autowired
    CollectInfoService collectInfoService;

    @RequestMapping("getCollectList")
    public ResponseBean getCollectList(String userId){
        if(userId != null){
            List<CollectVo> collectVoList = collectInfoService.getCollectList(userId);
            return ResponseBean.baseSuccess("获取成功",collectVoList);
        }
        return ResponseBean.baseError("出错了，请重试");
    }

    //取消收藏
    @RequestMapping("doCancelCollect")
    public ResponseBean doCancelCollect(String userId, String goodsId){
        if(userId != null && goodsId != null) {
            Boolean res = collectInfoService.doCancelCollect(userId, goodsId);
            if(res){
                return ResponseBean.baseSuccess("已经收藏");
            }
        }
        return ResponseBean.baseError("没有收藏该商品");
    }

    //收藏商品
    @RequestMapping("doCollect")
    public ResponseBean doCollect(String userId, String goodsId){
        if(userId != null && goodsId != null) {
            //后端验证此条商品是否已经存在数据库，因为取消收藏是软删除
            Boolean isCollect = collectInfoService.verifyCollect(userId, goodsId);
            Boolean res;
            if(isCollect){
                //如果有记录，就更新status的值
                res = collectInfoService.doUpdateCollect(userId,goodsId);
            }else {
                //没有记录，就新增一条数据
                res = collectInfoService.doCollect(userId, goodsId);
            }
            if(res){
                return ResponseBean.baseSuccess("已经收藏");
            }
        }
        return ResponseBean.baseError("没有收藏该商品");
    }

    //验证商品是否被用户收藏
    @RequestMapping("verifyCollect")
    public ResponseBean verifyCollect(String userId, String goodsId){
        if(userId != null && goodsId != null) {
            Boolean res = collectInfoService.verifyCollect(userId, goodsId);
            if(res){
                return ResponseBean.baseSuccess("已经收藏");
            }
        }
        return ResponseBean.baseError("没有收藏该商品");
    }

}
