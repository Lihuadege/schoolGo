import requests from "/util/request"
App({
    onLaunch() {
        //程序加载，后台获取用户code信息发送到后端验证返回用户openId
        let token = wx.getStorageSync("token");

        if (!wx.getStorageSync("isLogin")) {
            wx.setStorageSync("isLogin", false);
        }

        if (!token) {
            wx.login({
                //当用户刚进入的时候login，获取用户openid，存入数据库
                //此时，数据库存入用户的openid，但还不算登录，需要在真正需要登录的地方，让用户授权，获取用户数据
                //获取玩用户数据之后，传入后台，保存到数据库里面，完善用户信息
                //考虑需要存入用户缓存数据：token？或者登录状态什么的？
                timeout: 10000,
                success: (result) => {
                    console.log(result);
                    let codes = result.code;
                    //打开应用向服务器请求用户数据，返回用户信息
                    //不论数据库是否存在过用户，都应该返回一个userInfo或者空的user对象，存入缓存
                    requests("/auth", { code: codes }).then(res => {
                        let token = res.data.token
                        wx.setStorageSync("token", token);
                        this.globalData.token = token
                        let userInfo = res.data.user;
                        if (userInfo) {
                            wx.setStorageSync("isLogin", true)
                        }
                        wx.setStorageSync("userInfo", userInfo);
                    })
                }
            });
        }
    },
    globalData: {
        token: undefined
    }
})