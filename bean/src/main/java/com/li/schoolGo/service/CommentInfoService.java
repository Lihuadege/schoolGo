package com.li.schoolGo.service;

import java.util.Map;

public interface CommentInfoService {
    Map<String, Object> getAllComment(Integer pageNum, Integer pageSize, String userNickName, String articleTitle);

    Boolean delComment(String checkId);
}
