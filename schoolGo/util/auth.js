import requests from "../util/request"

//检查用户是否登录
function checkIsLogin() {
    let flag = wx.getStorageSync("isLogin");

    //flage为false或者不存在，都是未登录状态
    if (!flag || flag == false) {
        return false;
    } else {
        return true;
    }
}

//登录功能
function userLogin(desc = '要获取您的一些信息，请授权') {
    return new Promise((resolve, reject) => {
        wx.getUserProfile({
            desc: desc,
            lang: 'zh_CN',
            success: (res) => {
                //用户点击授权之后，需要修改登录状态
                wx.setStorageSync("isLogin", true);
                var userInfo = res.userInfo;

                let avatarUrl = userInfo.avatarUrl;
                let gender = userInfo.gender;
                let nickName = userInfo.nickName;
                let province = userInfo.province;

                let token = wx.getStorageSync("token");

                requests("/userLogin", {
                    token: token,
                    avatarUrl: avatarUrl,
                    gender: gender,
                    nickName: nickName,
                    province: province
                }).then(result => {
                    //因为此时数据库里必定存在用户的信息，所以必定有用户信息返回
                    userInfo = result.data;
                    wx.setStorageSync("userInfo", userInfo);
                    resolve(true);
                })
            },
            fail: (err) => {
                //用户拒绝之后
                wx.showToast({
                    title: '亲，不授权不能继续操作哦',
                    duration: 1000,
                    icon: 'none'
                });
                reject(false)
            }
        })
    })
}

module.exports = {
    checkIsLogin: checkIsLogin,
    userLogin: userLogin,
}