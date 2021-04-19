import requests from "../../util/request"
import auth from "../../util/auth"
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        hasUserInfo: false,
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        //当用户刚进入的时候login，获取用户openid，存入数据库
        //此时，数据库存入用户的openid，但还不算登录，需要在真正需要登录的地方，让用户授权，获取用户数据
        //获取玩用户数据之后，传入后台，保存到数据库里面，完善用户信息
        //考虑需要存入用户缓存数据：token？或者登录状态什么的？

        let res = auth.checkIsLogin();
        if (res) {
            this.setData({
                hasUserInfo: true,
                userInfo: wx.getStorageSync("userInfo")
            })
            console.log("已登录");
        }
    },

    userLogin: async function() {
        let token = wx.getStorageSync("token");
        let user = await auth.userLogin(token);
        console.log(user);
        let userInfo = wx.getStorageSync("userInfo");
        console.log(userInfo)
        this.setData({
            userInfo,
            hasUserInfo: true
        })
        console.log(userInfo);
    },








    getUserProfile: function(e) {
        // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
        // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
        wx.getUserProfile({
            desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
            lang: 'zh_CN',
            success: (res) => {
                console.log(res);
                this.setData({
                    userInfo: res.userInfo,
                    hasUserInfo: true,
                    cloudId: res.CloudID
                })
            },
            fail: (err) => {
                console.log("tiao pi")
            }
        });
    },

    getSettings: function(e) {
        wx.getSetting({
            success: (result) => {
                console.log(result.authSetting)
            }
        });
    }
})