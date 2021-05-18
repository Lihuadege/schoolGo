import requests from "../../util/request"
Page({

    /**
     * 页面的初始数据
     */
    data: {
        articleList: [],
        schoolId: undefined,
    },


    currentPage: 1,
    totalPage: 1,

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        //获取schoolID，根据这个值查询文章列表
        let schoolId = wx.getStorageSync("schoolId");
        this.setData({ schoolId });
        this.getArticle(this.currentPage);
    },

    getArticle: function(pages) {
        let schoolId = this.data.schoolId;
        //还是后端决定分页大小
        requests("/listArticle", { page: pages, schoolId: schoolId }).then(result => {
            if (result.code == 0) {
                const total = result.data.total;
                //计算总页数，以每次加载10个数据为例
                this.totalPage = Math.ceil(total / 10);
                this.setData({
                    articleList: result.data.articleList
                })
            } else {
                wx.showToast({
                    title: result.msg,
                    icon: 'none',
                    duration: 1500
                });
            }
            wx.stopPullDownRefresh();
        })
    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {
        this.currentPage = 1;
        this.setData({
            goodsList: []
        })
        this.getGoods(this.currentPage)
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {
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
            requests("/listArticle", { page: this.currentPage, schoolId: schoolId }).then(result => {
                this.setData({
                    articleList: [...this.data.articleList, ...result.data.articleList]
                })
            })
        }
    },

})