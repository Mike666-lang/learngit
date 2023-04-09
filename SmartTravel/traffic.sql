/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : traffic

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2023-04-07 18:39:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cars`
-- ----------------------------
DROP TABLE IF EXISTS `cars`;
CREATE TABLE `cars` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `garage_num` int(5) NOT NULL COMMENT '用户的车库，虚拟车库，表示一个用户多辆车的集合',
  `car_brand` varchar(50) DEFAULT NULL COMMENT '车辆品牌',
  `car_num` varchar(10) NOT NULL COMMENT '车牌号',
  `car_type` varchar(50) DEFAULT NULL COMMENT '车牌类型',
  `restriction_id` int(2) unsigned zerofill DEFAULT NULL COMMENT '限行地区id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of cars
-- ----------------------------
INSERT INTO `cars` VALUES ('1', '10000', 'BYD护卫舰07', '晋BX630', '蓝牌', '02');
INSERT INTO `cars` VALUES ('2', '10000', '红旗HS7', '苏AE6634', '绿牌', '00');
INSERT INTO `cars` VALUES ('3', '1000', 'CF450SR', '晋H0811', '黄牌', '01');

-- ----------------------------
-- Table structure for `etc`
-- ----------------------------
DROP TABLE IF EXISTS `etc`;
CREATE TABLE `etc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `etc_num` int(10) DEFAULT NULL COMMENT 'etc账户',
  `monery` float(7,2) unsigned zerofill NOT NULL COMMENT 'etc余额',
  `bank` varchar(20) NOT NULL COMMENT '开户银行',
  `status` tinyint(1) unsigned zerofill NOT NULL COMMENT '账户状态0正常1冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of etc
-- ----------------------------
INSERT INTO `etc` VALUES ('1', '1000000000', '1000.00', '中国人民天地银行', '0');

-- ----------------------------
-- Table structure for `restriction`
-- ----------------------------
DROP TABLE IF EXISTS `restriction`;
CREATE TABLE `restriction` (
  `restriction_id` int(2) NOT NULL COMMENT '地区id自增',
  `area` varchar(50) NOT NULL COMMENT '地区',
  `policy` varchar(255) DEFAULT NULL COMMENT '限行政策',
  `ok_time` datetime DEFAULT NULL COMMENT '通行时间',
  PRIMARY KEY (`restriction_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of restriction
-- ----------------------------

-- ----------------------------
-- Table structure for `stop`
-- ----------------------------
DROP TABLE IF EXISTS `stop`;
CREATE TABLE `stop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `car_num` varchar(20) NOT NULL COMMENT '车辆号牌',
  `start_time` datetime NOT NULL COMMENT '进入停车场时间',
  `end_time` datetime NOT NULL COMMENT '离开停车场时间',
  `stop_id` int(5) NOT NULL COMMENT '停车场id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of stop
-- ----------------------------
INSERT INTO `stop` VALUES ('1', '晋BX630', '2023-03-31 08:22:15', '2023-03-31 14:22:23', '1');
INSERT INTO `stop` VALUES ('2', '晋BX630', '2023-03-31 12:22:33', '2023-03-31 14:22:39', '1');
INSERT INTO `stop` VALUES ('3', '苏AE6634', '2023-03-31 09:22:59', '2023-03-31 14:23:03', '2');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(20) NOT NULL COMMENT '登录用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `usertype` tinyint(2) unsigned zerofill NOT NULL COMMENT '用户类型0为普通用户，1为管理员',
  `sex` int(3) DEFAULT NULL COMMENT '用户性别',
  `address` varchar(255) DEFAULT NULL COMMENT '用户地址',
  `garage_num` int(5) DEFAULT NULL COMMENT '用户车库编号，虚拟存在，表示一个用户有多个车的集合',
  `etc_num` int(10) DEFAULT NULL COMMENT 'etc账户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '徐长鑫', '123456', '01', '1', '新乡', '666666', '88888888');
INSERT INTO `user` VALUES ('2', '政治家', '123', '00', '1', '中国', '41232421', '2342341');
INSERT INTO `user` VALUES ('10', '政治家', '123', '00', '1', '小日子', '41232421', '2342341');
INSERT INTO `user` VALUES ('11', '周玉祥', '123', '00', '1', '中国', '2142365236', '234213');
INSERT INTO `user` VALUES ('13', '政治家', '123', '00', '0', '中国', '41232421', '2342341');
INSERT INTO `user` VALUES ('14', '政治家', '123', '00', '1', '中国', '66666666', '2342341');
INSERT INTO `user` VALUES ('19', '郑治家', '123', '00', '1', '中国', '4123', '23423');
INSERT INTO `user` VALUES ('20', '王志恒', '1233231', '00', '1', '新乡', '4122', '234223');
INSERT INTO `user` VALUES ('24', '政治家1号', '1233231', '00', '0', '中国', '865', '6756');
INSERT INTO `user` VALUES ('26', '徐长鑫', '123456', '00', '0', null, '0', '0');
