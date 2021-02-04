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
    ERROR(500,"服务端异常");

    //登录消息


    public final Integer code;
    public final String msg;
}
