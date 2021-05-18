import auth from "../../util/auth"
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isLogin: "",
        userInfo: {},
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        //虽然一开始在缓存里放入了数据，但是当用户清除缓存之后，还是需要用户授权，以获取用户最新信息
        var isLogin = wx.getStorageSync("isLogin");
        this.setData({
            isLogin
        })
        if (isLogin) {
            let userInfo = wx.getStorageSync("userInfo");

            this.setData({ userInfo })
        }

    },

    userLogin: async function() {
        //当用户刚进入的时候login，获取用户openid，存入数据库
        //此时，数据库存入用户的openid，但还不算登录，需要在真正需要登录的地方，让用户授权，获取用户数据
        //获取玩用户数据之后，传入后台，保存到数据库里面，完善用户信息
        //考虑需要存入用户缓存数据：token？或者登录状态什么的？
        // let token = wx.getStorageSync("token");
        await auth.userLogin();
        let userInfo = wx.getStorageSync("userInfo");
        this.setData({
            userInfo: userInfo,
            isLogin: true
        })
        console.log(userInfo);
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },

    clearStorage() {
        //用户清除缓存，就是清掉了用户信息，其余信息(登录标志，token)不清除
        wx.setStorageSync("isLogin", false);
        wx.removeStorageSync("userInfo");
        wx.removeStorageSync("schoolId");
        this.setData({ userInfo: {}, isLogin: false })
        wx.showToast({
            title: '已清除',
            icon: 'none',
            duration: 1500,
        });
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})