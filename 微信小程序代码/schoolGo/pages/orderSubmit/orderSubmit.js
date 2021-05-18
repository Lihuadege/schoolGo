// pages/orderSubmit/orderSubmit.js
import dateFormat from "../../util/dateFormat"
import requests from "../../util/request"
Page({

    /**
     * 页面的初始数据
     */
    data: {
        number: 1,
        totalPrice: 200,
        price: 200,
        time: undefined,
        goodsInfo: {},
        userPhone: ""
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: async function(options) {
        //存入当前时间，以便回显
        var time = dateFormat(new Date(), 'HH:mm');
        this.setData({ time });

        let goodsId = options.goodsId;

        await requests("/getGoodsById", { goodsId: goodsId }).then(result => {
            let goodsInfo = result.data.goodsInfo
            this.setData({ goodsInfo, price: goodsInfo.price, totalPrice: goodsInfo.price })
        })

        //能进到这个界面，已经说明是登录状态了，所以从缓存获取用户数据
        let userInfo = wx.getStorageSync("userInfo");
        this.setData({ userPhone: userInfo.phoneNum });
    },

    //监听表单提交，构建订单数据，这是动态的，用户可以改变的
    formSubmit(e) {
        let data = e.detail.value;

        //对data数据中的姓名，电话等进行校验
        let res1 = this.nullVerify(data.purchaserName, "名称");
        if (!res1) {
            return false;
        }

        let res2 = this.phone(data.phoneNum);
        if (!res2) {
            return false;
        }

        let res3 = this.nullVerify(data.tradeAddr, "地址");
        if (!res3) {
            return false;
        }

        //获取交易数量并设置
        data.tradeNum = this.data.number;

        let userInfo = wx.getStorageSync("userInfo");
        data.purchaserId = userInfo.id;
        var goodsInfo = this.data.goodsInfo;
        let isPersonal = goodsInfo.isPersonal;
        let reqParam = {};
        if (isPersonal === 1) {
            //个人商品
            data.userId = goodsInfo.userId;
            reqParam.userId = goodsInfo.userId;
        } else {
            //商家商品
            data.sysUserId = goodsInfo.sysUserId;
            reqParam.sysUserId = goodsInfo.sysUserId;
        }
        requests("/getSalerInfo", reqParam).then(res => {

            data.salerName = res.data.salerName;
            data.salerHeadImg = res.data.salerHeadImg;

        })
        data.goodsId = goodsInfo.id;
        data.goodsTitle = goodsInfo.title;
        data.goodsHeadImg = "http://localhost:8080" + goodsInfo.coverImg;
        data.price = this.data.price;

        console.log(data)

        //此处不进行重复提交的验证
        wx.showModal({
            title: '支付',
            content: '这是支付步骤，点击支付即可',
            showCancel: true,
            cancelText: '取消',
            cancelColor: '#000000',
            confirmText: '确定',
            confirmColor: '#3CC51F',
            success: (result) => {
                if (result.confirm) {
                    //向服务器发送请求，创建订单
                    requests("/payAndCreateOrder", data).then(result => {
                        if (result.code == 0) {
                            //新建订单成功
                            wx.showToast({
                                title: '购买成功',
                                duration: 1500
                            });
                            wx.navigateTo({
                                url: '/pages/order/order'
                            });

                        } else {
                            //创建失败
                            wx.showToast({
                                title: '提交失败，请重试'
                            });
                        }
                    })
                }
            }
        });


    },

    minusNumber: function() {
        let number = this.data.number;
        let totalPrice = this.data.totalPrice;
        if (number <= 1) {
            return
        } else {
            number -= 1;
            totalPrice -= this.data.price;
            this.setData({ number, totalPrice })
        }
    },

    plusNumber: function() {
        let number = this.data.number;
        let totalPrice = this.data.totalPrice;
        number += 1;
        totalPrice += this.data.price;
        this.setData({ number, totalPrice })
        this.setData({ number })
    },

    bindDateChange: function(e) {
        // console.log(e)
        var time = e.detail.value;
        this.setData({ time })
    },

    phoneVerify: function(e) {
        this.phone(e.detail.value);
    },

    phone: function(number) {
        if (!(/^1[3|4|5|7|8][0-9]{9}$/.test(number))) {
            wx.showModal({
                title: '请输入正确的手机号',
                showCancel: false,
                confirmText: '确定',
                confirmColor: '#3CC51F'
            });
            return false;
        } else {
            return true;
        }
    },

    isNullVerify: function(e) {
        let words = e.detail.value;
        let type = e.currentTarget.dataset.type;
        this.nullVerify(words, type);
    },

    nullVerify: function(words, type) {
        if (words == "") {
            wx.showModal({
                title: '请输入' + type,
                content: '',
                showCancel: false,
                confirmText: '确定',
                confirmColor: '#3CC51F'
            });
            return false;
        } else {
            return true;
        }
    }

})