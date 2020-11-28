/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.68.131_3306
 Source Server Type    : MySQL
 Source Server Version : 50648
 Source Host           : 192.168.68.131:3306
 Source Schema         : db_dev_rixing

 Target Server Type    : MySQL
 Target Server Version : 50648
 File Encoding         : 65001

 Date: 28/11/2020 17:57:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for de_test
-- ----------------------------
DROP TABLE IF EXISTS `de_test`;
CREATE TABLE `de_test`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of de_test
-- ----------------------------
INSERT INTO `de_test` VALUES (1, NULL, NULL, NULL, NULL, 'zs@qq.com');
INSERT INTO `de_test` VALUES (2, NULL, NULL, NULL, NULL, 'zs@qq.com');
INSERT INTO `de_test` VALUES (3, NULL, NULL, NULL, NULL, 'zs@qq.com');
INSERT INTO `de_test` VALUES (4, NULL, NULL, NULL, NULL, 'zs@qq.com');
INSERT INTO `de_test` VALUES (5, NULL, NULL, NULL, NULL, 'zs@qq.com');
INSERT INTO `de_test` VALUES (6, NULL, NULL, NULL, NULL, 'zs@qq.com');
INSERT INTO `de_test` VALUES (7, NULL, NULL, NULL, NULL, 'zs@qq.com');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `level` smallint(6) NULL DEFAULT NULL COMMENT '日志级别 1.TRACE < 2.DEBUG < 3.INFO < 4.WARN < 5.ERROR',
  `module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志记录模块名称',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志详细内容',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志描述',
  `login_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_log_ibfk_1`(`user_id`) USING BTREE,
  CONSTRAINT `sys_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (2, '2020-11-27 16:53:43', 1, 'super', NULL, 2, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:add,[参数]:entity={\"address\":\"\",\"archivesCenter\":1,\"description\":\"\",\"phone\":\"\",\"qzhName\":\"1111\",\"qzhNumber\":\"1111\"}&,[IP]:0:0:0:0:0:0:0:1', '添加全宗号信息', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES (3, '2020-11-27 16:59:32', 1, 'super', NULL, 2, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:add,[参数]:entity={\"address\":\"\",\"archivesCenter\":1,\"description\":\"\",\"phone\":\"\",\"qzhName\":\"1111\",\"qzhNumber\":\"1111\"}&,[IP]:0:0:0:0:0:0:0:1', '添加全宗号信息', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES (4, '2020-11-27 18:32:43', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=10&num=1&qzhName=\"11\"&,[IP]:0:0:0:0:0:0:0:1', '查询全宗号信息列表', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES (5, '2020-11-28 13:57:33', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=\"1\"&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (6, '2020-11-28 14:10:38', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (7, '2020-11-28 14:13:24', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (8, '2020-11-28 14:22:17', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (9, '2020-11-28 14:22:24', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (10, '2020-11-28 15:29:14', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (11, '2020-11-28 15:29:26', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (12, '2020-11-28 15:42:13', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.250', '查询全宗号信息列表', '192.168.2.250');
INSERT INTO `sys_log` VALUES (13, '2020-11-28 15:45:08', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.250', '查询全宗号信息列表', '192.168.2.250');
INSERT INTO `sys_log` VALUES (14, '2020-11-28 15:45:25', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.250', '查询全宗号信息列表', '192.168.2.250');
INSERT INTO `sys_log` VALUES (15, '2020-11-28 15:49:18', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (16, '2020-11-28 15:49:52', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (17, '2020-11-28 15:49:59', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (18, '2020-11-28 15:55:29', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (19, '2020-11-28 15:56:18', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (20, '2020-11-28 15:58:44', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.203', '查询全宗号信息列表', '192.168.2.203');
INSERT INTO `sys_log` VALUES (21, '2020-11-28 16:05:07', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.250', '查询全宗号信息列表', '192.168.2.250');
INSERT INTO `sys_log` VALUES (22, '2020-11-28 16:05:07', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.250', '查询全宗号信息列表', '192.168.2.250');
INSERT INTO `sys_log` VALUES (23, '2020-11-28 17:20:30', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=1&num=1&qzhName=null&,[IP]:0:0:0:0:0:0:0:1', '查询全宗号信息列表', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES (24, '2020-11-28 17:23:07', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:127.0.0.1', '查询全宗号信息列表', '127.0.0.1');
INSERT INTO `sys_log` VALUES (25, '2020-11-28 17:25:11', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:192.168.2.250', '查询全宗号信息列表', '192.168.2.250');
INSERT INTO `sys_log` VALUES (26, '2020-11-28 17:31:05', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:127.0.0.1', '查询全宗号信息列表', '127.0.0.1');
INSERT INTO `sys_log` VALUES (27, '2020-11-28 17:33:47', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findById,[参数]:id=1&,[IP]:0:0:0:0:0:0:0:1', '查询全宗号信息', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_log` VALUES (28, '2020-11-28 17:42:54', 1, 'super', '马龙飞', 3, '系统管理', '[类名]:com.yintu.rixing.system.SysQzhController,[方法]:findPage,[参数]:size=15&num=1&qzhName=\"\"&,[IP]:127.0.0.1', '查询全宗号信息列表', '127.0.0.1');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `parent_id` int(11) NOT NULL COMMENT '父节点主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `priority` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `authorized_name` smallint(6) NULL DEFAULT NULL COMMENT '授权名称',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相对地址',
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `menu` smallint(6) NULL DEFAULT NULL COMMENT '是否是菜单项 1.是 0.否',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由转向',
  `img_path` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单项显示的图片地址',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '模块状态：1.可用 0. 禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1428 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_qzh
-- ----------------------------
DROP TABLE IF EXISTS `sys_qzh`;
CREATE TABLE `sys_qzh`  (
  `id` int(11) NOT NULL COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `qzh_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全宗号名称',
  `qzh_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全宗号',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `archives_center` smallint(6) NULL DEFAULT NULL COMMENT '是否是档案馆 1.是 0.否',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `qzh_number`(`qzh_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统全宗号表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_qzh
-- ----------------------------
INSERT INTO `sys_qzh` VALUES (1, '', '2020-11-27 11:07:36', '', '2020-11-27 11:07:41', '全宗号名称1', 'BCD123', '', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `default_role` smallint(6) NULL DEFAULT NULL COMMENT '是否作为默认用户角色 1.是 0.否',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL,
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_role_permission_ibfk_1`(`role_id`) USING BTREE,
  INDEX `sys_role_permission_ibfk_2`(`permission_id`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色权限中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `true_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
  `certificate_type` smallint(6) NULL DEFAULT NULL COMMENT '证件类型',
  `certificate_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `postal_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话或者手机号码',
  `order_number` int(11) NULL DEFAULT NULL COMMENT '排序编号',
  `select_user` smallint(6) NULL DEFAULT NULL COMMENT '查档用户 1.是 0.否',
  `account_expired` smallint(6) NOT NULL COMMENT '账户过期',
  `account_locked` smallint(6) NOT NULL COMMENT '账户锁定',
  `credentials_expired` smallint(6) NOT NULL COMMENT '密码过期',
  `account_enabled` smallint(6) NOT NULL COMMENT '账户禁用',
  `auth_type` smallint(6) NOT NULL COMMENT '用户类型 0.普通用户 1.管理员用户',
  `qzh_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全宗号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_user_ibfk_1`(`qzh_number`) USING BTREE,
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`qzh_number`) REFERENCES `sys_qzh` (`qzh_number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '', '2020-11-27 11:03:24', '', '2020-11-27 11:03:27', 'super', '$2a$10$5JOpGerCZFUMUunh9AIZO.GFTfW5Cxwt12..qaF35M2YRq71zY1DW', '马龙飞', 1, '410184199702068318', '451100', '1107201045@qq.com', '河南省新郑市辛店镇', '15038233127', 1, 1, 0, 0, 0, 1, 1, 'BCD123');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_user_role_ibfk_1`(`user_id`) USING BTREE,
  INDEX `sys_user_role_ibfk_2`(`role_id`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
