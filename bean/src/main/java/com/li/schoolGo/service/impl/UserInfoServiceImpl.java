package com.li.schoolGo.service.impl;

import com.li.schoolGo.bean.UserInfo;
import com.li.schoolGo.mapper.UserInfoMapper;
import com.li.schoolGo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoById(String userId) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        return userInfo;
    }

    /**
     * 根据openid更新用户信息，并且再次查询
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo getNewUserInfo(UserInfo userInfo) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("openId",userInfo.getOpenId());
        userInfoMapper.updateByExampleSelective(userInfo,example);

        UserInfo user = userInfoMapper.selectOneByExample(example);
        //设置用户openId为null，不让用户看到自己的openId
        user.setOpenId(null);

        return user;
    }

    @Override
    public UserInfo checkAndGetUserInfo(String openid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openid);
        UserInfo user = userInfoMapper.selectOne(userInfo);
        if(user == null){
            userInfoMapper.insertSelective(userInfo);
            return userInfo;
        }
        return user;
    }
}
