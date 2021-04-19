package com.li.schoolGo.service;

import com.li.schoolGo.bean.OrderInfo;

import java.util.List;

public interface OrderInfoService {
    List<OrderInfo> getOrderInfoListByCriteria(Integer pageNum, Integer pageSize, String goodsTitle, String status, String userId);

    Boolean batchDeliver(List<String> checkId);

    Boolean delOrder(List<String> checkId);

    Boolean insertNewOrder(OrderInfo orderInfo);

    List<OrderInfo> getOrderListByStatus(Integer currentPage, String status, String userId);

    Boolean confirmReceive(String orderId);

    Boolean userDeleteOrder(String orderId);

    Boolean cancelOrder(String orderId);
}
