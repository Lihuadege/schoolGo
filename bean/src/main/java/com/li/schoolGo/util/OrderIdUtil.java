package com.li.schoolGo.util;

import java.util.UUID;

public class OrderIdUtil {

    public static String getOrderId(){
        String pre = UUID.randomUUID().toString();
        pre = pre.replace("-","").substring(7, 14);

        String suf = String.valueOf(System.currentTimeMillis());

        return pre+suf;

    }

}
