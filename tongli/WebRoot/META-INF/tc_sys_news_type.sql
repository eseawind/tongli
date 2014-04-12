/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : tongli

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2014-04-11 22:29:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tc_sys_news_type`
-- ----------------------------
DROP TABLE IF EXISTS `tc_sys_news_type`;
CREATE TABLE `tc_sys_news_type` (
  `id` varchar(55) NOT NULL COMMENT 'id',
  `name` varchar(200) NOT NULL,
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `parent_id` varchar(55) DEFAULT NULL COMMENT '父id',
  `sort_num` int(11) DEFAULT NULL COMMENT '序号',
  `date_created` datetime DEFAULT NULL COMMENT '数据输入日期',
  `create_id` varchar(50) DEFAULT NULL COMMENT '建立者ID',
  `create_ip` varchar(50) DEFAULT NULL COMMENT '建立者IP',
  `last_updated` datetime DEFAULT NULL COMMENT '资料更新日期',
  `update_id` varchar(50) DEFAULT NULL COMMENT '修改者ID',
  `update_ip` varchar(50) DEFAULT NULL COMMENT '修改者IP',
  `version` int(11) DEFAULT '0' COMMENT 'VERSION',
  `del_flag` char(1) DEFAULT '0' COMMENT '0否1是',
  `detail_info` varchar(250) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资讯分类信息';

-- ----------------------------
-- Records of tc_sys_news_type
-- ----------------------------
INSERT INTO `tc_sys_news_type` VALUES ('1556c51b42cc4b16b1cd1d6b652578d4', '科学探索营', null, '26f1017792024a358c73639b08e74393', '4', '2014-04-11 22:23:16', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:23:16', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('26f1017792024a358c73639b08e74393', '冬夏令营', null, '', '1', '2014-04-11 22:16:45', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:16:45', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('2eab49b782314c2bb645d2881bea69b3', '武道社团', null, '6690aceda07a405a9428e6e02ba2d416', '3', '2014-04-11 22:21:11', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:21:11', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('39d6a192e3004e6ba4972e029a398390', '轮滑社团', null, '6690aceda07a405a9428e6e02ba2d416', '2', '2014-04-11 22:20:56', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:20:56', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('3a5e43727219487cb6a44724f8cc0471', '童励介绍', null, '966a13c753f34faa927510c610b5e0b6', '0', '2014-04-11 22:25:10', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:25:10', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('3f2b286347174e728d39169c212fe56b', '新闻资讯', null, '', '0', '2014-04-11 22:16:14', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:16:14', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('48161e51b78a4dc78a57032362d5d4f3', '联系方式', null, '966a13c753f34faa927510c610b5e0b6', '2', '2014-04-11 22:26:11', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:26:11', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('5b3b90343ddc4490acaa26023ec84721', '假期营', null, '26f1017792024a358c73639b08e74393', '3', '2014-04-11 22:22:45', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:22:45', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('6690aceda07a405a9428e6e02ba2d416', '运动课程', null, '', '2', '2014-04-11 22:17:04', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:17:04', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('6730aae5fc2c41f2984a339dff339a11', '校区查询', null, '966a13c753f34faa927510c610b5e0b6', '1', '2014-04-11 22:25:48', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:25:48', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('690e3d1f73224bb7bd766b4648041798', '图片资讯', null, '3f2b286347174e728d39169c212fe56b', '1', '2014-04-11 22:17:36', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:17:36', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('6fba86e8436049e5b30123c538b7fc83', '焦点图资讯', null, '3f2b286347174e728d39169c212fe56b', '0', '2014-04-11 22:17:24', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:17:24', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('894a47f564f04149b31c5b6f25d0b3e1', '夏令营', null, '26f1017792024a358c73639b08e74393', '0', '2014-04-11 22:18:12', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:18:12', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('966a13c753f34faa927510c610b5e0b6', '关于我们', null, '', '4', '2014-04-11 22:24:49', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:24:49', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('9845745635ab4354b05f1785b78a3862', '冬令营', null, '26f1017792024a358c73639b08e74393', '1', '2014-04-11 22:18:23', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:18:23', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('a534ef573de54297acd70f93937985c6', '舞蹈社团', null, '6690aceda07a405a9428e6e02ba2d416', '4', '2014-04-11 22:21:24', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:21:24', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('ca90c5f95a88475d877e2819b2cedfa6', '球类社团', null, '6690aceda07a405a9428e6e02ba2d416', '1', '2014-04-11 22:20:41', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:20:41', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('cff7aad3a82041d08a6a1565ac87fc7b', '小小铁人三项训练营', null, '6690aceda07a405a9428e6e02ba2d416', '5', '2014-04-11 22:21:59', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:21:59', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('d76b4e2ba4b84e5db471988377f8ba52', '视频资讯', null, '3f2b286347174e728d39169c212fe56b', '2', '2014-04-11 22:17:48', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:17:48', null, null, '0', '0', null);
INSERT INTO `tc_sys_news_type` VALUES ('eff3b3c2b2cc4fc0a5a0009ca1bc137c', '游泳社团', null, '6690aceda07a405a9428e6e02ba2d416', '0', '2014-04-11 22:20:10', 'admin', '0:0:0:0:0:0:0:1', '2014-04-11 22:20:25', 'admin', '0:0:0:0:0:0:0:1', '1', '0', null);
