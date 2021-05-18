package com.li.schoolGo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.DetailGoods;
import com.li.schoolGo.bean.GoodsInfo;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolGo.bean.UserInfo;
import com.li.schoolGo.mapper.GoodsImgInfoMapper;
import com.li.schoolGo.mapper.GoodsInfoMapper;
import com.li.schoolGo.mapper.SysUserMapper;
import com.li.schoolGo.mapper.UserInfoMapper;
import com.li.schoolGo.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @Autowired
    GoodsImgInfoMapper goodsImgInfoMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Value("${index.pageSize}")
    Integer pageSize;

    /**
     * 根据商品id查询商品信息，包括商品的图片
     *
     * @param goodsId 商品id
     * @return
     */
    @Override
    public DetailGoods getGoodsInfoByGoodsId(String goodsId) {
        GoodsInfo goodsInfo = goodsInfoMapper.getGoodsInfoByGoodsId(goodsId);

        DetailGoods detailGoods = new DetailGoods();
        detailGoods.setGoodsInfo(goodsInfo);
        //因为还要获取商品对应的用户昵称和头像，但是商品是混合发布的，所以要进行判断
        if (goodsInfo.getSysUserId() == null) {
            String userId = goodsInfo.getUserId();
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
            detailGoods.setUserName(userInfo.getNickName());
            detailGoods.setCoverImg(userInfo.getAvatarUrl());
        } else if (goodsInfo.getUserId() == null) {
            String sysUserId = goodsInfo.getSysUserId();
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(sysUserId);
            detailGoods.setUserName(sysUser.getUserName());
            detailGoods.setCoverImg("http://localhost:8080" + sysUser.getHeadImg());
        } else {
            return null;
        }

        return detailGoods;
    }

    @Override
    public Map<String, Object> getGoodsInfoByCategoryId(Integer page, String categoryId, String schoolId) {
        //因为写到配置文件需要2个项目都写，因此直接在这里定义，避免麻烦，不过不利于扩展和更改配置
        Integer pageSize = 6;

        Example example = new Example(GoodsInfo.class);
        example.createCriteria().andEqualTo("status", 2)
                .andEqualTo("categoryId", categoryId)
        .andEqualTo("schoolId",schoolId);
        example.setOrderByClause("create_time desc");
        PageHelper.startPage(page, pageSize);
        List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectByExample(example);
        PageInfo<GoodsInfo> pageInfo = new PageInfo<>(goodsInfoList);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("code", 0);
        retMap.put("msg", "获取成功");
        retMap.put("data", goodsInfoList);
        retMap.put("total", pageInfo.getTotal());
        return retMap;
    }

    @Override
    @Transactional
    public Map<String, Object> insertGoodsInfo(GoodsInfo goodsInfo) {
        goodsInfo.setCreateTime(new Date(System.currentTimeMillis()));
        goodsInfo.setIsPersonal(0);
        goodsInfo.setId(null);
        int res = goodsInfoMapper.insertSelective(goodsInfo);
        String newId = goodsInfo.getId();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("res", res);
        resMap.put("newId", newId);
        return resMap;
    }

    @Override
    public Map<String, Object> getGoodsInfo(Integer page, String schoolId) {
        PageHelper.startPage(page, pageSize);
        Example example = new Example(GoodsInfo.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", "2");
        criteria.andEqualTo("schoolId",schoolId);
        List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectByExample(example);
        PageInfo<GoodsInfo> pageInfo = new PageInfo<>(goodsInfoList);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("code", 0);
        retMap.put("msg", "获取成功");
        retMap.put("data", goodsInfoList);
        retMap.put("total", pageInfo.getTotal());
        return retMap;
    }

    /**
     * 三步走战略，更新goodsInfo，删除其下图片url，重新填放-----作废
     * 重构编辑逻辑，直接更新，在插入
     *
     * @param goodsInfo
     * @param imgUrl
     * @return
     */
    @Override
    @Transactional
    public Boolean updateGoods(GoodsInfo goodsInfo, List<String> imgUrl) {
        int i = goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);
//        Example example = new Example(GoodsImgInfo.class);
//        example.createCriteria().andEqualTo("goodsId", goodsInfo.getId());
//        int n = goodsImgInfoMapper.deleteByExample(example);

        int m = goodsImgInfoMapper.insertImgList(imgUrl, goodsInfo.getId());

        if (m > 0 && i == 1) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public Boolean updateGoodsWithoutImg(GoodsInfo goodsInfo) {
        int i = goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Override
    public GoodsInfo getGoodsInfoById(String id, String sysUserId) {
        GoodsInfo goodsInfo = goodsInfoMapper.getGoodsInfoById(sysUserId, id);
        return goodsInfo;
    }

    @Override
    public boolean delGoodsById(String id) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setId(id);
        goodsInfo.setStatus(0);
        int i = goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean removeGoods(List<String> checkId) {
        Example example = new Example(GoodsInfo.class);
        example.createCriteria().andIn("id", checkId);
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setStatus(1);
        int i = goodsInfoMapper.updateByExampleSelective(goodsInfo, example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<GoodsInfo> getGoodsInfoByCriteria(String userId, Integer pageNum, Integer pageSize, String title, String detailDesc, String categoryId, String status) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        PageHelper.startPage(pageNum, pageSize);
        if (sysUser.getIsSuperManager() == 1) {
            return goodsInfoMapper.selectAll();
        } else {
            if (detailDesc != null && !detailDesc.isEmpty()) {
                detailDesc = '%' + detailDesc + '%';
            }
            if (title != null && !title.isEmpty()) {
                title = '%' + title + '%';
            }
            return goodsInfoMapper.getAllByCriteria(userId, title, detailDesc, categoryId, status);
        }
        //以下代码皆作废，使用通用Mapper的example判断条件，有的条件能走到，但是查询时加不上，也不知道是不是bug，还是用xml映射的好些
        //现在知道为什么不能用了，原因是没有使用同一个criteria
//        Example example = new Example(GoodsInfo.class);
//
//        if (title != null && !title.isEmpty()) {
//            example.createCriteria().andLike("title", "%" + title + "%");
//        }
//
//        if (detailDesc != null && !detailDesc.isEmpty()) {
//            example.createCriteria().andLike("detailDesc", "%" + detailDesc + "%");
//        }
//
//        //当categoryId是一个空串的时候，虽然也会拼接上条件，但因为不需要查询条件，相当于没有，我想多了
//        if(categoryId != null && categoryId != "0") {
//            example.createCriteria().andEqualTo("categoryId", categoryId);
//        }
//
//        //这里与上面不同，是因为要考虑到当商品状态为0时是软删除状态，不能让商户看到
//        if (status == null || status.equals("0")) {
//            example.createCriteria().andNotEqualTo("status",0);
//        }else {
//            example.createCriteria().andEqualTo("status",status);
//        }
//
//        example.createCriteria().andEqualTo("sysUserId",userId);
//
//        PageHelper.startPage(pageNum,pageSize);
//        List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectByExample(example);
//        return goodsInfoList;
    }
}
