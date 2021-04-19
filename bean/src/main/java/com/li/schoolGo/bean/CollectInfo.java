package com.li.schoolGo.bean;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

//收藏商品表
@Data
@Table(name = "collect_info")
public class CollectInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String goodsId;

    private String userId;

    private Date createTime;

    private Integer status;

}