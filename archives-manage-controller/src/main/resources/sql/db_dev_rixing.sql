/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.68.132_3306
 Source Server Type    : MySQL
 Source Server Version : 50648
 Source Host           : 192.168.68.132:3306
 Source Schema         : db_dev_rixing

 Target Server Type    : MySQL
 Target Server Version : 50648
 File Encoding         : 65001

 Date: 27/01/2021 16:53:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_archives_library_file
-- ----------------------------
DROP TABLE IF EXISTS `data_archives_library_file`;
CREATE TABLE `data_archives_library_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `original_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始文件名',
  `path` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
  `size` double NOT NULL COMMENT '文件大小',
  `unit` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件大小单位（B、KB、MB、GB）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `request_mapping` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求映射',
  `type` smallint(6) NOT NULL COMMENT '文件类型 1.电子文件 2.扫描文件',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `order` int(11) NOT NULL COMMENT '顺序',
  `archives_library_id` int(11) NOT NULL COMMENT '档案库id',
  `data_id` int(11) NOT NULL COMMENT '动态表id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_archives_library_id`(`archives_library_id`) USING BTREE,
  CONSTRAINT `data_archives_library_file_ibfk_1` FOREIGN KEY (`archives_library_id`) REFERENCES `sys_archives_library` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据档案库文件表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of data_archives_library_file
-- ----------------------------
INSERT INTO `data_archives_library_file` VALUES (128, 'huxiaowen', '2021-01-26 19:28:20', 'huxiaowen', '2021-01-26 19:39:55', '档案收集模板测试1611660249281.xlsx', 'C:\\data\\archives-manage\\files', 8.61, 'KB', 'dc7e044aac244a63a585fab319f0232e.xlsx', 'http://192.168.2.250:8080/archivesManage/files/dc7e044aac244a63a585fab319f0232e.xlsx', 1, '2221111', 23, 124, 2);

-- ----------------------------
-- Table structure for data_dagas
-- ----------------------------
DROP TABLE IF EXISTS `data_dagas`;
CREATE TABLE `data_dagas`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `archives_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '档号',
  `topic_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '题名',
  `retention_period` smallint(20) NOT NULL COMMENT '保管期限',
  `security_level` smallint(20) NOT NULL COMMENT '密级',
  `filing_annual` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '归档年度',
  `department_id` int(11) NOT NULL COMMENT '组织机构',
  `status` smallint(6) NOT NULL COMMENT '档案状态：0.档案收集 1.临时库 2.整理库 3.正式库 4.回收站 5.销毁库 6.回退管理 7.病档管理 8.档案销毁',
  `status_field1` smallint(6) NOT NULL COMMENT '档案状态预留字段1',
  `operation_time _field1` datetime(0) NOT NULL COMMENT '档案操作时间预留字段1',
  `status_field2` smallint(6) NOT NULL COMMENT '档案状态预留字段2',
  `operation_time _field2` datetime(0) NOT NULL COMMENT '档案操作时间预留字段2',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '11' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_dagas
-- ----------------------------

-- ----------------------------
-- Table structure for data_dagas_rollback_info
-- ----------------------------
DROP TABLE IF EXISTS `data_dagas_rollback_info`;
CREATE TABLE `data_dagas_rollback_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `archives_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '档号',
  `topic_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '题名',
  `retention_period` smallint(20) NOT NULL COMMENT '保管期限',
  `security_level` smallint(20) NOT NULL COMMENT '密级',
  `filing_annual` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '归档年度',
  `department_id` int(11) NOT NULL COMMENT '组织机构',
  `status` smallint(6) NOT NULL COMMENT '档案状态：0.档案收集 1.临时库 2.整理库 3.正式库 4.回收站 5.销毁库 6.回退管理 7.病档管理 8.档案销毁',
  `status_field1` smallint(6) NOT NULL COMMENT '档案状态预留字段1',
  `operation_time _field1` datetime(0) NOT NULL COMMENT '档案操作时间预留字段1',
  `status_field2` smallint(6) NOT NULL COMMENT '档案状态预留字段2',
  `operation_time _field2` datetime(0) NOT NULL COMMENT '档案操作时间预留字段2',
  `data_id` int(11) NOT NULL COMMENT '动态表id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `data_id`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '11-回退管理记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_dagas_rollback_info
-- ----------------------------

-- ----------------------------
-- Table structure for de_test
-- ----------------------------
DROP TABLE IF EXISTS `de_test`;
CREATE TABLE `de_test`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` smallint(11) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `d` date NULL DEFAULT NULL,
  `f` time(0) NULL DEFAULT NULL,
  `g` datetime(0) NULL DEFAULT NULL,
  `h` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of de_test
