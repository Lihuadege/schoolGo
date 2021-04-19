package com.li.schoolGo.bean;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "order_info")
public class OrderInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String orderId;

    private String userId;

    private String sysUserId;

    private String salerName;

    private String salerHeadImg;

    private String purchaserId;

    private String purchaserName;

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date expireTime;

    private String salerStatus;

    private String status;

    private String phoneNum;

    private String goodsId;

    //商品头图
    private String goodsHeadImg;

    private String goodsTitle;

    private String price;

    private Integer tradeWay;

    private Integer tradeNum;

    private String tradeAddr;

    private String tradeTime;

    private String orderNote;

}
