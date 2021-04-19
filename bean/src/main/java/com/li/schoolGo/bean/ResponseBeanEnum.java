package com.li.schoolGo.bean;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ResponseBeanEnum {

    //通用消息
    SUCCESS(0,"success"),
    ERROR(500,"服务端异常"),

    TRANSFER_ERROR(3,"出错了");


    public final Integer code;
    public final String msg;
}
