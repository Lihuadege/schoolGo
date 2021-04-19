package com.li.schoolGo.bean;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "user_info")
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String openId;

    private String nickName;

    private String gender;

    private String personalWords;

    private String schoolId;

    private String phoneNum;

    private String email;

    /*
    由于从前端获取的用户授权信息中是有一个province值的，可以根据城市名称获取学校列表（当然也可以让用户切换省）
    判断是否是新用户，如果是新用户，需要用户选择学校；如果是已授权注册的用户，则不需要选择
     */
    private String detailArea;

    private String avatarUrl;

}
