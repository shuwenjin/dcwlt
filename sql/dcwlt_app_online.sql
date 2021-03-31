/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.23.171
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 10.0.23.171:3306
 Source Schema         : dcwlt_app_online

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 31/03/2021 10:53:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_batch_checkcollect
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_checkcollect`;
CREATE TABLE `pay_batch_checkcollect`  (
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `coreacctdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心会计日期',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `payflag` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收付标识',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发起机构',
  `dbitparty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款机构编码',
  `payerwalletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人钱包id',
  `payeraccount` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人账号',
  `crdtparty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款机构编码',
  `payeename` varchar(480) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人名称',
  `payeeaccount` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人账号',
  `payeewalletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人钱包id',
  `ccy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '货币代码',
  `amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '金额',
  `ognlmsgtype` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原报文编号',
  `ognlmsgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原报文标识号',
  `tradestatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易状态',
  `corestatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心状态',
  `pathstatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道状态',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`paydate`, `payserno`) USING BTREE,
  INDEX `ecny_batch_checkcollect_idx1`(`msgid`) USING BTREE,
  INDEX `ecny_batch_checkcollect_idx2`(`batchid`, `msgid`) USING BTREE,
  INDEX `ecny_batch_checkcollect_idx3`(`coreacctdate`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '对账明细采集表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_batch_checkcollect
-- ----------------------------
INSERT INTO `pay_batch_checkcollect` VALUES ('20210317', '14485725007', '144857', '20210317', 'dcep.221.001.01', '20210113000122184595346246598765', 'B202101131600', 'PAYEE', 'C1010411000013', 'C1010411000013', '101112345678916', NULL, 'C1030644021075', NULL, '6214622121002964305', NULL, 'CNY', '1100', NULL, NULL, '1', '1', '1', '20210317', '144858');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210317', '14493325010', '144933', '20210317', 'dcep.221.001.01', '20210113000122184595346246598767', 'B202101131600', 'PAYEE', 'C1010411000013', 'C1010411000013', '101112345678916', NULL, 'C1030644021075', NULL, '6214622121002964305', NULL, 'CNY', '1100', NULL, NULL, '1', '1', '1', '20210317', '144934');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210317', '14504625013', '145046', '20210317', 'dcep.221.001.01', '20210113000122184595346246598768', 'B202101131600', 'PAYEE', 'C1010411000013', 'C1010411000013', '101112345678916', NULL, 'C1030644021075', NULL, '6214622121002964305', NULL, 'CNY', '1100', NULL, NULL, '1', '1', '1', '20210317', '145053');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210317', '14514325014', '145143', '20210317', 'dcep.221.001.01', '20210113000122184595346246598769', 'B202101131600', 'PAYEE', 'C1010411000013', 'C1010411000013', '101112345678916', NULL, 'C1030644021075', NULL, '6214622121002964305', NULL, 'CNY', '1100', NULL, NULL, '1', '1', '1', '20210317', '145153');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210317', '15495535003', '154955', NULL, 'dcep.221.001.01', '20210113000122184595346246598777', 'B202101131600', 'PAYEE', 'C1010411000013', 'C1010411000013', '101112345678916', NULL, 'C1030644021075', NULL, '6214622121002964305', NULL, 'CNY', '1100', NULL, NULL, '2', '9', '1', '20210317', '154955');

