/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : shq

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2016-03-28 15:05:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_back_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_back_user`;
CREATE TABLE `tb_back_user` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `login_name` varchar(40) NOT NULL COMMENT '用户登录名称',
  `password` varchar(40) NOT NULL COMMENT '用户密码',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：（0.正常，1.锁定，2.注销）',
  `name` varchar(40) NOT NULL COMMENT '用户姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `email` varchar(40) DEFAULT NULL COMMENT '电子邮箱',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_back_user
-- ----------------------------
INSERT INTO `tb_back_user` VALUES ('a661a3e0faa84e30ac0eee1731a00489', 'admin', '0740b251b4dcf750444230b9cdf7d3d0', '0', '系统管理员', '1236655555', '1233@223.cn', '去去去1', '2015-11-13 17:51:30');

-- ----------------------------
-- Table structure for tb_params
-- ----------------------------
DROP TABLE IF EXISTS `tb_params`;
CREATE TABLE `tb_params` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `key` varchar(40) DEFAULT NULL COMMENT '参数key',
  `value` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `desc` varchar(200) DEFAULT NULL COMMENT '参数描述',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_params
-- ----------------------------

-- ----------------------------
-- Table structure for tb_perm
-- ----------------------------
DROP TABLE IF EXISTS `tb_perm`;
CREATE TABLE `tb_perm` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `name` varchar(40) NOT NULL COMMENT '资源名称',
  `code` varchar(40) NOT NULL COMMENT '资源权限编码，系统内唯一',
  `type` char(1) NOT NULL COMMENT '类型：0 菜单 1按钮',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：0 启用，1禁用',
  `order_num` int(2) NOT NULL DEFAULT '0' COMMENT '菜单索引，排列顺序',
  `parent_id` varchar(40) DEFAULT NULL COMMENT '上级ID',
  `system_id` varchar(40) NOT NULL COMMENT '系统ID',
  `icon_url` varchar(200) DEFAULT NULL COMMENT '菜单图标地址',
  `page_url` varchar(200) DEFAULT NULL COMMENT '请求url地址',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_perm
-- ----------------------------
INSERT INTO `tb_perm` VALUES ('27e6e3620be4491187d2326621aaebe3', '系统管理', 'usercenter-system', '0', '0', '4', '30fe6df68d114cbba460b3bc02469516', '529d2fb6c84d4c50af49104cf4e9f8ca', '-880,-280', '/usercenter/system/index.do', '', '2015-11-18 11:24:58');
INSERT INTO `tb_perm` VALUES ('30fe6df68d114cbba460b3bc02469516', '系统管理', 'usercenter', '0', '0', '0', '', '529d2fb6c84d4c50af49104cf4e9f8ca', '0,-560', '', '', '2015-11-18 11:14:09');
INSERT INTO `tb_perm` VALUES ('4c5fbea288f94fc594f75279ccf9b0d3', '后台用户', 'centeruser-backuser', '0', '0', '1', '30fe6df68d114cbba460b3bc02469516', '529d2fb6c84d4c50af49104cf4e9f8ca', '-880,-70', '/usercenter/backUser/index.do', '', '2015-11-18 11:15:21');
INSERT INTO `tb_perm` VALUES ('5770392895264c49b0b3de06da574924', '权限管理', 'usercenter-perm', '0', '0', '3', '30fe6df68d114cbba460b3bc02469516', '529d2fb6c84d4c50af49104cf4e9f8ca', '-880,-0', '/usercenter/perm/index.do', '', '2015-11-18 11:25:40');
INSERT INTO `tb_perm` VALUES ('7c54ffef146e4b0ba76fe3fe0f882393', '系统参数', 'usercenter-params', '0', '0', '5', '30fe6df68d114cbba460b3bc02469516', '529d2fb6c84d4c50af49104cf4e9f8ca', '-880,-630', '/usercenter/params/index.do', '', '2016-01-26 09:25:46');
INSERT INTO `tb_perm` VALUES ('88404a25b4414e88be1f56a61ee49ae5', '角色管理', 'usercenter-role', '0', '0', '2', '30fe6df68d114cbba460b3bc02469516', '529d2fb6c84d4c50af49104cf4e9f8ca', '-880,-210', '/usercenter/role/index.do', '', '2016-01-07 16:18:15');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `name` varchar(40) NOT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('47f2bc96746747bdab5c48eaad57e42f', '系统管理员', '负责权限分配', '2015-11-13 17:18:57');

-- ----------------------------
-- Table structure for tb_system
-- ----------------------------
DROP TABLE IF EXISTS `tb_system`;
CREATE TABLE `tb_system` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `name` varchar(40) NOT NULL COMMENT '系统名称',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：0 正常 1 维护中 2 关闭',
  `code` varchar(40) NOT NULL COMMENT '系统编码，唯一索引',
  `url` varchar(200) DEFAULT NULL COMMENT '访问地址:，如：http://www.dytj.com',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_system_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_system
-- ----------------------------
INSERT INTO `tb_system` VALUES ('529d2fb6c84d4c50af49104cf4e9f8ca', '运营系统', '0', 'shq-boss', 'http://boss.shq.com', '', '2015-11-16 13:31:14');

-- ----------------------------
-- Table structure for tr_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `tr_role_perm`;
CREATE TABLE `tr_role_perm` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `role_id` varchar(40) NOT NULL COMMENT '角色ID',
  `perm_id` varchar(40) NOT NULL COMMENT '资源权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tr_role_perm
-- ----------------------------
INSERT INTO `tr_role_perm` VALUES ('0505151cd6fa475fbedcfa46bf8b13ad', '47f2bc96746747bdab5c48eaad57e42f', '27e6e3620be4491187d2326621aaebe3');
INSERT INTO `tr_role_perm` VALUES ('4f1e0db1b79e4ce09b47cf1bc47f8c11', '47f2bc96746747bdab5c48eaad57e42f', '30fe6df68d114cbba460b3bc02469516');
INSERT INTO `tr_role_perm` VALUES ('69bf9d1d25cd44a2b21859f108f56e58', '47f2bc96746747bdab5c48eaad57e42f', '7c54ffef146e4b0ba76fe3fe0f882393');
INSERT INTO `tr_role_perm` VALUES ('a8c4669ad0b048689ed59ffa8b58b8c7', '47f2bc96746747bdab5c48eaad57e42f', '4c5fbea288f94fc594f75279ccf9b0d3');
INSERT INTO `tr_role_perm` VALUES ('c58b6bf99dcc4f9f859ca61721f86ed9', '47f2bc96746747bdab5c48eaad57e42f', '5770392895264c49b0b3de06da574924');
INSERT INTO `tr_role_perm` VALUES ('fe7aae67adf3467d9457db2e74a9b7c9', '47f2bc96746747bdab5c48eaad57e42f', '88404a25b4414e88be1f56a61ee49ae5');

-- ----------------------------
-- Table structure for tr_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tr_user_role`;
CREATE TABLE `tr_user_role` (
  `id` varchar(40) NOT NULL,
  `user_id` varchar(40) NOT NULL COMMENT '用户ID',
  `role_id` varchar(40) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tr_user_role
-- ----------------------------
INSERT INTO `tr_user_role` VALUES ('27e6e3620be4491187d2326621aaebe4', 'a661a3e0faa84e30ac0eee1731a00489', '47f2bc96746747bdab5c48eaad57e42f');
