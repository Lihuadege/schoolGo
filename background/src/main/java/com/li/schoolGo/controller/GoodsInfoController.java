package com.li.schoolGo.controller;

import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.CategoryInfo;
import com.li.schoolGo.bean.GoodsImgInfo;
import com.li.schoolGo.bean.GoodsInfo;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.service.CategoryInfoService;
import com.li.schoolGo.service.GoodsImgInfoService;
import com.li.schoolGo.service.GoodsInfoService;
import com.li.schoolGo.util.FileNameUtils;
import com.li.schoolGo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class GoodsInfoController {

    @Autowired
    CategoryInfoService categoryInfoService;

    @Autowired
    GoodsInfoService goodsInfoService;

    @Autowired
    GoodsImgInfoService goodsImgInfoService;

//    @Autowired
//    RedisUtil redisUtil;

    /**
     * 前台删除图片功能；因为在磁盘删除图片，有可能是goodsInfo表里的coverImg失效，因此进行软删除
     *
     * @param goodsImgInfo
     * @return
     */
    @RequestMapping("delImg")
    @ResponseBody
    public ResponseBean delImg(GoodsImgInfo goodsImgInfo) {
        System.out.println(goodsImgInfo);
        Boolean res = goodsImgInfoService.delGoodsImg(goodsImgInfo);
        if (res) {
            return ResponseBean.baseSuccess("删除成功");
        }
        return ResponseBean.baseSuccess("删除失败");
    }

    @RequestMapping("doAddGoods")
    @ResponseBody
    public ResponseBean doAddGoods(MultipartFile[] file, GoodsInfo goodsInfo, HttpServletRequest request) {

        String sysUserId = (String) request.getAttribute("userId");
        goodsInfo.setSysUserId(sysUserId);
        //上传逻辑：既然不是伪多图片上传，可以一次性传入所有图片，可以考虑如下逻辑
        //1、建一个关于该商品的单独的文件夹
        //2、将所有file图片保存到该文件夹下，并添加到一个ArrayList中
        //关于新增和修改的问题： 新增没问题，
        //修改是回显到前端页面，单独定制函数，单个点击发送ajax请求到后台进行删除，若不添加新的图片，要考虑到file为null的情况，
        log.debug("要修改或者添加商品是：" + goodsInfo + "---  id是" + goodsInfo.getId());


        if (StringUtils.equals(goodsInfo.getId(), "0")) {
            //此判断，是为新增
            //新增逻辑：先判断file数组是否为空，不为空开始逻辑判断：
            //考虑问题，要先写入服务器，还是先保存商品信息到数据库？先新增，再写入服务器，因为需要一个id作为路径

            // 处理图片上传
            if (file == null || file.length == 0) {
                return ResponseBean.baseError("上传错误或者需要添加商品图片");
            }

            Map<String, Object> res = goodsInfoService.insertGoodsInfo(goodsInfo);
            if ((int) res.get("res") != 1) {
                return ResponseBean.baseError("新增失败，请重试");
            }

            String newId = (String) res.get("newId");
            String goodsImgPath = FileNameUtils.getGoodsImgPath(newId);

            //判断该商品文件夹是否存在，不存在，创建之
            File directory = new File(goodsImgPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

            //保存新写入图片访问url地址的集合
            String visitPath = StringUtils.substringAfter(goodsImgPath, "static");

            //将图片写入对应的文件夹，即磁盘，并将该图片的访问路径保存在集合里
            FileOutputStream fo = null;
            List<String> imgUrl = new ArrayList<>();
            try {
                for (int i = 0; i < file.length; i++) {
                    //修改

                    String realName = FileNameUtils.getRealName(file[i].getOriginalFilename());
                    fo = new FileOutputStream(goodsImgPath + "/" + realName);

                    fo.write(file[i].getBytes());

                    //把拼接好的图片访问路径存入该集合
                    imgUrl.add(visitPath + "/" + realName);
                }
                if (fo != null) fo.close();
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
            Boolean result = goodsImgInfoService.insertBatch(newId, imgUrl);
            GoodsInfo newOldGoods = new GoodsInfo();
            newOldGoods.setId(newId);
            newOldGoods.setCoverImg(imgUrl.get(0));
            //头图设置，不太重要，忽略
            goodsInfoService.updateGoodsWithoutImg(newOldGoods);
            if (result) {
                return ResponseBean.baseSuccess("新增成功");
            }
            return ResponseBean.baseError("新增失败");

        } else if (!goodsInfo.getId().isEmpty()) {
            //此判断，是为修改
            //修改商品功能，首先判断前台传进来的file数组是否为空，如果为空，就说明没有添加新的图片，直接修改商品信息即可
            Boolean res = false;
            if (file == null || file.length == 0) {
                //传进来的file数组为空，直接修改商品信息
                res = goodsInfoService.updateGoodsWithoutImg(goodsInfo);
            } else {
                //有新增的图片，需要进行修改（删除有单独的功能做，不用担心删除的图片
                String id = goodsInfo.getId();
                String goodsImgPath = FileNameUtils.getGoodsImgPath(id);

                //判断该商品文件夹是否存在，不存在，创建之
                File directory = new File(goodsImgPath);
                if (!directory.exists()) {
                    directory.mkdir();
                }

                //保存新写入图片访问url地址的集合
                String visitPath = StringUtils.substringAfter(goodsImgPath, "static");

                //将图片写入对应的文件夹，即磁盘，并将该图片的访问路径保存在集合里
                FileOutputStream fo = null;
                List<String> imgUrl = new ArrayList<>();
                try {
                    for (int i = 0; i < file.length; i++) {
                        //修改

                        String realName = FileNameUtils.getRealName(file[i].getOriginalFilename());
                        fo = new FileOutputStream(goodsImgPath + "/" + realName);

                        fo.write(file[i].getBytes());

                        //把拼接好的图片访问路径存入该集合
                        imgUrl.add(visitPath + "/" + realName);
                    }
                    if (fo != null) fo.close();
                } catch (IOException e) {
                    log.debug(e.getMessage());
                }
                //设置头图直接上传一个新图片即可，因为一般商品是不怎么进行图片修改的
                goodsInfo.setCoverImg(imgUrl.get(0));
                res = goodsInfoService.updateGoods(goodsInfo, imgUrl);
            }
            if (res) {
                return ResponseBean.baseSuccess("修改成功");
            }
            return ResponseBean.baseError("修改失败");
        } else {
            return ResponseBean.baseError("操作失败，请重试");
        }

//        原逻辑
//                处理商品新增
//        String sysUserId = (String) request.getAttribute("userId");
//        goodsInfo.setSysUserId(sysUserId);
//        log.debug("要修改或者添加商品是：" + goodsInfo + "---  key是：" + key + "---  id是" + goodsInfo.getId());
//        Jedis jedis = redisUtil.getRedis();
//        if (jedis == null) {
//            return ResponseBean.baseError("出错了，请重试");
//        }
//        if (goodsInfo.getId().equals("0") && key != "0" && !key.isEmpty()) {
//            //此判断，是为新增
//            List<String> imgUrl = jedis.lrange(ImagePath.GOODS_IMG_LIST_KEY + key, 0, -1);
//            if (imgUrl.size() == 0) {
//                return ResponseBean.baseError("请添加至少一张图片");
//            }
//            Boolean res = goodsInfoService.insertGoodsInfo(goodsInfo, imgUrl);
//
//            jedis.del(ImagePath.GOODS_IMG_LIST_KEY + key);
//            return ResponseBean.baseSuccess();
//        } else if (goodsInfo.getId() != "0" && !goodsInfo.getId().isEmpty() && key.equals("0")) {
//            //此判断，是为修改
//            String selectKey = ImagePath.GOODS_IMG_LIST_KEY + goodsInfo.getId();
//            List<String> imgUrl = jedis.lrange(selectKey, 0, -1);
//            if (imgUrl.size() == 0) {
//                //获取到的图片url集合为空，证明没有操作图片，直接修改即可
//                Boolean res = goodsInfoService.updateGoodsWithoutImg(goodsInfo);
//            } else {
//                Boolean res = goodsInfoService.updateGoods(goodsInfo, imgUrl);
//            }
//            jedis.del(selectKey);
//            return ResponseBean.baseSuccess();
//        } else {
//            return ResponseBean.baseError("操作失败，请重试");
//        }
    }

    @RequestMapping("toAddGoods")
    public String toAddGoods(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object categoryList = session.getAttribute("categoryInfo");
        if (categoryList == null) {
            List<CategoryInfo> categoryInfo = categoryInfoService.getAll();
            session.setAttribute("categoryInfo", categoryInfo);
        }
        String userId = (String) request.getAttribute("userId");

        if (userId == null) {
            return "tips/error";
        }

        if (id != null) {
            GoodsInfo goodsInfo = goodsInfoService.getGoodsInfoById(id, userId);
            if (goodsInfo != null) request.setAttribute("goodsInfo", goodsInfo);
        } else {
            String key = System.currentTimeMillis() + userId;
            request.setAttribute("key", key);
        }
        return "manage/editGoods";
    }

    @RequestMapping("delGoods")
    @ResponseBody
    public ResponseBean delGoods(String id) {
        boolean res = false;
        if (id != null) {
            res = goodsInfoService.delGoodsById(id);
        }
        if (res) {
            return ResponseBean.baseSuccess("删除商品成功");
        } else {
            return ResponseBean.baseError("删除商品失败");
        }
    }

    @RequestMapping("removeGoods")
    @ResponseBody
    public ResponseBean removeGoods(@RequestBody List<String> checkId) {
        Boolean res = goodsInfoService.removeGoods(checkId);
        if (res) {
            return ResponseBean.baseSuccess("下架成功");
        } else {
            return ResponseBean.baseError("下架失败");
        }
    }

    /**
     * 获取商户所属的所有商品
     *
     * @param pageNum    分页数据，第几页
     * @param pageSize   分页数据，每页多少
     * @param request    request对象
     * @param title      商品名称
     * @param detailDesc 商品详细描述
     * @param categoryId 商品类别id
     * @param status     商品状态
     * @return
     */
    @RequestMapping("listGoods")
    @ResponseBody
    public Map<String, Object> listGoods(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                                         HttpServletRequest request, String title, String detailDesc, String categoryId, String status) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null || userId.isEmpty()) {
            ResponseBean.baseError("未登录，不允许访问");
        }
        //访问带有4个查询条件的参数，可能不都有。在service层判断可能为空的参数
        //提醒自己，虽然可能有空现象，但是categoryId 和 status 《《不允许》》 为空，但可能为0，需要注意，前台先判断为不为空安全一些
        //不对，一开始这里是没有参数的，不能直接拦截返回
//        if(categoryId.isEmpty() || categoryId == null || status.isEmpty() || status ==null){
//            ResponseBean.baseError("获取失败");
//        }
//做完非空校验，再进行查询

        //已在xml中进行映射，此处不需要
        List<GoodsInfo> goodsInfoList = goodsInfoService.getGoodsInfoByCriteria(userId, pageNum, pageSize, title, detailDesc, categoryId, status);

        HashMap<String, Object> map = new HashMap<>();
        if (goodsInfoList.size() > 0) {
            PageInfo<GoodsInfo> pageInfo = new PageInfo<>(goodsInfoList);
            map.put("count", pageInfo.getTotal());
        }

        map.put("code", 0);
        map.put("msg", "获取成功");
        map.put("data", goodsInfoList);

        return map;
    }

    @RequestMapping("toGoodsList")
    public String toGoodsList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object categoryList = session.getAttribute("categoryInfo");
        if (categoryList == null) {
            List<CategoryInfo> categoryInfo = categoryInfoService.getAll();
            session.setAttribute("categoryInfo", categoryInfo);
        }
        return "manage/listGoods";
    }

}
