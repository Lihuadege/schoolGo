/*
Navicat MySQL Data Transfer

Source Server         : aliyun
Source Server Version : 50716
Source Host           : 139.196.216.221:3306
Source Database       : schoolGo

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2021-04-29 14:08:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article_img
-- ----------------------------
DROP TABLE IF EXISTS `article_img`;
CREATE TABLE `article_img` (
  `article_id` int(11) NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_img
-- ----------------------------
INSERT INTO `article_img` VALUES ('1', '/images/useme.jpg');
INSERT INTO `article_img` VALUES ('1', '/images/headImg/default.jpg');
INSERT INTO `article_img` VALUES ('2', '/images/example/20210217192239.png');
INSERT INTO `article_img` VALUES ('2', '/images/example/20210217192100.png');

-- ----------------------------
-- Table structure for article_info
-- ----------------------------
DROP TABLE IF EXISTS `article_info`;
CREATE TABLE `article_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_nick_name` varchar(100) NOT NULL COMMENT '用户名',
  `user_head_img` varchar(255) NOT NULL DEFAULT '/images/headImg/default.jpg' COMMENT '用户头像',
  `title` varchar(255) DEFAULT NULL,
  `main_body` text COMMENT '文章正文',
  `commend` int(10) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_time` datetime NOT NULL,
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '文章状态，0是删除状态，1是有效状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_info
-- ----------------------------
INSERT INTO `article_info` VALUES ('1', '1', '张三', '/images/headImg/default.jpg', '中国好', '    中国人，中国心，几万年不变的事实，我们永远同在。', '6', '2021-02-16 14:07:50', '1');
INSERT INTO `article_info` VALUES ('2', '2', '李四', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eokmgMic76YFnAjiaHlG2Rkfa0JfuHTtX92GYenzElNhF9ZrqLibw52G1dcbvial1zq7Mgx5FPg1icvH5g/132', '校园交易系统正式开通运营', '欢迎各位同学关注我们校易购商城，请各位同学请进行享用', '89', '2021-04-28 10:00:20', '1');

-- ----------------------------
-- Table structure for attention_info
-- ----------------------------
DROP TABLE IF EXISTS `attention_info`;
CREATE TABLE `attention_info` (
  `user_id` int(11) NOT NULL,
  `notice_user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attention_info
-- ----------------------------

-- ----------------------------
-- Table structure for banner_info
-- ----------------------------
DROP TABLE IF EXISTS `banner_info`;
CREATE TABLE `banner_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_url` varchar(255) NOT NULL COMMENT '请求相对路径，应当与requestUrl后面路径一致',
  `img_name` varchar(100) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求全路径',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态，软删除，1为生效，0为删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banner_info
-- ----------------------------
INSERT INTO `banner_info` VALUES ('1', '/images/example/20210217192100.png', '苹果', 'http://localhost:8080/images/example/20210217192100.png', '2021-04-06 11:27:22', '2021-04-06 00:00:00', '0');
INSERT INTO `banner_info` VALUES ('2', '/images/banner/202102251036011663376544IMG_20200212_154421.JPG', 'IMG_20200212_154421.JPG', 'http://localhost:8080/images/banner/202102251036011663376544IMG_20200212_154421.JPG', '2021-04-06 11:19:51', '2021-04-06 11:19:51', '0');
INSERT INTO `banner_info` VALUES ('3', '/images/banner/20210225103723-525730908IMG_20200212_154421.JPG', 'IMG_20200212_154421.JPG', 'http://localhost:8080/images/banner/20210225103723-525730908IMG_20200212_154421.JPG', '2021-04-06 11:27:22', '2021-04-06 00:00:00', '0');
INSERT INTO `banner_info` VALUES ('4', '/images/banner/20210225103747883091632截屏_20180805_134945.jpg', '截屏_20180805_134945.jpg', 'http://localhost:8080/images/banner/20210225103747883091632截屏_20180805_134945.jpg', '2021-04-06 11:27:22', '2021-04-06 00:00:00', '0');
INSERT INTO `banner_info` VALUES ('5', '/images/banner/2021040612305012077325481.jpg', '1.jpg', 'http://localhost:8080/images/banner/2021040612305012077325481.jpg', '2021-04-06 12:32:41', '2021-04-06 12:32:41', '1');
INSERT INTO `banner_info` VALUES ('6', '/images/banner/202104061230548231153602.jpg', '2.jpg', 'http://localhost:8080/images/banner/202104061230548231153602.jpg', '2021-04-06 12:32:45', '2021-04-06 12:32:45', '1');
INSERT INTO `banner_info` VALUES ('7', '/images/banner/20210406123057-8002136563.png', '3.png', 'http://localhost:8080/images/banner/20210406123057-8002136563.png', '2021-04-06 12:32:48', '2021-04-06 12:32:48', '1');
INSERT INTO `banner_info` VALUES ('8', '/images/banner/2021040612370814097981044.jpg', '4.jpg', 'http://localhost:8080/images/banner/2021040612370814097981044.jpg', '2021-04-06 12:37:38', '2021-04-06 00:00:00', '0');

-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS `category_info`;
CREATE TABLE `category_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category_info
-- ----------------------------
INSERT INTO `category_info` VALUES ('1', '手机');
INSERT INTO `category_info` VALUES ('2', '衣服');
INSERT INTO `category_info` VALUES ('3', '书籍');
INSERT INTO `category_info` VALUES ('4', '外出');
INSERT INTO `category_info` VALUES ('5', '运动');
INSERT INTO `category_info` VALUES ('6', '电脑');
INSERT INTO `category_info` VALUES ('7', '生活');
INSERT INTO `category_info` VALUES ('8', '纸笔');
INSERT INTO `category_info` VALUES ('9', '鞋帽');
INSERT INTO `category_info` VALUES ('10', '食物');

-- ----------------------------
-- Table structure for collect_info
-- ----------------------------
DROP TABLE IF EXISTS `collect_info`;
CREATE TABLE `collect_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `create_time` datetime NOT NULL COMMENT '收藏时间',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '1是在收藏中，0是取消收藏，默认1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collect_info
-- ----------------------------
INSERT INTO `collect_info` VALUES ('1', '3', '5', '2021-04-15 18:43:20', '1');
INSERT INTO `collect_info` VALUES ('2', '3', '14', '2021-04-15 21:06:36', '1');
INSERT INTO `collect_info` VALUES ('3', '3', '15', '2021-04-25 16:23:22', '0');
INSERT INTO `collect_info` VALUES ('4', '3', '1', '2021-04-27 19:46:16', '0');

-- ----------------------------
-- Table structure for comment_info
-- ----------------------------
DROP TABLE IF EXISTS `comment_info`;
CREATE TABLE `comment_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `content` varchar(255) NOT NULL COMMENT '留言内容',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `article_title` varchar(255) NOT NULL COMMENT '文章标题',
  `user_id` int(11) NOT NULL COMMENT '评论用户id',
  `user_nick_name` varchar(100) NOT NULL COMMENT '评论用户昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像url',
  `commend` int(11) DEFAULT '0' COMMENT '点赞数',
  `create_time` datetime NOT NULL,
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment_info
-- ----------------------------
INSERT INTO `comment_info` VALUES ('1', '这篇文章写得真不错', '1', '中国好', '1', '张三', '/images/headImg/default.jpg', '6', '2021-02-19 11:30:55', '1');
INSERT INTO `comment_info` VALUES ('2', '生活在中国是一种幸运', '1', '中国好', '2', '李四', '/images/headImg/default.jpg', '9', '2021-02-19 11:30:40', '1');

-- ----------------------------
-- Table structure for goods_img_info
-- ----------------------------
DROP TABLE IF EXISTS `goods_img_info`;
CREATE TABLE `goods_img_info` (
  `goods_id` bigint(20) NOT NULL,
  `goods_url` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_img_info
-- ----------------------------
INSERT INTO `goods_img_info` VALUES ('2', '/images/example/20210217192225.png');
INSERT INTO `goods_img_info` VALUES ('2', '/images/example/20210217192302.png');
INSERT INTO `goods_img_info` VALUES ('2', '/images/example/20210217192239.png');
INSERT INTO `goods_img_info` VALUES ('2', '/images/example/20210217192100.png');
INSERT INTO `goods_img_info` VALUES ('9', '/images/goods_img/goodsKey9/202103071842350IMG_20200212_154421.JPG');
INSERT INTO `goods_img_info` VALUES ('4', '/images/goods_img/goodsKey4/202103071842580截屏_20180324_185817.jpg');
INSERT INTO `goods_img_info` VALUES ('4', '/images/goods_img/goodsKey4/202103071842580截屏_20180805_134945.jpg');
INSERT INTO `goods_img_info` VALUES ('4', '/images/goods_img/goodsKey4/202103071842580截屏_20190126_171128.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134001.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134002.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134003.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134004.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134005.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134006.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134007.jpg');
INSERT INTO `goods_img_info` VALUES ('14', '/images/goods_img/goodsKey14/2021040617134008.jpg');
INSERT INTO `goods_img_info` VALUES ('5', '/images/goods_img/goodsKey5/27580154431.jpg');
INSERT INTO `goods_img_info` VALUES ('5', '/images/goods_img/goodsKey5/977241035.jpg');
INSERT INTO `goods_img_info` VALUES ('5', '/images/goods_img/goodsKey5/1614985513.jpg');
INSERT INTO `goods_img_info` VALUES ('5', '/images/goods_img/goodsKey5/3239984347.jpg');
INSERT INTO `goods_img_info` VALUES ('9', '/images/goods_img/goodsKey9/202104081623340IMG_20180801_205905.JPG');
INSERT INTO `goods_img_info` VALUES ('9', '/images/goods_img/goodsKey9/202104081623340IMG_20200428_062527.JPG');
INSERT INTO `goods_img_info` VALUES ('3', '/images/goods_img/goodsKey3/202104131647230190256-102.jpg');
INSERT INTO `goods_img_info` VALUES ('15', '/images/goods_img/goodsKey15/20210419204841011.jpg');
INSERT INTO `goods_img_info` VALUES ('15', '/images/goods_img/goodsKey15/20210419204841012.jpg');

-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `sys_user_id` int(11) DEFAULT NULL,
  `school_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `detail_desc` text,
  `category_id` int(11) NOT NULL,
  `cover_img` varchar(255) DEFAULT NULL COMMENT '封面图url',
  `status` int(1) NOT NULL DEFAULT '2' COMMENT '0删除，1下架，2上架中',
  `price` double(10,2) NOT NULL,
  `is_personal` int(1) NOT NULL COMMENT '是否是学生发布的，0不是，1是',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO `goods_info` VALUES ('1', '1', null, '35', '小米手机', '小米11Pro，还未上市，本人提前到手，二手便宜出', '1', '/images/headImg/default.jpg', '2', '2000.00', '1', '2021-04-12 17:04:03');
INSERT INTO `goods_info` VALUES ('2', null, '2', '35', '光明眼镜防蓝光款', '光明眼镜店特品，抗蓝光眼镜，让您的眼睛处于更安全的状态', '7', '/images/headImg/default.jpg', '2', '500.00', '0', '2021-04-12 17:03:59');
INSERT INTO `goods_info` VALUES ('3', null, '2', '35', '明日方舟', '精致的明日方舟模型，欢迎您的选购', '7', '/images/goods_img/goodsKey3/202104131647230190256-102.jpg', '2', '300.00', '0', '2021-04-13 16:47:24');
INSERT INTO `goods_info` VALUES ('4', null, '2', '35', '昨日重现歌曲', '经典曲目昨日重现的歌曲MP3下载链接', '5', '/images/goods_img/goodsKey4/202103071842580截屏_20180324_185817.jpg', '2', '2.00', '0', '2021-04-12 17:03:51');
INSERT INTO `goods_info` VALUES ('5', '1', null, '35', '华为mate30pro', '这个商品是2019年10月份买来的，一直很珍重的使用，没有过任何的磕碰，小心爱护。使用情况来说，这是正常的磨损，校友们可以放心痛快的来咨询，这个价格绝对良心。', '1', '/images/goods_img/goodsKey5/977241035.jpg', '2', '2800.00', '1', '2021-04-12 17:03:47');
INSERT INTO `goods_info` VALUES ('9', null, '2', '35', '啊哈巧克力', '啊哈巧克力，巧克力中的翘楚，让您回味无穷', '4', '/images/goods_img/goodsKey9/202104081623340IMG_20180801_205905.JPG', '2', '10.00', '0', '2021-04-12 17:03:43');
INSERT INTO `goods_info` VALUES ('14', null, '2', '35', '澳洲洁面乳', '引进来自澳洲的最新技术的洁面乳，只需轻轻一抹，您就即可享受不一样的洁面体验，拥有一张干净的脸。本产品采用氨基酸，能使您的皮肤紧致白皙，深层清洁你的皮肤，净化毛孔，具有水润保湿的效果！', '7', '/images/goods_img/goodsKey14/2021040617134001.jpg', '1', '888.00', '0', '2021-04-12 17:03:40');
INSERT INTO `goods_info` VALUES ('15', null, '2', '35', '甲级甘油酸', '没啥好说的，买就完事了', '7', '/images/goods_img/goodsKey15/20210419204841011.jpg', '2', '36.00', '0', '2021-04-19 00:00:00');

-- ----------------------------
-- Table structure for notice_info
-- ----------------------------
DROP TABLE IF EXISTS `notice_info`;
CREATE TABLE `notice_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告表id',
  `create_time` datetime NOT NULL,
  `notice_content` varchar(255) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '0代表不展示，1代表展示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_info
-- ----------------------------
INSERT INTO `notice_info` VALUES ('1', '2021-02-25 11:50:25', '2021年是新的一年，希望同学们在新的一年里能够开心快乐', '1');
INSERT INTO `notice_info` VALUES ('2', '2021-02-25 11:50:31', '欢迎访问schoolGo，希望你购物愉快，正确食用本平台', '1');
INSERT INTO `notice_info` VALUES ('3', '2021-04-06 15:46:05', '咳咳，同学们，这是一则无用的公告，看到也不用点进来，就是测试用的！\n    如果点进来了，也没啥，返回就是了。', '1');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) NOT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '商家id',
  `saler_name` varchar(30) DEFAULT NULL,
  `saler_head_img` varchar(255) DEFAULT NULL,
  `purchaser_id` int(11) NOT NULL COMMENT '购买者id',
  `purchaser_name` varchar(30) NOT NULL,
  `create_time` datetime NOT NULL,
  `expire_time` varchar(20) DEFAULT NULL COMMENT '过期时间，若商品状态为3，则有该值',
  `saler_status` int(4) NOT NULL DEFAULT '0' COMMENT '商家订单状态：0代表未发货，1代表已发货但待收货，2代表交易已完成，3代表已取消订单，4代表已删除',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '购买者订单状态：0代表未发货，1代表已发货但待收货，2代表交易已完成，3代表已取消订单,4代表已删除',
  `phone_num` varchar(20) NOT NULL COMMENT '预留手机号',
  `goods_id` int(11) NOT NULL,
  `goods_head_img` varchar(255) DEFAULT NULL COMMENT '商品图片路径',
  `goods_title` varchar(255) DEFAULT NULL COMMENT '商品标题',
  `price` double(10,2) NOT NULL,
  `trade_way` int(2) NOT NULL COMMENT '交易方式：0代表约定取货，1代表上门送货',
  `trade_num` int(5) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `trade_addr` varchar(255) NOT NULL COMMENT '交易地点',
  `trade_time` varchar(255) NOT NULL,
  `order_note` varchar(255) DEFAULT NULL COMMENT '订单备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户查看的订单表';

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2', 'E3054481618384682250', null, '2', '艾希眼镜店', 'http://localhost:8080/images/headImg/20210412171223772549607329770-102.jpg', '3', '轮子妈', '2021-04-14 15:18:02', null, '2', '2', '17633801586', '14', 'http://localhost:8080/images/goods_img/goodsKey14/2021040617134001.jpg', '澳洲洁面乳', '888.00', '0', '2', '枣园', '12:31', '这是一条测试数据，请商家不要发货，我付钱就是了');
INSERT INTO `order_info` VALUES ('3', 'EC1944d1618409582097', null, '2', '艾希眼镜店', 'http://localhost:8080/images/headImg/20210412171223772549607329770-102.jpg', '3', '张三', '2021-04-14 22:13:02', null, '0', '0', '17633801586', '4', 'http://localhost:8080/images/goods_img/goodsKey4/202103071842580截屏_20180324_185817.jpg', '昨日重现歌曲', '2.00', '0', '1', '洛师西门', '10:12', '请准时到达');
INSERT INTO `order_info` VALUES ('4', 'B337F481618410251861', null, '2', '艾希眼镜店', 'http://localhost:8080/images/headImg/20210412171223772549607329770-102.jpg', '3', '猫猫', '2021-04-14 22:24:12', null, '1', '1', '17633801586', '9', 'http://localhost:8080/images/goods_img/goodsKey9/202104081623340IMG_20180801_205905.JPG', '啊哈巧克力', '10.00', '1', '7', '枣园超市门口', '16:23', '猫猫爱吃巧克力');
INSERT INTO `order_info` VALUES ('5', '1BCA14A1619339021407', null, '2', '生活用品店', 'http://localhost:8080/images/headImg/20210412171223772549607329770-102.jpg', '3', 'aa', '2021-04-25 16:23:41', null, '3', '3', '17633801586', '15', 'http://localhost:8080/images/goods_img/goodsKey15/20210419204841011.jpg', '甲级甘油酸', '36.00', '1', '4', 'aff', '16:23', '');

-- ----------------------------
-- Table structure for school_info
-- ----------------------------
DROP TABLE IF EXISTS `school_info`;
CREATE TABLE `school_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父分类id，用一张表代替省和高校',
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school_info
-- ----------------------------
INSERT INTO `school_info` VALUES ('1', '0', '河南');
INSERT INTO `school_info` VALUES ('2', '0', '河北');
INSERT INTO `school_info` VALUES ('3', '0', '北京');
INSERT INTO `school_info` VALUES ('4', '0', '浙江');
INSERT INTO `school_info` VALUES ('5', '0', '湖南');
INSERT INTO `school_info` VALUES ('6', '0', '湖北');
INSERT INTO `school_info` VALUES ('7', '0', '广东');
INSERT INTO `school_info` VALUES ('8', '0', '辽宁');
INSERT INTO `school_info` VALUES ('9', '0', '广西');
INSERT INTO `school_info` VALUES ('10', '0', '新疆');
INSERT INTO `school_info` VALUES ('11', '0', '西藏');
INSERT INTO `school_info` VALUES ('12', '0', '青海');
INSERT INTO `school_info` VALUES ('13', '0', '内蒙古');
INSERT INTO `school_info` VALUES ('14', '0', '哈尔滨');
INSERT INTO `school_info` VALUES ('15', '0', '吉林');
INSERT INTO `school_info` VALUES ('16', '0', '云南');
INSERT INTO `school_info` VALUES ('17', '0', '四川');
INSERT INTO `school_info` VALUES ('18', '0', '宁夏');
INSERT INTO `school_info` VALUES ('19', '0', '山西');
INSERT INTO `school_info` VALUES ('20', '0', '江苏');
INSERT INTO `school_info` VALUES ('21', '0', '安徽');
INSERT INTO `school_info` VALUES ('22', '0', '福建');
INSERT INTO `school_info` VALUES ('23', '0', '江西');
INSERT INTO `school_info` VALUES ('24', '0', '山东');
INSERT INTO `school_info` VALUES ('25', '0', '天津');
INSERT INTO `school_info` VALUES ('26', '0', '上海');
INSERT INTO `school_info` VALUES ('27', '0', '重庆');
INSERT INTO `school_info` VALUES ('28', '0', '贵州');
INSERT INTO `school_info` VALUES ('29', '0', '黑龙江');
INSERT INTO `school_info` VALUES ('30', '0', '陕西');
INSERT INTO `school_info` VALUES ('31', '0', '甘肃');
INSERT INTO `school_info` VALUES ('32', '0', '台湾');
INSERT INTO `school_info` VALUES ('33', '0', '香港');
INSERT INTO `school_info` VALUES ('34', '0', '澳门');
INSERT INTO `school_info` VALUES ('35', '1', '洛阳师范学院');
INSERT INTO `school_info` VALUES ('36', '1', '洛阳理工学院');
INSERT INTO `school_info` VALUES ('37', '1', '郑州大学');
INSERT INTO `school_info` VALUES ('38', '1', '河南大学');
INSERT INTO `school_info` VALUES ('39', '1', '信阳师范学院');
INSERT INTO `school_info` VALUES ('40', '1', '南阳师范学院');
INSERT INTO `school_info` VALUES ('41', '3', '北京大学');
INSERT INTO `school_info` VALUES ('42', '3', '清华大学');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL COMMENT '登录名',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `user_area_id` int(10) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_num` varchar(15) DEFAULT NULL,
  `password` varchar(255) NOT NULL DEFAULT '',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '0是封禁或者注销，1代表正在使用',
  `is_super_manager` int(1) NOT NULL DEFAULT '0' COMMENT '1是超级管理员，0不是，只是普通的商户',
  `head_img` varchar(255) DEFAULT '/images/headImg/default.jpg' COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', '1', 'school@go.com', '15858858888', 'e10adc3949ba59abbe56e057f20f883e', '1', '1', '/images/headImg/20210222180103450381728102216-102.jpg');
INSERT INTO `sys_user` VALUES ('2', 'lmz', '生活用品店', '35', 'aixi@gmail.com', '12345678910', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/20210412171223772549607329770-102.jpg');
INSERT INTO `sys_user` VALUES ('3', 'adff', 'fseafa', '35', 'aeasf@fsw.com', '13412345678', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('4', 'asfewsse', 'faeffd', '35', 'sfji@fowje.com', '13412345678', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('5', 'tencent', '腾讯', '35', 'tencent@ma.com', '13412345677', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('6', 'faefe', 'grag', '35', 'fergv@lco.com', '13412345678', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('7', 'gyhht', 'hdrthd', '35', 'cofj@cljoi.com', '13412345678', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('8', 'rag', 'aefa', '35', 'hcjedi@fji.com', '13245678956', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('9', 'fdsfn', 'brtd', '35', 'fewjan@dfj.com', '13256984785', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('10', 'gbv', '好人多好听', '35', 'gre@bjirf.cvom', '13245678546', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('11', 'gjri', 'jifrg', '35', 'feraf@jgri.com', '14523654789', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');
INSERT INTO `sys_user` VALUES ('12', 'gfhht', 'grsgr', '35', 'fejfie@ogrj.com', '18974563289', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '/images/headImg/default.jpg');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，用户的openId，是唯一的',
  `open_id` varchar(255) NOT NULL COMMENT '用户的openId',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `gender` int(1) DEFAULT NULL COMMENT '性别：0女；1男；2未知',
  `personal_words` varchar(255) DEFAULT '' COMMENT '个性签名',
  `school_id` int(11) DEFAULT NULL COMMENT '学校id,此字段值只能是有父id的学校id，而不是省的id',
  `province` varchar(15) DEFAULT NULL,
  `phone_num` varchar(12) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `detail_area` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT '' COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='因为这个表是面向用户的表格，用户一开始可能不会登录，但是只要用户访问小程序，后台就会产生一个用户的openid存入本表，\r\n但因为本表此条数据';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '', '张三', '1', '我是张三', '35', null, '12345678910', 'zhangsan@qq.com', '枣园', 'http://localhost:8080/images/headImg/default.jpg');
INSERT INTO `user_info` VALUES ('2', '', '李四', '0', '我不是李四', '35', null, '13478946589', 'lisi@ali.com', '桂圆', 'http://localhost:8080/images/headImg/default.jpg');
INSERT INTO `user_info` VALUES ('3', 'o3a_l5MqZrgb6Pt7Z4KBaqtLc_hA', '砒霜', '1', '我就是我，不一样的烟火', '35', null, '17633801586', null, null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eokmgMic76YFnAjiaHlG2Rkfa0JfuHTtX92GYenzElNhF9ZrqLibw52G1dcbvial1zq7Mgx5FPg1icvH5g/132');
