package com.li.schoolGo.controller;

import com.alibaba.fastjson.JSON;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.bean.UserInfo;
import com.li.schoolGo.bean.WxLogin;
import com.li.schoolGo.service.UserInfoService;
import com.li.schoolGo.util.HttpClientUtil;
import com.li.schoolGo.util.JwtUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("verifyOld")
    public ResponseBean verifyOld(String token){
        String openId = JwtUtil.getOpenId(token);
        UserInfo userInfo = userInfoService.getUserINfoByOpenId(openId);
        if(userInfo.getSchoolId() != null){
            return ResponseBean.baseSuccess("已存在该用户",userInfo.getSchoolId());
        }
        return ResponseBean.baseError("是新用户");
    }

    @RequestMapping("insertSchool")
    public ResponseBean insertSchool(String token, String userSchoolId) {
        String openId = JwtUtil.getOpenId(token);
        Boolean res = userInfoService.updateSchoolIdByOpenId(openId, userSchoolId);
        if (res) {
            return ResponseBean.baseSuccess("更新成功");
        }
        return ResponseBean.baseError();
    }

    /**
     * 当前端获取到信息，发送后端更新数据
     *
     * @param userInfo
     * @param token
     * @return
     */
    @RequestMapping("userLogin")
    public ResponseBean userLogin(UserInfo userInfo, String token) {
        System.out.println(userInfo + "   " + token);
        /*
        根据前端传入的信息，解码token获得用户的openid，查询数据库
        此时有两种情况：
            1、这是一个新用户，以前没在数据库里面注册过，表里对应的id只有一个openid，根据传入userInfo更新用户信息，并返回最新的
            2、老用户，注册过，也更新用户信息，并返回
         */
        Map<String, Object> map = JwtUtil.decode(token, "schoolGoUserOpenId");
        String openId = (String) map.get("openId");
        userInfo.setOpenId(openId);
        UserInfo user = userInfoService.getNewUserInfo(userInfo);

        return ResponseBean.baseSuccess("获取成功", user);
    }

    @RequestMapping("auth")
    public ResponseBean auth(String code) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //此处踩坑无数，首先设置get请求参数一开始用httpGet.setHeader()方法，但是行不通，改成url拼接
        //但是又发现{"errcode":40029,"errmsg":"invalid code, hints: [ req_id: cheB36yFe-tD2h0a ]"}错误，结果是测试小程序的appid与应用的appid不一致
        //保护隐私，删除appid和secretid
        HttpGet httpGet =
                new HttpGet("https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&js_code=" +
                        code);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);

            WxLogin wxLogin = JSON.parseObject(res, WxLogin.class);

            HashMap<String, Object> retMap = new HashMap<>();

            //服务器获取用户的openid之后，需要检查数据库，看用户是否存在于数据库之中
            //若存在，返回用户信息
            // 若不存在，返回空的用户,并插入一个新的用户，除openId外都是null
            UserInfo user = userInfoService.checkAndGetUserInfo(wxLogin.getOpenid());

            //处理wxLogin中的openId，返回给前端一个token，当前端过来验证的时候，可以进行判断
            HashMap<String, Object> param = new HashMap<>();
            param.put("openId", wxLogin.getOpenid());
            String token = JwtUtil.encode("schoolGoUserOpenId", param);

            retMap.put("token", token);
            retMap.put("user", user);

            if (response != null) response.close();
            return ResponseBean.baseSuccess("获取成功", retMap);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return ResponseBean.baseError("获取失败");
    }
}

