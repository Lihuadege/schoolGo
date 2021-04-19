package com.li.schoolGo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.CommentInfo;
import com.li.schoolGo.mapper.CommentInfoMapper;
import com.li.schoolGo.service.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentInfoServiceImpl implements CommentInfoService {

    @Autowired
    CommentInfoMapper commentInfoMapper;

    @Override
    public Boolean delComment(String checkId) {
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setId(checkId);
        commentInfo.setStatus(0);

        int i = commentInfoMapper.updateByPrimaryKeySelective(commentInfo);
        if(i == 1){
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getAllComment(Integer pageNum, Integer pageSize, String userNickName, String articleTitle) {

        Example example = new Example(CommentInfo.class);

        if (!StringUtils.isEmpty(userNickName)) {
            example.createCriteria().andLike("userNickName", "%" + userNickName + "%");
        }
        if (!StringUtils.isEmpty(articleTitle)) {
            example.createCriteria().andLike("phoneNum", "%" + articleTitle + "%");
        }

        example.createCriteria().andEqualTo("status", 1);

        PageHelper.startPage(pageNum, pageSize);
        List<CommentInfo> commentInfos = commentInfoMapper.selectByExample(example);

        System.out.println(commentInfos);

        HashMap<String, Object> resultMap = new HashMap<>();
        PageInfo<CommentInfo> page = new PageInfo<>(commentInfos);

        resultMap.put("count", page.getTotal());

        if (commentInfos.size() != 0) {
            resultMap.put("data", commentInfos);
            resultMap.put("msg","获取成功");
        }else {
            resultMap.put("msg", "获取失败，查询为空");
        }
        return resultMap;

    }
}
