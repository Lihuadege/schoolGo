import requests from "../../util/request"
import auth from "../../util/auth"
Page({

    /**
     * 页面的初始数据
     */
    data: {
        detailGoods: undefined,
        isPersonal: false,
        goodsId: undefined,
        isLogin: '',
        isCollect: false,
        userId: '',
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: async function(options) {
        var isLogin = wx.getStorageSync("isLogin");
        this.setData({ isLogin })

        if (isLogin) {
            let userInfo = wx.getStorageSync("userInfo");
            this.setData({ userId: userInfo.id });
        }

        // console.log(options)
        let goodsId = options.goods_id;
        requests("/getGoodsById", { goodsId }).then(result => {
            let detailGoods = result.data;
            this.verifyCollect(detailGoods.goodsInfo.id)
            this.setData({
                detailGoods,
                goodsId: detailGoods.goodsInfo.id
            })
            if (detailGoods.goodsInfo.isPersonal === 1) {
                this.setData({ isPersonal: true })
            }
        })
    },

    verifyCollect: function(goodsId) {
        let userId = this.data.userId;
        if (!userId) {
            return;
        }
        requests("/verifyCollect", { userId: userId, goodsId: goodsId }).then(result => {
            if (result.code === 0) {
                this.setData({ isCollect: true, userId })
            }
        })
    },

    collectGoods: function() {
        let userId = this.data.userId;
        if (!userId) {
            wx.showToast({
                title: '您未登录,不可操作',
                duration: 1000,
                mask: true
            })
            return;
        }

        let goodsId = this.data.goodsId;
        let isCollect = this.data.isCollect;
        if (isCollect) {
            //如果是收藏，则点击就是取消收藏
            requests("/doCancelCollect", { userId: userId, goodsId: goodsId }).then(result => {
                if (result.code === 0) {
                    this.setData({ isCollect: false })
                    wx.showToast({
                        title: '已取消',
                        icon: 'success',
                        duration: 1000,
                    });
                } else {
                    wx.showToast({
                        title: '操作失败',
                        icon: 'error',
                        duration: 1000,
                    });
                }
            })
        } else {
            //不是正在收藏中，就收藏
            requests("/doCollect", { userId: userId, goodsId: goodsId }).then(result => {
                if (result.code === 0) {
                    this.setData({ isCollect: true })
                    wx.showToast({
                        title: '收藏成功',
                        icon: 'success',
                        duration: 1000,
                    });
                } else {
                    wx.showToast({
                        title: '收藏失败',
                        icon: 'error',
                        duration: 1000,
                    });
                }
            })
        }
    },

    createOrder: async function() {
        let that = this;
        let isLogin = that.data.isLogin;
        // console.log(isLogin)
        let goodsId = this.data.goodsId

        if (isLogin) {
            wx.navigateTo({
                url: '/pages/orderSubmit/orderSubmit?goodsId=' + goodsId
            });
        } else {
            auth.userLogin('要想购买，请先您的授权').then(result => {
                if (result) {
                    // console.log("nice")
                    this.setData({ isLogin: true })
                    wx.navigateTo({
                        url: '/pages/orderSubmit/orderSubmit?goodsId=' + goodsId
                    });
                } else {
                    console.log("oh no")
                }
            });
        }
    }
})