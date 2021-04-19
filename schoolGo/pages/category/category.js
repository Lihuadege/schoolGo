import requests from "../../util/request"
Page({

    /**
     * 页面的初始数据
     * 需要先获取到所有的类别，然后根据类别查找到对应的分类的商品
     * 一次请求，还是2次请求？
     */
    data: {
        activeId: 1,
        category: [],
        goodsList: [],
        total: 0
    },

    /**切换商品分类出发函数，改变categoryId和goodsList
     * 分析业务逻辑：
     * 每一个分类都需要选择对应的商品，并且需要分页
     * 避免一次加载过多的商品导致卡顿，因此需要当前页和总页数
     * 分页大小仍然由后台直接定义，前台不负责定义，就6个好了
     * 每当点击新的分类后，都需要充值分页信息，然后向后台发送请求获取最新分类的商品、
     * 并且填充到category数组中，在前台页面显示
     */
    changeCategory: function(e) {
        // 重置商品类别id，activeId就是categoryId
        var activeId = e.currentTarget.dataset.aid;
        this.setData({
                activeId
            })
            //重置currentPage
        this.currentPage = 1;
        //totalPage不需重置，因为在下面这个方法里已经有重新赋值了
        this.getGoods();
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.getCategoryList();
        this.getGoods();
    },

    currentPage: 1,
    totalPage: 1,

    //获取商品列表，带分页功能
    getGoods: function() {
        let nowPage = this.currentPage;
        let thatCategoryId = this.data.activeId;
        wx.showLoading({
            title: '加载中',
        })
        requests("/getGoodsList", { page: nowPage, categoryId: thatCategoryId }).then(result => {
            const total = result.total;
            this.setData({
                total
            })
            this.totalPage = Math.ceil(total / 4);
            this.setData({
                goodsList: result.data
            })
            wx.hideLoading();
        })
    },

    /* 触底获取新商品操作
       当前分类的商品滑到最下面，需要加载剩余的商品触发的函数
    */
    getNewGoods: function() {
        // console.log("你已经拉到最低下了，没有更多商品了");
        if (this.currentPage >= this.totalPage) {
            //已经查询完所有的商品了，不进行操作
            wx.showToast({
                title: '已经没有更多了',
                icon: 'none',
                duration: 1500
            });
        } else {
            this.currentPage += 1;
            let nowPage = this.currentPage;
            let thatCategoryId = this.data.activeId;
            //下面还有数据，继续请求，注意原来数组不能直接覆盖数据
            requests("/getGoodsList", { page: nowPage, categoryId: thatCategoryId }).then(result => {
                this.setData({
                    goodsList: [...this.data.goodsList, ...result.data]
                })
            })
        }
    },

    //函数封装，获取商品分类列表
    getCategoryList: function() {
        requests("/getCategory").then(res => {
            let category = res.data;
            this.setData({
                category
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
        this.getNewGoods();
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})