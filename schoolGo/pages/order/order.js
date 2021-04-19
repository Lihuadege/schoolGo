import requests from '../../util/request'
Page({

    /**
     * 页面的初始数据
     */
    data: {
        tabs: [{
                id: 0,
                value: "待发货",
                isActive: true
            },
            {
                id: 1,
                value: "待收货",
                isActive: false
            },
            {
                id: 2,
                value: "全部",
                isActive: false
            }
        ],
        orderList: [],
        currentPage: 1,
        totalPage: undefined,
        currentStatus: 0,
        userId: undefined,
        haveMore: true,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        //没有在个人中心做各个入口，所以直接默认计入页面是待收货

        let userInfo = wx.getStorageSync("userInfo");

        let userId = userInfo.id;
        this.setData({ userId })

        this.getOrders();
    },

    confirmReceive: function(e) {
        let orderId = e.currentTarget.dataset.id;
        this.baseOrder(orderId, "/confirmReceive");
    },

    cancelOrder: function(e) {
        let orderId = e.currentTarget.dataset.id;
        this.baseOrder(orderId, "/cancelOrder");
    },

    deleteOrder: function(e) {
        let orderId = e.currentTarget.dataset.id;
        this.baseOrder(orderId, "/userDeleteOrder");
    },

    baseOrder: function(orderId, methodName) {
        requests(methodName, { orderId: orderId }).then(res => {
            let result = res.msg;
            wx.showToast({
                title: result,
                duration: 1000,
            });
            if (res.code == 0) {
                let orderList = this.data.orderList;
                for (let index = 0; index < orderList.length; index++) {
                    let id = orderList[index].id;
                    if (id == orderId) {
                        orderList.splice(index, 1);
                    }
                }
                // console.log(orderList);
                this.setData({ orderList });
            }
        })
    },

    /**
     * 获取订单函数，这个是从开始就获取，不是追加
     */
    async getOrders() {
        let status = this.data.currentStatus;

        let userId = this.data.userId;

        if (!userId) {
            wx.showModal({
                title: '提示',
                content: '你还没有登录，请先登录',
                cancelColor: '#000000',
                confirmText: '确定',
                confirmColor: '#3CC51F',
                success: (result) => {
                    wx.navigateTo({
                        url: '/pages/personal/personal'
                    });
                }
            });
        }

        //触发这个函数，代表切换订单状态或者刚刚进入，需要直接清空订单列表
        this.setData({
            orderList: []
        })

        wx.showLoading({
                title: '加载中',
            })
            //根据status的值，获取对应状态的订单
        await requests("/getOrderList", { status: status, userId: userId }).then(result => {
            if (result.code == 0) {
                let total = result.total;
                let totalPage = Math.ceil(total / 4);
                this.setData({
                    currentPage: 1,
                    totalPage: totalPage,
                    orderList: result.data
                })

            } else {
                wx.showToast({
                    title: '出错了，请重试',
                    duration: 1000
                })
            }
        });
        wx.hideLoading();
    },

    //处理点击切换的导航栏事件
    handleItemChange(e) {
        var id = e.detail.index;
        let newTabs = this.data.tabs;
        newTabs.forEach((v, i) =>
            i === id ? v.isActive = true : v.isActive = false
        );
        this.setData({
            tabs: newTabs,
            currentStatus: id,
            haveMore: true,
        })
        this.getOrders();
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {
        // console.log("你已经拉到最低下了，没有更多商品了");
        if (this.data.currentPage >= this.data.totalPage) {
            //已经查询完所有的商品了,不同于之前的样式
            this.setData({
                haveMore: false
            })

        } else {
            this.data.currentPage += 1;
            let nowPage = this.data.currentPage;
            let status = this.data.currentStatus;
            //下面还有数据，继续请求，注意原来数组不能直接覆盖数据
            requests("/getOrderList", { page: nowPage, status: status }).then(result => {
                this.setData({
                    orderList: [...this.data.orderList, ...result.data]
                })
            })
        }
    },


})