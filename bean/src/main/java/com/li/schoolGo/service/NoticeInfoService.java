package com.li.schoolGo.service;

import com.li.schoolGo.bean.NoticeInfo;

import java.util.ArrayList;
import java.util.List;

public interface NoticeInfoService {


    List<NoticeInfo> getAllNotice();

    NoticeInfo getOneNoticeById(String id);

    Boolean addNotice(NoticeInfo noticeInfo);

    Boolean delNotice(ArrayList<String> checkId);
}
