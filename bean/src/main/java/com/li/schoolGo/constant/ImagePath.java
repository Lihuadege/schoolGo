package com.li.schoolGo.constant;

import org.springframework.util.ResourceUtils;

import java.net.URL;
import java.net.URLDecoder;

public class ImagePath {

    public static final String HEAD_IMG_PATH = "/images/headImg";

    public static final String BANNER_IMG_RELATIVE_URL ="/images/banner";

    public static final String GOODS_IMG_PATH = "/images/goods_img/";

    public static final String ABSOLUTE_PATH = "absolutePath";
    public static final String RELATIVE_PATH_FILE = "relativePathFile";
    public static final String FINAL_FILE_NAME = "finalFileName";
    public static final String CREATE_DIR_PATH = "createDirPath";

    public static final String GOODS_IMG_LIST_KEY = "createDirKey";
    public static final String GOODS_IMG_DIRECTORY = "goodsKey";

    public static String getClassPath(){
        try {
            URL path = ResourceUtils.getURL("classpath:");
            String classpath = URLDecoder.decode(path.getPath(), "UTF-8");
            return classpath;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
