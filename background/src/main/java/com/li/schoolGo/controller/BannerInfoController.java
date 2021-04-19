package com.li.schoolGo.controller;

import com.li.schoolGo.constant.ImagePath;
import com.li.schoolGo.bean.BannerInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.BannerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
public class BannerInfoController {

    @Autowired
    BannerInfoService bannerInfoService;

    @Value("${li.schoolGo.ip}")
    String ip;

    /**
     * 简单的转发视图，到banner页面
     *
     * @return
     */
    @RequestMapping("toBannerList")
    public String toBannerList() {
        return "extend/listBanner";
    }

    /**
     * 获取banner图列表，简单的转发
     *
     * @return
     */
    @RequestMapping("listBanner")
    @ResponseBody
    public ResponseBean listBanner() {

        List<BannerInfo> bannerInfoList = bannerInfoService.getAllBanner();
        if (bannerInfoList.size() > 0) {
            return ResponseBean.baseSuccess("获取成功", bannerInfoList);
        } else {
            return ResponseBean.baseError("banner图为空");
        }
    }

    /**
     * banner图上传功能，由于banner数据库结构较为简单，去除了编辑功能
     *
     * @param file 上传的文件
     * @return
     */
    @RequestMapping("doAddBanner")
    @ResponseBody
    public ResponseBean doBannerAdd(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseBean.baseError("上传图片为空");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(new java.util.Date());
        String name = file.getOriginalFilename();
        int i = new Random().nextInt() * 4;

        String fileName = format + i + name;

        FileOutputStream fo = null;
        String relativePath = ImagePath.BANNER_IMG_RELATIVE_URL + "/" + fileName;
        try {
            URL path = ResourceUtils.getURL("classpath:");
            String classpath = URLDecoder.decode(path.getPath(), "UTF-8");

            File filePath = new File(classpath + "static" + ImagePath.BANNER_IMG_RELATIVE_URL);
            if (!filePath.exists()) {
                filePath.mkdir();
            }

            fo = new FileOutputStream(classpath + "static" + relativePath);

            fo.write(file.getBytes());

            BannerInfo bannerInfo = new BannerInfo();
            bannerInfo.setImgName(name);
            bannerInfo.setCreateTime(new Date(System.currentTimeMillis()));
            bannerInfo.setImgUrl(relativePath);
            bannerInfo.setRequestUrl(ip + relativePath);

            Boolean res = bannerInfoService.insertBannerInfo(bannerInfo);

            log.debug("banner图路径：" + classpath + "static" + relativePath);

            if (fo != null) fo.close();

            if (!res) {
                return ResponseBean.baseError("上传失败");
            }
        } catch (IOException e) {
            log.debug(e.getMessage());
            return ResponseBean.baseTransfer();
        }
        return ResponseBean.baseSuccess("上传成功");
    }

    /**
     * 删除banner视图的功能，批量删除和单独删除功能都可以
     *
     * @param checkId
     * @return
     */
    @RequestMapping("delBanner")
    @ResponseBody
    public ResponseBean delBanner(@RequestBody ArrayList<String> checkId) {
        System.out.println(checkId);
        Boolean res = bannerInfoService.delBanner(checkId);

        return ResponseBean.baseSuccess("成功");
    }

}
