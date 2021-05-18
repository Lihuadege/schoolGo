import requests from "../../util/request"
Page({

    data: {
        isShow: false,
        provinceArray: ["请选择省", "河南",
            "河北",
            "北京",
            "浙江",
            "湖南",
            "湖北",
            "广东",
            "辽宁",
            "广西",
            "新疆",
            "西藏",
            "青海",
            "内蒙古",
            "哈尔滨",
            "吉林",
            "云南",
            "四川",
            "宁夏",
            "山西",
            "江苏",
            "安徽",
            "福建",
            "江西",
            "山东",
            "天津",
            "上海",
            "重庆",
            "贵州",
            "黑龙江",
            "陕西",
            "甘肃",
            "台湾",
            "香港",
            "澳门"
        ],
        selectedParent: "请选择省",
        schoolArray: [],
        selectedSchool: "请选择学校",
        isDisabled: true,
        subId: undefined
    },

    onLoad: async function(options) {
        let isChecked = await this.verifySchool();

        if (!isChecked) {
            //只有判断用户没有登录而且用户信息中没有学校信息
            this.setData({ isShow: true })
        }
    },

    verifySchool: async function() {
        let isLogin = wx.getStorageSync("isLogin");
        if (isLogin) {
            wx.switchTab({
                url: '/pages/index/index'
            });
            return true;
        } else {
            let token = wx.getStorageSync("token");
            //这里用同步方法，防止数据还没到就显示欢迎页面内容
            await requests("/verifyOld", { token: token }).then(res => {
                if (res.code == 0) {
                    wx.setStorageSync("schoolId", res.data);
                    wx.switchTab({
                        url: '/pages/index/index'
                    });
                    return true;
                }
            })
        }
        return false;
    },

    submitSchool: function() {

        //先验证用户有没有选择，不是空的判断选中id有无值

        let subId = this.data.subId;
        if (!subId) {
            wx.showToast({
                title: '选择学校才可提交',
                duration: 1000,
                icon: "none"
            });
            return
        }

        //通过验证，发送请求，跳转页面
        let token = wx.getStorageSync("token");
        requests("/insertSchool", { token: token, userSchoolId: subId }).then(res => {
            if (res.code == 0) {
                wx.setStorageSync("schoolId", subId);

                wx.switchTab({
                    url: '/pages/index/index'
                });
            } else {
                wx.showToast({
                    title: '服务器错误，请稍后再试',
                    duration: 1000,
                    icon: "none"
                });
            }
        })

    },

    changeProvince: function(e) {
        let parentId = e.detail.value;
        let name = this.data.provinceArray[parentId];
        this.setData({
            selectedParent: name,
            schoolArray: [],
            subId: undefined
        })
        if (parentId == 0) {
            this.setData({
                isDisabled: true,
                selectedSchool: "请选择学校"
            })
            wx.showToast({
                title: '要选择省份哦',
                duration: 1000,
                icon: "none"
            });
        } else {
            //页面显示修改
            //发送请求，获取学校数组
            requests("/getChildren", { parentId: parentId }).then(result => {
                //清空原数组，填充新数组
                if (result.code == 0) {
                    //有数据，先设置是否禁用为true
                    let data = result.data;
                    let schoolArray = [];
                    for (const key in data) {
                        const element = data[key];
                        schoolArray[key] = { index: key, id: element.id, name: element.name }
                    }

                    this.setData({ isDisabled: false, schoolArray, selectedSchool: "请选择学校" })

                } else if (result.code == 1) {
                    this.setData({ selectedSchool: "无学校可选", isDisabled: true })
                } else {

                    this.setData({ selectedSchool: "请选择学校", isDisabled: true })
                    wx.showToast({
                        title: '发生未知错误',
                        duration: 1000,
                        icon: "none"
                    });
                }
            })
        }

    },

    changeSchool: function(e) {
        //切换学校逻辑
        let index = e.detail.value;
        // console.log(index);
        let schoolArray = this.data.schoolArray;
        let subId;
        let name;
        for (const key in schoolArray) {
            const element = schoolArray[key];
            if (index == element.index) {
                subId = element.id;
                name = element.name;
            }
        }
        this.setData({ subId, selectedSchool: name })
    }
})