package com.li.schoolGo.controller;

import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.OrderInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;


    @RequestMapping("delOrder")
    @ResponseBody
    public ResponseBean delOrder(@RequestBody List<String> checkId) {
        Boolean res = orderInfoService.delOrder(checkId);
        if(res){
            return ResponseBean.baseSuccess("删除成功");
        }else {
            return ResponseBean.baseError("删除失败");
        }
    }

    @RequestMapping("batchDeliver")
    @ResponseBody
    public ResponseBean batchDeliver(@RequestBody List<String> checkId) {
        Boolean res = orderInfoService.batchDeliver(checkId);
        if(res){
            return ResponseBean.baseSuccess("发货成功");
        }else {
            return ResponseBean.baseError("发货失败");
        }
    }

    @RequestMapping("listOrder")
    @ResponseBody
    public Map<String, Object> listOrder(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                                         HttpServletRequest request, String goodsTitle,
                                         String salerStatus) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null || userId.isEmpty()) {
            ResponseBean.baseError("未登录，不允许访问");
        }

        List<OrderInfo> orderInfoList = orderInfoService.getOrderInfoListByCriteria(pageNum, pageSize, goodsTitle, salerStatus, userId);

        PageInfo<OrderInfo> page = new PageInfo(orderInfoList);
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("data", orderInfoList);
        retMap.put("code", 0);
        retMap.put("msg", "获取成功");
        retMap.put("total", page.getTotal());

        return retMap;
    }


    @RequestMapping("toWaitSendingOrder")
    public String toOrderController() {
        return "manage/waitSendingOrder";
    }

    @RequestMapping("toReceivedOrder")
    public String toReceivedOrder() {
        return "manage/receivedOrder";
    }

    @RequestMapping("toCompleteOrder")
    public String toCompleteOrder() {
        return "manage/completeOrder";
    }

}
