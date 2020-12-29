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

 Date: 29/12/2020 19:17:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for de_test
-- ----------------------------
DROP TABLE IF EXISTS `de_test`;
CREATE TABLE `de_test`
(
    `id`          int(11)                                                       NOT NULL AUTO_INCREMENT,
    `create_by`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_date` datetime(0)                                                   NULL DEFAULT NULL,
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `age`         int(11)                                                       NULL DEFAULT NULL,
    `email`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_archives
-- ----------------------------
DROP TABLE IF EXISTS `sys_archives`;
CREATE TABLE `sys_archives`
(
    `id`                int(5)                                                  NOT NULL AUTO_INCREMENT,
    `chinesename`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `englishname`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `standardfields`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `valuetype`         varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `fieldlength`       int(30)                                                 NULL DEFAULT NULL,
    `ordernumber`       int(100)                                                NULL DEFAULT NULL,
    `listconcealsign`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `redactconcealsign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `showlength`        int(10)                                                 NULL DEFAULT NULL,
    `create_by`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time`       datetime(0)                                             NULL DEFAULT NULL,
    `modified_by`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `modified_time`     datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `id` (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 23
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`
(
    `id`            int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_time`   datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `modified_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
    `modified_time` datetime(0)                                                  NOT NULL COMMENT '更新时间',
    `parent_id`     int(11)                                                      NOT NULL COMMENT '父节点主键',
    `name`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '部门表'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sys_library
-- ----------------------------
DROP TABLE IF EXISTS `sys_library`;
CREATE TABLE `sys_library`
(
    `id`             int(5)                                                        NOT NULL AUTO_INCREMENT,
    `librarynumber`  int(10)                                                       NULL DEFAULT NULL COMMENT '库编号',
    `pid`            int(5)                                                        NULL DEFAULT NULL,
    `libraryname`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '库名称',
    `tablename`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '表名',
    `ordernumber`    int(5)                                                        NULL DEFAULT NULL COMMENT '顺序号',
    `describee`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `libraryclasses` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '库类别',
    `create_by`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `create_time`    datetime(0)                                                   NULL DEFAULT NULL,
    `modified_by`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `modified_time`  datetime(0)                                                   NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`          int(11)                                                       NOT NULL AUTO_INCREMENT,
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `user_id`     int(11)                                                       NULL DEFAULT NULL COMMENT '用户id',
    `username`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '用户名',
    `operator`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作人',
    `level`       smallint(6)                                                   NULL DEFAULT NULL COMMENT '日志级别 1.TRACE < 2.DEBUG < 3.INFO < 4.WARN < 5.ERROR',
    `module`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '日志记录模块名称',
    `context`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '日志详细内容',
    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志描述',
    `login_ip`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '登录ip',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `sys_log_ibfk_1` (`user_id`) USING BTREE,
    CONSTRAINT `sys_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 241
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统日志表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`              int(11)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人（用户名为主）',
    `create_time`     datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `modified_by`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人（用户名为主）',
    `modified_time`   datetime(0)                                                   NOT NULL COMMENT '修改时间',
    `parent_id`       int(11)                                                       NOT NULL COMMENT '父节点主键',
    `name`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '权限名称',
    `priority`        int(11)                                                       NULL DEFAULT NULL COMMENT '优先级',
    `authorized_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '授权名称',
    `menu`            smallint(6)                                                   NULL DEFAULT NULL COMMENT '是否是菜单项 1.是 0.否',
    `url`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相对地址',
    `method`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '请求方法',
    `path`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由转向',
    `img_path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单项显示的图片地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统权限表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_qzh
-- ----------------------------
DROP TABLE IF EXISTS `sys_qzh`;
CREATE TABLE `sys_qzh`
(
    `id`              int(11)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人（用户名为主）',
    `create_time`     datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `modified_by`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人（用户名为主）',
    `modified_time`   datetime(0)                                                   NOT NULL COMMENT '修改时间',
    `qzh_name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全宗号名称',
    `qzh_number`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '全宗号',
    `phone`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `address`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `archives_center` smallint(6)                                                   NOT NULL COMMENT '是否是档案馆 1.是 0.否',
    `description`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `qzh_number` (`qzh_number`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 22
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统全宗号表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`            int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_by`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
    `create_time`   datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `modified_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
    `modified_time` datetime(0)                                                  NOT NULL COMMENT '修改时间',
    `name`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
    `default_role`  smallint(6)                                                  NULL DEFAULT NULL COMMENT '是否作为默认用户角色 1.是 0.否',
    `description`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统角色表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            int(11)                                                      NOT NULL,
    `create_by`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
    `create_time`   datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `modified_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
    `modified_time` datetime(0)                                                  NOT NULL COMMENT '修改时间',
    `role_id`       int(11)                                                      NOT NULL COMMENT '角色id',
    `permission_id` int(11)                                                      NOT NULL COMMENT '权限id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `sys_role_permission_ibfk_1` (`role_id`) USING BTREE,
    INDEX `sys_role_permission_ibfk_2` (`permission_id`) USING BTREE,
    CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统角色权限中间表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`                  int(11)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_by`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人（用户名为主）',
    `create_time`         datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `modified_by`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人（用户名为主）',
    `modified_time`       datetime(0)                                                   NOT NULL COMMENT '修改时间',
    `username`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户名',
    `password`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `nickname`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户名称',
    `certificate_type`    smallint(6)                                                   NULL DEFAULT NULL COMMENT '证件类型',
    `certificate_number`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '证件号码',
    `email`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '邮箱',
    `address`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '地址',
    `phone`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '电话或者手机号码',
    `account_expired`     smallint(6)                                                   NOT NULL COMMENT '账户过期',
    `account_locked`      smallint(6)                                                   NOT NULL COMMENT '账户锁定',
    `credentials_expired` smallint(6)                                                   NOT NULL COMMENT '密码过期',
    `account_enabled`     smallint(6)                                                   NOT NULL COMMENT '账户禁用',
    `auth_type`           smallint(6)                                                   NOT NULL COMMENT '用户类型 0.普通用户 1.管理员用户',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统用户表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department`
(
    `id`            int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT '主键用户',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_time`   datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `modified_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
    `modified_time` datetime(0)                                                  NOT NULL COMMENT '更新时间',
    `user_id`       int(11)                                                      NOT NULL COMMENT '用户id',
    `department_id` int(11)                                                      NOT NULL COMMENT '部门id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `sys_department_user_ibfk_2` (`department_id`) USING BTREE,
    INDEX `sys_department_user_ibfk_1` (`user_id`) USING BTREE,
    CONSTRAINT `sys_user_department_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `sys_user_department_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统用户部门中间表'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`            int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_by`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人（用户名为主）',
    `create_time`   datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `modified_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人（用户名为主）',
    `modified_time` datetime(0)                                                  NOT NULL COMMENT '修改时间',
    `user_id`       int(11)                                                      NOT NULL COMMENT '用户id',
    `role_id`       int(11)                                                      NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `sys_user_role_ibfk_1` (`user_id`) USING BTREE,
    INDEX `sys_user_role_ibfk_2` (`role_id`) USING BTREE,
    CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统用户角色表'
  ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
