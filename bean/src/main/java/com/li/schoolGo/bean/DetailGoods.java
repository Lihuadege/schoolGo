package com.li.schoolGo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailGoods {

    private GoodsInfo goodsInfo;

    private String userName;

    private String coverImg;

}