-- ----------------------------
INSERT INTO `de_test` VALUES (3, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (19, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (31, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (36, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (52, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (53, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (54, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (55, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (56, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (57, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (58, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (59, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (60, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (61, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');
INSERT INTO `de_test` VALUES (62, 'super', '2021-01-15 13:35:07', 'zs', 20, '11', '2021-01-27', '14:49:46', '2021-01-27 14:49:49', '2021-01-27 14:49:57');

-- ----------------------------
-- Table structure for make_borrow
-- ----------------------------
DROP TABLE IF EXISTS `make_borrow`;
CREATE TABLE `make_borrow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '借阅申请表id',
  `fileid` int(11) NULL DEFAULT NULL COMMENT '文件id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借阅人姓名',
  `identity` int(11) NULL DEFAULT NULL COMMENT '借阅人身份',
  `certificate_type` int(11) NULL DEFAULT NULL COMMENT '证件类型',
  `certificate_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `college_or_unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院或者单位',
  `phone_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `purpose` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查阅目的',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `borrow_type` int(11) NULL DEFAULT NULL COMMENT '借阅类型 1：电子借阅  2：实体借阅',
  `borrow_start_time` datetime(0) NULL DEFAULT NULL COMMENT '借阅开始时间',
  `borrow_end_time` datetime(0) NULL DEFAULT NULL COMMENT '借阅结束时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '利用中心的借阅申请表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of make_borrow
-- ----------------------------
INSERT INTO `make_borrow` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'liu', '2021-01-12 18:24:23', 'liu', '2021-01-12 18:24:23');
INSERT INTO `make_borrow` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'liu', '2021-01-12 19:32:39', 'liu', '2021-01-12 19:32:39');
INSERT INTO `make_borrow` VALUES (21, 11, '胡晓雯', 1, 1, '410527199811129743', '港然网络科技有限公司', '18811597991', '1123', '', 1, '2021-01-12 00:00:00', '2021-01-23 00:00:00', 'huxiaowen', '2021-01-13 16:16:03', 'huxiaowen', '2021-01-13 16:16:44');
INSERT INTO `make_borrow` VALUES (23, 111, '王美丽', 3, 1, '410527199811129743', '港然网络科技有限公司', '18811597991', 'qqqq', '', 2, '2021-01-01 00:00:00', '2021-02-26 00:00:00', 'huxiaowen', '2021-01-13 16:21:20', 'huxiaowen', '2021-01-13 16:21:20');

-- ----------------------------
-- Table structure for sec_data_backup
-- ----------------------------
DROP TABLE IF EXISTS `sec_data_backup`;
CREATE TABLE `sec_data_backup`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备份名称',
  `backup_time` datetime(0) NOT NULL COMMENT '备份时间',
  `backup_path` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备份路径',
  `backup_file_size` double NOT NULL COMMENT '文件大小（MB）',
  `request_mapping` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求映射',
  `recovery_time` datetime(0) NULL DEFAULT NULL COMMENT '还原时间',
  `table_count` int(11) NOT NULL COMMENT '表个数',
  `record_count` int(11) NOT NULL COMMENT '记录数',
  `record_size` double NOT NULL COMMENT '记录大小（MB）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '安全数据备份表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sec_data_backup
-- ----------------------------

-- ----------------------------
-- Table structure for sec_log
-- ----------------------------
DROP TABLE IF EXISTS `sec_log`;
CREATE TABLE `sec_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人',
  `level` smallint(6) NOT NULL COMMENT '日志级别 1.TRACE < 2.DEBUG < 3.INFO < 4.WARN < 5.ERROR',
  `module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志记录模块名称',
  `context` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志内容',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志描述',
  `login_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21917 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '安全日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sec_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_approval_process
-- ----------------------------
DROP TABLE IF EXISTS `sys_approval_process`;
CREATE TABLE `sys_approval_process`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `approval_model` smallint(6) NOT NULL COMMENT '审批模式 1.固定 2.灵活',
  `approval` smallint(6) NOT NULL COMMENT '是否审批 1.是 0.否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统审批流程表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_approval_process
-- ----------------------------
INSERT INTO `sys_approval_process` VALUES (34, 'super', '2021-01-16 18:26:49', 'super', '2021-01-16 18:26:49', '审批流1', 1, 1);
INSERT INTO `sys_approval_process` VALUES (35, 'super', '2021-01-16 18:28:05', 'super', '2021-01-16 18:28:05', '审批流2', 1, 1);
INSERT INTO `sys_approval_process` VALUES (36, 'super', '2021-01-16 18:32:58', 'super', '2021-01-16 18:33:12', '审批流3', 1, 1);
INSERT INTO `sys_approval_process` VALUES (37, 'super', '2021-01-16 18:39:09', 'super', '2021-01-16 18:39:09', '审批流4', 2, 1);

-- ----------------------------
-- Table structure for sys_approval_process_configuration
-- ----------------------------
DROP TABLE IF EXISTS `sys_approval_process_configuration`;
CREATE TABLE `sys_approval_process_configuration`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `approval_process_id` int(11) NOT NULL COMMENT '审批流程id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `order` smallint(11) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_approval_process_id`(`approval_process_id`) USING BTREE,
  INDEX `fk_role_id`(`role_id`) USING BTREE,
  INDEX `fk_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `sys_approval_process_configuration_ibfk_1` FOREIGN KEY (`approval_process_id`) REFERENCES `sys_approval_process` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_approval_process_configuration_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_approval_process_configuration_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统审批流程配置中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_approval_process_configuration
-- ----------------------------
INSERT INTO `sys_approval_process_configuration` VALUES (47, 'super', '2021-01-16 18:26:49', 'super', '2021-01-16 18:26:49', 34, 25, 12, 1);
INSERT INTO `sys_approval_process_configuration` VALUES (48, 'super', '2021-01-16 18:26:49', 'super', '2021-01-16 18:26:49', 34, 29, 11, 1);
INSERT INTO `sys_approval_process_configuration` VALUES (49, 'super', '2021-01-16 18:26:49', 'super', '2021-01-16 18:26:49', 34, 27, 3, 2);
INSERT INTO `sys_approval_process_configuration` VALUES (50, 'super', '2021-01-16 18:28:05', 'super', '2021-01-16 18:28:05', 35, 29, 11, 1);
INSERT INTO `sys_approval_process_configuration` VALUES (54, 'super', '2021-01-16 18:33:12', 'super', '2021-01-16 18:33:12', 36, 26, 7, 1);

-- ----------------------------
-- Table structure for sys_archives_library
-- ----------------------------
DROP TABLE IF EXISTS `sys_archives_library`;
CREATE TABLE `sys_archives_library`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `parent_id` int(11) NOT NULL COMMENT '父节点主键',
  `number` int(11) NULL DEFAULT NULL COMMENT '档案库编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '档案库名称',
  `data_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key（定义数据库表名）',
  `type` smallint(6) NOT NULL COMMENT '档案库分类 1.目录 2.档案库',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案库描述',
  `template_library_id` int(11) NULL DEFAULT NULL COMMENT '模板库id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_number`(`number`) USING BTREE,
  INDEX `uk_data_key`(`data_key`) USING BTREE,
  INDEX `idx_template_library_id`(`template_library_id`) USING BTREE,
  CONSTRAINT `sys_archives_library_ibfk_1` FOREIGN KEY (`template_library_id`) REFERENCES `sys_template_library` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 120 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统档案库表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_archives_library
-- ----------------------------
INSERT INTO `sys_archives_library` VALUES (15, 'huxiaowen', '2021-01-13 10:35:01', 'huxiaowen', '2021-01-14 15:32:29', -1, NULL, '文书档案目录', '', 1, '文书档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (16, 'huxiaowen', '2021-01-13 10:36:13', 'super', '2021-01-14 13:33:17', -1, NULL, '业务档案目录', '', 1, '业务档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (17, 'huxiaowen', '2021-01-13 10:36:22', 'super', '2021-01-14 13:39:36', -1, NULL, '实物档案目录', '', 1, '实物档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (18, 'huxiaowen', '2021-01-13 10:36:31', 'super', '2021-01-14 13:39:47', -1, NULL, '会计档案目录', '', 1, '会计档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (19, 'huxiaowen', '2021-01-13 10:36:39', 'super', '2021-01-14 13:42:35', -1, NULL, '照片档案目录', '', 1, '照片档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (20, 'huxiaowen', '2021-01-13 10:41:51', 'super', '2021-01-14 13:42:47', -1, NULL, '音像档案目录', '', 1, '音像档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (27, 'super', '2021-01-14 13:45:15', 'super', '2021-01-14 13:45:15', -1, NULL, '人事档案目录', '', 1, '人事档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (28, 'super', '2021-01-14 13:45:25', 'super', '2021-01-14 13:45:25', -1, NULL, '科技档案目录', '', 1, '科技档案目录描述', NULL);
INSERT INTO `sys_archives_library` VALUES (119, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', 15, 11, '11', 'dagas', 2, '11', 62);

-- ----------------------------
-- Table structure for sys_archives_library_field
-- ----------------------------
DROP TABLE IF EXISTS `sys_archives_library_field`;
CREATE TABLE `sys_archives_library_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称（注释或者描述）',
  `data_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key（定义数据库字段名）',
  `length` int(11) NOT NULL COMMENT '字段长度',
  `required` smallint(6) NOT NULL COMMENT '字段是否必填 1.是 0.否',
  `index` smallint(6) NOT NULL COMMENT '字段是否是索引 1.是 0.否',
  `order` int(11) NOT NULL COMMENT '字段顺序',
  `query` smallint(6) NOT NULL COMMENT '字段是否在页面查询',
  `title` smallint(6) NOT NULL COMMENT '字段是否在页面表头是否显示',
  `form` smallint(6) NOT NULL COMMENT '字段是否在页面表单是否显示',
  `system` smallint(6) NOT NULL COMMENT '字段是否默认',
  `archives_library_id` int(11) NOT NULL COMMENT '档案库id',
  `template_library_id` int(11) NULL DEFAULT NULL COMMENT '模板库id',
  `template_library_field_type_id` int(11) NOT NULL COMMENT '模板库字段类型id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_data_key_archives_library_id`(`data_key`, `archives_library_id`) USING BTREE,
  INDEX `idx_template_library_field_type_id`(`template_library_field_type_id`) USING BTREE,
  INDEX `idx_template_library_id`(`template_library_id`) USING BTREE,
  INDEX `idx_archives_library_id`(`archives_library_id`) USING BTREE,
  CONSTRAINT `sys_archives_library_field_ibfk_1` FOREIGN KEY (`archives_library_id`) REFERENCES `sys_archives_library` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_archives_library_field_ibfk_2` FOREIGN KEY (`template_library_id`) REFERENCES `sys_template_library` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_archives_library_field_ibfk_3` FOREIGN KEY (`template_library_field_type_id`) REFERENCES `sys_template_library_field_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 342 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统档案库字段表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_archives_library_field
-- ----------------------------
INSERT INTO `sys_archives_library_field` VALUES (326, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '主键id', 'id', 11, 1, 0, 1, 0, 0, 0, 1, 119, NULL, 5);
INSERT INTO `sys_archives_library_field` VALUES (327, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '创建人', 'create_by', 20, 1, 0, 2, 0, 0, 0, 1, 119, NULL, 1);
INSERT INTO `sys_archives_library_field` VALUES (328, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '创建时间', 'create_time', 0, 1, 0, 3, 0, 0, 0, 1, 119, NULL, 6);
INSERT INTO `sys_archives_library_field` VALUES (329, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '修改人', 'modified_by', 20, 1, 0, 4, 0, 0, 0, 1, 119, NULL, 1);
INSERT INTO `sys_archives_library_field` VALUES (330, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '修改时间', 'modified_time', 0, 1, 0, 5, 0, 0, 0, 1, 119, NULL, 6);
INSERT INTO `sys_archives_library_field` VALUES (331, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '档号', 'archives_num', 50, 1, 0, 6, 0, 1, 0, 1, 119, NULL, 1);
INSERT INTO `sys_archives_library_field` VALUES (332, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '题名', 'topic_name', 20, 1, 0, 7, 1, 1, 1, 1, 119, NULL, 1);
INSERT INTO `sys_archives_library_field` VALUES (333, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '保管期限', 'retention_period', 20, 1, 0, 8, 1, 1, 1, 1, 119, NULL, 4);
INSERT INTO `sys_archives_library_field` VALUES (334, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '密级', 'security_level', 20, 1, 0, 9, 1, 1, 1, 1, 119, NULL, 4);
INSERT INTO `sys_archives_library_field` VALUES (335, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '归档年度', 'filing_annual', 20, 1, 0, 10, 1, 1, 1, 1, 119, NULL, 1);
INSERT INTO `sys_archives_library_field` VALUES (336, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '组织机构', 'department_id', 11, 1, 0, 11, 1, 1, 1, 1, 119, NULL, 5);
INSERT INTO `sys_archives_library_field` VALUES (337, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '档案状态：0.档案收集 1.临时库 2.整理库 3.正式库 4.回收站 5.销毁库 6.回退管理 7.病档管理 8.档案销毁', 'status', 6, 1, 0, 12, 0, 0, 0, 1, 119, NULL, 4);
INSERT INTO `sys_archives_library_field` VALUES (338, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '档案状态预留字段1', 'status_field1', 6, 1, 0, 13, 0, 0, 0, 1, 119, NULL, 4);
INSERT INTO `sys_archives_library_field` VALUES (339, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '档案操作时间预留字段1', 'operation_time _field1', 0, 1, 0, 14, 0, 0, 0, 1, 119, NULL, 6);
INSERT INTO `sys_archives_library_field` VALUES (340, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '档案状态预留字段2', 'status_field2', 6, 1, 0, 15, 0, 0, 0, 1, 119, NULL, 4);
INSERT INTO `sys_archives_library_field` VALUES (341, 'huxiaowen', '2021-01-27 15:43:32', 'huxiaowen', '2021-01-27 15:43:32', '档案操作时间预留字段2', 'operation_time _field2', 0, 1, 0, 16, 0, 0, 0, 1, 119, NULL, 6);

-- ----------------------------
-- Table structure for sys_archives_library_field_default
-- ----------------------------
DROP TABLE IF EXISTS `sys_archives_library_field_default`;
CREATE TABLE `sys_archives_library_field_default`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称（注释或者描述）',
  `data_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key（定义数据库字段名）',
  `length` int(11) NOT NULL COMMENT '字段长度',
  `required` smallint(6) NOT NULL COMMENT '字段是否必填 1.是 0.否',
  `index` smallint(6) NOT NULL COMMENT '字段是否是索引 1.是 0.否',
  `order` int(11) NOT NULL COMMENT '字段顺序',
  `query` smallint(6) NOT NULL COMMENT '字段是否在页面查询',
  `title` smallint(6) NOT NULL COMMENT '字段是否在页面表头显示',
  `form` smallint(6) NOT NULL COMMENT '字段是否在页面表单显示',
  `template_library_field_type_id` int(11) NOT NULL COMMENT '模板库字段类型id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_data_key`(`data_key`) USING BTREE,
  INDEX `template_library_field_type_id`(`template_library_field_type_id`) USING BTREE,
  CONSTRAINT `sys_archives_library_field_default_ibfk_1` FOREIGN KEY (`template_library_field_type_id`) REFERENCES `sys_template_library_field_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统档案库字段默认表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_archives_library_field_default
-- ----------------------------
INSERT INTO `sys_archives_library_field_default` VALUES (1, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '主键id', 'id', 11, 1, 0, 1, 0, 0, 0, 5);
INSERT INTO `sys_archives_library_field_default` VALUES (2, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '创建人', 'create_by', 20, 1, 0, 2, 0, 0, 0, 1);
INSERT INTO `sys_archives_library_field_default` VALUES (3, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '创建时间', 'create_time', 0, 1, 0, 3, 0, 0, 0, 6);
INSERT INTO `sys_archives_library_field_default` VALUES (4, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '修改人', 'modified_by', 20, 1, 0, 4, 0, 0, 0, 1);
INSERT INTO `sys_archives_library_field_default` VALUES (5, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '修改时间', 'modified_time', 0, 1, 0, 5, 0, 0, 0, 6);
INSERT INTO `sys_archives_library_field_default` VALUES (6, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '档号', 'archives_num', 50, 1, 0, 6, 0, 1, 0, 1);
INSERT INTO `sys_archives_library_field_default` VALUES (7, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '题名', 'topic_name', 20, 1, 0, 7, 1, 1, 1, 1);
INSERT INTO `sys_archives_library_field_default` VALUES (8, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '保管期限', 'retention_period', 20, 1, 0, 8, 1, 1, 1, 4);
INSERT INTO `sys_archives_library_field_default` VALUES (9, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '密级', 'security_level', 20, 1, 0, 9, 1, 1, 1, 4);
INSERT INTO `sys_archives_library_field_default` VALUES (10, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '归档年度', 'filing_annual', 20, 1, 0, 10, 1, 1, 1, 1);
INSERT INTO `sys_archives_library_field_default` VALUES (11, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '组织机构', 'department_id', 11, 1, 0, 11, 1, 1, 1, 5);
INSERT INTO `sys_archives_library_field_default` VALUES (12, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '档案状态：0.档案收集 1.临时库 2.整理库 3.正式库 4.回收站 5.销毁库 6.回退管理 7.病档管理 8.档案销毁', 'status', 6, 1, 0, 12, 0, 0, 0, 4);
INSERT INTO `sys_archives_library_field_default` VALUES (13, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '档案状态预留字段1', 'status_field1', 6, 1, 0, 13, 0, 0, 0, 4);
INSERT INTO `sys_archives_library_field_default` VALUES (14, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '档案操作时间预留字段1', 'operation_time _field1', 0, 1, 0, 14, 0, 0, 0, 6);
INSERT INTO `sys_archives_library_field_default` VALUES (15, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '档案状态预留字段2', 'status_field2', 6, 1, 0, 15, 0, 0, 0, 4);
INSERT INTO `sys_archives_library_field_default` VALUES (16, 'super', '2021-01-22 12:41:50', 'super', '2021-01-22 12:42:08', '档案操作时间预留字段2', 'operation_time _field2', 0, 1, 0, 16, 0, 0, 0, 6);

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `modified_time` datetime(0) NOT NULL COMMENT '更新时间',
  `parent_id` int(11) NOT NULL COMMENT '父节点主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统部门表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES (8, 'huxiaowen', '2021-01-05 11:50:27', 'super', '2021-01-15 20:06:13', -1, '办公室');
INSERT INTO `sys_department` VALUES (10, 'huxiaowen', '2021-01-05 11:50:53', 'super', '2021-01-15 20:06:23', -1, '财务部');
INSERT INTO `sys_department` VALUES (11, 'huxiaowen', '2021-01-05 11:51:00', 'super', '2021-01-15 20:06:28', -1, '市场部');
INSERT INTO `sys_department` VALUES (12, 'huxiaowen', '2021-01-05 11:51:12', 'super', '2021-01-15 20:06:36', -1, '商务部');
INSERT INTO `sys_department` VALUES (13, 'huxiaowen', '2021-01-05 11:51:31', 'super', '2021-01-15 20:06:48', -1, '云计算事业部');
INSERT INTO `sys_department` VALUES (14, 'huxiaowen', '2021-01-05 11:51:40', 'super', '2021-01-15 20:07:18', -1, '档案数据事业部');
INSERT INTO `sys_department` VALUES (16, 'huxiaowen', '2021-01-08 09:45:40', 'huxiaowen', '2021-01-08 09:50:04', 15, '测试机构子部门');
INSERT INTO `sys_department` VALUES (17, 'huxiaowen', '2021-01-08 09:45:54', 'huxiaowen', '2021-01-08 09:46:14', 16, '测试机构第二子部门22');
INSERT INTO `sys_department` VALUES (19, 'huxiaowen', '2021-01-08 09:47:59', 'huxiaowen', '2021-01-08 09:47:59', 18, '22');
INSERT INTO `sys_department` VALUES (20, 'huxiaowen', '2021-01-08 09:49:32', 'huxiaowen', '2021-01-08 09:49:37', 18, '2222');
INSERT INTO `sys_department` VALUES (21, 'super', '2021-01-15 20:07:26', 'super', '2021-01-15 20:07:26', -1, '档案工程部');
INSERT INTO `sys_department` VALUES (22, 'super', '2021-01-15 20:07:34', 'super', '2021-01-15 20:07:34', -1, '外联合作部');
INSERT INTO `sys_department` VALUES (23, 'super', '2021-01-15 20:07:45', 'super', '2021-01-15 20:07:45', -1, '后勤保证部');
INSERT INTO `sys_department` VALUES (24, 'super', '2021-01-15 20:07:51', 'super', '2021-01-15 20:07:51', -1, '董事会');
INSERT INTO `sys_department` VALUES (25, 'super', '2021-01-15 20:07:57', 'super', '2021-01-15 20:07:57', -1, '人事部');

-- ----------------------------
-- Table structure for sys_department_qzh
-- ----------------------------
DROP TABLE IF EXISTS `sys_department_qzh`;
CREATE TABLE `sys_department_qzh`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全宗号名称',
  `number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全宗号',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `department_id` int(11) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_number`(`number`) USING BTREE,
  INDEX `fk_department_id`(`department_id`) USING BTREE,
  CONSTRAINT `sys_department_qzh_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统全宗号表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_department_qzh
-- ----------------------------
INSERT INTO `sys_department_qzh` VALUES (30, 'super', '2021-01-11 16:56:58', 'super', '2021-01-11 16:56:58', '全宗号名称3', '5FFC12DAC2266C599F8FBFD6', '111', 14);
INSERT INTO `sys_department_qzh` VALUES (33, 'huxiaowen', '2021-01-11 17:33:55', 'super', '2021-01-15 20:10:28', '全宗号名称2', '5FFC1B82C22606C5B38C0669', '哈哈哈哈哈', 8);
INSERT INTO `sys_department_qzh` VALUES (46, 'huxiaowen', '2021-01-11 17:38:18', 'super', '2021-01-15 20:10:19', '全宗号名称1', '5FFC1C8AC2264E7F3AD7F0B0', '11', 8);

-- ----------------------------
-- Table structure for sys_department_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_department_user`;
CREATE TABLE `sys_department_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键用户',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `modified_time` datetime(0) NOT NULL COMMENT '更新时间',
  `department_id` int(11) NOT NULL COMMENT '部门id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_department_user_ibfk_1`(`department_id`) USING BTREE,
  INDEX `sys_department_user_ibfk_2`(`user_id`) USING BTREE,
  CONSTRAINT `sys_department_user_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_department_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统部门用户中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_department_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message`  (
  `id` int(5) NULL DEFAULT NULL,
  `niandu` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年度',
  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `feileihao` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类号',
  `entityflh` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实体分类号',
  `storagelife` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保管期限',
  `anjuanhao` int(10) NULL DEFAULT NULL COMMENT '案卷号',
  `danghao` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档号',
  `timing` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '题名',
  `shuruyuan` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入员',
  `zerenzhe` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '责任者',
  `gddw` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归档单位',
  `starttime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开起日期',
  `stoptime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '截止日期',
  `Archivelogo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归档标志',
  `futiming` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副题名',
  `updatetime` date NULL DEFAULT NULL COMMENT '更新时间',
  `totaljnumber` int(10) NULL DEFAULT NULL COMMENT '总件数',
  `totalyenumber` int(10) NULL DEFAULT NULL COMMENT '总页数',
  `cunzhi` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存址',
  `zaitileixing` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '载体类型',
  `lijuantime` date NULL DEFAULT NULL COMMENT '立卷时间',
  `wenzhong` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文种',
  `beizhu` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `jieshouren` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收人',
  `jieshoutime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_message
-- ----------------------------

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
  `menu` smallint(6) NOT NULL COMMENT '是否是菜单项 1.是 0.否',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相对地址',
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `authorized_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权名称',
  `icon_cls` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单项显示的图标',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由转向',
  `priority` int(11) NULL DEFAULT NULL COMMENT '优先级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (3, 'huxiaowen', '2021-01-04 09:56:56', 'huxiaowen', '2021-01-15 14:16:03', -1, '首页', 1, NULL, NULL, 'SY', 'iconfont iconshouye', '/homechild', 1);
INSERT INTO `sys_permission` VALUES (4, 'huxiaowen', '2021-01-04 10:46:03', 'huxiaowen', '2021-01-15 16:37:38', -1, '个人中心', 1, NULL, NULL, 'GRZX', 'iconfont icongeren1', NULL, 2);
INSERT INTO `sys_permission` VALUES (5, 'huxiaowen', '2021-01-04 10:48:17', 'huxiaowen', '2021-01-04 10:51:22', 4, '借阅管理', 1, NULL, NULL, 'JYGL', NULL, '/personalread', 3);
INSERT INTO `sys_permission` VALUES (6, 'huxiaowen', '2021-01-04 10:49:06', 'huxiaowen', '2021-01-18 19:07:13', 4, '我的收藏', 1, NULL, NULL, 'WDSC', NULL, '/myCollection', 4);
INSERT INTO `sys_permission` VALUES (7, 'huxiaowen', '2021-01-04 10:49:25', 'huxiaowen', '2021-01-15 14:19:41', -1, '利用中心', 1, NULL, NULL, 'LYZX', 'iconfont iconliyonglinshiyongdishenpi', NULL, 3);
INSERT INTO `sys_permission` VALUES (8, 'huxiaowen', '2021-01-04 10:49:44', 'huxiaowen', '2021-01-15 15:37:09', 7, '借阅申请', 1, NULL, NULL, 'JYSQ', NULL, '/loanApplication', 7);
INSERT INTO `sys_permission` VALUES (9, 'huxiaowen', '2021-01-04 10:50:15', 'huxiaowen', '2021-01-15 15:37:19', 7, '电子借阅管理', 1, NULL, NULL, 'DZJYGL', NULL, '/electronicBorrowing', 8);
INSERT INTO `sys_permission` VALUES (10, 'huxiaowen', '2021-01-04 10:50:34', 'huxiaowen', '2021-01-15 15:37:29', 7, '实体借阅管理', 1, NULL, NULL, 'STJYGL', NULL, '/physicalBorrowing', 9);
INSERT INTO `sys_permission` VALUES (11, 'huxiaowen', '2021-01-04 10:52:34', 'huxiaowen', '2021-01-15 15:37:39', 7, '档案检索', 1, NULL, NULL, 'DAJS', NULL, '/archivesRetrieval', 10);
INSERT INTO `sys_permission` VALUES (12, 'super', '2021-01-09 14:37:46', 'huxiaowen', '2021-01-15 14:19:58', -1, '数据中心', 1, NULL, NULL, 'SJZX', 'iconfont iconjiekouguanli', NULL, 3);
INSERT INTO `sys_permission` VALUES (13, 'super', '2021-01-09 14:38:46', 'huxiaowen', '2021-01-15 14:20:16', -1, '库房管理', 1, NULL, NULL, 'KFGL', 'iconfont iconkufangguanli', NULL, 5);
INSERT INTO `sys_permission` VALUES (14, 'super', '2021-01-09 14:39:11', 'huxiaowen', '2021-01-15 14:20:43', -1, '档案统计', 1, NULL, NULL, 'DATJ', 'iconfont icontongji', NULL, 6);
INSERT INTO `sys_permission` VALUES (15, 'super', '2021-01-09 14:39:36', 'huxiaowen', '2021-01-15 14:21:20', -1, '安全中心', 1, NULL, NULL, 'AQZX', 'iconfont iconanquanzhongxin', NULL, 7);
INSERT INTO `sys_permission` VALUES (16, 'super', '2021-01-09 14:41:44', 'huxiaowen', '2021-01-18 19:06:43', -1, '辅助工具', 1, NULL, NULL, 'FZGJ', 'iconfont iconmytool-copy', '/auxiliaryTools', 8);
INSERT INTO `sys_permission` VALUES (17, 'super', '2021-01-09 14:42:21', 'huxiaowen', '2021-01-15 14:33:00', -1, '通知公告', 1, NULL, NULL, 'TZGG', 'iconfont icongonggao', '/notive', 9);
INSERT INTO `sys_permission` VALUES (18, 'super', '2021-01-09 14:42:43', 'huxiaowen', '2021-01-15 14:32:40', -1, '系统设置', 1, NULL, NULL, 'XTSZ', 'iconfont iconshezhi', NULL, 10);
INSERT INTO `sys_permission` VALUES (19, 'huxiaowen', '2021-01-15 10:30:00', 'huxiaowen', '2021-01-15 10:30:49', 7, '条件检索', 1, NULL, NULL, 'TJJS', NULL, '/conditionalSearch', 11);
INSERT INTO `sys_permission` VALUES (20, 'huxiaowen', '2021-01-15 10:33:23', 'huxiaowen', '2021-01-15 10:33:32', 7, '档案编译', 1, NULL, NULL, 'DABY', NULL, '/fileCompilationmain', 12);
INSERT INTO `sys_permission` VALUES (21, 'huxiaowen', '2021-01-15 10:34:07', 'huxiaowen', '2021-01-15 10:34:07', 7, '编研成果', 1, NULL, NULL, 'BYCG', NULL, '/bianyiAchievementsmain', 13);
INSERT INTO `sys_permission` VALUES (22, 'huxiaowen', '2021-01-15 10:34:47', 'huxiaowen', '2021-01-15 10:34:47', 12, '档案收集', 1, NULL, NULL, 'DASJ', NULL, '/archivesShouji', 14);
INSERT INTO `sys_permission` VALUES (23, 'huxiaowen', '2021-01-15 10:35:24', 'huxiaowen', '2021-01-15 10:35:24', 12, '临时库', 1, NULL, NULL, 'LSK', NULL, '/temporaryLibrary', 15);
INSERT INTO `sys_permission` VALUES (24, 'huxiaowen', '2021-01-15 10:36:26', 'huxiaowen', '2021-01-15 10:36:26', 12, '整理库', 1, NULL, NULL, 'ZLK', NULL, '/sortingLibrary', 16);
INSERT INTO `sys_permission` VALUES (25, 'huxiaowen', '2021-01-15 10:43:22', 'huxiaowen', '2021-01-15 10:43:27', 12, '正式库', 1, NULL, NULL, 'ZSK', NULL, '/officialLibrary', 17);
INSERT INTO `sys_permission` VALUES (27, 'huxiaowen', '2021-01-15 10:44:52', 'huxiaowen', '2021-01-15 10:44:52', 12, '回收站', 1, NULL, NULL, 'HSZ', NULL, '/recycleBin', 18);
INSERT INTO `sys_permission` VALUES (28, 'huxiaowen', '2021-01-15 10:45:16', 'huxiaowen', '2021-01-15 10:45:16', 12, '销毁库', 1, NULL, NULL, 'XHK', NULL, '/destructionLibrary', 19);
INSERT INTO `sys_permission` VALUES (29, 'huxiaowen', '2021-01-15 10:48:38', 'huxiaowen', '2021-01-15 10:48:38', 12, '回退管理', 1, NULL, NULL, 'HYGL', NULL, '/fallbackManagement', 20);
INSERT INTO `sys_permission` VALUES (30, 'huxiaowen', '2021-01-15 10:49:08', 'huxiaowen', '2021-01-15 10:49:08', 12, '病档管理', 1, NULL, NULL, 'BDGL', NULL, '/managementOfMedicalRecords', 21);
INSERT INTO `sys_permission` VALUES (31, 'huxiaowen', '2021-01-15 10:49:35', 'huxiaowen', '2021-01-15 10:49:35', 12, '档案销毁', 1, NULL, NULL, 'DAXH', NULL, '/fileDestruction', 22);
INSERT INTO `sys_permission` VALUES (32, 'huxiaowen', '2021-01-15 10:49:59', 'huxiaowen', '2021-01-15 17:36:33', 13, '实体档案入库管理', 1, NULL, NULL, 'STDARK', NULL, '/entityArchivesInManagement', 23);
INSERT INTO `sys_permission` VALUES (33, 'huxiaowen', '2021-01-15 10:50:27', 'huxiaowen', '2021-01-15 10:50:27', 13, '实体档案出库管理', 1, NULL, NULL, 'STDACK', NULL, '/entityArchivesOutManagement', 24);
INSERT INTO `sys_permission` VALUES (34, 'huxiaowen', '2021-01-15 10:50:59', 'huxiaowen', '2021-01-15 10:50:59', 13, '实体档案管理', 1, NULL, NULL, 'STDAGL', NULL, '/entityArchivesManagement', 25);
INSERT INTO `sys_permission` VALUES (35, 'huxiaowen', '2021-01-15 10:51:20', 'huxiaowen', '2021-01-16 10:22:58', 13, '库房管理', 1, NULL, NULL, 'KFGL', NULL, '/warehouseManagementIn', 27);
INSERT INTO `sys_permission` VALUES (36, 'huxiaowen', '2021-01-15 10:51:58', 'huxiaowen', '2021-01-16 10:23:04', 13, '实体档案库房大屏看板', 1, NULL, NULL, 'DPKB', NULL, '/visualizationCenter', 28);
INSERT INTO `sys_permission` VALUES (37, 'huxiaowen', '2021-01-15 10:52:19', 'huxiaowen', '2021-01-16 10:23:09', 13, '智能密集架系统', 1, NULL, NULL, '', NULL, NULL, 29);
INSERT INTO `sys_permission` VALUES (38, 'huxiaowen', '2021-01-15 10:52:46', 'huxiaowen', '2021-01-16 10:23:14', 13, '库房环控系统', 1, NULL, NULL, '', NULL, NULL, 30);
INSERT INTO `sys_permission` VALUES (39, 'huxiaowen', '2021-01-15 10:53:15', 'huxiaowen', '2021-01-16 10:23:19', 13, '库房安防系统', 1, NULL, NULL, '', NULL, NULL, 31);
INSERT INTO `sys_permission` VALUES (40, 'huxiaowen', '2021-01-15 10:53:34', 'huxiaowen', '2021-01-16 10:23:24', 13, '库房门禁系统', 1, NULL, NULL, '', NULL, NULL, 32);
INSERT INTO `sys_permission` VALUES (41, 'huxiaowen', '2021-01-15 10:54:24', 'huxiaowen', '2021-01-15 10:54:57', 14, '查档数量统计', 1, NULL, NULL, 'CDSL', NULL, '/danserseenum', 32);
INSERT INTO `sys_permission` VALUES (42, 'huxiaowen', '2021-01-15 10:54:48', 'huxiaowen', '2021-01-15 10:54:48', 14, '利用目的统计', 1, NULL, NULL, 'LYMD', NULL, '/objective', 33);
INSERT INTO `sys_permission` VALUES (43, 'huxiaowen', '2021-01-15 10:55:16', 'huxiaowen', '2021-01-15 10:55:16', 14, '档案借阅统计', 1, NULL, NULL, 'DAJY', NULL, '/danserread', 34);
INSERT INTO `sys_permission` VALUES (44, 'huxiaowen', '2021-01-15 10:55:37', 'huxiaowen', '2021-01-15 10:55:37', 14, '档案数量统计', 1, NULL, NULL, 'DASL', NULL, '/dansernum', 35);
INSERT INTO `sys_permission` VALUES (45, 'huxiaowen', '2021-01-15 10:56:01', 'huxiaowen', '2021-01-15 10:56:01', 14, '自定义统计内容', 1, NULL, NULL, 'ZDY', NULL, '/dansernum', 36);
INSERT INTO `sys_permission` VALUES (46, 'huxiaowen', '2021-01-15 10:56:29', 'huxiaowen', '2021-01-15 10:56:29', 14, '病档鉴定统计', 1, NULL, NULL, 'BDJD', NULL, '/illness', 37);
INSERT INTO `sys_permission` VALUES (47, 'huxiaowen', '2021-01-15 10:56:48', 'huxiaowen', '2021-01-15 10:56:48', 14, '年报统计', 1, NULL, NULL, 'NB', NULL, '/yeartotol', 38);
INSERT INTO `sys_permission` VALUES (48, 'huxiaowen', '2021-01-15 10:57:18', 'huxiaowen', '2021-01-15 10:57:18', 15, '日志管理', 1, NULL, NULL, 'RIGL', NULL, '/journalManage', 39);
INSERT INTO `sys_permission` VALUES (49, 'huxiaowen', '2021-01-15 10:57:37', 'huxiaowen', '2021-01-15 10:57:37', 15, '数据备份', 1, NULL, NULL, 'SJBF', NULL, '/databackup', 39);
INSERT INTO `sys_permission` VALUES (50, 'huxiaowen', '2021-01-15 10:58:08', 'huxiaowen', '2021-01-15 10:58:18', 15, '安全监测', 1, NULL, NULL, 'AQJC', NULL, NULL, 40);
INSERT INTO `sys_permission` VALUES (52, 'huxiaowen', '2021-01-15 10:59:49', 'huxiaowen', '2021-01-15 10:59:49', 18, '组织结构管理', 1, NULL, NULL, 'ZZJG', NULL, '/organizationManagement', 1);
INSERT INTO `sys_permission` VALUES (53, 'huxiaowen', '2021-01-15 11:00:11', 'huxiaowen', '2021-01-15 11:00:11', 18, '角色管理', 1, NULL, NULL, 'JSGL', NULL, '/roleManage', 2);
INSERT INTO `sys_permission` VALUES (54, 'huxiaowen', '2021-01-15 11:00:30', 'huxiaowen', '2021-01-15 11:00:30', 18, '用户管理', 1, NULL, NULL, 'YHGL', NULL, '/userManage', 3);
INSERT INTO `sys_permission` VALUES (55, 'huxiaowen', '2021-01-15 11:00:50', 'huxiaowen', '2021-01-15 11:00:50', 18, '权限管理', 1, NULL, NULL, 'QXGL', NULL, '/powerManage', 4);
INSERT INTO `sys_permission` VALUES (56, 'huxiaowen', '2021-01-15 11:01:16', 'huxiaowen', '2021-01-15 11:01:16', 18, '远程借阅人员管理', 1, NULL, NULL, 'YCJYRYGL', NULL, '/remoteStaff', 5);
INSERT INTO `sys_permission` VALUES (57, 'huxiaowen', '2021-01-15 11:01:39', 'huxiaowen', '2021-01-15 11:01:39', 18, '审批流程配置', 1, NULL, NULL, 'SPLCPZ', NULL, '/shenpiliucheng', 6);
INSERT INTO `sys_permission` VALUES (58, 'huxiaowen', '2021-01-15 11:02:06', 'huxiaowen', '2021-01-15 11:02:06', 18, '模板库管理', 1, NULL, NULL, 'MBKGL', NULL, '/templateManagement', 7);
INSERT INTO `sys_permission` VALUES (59, 'huxiaowen', '2021-01-15 11:02:33', 'huxiaowen', '2021-01-15 11:02:33', 18, '全宗号管理', 1, NULL, NULL, 'QZHGL', NULL, '/quanzhonghao', 8);
INSERT INTO `sys_permission` VALUES (60, 'huxiaowen', '2021-01-15 11:02:55', 'huxiaowen', '2021-01-15 11:02:55', 18, '档案库管理', 1, NULL, NULL, 'DAKGL', NULL, '/danserKu', 9);
INSERT INTO `sys_permission` VALUES (61, 'huxiaowen', '2021-01-15 11:03:19', 'huxiaowen', '2021-01-15 11:03:19', 18, '数据字典', 1, NULL, NULL, 'SJZD', NULL, '/dataDictionary', 10);
INSERT INTO `sys_permission` VALUES (62, 'huxiaowen', '2021-01-15 11:03:50', 'huxiaowen', '2021-01-15 11:03:50', 18, '报表定义', 1, NULL, NULL, 'BBDY', NULL, '/dataDictionary', 11);
INSERT INTO `sys_permission` VALUES (63, 'huxiaowen', '2021-01-15 11:04:07', 'huxiaowen', '2021-01-15 11:04:07', 18, '水印功能', 1, NULL, NULL, 'SYGN', NULL, '/dataDictionary', 12);
INSERT INTO `sys_permission` VALUES (64, 'huxiaowen', '2021-01-15 11:04:28', 'huxiaowen', '2021-01-15 11:04:28', 18, '参数设置', 1, NULL, NULL, 'CSSZ', NULL, '/paramsConfig', 13);
INSERT INTO `sys_permission` VALUES (65, 'huxiaowen', '2021-01-16 10:23:58', 'huxiaowen', '2021-01-16 10:24:08', 13, '实体库管理', 1, NULL, NULL, 'STK:', NULL, '/entityLibraryManagement', 26);

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
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (25, 'super', '2021-01-16 18:21:33', 'super', '2021-01-16 18:21:33', '角色1', 1, '111');
INSERT INTO `sys_role` VALUES (26, 'super', '2021-01-16 18:21:44', 'super', '2021-01-16 18:21:44', '角色2', 1, '111');
INSERT INTO `sys_role` VALUES (27, 'super', '2021-01-16 18:21:52', 'super', '2021-01-16 18:21:52', '角色3', 1, '颠三倒四');
INSERT INTO `sys_role` VALUES (28, 'super', '2021-01-16 18:22:02', 'super', '2021-01-16 18:22:02', '角色4', 1, '颠三倒四');
INSERT INTO `sys_role` VALUES (29, 'super', '2021-01-16 18:22:13', 'super', '2021-01-16 18:22:13', '角色5', 1, '1111');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role_id`(`role_id`) USING BTREE,
  INDEX `fk_permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色权限中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (35, 'super', '2021-01-16 18:21:33', 'super', '2021-01-16 18:21:33', 25, 3);
INSERT INTO `sys_role_permission` VALUES (36, 'super', '2021-01-16 18:21:44', 'super', '2021-01-16 18:21:44', 26, 14);
INSERT INTO `sys_role_permission` VALUES (37, 'super', '2021-01-16 18:21:44', 'super', '2021-01-16 18:21:44', 26, 41);
INSERT INTO `sys_role_permission` VALUES (38, 'super', '2021-01-16 18:21:44', 'super', '2021-01-16 18:21:44', 26, 42);
INSERT INTO `sys_role_permission` VALUES (39, 'super', '2021-01-16 18:21:44', 'super', '2021-01-16 18:21:44', 26, 43);
INSERT INTO `sys_role_permission` VALUES (40, 'super', '2021-01-16 18:21:44', 'super', '2021-01-16 18:21:44', 26, 44);
INSERT INTO `sys_role_permission` VALUES (41, 'super', '2021-01-16 18:21:45', 'super', '2021-01-16 18:21:45', 26, 45);
INSERT INTO `sys_role_permission` VALUES (42, 'super', '2021-01-16 18:21:45', 'super', '2021-01-16 18:21:45', 26, 46);
INSERT INTO `sys_role_permission` VALUES (43, 'super', '2021-01-16 18:21:45', 'super', '2021-01-16 18:21:45', 26, 47);
INSERT INTO `sys_role_permission` VALUES (44, 'super', '2021-01-16 18:21:52', 'super', '2021-01-16 18:21:52', 27, 15);
INSERT INTO `sys_role_permission` VALUES (45, 'super', '2021-01-16 18:21:52', 'super', '2021-01-16 18:21:52', 27, 48);
INSERT INTO `sys_role_permission` VALUES (46, 'super', '2021-01-16 18:21:52', 'super', '2021-01-16 18:21:52', 27, 49);
INSERT INTO `sys_role_permission` VALUES (47, 'super', '2021-01-16 18:21:52', 'super', '2021-01-16 18:21:52', 27, 50);
INSERT INTO `sys_role_permission` VALUES (48, 'super', '2021-01-16 18:22:02', 'super', '2021-01-16 18:22:02', 28, 17);
INSERT INTO `sys_role_permission` VALUES (49, 'super', '2021-01-16 18:22:13', 'super', '2021-01-16 18:22:13', 29, 16);

-- ----------------------------
-- Table structure for sys_template_library
-- ----------------------------
DROP TABLE IF EXISTS `sys_template_library`;
CREATE TABLE `sys_template_library`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `parent_id` int(11) NOT NULL COMMENT '父节点主键',
  `number` int(11) NULL DEFAULT NULL COMMENT '模板库编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板库名称',
  `type` smallint(6) NOT NULL COMMENT '模板库分类 1.目录 2.模板库',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板库描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_number`(`number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统模板库表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_template_library
-- ----------------------------
INSERT INTO `sys_template_library` VALUES (26, 'super', '2020-12-30 18:39:08', 'super', '2021-01-14 09:38:57', -1, NULL, '文书档案模板目录', 1, '文书档案模板描述');
INSERT INTO `sys_template_library` VALUES (27, 'super', '2020-12-30 18:39:21', 'super', '2021-01-14 09:30:31', -1, NULL, '业务档案模板目录', 1, '业务档案模板目录描述');
INSERT INTO `sys_template_library` VALUES (28, 'super', '2020-12-30 18:39:24', 'super', '2021-01-14 09:30:48', -1, NULL, '实物档案模板目录', 1, '实物档案模板目录描述');
INSERT INTO `sys_template_library` VALUES (33, 'huxiaowen', '2021-01-09 18:30:24', 'super', '2021-01-14 09:31:05', -1, NULL, '会计档案模板目录', 1, '会计档案模板目录描述');
INSERT INTO `sys_template_library` VALUES (51, 'super', '2021-01-14 09:31:24', 'super', '2021-01-14 09:31:24', -1, NULL, '照片档案模板目录', 1, '照片档案模板目录描述');
INSERT INTO `sys_template_library` VALUES (52, 'super', '2021-01-14 09:31:45', 'super', '2021-01-14 09:31:45', -1, NULL, '音像档案模板目录', 1, '音像档案模板目录描述');
INSERT INTO `sys_template_library` VALUES (53, 'super', '2021-01-14 09:32:02', 'super', '2021-01-14 09:32:02', -1, NULL, '人事档案模板目录', 1, '人事档案模板目录描述');
INSERT INTO `sys_template_library` VALUES (54, 'super', '2021-01-14 09:32:20', 'super', '2021-01-14 09:32:20', -1, NULL, '科技档案模板目录', 1, '科技档案模板目录描述');
INSERT INTO `sys_template_library` VALUES (57, 'super', '2021-01-14 09:44:15', 'huxiaowen', '2021-01-14 15:45:13', 27, 10101, '业务档案模板', 2, '业务档案模板描述');
INSERT INTO `sys_template_library` VALUES (58, 'super', '2021-01-14 09:41:03', 'super', '2021-01-14 09:41:03', 28, 1003, '实物档案模板', 2, '实物档案模板描述');
INSERT INTO `sys_template_library` VALUES (59, 'super', '2021-01-14 09:41:21', 'huxiaowen', '2021-01-14 15:42:05', 33, 1004, '会计档案模板', 1, '会计档案模板描述');
INSERT INTO `sys_template_library` VALUES (60, 'super', '2021-01-14 09:41:35', 'super', '2021-01-14 09:41:35', 51, 1005, '照片档案模板', 2, '照片档案模板描述');
INSERT INTO `sys_template_library` VALUES (61, 'super', '2021-01-14 09:42:13', 'super', '2021-01-14 09:42:13', 52, 1006, '音像档案模板', 2, '音像档案模板描述');
INSERT INTO `sys_template_library` VALUES (62, 'super', '2021-01-14 09:42:31', 'super', '2021-01-14 09:42:31', 53, 1007, '人事档案模板', 2, '人事档案模板描述');
INSERT INTO `sys_template_library` VALUES (63, 'super', '2021-01-14 09:42:47', 'super', '2021-01-14 09:42:47', 54, 1008, '科技档案模板', 2, '科技档案模板描述');
INSERT INTO `sys_template_library` VALUES (68, 'super', '2021-01-22 18:30:02', 'super', '2021-01-22 18:30:02', 26, 1001, '发文', 2, '');
INSERT INTO `sys_template_library` VALUES (71, 'super', '2021-01-27 14:18:28', 'super', '2021-01-27 14:18:28', 26, 1002, '收文', 2, '');

-- ----------------------------
-- Table structure for sys_template_library_field
-- ----------------------------
DROP TABLE IF EXISTS `sys_template_library_field`;
CREATE TABLE `sys_template_library_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称（注释或者描述）',
  `data_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key（定义数据库字段名）',
  `length` int(11) NOT NULL COMMENT '字段长度',
  `required` smallint(6) NOT NULL COMMENT '字段是否必填 1.是 0.否',
  `index` smallint(6) NOT NULL COMMENT '字段是否是索引 1.是 0.否',
  `order` int(11) NOT NULL COMMENT '字段顺序',
  `query` smallint(6) NOT NULL COMMENT '字段是否在页面查询 1.是 0.否',
  `title` smallint(6) NOT NULL COMMENT '字段是否在页面表头是否显示 1.是 0.否',
  `form` smallint(6) NOT NULL COMMENT '字段是否在页面表单是否显示 1.是 0.否',
  `template_library_id` int(11) NOT NULL COMMENT '模板库id',
  `template_library_field_type_id` int(11) NOT NULL COMMENT '模板库字段类型id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_data_key_template_library_id`(`data_key`, `template_library_id`) USING BTREE,
  INDEX `fk_template_library_field_type_id`(`template_library_field_type_id`) USING BTREE,
  INDEX `fk_template_library_id`(`template_library_id`) USING BTREE,
  CONSTRAINT `sys_template_library_field_ibfk_1` FOREIGN KEY (`template_library_id`) REFERENCES `sys_template_library` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_template_library_field_ibfk_2` FOREIGN KEY (`template_library_field_type_id`) REFERENCES `sys_template_library_field_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统模板库字段表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_template_library_field
-- ----------------------------

-- ----------------------------
-- Table structure for sys_template_library_field_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_template_library_field_type`;
CREATE TABLE `sys_template_library_field_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段类型名称',
  `data_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key（定义数据库字段名类型名）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统模板库字段类型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_template_library_field_type
-- ----------------------------
INSERT INTO `sys_template_library_field_type` VALUES (1, 'super', '2021-01-05 10:47:32', 'super', '2021-01-05 10:47:34', '文本框(文本)', 'varchar');
INSERT INTO `sys_template_library_field_type` VALUES (2, 'super', '2021-01-05 10:47:32', 'super', '2021-01-05 10:47:34', '文本域', 'text');
INSERT INTO `sys_template_library_field_type` VALUES (3, 'super', '2021-01-05 10:47:32', 'super', '2021-01-05 10:47:34', '单选框', 'tinyint');
INSERT INTO `sys_template_library_field_type` VALUES (4, 'super', '2021-01-05 10:47:32', 'super', '2021-01-05 10:47:34', '文本框(状态数值)', 'smallint');
INSERT INTO `sys_template_library_field_type` VALUES (5, 'super', '2021-01-05 10:47:32', 'super', '2021-01-05 10:47:34', '文本框(数值)', 'int');
INSERT INTO `sys_template_library_field_type` VALUES (6, 'super', '2021-01-05 10:47:32', 'super', '2021-01-05 10:47:34', '日期时间框', 'datetime');
INSERT INTO `sys_template_library_field_type` VALUES (7, 'super', '2021-01-05 10:47:32', 'super', '2021-01-05 10:47:34', '日期框', 'date');

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
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `certificate_type` smallint(6) NULL DEFAULT NULL COMMENT '证件类型',
  `certificate_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话或者手机号码',
  `account_expired` smallint(6) NOT NULL COMMENT '账户过期',
  `account_locked` smallint(6) NOT NULL COMMENT '账户锁定',
  `credentials_expired` smallint(6) NOT NULL COMMENT '密码过期',
  `account_enabled` smallint(6) NOT NULL COMMENT '账户禁用',
  `auth_type` smallint(6) NOT NULL COMMENT '用户类型 0.普通用户 1.管理员用户',
  `version` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'super', '2020-11-27 11:03:24', 'super', '2021-01-16 18:23:29', 'super', '$2a$10$Ho.dr6ixnGR8nKFDnDLjTuWa9ZFriSJ2I3dZEUlRb7b.AG4r/pWae', '马龙飞', 1, '410184199702068318', '1107201045@qq.com', '河南省新郑市辛店镇', '15038233127', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (3, 'super', '2020-12-29 18:12:11', 'super', '2021-01-16 18:23:33', 'huxiaowen', '$2a$10$1lSyom.fjWPHMZEFS.6OUO3kNV84rAuLEAsMOlYQVk99Xn6DCYzVe', '胡晓雯', NULL, '', '1107201045@qq.com', '地址', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (6, 'huxiaowen', '2021-01-07 14:48:48', 'super', '2021-01-09 14:20:26', 'hxw', '$2a$10$fVczRcXXzUmHB8Gpn5GP5.Hr09VF.SvWwuQsbo8yBp7KmnEzNpEGy', 'Summer。', 1, '410527199811129743', '1812538763@qq.com', '河南省安阳市内黄县', '18811597991', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (7, 'huxiaowen', '2021-01-07 15:57:46', 'super', '2021-01-16 18:23:38', 'sunshine', '$2a$10$akEfHr6Kre64II.fTBOZ1upuT1vk4ymjV7DiDuTKj25jzQ2dhd5iO', 'Spring。', 1, '410527199811129743', '1812538763@qq.com', '河南省安阳市', '15518256210', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (8, 'huxiaowen', '2021-01-08 10:41:29', 'super', '2021-01-16 18:23:42', 'liuSir', '$2a$10$mIc9VaIpw9uPOghM1UaqwekPMk5dmMEgeaerrw6eoeDwPpgUQySpa', '刘春雨', 1, '', '', '河南省郑州市', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (9, 'huxiaowen', '2021-01-08 10:44:55', 'super', '2021-01-16 18:23:46', 'Ladyliu', '$2a$10$o7HY3bSRMluGLLlVTzlIZu5YPNdczHA3TSrXK/62AWQzniFcn0UuK', '刘露', 1, '', '', '河南省郑州市', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (10, 'huxiaowen', '2021-01-08 10:45:18', 'super', '2021-01-16 18:23:19', 'liMadam', '$2a$10$fKqmZy66jeqSZsGzUr9QE.NYL2Yc0ZJPT5YhlFJk8/wkQcOx6Dgl2', '李约', 1, '', '', '河南省郑州市', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (11, 'huxiaowen', '2021-01-08 10:49:42', 'super', '2021-01-16 18:23:03', 'pengSir', '$2a$10$xd/wGy.XTzhUh2Lx6ZPlJOf8KUEFZDio8mdX8htMiJTbXJe.NyNo2', '彭万顺', 1, '', '', '河南省郑州市', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (12, 'huxiaowen', '2021-01-08 10:50:40', 'super', '2021-01-16 18:22:57', 'zhangsan', '$2a$10$OvnijztyYFcnytPua22FjO4aNzzUAavAgZnwvdzRLq.VbFtid.2kq', '张三', 1, '', '', '河南省郑州市', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (13, 'huxiaowen', '2021-01-08 10:55:38', 'super', '2021-01-16 18:22:50', 'lisi', '$2a$10$E9/CCm16L1Y33mNIpns0oOd4kx0oCKpXh071zAeOWvfoxnXeayoyK', '李四', 1, '', '', '河南省郑州市', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (14, 'huxiaowen', '2021-01-08 10:56:08', 'super', '2021-01-16 18:22:43', 'hiheihei', '$2a$10$sxYqKmRwnK0OUTlaDhgAAuZvT1cgE8T3H1AYfaPJxLNTTelsvAglu', '嘿嘿嘿', 1, '', '', '河南省郑州市', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (15, 'huxiaowen', '2021-01-08 11:29:16', 'huxiaowen', '2021-01-08 11:29:16', 'lll', '$2a$10$ctiRrahI7Cw9LPKm2PCRcOsd59vysgjqUu98Jzkvp9daMPVYXOyzC', '李四', NULL, '', '', '', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (16, 'huxiaowen', '2021-01-08 11:29:30', 'super', '2021-01-16 18:25:20', 'hhh', '$2a$10$7jT17l0oUq7wdZ7q8kKIgedfjPd/l./L6J54RjBNQ1/DpDGCj6/IS', 'heihei', NULL, '', '', '', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (17, 'super', '2021-01-09 14:47:27', 'super', '2021-01-16 18:22:29', 'spring', '$2a$10$VnWwkchXgvMtvDDFPKlhZeSGr.5At9vgH4MKCSlmQbV0DL7Oay0BK', 'Spring。', NULL, '', '', '', '', 0, 0, 0, 1, 1, 1);
INSERT INTO `sys_user` VALUES (18, 'super', '2021-01-15 19:45:02', 'super', '2021-01-16 18:25:15', 'mwy', '$2a$10$DHlI61CXHzyy9cr6h/W5mOiv/xjARdEkNAX0apfnEN5Pg4eykk/8W', '马葳严', NULL, '', '', '', '', 0, 0, 0, 1, 0, 1);
INSERT INTO `sys_user` VALUES (19, 'super', '2021-01-21 18:54:16', 'unknown', '2021-01-21 19:10:19', '!!11', '$2a$10$SsMCe6cU1zw/lwHMKnY92.KQ4jJljd0SVrMnOE8.tKYNBuYy.ylfa', '111', NULL, '', '', '', '', 0, 0, 0, 1, 0, 1);
INSERT INTO `sys_user` VALUES (20, 'super', '2021-01-22 11:09:15', 'super', '2021-01-22 11:09:15', 'admin', '$2a$10$xRpetfz5fb.lY8j/sZw5aOW1iK0as6Ajiz6JrbYZ5rAuoXCDyOutC', '管理员', NULL, '', '', '', '', 0, 0, 0, 1, 1, 1);

-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键用户',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `modified_time` datetime(0) NOT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `department_id` int(11) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_department_id`(`department_id`) USING BTREE,
  INDEX `fk_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `sys_user_department_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_department_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 177 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户部门中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_department
-- ----------------------------
INSERT INTO `sys_user_department` VALUES (129, 'super', '2021-01-07 21:20:43', 'super', '2021-01-07 21:20:43', 6, 12);
INSERT INTO `sys_user_department` VALUES (144, 'huxiaowen', '2021-01-08 11:29:16', 'huxiaowen', '2021-01-08 11:29:16', 15, 11);
INSERT INTO `sys_user_department` VALUES (150, 'super', '2021-01-16 18:22:29', 'super', '2021-01-16 18:22:29', 17, 14);
INSERT INTO `sys_user_department` VALUES (153, 'super', '2021-01-16 18:22:43', 'super', '2021-01-16 18:22:43', 14, 10);
INSERT INTO `sys_user_department` VALUES (154, 'super', '2021-01-16 18:22:43', 'super', '2021-01-16 18:22:43', 14, 11);
INSERT INTO `sys_user_department` VALUES (155, 'super', '2021-01-16 18:22:50', 'super', '2021-01-16 18:22:50', 13, 10);
INSERT INTO `sys_user_department` VALUES (156, 'super', '2021-01-16 18:22:50', 'super', '2021-01-16 18:22:50', 13, 11);
INSERT INTO `sys_user_department` VALUES (157, 'super', '2021-01-16 18:22:57', 'super', '2021-01-16 18:22:57', 12, 10);
INSERT INTO `sys_user_department` VALUES (158, 'super', '2021-01-16 18:22:57', 'super', '2021-01-16 18:22:57', 12, 11);
INSERT INTO `sys_user_department` VALUES (159, 'super', '2021-01-16 18:23:03', 'super', '2021-01-16 18:23:03', 11, 11);
INSERT INTO `sys_user_department` VALUES (160, 'super', '2021-01-16 18:23:19', 'super', '2021-01-16 18:23:19', 10, 11);
INSERT INTO `sys_user_department` VALUES (163, 'super', '2021-01-16 18:23:29', 'super', '2021-01-16 18:23:29', 1, 10);
INSERT INTO `sys_user_department` VALUES (164, 'super', '2021-01-16 18:23:29', 'super', '2021-01-16 18:23:29', 1, 14);
INSERT INTO `sys_user_department` VALUES (165, 'super', '2021-01-16 18:23:33', 'super', '2021-01-16 18:23:33', 3, 12);
INSERT INTO `sys_user_department` VALUES (166, 'super', '2021-01-16 18:23:33', 'super', '2021-01-16 18:23:33', 3, 14);
INSERT INTO `sys_user_department` VALUES (167, 'super', '2021-01-16 18:23:38', 'super', '2021-01-16 18:23:38', 7, 10);
INSERT INTO `sys_user_department` VALUES (168, 'super', '2021-01-16 18:23:38', 'super', '2021-01-16 18:23:38', 7, 11);
INSERT INTO `sys_user_department` VALUES (169, 'super', '2021-01-16 18:23:38', 'super', '2021-01-16 18:23:38', 7, 12);
INSERT INTO `sys_user_department` VALUES (170, 'super', '2021-01-16 18:23:42', 'super', '2021-01-16 18:23:42', 8, 10);
INSERT INTO `sys_user_department` VALUES (171, 'super', '2021-01-16 18:23:42', 'super', '2021-01-16 18:23:42', 8, 11);
INSERT INTO `sys_user_department` VALUES (172, 'super', '2021-01-16 18:23:46', 'super', '2021-01-16 18:23:46', 9, 10);
INSERT INTO `sys_user_department` VALUES (173, 'super', '2021-01-16 18:25:15', 'super', '2021-01-16 18:25:15', 18, 14);
INSERT INTO `sys_user_department` VALUES (174, 'super', '2021-01-16 18:25:20', 'super', '2021-01-16 18:25:20', 16, 10);
INSERT INTO `sys_user_department` VALUES (175, 'super', '2021-01-21 18:54:16', 'super', '2021-01-21 18:54:16', 19, 11);
INSERT INTO `sys_user_department` VALUES (176, 'super', '2021-01-22 11:09:15', 'super', '2021-01-22 11:09:15', 20, 24);

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
  INDEX `fk_user_id`(`user_id`) USING BTREE,
  INDEX `fk_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (101, 'super', '2021-01-16 18:22:29', 'super', '2021-01-16 18:22:29', 17, 26);
INSERT INTO `sys_user_role` VALUES (102, 'super', '2021-01-16 18:22:29', 'super', '2021-01-16 18:22:29', 17, 27);
INSERT INTO `sys_user_role` VALUES (105, 'super', '2021-01-16 18:22:43', 'super', '2021-01-16 18:22:43', 14, 26);
INSERT INTO `sys_user_role` VALUES (106, 'super', '2021-01-16 18:22:43', 'super', '2021-01-16 18:22:43', 14, 28);
INSERT INTO `sys_user_role` VALUES (107, 'super', '2021-01-16 18:22:50', 'super', '2021-01-16 18:22:50', 13, 28);
INSERT INTO `sys_user_role` VALUES (108, 'super', '2021-01-16 18:22:50', 'super', '2021-01-16 18:22:50', 13, 27);
INSERT INTO `sys_user_role` VALUES (109, 'super', '2021-01-16 18:22:57', 'super', '2021-01-16 18:22:57', 12, 29);
INSERT INTO `sys_user_role` VALUES (110, 'super', '2021-01-16 18:22:57', 'super', '2021-01-16 18:22:57', 12, 25);
INSERT INTO `sys_user_role` VALUES (111, 'super', '2021-01-16 18:23:03', 'super', '2021-01-16 18:23:03', 11, 29);
INSERT INTO `sys_user_role` VALUES (112, 'super', '2021-01-16 18:23:19', 'super', '2021-01-16 18:23:19', 10, 28);
INSERT INTO `sys_user_role` VALUES (113, 'super', '2021-01-16 18:23:19', 'super', '2021-01-16 18:23:19', 10, 27);
INSERT INTO `sys_user_role` VALUES (115, 'super', '2021-01-16 18:23:29', 'super', '2021-01-16 18:23:29', 1, 27);
INSERT INTO `sys_user_role` VALUES (116, 'super', '2021-01-16 18:23:33', 'super', '2021-01-16 18:23:33', 3, 27);
INSERT INTO `sys_user_role` VALUES (117, 'super', '2021-01-16 18:23:38', 'super', '2021-01-16 18:23:38', 7, 28);
INSERT INTO `sys_user_role` VALUES (118, 'super', '2021-01-16 18:23:38', 'super', '2021-01-16 18:23:38', 7, 26);
INSERT INTO `sys_user_role` VALUES (119, 'super', '2021-01-16 18:23:42', 'super', '2021-01-16 18:23:42', 8, 27);
INSERT INTO `sys_user_role` VALUES (120, 'super', '2021-01-16 18:23:46', 'super', '2021-01-16 18:23:46', 9, 28);
INSERT INTO `sys_user_role` VALUES (121, 'super', '2021-01-16 18:25:15', 'super', '2021-01-16 18:25:15', 18, 27);
INSERT INTO `sys_user_role` VALUES (122, 'super', '2021-01-16 18:25:15', 'super', '2021-01-16 18:25:15', 18, 28);
INSERT INTO `sys_user_role` VALUES (123, 'super', '2021-01-16 18:25:20', 'super', '2021-01-16 18:25:20', 16, 27);
INSERT INTO `sys_user_role` VALUES (124, 'super', '2021-01-16 18:25:20', 'super', '2021-01-16 18:25:20', 16, 29);
INSERT INTO `sys_user_role` VALUES (125, 'super', '2021-01-21 18:54:16', 'super', '2021-01-21 18:54:16', 19, 28);
INSERT INTO `sys_user_role` VALUES (126, 'super', '2021-01-22 11:09:15', 'super', '2021-01-22 11:09:15', 20, 29);

-- ----------------------------
-- Table structure for ware_template_library_field
-- ----------------------------
DROP TABLE IF EXISTS `ware_template_library_field`;
CREATE TABLE `ware_template_library_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称（注释或者描述）',
  `data_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key（定义数据库字段名）',
  `length` int(11) NOT NULL COMMENT '字段长度',
  `required` smallint(6) NOT NULL COMMENT '字段是否必填 1.是 0.否',
  `index` smallint(6) NOT NULL COMMENT '字段是否是索引 1.是 0.否',
  `order` int(11) NOT NULL COMMENT '字段顺序',
  `template_library_field_type_id` int(6) NOT NULL COMMENT '模板库字段类型id',
  `typeid` int(11) NOT NULL COMMENT '类型区分 0：未选  1：已选',
  `query` smallint(6) NOT NULL COMMENT '字段是否在页面查询 1.是 0.否',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
  `modified_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `template_library_field_type_id`(`template_library_field_type_id`) USING BTREE,
  CONSTRAINT `ware_template_library_field_ibfk_2` FOREIGN KEY (`template_library_field_type_id`) REFERENCES `sys_template_library_field_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库房管理实体库字段表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ware_template_library_field
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
