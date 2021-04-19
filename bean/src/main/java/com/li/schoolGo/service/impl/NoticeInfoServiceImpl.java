package com.li.schoolGo.service.impl;

import com.li.schoolGo.bean.NoticeInfo;
import com.li.schoolGo.mapper.NoticeInfoMapper;
import com.li.schoolGo.service.NoticeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeInfoServiceImpl implements NoticeInfoService {
    @Autowired
    NoticeInfoMapper noticeInfoMapper;


    @Override
    public Boolean delNotice(ArrayList<String> checkId) {
        Example example = new Example(NoticeInfo.class);
        example.createCriteria().andIn("id", checkId);
        NoticeInfo noticeInfo = new NoticeInfo();
        noticeInfo.setStatus(0);
        int i = noticeInfoMapper.updateByExample(noticeInfo, example);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean addNotice(NoticeInfo noticeInfo) {
        int i = 0;
        if (noticeInfo.getId() == null || noticeInfo.getId().isEmpty()) {
            noticeInfo.setId(null);
            noticeInfo.setCreateTime(new Date(System.currentTimeMillis()));
            i = noticeInfoMapper.insertSelective(noticeInfo);
        } else {
            i = noticeInfoMapper.updateByPrimaryKeySelective(noticeInfo);
        }
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Override
    public NoticeInfo getOneNoticeById(String id) {
        NoticeInfo noticeInfo = noticeInfoMapper.selectByPrimaryKey(id);
        return noticeInfo;
    }

    @Override
    public List<NoticeInfo> getAllNotice() {
        Example example = new Example(NoticeInfo.class);
        example.createCriteria().andEqualTo("status", 1);
        List<NoticeInfo> noticeInfos = noticeInfoMapper.selectByExample(example);
        return noticeInfos;
    }
}
