/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : tongli

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2014-04-21 21:59:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tc_sys_variable`
-- ----------------------------
DROP TABLE IF EXISTS `tc_sys_variable`;
CREATE TABLE `tc_sys_variable` (
  `variable_id` varchar(50) NOT NULL COMMENT '参考：系统设置.xls',
  `variable_name` varchar(50) NOT NULL COMMENT '变量名称',
  `variable_sub_id` varchar(50) NOT NULL COMMENT '变量子项关键字',
  `variable_sub_name` varchar(50) NOT NULL COMMENT '变量子项名称',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据输入日期',
  `create_id` varchar(50) DEFAULT NULL COMMENT '建立者ID',
  `create_ip` varchar(50) DEFAULT NULL COMMENT '建立者IP',
  `last_updated` datetime DEFAULT '2000-00-00 00:00:00' COMMENT '资料更新日期',
  `update_id` varchar(50) DEFAULT NULL COMMENT '修改者ID',
  `update_ip` varchar(50) DEFAULT NULL COMMENT '修改者IP',
  `version` int(11) DEFAULT '0' COMMENT 'VERSION',
  `del_flag` char(1) DEFAULT '0' COMMENT '0否1是',
  PRIMARY KEY (`variable_id`,`variable_sub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统变量设置表';

-- ----------------------------
-- Records of tc_sys_variable
-- ----------------------------

INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_0', '游泳', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_1', '网球', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_2', '羽毛球', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_3', '篮球', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_4', '球类私教', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_5', '空手道', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_6', '形体芭蕾', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_7', '爵士街舞', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
INSERT INTO `tc_sys_variable` VALUES ('course_subject', '课程主题', 'course_subject_8', '轮滑', '2014-04-21 21:58:39', null, null, '2000-00-00 00:00:00', null, null, '0', '0');
