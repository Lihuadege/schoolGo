package com.li.schoolGo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {
    private Integer code;
    private String msg;
    private Object data;
    
    public static ResponseBean baseSuccess(){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.code,ResponseBeanEnum.SUCCESS.msg,null);
    }

    public static ResponseBean baseSuccess(Object data){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.code,ResponseBeanEnum.SUCCESS.msg,data);
    }

    public static ResponseBean baseSuccess(String msg){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.code,msg,null);
    }

    public static ResponseBean baseSuccess(String msg, Object data){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.code,msg,data);
    }

    public static ResponseBean baseError(){
        return new ResponseBean(ResponseBeanEnum.ERROR.code,ResponseBeanEnum.ERROR.msg,null);
    }

    public static ResponseBean baseError(Object data){
        return new ResponseBean(ResponseBeanEnum.ERROR.code,ResponseBeanEnum.ERROR.msg,data);
    }

    public static ResponseBean baseError(String msg){
        return new ResponseBean(ResponseBeanEnum.ERROR.code,msg,null);
    }

    public static ResponseBean baseError(String msg, Object data){
        return new ResponseBean(ResponseBeanEnum.ERROR.code,msg,data);
    }
    
}
