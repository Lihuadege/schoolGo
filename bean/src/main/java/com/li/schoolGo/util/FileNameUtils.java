package com.li.schoolGo.util;

import com.li.schoolGo.constant.ImagePath;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class FileNameUtils {


    public static String getGoodsImgPath(String goodsId) {
        try {
            String classpath = ImagePath.getClassPath();
            classpath = classpath + "static" + ImagePath.GOODS_IMG_PATH + ImagePath.GOODS_IMG_DIRECTORY + goodsId;
            log.debug("商品id为：" + goodsId + "的商品图片所在系统路径为: " + classpath);
            return classpath;
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return null;
    }

    public static String getRealName(String fileName) {
        //生成文件名防止重复
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(new java.util.Date());
        int i = (int) Math.random() * 4;

        String finalFileName = format + i + fileName;

        return finalFileName;
    }

}
