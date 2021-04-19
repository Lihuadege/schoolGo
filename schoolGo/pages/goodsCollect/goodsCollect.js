import requests from "../../util/request"
Page({

    data: {
        goodsList: [],
        userId: '',
        isEmpty: false
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        let userInfo = wx.getStorageSync("userInfo");
        let userId = userInfo.id;
        if (!userId) {
            wx.navigateBack({
                delta: 1
            });
        } else {
            this.setData({ userId })
        }
        requests("/getCollectList", { userId: userId }).then(res => {
            if (res.code === 0) {
                //获取成功
                let goodsList = res.data;
                if (!goodsList) {
                    this.setData({ isEmpty: true })
                }
                this.setData({ goodsList })
            } else {
                wx.showToast({
                    title: res.msg,
                    duration: 1000
                })
            }
        })

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

})