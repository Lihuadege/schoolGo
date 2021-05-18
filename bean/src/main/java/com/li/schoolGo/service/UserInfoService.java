package com.li.schoolGo.service;

import com.li.schoolGo.bean.UserInfo;

public interface UserInfoService {
    UserInfo checkAndGetUserInfo(String openid);

    UserInfo getNewUserInfo(UserInfo userInfo);

    UserInfo getUserInfoById(String userId);

    Boolean updateSchoolIdByOpenId(String openId, String userSchoolId);

    UserInfo getUserINfoByOpenId(String openId);
}
