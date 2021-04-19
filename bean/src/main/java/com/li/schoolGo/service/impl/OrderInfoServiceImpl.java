package com.li.schoolGo.service.impl;

import com.github.pagehelper.PageHelper;
import com.li.schoolGo.bean.OrderInfo;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolGo.mapper.OrderInfoMapper;
import com.li.schoolGo.mapper.SysUserMapper;
import com.li.schoolGo.service.OrderInfoService;
import com.li.schoolGo.util.OrderIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    @Transactional
    public Boolean userDeleteOrder(String orderId) {
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus("4");
        int i = orderInfoMapper.updateByExampleSelective(orderInfo, example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean confirmReceive(String orderId) {
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus("2");
        orderInfo.setSalerStatus("2");
        int i = orderInfoMapper.updateByExampleSelective(orderInfo, example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean cancelOrder(String orderId) {
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus("3");
        orderInfo.setSalerStatus("3");
        int i = orderInfoMapper.updateByExampleSelective(orderInfo, example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<OrderInfo> getOrderListByStatus(Integer currentPage, String status, String userId) {
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", status);
        criteria.andEqualTo("purchaserId", userId);
        PageHelper.startPage(currentPage, 10);
        List<OrderInfo> userOrderInfoList = orderInfoMapper.selectByExample(example);

        return userOrderInfoList;
    }

    @Override
    @Transactional
    public Boolean insertNewOrder(OrderInfo orderInfo) {
        String id = OrderIdUtil.getOrderId();

        Date date = new Date();
        orderInfo.setOrderId(id);
        orderInfo.setCreateTime(date);
        orderInfo.setStatus("0");
        orderInfo.setSalerStatus("0");

        int res = orderInfoMapper.insertSelective(orderInfo);
        if (res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean delOrder(List<String> checkId) {
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", checkId);
        OrderInfo orderInfo = new OrderInfo();
        //4代表订单状态已删除
        orderInfo.setSalerStatus("4");
        int i = orderInfoMapper.updateByExampleSelective(orderInfo, example);
        if (i >= 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean batchDeliver(List<String> checkId) {
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", checkId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setSalerStatus("1");
        orderInfo.setStatus("1");
        int i = orderInfoMapper.updateByExampleSelective(orderInfo, example);
        if (i >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<OrderInfo> getOrderInfoListByCriteria(Integer pageNum, Integer pageSize, String goodsTitle, String status, String userId) {
        //因为status必定有默认值，所以不需要判断
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("salerStatus", status);

        //校验是否是超级管理员
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (!sysUser.getIsSuperManager().equals("1")) {
            criteria.andEqualTo("sysUserId", userId);
        }

        //如果goodsTitle不为空，说明有查询条件
        if (!StringUtils.isEmpty(goodsTitle) && !goodsTitle.equals("")) {
            criteria.andLike("goodsTitle", "%" + goodsTitle + "%");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectByExample(example);

        return orderInfoList;
    }
}
