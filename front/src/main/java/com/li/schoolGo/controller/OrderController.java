package com.li.schoolGo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.*;
import com.li.schoolGo.service.OrderInfoService;
import com.li.schoolGo.service.SysUserService;
import com.li.schoolGo.service.UserInfoService;
import org.apache.poi.util.Removal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("userDeleteOrder")
    public ResponseBean userDeleteOrder(String orderId){
        if(orderId != null){
            Boolean res = orderInfoService.userDeleteOrder(orderId);
            if(res){
                return ResponseBean.baseSuccess("删除成功");
            }
            return ResponseBean.baseError("删除失败");
        }
        return ResponseBean.baseError("未知错误");
    }

    @RequestMapping("confirmReceive")
    public ResponseBean confirmReceive(String orderId){
        if(orderId != null){
            Boolean res = orderInfoService.confirmReceive(orderId);
            if(res){
                return ResponseBean.baseSuccess("收货成功");
            }
            return ResponseBean.baseError("收货失败");
        }
        return ResponseBean.baseError("未知错误");
    }

    @RequestMapping("cancelOrder")
    public ResponseBean cancelOrder(String orderId){
        if(orderId != null){
            Boolean res = orderInfoService.cancelOrder(orderId);
            if(res){
                return ResponseBean.baseSuccess("取消订单成功");
            }
            return ResponseBean.baseError("取消订单失败");
        }
        return ResponseBean.baseError("未知错误");
    }

    @RequestMapping("getOrderList")
    public Map<String, Object> getOrderList(@RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                                            String status, String userId) {
        //老规矩，前端不设置分页大小，有后面service层直接设置，不设置可扩展性
        List<OrderInfo> userOrderInfoList = orderInfoService.getOrderListByStatus(currentPage, status, userId);

        PageInfo<OrderInfo> pageInfo = new PageInfo<>(userOrderInfoList);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("code",0);
        retMap.put("msg","获取成功");
        retMap.put("total",pageInfo.getTotal());
        retMap.put("data",userOrderInfoList);
        return retMap;
    }

    //获取卖方的头像和名称
    @RequestMapping("getSalerInfo")
    public ResponseBean getSalerInfo(String userId,String sysUserId){
        HashMap<String, Object> retMap = new HashMap<>();
        if(userId == null){
            //获取的是商家信息
            SysUser sysUser = sysUserService.getSysUserById(sysUserId);
            retMap.put("salerName",sysUser.getUserName());
            retMap.put("salerHeadImg","http://localhost:8080" + sysUser.getHeadImg());
        }else{
            //获取个人信息
            UserInfo userInfo = userInfoService.getUserInfoById(userId);
            retMap.put("salerName",userInfo.getNickName());
            retMap.put("salerHeadImg",userInfo.getAvatarUrl());
        }
        return ResponseBean.baseSuccess("获取成功",retMap);
    }

    @RequestMapping("payAndCreateOrder")
    public ResponseBean payAndCreateOrder(OrderInfo orderInfo) {
//        System.out.println("新增订单数据：" + orderInfo +"\n");
//        此处不进行后台数据校验
        Boolean res = orderInfoService.insertNewOrder(orderInfo);

        if (res) {
            return ResponseBean.baseSuccess();
        }

        return ResponseBean.baseError();
    }

}
