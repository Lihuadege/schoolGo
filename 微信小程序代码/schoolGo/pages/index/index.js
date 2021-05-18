import requests from '../../util/request'
Page({

    /**
     * 页面的初始数据
     */
    data: {
        bannerList: [],
        noticeList: [],
        goodsList: [],
        schoolId: undefined
    },

    currentPage: 1,

    totalPage: 1,

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        let schoolId = wx.getStorageSync("schoolId");
        this.setData({ schoolId })
        this.getBanners();
        this.getNotice();
        this.getGoods(this.currentPage);
    },

    /**
     * 获取商品图片的方法，考虑到不能一次加载出所有的商品
     * 因此需要对商品进行分页，需要定义一些数据来进行记录
     * 定义一个当前页，分页大小直接由后端决定算了
     */
    getGoods: function(pages) {
        let schoolId = this.data.schoolId;
        requests("/listGoods", { page: pages, schoolId: schoolId }).then(result => {
            const total = result.total;
            this.totalPage = Math.ceil(total / 4);
            // console.log("总页数是" + this.totalPage + "，获取到的数据是"+total)
            this.setData({
                    goodsList: result.data
                })
                //下拉刷新有动画，要关闭动画
            wx.stopPullDownRefresh();
        })
    },

    /**
     * 页面上拉触底事件的处理函数、
     * 这个可以在主页使用一下
     */
    onReachBottom: function() {
        /*触底时，判断当前页与总页数的关系，
         *来判断是否要发送请求到后台，从而可以一直下滑
         */
        // console.log(this.currentPage);
        if (this.currentPage >= this.totalPage) {
            //已经查询完所有的商品了，不进行操作
            wx.showToast({
                title: '已经没有更多了',
                icon: 'none',
                duration: 1000
            });
        } else {
            let schoolId = this.data.schoolId;
            this.currentPage += 1;
            //下面还有数据，继续请求，注意原来数组不能直接覆盖数据
            requests("/listGoods", { page: this.currentPage, schoolId: schoolId }).then(result => {
                this.setData({
                    goodsList: [...this.data.goodsList, ...result.data]
                })
            })
        }
    },

    getBanners: function() {
        requests("/getBanners").then(result => {
            this.setData({
                bannerList: result.data
            })
        })
    },

    getNotice: function() {
        requests("/getNotice").then(result => {
            this.setData({
                noticeList: result.data
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
        //用户下拉刷新当前页面
        this.currentPage = 1;
        this.setData({
            goodsList: []
        })
        this.getGoods(this.currentPage)
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})