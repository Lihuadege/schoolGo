// pages/noticeDetail/noticeDetail.js
import requests from '../../util/request'
import daeFormat from '../../util/dateFormat'
import dateFormat from '../../util/dateFormat';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        createTime: "",
        noticeContent: ""
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        let noticeId = options.id;
        requests("/toDetailNotice", { id: noticeId }).then(result => {
            this.setData({
                noticeContent: result.data.noticeContent,
                createTime: dateFormat(new Date(result.data.createTime), 'yyyy-MM-dd HH:mm:ss')
            })
        })
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

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
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})