-- ----------------------------
-- Table structure for pay_batch_checkfilelist
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_checkfilelist`;
CREATE TABLE `pay_batch_checkfilelist`  (
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `srcfilepath` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '源文件路径',
  `localfilepath` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '本地文件路径',
  `destfilepath` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '目标文件路径',
  `filename` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件名',
  `fileprocstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'init:未下载proc:下载中succ:已下载',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`, `batchid`, `filename`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通道对账明细文件列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_batch_checkpath
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_checkpath`;
CREATE TABLE `pay_batch_checkpath`  (
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `senderdatetime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文发送时间',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `instddrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '接收机构',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `digitalenvelope` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '数字信封',
  `batchdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '批次日期',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `countnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '总笔数',
  `countamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '总金额',
  `ccy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '货币代码',
  `dbitcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款笔数',
  `dbitcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款金额',
  `crdtcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款笔数',
  `crdtcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款金额',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '报文编号',
  `msgbizstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务状态',
  `msgcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '总笔数',
  `msgcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '总金额',
  `msgdbitcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款笔数',
  `msgdbitcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款金额',
  `msgcrdtcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款笔数',
  `msgcrdtcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款金额',
  `checkstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '对账标识',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`paydate`, `payserno`) USING BTREE,
  INDEX `ecny_batch_checkpath_idx1`(`msgid`, `msgtype`, `msgbizstatus`) USING BTREE,
  INDEX `ecny_batch_checkpath_idx2`(`batchdate`, `batchid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通道对账汇总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_batch_checkpath
-- ----------------------------
INSERT INTO `pay_batch_checkpath` VALUES ('20210309', '123456789', '2021-03-09', 'fdsvdsvsvsvsvsvsavsvfww', '213123312', '213123312', '213123312', NULL, NULL, '20210309', '123456789', '111', '11111', '123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '20210309', '155021');
INSERT INTO `pay_batch_checkpath` VALUES ('20210325', '20053445001', '200413', '20210113000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '机构对账汇总核对报文', NULL, '20210113', 'B202101131600', '15', '1016134', 'CNY', '4', '10396', '11', '1005738', 'dcep.227.001.01', 'PR00', '4', '126', '0', '0', '4', '126', 'LESS', '20210325', '200534');
INSERT INTO `pay_batch_checkpath` VALUES ('20210325', '20053445002', '200413', '20210113000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '机构对账汇总核对报文', NULL, '20210113', 'B202101131600', '15', '1016134', 'CNY', '4', '10396', '11', '1005738', 'dcep.221.001.01', 'PR01', '1', '1', '0', '0', '1', '1', 'LESS', '20210325', '200534');
INSERT INTO `pay_batch_checkpath` VALUES ('20210325', '20053445003', '200413', '20210113000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '机构对账汇总核对报文', NULL, '20210113', 'B202101131600', '15', '1016134', 'CNY', '4', '10396', '11', '1005738', 'dcep.227.001.01', 'PR01', '1', '1000000', '0', '0', '1', '1000000', 'LESS', '20210325', '200534');

-- ----------------------------
-- Table structure for pay_batch_checkpathdtl
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_checkpathdtl`;
CREATE TABLE `pay_batch_checkpathdtl`  (
  `workdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务日期',
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `filename` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件名',
  `dtlbiztime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务处理时间',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `dtlmsgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '明细的报文标识号',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `dbitparty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '付款机构编码',
  `crdtparty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收款机构编码',
  `ccy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '货币代码',
  `amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '金额',
  `dtlbizstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务状态',
  `dtldesc` varchar(384) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易描述信息',
  `payeename` varchar(480) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人名称',
  `payeeaccount` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人账号',
  `payeewalletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人钱包id',
  `payeraccount` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人账号',
  `ognlmsgtype` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原报文编号',
  `ognlmsgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原报文标识号',
  `checkstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '对账标识',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`, `dtlmsgid`) USING BTREE,
  INDEX `ecny_batch_checkpathdtl_idx1`(`workdate`, `batchid`, `checkstatus`) USING BTREE,
  INDEX `ecny_batch_checkpathdtl_idx2`(`batchid`, `dtlmsgid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通道对账明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_batch_checkresult
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_checkresult`;
CREATE TABLE `pay_batch_checkresult`  (
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `coreacctdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心会计日期',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `payflag` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收付标识',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发起机构',
  `dbitparty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款机构编码',
  `payerwalletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人钱包id',
  `payeraccount` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人账号',
  `crdtparty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款机构编码',
  `payeename` varchar(480) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人名称',
  `payeeaccount` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人账号',
  `payeewalletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人钱包id',
  `ccy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '货币代码',
  `amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '金额',
  `ognlmsgtype` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原报文编号',
  `ognlmsgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原报文标识号',
  `tradestatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易状态',
  `corestatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心状态',
  `pathstatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道状态',
  `checkstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '对账标识',
  `procstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '不平记录处理状态',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`paydate`, `payserno`) USING BTREE,
  INDEX `ecny_batch_checkresult_idx1`(`msgid`) USING BTREE,
  INDEX `ecny_batch_checkresult_idx2`(`batchid`) USING BTREE,
  INDEX `ecny_batch_checkresult_idx3`(`coreacctdate`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通道对账结果表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_batch_finance_report
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_finance_report`;
CREATE TABLE `pay_batch_finance_report`  (
  `cashout_amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑出金额',
  `cashout_success_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑出成功笔数',
  `cashout_total_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑出总笔数',
  `cashin_amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑回金额',
  `cashin_success_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑回成功笔数',
  `cashin_total_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑回总笔数',
  `remitout_amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '汇款兑出金额',
  `remitout_success_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '汇款兑出成功笔数',
  `remitout_total_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '汇款兑出总笔数',
  `report_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报表日期',
  PRIMARY KEY (`report_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '金融交易统计报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_batch_finance_report
-- ----------------------------
INSERT INTO `pay_batch_finance_report` VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0', '2015-1-1');
INSERT INTO `pay_batch_finance_report` VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0', '2018-10-1');
INSERT INTO `pay_batch_finance_report` VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0', '2018-10-2');
INSERT INTO `pay_batch_finance_report` VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0', '2021-03-19');

-- ----------------------------
-- Table structure for pay_batch_nonfinance_report
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_nonfinance_report`;
CREATE TABLE `pay_batch_nonfinance_report`  (
  `tied_card_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '绑卡数量',
  `untie_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '解绑数量',
  `report_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报表日期',
  PRIMARY KEY (`report_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '非金融交易统计报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_batch_nonfinance_report
-- ----------------------------
INSERT INTO `pay_batch_nonfinance_report` VALUES ('0', '0', '2015-1-1');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('0', '0', '2018-10-1');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('0', '0', '2018-10-2');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('0', '0', '2021-03-19');

-- ----------------------------
-- Table structure for pay_comm_core_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_core_config`;
CREATE TABLE `pay_comm_core_config`  (
  `serverid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '服务id',
  `acctmeth` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '核算方式',
  `serverclass` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '服务类名',
  `reverseflag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '冲正服务使用方式',
  PRIMARY KEY (`serverid`, `acctmeth`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '核心服务映射配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_coretradetypeinfo
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_coretradetypeinfo`;
CREATE TABLE `pay_comm_coretradetypeinfo`  (
  `paypath` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '通道代码',
  `acctmeth` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '核算方式',
  `seq` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '序号',
  `coreaccmode` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '记账方式',
  `coreprdmode` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '产品模型',
  `coreprdcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '产品代码',
  `coreeventcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '事件代码',
  `coreaccelement` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算要素',
  `coreaccbrnotype1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构类型1',
  `coreaccbrno1` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构1',
  `coreaccbrnotype2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构类型2',
  `coreaccbrno2` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构2',
  `coreaccbrnotype3` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构类型3',
  `coreaccbrno3` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构3',
  `coreaccbrnotype4` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构类型4',
  `coreaccbrno4` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构4',
  `coreaccbrnotype5` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构类型5',
  `coreaccbrno5` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算机构5',
  `coreaccamounttype1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型1',
  `coreaccamountptr1` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针1',
  `coreaccamounttype2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型2',
  `coreaccamountptr2` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针2',
  `coreaccamounttype3` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型3',
  `coreaccamountptr3` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针3',
  `coreaccamounttype4` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型4',
  `coreaccamountptr4` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针4',
  `coreaccamounttype5` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型5',
  `coreaccamountptr5` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针5',
  `coreaccamounttype6` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型6',
  `coreaccamountptr6` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针6',
  `coreaccamounttype7` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型7',
  `coreaccamountptr7` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针7',
  `coreaccamounttype8` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型8',
  `coreaccamountptr8` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针8',
  `coreaccamounttype9` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型9',
  `coreaccamountptr9` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针9',
  `coreaccamounttype10` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额类型10',
  `coreaccamountptr10` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核算金额指针10',
  `coreprtabscode` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '打印摘要代码',
  `corefeecode` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '费用代码',
  `coredrtrxtype` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易类型',
  `coremateaccno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '默认对手账号',
  `corematename` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '默认对手户名',
  `coremateunitecode` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '默认对手联行号',
  `coremateunitename` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '默认对手联行名',
  `coremateflag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '对手信息获取方式',
  PRIMARY KEY (`paypath`, `acctmeth`, `seq`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '核算规则配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_coretradetypeinfo
-- ----------------------------
INSERT INTO `pay_comm_coretradetypeinfo` VALUES ('ECNY', 'DJ010011', '1', 'A', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `pay_comm_coretradetypeinfo` VALUES ('ECNY', 'DJ010011', '2', 'E', 'OSTL', NULL, 'ECNYQSCR', NULL, '', '173001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `pay_comm_coretradetypeinfo` VALUES ('ECNY', 'DJ050001', '1', 'E', 'OSTL', NULL, 'ECNYQSDR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `pay_comm_coretradetypeinfo` VALUES ('ECNY', 'DJ050001', '2', 'A', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for pay_comm_excepteventconfig
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_excepteventconfig`;
CREATE TABLE `pay_comm_excepteventconfig`  (
  `excepteventcode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件代码',
  `exceptdealmode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常处理模式',
  `excepteventtrxcode` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常处理交易码',
  `excepteventdealmaxcount` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常处理最大尝试次数',
  `excepteventdealtype` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常处理类型',
  `excepteventdealintervalmin` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '处理时间间隔',
  `excepteventremark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件描述',
  PRIMARY KEY (`excepteventcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '异常事件配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_excepteventconfig
-- ----------------------------
INSERT INTO `pay_comm_excepteventconfig` VALUES ('EVENTNCHECKDETIAL', 'ASYNC_INVOKE', 'batch/handleCheckDetail', '3', 'AUTO', '05S', '批量对明细账事件');
INSERT INTO `pay_comm_excepteventconfig` VALUES ('EVENTNCHECKSUMMARY', 'ASYNC_INVOKE', 'batch/handleCheckGeneralLedger', '3', 'AUTO', '05S', '批量对总账事件');
INSERT INTO `pay_comm_excepteventconfig` VALUES ('EVENT_CORE_ENQUIRE', 'ASYNC_INVOKE', 'coreQry/runCoreQry', '5', 'AUTO', '05S', '核心回查事件');
INSERT INTO `pay_comm_excepteventconfig` VALUES ('EVENT_CORE_RECREDIT', 'ASYNC_INVOKE', 'reCredit/runReCredit', '3', 'AUTO', '05S', '补入账事件');
INSERT INTO `pay_comm_excepteventconfig` VALUES ('EVENT_CORE_REVERSAL', 'ASYNC_INVOKE', 'bankRev/runBankRev', '3', 'AUTO', '05S', '核心冲正事件');
INSERT INTO `pay_comm_excepteventconfig` VALUES ('EVENT_PAYPATH_STATUS_QRY', 'ASYNC_INVOKE', 'pathQry/runPathQry', '3', 'AUTO', '05S', '通道状态回查事件');

-- ----------------------------
-- Table structure for pay_comm_excepteventinfo
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_excepteventinfo`;
CREATE TABLE `pay_comm_excepteventinfo`  (
  `excepteventdate` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件登记日期',
  `excepteventserno` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件登记流水',
  `excepteventtime` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件登记时间',
  `excepteventcode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件代码',
  `excepteventseqno` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道系统流水',
  `excepteventdealcount` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件当前处理次数',
  `excepteventsysstatus` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件处理状态',
  `excepteventerrorcode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常处理业务错误码',
  `excepteventerrormsg` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常处理业务错误信息',
  `excepteventdealpath` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常事件的处理路径，登记处理服务器',
  `excepteventcontext` varchar(1200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常事件参数',
  `lastupdate` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常事件最后处理日期',
  `lastuptime` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常事件最后处理时间',
  PRIMARY KEY (`excepteventdate`, `excepteventserno`) USING BTREE,
  UNIQUE INDEX `idx_pay_comm_excepteventinfo`(`excepteventcode`, `excepteventseqno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '异常事件流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_idempotent
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_idempotent`;
CREATE TABLE `pay_comm_idempotent`  (
  `msgid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报文标识号',
  `msgtp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报文编号',
  `sender` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送机构',
  `receiver` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收机构',
  `snddttm` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报文发送时间',
  `direct` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方向',
  `hostname` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主机名',
  PRIMARY KEY (`msgid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易堵重表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_monitor
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_monitor`;
CREATE TABLE `pay_comm_monitor`  (
  `exceptdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '异常登记日期',
  `exceptserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '异常登记流水',
  `excepttime` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'isodate数据',
  `exceptscenario` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '异常交易场景',
  `exceptparams` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '优先存msgid或者平台日期流水',
  `exceptcontext` varchar(1200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '例如：汇总对账报文711入cnap_online_checkpath失败',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`exceptdate`, `exceptserno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_param_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_param_config`;
CREATE TABLE `pay_comm_param_config`  (
  `pamkey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '配置参数key',
  `pamvalue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '配置参数value',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生效状态：0不生效，1生效',
  `pamCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`pamkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_param_config
-- ----------------------------
INSERT INTO `pay_comm_param_config` VALUES ('C201', '03011', '1', 'BIZTP');
INSERT INTO `pay_comm_param_config` VALUES ('E100', '08001', '1', 'BIZTP');

-- ----------------------------
-- Table structure for pay_comm_party
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_party`;
CREATE TABLE `pay_comm_party`  (
  `partyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构编码',
  `partytype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构类型',
  `partyname` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '机构名称',
  `partyalias` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '机构标识',
  `partystatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构状态',
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '撤销状态',
  `contact` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `mail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `fax` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '传真号',
  `effectdate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '失效日期',
  `ineffectivedate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '失效日期',
  `changenumber` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '变更期数',
  `changecircletimes` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '变更记录条目',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`) USING BTREE,
  INDEX `pay_inst_info_idx1`(`effectdate`, `lastupdate`) USING BTREE,
  INDEX `pay_inst_info_idx2`(`partyalias`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '机构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_party
-- ----------------------------
INSERT INTO `pay_comm_party` VALUES ('1', 'PT00', '神码', 'dc', 'ST00', '1', NULL, NULL, NULL, NULL, '20210307', '20220307', '0', '0', NULL, NULL);
INSERT INTO `pay_comm_party` VALUES ('2', 'PT01', '神州', 'dt', 'ST01', '1', NULL, NULL, NULL, NULL, '20210307', '20220307', '0', '0', NULL, NULL);
INSERT INTO `pay_comm_party` VALUES ('3', 'PT01', '3', '3', 'ST00', '1', '3', '3', '3', '3', '20210307', NULL, '3', '3', '3', '3');
INSERT INTO `pay_comm_party` VALUES ('4', 'PT03', '神息', 'di', 'ST01', '1', NULL, NULL, NULL, NULL, '20210307', '20220307', '0', '0', NULL, NULL);
INSERT INTO `pay_comm_party` VALUES ('5', 'PT00', '神数', 'ds', 'ST01', '1', NULL, NULL, NULL, NULL, '20210307', '20220307', '0', '0', NULL, NULL);
INSERT INTO `pay_comm_party` VALUES ('C1010411000013', 'PT00', '中国邮政储蓄银行', '008000', 'ST01', '1', 'Z', '111', '1@qq.com', '1', '20210307', '20220307', '500', '0', '20210305', '165632');
INSERT INTO `pay_comm_party` VALUES ('C1030644021075', 'PT00', '中国邮政储蓄银行', '008000', 'ST01', '1', 'Z', '111', '1@qq.com', '1', '20210307', '20220307', '500', '0', '20210317', '093318');
INSERT INTO `pay_comm_party` VALUES ('C1040311005293', 'PT00', '中国邮政储蓄银行', '008000', 'ST02', '1', 'Z', '111', '1@qq.com', '1', '20210113114554', NULL, '500', '0', '20210330', '100135');

-- ----------------------------
-- Table structure for pay_comm_party_tobeffect
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_party_tobeffect`;
CREATE TABLE `pay_comm_party_tobeffect`  (
  `partyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构编码',
  `changetype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '变更类型',
  `effectivetype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '生效类型',
  `partytype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '机构类型',
  `partyname` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构名称',
  `partyalias` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构标识',
  `partystatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构状态',
  `contact` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '电话',
  `mail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '邮箱',
  `fax` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '传真号',
  `effectdate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生效日期',
  `ineffectivedate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '失效日期',
  `changenumber` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '变更期数',
  `changecircletimes` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '变更记录条目',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`) USING BTREE,
  INDEX `pay_inst_info_idx1`(`effectdate`, `lastupdate`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '机构待生效表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_partyauth
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_partyauth`;
CREATE TABLE `pay_comm_partyauth`  (
  `partyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文类型',
  `tradectgycode` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务类型',
  `sendauth` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '发送权限标识',
  `recvauth` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '接收权限标识',
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '撤销状态1-生效中 0-已撤销',
  `effectdate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生效日期',
  `ineffectivedate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '失效日期',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`, `msgtype`, `tradectgycode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '业务权限变更信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_partyauth
-- ----------------------------
INSERT INTO `pay_comm_partyauth` VALUES ('C1010411000013', 'dcep.221.001.01', 'null', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1010411000013', 'dcep.225.001.01', 'C201', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1010411000013', 'dcep.801.001.01', 'E100', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.221.001.01', 'E100', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.225.001.01', 'D201', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.401.001.01', '11', 'AS00', 'AS00', '1', '20210113', NULL, '20210330', '102937');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.401.001.01', 'null', 'AS00', 'AS00', '1', '20210113', NULL, '20210308', '114729');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.433.001.01', 'null', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.801.001.01', 'E100', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');

-- ----------------------------
-- Table structure for pay_comm_partyauth_tobeffect
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_partyauth_tobeffect`;
CREATE TABLE `pay_comm_partyauth_tobeffect`  (
  `partyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '机构编码',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `tradectgycode` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务类型',
  `sendauth` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '发起权限标识',
  `recvauth` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '接收权限标识',
  `changetype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '变更类型',
  `effectivetype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '生效类型',
  `effectdate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生效日期',
  `ineffectivedate` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '失效日期',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`, `msgtype`, `tradectgycode`) USING BTREE,
  INDEX `pay_comm_partyauth_tobeffect_effectdate_idx`(`effectdate`, `ineffectivedate`, `lastupdate`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '业务权限变更临时表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_rspcodemap
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_rspcodemap`;
CREATE TABLE `pay_comm_rspcodemap`  (
  `paypath` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '通道',
  `srcid` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '源系统',
  `destid` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '目标系统',
  `txntype` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易类别',
  `srcrspcode` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '源响应码1',
  `srcrspcode2` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '源响应码2',
  `srcrspcodedsp` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '源响应信息描述',
  `destrspcode` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '目标响应码1',
  `destrspcode2` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '目标响应码2',
  `rspcodedsp` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '响应信息描述',
  `rsv1` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`paypath`, `srcid`, `destid`, `txntype`, `srcrspcode`, `srcrspcode2`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '错误码映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_rspcodemap
-- ----------------------------
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI0016', '*', '客户名称不一致', 'R022', '*', '账号、户名不符', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6038', '*', '有效身份证件已过有效期三个月，拒绝交易', 'R134', '*', '身份证过期', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6060', '*', '此客户为非实名制客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6061', '*', '该客户为黑名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6062', '*', '该客户为本行风险名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6063', '*', '该客户为三方风险名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6064', '*', '该客户为外国政要名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6065', '*', '该客户为制裁名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6066', '*', '该客户为特殊关注名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6068', '*', '该客户为其他官方名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6069', '*', '该客户为其他禁令名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI6070', '*', '该客户为人行黑名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI7207', '*', '账户已销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI7315', '*', '转出方账户状态异常，请临柜核验调整', 'R055', '*', '付款账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI7317', '*', '该客户为黑名单客户，请临柜核验调整', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI7318', '*', '该账户为黑名单账户，请临柜核验调整', 'R069', '*', '黑名单账户', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI8000', '*', '该客户为黑名单客户', 'R074', '*', '其他因风险控制导致的交易失败', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI8093', '*', '客户白名单对应记录不存在', 'R128', '*', '客户不在白名单中', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CI8116', '*', '留存我行的个人客户信息（联系等）不规范，交易失败，请更新后重新发起', 'R058', '*', '未预留手机号码', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1002', '*', '证件类型不一致', 'R036', '*', '账户不支持此类交易', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1003', '*', '证件号码不一致', 'R037', '*', '身份证件类型错误', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1004', '*', '客户名称不一致', 'R022', '*', '账号、户名不符', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1015', '*', '账户类型错误', 'R024', '*', '账户类型错', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1200', '*', '账户冻结或权利机关金额冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1512', '*', '账户销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1513', '*', '账户冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1514', '*', '账户黑名单', 'R069', '*', '黑名单账户', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'CM1517', '*', '该卡已挂失', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC0012', '*', '卡处于挂失状态', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC0035', '*', '卡已处于口头挂失状态', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC0091', '*', '卡已过期', 'R028', '*', '过期卡', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC1019', '*', '无正确商户代码', 'R130', '*', '推送商户账号不存在', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC1046', '*', '余额不足', 'R023', '*', '账户余额不足支付', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC1103', '*', '交易次数超出日限制', 'R076', '*', '账户当日交易次数超限', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC1107', '*', '交易次数超出日限制', 'R076', '*', '账户当日交易次数超限', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC1637', '*', '输入的电话号码和预留的安全手机号码不匹配', 'R059', '*', '手机号码错误', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC1771', '*', '此为存量密码挂失的卡，请进行实时重置', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2070', '*', '无该账户信息，请检查输入账户的有效性', 'R021', '*', '账户不存在', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2221', '*', '活期账户已经销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2222', '*', '定期账户已经销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2226', '*', '账户已经有账户冻结，不能做只借记', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2227', '*', '账户已有权力机关冻结，不能做只借记', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2250', '*', '账户已被冻结，请确认继续内部暂禁交易或退出', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2251', '*', '账户已被冻结，请确认继续冻结交易或退出', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2252', '*', '账户已被法律冻结，请确认继续冻结交易或退出', '*', 'R032', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2253', '*', '账户已被有权机关暂禁，请确认继续冻结交易或退出', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2318', '*', '主账户冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2319', '*', '主账户暂禁', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2322', '*', '主账户暂禁（有权机关发起）', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2370', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC2423', '*', '卡已挂失不能进行此交易', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC9430', '*', '定期主账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC9468', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DC9624', '*', '卡已处于密码挂失状态', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0002', '*', '账户未激活，不能进行此交易', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0006', '*', '账户密码锁定，不能进此交易', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0007', '*', '凭证为头口挂失状态，不能进行此交易', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0008', '*', '凭证为正式挂失状态，不能进行此交易', 'R030', '*', '已挂失', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0009', '*', '活期账户冻结状态，交易拒绝', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0011', '*', '活期账户状态销户（结清），不能进行此交易', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0202', '*', '账户未激活', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD0209', '*', '账户冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD1001', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD1020', '*', '账户未激活', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD1343', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD1401', '*', '账户已销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD1452', '*', '余额不足', 'R023', '*', '账户余额不足支付', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD1602', '*', '客户名称不一致', 'R022', '*', '账号、户名不符', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD2059', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD2107', '*', '账户已冻结，交易拒绝', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD2115', '*', '账户已销户计息', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD2116', '*', '账户已销户结息', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD3007', '*', '入帐账户已经销户', 'R056', '*', '收款账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD3009', '*', '账户已经密码待重置', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD3061', '*', '账户没有激活', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD3125', '*', '账户整户冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD7011', '*', '账户已销户计息', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD7070', '*', '账户密码锁定，不能进此交易', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD7120', '*', '账户已销户计息,不允许交易', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD7121', '*', '账户已销户，不允许交易', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD7230', '*', '账户已有权力机关冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'DD7593', '*', '该账户非柜面转账日限额超限，请修改非柜面转账限额或临柜处理', 'R043', '*', '当日业务累计金额超过规定金额', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'GD0002', '*', '证件类型错误', 'R057', '*', '不支持的证件类型', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'GD0031', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'GD0032', '*', '账户已销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'GD0100', '*', '账户未激活', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'GD0103', '*', '账户被密码锁定', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'IB0024', '*', '账户已销户，请先重开户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'IB0028', '*', '账户已销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'IB0030', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'IB0031', '*', '账户已法律冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'LN3175', '*', '该账户全部冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'NM4019', '*', '账户已冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'NM4020', '*', '账户已销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'NM4029', '*', '卡密码输入错误次数过多', 'R132', '*', '密码输入错误次数超限', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD1022', '*', '账户已被特殊冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD1023', '*', '账户已被部分冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD1024', '*', '账户已被法律冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD3041', '*', '密码输入错误', 'R131', '*', '密码输入错误（还剩X次）', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD3113', '*', '账户已被法律冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD3117', '*', '账户已销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD3192', '*', '账户已被冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD4101', '*', '账户已销户', 'R034', '*', '账户状态异常', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD4219', '*', '主账户已账户冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'C', 'TD4221', '*', '已账户冻结', 'R032', '*', '已冻结', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', '*', '*', '账户验证错误', 'R042', '*', '核验身份错误', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CI3001', '*', '账号状态异常（暂禁/冻结/质押）', 'R034', '*', '账户状态异常', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CI7207', '*', '账户已销户', 'R033', '*', '已清户', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CI8116', '*', '留存我行的个人客户信息（联系等）不规范，交易失败，请更新后重新发起', 'R058', '*', '未预留手机号码', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CM1002', '*', '证件类型不一致', 'R036', '*', '身份证件类型错误', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CM1003', '*', '证件号码不一致', 'R037', '*', '身份证号码错误', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CM1004', '*', '客户名称不一致', 'R022', '*', '账户、户名不符', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CM1005', '*', '手机号码不一致', 'R059', '*', '手机号码错误', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CM1513', '*', '账户冻结', 'R032', '*', '已冻结', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CM1517', '*', '该卡已挂失', 'R030', '*', '已挂失', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'CM1518', '*', '该卡已过期', 'R028', '*', '过期卡', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'DC1637', '*', '输入的电话号码和预留的安全手机号码不匹配', 'R059', '*', '手机号码错误', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'CRPM', 'DCEP', 'S', 'GD0002', '*', '证件类型错误', 'R057', '*', '不支持的证件类型', NULL);
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'ECNY', 'DCEP', 'C', 'ECNYC10001', '*', '业务类型与业务种类不匹配', 'R007', '*', '请求报文参数有误', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'ECNY', 'DCEP', 'C', 'ECNYC10005', '*', '交易资金来源不能为空', 'R007', '*', '请求报文参数有误', '');
INSERT INTO `pay_comm_rspcodemap` VALUES ('ECNY', 'ECNY', 'DCEP', 'C', 'ECNYS09999', '*', '其他错误', 'ECNYS09999', '*', '其他错误', '');

-- ----------------------------
-- Table structure for pay_comm_task_exec
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_task_exec`;
CREATE TABLE `pay_comm_task_exec`  (
  `settledate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '清算日期',
  `taskcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务代码',
  `taskname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
  `taskgroupcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组代码',
  `taskgroupname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组名称',
  `busicode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务代码',
  `busicodename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务名称',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `tasktype` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务类型',
  `taskindex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务顺序',
  `taskclassname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务执行类',
  `execparam` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '执行参数',
  `execstate` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '执行状态',
  `starttime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建时间',
  `endtime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`settledate`, `taskgroupcode`, `taskcode`, `batchid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '任务执行信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_task_exec
-- ----------------------------
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202101131600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210325080115', '20210325080136');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CT001', 'CT001-对账明细文件转存', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileListTask', '', 'EXPT', '20210325123916', '20210325123916');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CT002', 'CT002-金融开放平台申请文件', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailSendFileReqTask', '', 'INIT', '20210325123915', '20210325123915');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CT003', 'CT003-检查明细文件是否收齐', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileCheckTask', '', 'INIT', '20210325123915', '20210325123915');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CT004', 'CT004-解析明细文件入库任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', '00', '04', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileLoadTask', '', 'INIT', '20210325123915', '20210325123915');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CT005', 'CT005-抽数前置任务检查', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', '00', '05', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', 'INIT', '20210325123915', '20210325123915');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CT006', 'CT006-明细对账任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', '00', '06', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailTask', '', 'INIT', '20210325123915', '20210325123915');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CT007', 'CT007-明细对账结果处理任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', '00', '07', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailResultHandleTask', '', 'INIT', '20210325123915', '20210325123915');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CS001', 'CS002-抽数前置任务检', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202101131600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', 'SUCC', '20210325080521', '20210325080533');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CS002', 'CS002-汇总对账处理', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202101131600', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryTask', '', 'SUCC', '20210325080534', '20210325080534');
INSERT INTO `pay_comm_task_exec` VALUES ('20210113', 'CS003', 'CS003-汇总对账结果处理', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202101131600', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryResultHandleTask', '', 'SUCC', '20210325080534', '20210325080535');

-- ----------------------------
-- Table structure for pay_comm_task_group_exec
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_task_group_exec`;
CREATE TABLE `pay_comm_task_group_exec`  (
  `settledate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '清算日期',
  `taskgroupcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组代码',
  `taskgroupname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组名称',
  `busicode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务代码',
  `busicodename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务名称',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `execstate` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '执行状态',
  `createtime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建时间',
  `updatetime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`settledate`, `taskgroupcode`, `batchid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '任务组执行信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_task_group_exec
-- ----------------------------
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210113', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202101131600', 'SUCC', '20210325015904', '20210325080148');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210113', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202101131600', 'EXPT', '20210325123915', '20210325123916');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210113', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202101131600', 'SUCC', '20210325110505', '20210325080535');

-- ----------------------------
-- Table structure for pay_comm_task_group_info
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_task_group_info`;
CREATE TABLE `pay_comm_task_group_info`  (
  `taskgroupcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组代码',
  `taskgroupname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组名称',
  `busicode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务代码',
  `busicodename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务名称',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`taskgroupcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '任务组信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_task_group_info
-- ----------------------------
INSERT INTO `pay_comm_task_group_info` VALUES ('CHK_DATA', '抽数批次', 'D0001', '行内', '');
INSERT INTO `pay_comm_task_group_info` VALUES ('CHK_DTL', '明细对账', 'D0001', '行内', '');
INSERT INTO `pay_comm_task_group_info` VALUES ('CHK_SUM', '汇总对账', 'D0001', '行内', '');

-- ----------------------------
-- Table structure for pay_comm_task_info
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_task_info`;
CREATE TABLE `pay_comm_task_info`  (
  `taskcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务代码',
  `taskname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
  `taskgroupcode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组代码',
  `tasktype` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务类型',
  `taskindex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务顺序',
  `taskclassname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务执行类',
  `execparam` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '执行参数',
  `taskstate` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务状态',
  PRIMARY KEY (`taskcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '任务信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_task_info
-- ----------------------------
INSERT INTO `pay_comm_task_info` VALUES ('CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CS001', 'CS002-抽数前置任务检', 'CHK_SUM', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CS002', 'CS002-汇总对账处理', 'CHK_SUM', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CS003', 'CS003-汇总对账结果处理', 'CHK_SUM', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryResultHandleTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CT001', 'CT001-对账明细文件转存', 'CHK_DTL', '00', '01', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileListTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CT002', 'CT002-金融开放平台申请文件', 'CHK_DTL', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailSendFileReqTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CT003', 'CT003-检查明细文件是否收齐', 'CHK_DTL', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileCheckTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CT004', 'CT004-解析明细文件入库任务', 'CHK_DTL', '00', '04', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileLoadTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CT005', 'CT005-抽数前置任务检查', 'CHK_DTL', '00', '05', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CT006', 'CT006-明细对账任务', 'CHK_DTL', '00', '06', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailTask', '', '00');
INSERT INTO `pay_comm_task_info` VALUES ('CT007', 'CT007-明细对账结果处理任务', 'CHK_DTL', '00', '07', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailResultHandleTask', '', '00');

-- ----------------------------
-- Table structure for pay_online_checkclear
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkclear`;
CREATE TABLE `pay_online_checkclear`  (
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `senderdatetime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '报文发送时间',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发起机构',
  `instddrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接收机构',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `cleardate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算日期',
  `clearcountnum` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算总笔数',
  `cleardbttotamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算借方总金额',
  `clearcbttotamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算贷方总金额',
  `clearnetnum` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '清算场次编号',
  `clearmsgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算报文标识号',
  `cleardrct` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算借贷标识',
  `clearamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算金额',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '批次号',
  `batchdrct` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '批次借贷标识',
  `batchnetamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '批次扎差净额',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`, `batchid`, `clearnetnum`) USING BTREE,
  INDEX `pay_online_checkclear_clear_msg_id_idx`(`clearmsgid`) USING BTREE,
  INDEX `pay_online_checkclear_clear_date_idx`(`cleardate`, `batchid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资金调整汇总核对表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_online_checkfilelist
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkfilelist`;
CREATE TABLE `pay_online_checkfilelist`  (
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `srcfilepath` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '源文件路径',
  `filename` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件名',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`, `batchid`, `filename`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通道对账明细文件列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_online_checkfilelist
-- ----------------------------
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251400_00_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/01/', 'B202005251400_01_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/02/', 'B202005251400_02_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/03/', 'B202005251400_03_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/33/', 'B202005251400_33_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/46/', 'B202005251400_46_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/68/', 'B202005251400_68_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/73/', 'B202005251400_73_01.zip.sec', '20210329', '195604');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210113000171181982980514700000', 'B202101131600', '127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/76/', 'B202005251400_76_01.zip.sec', '20210329', '195604');

-- ----------------------------
-- Table structure for pay_online_checkpath_main
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkpath_main`;
CREATE TABLE `pay_online_checkpath_main`  (
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `senderdatetime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文发送时间',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `instddrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '接收机构',
  `batchdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '批次日期',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `countnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '总笔数',
  `countamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '总金额',
  `ccy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '货币代码',
  `dbitcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款笔数',
  `dbitcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款金额',
  `crdtcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款笔数',
  `crdtcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款金额',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  `reconindex` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '区块链对账号',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `digitalenvelope` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '数字信封',
  PRIMARY KEY (`paydate`, `payserno`) USING BTREE,
  UNIQUE INDEX `pay_batch_checkpath_idx1`(`msgid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_online_checkpath_main
-- ----------------------------
INSERT INTO `pay_online_checkpath_main` VALUES ('20210329', '19560405001', '195604', '20210113000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '20210113', 'B202101131600', '15', '1016134', 'CNY', '4', '10396', '11', '1005738', '20210329', '195604', '0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba', '机构对账汇总核对报文', NULL);

-- ----------------------------
-- Table structure for pay_online_checkpath_sub
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkpath_sub`;
CREATE TABLE `pay_online_checkpath_sub`  (
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `batchdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '批次日期',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `splitnum` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '分片编号',
  `splitcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分片总笔数',
  `splitcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分片总金额',
  `splitdbitcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分片付款笔数',
  `splitdbitcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分片付款金额',
  `splitcrdtcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分片收款笔数',
  `splitcrdtcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分片收款金额',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `msgbizstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务状态',
  `msgcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '总笔数',
  `msgcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '总金额',
  `msgdbitcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款笔数',
  `msgdbitcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款金额',
  `msgcrdtcountnum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款笔数',
  `msgcrdtcountamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款金额',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`, `splitnum`, `msgtype`, `msgbizstatus`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通道对账汇总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_online_checkpath_sub
-- ----------------------------
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210113000171181982980514700000', '20210113', 'B202101131600', '00', '2', '1000100', '0', '0', '2', '1000100', 'dcep.227.001.01', 'PR00', '1', '100', '0', '0', '1', '100', '20210329', '195604');
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210113000171181982980514700000', '20210113', 'B202101131600', '00', '2', '1000100', '0', '0', '2', '1000100', 'dcep.227.001.01', 'PR01', '1', '1000000', '0', '0', '1', '1000000', '20210329', '195604');
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210113000171181982980514700000', '20210113', 'B202101131600', '68', '2', '7', '0', '0', '2', '7', 'dcep.221.001.01', 'PR01', '1', '1', '0', '0', '1', '1', '20210329', '195604');
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210113000171181982980514700000', '20210113', 'B202101131600', '68', '2', '7', '0', '0', '2', '7', 'dcep.227.001.01', 'PR03', '1', '6', '0', '0', '1', '6', '20210329', '195604');
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210113000171181982980514700000', '20210113', 'B202101131600', '76', '2', '20', '0', '0', '2', '20', 'dcep.227.001.01', 'PR00', '1', '10', '0', '0', '1', '10', '20210329', '195604');
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210113000171181982980514700000', '20210113', 'B202101131600', '76', '2', '20', '0', '0', '2', '20', 'dcep.227.001.01', 'PR03', '1', '10', '0', '0', '1', '10', '20210329', '195604');

-- ----------------------------
-- Table structure for pay_pay_accflow
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_accflow`;
CREATE TABLE `pay_pay_accflow`  (
  `corereqdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求核心日期',
  `corereqserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求核心流水',
  `booktype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '记账类型',
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务日期',
  `payserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务流水',
  `brno` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '行所号',
  `tellerno` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '柜员号',
  `acctbrno` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '记账网所',
  `currency` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '币种',
  `amount` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '金额',
  `feeamount` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手续费',
  `coresysid` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心系统标识',
  `coreintflag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心接口标志',
  `revtranflag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '反交易标志',
  `coretime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心时间',
  `coretrxtype` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心交易类型',
  `coretrxcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心交易代码',
  `coreprocstatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心状态',
  `coreretcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心返回代码',
  `coreretmsg` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心返回信息',
  `coreacctdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心返回日期',
  `coreserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心返回流水',
  `payeracct` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人账号',
  `payername` varchar(244) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人名称',
  `payeeacct` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人账号',
  `payeename` varchar(244) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人名称',
  `origcorereqdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原请求核心日期',
  `origcorereqserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原请求核心流水',
  `origpaydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原业务日期',
  `origpayserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原业务流水',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastmicrosecond` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新毫秒',
  PRIMARY KEY (`corereqdate`, `corereqserno`) USING BTREE,
  INDEX `pay_pay_trade_jrn_ind01`(`paydate`, `payserno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '账务流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_pay_notify
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_notify`;
CREATE TABLE `pay_pay_notify`  (
  `txenddate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知日期',
  `txendserno` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知请求流水',
  `txendmsgid` varchar(39) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知报文标识号',
  `txendmsgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知报文编号',
  `txendinstgpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '终态通知发起机构',
  `txendinstdpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '终态通知接收机构',
  `processstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务状态',
  `processcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务处理码',
  `rejectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '机构业务拒绝码',
  `rejectinf` varchar(420) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务拒绝信息',
  `msgid` varchar(39) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '原报文编号',
  `instgpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原发起机构',
  `instdpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原接收机构',
  `amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易金额',
  `ccy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '币种',
  `remark` varchar(560) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '终态通知备注',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间',
  `lastmicrosecond` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间毫秒',
  PRIMARY KEY (`txenddate`, `txendserno`) USING BTREE,
  UNIQUE INDEX `pay_pay_notify_msgid_idx`(`msgid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '终态通知请求登记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_pay_transdtl
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_transdtl`;
CREATE TABLE `pay_pay_transdtl`  (
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `direct` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '往来标识',
  `payflag` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收付标识',
  `operstep` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作步骤',
  `operstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作状态',
  `trxstatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务状态',
  `trxretcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务处理码',
  `trxretmsg` varchar(420) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务处理信息',
  `coreprocstatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心处理状态',
  `corereqdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心请求日期',
  `corereqserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心请求流水',
  `coreacctdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心返回日期',
  `coreserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心返回流水',
  `coreretcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心拒绝码',
  `coreretmsg` varchar(420) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心拒绝信息',
  `paypathdatetime` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道日期时间',
  `paypathserno` varchar(39) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '通道流水',
  `pathprocstatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道状态',
  `paypathrspstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道回执业务状态',
  `paypathretcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道返回码',
  `paypathretmsg` varchar(420) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道返回信息',
  `paypathretdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道返回日期',
  `paypathretserno` varchar(39) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通道返回流水',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易批次号',
  `busichnl` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '渠道大类',
  `busichnl2` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '渠道中类',
  `busisysdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '渠道日期',
  `busisysserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '渠道流水',
  `busisystime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '渠道时间',
  `msgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `busitype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务类型',
  `busikind` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务种类',
  `instgpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发起机构',
  `instdpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接收机构',
  `amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易金额',
  `tradefundsource` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易资金来源',
  `tradepurpose` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易用途',
  `payerptyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人钱包所属机构',
  `payername` varchar(480) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人名称',
  `payeraccttype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人账户类型',
  `payeracct` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人账户账号',
  `payerwalletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人钱包id',
  `payerwalletname` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人钱包名称',
  `payerwalletlv` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人钱包等级',
  `payerwallettype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '付款人钱包类型',
  `payeeptyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人账户所属机构',
  `payeename` varchar(480) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人名称',
  `payeeaccttype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人账户类型',
  `payeeacct` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人账户账号',
  `payeewalletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人钱包id',
  `payeewalletname` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人钱包名称',
  `payeewalletlv` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人钱包等级',
  `payeewallettype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款人钱包类型',
  `protocolnum` varchar(34) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '挂接协议号',
  `ccy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '币种',
  `tellerno` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '柜员号',
  `zoneno` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分行号',
  `brno` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易行所号',
  `acctbrno` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '账务行所',
  `origchnl` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '源发起渠道大类',
  `origchnl2` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '源发起渠道中类',
  `origchnldtl` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '源发起渠道细分',
  `origmsgtype` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原报文编号',
  `origpaypathdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原业务通道日期',
  `origpaypathserno` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '原业务通道流水',
  `summary` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '摘要码',
  `endtoendid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '端对端标志',
  `lastupjrnno` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新流水',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间',
  `narrative` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '附言',
  `remark` varchar(560) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`paydate`, `payserno`) USING BTREE,
  UNIQUE INDEX `pay_pay_transdtl_paypathserno_idx`(`paypathserno`) USING BTREE,
  INDEX `pay_pay_transdtl_busisysserno_idx`(`busisysserno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '金融交易登记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_pay_transdtl_nonf
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_transdtl_nonf`;
CREATE TABLE `pay_pay_transdtl_nonf`  (
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `paytime` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `payserno` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `pkgno` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `drct` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文方向',
  `tradestatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易状态',
  `senderdatetime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文发送时间',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `instddrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '接收机构',
  `opterationtype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作类型',
  `procstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务处理状态',
  `rejectcode` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务拒绝码',
  `rejectinfo` varchar(315) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务拒绝信息',
  `tlrno` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '柜员号',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `messagecontext` varchar(3072) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '信息内容',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`) USING BTREE,
  INDEX `pay_free_talk_idx1`(`paydate`, `payserno`, `pkgno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '非金融登记簿' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_sign_signinfo
-- ----------------------------
DROP TABLE IF EXISTS `pay_sign_signinfo`;
CREATE TABLE `pay_sign_signinfo`  (
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `signno` varchar(34) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '挂接协议号',
  `signstatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '协议状态N：签约状态C：解约状态',
  `acctptyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户所属机构',
  `accttype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户类型',
  `acctid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户账号',
  `acctname` varchar(960) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户户名',
  `idtype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件类型',
  `idno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件号码',
  `telephone` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '银行预留手机号码',
  `walletptyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '钱包开立所属机构编码',
  `walletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '钱包id',
  `wallettype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包',
  `walletlevel` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '钱包等级WL01：一类钱包，WL02：二类钱包，WL03：三类钱包，WL04：四类钱包',
  `lastupjrnno` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新流水',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`signno`) USING BTREE,
  UNIQUE INDEX `pay_protocol_idx01`(`walletid`, `acctid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '协议表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_sign_signinfo
-- ----------------------------
INSERT INTO `pay_sign_signinfo` VALUES ('20210305', '20210305', '20210', '123456', '1', '1', '1', '6214622121003305144', '付款人名称', '1', '1', '1', '1', '1', '1', '1', '1', NULL, NULL);

-- ----------------------------
-- Table structure for pay_sign_signinfo_jrn
-- ----------------------------
DROP TABLE IF EXISTS `pay_sign_signinfo_jrn`;
CREATE TABLE `pay_sign_signinfo_jrn`  (
  `paydate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `msgid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `instgpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `instdpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '接收机构',
  `direct` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '往来send:发送，recv：接收',
  `managetype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '管理类型MT01：身份认证,MT02：身份确认MT03：解约申请',
  `signtype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约类型SG00：不签约，SG01：签约',
  `signno` varchar(34) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '挂接协议号',
  `msgsendcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '动态关联码：msg+应答报文流水',
  `msgverifycode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '动态验证码sm4加密存储',
  `trxstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务处理状态：0-失败,1-成功,2-处理中',
  `trxretcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务处理码',
  `trxretmsg` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务处理信息',
  `rspmsgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应答报文标识号',
  `rspstatus` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应答回执状态',
  `rspcode` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应答业务处理码',
  `rspmsg` varchar(420) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应答业务处理信息',
  `acctptyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户所属机构',
  `accttype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户类型',
  `acctid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户账号',
  `acctname` varchar(960) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户户名',
  `idtype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件类型',
  `idno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件号码',
  `telephone` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '银行预留手机号码',
  `walletptyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '钱包开立所属机构编码',
  `walletid` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '钱包id',
  `wallettype` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '钱包类型',
  `walletlevel` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '钱包等级',
  `lastupjrnno` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新流水',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(560) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`paydate`, `payserno`) USING BTREE,
  INDEX `pay_protocol_jrn_idx01`(`walletid`, `msgsendcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_sign_signinfo_jrn
-- ----------------------------
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '09580210001', '095802', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '2', 'ECNY000002', 'PROCESSING', '20210114022378751937809029856100', 'PR02', 'PR02', '处理中', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '095803', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10002210002', '100022', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '2', 'ECNY000002', 'PROCESSING', '20210114022378751937809029856100', 'PR02', 'PR02', '处理中', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '100022', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10011710003', '100117', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '2', 'ECNY000002', 'PROCESSING', '20210114022378751937809029856100', 'PR02', 'PR02', '处理中', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '100117', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10335235001', '103352', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '103405', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10342635002', '103426', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '103627', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10365535003', '103655', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '103952', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10405135004', '104051', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '104154', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10423435005', '104234', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '104240', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10545635006', '105456', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '105532', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10563835007', '105638', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '105656', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10575035008', '105750', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '105919', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '10595135009', '105951', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '110001', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11033165001', '110331', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '110356', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11075270001', '110752', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '110834', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11111470002', '111114', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '111120', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11140075001', '111400', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '接收机构权限不足', '20210114022378751937809029856100', 'PR01', 'PR01', '接收机构权限不足', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '111611', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11171475002', '111714', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '111807', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11181075003', '111810', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '111930', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11193475004', '111934', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '111940', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11194875005', '111948', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '112121', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11213075006', '112130', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '112246', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11242175007', '112421', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '112529', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11255675008', '112556', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '112819', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '11294575009', '112945', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '113139', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '13350875010', '133508', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '133556', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '13364275011', '133642', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '133957', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '13524680001', '135246', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '135429', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '13544580002', '135445', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '135526', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14004275012', '140042', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '140100', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14021075013', '140210', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '其他错误', '20210114022378751937809029856100', 'PR01', 'PR01', '其他错误', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '140242', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14192400001', '141924', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '141942', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14194800002', '141948', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '142023', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14202500003', '142025', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '142452', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14262405001', '142624', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '142630', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14263205002', '142632', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '142707', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14272605003', '142726', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '142735', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14352415001', '143524', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '143602', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14361315002', '143613', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '143623', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14363115003', '143631', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '143829', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14383215004', '143832', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', NULL, NULL, '0', 'ECNYS09999', '客户状态异常', '20210114022378751937809029856100', 'PR01', 'PR01', '客户状态异常', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '143926', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14432525001', '144325', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', '1714434725002', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114022378751937809029856100', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '144357', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14462725003', '144627', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', '1714463225004', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114022378751937809029856100', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '144632', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('17', '14464825005', '144648', '20210114022378751937809029856100', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', 'X1062021011418550100000009', '1714464925006', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114022378751937809029856100', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214620921000000839', '测糠咨', 'IT01', '654202197601178868', '13800138000', 'C1010411000013', '23534765423543256556', 'WT02', 'WL02', NULL, '17', '144649', '备注');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `blob_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `int_prop_1` int(11) NULL DEFAULT NULL,
  `int_prop_2` int(11) NULL DEFAULT NULL,
  `long_prop_1` bigint(20) NULL DEFAULT NULL,
  `long_prop_2` bigint(20) NULL DEFAULT NULL,
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL,
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `next_fire_time` bigint(13) NULL DEFAULT NULL,
  `prev_fire_time` bigint(13) NULL DEFAULT NULL,
  `priority` int(11) NULL DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) NULL DEFAULT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `misfire_instr` smallint(2) NULL DEFAULT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for segment_allocator
-- ----------------------------
DROP TABLE IF EXISTS `segment_allocator`;
CREATE TABLE `segment_allocator`  (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务模块标识',
  `max_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '当前号段的最大值',
  `step` int(11) NOT NULL COMMENT '号段的步长',
  `max_total` bigint(20) NOT NULL DEFAULT 0 COMMENT '序列的上限，到达上限后会从init_id重新来一轮，循环往复，0表示没有上限',
  `init_id` bigint(20) NOT NULL COMMENT '序列的初始id，配合max_total使用，到达上限后的循环初始值',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`biz_tag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '流水号号段表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of segment_allocator
-- ----------------------------
INSERT INTO `segment_allocator` VALUES ('agreement-flowno', 1, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-03-05 16:35:26');
INSERT INTO `segment_allocator` VALUES ('corereq-flowno', 225001, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-03-31 10:17:20');
INSERT INTO `segment_allocator` VALUES ('flowno', 1, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-03-05 16:35:26');
INSERT INTO `segment_allocator` VALUES ('msg-flowno', 325001, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-03-29 11:19:26');
INSERT INTO `segment_allocator` VALUES ('platform-flowno', 35001, 5000, 99999, 1, '2021-03-05 16:35:26', '2021-03-31 10:17:18');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '深色主题theme-dark，浅色主题theme-light');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '神州银行', 0, 'DC', '15888888888', 'dcwlt@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-02-26 18:04:04');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '总行', 1, 'DC', '15888888888', 'dc@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-02-26 18:04:04');
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '科技部门', 1, '可可', '15888888888', 'dc@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-02-26 18:03:43');
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, 'WWI', '15888888888', 'ww@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-02-26 18:04:04');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (29, 1, '授权码模式', 'authorization_code', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '授权码模式');
INSERT INTO `sys_dict_data` VALUES (30, 2, '密码模式', 'password', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '密码模式');
INSERT INTO `sys_dict_data` VALUES (31, 3, '客户端模式', 'client_credentials', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '客户端模式');
INSERT INTO `sys_dict_data` VALUES (32, 4, '简化模式', 'implicit', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '简化模式');
INSERT INTO `sys_dict_data` VALUES (33, 5, '刷新模式', 'refresh_token', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '刷新模式');
INSERT INTO `sys_dict_data` VALUES (34, 0, '成功', '0', 'sys_excute_status', NULL, NULL, 'N', '0', 'admin', '2021-03-11 19:48:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (35, 0, '失败', '1', 'sys_excute_status', NULL, NULL, 'N', '0', 'admin', '2021-03-11 19:48:41', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (11, '授权类型', 'sys_grant_type', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '授权类型列表');
INSERT INTO `sys_dict_type` VALUES (12, '执行状态', 'sys_excute_status', '0', 'admin', '2021-03-11 19:47:47', '', NULL, '0成功 1失败');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务ID',
  `fid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父实例ID',
  `fjob_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父任务ID',
  `job_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '任务类型（0父任务 1子任务）',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `retry_cron` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '失败重试cron',
  `retry_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '失败重试状态（0正常 1暂停）',
  `fail_time` datetime(0) NULL DEFAULT NULL COMMENT '父任务失败时间',
  `retry_job_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重试是否成功（0成功 1失败）',
  `retry_num` int(4) NULL DEFAULT NULL COMMENT '当前重试次数',
  `retry_max_num` int(4) NULL DEFAULT 0 COMMENT '重试最大次数',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('14a3d5a8-e3fd-46e6-82ce-977005d60f12', NULL, NULL, '0', 'JSONArray参数调用示例', 'DEFAULT', 'ryTask.JSONArrayParams([{name: \"ruoyi\", age: 10}, {name: \"xiaoming\", age: 12}])', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '0', NULL, NULL, NULL, 5, 'admin', '2021-03-19 09:56:14', 'admin', '2021-03-19 16:04:58', '');
INSERT INTO `sys_job` VALUES ('2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '0', NULL, NULL, NULL, 5, 'admin', '2018-03-16 11:35:00', 'admin', '2021-03-19 09:59:57', '');
INSERT INTO `sys_job` VALUES ('26672c50-864c-11eb-a040-9c5c8e383269', NULL, NULL, '0', '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'yyyy-MM-dd\')', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '0', NULL, NULL, NULL, 5, 'admin', '2018-03-16 11:34:00', 'admin', '2021-03-19 09:59:53', '');
INSERT INTO `sys_job` VALUES ('2667a12e-864c-11eb-a040-9c5c8e383269', NULL, NULL, '0', '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'yyyy-MM-dd HH:mm:ss\', true, 2000L, 316.50D, 100)', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '0', NULL, NULL, NULL, 5, 'admin', '2018-03-16 11:33:00', 'admin', '2021-03-19 09:59:48', '');
INSERT INTO `sys_job` VALUES ('49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, '0', '支付批量服务报表', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '0 0 1 * * ?', '3', '1', '1', '0 0/10 * * * ?', '0', NULL, NULL, NULL, 5, 'admin', '2021-03-19 16:04:08', 'admin', '2021-03-19 20:12:46', '');
INSERT INTO `sys_job` VALUES ('82773183-6c20-49cd-802b-93b37560a1b6', NULL, NULL, '0', 'JSONObject参数调用示例', 'DEFAULT', 'ryTask.JSONObjectParams({name: \"ruoyi\", age: 10})', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '0', NULL, NULL, NULL, 5, 'admin', '2021-03-19 09:58:05', 'admin', '2021-03-19 16:04:43', '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务日志ID',
  `job_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务ID',
  `fid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父实例ID',
  `fjob_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父任务ID',
  `fail_time` datetime(0) NULL DEFAULT NULL COMMENT '父任务失败时间',
  `job_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '任务类型（0父任务 1子任务）',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0成功 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT '停止时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `excute_ret` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行返回值',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示信息',
  `access_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '169.254.204.165', '0', '退出成功', '2021-03-19 12:39:15');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '169.254.204.165', '0', '登录成功', '2021-03-19 12:39:22');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '169.254.204.165', '0', '登录成功', '2021-03-19 18:06:26');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2013 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 9, 'notice', 'system/notice/index', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 10, 'log', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '调度日志', 2, 2, 'jobLog', 'monitor/job/log', 1, 0, 'C', '0', '0', 'monitor:job:list', 'log', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '调度日志菜单');
INSERT INTO `sys_menu` VALUES (112, 'Sentinel控制台', 2, 3, 'http://localhost:8718', '', 1, 0, 'C', '0', '0', 'monitor:sentinel:list', 'sentinel', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '流量控制菜单');
INSERT INTO `sys_menu` VALUES (113, 'Nacos控制台', 2, 4, 'http://localhost:8848/nacos', '', 1, 0, 'C', '0', '0', 'monitor:nacos:list', 'nacos', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '服务治理菜单');
INSERT INTO `sys_menu` VALUES (114, 'Admin控制台', 2, 5, 'http://localhost:9100/login', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'http://localhost:8000/swagger-ui.html', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'system/operlog/index', 1, 0, 'C', '0', '0', 'system:operlog:list', 'form', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'system/logininfor/index', 1, 0, 'C', '0', '0', 'system:logininfor:list', 'logininfor', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1041, '公告查询', 107, 1, '#', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1042, '公告新增', 107, 2, '#', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1043, '公告修改', 107, 3, '#', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1044, '公告删除', 107, 4, '#', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1045, '操作查询', 500, 1, '#', '', 1, 0, 'F', '0', '0', 'system:operlog:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1046, '操作删除', 500, 2, '#', '', 1, 0, 'F', '0', '0', 'system:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1047, '日志导出', 500, 4, '#', '', 1, 0, 'F', '0', '0', 'system:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1048, '登录查询', 501, 1, '#', '', 1, 0, 'F', '0', '0', 'system:logininfor:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1049, '登录删除', 501, 2, '#', '', 1, 0, 'F', '0', '0', 'system:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1050, '日志导出', 501, 3, '#', '', 1, 0, 'F', '0', '0', 'system:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1051, '在线查询', 109, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1052, '批量强退', 109, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1053, '单条强退', 109, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1054, '任务查询', 110, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1055, '任务新增', 110, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1056, '任务修改', 110, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1057, '任务删除', 110, 4, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1058, '状态修改', 110, 5, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1059, '任务导出', 110, 7, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1060, '生成查询', 115, 1, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1061, '生成修改', 115, 2, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1062, '生成删除', 115, 3, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1063, '导入代码', 115, 2, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1064, '预览代码', 115, 4, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1065, '生成代码', 115, 5, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (2006, '机构管理', 2012, 1, 'party', 'system/party/index', 1, 0, 'C', '0', '0', 'system:party:list', 'party', 'admin', '2018-03-01 00:00:00', 'admin', '2021-02-26 17:55:04', '机构菜单');
INSERT INTO `sys_menu` VALUES (2007, '机构查询', 2006, 1, '#', '', 1, 0, 'F', '0', '0', 'system:party:query', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2008, '机构新增', 2006, 2, '#', '', 1, 0, 'F', '0', '0', 'system:party:add', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2009, '机构修改', 2006, 3, '#', '', 1, 0, 'F', '0', '0', 'system:party:edit', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2010, '机构删除', 2006, 4, '#', '', 1, 0, 'F', '0', '0', 'system:party:remove', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2011, '机构导出', 2006, 5, '#', '', 1, 0, 'F', '0', '0', 'system:party:export', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2012, 'DCEP支付', 0, 0, 'pay', NULL, 1, 0, 'M', '0', '0', '', 'pay', 'admin', '2021-02-26 17:25:41', 'admin', '2021-02-26 17:54:53', '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '公告', '2', 0xE585ACE5918A, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '管理员');
INSERT INTO `sys_notice` VALUES (2, '通知', '1', 0xE9809AE79FA5, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 218 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 12:40:13');
INSERT INTO `sys_oper_log` VALUES (101, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 12:40:15');
INSERT INTO `sys_oper_log` VALUES (102, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 12:43:45');
INSERT INTO `sys_oper_log` VALUES (103, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 12:43:51');
INSERT INTO `sys_oper_log` VALUES (104, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"Connection refused: connect executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19\",\"success\":false}', 0, NULL, '2021-03-19 13:01:39');
INSERT INTO `sys_oper_log` VALUES (105, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"com.netflix.client.ClientException: Load balancer does not have available server for client: dcwlt-pay-batch\",\"success\":false}', 0, NULL, '2021-03-19 13:01:56');
INSERT INTO `sys_oper_log` VALUES (106, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:02:25');
INSERT INTO `sys_oper_log` VALUES (107, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:15:46');
INSERT INTO `sys_oper_log` VALUES (108, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"com.netflix.client.ClientException: Load balancer does not have available server for client: dcwlt-pay-batch\",\"success\":false}', 0, NULL, '2021-03-19 13:19:48');
INSERT INTO `sys_oper_log` VALUES (109, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"com.netflix.client.ClientException: Load balancer does not have available server for client: dcwlt-pay-batch\",\"success\":false}', 0, NULL, '2021-03-19 13:19:58');
INSERT INTO `sys_oper_log` VALUES (110, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:20:30');
INSERT INTO `sys_oper_log` VALUES (111, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:32:47');
INSERT INTO `sys_oper_log` VALUES (112, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"Read timed out executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19\",\"success\":false}', 0, NULL, '2021-03-19 13:33:31');
INSERT INTO `sys_oper_log` VALUES (113, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:36:37');
INSERT INTO `sys_oper_log` VALUES (114, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:36:45');
INSERT INTO `sys_oper_log` VALUES (115, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"Read timed out executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19\",\"success\":false}', 0, NULL, '2021-03-19 13:37:37');
INSERT INTO `sys_oper_log` VALUES (116, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:47:06');
INSERT INTO `sys_oper_log` VALUES (117, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:50:28');
INSERT INTO `sys_oper_log` VALUES (118, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:52:21');
INSERT INTO `sys_oper_log` VALUES (119, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:54:07');
INSERT INTO `sys_oper_log` VALUES (120, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 13:55:12');
INSERT INTO `sys_oper_log` VALUES (121, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"Read timed out executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19\",\"success\":false}', 0, NULL, '2021-03-19 13:55:27');
INSERT INTO `sys_oper_log` VALUES (122, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: Read timed out executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19', '2021-03-19 14:20:07');
INSERT INTO `sys_oper_log` VALUES (123, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 14:20:50');
INSERT INTO `sys_oper_log` VALUES (124, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 14:22:35');
INSERT INTO `sys_oper_log` VALUES (125, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: Read timed out executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19%2014%3A24%3A19', '2021-03-19 14:25:44');
INSERT INTO `sys_oper_log` VALUES (126, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: com.netflix.client.ClientException: Load balancer does not have available server for client: dcwlt-pay-batch', '2021-03-19 14:30:13');
INSERT INTO `sys_oper_log` VALUES (127, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:36:46\')\",\"success\":true}', 0, NULL, '2021-03-19 14:36:49');
INSERT INTO `sys_oper_log` VALUES (128, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:37:41\')\",\"success\":true}', 0, NULL, '2021-03-19 14:37:41');
INSERT INTO `sys_oper_log` VALUES (129, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:38:24\')\",\"success\":true}', 0, NULL, '2021-03-19 14:38:24');
INSERT INTO `sys_oper_log` VALUES (130, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:45:32\')\",\"success\":true}', 0, NULL, '2021-03-19 14:45:33');
INSERT INTO `sys_oper_log` VALUES (131, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:46:31\')\",\"success\":true}', 0, NULL, '2021-03-19 14:46:52');
INSERT INTO `sys_oper_log` VALUES (132, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:47:18\')\",\"success\":true}', 0, NULL, '2021-03-19 14:47:18');
INSERT INTO `sys_oper_log` VALUES (133, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:48:14\')\",\"success\":true}', 0, NULL, '2021-03-19 14:48:14');
INSERT INTO `sys_oper_log` VALUES (134, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:48:20\')\",\"success\":true}', 0, NULL, '2021-03-19 14:48:20');
INSERT INTO `sys_oper_log` VALUES (135, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19 14:48:22\')\",\"success\":true}', 0, NULL, '2021-03-19 14:48:22');
INSERT INTO `sys_oper_log` VALUES (136, '定时任务', 1, 'com.dcits.dcwlt.job.controller.SysJobController.add()', 'POST', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"nextValidTime\":1616173200000,\"retryMaxNum\":5,\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')\",\"jobType\":\"0\",\"jobName\":\"支付批量服务报表\",\"retryCron\":\"0/2 * * * * ? *\",\"retryStatus\":\"0\",\"params\":{},\"cronExpression\":\"0 0 1 * * ?\",\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"createBy\":\"admin\",\"misfirePolicy\":\"1\",\"nextRetryValidTime\":1616141050000,\"status\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 16:04:10');
INSERT INTO `sys_oper_log` VALUES (137, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616141100000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"ryTask.JSONObjectParams({name: \\\"ruoyi\\\", age: 10})\",\"jobType\":\"0\",\"jobName\":\"JSONObject参数调用示例\",\"retryCron\":\"0/2 * * * * ?\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0/30 * * * * ?\",\"jobId\":\"82773183-6c20-49cd-802b-93b37560a1b6\",\"createBy\":\"admin\",\"createTime\":1616119085000,\"misfirePolicy\":\"1\",\"nextRetryValidTime\":1616141084000,\"status\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 16:04:44');
INSERT INTO `sys_oper_log` VALUES (138, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616141100000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"ryTask.JSONArrayParams([{name: \\\"ruoyi\\\", age: 10}, {name: \\\"xiaoming\\\", age: 12}])\",\"jobType\":\"0\",\"jobName\":\"JSONArray参数调用示例\",\"retryCron\":\"0/2 * * * * ?\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0/30 * * * * ?\",\"jobId\":\"14a3d5a8-e3fd-46e6-82ce-977005d60f12\",\"createBy\":\"admin\",\"createTime\":1616118974000,\"misfirePolicy\":\"1\",\"nextRetryValidTime\":1616141100000,\"status\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 16:04:59');
INSERT INTO `sys_oper_log` VALUES (139, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:07:26.421+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:07:47');
INSERT INTO `sys_oper_log` VALUES (140, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:08:08.768+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:08:09');
INSERT INTO `sys_oper_log` VALUES (141, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:08:17.385+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:08:17');
INSERT INTO `sys_oper_log` VALUES (142, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:09:05.370+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:09:05');
INSERT INTO `sys_oper_log` VALUES (143, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:10:10.497+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:10:11');
INSERT INTO `sys_oper_log` VALUES (144, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:10:35');
INSERT INTO `sys_oper_log` VALUES (145, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:11:09.154+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:11:09');
INSERT INTO `sys_oper_log` VALUES (146, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:11:36.318+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:12:28');
INSERT INTO `sys_oper_log` VALUES (147, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:13:06.284+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:13:55');
INSERT INTO `sys_oper_log` VALUES (148, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: Read timed out executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1', '2021-03-19 16:14:50');
INSERT INTO `sys_oper_log` VALUES (149, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:18:13.663+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:19:51');
INSERT INTO `sys_oper_log` VALUES (150, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, 'null', 1, 'syntax error, pos 1, line 1, column 2java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\"timestamp\":\"2021-03-19T16:22:05.834+08:00\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"\",\"path\":\"/reportdata/statistics\"}]', '2021-03-19 16:22:16');
INSERT INTO `sys_oper_log` VALUES (151, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:28:31');
INSERT INTO `sys_oper_log` VALUES (152, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:28:38');
INSERT INTO `sys_oper_log` VALUES (153, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:28:57');
INSERT INTO `sys_oper_log` VALUES (154, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:31:16');
INSERT INTO `sys_oper_log` VALUES (155, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:31:57');
INSERT INTO `sys_oper_log` VALUES (156, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:33:28');
INSERT INTO `sys_oper_log` VALUES (157, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:33:35');
INSERT INTO `sys_oper_log` VALUES (158, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:36:28');
INSERT INTO `sys_oper_log` VALUES (159, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:36:28');
INSERT INTO `sys_oper_log` VALUES (160, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:36:28');
INSERT INTO `sys_oper_log` VALUES (161, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:36:29');
INSERT INTO `sys_oper_log` VALUES (162, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:36:30');
INSERT INTO `sys_oper_log` VALUES (163, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:36:34');
INSERT INTO `sys_oper_log` VALUES (164, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:38');
INSERT INTO `sys_oper_log` VALUES (165, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:45');
INSERT INTO `sys_oper_log` VALUES (166, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:47');
INSERT INTO `sys_oper_log` VALUES (167, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:47');
INSERT INTO `sys_oper_log` VALUES (168, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:49');
INSERT INTO `sys_oper_log` VALUES (169, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:55');
INSERT INTO `sys_oper_log` VALUES (170, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:56');
INSERT INTO `sys_oper_log` VALUES (171, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:57');
INSERT INTO `sys_oper_log` VALUES (172, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:58');
INSERT INTO `sys_oper_log` VALUES (173, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:38:59');
INSERT INTO `sys_oper_log` VALUES (174, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"success\":true}', 0, NULL, '2021-03-19 16:49:32');
INSERT INTO `sys_oper_log` VALUES (175, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:07:29.490+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:07:31');
INSERT INTO `sys_oper_log` VALUES (176, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:07:48.545+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:07:49');
INSERT INTO `sys_oper_log` VALUES (177, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:07:50.301+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:07:51');
INSERT INTO `sys_oper_log` VALUES (178, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:08:02.385+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:08:02');
INSERT INTO `sys_oper_log` VALUES (179, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-2\')\",\"success\":true}', 0, NULL, '2021-03-19 17:08:10');
INSERT INTO `sys_oper_log` VALUES (180, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-2\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-2] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:08:12.292+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:08:12');
INSERT INTO `sys_oper_log` VALUES (181, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-2\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-2] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:14.353+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:15');
INSERT INTO `sys_oper_log` VALUES (182, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:30.236+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:30');
INSERT INTO `sys_oper_log` VALUES (183, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:34.863+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:35');
INSERT INTO `sys_oper_log` VALUES (184, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:48.305+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:48');
INSERT INTO `sys_oper_log` VALUES (185, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:50.840+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:51');
INSERT INTO `sys_oper_log` VALUES (186, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:52.886+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:53');
INSERT INTO `sys_oper_log` VALUES (187, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:55.060+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:55');
INSERT INTO `sys_oper_log` VALUES (188, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:57.282+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:57');
INSERT INTO `sys_oper_log` VALUES (189, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:58.607+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:59');
INSERT INTO `sys_oper_log` VALUES (190, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T17:15:59.077+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 17:15:59');
INSERT INTO `sys_oper_log` VALUES (191, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: Connection refused: connect executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1\",\"success\":false}', 0, NULL, '2021-03-19 17:16:28');
INSERT INTO `sys_oper_log` VALUES (192, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: Connection refused: connect executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1\",\"success\":false}', 0, NULL, '2021-03-19 17:16:34');
INSERT INTO `sys_oper_log` VALUES (193, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: Connection refused: connect executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1\",\"success\":false}', 0, NULL, '2021-03-19 17:16:37');
INSERT INTO `sys_oper_log` VALUES (194, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2018-10-1\')\",\"message\":\"java.lang.Exception: Connection refused: connect executing POST http://dcwlt-pay-batch/reportdata/statistics?reportDate=2018-10-1\",\"success\":false}', 0, NULL, '2021-03-19 17:16:39');
INSERT INTO `sys_oper_log` VALUES (195, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 18:07:12');
INSERT INTO `sys_oper_log` VALUES (196, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 18:07:27');
INSERT INTO `sys_oper_log` VALUES (197, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 18:07:32');
INSERT INTO `sys_oper_log` VALUES (198, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 18:07:41');
INSERT INTO `sys_oper_log` VALUES (199, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 18:07:45');
INSERT INTO `sys_oper_log` VALUES (200, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"success\":true}', 0, NULL, '2021-03-19 18:08:00');
INSERT INTO `sys_oper_log` VALUES (201, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T18:20:24.785+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 18:20:25');
INSERT INTO `sys_oper_log` VALUES (202, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2015-1-1\')\",\"success\":true}', 0, NULL, '2021-03-19 18:21:23');
INSERT INTO `sys_oper_log` VALUES (203, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2015-1-1\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2015-1-1] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T18:21:26.483+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 18:21:26');
INSERT INTO `sys_oper_log` VALUES (204, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616149610000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"dcwltPayBatchTask.statistics(${yyyy-MM-dd})\",\"jobType\":\"0\",\"jobName\":\"支付批量服务报表\",\"retryCron\":\"0/2 * * * * ? *\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0/10 * * * * ? *\",\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"createBy\":\"admin\",\"createTime\":1616141048000,\"misfirePolicy\":\"1\",\"nextRetryValidTime\":1616149608000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 18:26:48');
INSERT INTO `sys_oper_log` VALUES (205, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.changeStatus()', 'PUT', 1, 'admin', NULL, '/job/changeStatus', '127.0.0.1', '', '{\"params\":{},\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"misfirePolicy\":\"0\",\"status\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 18:30:23');
INSERT INTO `sys_oper_log` VALUES (206, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616173200000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"dcwltPayBatchTask.statistics(${yyyy-MM-dd})\",\"jobType\":\"0\",\"jobName\":\"支付批量服务报表\",\"retryCron\":\"0/2 * * * * ? *\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0 0 1 * * ?\",\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"createBy\":\"admin\",\"createTime\":1616141048000,\"misfirePolicy\":\"1\",\"nextRetryValidTime\":1616149864000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 18:31:03');
INSERT INTO `sys_oper_log` VALUES (207, '失败重试任务调度日志', 9, 'com.dcits.dcwlt.job.controller.SysJobLogController.cleanRetry()', 'DELETE', 1, 'admin', NULL, '/job/log/cleanRetry', '127.0.0.1', '', NULL, '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 18:32:58');
INSERT INTO `sys_oper_log` VALUES (208, '失败重试任务调度日志', 9, 'com.dcits.dcwlt.job.controller.SysJobLogController.cleanRetry()', 'DELETE', 1, 'admin', NULL, '/job/log/cleanRetry', '127.0.0.1', '', NULL, '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 18:36:59');
INSERT INTO `sys_oper_log` VALUES (209, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616151000000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"dcwltPayBatchTask.statistics(${yyyy-MM-dd})\",\"jobType\":\"0\",\"jobName\":\"支付批量服务报表\",\"retryCron\":\"0/2 * * * * ? *\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0/30 * * * * ?\",\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"createBy\":\"admin\",\"createTime\":1616141048000,\"misfirePolicy\":\"1\",\"nextRetryValidTime\":1616150994000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 18:49:53');
INSERT INTO `sys_oper_log` VALUES (210, '失败重试任务调度日志', 9, 'com.dcits.dcwlt.job.controller.SysJobLogController.cleanRetry()', 'DELETE', 1, 'admin', NULL, '/job/log/cleanRetry', '127.0.0.1', '', NULL, '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 18:50:46');
INSERT INTO `sys_oper_log` VALUES (211, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.changeStatus()', 'PUT', 1, 'admin', NULL, '/job/changeStatus', '127.0.0.1', '', '{\"params\":{},\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"misfirePolicy\":\"0\",\"status\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 19:50:33');
INSERT INTO `sys_oper_log` VALUES (212, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.changeStatus()', 'PUT', 1, 'admin', NULL, '/job/changeStatus', '127.0.0.1', '', '{\"params\":{},\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"misfirePolicy\":\"0\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 19:59:31');
INSERT INTO `sys_oper_log` VALUES (213, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616173200000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"dcwltPayBatchTask.statistics(${yyyy-MM-dd})\",\"jobType\":\"0\",\"jobName\":\"支付批量服务报表\",\"retryCron\":\"0/2 * * * * ? *\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0 0 1 * * ?\",\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"createBy\":\"admin\",\"createTime\":1616141048000,\"misfirePolicy\":\"3\",\"nextRetryValidTime\":1616155310000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 20:01:49');
INSERT INTO `sys_oper_log` VALUES (214, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T20:08:05.774+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 20:08:06');
INSERT INTO `sys_oper_log` VALUES (215, '手动执行方法', 0, 'com.dcits.dcwlt.job.controller.SysJobController.manualRun()', 'GET', 1, 'admin', NULL, '/job/manualRun', '127.0.0.1', '', NULL, '{\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'2021-03-19\')\",\"message\":\"java.lang.Exception: [500] during [POST] to [http://dcwlt-pay-batch/reportdata/statistics?reportDate=2021-03-19] [RemotePayBatchService#statistics(String)]: [{\\\"timestamp\\\":\\\"2021-03-19T20:08:18.355+08:00\\\",\\\"status\\\":500,\\\"error\\\":\\\"Internal Server Error\\\",\\\"message\\\":\\\"\\\",\\\"path\\\":\\\"/reportdata/statistics\\\"}]\",\"success\":false}', 0, NULL, '2021-03-19 20:08:18');
INSERT INTO `sys_oper_log` VALUES (216, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616173200000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')\",\"jobType\":\"0\",\"jobName\":\"支付批量服务报表\",\"retryCron\":\"0/2 * * * * ? *\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0 0 1 * * ?\",\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"createBy\":\"admin\",\"createTime\":1616141048000,\"misfirePolicy\":\"3\",\"nextRetryValidTime\":1616155842000,\"status\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 20:10:41');
INSERT INTO `sys_oper_log` VALUES (217, '定时任务', 2, 'com.dcits.dcwlt.job.controller.SysJobController.edit()', 'PUT', 1, 'admin', NULL, '/job', '127.0.0.1', '', '{\"concurrent\":\"1\",\"remark\":\"\",\"nextValidTime\":1616173200000,\"retryMaxNum\":5,\"updateBy\":\"admin\",\"invokeTarget\":\"dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')\",\"jobType\":\"0\",\"jobName\":\"支付批量服务报表\",\"retryCron\":\"0 0/10 * * * ?\",\"retryStatus\":\"0\",\"jobGroup\":\"DEFAULT\",\"params\":{},\"cronExpression\":\"0 0 1 * * ?\",\"jobId\":\"49ef9c73-b619-471d-80e4-826a26904da6\",\"createBy\":\"admin\",\"createTime\":1616141048000,\"misfirePolicy\":\"3\",\"nextRetryValidTime\":1616156400000,\"status\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2021-03-19 20:12:48');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);
INSERT INTO `sys_role_menu` VALUES (2, 1061);
INSERT INTO `sys_role_menu` VALUES (2, 1062);
INSERT INTO `sys_role_menu` VALUES (2, 1063);
INSERT INTO `sys_role_menu` VALUES (2, 1064);
INSERT INTO `sys_role_menu` VALUES (2, 1065);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '管理员', '00', 'dcwlt@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '管理员');
INSERT INTO `sys_user` VALUES (2, 107, 'hugh', '管理员', '00', 'dcwlt@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for wlt_trans_log
-- ----------------------------
DROP TABLE IF EXISTS `wlt_trans_log`;
CREATE TABLE `wlt_trans_log`  (
  `txn_dt` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易日期',
  `txn_tm` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易时间',
  `request_serial_no` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '前端流水号',
  `serial_no` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易流水号',
  `system_code` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '系统来源',
  `channel_no` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '渠道编号',
  `trxn_code` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易码',
  `trxn_sts` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '交易状态',
  `request_time` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求时间',
  `trans_time` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '响应时间',
  `return_code` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '返回码值',
  `return_message` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '返回消息',
  `last_up_date` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `last_up_time` varchar(480) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`txn_dt`, `serial_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '交易流水表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
