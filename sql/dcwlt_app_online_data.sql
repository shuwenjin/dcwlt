/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.23.171-dcwlt
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 10.0.23.171:3306
 Source Schema         : dcwlt_app_online

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 27/05/2021 17:55:48
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
INSERT INTO `pay_batch_checkcollect` VALUES ('20210413', '15422340001', '154223', NULL, 'dcep.221.001.01', '20210113000122184595346246598765', 'B202103091600', 'PAYEE', 'C1010411000013', 'C1010411000013', '101112345678916', NULL, 'C1030644021075', NULL, '6214622121002964305', NULL, 'CNY', '1100', NULL, NULL, '2', '2', '7', '20210414', '154231');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210414', '14031280007', '140312', NULL, 'dcep.801.001.01', '20210414106180114031200375024000', 'B202103091600', 'PAYER', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, '0', '9', '0', '20210414', '140312');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210414', '14295005001', '142950', NULL, 'dcep.225.001.01', '20210113000122532910308590900000', 'B202103091600', 'PAYER', 'C1010411000013', 'C1030644021075', NULL, '6214622121003305144', 'C1010411000013', '收款人钱包名称', NULL, '23534645224653442', 'CNY', '12311', NULL, NULL, '2', '2', '7', '20210414', '143000');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210414', '14445005004', '144451', NULL, 'dcep.225.001.01', '20210414000122532910308590900000', 'B202103091600', 'PAYER', 'C1010411000013', 'C1030644021075', NULL, '6214622121003305144', 'C1010411000013', '收款人钱包名称', NULL, '23534645224653442', 'CNY', '12311', NULL, NULL, '2', '2', '7', '20210414', '144519');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210414', '15025725001', '150257', '20210414', 'dcep.225.001.01', '20210401000122532910308590900000', 'B202103091600', 'PAYER', 'C1010411000013', 'C1030644021075', NULL, '6214622121003305144', 'C1010411000013', '收款人钱包名称', NULL, '23534645224653442', 'CNY', '12311', NULL, NULL, '1', '1', '1', '20210414', '150443');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210420', '15260170001', '152602', NULL, 'dcep.801.001.01', '20210420106180115260200560001000', 'B202104201600', 'PAYER', 'C1030644021075', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '123145', 'dcep.221.001.01', '20210315000180137727331885800000', '1', '9', '1', '20210420', '152635');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210420', '15341975001', '153419', NULL, 'dcep.801.001.01', '20210420106180115341900565001000', 'B202104201600', 'PAYER', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, '0', '9', '0', '20210420', '153419');
INSERT INTO `pay_batch_checkcollect` VALUES ('20210420', '15494080001', '154940', NULL, 'dcep.801.001.01', '20210420106180115494100570001000', 'B202104201600', 'PAYER', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, '0', '9', '0', '20210420', '154945');

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
-- Records of pay_batch_checkfilelist
-- ----------------------------
INSERT INTO `pay_batch_checkfilelist` VALUES ('20210308000171181982980514700000', 'B202103081600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', '/home/sftp/dcrecon/20200525/C1010511003703/trade/00/B202005251400_00_01.zip', '/home/sftp/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251400_00_01.zip', 'SUCC', '20210420', '180823');
INSERT INTO `pay_batch_checkfilelist` VALUES ('20210309000171181982980514700000', 'B202103091600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', 'D:\\sftp/dcrecon/20200525/C1010511003703/trade/00/B202005251500_00_01.zip', 'D:\\sftp/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251500_00_01.zip', 'SUCC', '20210420', '182849');

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
INSERT INTO `pay_batch_checkpath` VALUES ('20210420', '18114215001', '181137', '20210308000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '机构对账汇总核对报文', NULL, '20210308', 'B202103081600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', 'INIT', '20210420', '181142');
INSERT INTO `pay_batch_checkpath` VALUES ('20210420', '18280810002', '182733', '20210309000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '机构对账汇总核对报文', NULL, '20210309', 'B202103091600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', 'SAME', '20210420', '190351');
INSERT INTO `pay_batch_checkpath` VALUES ('20210420', '19023610003', '190215', '20210309000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '机构对账汇总核对报文', NULL, '20210309', 'B202103091600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', 'SAME', '20210420', '190356');

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
-- Records of pay_batch_checkpathdtl
-- ----------------------------
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210308', '20210308000171181982980514700000', 'B202103081600', 'B202005251400_00_01.zip.sec', '20210308', 'dcep.711.001.01', 'dcep.227.001.01', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '8396', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'LESS', '20210420', '181743');
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210414', '20210308000171181982980514700000', 'B202103081600', 'B202005251400_00_01.zip.sec', '20210308', 'dcep.221.001.01', 'dcep.227.001.02', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '1403', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'LESS', '20210420', '181743');
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210308', '20210308000171181982980514700000', 'B202103081600', 'B202005251400_00_01.zip.sec', '20210308', 'dcep.711.001.01', 'dcep.227.001.03', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '505700', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'SAME', '20210420', '181743');
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210308', '20210308000171181982980514700000', 'B202103081600', 'B202005251400_00_01.zip.sec', '20210308', 'dcep.711.001.01', 'dcep.227.001.04', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '505738', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'SAME', '20210420', '181743');
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210309', '20210309000171181982980514700000', 'B202103091600', 'B202005251500_00_01.zip.sec', '20210309', 'dcep.711.001.01', 'dcep.227.001.01', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '8396', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'SAME', '20210420', '182902');
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210309', '20210309000171181982980514700000', 'B202103091600', 'B202005251500_00_01.zip.sec', '20210309', 'dcep.711.001.01', 'dcep.227.001.02', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '2000', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'SAME', '20210420', '182902');
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210309', '20210309000171181982980514700000', 'B202103091600', 'B202005251500_00_01.zip.sec', '20210309', 'dcep.711.001.01', 'dcep.227.001.03', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '505700', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'SAME', '20210420', '182902');
INSERT INTO `pay_batch_checkpathdtl` VALUES ('20210309', '20210309000171181982980514700000', 'B202103091600', 'B202005251500_00_01.zip.sec', '20210309', 'dcep.711.001.01', 'dcep.227.001.04', 'G4001011000013', 'C1030644021075', 'G4001011000013', 'CNY', '505738', 'SUCC', 'TEST', NULL, NULL, NULL, NULL, NULL, NULL, 'SAME', '20210420', '182902');

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
  `report_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报表日期',
  `cashout_amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑出金额',
  `cashout_success_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑出成功笔数',
  `cashout_total_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑出总笔数',
  `cashin_amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑回金额',
  `cashin_success_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑回成功笔数',
  `cashin_total_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '兑回总笔数',
  `remitout_amount` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '汇款兑出金额',
  `remitout_success_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '汇款兑出成功笔数',
  `remitout_total_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '汇款兑出总笔数',
  PRIMARY KEY (`report_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '金融交易统计报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_batch_finance_report
-- ----------------------------
INSERT INTO `pay_batch_finance_report` VALUES ('2015-1-1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2018-10-1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2018-10-2', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-03-19', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-13', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-20', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-21', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-22', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-23', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-24', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-25', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-26', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-27', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-28', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-29', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-04-30', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-01', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-02', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-03', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-04', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-05', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-06', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-07', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-08', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-24', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-25', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-26', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `pay_batch_finance_report` VALUES ('2021-05-27', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for pay_batch_nonfinance_report
-- ----------------------------
DROP TABLE IF EXISTS `pay_batch_nonfinance_report`;
CREATE TABLE `pay_batch_nonfinance_report`  (
  `report_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '报表日期',
  `tied_card_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '绑卡数量',
  `untie_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '解绑数量',
  PRIMARY KEY (`report_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '非金融交易统计报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_batch_nonfinance_report
-- ----------------------------
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2015-1-1', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2018-10-1', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2018-10-2', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-03-19', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-13', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-20', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-21', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-22', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-23', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-24', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-25', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-26', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-27', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-28', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-29', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-04-30', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-01', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-02', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-03', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-04', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-05', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-06', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-07', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-08', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-24', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-25', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-26', '0', '0');
INSERT INTO `pay_batch_nonfinance_report` VALUES ('2021-05-27', '0', '0');

-- ----------------------------
-- Table structure for pay_cert_info
-- ----------------------------
DROP TABLE IF EXISTS `pay_cert_info`;
CREATE TABLE `pay_cert_info`  (
  `certid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `certtype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `certinfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `publickey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `lastUpDate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `lastUpTime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`certid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_cashbox_banlance
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_cashbox_banlance`;
CREATE TABLE `pay_comm_cashbox_banlance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chckngDt` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '对账日期',
  `coopBankInstnId` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '合作银行机构编码',
  `coopBankWltId` varchar(34) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '合作银行钱柜ID',
  `cshBoxInstnId` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '钱柜所属运营机构',
  `initlAmtCcy` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '期初余额货币符号',
  `initlAmtValue` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '期初余额',
  `cdtDbtInd` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '借贷标识',
  `dbtCntAmtCcy` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '借方金额货币符号',
  `dbtCntAmtValue` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '借方金额',
  `cdtCntAmtCcy` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '贷方金额货币符号',
  `cdtCntAmtValue` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '贷方金额',
  `fnlAmtCcy` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '期末余额货币符号',
  `fnlAmtValue` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '期末余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '钱柜余额对账通知表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_comm_cashbox_party
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_cashbox_party`;
CREATE TABLE `pay_comm_cashbox_party`  (
  `id` int(14) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `partyname` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `partyid` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构编码',
  `cashboxid` varchar(34) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '钱柜钱包id',
  `earlywarningamount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预警金额',
  `automaticstorage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自动入库金额',
  `automaticstuts` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否自动入库，0为否，1为是',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新时间',
  `cretername` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `cretertime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updatename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cshbox_pry_partyid`(`partyid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '运营机构钱柜参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_cashbox_party
-- ----------------------------
INSERT INTO `pay_comm_cashbox_party` VALUES (4, '中国建设银行', 'C2213141256762', '1221111122123456', '0', '100000', '1', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for pay_comm_cashbox_process
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_cashbox_process`;
CREATE TABLE `pay_comm_cashbox_process`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `oprTp` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '入库出库类型OT00，OT01',
  `cdtDbtInd` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '入库出库借贷标识',
  `amtCcy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '出入库金额货币符号',
  `amtValue` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '出入库金额',
  `coopBankInstnId` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '合作银行机构编码',
  `coopBankWltId` varchar(34) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '合作银行钱柜ID',
  `cshBoxInstnId` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '钱柜所属运营机构',
  `cert` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '额度凭证',
  `msgId` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '报文标识号',
  `creDtTmS` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务处理时间',
  `creDtTmR` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务响应时间',
  `prcSts` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务状态',
  `prcCd` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务回执状态',
  `rspsnSts` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务回执状态',
  `rjctCd` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务拒绝',
  `rjctInf` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务拒绝信息',
  `clrReptFlg` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算报文标识号',
  `clearAmountCcy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算金额货币符号',
  `clearAmountValue` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '清算金额',
  `coreSts` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心处理状态',
  `coreBatchId` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心批次号',
  `corepaySerno` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心流水号',
  `corePaydate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '核心日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '钱柜入库出库流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_cashbox_process
-- ----------------------------
INSERT INTO `pay_comm_cashbox_process` VALUES (1, 'OT01', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', NULL, '1', '3', '1', '1', '1');
INSERT INTO `pay_comm_cashbox_process` VALUES (2, 'OT00', 'DBIT', '156', '1111', '', '', 'C2213141256762', NULL, '20210527106112115080000770003000', '2021-05-27T15:08:00', NULL, 'PR02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'B202105271600', '15080085004', '20210527');
INSERT INTO `pay_comm_cashbox_process` VALUES (3, 'OT00', 'DBIT', '156', '2222', '', '', 'C2213141256762', NULL, '20210527106112115205700770005000', '2021-05-27T15:20:57', NULL, 'PR02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'B202105271600', '15205785006', '20210527');

-- ----------------------------
-- Table structure for pay_comm_certs
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_certs`;
CREATE TABLE `pay_comm_certs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `party_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构编码',
  `cert_type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书类型',
  `cert_status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书状态',
  `cert_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书编号',
  `public_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公钥',
  `private_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '私钥',
  `effect_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生效时间',
  `expired_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失效时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '证书管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_certs
-- ----------------------------
INSERT INTO `pay_comm_certs` VALUES (1, 'C1010411000013', 'CS00', 'CC00', '2345123', 'MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAETcVczGjTB4p7kerqtSDMcc2CfVI1j1Tr2tl9VV5irEKUnSq1QMRKsx1tbzMjgkZSTt/4wUNVzgGnk+D8GkHEGQ==', 'MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg5QXLcbQxbgpKAQgyBn+Lk0zZzmHPw4ZHo3UZDoFZcpegCgYIKoEcz1UBgi2hRANCAARNxVzMaNMHinuR6uq1IMxxzYJ9UjWPVOva2X1VXmKsQpSdKrVAxEqzHW1vMyOCRlJO3/jBQ1XOAaeT4PwaQcQZ', '2021-01-01', '2023-01-01', '2021-04-25 23:12:27', '2021-04-25 23:14:52', NULL);
INSERT INTO `pay_comm_certs` VALUES (2, 'C1030644021075', 'CS00', 'CC00', '3956257', 'MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAETcVczGjTB4p7kerqtSDMcc2CfVI1j1Tr2tl9VV5irEKUnSq1QMRKsx1tbzMjgkZSTt/4wUNVzgGnk+D8GkHEGQ==', 'MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg5QXLcbQxbgpKAQgyBn+Lk0zZzmHPw4ZHo3UZDoFZcpegCgYIKoEcz1UBgi2hRANCAARNxVzMaNMHinuR6uq1IMxxzYJ9UjWPVOva2X1VXmKsQpSdKrVAxEqzHW1vMyOCRlJO3/jBQ1XOAaeT4PwaQcQZ', '2021-01-01', '2023-01-01', '2021-04-25 23:12:27', '2021-04-25 23:14:52', NULL);

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
  PRIMARY KEY (`excepteventcode`, `excepteventseqno`) USING BTREE,
  UNIQUE INDEX `idx_pay_comm_excepteventinfo`(`excepteventcode`, `excepteventseqno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '异常事件流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_excepteventinfo
-- ----------------------------
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618913303170', '180823', 'EVENTNCHECKDETIAL', 'ECNY2021042018081800004500040004', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210308\",\"BatchId\":\"B202103081600\"}', '20210420', '180823');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618913502756', '181142', 'EVENTNCHECKDETIAL', 'ECNY2021042018113700004500060006', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210308\",\"BatchId\":\"B202103081600\"}', '20210420', '181142');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618913862947', '181742', 'EVENTNCHECKDETIAL', 'ECNY2021042018173700004650020002', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210308\",\"BatchId\":\"B202103081600\"}', '20210420', '181742');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618914458401', '182738', 'EVENTNCHECKDETIAL', 'ECNY2021042018273300004600040004', '1', 'SUCC', '00000000', '成功', '172.22.80.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210420', '182738');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618916541030', '190221', 'EVENTNCHECKDETIAL', 'ECNY2021042019021500004600060006', '1', 'SUCC', '00000000', '成功', '172.22.80.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210420', '190221');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619072025422', '141345', 'EVENTNCHECKDETIAL', 'ECNY2021042214134000004700020002', '1', 'SUCC', '00000000', '成功', '10.7.91.113', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '141345');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619079016707', '161016', 'EVENTNCHECKDETIAL', 'ECNY2021042216101100004700040004', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '161016');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619079288484', '161448', 'EVENTNCHECKDETIAL', 'ECNY2021042216144300004700060006', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '161448');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080091670', '162811', 'EVENTNCHECKDETIAL', 'ECNY2021042216280600004700080008', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '162811');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080308457', '163148', 'EVENTNCHECKDETIAL', 'ECNY2021042216314300004700100010', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '163148');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080363292', '163243', 'EVENTNCHECKDETIAL', 'ECNY2021042216322700004700120012', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '163243');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080912251', '164152', 'EVENTNCHECKDETIAL', 'ECNY2021042216414700004700140014', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '164152');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619144153965', '101553', 'EVENTNCHECKDETIAL', 'ECNY2021042310154000004750030003', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '101553');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159616176', '143336', 'EVENTNCHECKDETIAL', 'ECNY2021042314332600004700160016', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143336');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159623369', '143343', 'EVENTNCHECKDETIAL', 'ECNY2021042314332800004700180018', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143343');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159623718', '143343', 'EVENTNCHECKDETIAL', 'ECNY2021042314333800004700200020', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143343');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159629604', '143349', 'EVENTNCHECKDETIAL', 'ECNY2021042314334200004700220022', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143349');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159669246', '143429', 'EVENTNCHECKDETIAL', 'ECNY2021042314341400004700240024', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143429');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159661605', '143421', 'EVENTNCHECKDETIAL', 'ECNY2021042314341400004700260026', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143421');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159677905', '143437', 'EVENTNCHECKDETIAL', 'ECNY2021042314343100004700280028', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143437');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159721403', '143521', 'EVENTNCHECKDETIAL', 'ECNY2021042314350600004700300030', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143521');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160114775', '144154', 'EVENTNCHECKDETIAL', 'ECNY2021042314414900004700320032', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '144154');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160177115', '144257', 'EVENTNCHECKDETIAL', 'ECNY2021042314425000004700340034', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '144257');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160179099', '144259', 'EVENTNCHECKDETIAL', 'ECNY2021042314425400004700360036', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '144259');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160289795', '144449', 'EVENTNCHECKDETIAL', 'ECNY2021042314444300004700380038', '2', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '151112');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619162952621', '152912', 'EVENTNCHECKDETIAL', 'ECNY2021042315290500004800040004', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '152912');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619163207712', '153327', 'EVENTNCHECKDETIAL', 'ECNY2021042315332200004800060006', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '153327');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619164654903', '155734', 'EVENTNCHECKDETIAL', 'ECNY2021042315571900004800080008', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '155734');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619164720839', '155840', 'EVENTNCHECKDETIAL', 'ECNY2021042315583300004800100010', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '155840');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619164782065', '155942', 'EVENTNCHECKDETIAL', 'ECNY2021042315593600004800120012', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '155942');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210425', '1619337543084', '155903', 'EVENTNCHECKDETIAL', 'ECNY2021042515583900005000020002', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210425', '155903');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621072373945', '175253', 'EVENTNCHECKDETIAL', 'ECNY2021051517523900005200030003', '1', 'SUCC', '00000000', '成功', '10.127.33.107', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '175253');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621072585731', '175625', 'EVENTNCHECKDETIAL', 'ECNY2021051517561700005200050005', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '175625');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621073168495', '180608', 'EVENTNCHECKDETIAL', 'ECNY2021051518055900005200070007', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '180608');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621073792988', '181632', 'EVENTNCHECKDETIAL', 'ECNY2021051518162400005200090009', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '181632');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621074128273', '182208', 'EVENTNCHECKDETIAL', 'ECNY2021051518215900005200110011', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '182208');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618913303170', '180823', 'EVENTNCHECKSUMMARY', 'ECNY2021042018081800004500030003', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210308\",\"BatchId\":\"B202103081600\"}', '20210420', '180823');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618913502753', '181142', 'EVENTNCHECKSUMMARY', 'ECNY2021042018113700004500050005', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210308\",\"BatchId\":\"B202103081600\"}', '20210420', '181142');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618913862948', '181742', 'EVENTNCHECKSUMMARY', 'ECNY2021042018173700004650010001', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210308\",\"BatchId\":\"B202103081600\"}', '20210420', '181742');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618914458369', '182738', 'EVENTNCHECKSUMMARY', 'ECNY2021042018273300004600030003', '1', 'SUCC', '00000000', '成功', '172.22.80.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210420', '182738');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210420', '1618916541002', '190221', 'EVENTNCHECKSUMMARY', 'ECNY2021042019021500004600050005', '1', 'SUCC', '00000000', '成功', '172.22.80.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210420', '190221');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619072025422', '141345', 'EVENTNCHECKSUMMARY', 'ECNY2021042214134000004700010001', '1', 'SUCC', '00000000', '成功', '10.7.91.113', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '141345');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619079016706', '161016', 'EVENTNCHECKSUMMARY', 'ECNY2021042216101100004700030003', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '161016');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619079288482', '161448', 'EVENTNCHECKSUMMARY', 'ECNY2021042216144300004700050005', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '161448');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080101858', '162821', 'EVENTNCHECKSUMMARY', 'ECNY2021042216280600004700070007', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '162821');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080308454', '163148', 'EVENTNCHECKSUMMARY', 'ECNY2021042216314300004700090009', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '163148');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080363056', '163243', 'EVENTNCHECKSUMMARY', 'ECNY2021042216322700004700110011', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '163243');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210422', '1619080912251', '164152', 'EVENTNCHECKSUMMARY', 'ECNY2021042216414700004700130013', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210422', '164152');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619144153965', '101553', 'EVENTNCHECKSUMMARY', 'ECNY2021042310153900004750020002', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '101553');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159616176', '143336', 'EVENTNCHECKSUMMARY', 'ECNY2021042314332600004700150015', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143336');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159623377', '143343', 'EVENTNCHECKSUMMARY', 'ECNY2021042314332800004700170017', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143343');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159623737', '143343', 'EVENTNCHECKSUMMARY', 'ECNY2021042314333800004700190019', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143343');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159629604', '143349', 'EVENTNCHECKSUMMARY', 'ECNY2021042314334200004700210021', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143349');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159669242', '143429', 'EVENTNCHECKSUMMARY', 'ECNY2021042314341400004700230023', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143429');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159661601', '143421', 'EVENTNCHECKSUMMARY', 'ECNY2021042314341400004700250025', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143421');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159686275', '143446', 'EVENTNCHECKSUMMARY', 'ECNY2021042314343100004700270027', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143446');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619159721402', '143521', 'EVENTNCHECKSUMMARY', 'ECNY2021042314350600004700290029', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '143521');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160114775', '144154', 'EVENTNCHECKSUMMARY', 'ECNY2021042314414900004700310031', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '144154');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160177115', '144257', 'EVENTNCHECKSUMMARY', 'ECNY2021042314425000004700330033', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '144257');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160179094', '144259', 'EVENTNCHECKSUMMARY', 'ECNY2021042314425400004700350035', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '144259');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619160289791', '144449', 'EVENTNCHECKSUMMARY', 'ECNY2021042314444300004700370037', '2', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '151112');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619162960581', '152920', 'EVENTNCHECKSUMMARY', 'ECNY2021042315290500004800030003', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '152920');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619163209631', '153329', 'EVENTNCHECKSUMMARY', 'ECNY2021042315332200004800050005', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '153329');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619164655008', '155735', 'EVENTNCHECKSUMMARY', 'ECNY2021042315571900004800070007', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '155735');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619164720801', '155840', 'EVENTNCHECKSUMMARY', 'ECNY2021042315583300004800090009', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '155840');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619164782002', '155942', 'EVENTNCHECKSUMMARY', 'ECNY2021042315593600004800110011', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210423', '155942');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210425', '1619337543084', '155903', 'EVENTNCHECKSUMMARY', 'ECNY2021042515583900005000010001', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210425', '155903');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621072366773', '175246', 'EVENTNCHECKSUMMARY', 'ECNY2021051517523400005200020002', '1', 'SUCC', '00000000', '成功', '10.127.33.107', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '175246');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621072585707', '175625', 'EVENTNCHECKSUMMARY', 'ECNY2021051517561700005200040004', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '175625');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621073168391', '180608', 'EVENTNCHECKSUMMARY', 'ECNY2021051518055900005200060006', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '180608');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621073792882', '181632', 'EVENTNCHECKSUMMARY', 'ECNY2021051518162400005200080008', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '181632');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621074127973', '182207', 'EVENTNCHECKSUMMARY', 'ECNY2021051518215900005200100010', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '182207');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210515', '1621074379178', '182619', 'EVENTNCHECKSUMMARY', 'ECNY2021051518254200005200120012', '2', 'SUCC', '00000000', '成功', '10.127.33.107', '{\"BatchDate\":\"20210309\",\"BatchId\":\"B202103091600\"}', '20210515', '182621');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619167200523', '164000', 'EVENT_CORE_ENQUIRE', 'ECNY2021042316394900004850010001', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\n	\"paySerno\":\"16394860001\",\n	\"coreReqDate\":\"20210309\",\n	\"callBackCanonicalName\":\"com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack\",\n	\"coreReqSerno\":\"ECNY2021042316394900004850010001\",\n	\"canResn\":\"核心异常回查\",\n	\"payDate\":\"20210423\"\n}', '20210423', '164000');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619167275576', '164115', 'EVENT_CORE_ENQUIRE', 'ECNY2021042316411200004850020002', '1', 'SUCC', '00000000', '成功', '169.254.103.37', '{\n	\"paySerno\":\"16411160002\",\n	\"coreReqDate\":\"20210309\",\n	\"callBackCanonicalName\":\"com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack\",\n	\"coreReqSerno\":\"ECNY2021042316411200004850020002\",\n	\"canResn\":\"核心异常回查\",\n	\"payDate\":\"20210423\"\n}', '20210423', '164115');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619167339491', '164219', 'EVENT_CORE_ENQUIRE', 'ECNY2021042316421500004850030003', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\n	\"paySerno\":\"16421560003\",\n	\"coreReqDate\":\"20210309\",\n	\"callBackCanonicalName\":\"com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack\",\n	\"coreReqSerno\":\"ECNY2021042316421500004850030003\",\n	\"canResn\":\"核心异常回查\",\n	\"payDate\":\"20210423\"\n}', '20210423', '164219');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619167839819', '165039', 'EVENT_CORE_ENQUIRE', 'ECNY2021042316503400004850040004', '1', 'SUCC', '00000000', '成功', '127.0.0.1', '{\n	\"paySerno\":\"16503460004\",\n	\"coreReqDate\":\"20210309\",\n	\"callBackCanonicalName\":\"com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack\",\n	\"coreReqSerno\":\"ECNY2021042316503400004850040004\",\n	\"canResn\":\"核心异常回查\",\n	\"payDate\":\"20210423\"\n}', '20210423', '165039');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210514', '1620982543545', '165543', 'EVENT_CORE_ENQUIRE', 'ECNY2021051416553200005100020002', '1', 'SUCC', '00000000', '成功', '10.127.1.254', '{\n	\"paySerno\":\"16553145002\",\n	\"coreReqDate\":\"20210309\",\n	\"callBackCanonicalName\":\"com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack\",\n	\"coreReqSerno\":\"ECNY2021051416553200005100020002\",\n	\"canResn\":\"核心异常回查\",\n	\"payDate\":\"20210514\"\n}', '20210514', '165543');
INSERT INTO `pay_comm_excepteventinfo` VALUES ('20210423', '1619168037707', '165357', 'EVENT_TRANS_STATUS_QRY', '', '1', 'PROC', NULL, NULL, '172.10.10.1', '{\n	\"walletId\":\"$(ccJNDNJ[nKLhMBYvvWKHKU3RMQyp^[zg\",\n	\"walletInstNo\":\"vk%Dk$Q8wDzH[f\",\n	\"subWalletId \":\"\",\n	\"walletTdRType  \":\"\",\n	\"tradeNo \":\"\",\n	\"txnId\":\"1385071160361881600\"\n}', '20210423', '165357');

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
-- Records of pay_comm_idempotent
-- ----------------------------
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231509', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231528', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231532', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231557', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T15:57:13', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231558', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T15:58:23', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231559', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T15:59:36', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231639', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T16:39:28', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231641', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T16:41:11', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231642', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T16:42:15', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231650', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T16:50:33', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104231654', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-23T16:54:31', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251000', 'dcep.915.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T10:00:30', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251001', 'dcep.915.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T10:01:47', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251002', 'dcep.917.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T10:02:21', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251006', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T10:06:04', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251022', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T10:22:46', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251031', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T10:31:15', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251558', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T15:58:37', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104251934', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T19:34:42', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104252037', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-04-25T20:37:16', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104260906', 'dcep.915.001.01', 'G4001011000013', 'C1091231000013', '2021-04-26T09:06:42', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104260907', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2021-04-26T09:07:59', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104260909', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-26T09:09:27', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104260910', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-04-26T09:10:44', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104260918', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-26T09:18:04', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202104280934', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-04-28T09:34:16', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105141537', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-05-14T15:37:38', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105141655', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-05-14T16:55:25', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105141656', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-05-14T16:56:29', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151016', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T10:16:25', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151103', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T11:03:40', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151107', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T11:07:17', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151146', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T11:46:02', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151241', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:41:58', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151247', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:47:30', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151249', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:50:55', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151253', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:53:04', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151254', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:54:11', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151255', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:55:21', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151257', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:57:28', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151259', 'dcep.225.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T12:59:17', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151552', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T15:52:57', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151602', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T16:02:51', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151648', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T16:48:15', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151702', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:02:43', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151704', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:04:31', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151714', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:14:18', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151716', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:16:34', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151718', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:18:02', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151719', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:19:15', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151739', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:39:36', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151748', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:48:44', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151753', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:53:19', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151759', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T17:59:03', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151816', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T18:16:14', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151821', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T18:21:55', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('00000000001120010001202105151825', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2021-05-15T18:25:18', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210416205303900000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210416205404700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419111601100000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419111803600000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419112002900000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419142102000000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419142203700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419142404700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419142803800000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419143100500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419143600100000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419144601500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419171603100000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419172600500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419182003600000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419182602700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419182703200000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419182803500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419184404700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419184803900000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419184902300000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419184904800000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185001000000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185004000000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185004100000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185004200000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185103300000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185103400000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185103500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419185103700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210419190500000000000000112001', 'dcep.713.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420103002500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420105205500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420110100400000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420113303000000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420113502700000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420141205700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420155404200000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420155405400000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420155502200000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420160102100000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420161500300000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420161504500000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420162100400000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420162102200000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420162500900000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420162502400000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420162504700000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420172801000000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420174603800000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420180801800000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420181103700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420181603900000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420181703700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420182703200000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190201500000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-72JVNA8');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190403300000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190404000000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190405500000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190405700000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190405900000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190500000000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420190500100000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420193201200000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420193505800000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420193601000000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420193604200000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210420193805100000000000112001', 'dcep.433.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210421180900300000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422084105700000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422085505500000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422095904600000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422113003200000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422123505300000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422141303900000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422161001100000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422161404300000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422162800600000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422163104300000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422163202700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210422164104700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423090900900000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423091100700000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423095200800000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423100203300000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423101503800000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423110602100000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423110904800000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423111400700000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423134903100000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423141400500000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143302600000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143302800000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143303800000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143304200000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143305400000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'DESKTOP-HI01MMO');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143401400000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143403100000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423143500600000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423144104900000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423144205000000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423144205400000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423144404300000000000112001', 'dcep.711.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423163404400000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'appledeMacBook-Pro.local');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423185705500000000000112001', 'dcep.401.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210423190100100000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');
INSERT INTO `pay_comm_idempotent` VALUES ('20210425093205200000000000112001', 'dcep.221.001.01', 'G4001011000013', 'C1091231000013', '2020-10-30T09:45:08', 'R', 'localhost.localhost');

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
-- Records of pay_comm_monitor
-- ----------------------------
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '15493275019', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210113106040120333044574013000', '数据入库失败', '20210415', '154932');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '15500875022', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210113106040120333044574013000', '数据入库失败', '20210415', '155008');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17465090002', '2021-04-15T17:46:50', 'dcep.401.001.01', 'msgId:20210415106040117465000455001000', 'msgContext为空或超出限制', '20210415', '174650');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17470390004', '2021-04-15T17:47:03', 'dcep.401.001.01', 'msgId:20210415106040117470300455002000', 'msgContext为空或超出限制', '20210415', '174703');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17481790006', '2021-04-15T17:48:17', 'dcep.401.001.01', 'msgId:20210415106040117481700455003000', '接收机构无接收权限', '20210415', '174817');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17482390008', '2021-04-15T17:48:23', 'dcep.401.001.01', 'msgId:20210415106040117482300455004000', 'msgContext为空或超出限制', '20210415', '174823');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17482990010', '2021-04-15T17:48:29', 'dcep.401.001.01', 'msgId:20210415106040117482900455005000', 'msgContext为空或超出限制', '20210415', '174829');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17573895003', '2021-04-15T17:57:38', 'dcep.401.001.01', 'msgId:20210415106040117573500460001000', '交易成功', '20210415', '175738');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17574995006', '2021-04-15T17:57:49', 'dcep.401.001.01', 'msgId:20210415106040117574800460002000', '交易成功', '20210415', '175749');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17582195008', '2021-04-15T17:58:21', 'dcep.401.001.01', 'msgId:20210415106040117582100460004000', 'msgContext为空或超出限制', '20210415', '175821');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17582195010', '2021-04-15T17:58:21', 'dcep.401.001.01', 'msgId:20210415106040117582100460004000', 'msgContext为空或超出限制', '20210415', '175821');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '17590995012', '2021-04-15T17:59:09', 'dcep.401.001.01', 'msgId:20210415106040117590900460005000', 'msgContext为空或超出限制', '20210415', '175909');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18014795014', '2021-04-15T18:01:47', 'dcep.401.001.01', 'msgId:20210415106040118014700460006000', 'msgContext为空或超出限制', '20210415', '180147');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18014995016', '2021-04-15T18:01:49', 'dcep.401.001.01', 'msgId:20210415106040118014900460007000', 'msgContext为空或超出限制', '20210415', '180149');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18052595018', '2021-04-15T18:05:25', 'dcep.401.001.01', 'msgId:20210415106040118052500460008000', 'msgContext为空或超出限制', '20210415', '180525');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18053995020', '2021-04-15T18:05:39', 'dcep.401.001.02', 'msgId:20210415106040118053900460009000', 'msgContext为空或超出限制', '20210415', '180539');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18095695023', '2021-04-15T18:09:56', 'dcep.401.001.01', 'msgId:20210415106040118095600460010000', 'msgContext为空或超出限制', '20210415', '180956');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18114295026', '2021-04-15T18:11:42', 'dcep.401.001.01', 'msgId:20210415106040118114200460011000', 'msgContext为空或超出限制', '20210415', '181142');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18122295029', '2021-04-15T18:12:22', 'dcep.401.001.01', 'msgId:20210415106040118122200460012000', 'msgContext为空或超出限制', '20210415', '181222');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18123095032', '2021-04-15T18:12:30', 'dcep.401.001.01', 'msgId:20210415106040118123000460013000', 'msgContext为空或超出限制', '20210415', '181230');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18123695035', '2021-04-15T18:12:36', 'dcep.401.001.01', 'msgId:20210415106040118123600460014000', 'msgContext为空或超出限制', '20210415', '181236');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18125295038', '2021-04-15T18:12:52', 'dcep.401.001.01', 'msgId:20210415106040118125100460015000', 'msgContext为空或超出限制', '20210415', '181252');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18132195041', '2021-04-15T18:13:21', 'dcep.401.001.01', 'msgId:20210415106040118132100460016000', 'msgContext为空或超出限制', '20210415', '181321');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18135895043', '2021-04-15T18:13:58', 'dcep.401.001.01', 'msgId:20210415106040118135700460017000', '接收机构状态异常', '20210415', '181358');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18141195046', '2021-04-15T18:14:11', 'dcep.401.001.01', 'msgId:20210415106040118141000460018000', '接收机构状态异常', '20210415', '181411');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18141995049', '2021-04-15T18:14:19', 'dcep.401.001.01', 'msgId:20210415106040118141800460019000', '接收机构状态异常', '20210415', '181419');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18144795052', '2021-04-15T18:14:47', 'dcep.401.001.01', 'msgId:20210415106040118144600460020000', '接收机构状态异常', '20210415', '181447');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18151295055', '2021-04-15T18:15:12', 'dcep.401.001.01', 'msgId:20210415106040118151100460021000', '接收机构状态异常', '20210415', '181512');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18171395058', '2021-04-15T18:17:13', 'dcep.401.001.01', 'msgId:20210415106040118171300460022000', '接收机构状态异常', '20210415', '181713');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18172595061', '2021-04-15T18:17:25', 'dcep.401.001.01', 'msgId:20210415106040118172500460023000', '接收机构状态异常', '20210415', '181725');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18175195064', '2021-04-15T18:17:51', 'dcep.401.001.01', 'msgId:20210415106040118175100460024000', '接收机构状态异常', '20210415', '181751');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18211495067', '2021-04-15T18:21:14', 'dcep.401.001.01', 'msgId:20210415106040118211300460025000', '接收机构状态异常', '20210415', '182114');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18212195070', '2021-04-15T18:21:21', 'dcep.401.001.01', 'msgId:20210415106040118212100460026000', '接收机构状态异常', '20210415', '182121');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18255795072', '2021-04-15T18:25:57', 'dcep.401.001.01', 'msgId:20210415106040118255700460027000', '接收机构状态异常', '20210415', '182557');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18270695075', '2021-04-15T18:27:06', 'dcep.401.001.01', 'msgId:20210415106040118270600460028000', '接收机构状态异常', '20210415', '182706');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18271195078', '2021-04-15T18:27:11', 'dcep.401.001.01', 'msgId:20210415106040118271100460029000', '接收机构状态异常', '20210415', '182711');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18364990014', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210415', '183649');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18443395085', '2021-04-15T18:44:33', 'dcep.401.001.01', 'msgId:20210415106040118443200460030000', '接收机构状态异常', '20210415', '184433');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18444295088', '2021-04-15T18:44:42', 'dcep.401.001.01', 'msgId:20210415106040118444100460031000', '接收机构状态异常', '20210415', '184442');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18572305002', '2021-04-15T18:57:23', 'dcep.401.001.01', 'msgId:20210415106040118572300465001000', 'msgContext为空或超出限制', '20210415', '185723');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18572505004', '2021-04-15T18:57:25', 'dcep.401.001.01', 'msgId:20210415106040118572500465002000', 'msgContext为空或超出限制', '20210415', '185725');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18572905006', '2021-04-15T18:57:29', 'dcep.401.001.01', 'msgId:20210415106040118572900465003000', 'msgContext为空或超出限制', '20210415', '185729');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '18573805008', '2021-04-15T18:57:38', 'dcep.401.001.01', 'msgId:20210415106040118573800465004000', 'msgContext为空或超出限制', '20210415', '185738');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '19004805010', '2021-04-15T19:00:48', 'dcep.401.001.01', 'msgId:20210415106040119004800465005000', 'msgContext为空或超出限制', '20210415', '190048');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '19185615003', '2021-04-15T19:18:56', 'dcep.401.001.01', 'msgId:20210415106040119185600475001000', '接收机构状态异常', '20210415', '191856');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '19244215005', '2021-04-15T19:24:42', 'dcep.401.001.01', 'msgId:20210415106040119244200475002000', '接收机构状态异常', '20210415', '192442');
INSERT INTO `pay_comm_monitor` VALUES ('20210415', '19554895094', '2021-04-15T19:55:48', 'dcep.401.001.01', 'msgId:20210415106040119554800460032000', '接收机构状态异常', '20210415', '195548');
INSERT INTO `pay_comm_monitor` VALUES ('20210416', '17162540002', '2021-04-16T17:16:25', 'dcep.401.001.01', 'msgId:20210416106040117140500480001000', '接收机构状态异常', '20210416', '171625');
INSERT INTO `pay_comm_monitor` VALUES ('20210416', '17190640004', '2021-04-16T17:19:06', 'dcep.401.001.01', 'msgId:20210416106040117163200480002000', '接收机构状态异常', '20210416', '171906');
INSERT INTO `pay_comm_monitor` VALUES ('20210416', '17221740007', '2021-04-16T17:22:17', 'dcep.401.001.01', 'msgId:20210416106040117221700480004000', '接收机构状态异常', '20210416', '172217');
INSERT INTO `pay_comm_monitor` VALUES ('20210416', '17460630006', '2021-04-16T17:46:06', 'dcep.401.001.01', 'msgId:20210416106040117460200485001000', '交易成功', '20210416', '174606');
INSERT INTO `pay_comm_monitor` VALUES ('20210416', '17465530009', '2021-04-16T17:46:55', 'dcep.401.001.01', 'msgId:20210416106040117465500485002000', '交易成功', '20210416', '174655');
INSERT INTO `pay_comm_monitor` VALUES ('20210417', '17011755003', '2021-01-13T20:37:31', 'dcep.401.001.01', 'msgId:20210113106040120333044574013000', '数据入库失败', '20210417', '170117');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '10583780002', '2021-04-19T10:58:37', 'dcep.401.001.01', 'msgId:20210419106040110583700515001000', '接收机构状态异常', '20210419', '105837');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '14213185008', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '142131');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '14223785011', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '142237');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '14245090003', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '142450');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '14283990006', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '142839');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '14310690009', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '143106');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '14360400003', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '143604');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '18204415002', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '182044');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '18273220003', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '182732');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '18283520005', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '182835');
INSERT INTO `pay_comm_monitor` VALUES ('20210419', '18444820007', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210308000171181982980514700000', '其他错误', '20210419', '184448');
INSERT INTO `pay_comm_monitor` VALUES ('20210420', '09213730003', '2021-01-13T20:37:31', 'dcep.401.001.01', 'msgId:20210113106040120333044574013001', '数据入库失败', '20210420', '092137');
INSERT INTO `pay_comm_monitor` VALUES ('20210420', '09250930008', '2021-01-13T20:33:33', 'dcep.401.001.01', 'msgId:20210113106040120333044574013000', '数据入库失败', '20210420', '092509');
INSERT INTO `pay_comm_monitor` VALUES ('20210420', '09260030013', '2021-01-13T20:33:33', 'dcep.401.001.01', 'msgId:20210113106040120333044574013003', '数据入库失败', '20210420', '092600');
INSERT INTO `pay_comm_monitor` VALUES ('20210420', '12461995011', '2021-04-20T12:46:19', 'dcep.401.001.01', 'msgId:20210420106040112461400530009000', '交易成功', '20210420', '124619');
INSERT INTO `pay_comm_monitor` VALUES ('20210420', '12464795014', '2021-04-20T12:46:47', 'dcep.401.001.01', 'msgId:20210420106040112464700530010000', '交易成功', '20210420', '124647');
INSERT INTO `pay_comm_monitor` VALUES ('20210420', '13075395017', '2021-04-20T13:07:53', 'dcep.401.001.01', 'msgId:20210420106040113075100530011000', '交易成功', '20210420', '130753');
INSERT INTO `pay_comm_monitor` VALUES ('20210420', '14173695019', '2021-04-20T14:17:36', 'dcep.401.001.01', 'msgId:20210420106040114173500530012000', '接收机构为空', '20210420', '141736');
INSERT INTO `pay_comm_monitor` VALUES ('20210421', '10380340008', '2021-04-21T10:38:03', 'dcep.401.001.01', 'msgId:20210421106040110380200580007000', '接收机构为空', '20210421', '103803');
INSERT INTO `pay_comm_monitor` VALUES ('20210421', '10415940013', '2021-04-21T10:41:59', 'dcep.401.001.01', 'msgId:20210421106040110415700580009000', '数据入库失败', '20210421', '104159');
INSERT INTO `pay_comm_monitor` VALUES ('20210421', '10503140021', '2021-04-21T10:50:31', 'dcep.401.001.01', 'msgId:20210421106040110503100580013000', '数据入库失败', '20210421', '105031');
INSERT INTO `pay_comm_monitor` VALUES ('20210421', '10522940023', '2021-04-21T10:52:29', 'dcep.401.001.01', 'msgId:20210421106040110522900580014000', '数据入库失败', '20210421', '105229');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '08435950005', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210422', '084359');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '08584450008', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210422', '085844');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '10242855003', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210422', '102428');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '11320355006', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210422', '113203');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '12360355009', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210422', '123603');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '15561575002', '2021-04-22T15:56:15', 'dcep.401.001.01', 'msgId:20210422106040115561400610001000', '接收机构无接收权限', '20210422', '155615');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '15562875004', '2021-04-22T15:56:28', 'dcep.401.001.01', 'msgId:20210422106040115562800610002000', '接收机构状态异常', '20210422', '155628');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '15573775006', '2021-04-22T15:57:37', 'dcep.401.001.01', 'msgId:20210422106040115573700610003000', '接收机构状态异常', '20210422', '155737');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '15582375008', '2021-04-22T15:58:23', 'dcep.401.001.01', 'msgId:20210422106040115582300610004000', '接收机构状态异常', '20210422', '155823');
INSERT INTO `pay_comm_monitor` VALUES ('20210422', '15594175010', '2021-04-22T15:59:41', 'dcep.401.001.01', 'msgId:20210422106040115594000610005000', '接收机构状态异常', '20210422', '155941');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '11083405003', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210423', '110834');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '11095005006', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210423', '110950');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '11272405008', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210309000171181982980514700000', '其他错误', '20210423', '112724');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13123320007', '2021-04-23T13:12:33', 'dcep.401.001.01', 'msgId:20210423106040113123200645005000', '交易成功', '20210423', '131233');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13193420010', '2021-04-23T13:19:34', 'dcep.401.001.01', 'msgId:20210423106040113133200645006000', '交易成功', '20210423', '131934');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13260325003', '2021-04-23T13:26:03', 'dcep.401.001.01', 'msgId:20210423106040113260100650001000', '交易成功', '20210423', '132603');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13260425006', '2021-04-23T13:26:04', 'dcep.401.001.01', 'msgId:20210423106040113260300650002000', '交易成功', '20210423', '132604');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13271425009', '2021-04-23T13:27:14', 'dcep.401.001.01', 'msgId:20210423106040113271300650003000', '交易成功', '20210423', '132714');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13281325012', '2021-04-23T13:28:13', 'dcep.401.001.01', 'msgId:20210423106040113281300650004000', '交易成功', '20210423', '132813');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13323930004', '2021-04-23T13:32:39', 'dcep.401.001.01', 'msgId:20210423106040113323700655002000', '数据入库失败', '20210423', '133239');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13354730006', '2021-04-23T13:35:47', 'dcep.401.001.01', 'msgId:20210423106040113323700655002000', '数据入库失败', '20210423', '133547');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13403335003', '2021-04-23T13:40:33', 'dcep.401.001.01', 'msgId:20210423106040113395700660001000', '交易成功', '20210423', '134033');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13440340004', '2021-04-23T13:44:03', 'dcep.401.001.01', 'msgId:20210423106040113440100665002000', '数据入库失败', '20210423', '134403');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13513145004', '2021-04-23T13:51:31', 'dcep.401.001.01', 'msgId:20210423106040113512900670002000', '数据入库失败', '20210423', '135131');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13542945009', '2021-04-23T13:54:29', 'dcep.401.001.01', 'msgId:20210423106040113542900670004000', '接收机构无接收权限', '20210423', '135429');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '13544145011', '2021-04-23T13:54:41', 'dcep.401.001.01', 'msgId:20210423106040113544100670005000', '接收机构无接收权限', '20210423', '135441');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '16581870005', '2021-01-13T20:37:31', 'dcep.401.001.01', 'msgId:20210113106040120333044574013002', '数据入库失败', '20210423', '165818');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '17133570014', '2021-01-13T20:37:31', 'dcep.401.001.01', 'msgId:20210113106040120333044574013007', '数据入库失败', '20210423', '171335');
INSERT INTO `pay_comm_monitor` VALUES ('20210423', '18575580007', '2020-10-10T09:30:30', 'dcep.401.001.01', 'msgId:20210413106040120333044574013000', '数据入库失败', '20210423', '185755');
INSERT INTO `pay_comm_monitor` VALUES ('20210515', '17475555002', '2021-01-13T16:12:27', '对账汇总711报文数据库新增失败', '20210309000171181982980514700000', '其他错误', '20210515', '174755');
INSERT INTO `pay_comm_monitor` VALUES ('20210525', '17311460002', '2021-05-25T17:31:14', 'dcep.401.001.01', 'msgId:20210525106040117311300740001000', '接收机构无接收权限', '20210525', '173114');
INSERT INTO `pay_comm_monitor` VALUES ('20210525', '17313760004', '2021-05-25T17:31:37', 'dcep.401.001.01', 'msgId:20210525106040117313700740002000', '接收机构无接收权限', '20210525', '173137');
INSERT INTO `pay_comm_monitor` VALUES ('20210525', '18192865006', '2021-05-25T18:19:28', 'dcep.401.001.01', 'msgId:20210525106040118192800745003000', '接收机构无接收权限', '20210525', '181928');

-- ----------------------------
-- Table structure for pay_comm_param
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_param`;
CREATE TABLE `pay_comm_param`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数类型',
  `param_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数id',
  `param_value` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数数值',
  `param_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数描述',
  `param_status` int(1) NULL DEFAULT NULL COMMENT '参数状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '参数创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '参数更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_UNIQUE`(`id`) USING BTREE,
  UNIQUE INDEX `param_key_UNIQUE`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_comm_param
-- ----------------------------
INSERT INTO `pay_comm_param` VALUES (19, '1', 'core-addr', 'http://127.0.0.1:9002/core', '核心地址', 0, '2021-04-20 18:10:58', '2021-04-23 19:01:45');
INSERT INTO `pay_comm_param` VALUES (20, '1', 'core-acct-type', '1002345', '核心记账科目', 0, '2021-04-20 18:12:12', NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '配置参数表' ROW_FORMAT = Dynamic;

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
INSERT INTO `pay_comm_party` VALUES ('C1010411000013', 'PT00', '测试银行A', '008000', 'ST01', '1', 'Z', '111', '1@qq.com', '1', '20210307', '20220307', '500', '0', '20210305', '165632');
INSERT INTO `pay_comm_party` VALUES ('C1030644021075', 'PT00', '测试银行B', '008000', 'ST02', '1', 'Z', '111', '1@qq.com', '1', '20210307', '20220307', '500', '0', '20210526', '150839');
INSERT INTO `pay_comm_party` VALUES ('C1040311005293', 'PT00', '测试银行C', '008000', 'ST03', '1', 'Z', '111', '1@qq.com', '1', '20210113114554', NULL, '500', '0', '20210330', '100135');
INSERT INTO `pay_comm_party` VALUES ('C1091231000013', 'PT00', '上海银行股份有限公司测试行号', '000001', 'ST02', '1', '杨超', '13810108888', 'shanghaibank@shbk.com', '021-88990011', '20201011000000', '20301010000000', '1', '0', '20210425', '100221');

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
-- Records of pay_comm_party_tobeffect
-- ----------------------------
INSERT INTO `pay_comm_party_tobeffect` VALUES ('C1086932000016', 'CC00', 'EF01', 'PT00', '南京银行股份有限公司测试行号', '000001', 'ST02', '杨超越', '13810108889', 'nanjingibank@njbk.com', '021-88990012', '20201011000000', '20301010000000', '1', '0', '20210425', '100221');

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
INSERT INTO `pay_comm_partyauth` VALUES ('C1010411000013', 'dcep.227.001.01', 'D201', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1010411000013', 'dcep.801.001.01', 'E100', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.221.001.01', 'E100', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.225.001.01', 'D201', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.401.001.01', '11', 'AS00', 'AS00', '1', '20210113', NULL, '20210426', '090645');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.401.001.01', 'null', 'AS00', 'AS00', '1', '20210113', NULL, '20210308', '114729');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.433.001.01', 'null', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('C1030644021075', 'dcep.801.001.01', 'E100', 'AS00', 'AS00', '1', '20210113', NULL, '20210305', '093747');
INSERT INTO `pay_comm_partyauth` VALUES ('G4001011000013', 'dcep.401.001.01', 'null', 'AS00', 'AS00', '1', '20210113', NULL, '20210413', '194100');

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
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202103081600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210420061130', '20210420061130');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT001', 'CT001-对账明细文件转存', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileListTask', '', 'SUCC', '20210420060823', '20210420060823');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT002', 'CT002-金融开放平台申请文件', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailSendFileReqTask', '', 'SUCC', '20210420060823', '20210420060823');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT003', 'CT003-检查明细文件是否收齐', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileCheckTask', '', 'SUCC', '20210420060823', '20210420060823');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT004', 'CT004-解析明细文件入库任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '04', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileLoadTask', '', 'SUCC', '20210420060823', '20210420060823');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT005', 'CT005-抽数前置任务检查', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '05', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', 'SUCC', '20210420061142', '20210420061142');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT006', 'CT006-明细对账任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '06', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailTask', '', 'SUCC', '20210420061743', '20210420061743');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT007', 'CT007-明细对账结果处理任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '07', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailResultHandleTask', '', 'SUCC', '20210420061743', '20210420061743');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CT008', 'CT007-明细对账结果处理任务12', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', '00', '07', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailResultHandleTask', '', 'SUCC', '20210420061743', '20210420061743');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CS001', 'CS002-抽数前置任务检', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103081600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', 'SUCC', '20210420061142', '20210420061142');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CS002', 'CS002-汇总对账处理', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103081600', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryTask', '', 'SUCC', '20210420061142', '20210420061142');
INSERT INTO `pay_comm_task_exec` VALUES ('20210308', 'CS003', 'CS003-汇总对账结果处理', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103081600', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryResultHandleTask', '', 'SUCC', '20210420061142', '20210420061142');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202103091600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210420070119', '20210420070142');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CT001', 'CT001-对账明细文件转存', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileListTask', '', 'SUCC', '20210420062757', '20210420062803');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CT002', 'CT002-金融开放平台申请文件', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailSendFileReqTask', '', 'SUCC', '20210420062805', '20210420062849');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CT003', 'CT003-检查明细文件是否收齐', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileCheckTask', '', 'SUCC', '20210420062849', '20210420062850');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CT004', 'CT004-解析明细文件入库任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', '00', '04', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileLoadTask', '', 'SUCC', '20210420062850', '20210420062850');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CT005', 'CT005-抽数前置任务检查', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', '00', '05', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', 'SUCC', '20210420062850', '20210420062856');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CT006', 'CT006-明细对账任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', '00', '06', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailTask', '', 'SUCC', '20210420062856', '20210420062903');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CT007', 'CT007-明细对账结果处理任务', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', '00', '07', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailResultHandleTask', '', 'SUCC', '20210420062903', '20210420062903');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CS001', 'CS002-抽数前置任务检', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103091600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', 'SUCC', '20210420062739', '20210420062759');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CS002', 'CS002-汇总对账处理', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103091600', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryTask', '', 'SUCC', '20210420070221', '20210420070356');
INSERT INTO `pay_comm_task_exec` VALUES ('20210309', 'CS003', 'CS003-汇总对账结果处理', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103091600', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryResultHandleTask', '', 'SUCC', '20210420070356', '20210420071111');
INSERT INTO `pay_comm_task_exec` VALUES ('20210420', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104201600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210420101747', '20210420101747');
INSERT INTO `pay_comm_task_exec` VALUES ('20210421', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104211600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210421040000', '20210421040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210422', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104221600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210422022731', '20210422022731');
INSERT INTO `pay_comm_task_exec` VALUES ('20210423', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104231600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210423040000', '20210423040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210424', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104241600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210424040000', '20210424040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210425', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104251600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210425040001', '20210425040002');
INSERT INTO `pay_comm_task_exec` VALUES ('20210426', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104261600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210426040000', '20210426040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210427', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104271600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210427040000', '20210427040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210428', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104281600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210428040001', '20210428040001');
INSERT INTO `pay_comm_task_exec` VALUES ('20210429', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104291600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210429040001', '20210429040001');
INSERT INTO `pay_comm_task_exec` VALUES ('20210430', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104301600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210430040000', '20210430040001');
INSERT INTO `pay_comm_task_exec` VALUES ('20210501', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105011600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210501040000', '20210501040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210502', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105021600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210502040000', '20210502040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210503', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105031600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210503040000', '20210503040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210504', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105041600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210504040000', '20210504040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210505', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105051600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210505040000', '20210505040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210506', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105061600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210506040000', '20210506040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210507', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105071600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210507040000', '20210507040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210508', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105081600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210508040000', '20210508040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210524', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105241600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210524040000', '20210524040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210525', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105251600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210525040000', '20210525040000');
INSERT INTO `pay_comm_task_exec` VALUES ('20210526', 'CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105261600', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', 'SUCC', '20210526040000', '20210526040000');

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
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210308', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202103081600', 'SUCC', '20210420061130', '20210420061130');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210308', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103081600', 'SUCC', '20210420060823', '20210420061743');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210308', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103081600', 'SUCC', '20210420060823', '20210420061142');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210309', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202103091600', 'SUCC', '20210420062719', '20210420070142');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210309', 'CHK_DTL', '明细对账', 'D0001', '行内', 'B202103091600', 'SUCC', '20210420062738', '20210420062903');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210309', 'CHK_SUM', '汇总对账', 'D0001', '行内', 'B202103091600', 'SUCC', '20210420062738', '20210420071111');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210420', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104201600', 'SUCC', '20210420101747', '20210420101747');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210421', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104211600', 'SUCC', '20210421040000', '20210421040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210422', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104221600', 'SUCC', '20210422022731', '20210422022731');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210423', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104231600', 'SUCC', '20210423040000', '20210423040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210424', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104241600', 'SUCC', '20210424040000', '20210424040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210425', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104251600', 'SUCC', '20210425040001', '20210425040002');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210426', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104261600', 'SUCC', '20210426040000', '20210426040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210427', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104271600', 'SUCC', '20210427040000', '20210427040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210428', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104281600', 'SUCC', '20210428040001', '20210428040001');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210429', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104291600', 'SUCC', '20210429040001', '20210429040001');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210430', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202104301600', 'SUCC', '20210430040000', '20210430040001');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210501', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105011600', 'SUCC', '20210501040000', '20210501040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210502', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105021600', 'SUCC', '20210502040000', '20210502040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210503', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105031600', 'SUCC', '20210503040000', '20210503040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210504', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105041600', 'SUCC', '20210504040000', '20210504040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210505', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105051600', 'SUCC', '20210505040000', '20210505040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210506', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105061600', 'SUCC', '20210506040000', '20210506040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210507', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105071600', 'SUCC', '20210507040000', '20210507040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210508', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105081600', 'SUCC', '20210508040000', '20210508040001');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210524', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105241600', 'SUCC', '20210524040000', '20210524040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210525', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105251600', 'SUCC', '20210525040000', '20210525040000');
INSERT INTO `pay_comm_task_group_exec` VALUES ('20210526', 'CHK_DATA', '抽数批次', 'D0001', '行内', 'B202105261600', 'SUCC', '20210526040000', '20210526040000');

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
INSERT INTO `pay_comm_task_group_info` VALUES ('CHK_SUM', '汇总对账', 'D0001', '行内', '1');

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
INSERT INTO `pay_comm_task_info` VALUES ('CD001', 'CD001-金融登记簿抽数', 'CHK_DATA', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataFileTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CS001', 'CS002-抽数前置任务检', 'CHK_SUM', '00', '01', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', '1');
INSERT INTO `pay_comm_task_info` VALUES ('CS002', 'CS002-汇总对账处理', 'CHK_SUM', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CS003', 'CS003-汇总对账结果处理', 'CHK_SUM', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeSummaryResultHandleTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CT001', 'CT001-对账明细文件转存', 'CHK_DTL', '00', '01', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileListTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CT002', 'CT002-金融开放平台申请文件', 'CHK_DTL', '00', '02', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailSendFileReqTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CT003', 'CT003-检查明细文件是否收齐', 'CHK_DTL', '00', '03', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileCheckTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CT004', 'CT004-解析明细文件入库任务', 'CHK_DTL', '00', '04', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailFileLoadTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CT005', 'CT005-抽数前置任务检查', 'CHK_DTL', '00', '05', 'com.dcits.dcwlt.pay.batch.task.ImportTradeDataCheckTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CT006', 'CT006-明细对账任务', 'CHK_DTL', '00', '06', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailTask', '', '0');
INSERT INTO `pay_comm_task_info` VALUES ('CT007', 'CT007-明细对账结果处理任务', 'CHK_DTL', '00', '07', 'com.dcits.dcwlt.pay.batch.task.CheckTradeDetailResultHandleTask', '', '0');

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
INSERT INTO `pay_online_checkfilelist` VALUES ('00000000001120010001202105151759003', 'B202103091600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251500_00_01.zip.sec', '20210515', '180542');
INSERT INTO `pay_online_checkfilelist` VALUES ('00000000001120010001202105151816014', 'B202103091600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251500_00_01.zip.sec', '20210515', '181621');
INSERT INTO `pay_online_checkfilelist` VALUES ('00000000001120010001202105151821055', 'B202103091600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251500_00_01.zip.sec', '20210515', '182158');
INSERT INTO `pay_online_checkfilelist` VALUES ('00000000001120010001202105151825018', 'B202103091600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251500_00_01.zip.sec', '20210515', '182533');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210308000171181982980514700000', 'B202103081600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251400_00_01.zip.sec', '20210420', '181737');
INSERT INTO `pay_online_checkfilelist` VALUES ('20210309000171181982980514700000', 'B202103091600', '10.0.23.169:22/dcrecon/20200525/C1010511003703/trade/00/', 'B202005251500_00_01.zip.sec', '20210515', '175616');

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
INSERT INTO `pay_online_checkpath_main` VALUES ('20210420', '18173725002', '181737', '20210308000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '20210308', 'B202103081600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', '20210420', '181737', '0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba', '机构对账汇总核对报文', NULL);
INSERT INTO `pay_online_checkpath_main` VALUES ('20210515', '17540555004', '175405', '20210309000171181982980514700000', '20210113161227', 'C1030644021075', 'G4001011000013', '20210309', 'B202103091600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', '20210515', '175405', '0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba', '机构对账汇总核对报文', NULL);
INSERT INTO `pay_online_checkpath_main` VALUES ('20210515', '17591755005', '175917', '00000000001120010001202105151759003', '20210113161227', 'C1030644021075', 'G4001011000013', '20210309', 'B202103091600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', '20210515', '175917', '0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba', '机构对账汇总核对报文', NULL);
INSERT INTO `pay_online_checkpath_main` VALUES ('20210515', '18162155006', '181621', '00000000001120010001202105151816014', '20210113161227', 'C1030644021075', 'G4001011000013', '20210309', 'B202103091600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', '20210515', '181621', '0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba', '机构对账汇总核对报文', NULL);
INSERT INTO `pay_online_checkpath_main` VALUES ('20210515', '18215855007', '182158', '00000000001120010001202105151821055', '20210113161227', 'C1030644021075', 'G4001011000013', '20210309', 'B202103091600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', '20210515', '182158', '0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba', '机构对账汇总核对报文', NULL);
INSERT INTO `pay_online_checkpath_main` VALUES ('20210515', '18253355008', '182533', '00000000001120010001202105151825018', '20210113161227', 'C1030644021075', 'G4001011000013', '20210309', 'B202103091600', '4', '1016134', 'CNY', '4', '10396', '11', '1005738', '20210515', '182533', '0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba', '机构对账汇总核对报文', NULL);

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
INSERT INTO `pay_online_checkpath_sub` VALUES ('00000000001120010001202105151759003', '20210309', 'B202103091600', '00', '4', '1016134', '0', '0', '4', '1016134', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', '20210515', '180304');
INSERT INTO `pay_online_checkpath_sub` VALUES ('00000000001120010001202105151816014', '20210309', 'B202103091600', '00', '4', '1016134', '0', '0', '4', '1016134', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', '20210515', '181621');
INSERT INTO `pay_online_checkpath_sub` VALUES ('00000000001120010001202105151821055', '20210309', 'B202103091600', '00', '4', '1016134', '0', '0', '4', '1016134', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', '20210515', '182158');
INSERT INTO `pay_online_checkpath_sub` VALUES ('00000000001120010001202105151825018', '20210309', 'B202103091600', '00', '4', '1016134', '0', '0', '4', '1016134', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', '20210515', '182533');
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210308000171181982980514700000', '20210308', 'B202103081600', '00', '4', '1016134', '0', '0', '4', '1016134', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', '20210420', '181737');
INSERT INTO `pay_online_checkpath_sub` VALUES ('20210309000171181982980514700000', '20210309', 'B202103091600', '00', '4', '1016134', '0', '0', '4', '1016134', 'dcep.227.001.01', 'PR00', '4', '1016134', '0', '0', '4', '1016134', '20210515', '175616');

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
-- Records of pay_pay_accflow
-- ----------------------------
INSERT INTO `pay_pay_accflow` VALUES ('20201010', 'ECNY2021041417325700003150010001', 'BANKDEBIT', '20210414', '17323670014', '173001', NULL, '173001', '156', '8900000', NULL, 'IN0000', NULL, '1', '173257', '01', NULL, '1', 'CI0000', '交易成功', '20210414', '1', '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210414', '17323670014', '20210414', '173317', '1618392797616');
INSERT INTO `pay_pay_accflow` VALUES ('20201010', 'ECNY2021041417343600003150020002', 'BANKDEBIT', '20210414', '17343170015', '173001', NULL, '173001', '156', '8900000', NULL, 'IN0000', NULL, '1', '173436', '01', NULL, '1', '000000', '交易成功', '20210414', '1', '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210414', '17343170015', '20210414', '173437', '1618392877329');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041218143400002350050005', 'BANKDEBIT', '20210412', '18134345003', '173001', NULL, '173001', '156', '12311', NULL, 'IN0000', NULL, '1', '181434', '01', NULL, '1', '4', '5', '202011', '1', '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210412', '18134345003', '20210412', '181435', '1618222475180');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041219073700002300030003', 'BANKCREDIT', '20210412', '19071840010', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '190737', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210412', '19071840010', '20210412', '190737', '1618225657377');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041219114500002300040004', 'BANKDEBIT', '20210412', '19114540012', '173001', NULL, '173001', '156', '12311', NULL, 'IN0000', NULL, '1', '191145', '01', NULL, '1', '4', '5', '202011', '1', '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210412', '19114540012', '20210412', '191145', '1618225905828');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041320064900002650010001', 'BANKCREDIT', '20210413', '20063545004', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '200649', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210413', '20063545004', '20210413', '200649', '1618315609962');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041410541000002750010001', 'BANKCREDIT', '20210414', '10534065003', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '105410', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '10534065003', '20210414', '105410', '1618368850702');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041414300000002900010001', 'BANKDEBIT', '20210414', '14295005001', '173001', NULL, '173001', '156', '12311', NULL, 'IN0000', NULL, '1', '143000', '01', NULL, '2', NULL, NULL, NULL, NULL, '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210414', '14295005001', '20210414', '143000', '1618381800440');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041414451900002900020002', 'BANKDEBIT', '20210414', '14445005004', '173001', NULL, '173001', '156', '12311', NULL, 'IN0000', NULL, '1', '144519', '01', NULL, '2', NULL, NULL, NULL, NULL, '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210414', '14445005004', '20210414', '144519', '1618382719151');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041415031600002950010001', 'BANKDEBIT', '20210414', '15025725001', '173001', NULL, '173001', '156', '12311', NULL, 'IN0000', NULL, '1', '150316', '01', NULL, '1', 'CI0000', '交易成功', '20210414', '1', '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210414', '15025725001', '20210414', '150443', '1618383883434');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041415382800003000010001', 'BANKCREDIT', '20210414', '15380835002', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '153828', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '15380835002', '20210414', '153828', '1618385908684');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041415423000003050010001', 'BANKCREDIT', '20210414', '15422340001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '154231', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '15422340001', '20210414', '154231', '1618386151037');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041417225200003100010001', 'BANKCREDIT', '20210414', '17222975005', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '172252', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '17222975005', '20210414', '172252', '1618392172944');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041417345100003100020002', 'BANKCREDIT', '20210414', '17344675006', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '173451', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '17344675006', '20210414', '173451', '1618392891257');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041417415700003100030003', 'BANKCREDIT', '20210414', '17415675007', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '174157', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '17415675007', '20210414', '174157', '1618393317031');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041417553500003100040004', 'BANKCREDIT', '20210414', '17553575008', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '175535', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '17553575008', '20210414', '175535', '1618394135949');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041418165900003200010001', 'BANKCREDIT', '20210414', '18165900001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '181659', '02', '', '1', 'CI0016', '客户名称不一致', '20210414', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210414', '18165900001', '20210414', '181821', '1618395501774');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041418513900003200020002', 'BANKDEBIT', '20210414', '18513900002', '173001', NULL, '173001', '156', '12311', NULL, 'IN0000', NULL, '1', '185139', '01', NULL, '1', 'CI0016', '客户名称不一致', '20210414', '1', '6214622121003305144', '付款人名称', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210414', '18513900002', '20210414', '185152', '1618397512640');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041509450600003300010001', 'BANKCREDIT', '20210415', '09445740006', '173001', NULL, '1', '156', '123145', NULL, 'IN0000', NULL, '1', '094506', '02', '', '1', 'CI0016', '客户名称不一致', '20210415', '1', '6214622121003305144', NULL, NULL, '测试1', NULL, NULL, '20210415', '09445740006', '20210415', '094521', '1618451121315');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041510420900003300020002', 'BANKCREDIT', '20210415', '10420940017', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '104209', '02', '', '1', '000000', '成功', '20210415', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210415', '10420940017', '20210415', '104218', '1618454538888');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041514153000003350010001', 'BANKCREDIT', '20210415', '14151570004', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '141530', '02', '', '1', '000000', '成功', '20210415', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210415', '14151570004', '20210415', '141531', '1618467331256');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041514582900003450010001', 'BANKCREDIT', '20210415', '14582880001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '145829', '02', '', '1', '000000', '成功', '20210415', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210415', '14582880001', '20210415', '150058', '1618470058737');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041515425400003500010001', 'BANKCREDIT', '20210415', '15424275014', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '154254', '02', '', '1', '000000', '成功', '20210415', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210415', '15424275014', '20210415', '154255', '1618472575162');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041517040600003550010001', 'BANKCREDIT', '20210415', '17040585003', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '170406', '02', '', '1', '000000', '成功', '20210415', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210415', '17040585003', '20210415', '170420', '1618477460308');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041518025200003600010001', 'BANKCREDIT', '20210415', '18025100001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '180252', '02', '', '1', 'CI0016', '客户名称不一致', '20210415', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210415', '18025100001', '20210415', '180323', '1618481003995');
INSERT INTO `pay_pay_accflow` VALUES ('20210113', 'ECNY2021041519011200003600020002', 'BANKCREDIT', '20210415', '19011200002', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '190112', '02', '', '1', 'CI0016', '客户名称不一致', '20210415', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210415', '19011200002', '20210415', '190120', '1618484480769');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042310023400004750010001', 'BANKCREDIT', '20210423', '10023400002', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '100234', '02', '', '1', '000000', '成功', '20210423', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210423', '10023400002', '20210423', '100237', '1619143357281');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042315110600004800020002', 'BANKCREDIT', '20210423', '15093005012', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '151106', '02', '', '1', '000000', '成功', '20210423', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210423', '15093005012', '20210423', '151532', '1619162132792');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042316394900004850010001', 'BANKCREDIT', '20210423', '16394860001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '163949', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210423', '16394860001', '20210423', '163949', '1619167189228');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042316411200004850020002', 'BANKCREDIT', '20210423', '16411160002', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '164112', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210423', '16411160002', '20210423', '164112', '1619167272134');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042316421500004850030003', 'BANKCREDIT', '20210423', '16421560003', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '164216', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210423', '16421560003', '20210423', '164216', '1619167336015');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042316503400004850040004', 'BANKCREDIT', '20210423', '16503460004', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '165034', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210423', '16503460004', '20210423', '165034', '1619167834394');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042316543300004900010001', 'BANKCREDIT', '20210423', '16543265001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '165433', '02', '', '1', '000000', '成功', '20210423', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210423', '16543265001', '20210423', '165433', '1619168073867');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042510060400004950010001', 'BANKCREDIT', '20210425', '10060400001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '100605', '02', '', '1', '000000', '成功', '20210425', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210425', '10060400001', '20210425', '100605', '1619316365708');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042609092800005050010001', 'BANKCREDIT', '20210426', '09092840004', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '090928', '02', '', '1', '000000', '成功', '20210426', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210426', '09092840004', '20210426', '090928', '1619399368307');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042609180400005050020002', 'BANKCREDIT', '20210426', '09180440009', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '091804', '02', '', '1', '000000', '成功', '20210426', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210426', '09180440009', '20210426', '091804', '1619399884950');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021042809341600005050030003', 'BANKCREDIT', '20210428', '09341640010', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '093416', '02', '', '1', '000000', '成功', '20210428', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210428', '09341640010', '20210428', '093416', '1619573656777');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021051416514300005100010001', 'BANKCREDIT', '20210514', '16514245001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '165144', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210514', '16514245001', '20210514', '165144', '1620982304493');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021051416553200005100020002', 'BANKCREDIT', '20210514', '16553145002', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '165533', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210514', '16553145002', '20210514', '165533', '1620982533004');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021051510533500005150010001', 'BANKCREDIT', '20210515', '10193350001', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '105335', '02', '', '2', NULL, NULL, NULL, NULL, '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210515', '10193350001', '20210515', '105336', '1621047216106');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021051511042100005150020002', 'BANKCREDIT', '20210515', '11035850002', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '110421', '02', '', '1', '000000', '成功', '20210515', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210515', '11035850002', '20210515', '110501', '1621047901512');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021051511074800005150030003', 'BANKCREDIT', '20210515', '11072750003', '173001', NULL, '173001', '156', '1100', NULL, 'IN0000', NULL, '1', '110748', '02', '', '1', '000000', '成功', '20210515', '1', '101112345678916', '测试钱包', '6214622121002964305', '造数泰伯', NULL, NULL, '20210515', '11072750003', '20210515', '110827', '1621048107443');
INSERT INTO `pay_pay_accflow` VALUES ('20210309', 'ECNY2021051515550400005150040004', 'BANKDEBIT', '20210515', '12592050012', '173001', NULL, '173001', '156', '8900000', NULL, 'IN0000', NULL, '1', '155505', '01', NULL, '1', '000000', '成功', '20210515', '1', '6214622121003509638', '造数滢姬君', '23534645224653442', '收款人钱包名称', NULL, NULL, '20210515', '12592050012', '20210515', '155524', '1621065324890');
INSERT INTO `pay_pay_accflow` VALUES ('20210415', 'ECNY2021041513522800003300030003', 'BANKDEBIT', '20210415', '13513740028', '01234', NULL, '01234', '156', '500.00', NULL, 'IN0000', NULL, '1', '135228', '01', NULL, '1', '000000', '成功', '20210415', '1', '1', '小菊花', '123456', 'yuanxiaohua', NULL, NULL, '20210415', '13513740028', '20210415', '135232', '1618465952912');
INSERT INTO `pay_pay_accflow` VALUES ('20210415', 'ECNY2021041513530600003300040004', 'BANKDEBIT', '20210415', '13525940029', '01234', NULL, '01234', '156', '500', NULL, 'IN0000', NULL, '1', '135306', '01', NULL, '1', '000000', '成功', '20210415', '1', '1', '小菊花', '123456', 'yuanxiaohua', NULL, NULL, '20210415', '13525940029', '20210415', '135310', '1618465990082');
INSERT INTO `pay_pay_accflow` VALUES ('20210415', 'ECNY2021041514560500003400010001', 'BANKDEBIT', '20210415', '14560575003', '01234', NULL, '01234', '156', '500', NULL, 'IN0000', NULL, '1', '145605', '01', NULL, '1', '000000', '成功', '20210415', '1', '1', '小菊花', '123456', 'yuanxiaohua', NULL, NULL, '20210415', '14560575003', '20210415', '145606', '1618469766208');

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
-- Records of pay_pay_notify
-- ----------------------------
INSERT INTO `pay_pay_notify` VALUES ('20210414', '19395900008', '20210313000122184595346246598769', 'dcep.909.001.01', 'G4001011000013', 'C1030644021075', 'PR03', 'DCEPI0005', NULL, NULL, '20210114000122199849129770676545', 'dcep.221.001.01', 'C1010411000013', 'C1030644021075', '1100', 'CNY', NULL, '20210414', '193959', '20210414193959964');
INSERT INTO `pay_pay_notify` VALUES ('20210415', '09501140007', '20210213000190995705123259700000', 'dcep.909.001.01', 'G4001011000013', 'C1030644021075', 'PR03', 'DCEPI0005', NULL, NULL, '20210313000122184595346246598769', 'dcep.221.001.01', 'C1010411000013', 'C1030644021075', '1100', 'CNY', NULL, '20210415', '095011', '20210415095011102');
INSERT INTO `pay_pay_notify` VALUES ('20210415', '15392575010', '20210415000190995705123259900000', 'dcep.909.001.01', 'G4001011000013', 'C1030644021075', 'PR03', 'DCEPI0005', NULL, NULL, '20210414000122199849129770676545', 'dcep.221.001.01', 'C1010411000013', 'C1030644021075', '1100', 'CNY', '测试', '20210415', '153925', '20210415153925596');
INSERT INTO `pay_pay_notify` VALUES ('20210415', '15424275013', '20210415000190995705123259900000', 'dcep.909.001.01', 'G4001011000013', 'C1030644021075', 'PR03', 'DCEPI0005', NULL, NULL, '20210415000122199849129770676546', 'dcep.221.001.01', 'C1010411000013', 'C1030644021075', '1100', 'CNY', '测试', '20210415', '154242', '20210415154242500');

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
-- Records of pay_pay_transdtl
-- ----------------------------
INSERT INTO `pay_pay_transdtl` VALUES ('20210413', '15422340001', '154223', 'R', 'PAYEE', 'CRDT', 'EXPT', 'A', NULL, NULL, '0', '20210113', 'ECNY2021041415423000003050010001', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '20210113000122184595346246598765', '1', NULL, NULL, NULL, NULL, NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '15422340001', '20210425', '104130', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '14031280007', '140312', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYE10001', '原交易不存在', '0', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T14:03:12', '20210414106180114031200375024000', '1', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202103091600', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.221.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14031280007', '20210414', '140312', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '14033780008', '140337', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T14:03:37', '20210414106180114033700375025000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104141500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14033780008', '20210414', '140337', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '14043880009', '140438', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T14:04:38', '20210414106180114043800375026000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104141500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14043880009', '20210414', '140438', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '14295005001', '142950', 'R', 'PAYER', 'DRDT', 'EXPT', '2', NULL, NULL, '2', '20210113', 'ECNY2021041414300000002900010001', NULL, NULL, NULL, NULL, '2021-01-13T20:47:02', '20210113000122532910308590900000', '7', NULL, NULL, NULL, NULL, '20210113000122532910308590900000', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '12311', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '14295005001', '20210414', '143000', NULL, 'rmk');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '14445005004', '144451', 'R', 'PAYER', 'DRDT', 'EXPT', '2', NULL, NULL, '2', '20210113', 'ECNY2021041414451900002900020002', NULL, NULL, NULL, NULL, '2021-01-13T20:47:02', '20210414000122532910308590900000', '7', NULL, NULL, NULL, NULL, '20210414000122532910308590900000', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '12311', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '14445005004', '20210414', '144519', NULL, 'rmk');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '15025725001', '150257', 'R', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041415031600002950010001', '20210414', '1', 'CI0000', '交易成功', '2021-01-13T20:47:02', '20210401000122532910308590900000', '1', 'PR00', '000000', '交易成功', '20210414', '20210401000122532910308590900000', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '12311', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '15025725001', '20210414', '150443', NULL, 'rmk');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '16415260001', '164152', 'R', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '2010101000001260', '7', NULL, NULL, NULL, NULL, '2010101000001260', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'D201', '03001', 'C1030131001288', 'C1086932000016', '8900000', NULL, NULL, 'C1086932000016', '杨超', 'AT00', '6217778899001122', NULL, NULL, NULL, NULL, 'C1030131001288', '杨超', NULL, NULL, 'WLTID00000000000001', '测试钱包', 'WL01', 'WT01', 'SGN0000000000000000000000000000001', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '16415260001', '20210414', '164152', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '16560760005', '165608', 'R', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '2021101000001260', '7', NULL, NULL, NULL, NULL, '2021101000001260', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'D201', '03001', 'C1030131001288', 'C1086932000016', '8900000', NULL, NULL, 'C1086932000016', '杨超', 'AT00', '6217778899001122', NULL, NULL, NULL, NULL, 'C1030131001288', '杨超', NULL, NULL, 'WLTID00000000000001', '测试钱包', 'WL01', 'WT01', 'SGN0000000000000000000000000000001', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '16560760005', '20210414', '165613', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17144670005', '171446', 'R', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '2021181000001280', '7', NULL, NULL, NULL, NULL, '2021181000001280', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03001', 'C1030131001288', 'C1086932000016', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17144670005', '20210414', '171449', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17183370007', '171833', 'R', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '2021181000001290', '7', NULL, NULL, NULL, NULL, '2021181000001290', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03001', 'C1030131001288', 'C1086932000016', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17183370007', '20210414', '171833', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17222770009', '172227', 'R', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '2021181000001220', '7', NULL, NULL, NULL, NULL, '2021181000001220', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03001', 'C1030131001288', 'C1086932000016', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17222770009', '20210414', '172227', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17222975005', '172229', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', NULL, NULL, '2', '20210113', 'ECNY2021041417225200003100010001', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '20210313000122184595346246598765', '7', NULL, NULL, NULL, NULL, NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '17222975005', '20210414', '172253', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17232170011', '172321', 'R', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '2021181000001110', '7', NULL, NULL, NULL, NULL, '2021181000001110', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03001', 'C1030131001288', 'C1086932000016', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17232170011', '20210414', '172321', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17273670012', '172736', 'R', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '20210403000122532910308590900000', '7', NULL, NULL, NULL, NULL, '20210403000122532910308590900000', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03001', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17273670012', '20210414', '172736', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17323670014', '173236', 'R', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '1', '20201010', 'ECNY2021041417325700003150010001', '20210414', '1', 'CI0000', '交易成功', '2020-10-10T09:30:30', '20210405000122532910308590900000', '1', 'PR00', '000000', '交易成功', '20210414', '20210405000122532910308590900000', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17323670014', '20210414', '173317', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17343170015', '173431', 'R', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '1', '20201010', 'ECNY2021041417343600003150020002', '20210414', '1', '000000', '交易成功', '2020-10-10T09:30:30', '20210406000122532910308590900000', '1', 'PR00', '000000', '交易成功', '20210414', '20210406000122532910308590900000', 'B202010101000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17343170015', '20210414', '173437', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17344675006', '173446', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', NULL, NULL, '0', '20210113', 'ECNY2021041417345100003100020002', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '20210313000122184595346246598766', '1', NULL, NULL, NULL, NULL, NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '17344675006', '20210415', '112602', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17415675007', '174156', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', NULL, NULL, '2', '20210113', 'ECNY2021041417415700003100030003', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '20210313000122184595346246598767', '7', NULL, NULL, NULL, NULL, NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '17415675007', '20210414', '174157', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17472990001', '174729', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T17:47:29', '20210414106180117472900385001000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104141800', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '17472990001', '20210414', '174731', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17474290002', '174742', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T17:47:42', '20210414106180117474200385002000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104141800', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '17474290002', '20210414', '174743', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17474490003', '174744', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T17:47:44', '20210414106180117474400385003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104141800', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '17474490003', '20210414', '174744', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17474690004', '174746', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T17:47:46', '20210414106180117474600385004000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104141800', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '17474690004', '20210414', '174746', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '17553575008', '175535', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', NULL, NULL, '2', '20210113', 'ECNY2021041417553500003100040004', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '20210313000122184595346246598768', '7', NULL, NULL, NULL, NULL, NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '17553575008', '20210414', '175535', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '18165900001', '181659', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041418165900003200010001', '20210414', '1', 'CI0016', '客户名称不一致', '2021-01-13T16:49:07', '20210313000122184595346246598769', '1', 'PR03', 'DCEPI0005', '交易成功', '20210414', NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '18165900001', '20210415', '095011', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '18513900002', '185139', 'R', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041418513900003200020002', '20210414', '1', 'CI0016', '客户名称不一致', '2021-01-13T20:47:02', '20210313000122532910308590900001', '1', 'PR00', '000000', '交易成功', '20210414', '20210313000122532910308590900001', 'B202101132000', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '12311', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '18513900002', '20210414', '185154', NULL, 'rmk');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '19001400003', '190014', 'R', 'PAYEE', 'CRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-01-13T20:46:20', '20210414000180137727331885800003', '0', 'PR01', 'ECNYS09999', '原交易不存在', '20210414', '20210414000180137727331885800003', 'B202101132000', NULL, NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08001', 'C1010411000013', 'C1030644021075', '123145', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, 'dcep.227.001.01', NULL, '20210112106122714525999867002000', 'XSJ', NULL, '19001400003', '20210414', '190128', 'DR01_内测环境测试', '贷记调整test');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '21165770016', '211657', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T21:16:57', '20210414106180121165700390001000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104142200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '21165770016', '20210414', '211657', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '21170170017', '211701', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T21:17:01', '20210414106180121170100390002000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104142200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '21170170017', '20210414', '211701', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '21174470018', '211744', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T21:17:44', '20210414106180121174400390003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104142200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '21174470018', '20210414', '211744', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '21180470019', '211805', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T21:18:07', '20210414106180121181200390004000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104142200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '21180470019', '20210414', '211824', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '21190970020', '211910', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T21:19:10', '20210414106180121191500390005000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104142200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '21190970020', '20210414', '211925', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210414', '21262770021', '212627', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-14T21:26:27', '20210414106180121262700390006000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210414', NULL, 'B202104142200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '21262770021', '20210414', '212627', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '09333540005', '093335', 'R', 'PAYEE', 'CRDT', 'SUCC', 'A', 'ECNYS09999', '其他错误', '0', NULL, NULL, NULL, NULL, NULL, NULL, '2021-01-13T20:46:20', '20210315000180137727331885800000', '1', 'PR02', 'ECNYS09999', '其他错误', '20210415', '20210315000180137727331885800000', 'B202101132000', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'E100', '08001', 'C1010411000013', 'C1030644021075', '123145', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, 'dcep.227.001.01', NULL, '20210313000122184595346246598769', 'XSJ', NULL, '09333540005', '20210420', '152635', 'DR01_内测环境测试', '贷记调整test');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '09445740006', '094457', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041509450600003300010001', '20210415', '1', 'CI0016', '客户名称不一致', '2021-01-13T20:46:20', '20210316000180137727331885800000', '1', 'PR00', '000000', '交易成功', NULL, '20210316000180137727331885800000', 'B202101132000', NULL, NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08001', 'C1010411000013', 'C1030644021075', '123145', NULL, NULL, 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', NULL, NULL, 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, 'dcep.227.001.01', NULL, '20210313000122184595346246598769', 'XSJ', NULL, '09445740006', '20210415', '094521', 'DR01_内测环境测试', '贷记调整test');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '10154640012', '101546', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T10:15:46', '20210415106180110154600420003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210415', NULL, 'B202104151100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10154640012', '20210415', '101546', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '10195740013', '101957', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T10:19:57', '20210415106180110195700420004000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210415', NULL, 'B202104151100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10195740013', '20210415', '102328', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '10234540014', '102345', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T10:23:45', '20210415106180110234500420005000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210415', NULL, 'B202104151100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10234540014', '20210415', '102354', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '10253040015', '102530', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T10:25:30', '20210415106180110253000420006000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210415', NULL, 'B202104151100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10253040015', '20210415', '102540', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '10260840016', '102608', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T10:26:08', '20210415106180110260800420007000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210415', NULL, 'B202104151100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10260840016', '20210415', '102623', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '10420940017', '104209', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041510420900003300020002', '20210415', '1', '000000', '成功', '2021-01-13T16:49:07', '20210313090122184595346246598770', '1', 'PR00', '000000', '交易成功', '20210415', NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '10420940017', '20210415', '104218', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '10434640018', '104346', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T10:43:46', '20210415106180110434600420008000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210415', NULL, 'B202104151100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10434640018', '20210415', '104612', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11000240019', '110002', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:00:02', '20210415106180111000200420009000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210415', NULL, 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '11000240019', '20210415', '110122', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11014465017', '110144', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:01:44', '20210415106180111014400440009000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210415', NULL, 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '11014465017', '20210415', '110144', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11021165018', '110211', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:02:11', '20210415106180111021100440010000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210415', NULL, 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '11021165018', '20210415', '110438', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11043865019', '110438', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:04:38', '20210415106180111043800440011000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210415', NULL, 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '11043865019', '20210415', '110540', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11072865020', '110728', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:07:28', '20210415106180111072800440012000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210415', NULL, 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '11072865020', '20210415', '110810', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11081665021', '110816', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:08:16', '20210415106180111081600440013000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210415', NULL, 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '11081665021', '20210415', '110823', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11082565022', '110825', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:08:25', '20210415106180111082500440014000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210415', NULL, 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '11082565022', '20210415', '111251', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11125665023', '111256', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:12:56', '20210415106180111125600440015000', '1', 'PR00', '000000', '交易成功', '20210415', '20210113106040120333044574013001', 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210313000122184595346246598766', 'XSJ', NULL, '11125665023', '20210415', '111350', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11172865024', '111728', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:17:28', '20210415106180111172800440016000', '1', 'PR00', '000000', '交易成功', '20210415', '20210113106040120333044574013001', 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210313000122184595346246598766', 'XSJ', NULL, '11172865024', '20210415', '111740', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11204065025', '112040', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:20:40', '20210415106180111204000440017000', '1', 'PR00', '000000', '交易成功', '20210415', '20210113106040120333044574013001', 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210313000122184595346246598766', 'XSJ', NULL, '11204065025', '20210415', '112052', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '11253840020', '112538', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T11:25:38', '20210415106180111253800420010000', '1', 'PR00', '000000', '交易成功', '20210415', '20210113106040120333044574013001', 'B202104151200', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210313000122184595346246598766', 'XSJ', NULL, '11253840020', '20210415', '112602', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13293840021', '132938', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T13:29:38', '20210415106122713293800420011000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '132938', 'dcep.227.001.01', '1', 'D201', '509638', '101151800', '500.00', NULL, NULL, '509638', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, '101151800', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13293840021', '20210415', '132938', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13295840022', '132958', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T13:29:58', '20210415106122713295800420012000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '132958', 'dcep.227.001.01', '1', 'D201', '509638', '101151800', '500.00', NULL, NULL, '509638', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, '101151800', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13295840022', '20210415', '132958', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13344040023', '133440', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T13:34:40', '20210415106122713344000420013000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '133440', 'dcep.227.001.01', '1', 'D201', 'C1030644021075', 'C1091231000013', '500.00', NULL, NULL, 'C1030644021075', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1091231000013', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13344040023', '20210415', '133440', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13371040024', '133710', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T13:37:10', '20210415106122713371000420014000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '133710', 'dcep.227.001.01', '1', 'D201', 'C1010411000013', 'C1030644021075', '500.00', NULL, NULL, 'C1010411000013', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1030644021075', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13371040024', '20210415', '133710', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13381340025', '133813', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T13:38:13', '20210415106122713381300420015000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '133813', 'dcep.227.001.01', '1', 'D201', 'C1030644021075', 'C1010411000013', '500.00', NULL, NULL, 'C1030644021075', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1010411000013', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13381340025', '20210415', '133813', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13410740026', '134107', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T13:41:07', '20210415106122713410700420016000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '134107', 'dcep.227.001.01', '1', 'D201', 'C1040311005293', 'C1010411000013', '500.00', NULL, NULL, 'C1040311005293', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1010411000013', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13410740026', '20210415', '134107', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13433940027', '134339', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-15T13:43:39', '20210415106122713433900420017000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '134339', 'dcep.227.001.01', '1', 'D201', 'C1030644021075', 'C1010411000013', '500.00', NULL, NULL, 'C1030644021075', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1010411000013', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13433940027', '20210415', '134339', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13513740028', '135137', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '1', '20210415', 'ECNY2021041513522800003300030003', '20210415', '1', '000000', '成功', '2021-04-15T13:51:37', '20210415106122713513700420018000', '7', NULL, NULL, NULL, NULL, NULL, 'B202104151400', '1111', '2222', '15', 'B202104151400', '135137', 'dcep.227.001.01', 'D201', 'D201', 'C1030644021075', 'C1010411000013', '500.00', NULL, NULL, 'C1030644021075', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1010411000013', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13513740028', '20210415', '135232', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '13525940029', '135259', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210415', 'ECNY2021041513530600003300040004', '20210415', '1', '000000', '成功', '2021-04-15T13:52:59', '20210415106122713525900420019000', '1', '1', '000000', '交易成功', '20210415', '20210113106040120333044574013001', 'B202104151400', '1111', '2222', '15', 'B202104151400', '135259', 'dcep.227.001.01', 'D201', 'D201', 'C1030644021075', 'C1010411000013', '500', NULL, NULL, 'C1030644021075', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1010411000013', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '13525940029', '20210415', '135310', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '14560575003', '145605', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210415', 'ECNY2021041514560500003400010001', '20210415', '1', '000000', '成功', '2021-04-15T14:56:05', '20210415106122714560500450003000', '1', '1', '000000', '交易成功', '20210415', '20210113106040120333044574013001', 'B202104151500', '1111', '2222', '15', 'B202104151500', '145605', 'dcep.227.001.01', 'D201', 'D201', 'C1030644021075', 'C1010411000013', '500', NULL, NULL, 'C1030644021075', '小菊花', NULL, '1', NULL, NULL, NULL, NULL, 'C1010411000013', NULL, NULL, NULL, '123456', 'yuanxiaohua', NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSG', NULL, '14560575003', '20210415', '145606', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '14582880001', '145829', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041514582900003450010001', '20210415', '1', '000000', '成功', '2021-01-13T16:49:07', '20210313000122184595346246598771', '1', 'PR00', '000000', '交易成功', '20210415', NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '14582880001', '20210415', '150058', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '15424275014', '154242', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041515425400003500010001', '20210415', '1', '000000', '成功', '2021-01-13T16:49:07', '20210415000122199849129770676546', '1', 'PR03', 'DCEPI0005', '交易成功', NULL, NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '15424275014', '20210415', '154255', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '17040585003', '170405', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041517040600003550010001', '20210415', '1', '000000', '成功', '2021-01-13T16:49:07', '20210113000122184595346246598775', '1', 'PR00', '000000', '交易成功', '20210415', NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '17040585003', '20210415', '170420', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '17060385004', '170603', 'R', 'PAYER', 'INIT', 'SUCC', '0', 'ECNYR10001', '业务类型与业务种类不匹配', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-01-13T19:37:46', '2021011302122542448357248400000', '0', 'PR01', 'ECNYS09999', '业务类型与业务种类不匹配', '20210415', '2021011302122542448357248400000', 'B202101131900', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03001', 'C1010411000013', 'C1030644021075', '12311', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', 'X106202101131643345437576575', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '17060385004', '20210415', '170603', NULL, 'rmk');
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '18025100001', '180251', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041518025200003600010001', '20210415', '1', 'CI0016', '客户名称不一致', '2021-01-13T16:49:07', '20210313000122184595346246598760', '1', 'PR00', '000000', '交易成功', '20210415', NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '18025100001', '20210415', '180324', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210415', '19011200002', '190112', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210113', 'ECNY2021041519011200003600020002', '20210415', '1', 'CI0016', '客户名称不一致', '2021-01-13T16:49:07', '20210313010122184595346246598763', '1', 'PR00', '000000', '交易成功', '20210415', NULL, 'B202101131600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '19011200002', '20210415', '190120', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10151375001', '101513', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:15:13', '20210419106180110151300510001000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10151375001', '20210419', '101514', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10165875002', '101658', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:16:58', '20210419106180110165800510002000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10165875002', '20210419', '101659', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10183775003', '101837', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:18:37', '20210419106180110183700510003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10183775003', '20210419', '101837', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10193775004', '101937', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:19:37', '20210419106180110193700510004000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10193775004', '20210419', '101937', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10193975005', '101939', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:19:39', '20210419106180110193900510005000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10193975005', '20210419', '101939', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10240475006', '102404', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:24:04', '20210419106180110240400510006000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10240475006', '20210419', '102404', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10245275007', '102452', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:24:52', '20210419106180110245200510007000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10245275007', '20210419', '102452', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10250875008', '102508', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:25:08', '20210419106180110250800510008000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10250875008', '20210419', '102508', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10252475009', '102524', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:25:24', '20210419106180110252400510009000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10252475009', '20210419', '102524', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10254375010', '102543', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:25:43', '20210419106180110254300510010000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10254375010', '20210419', '102543', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '10294375011', '102943', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T10:29:43', '20210419106180110294300510011000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210419', NULL, 'B202104191100', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '10294375011', '20210419', '102943', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '13311075013', '133110', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T13:31:10', '20210419106180113311000510012000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191400', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '13311075013', '20210419', '133111', 'OT03_12312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '13314075014', '133140', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T13:31:40', '20210419106180113314000510013000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191400', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '13314075014', '20210419', '133140', 'OT03_12312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '13320475015', '133204', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T13:32:04', '20210419106180113320400510014000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191400', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '13320475015', '20210419', '133204', 'OT01_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '13321975016', '133219', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T13:32:19', '20210419106180113321900510015000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191400', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '13321975016', '20210419', '133219', 'OT01_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '13325775017', '133257', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T13:32:57', '20210419106180113325700510016000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210419', NULL, 'B202104191400', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '13325775017', '20210419', '133257', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '13533075018', '135330', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T13:53:30', '20210419106180113533000510017000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191400', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '13533075018', '20210419', '135330', 'OT01_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '13535275019', '135352', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T13:53:52', '20210419106180113535200510018000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191400', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '13535275019', '20210419', '135352', 'OT01_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '14351195001', '143512', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T14:35:12', '20210419106180114351200530001000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '14351195001', '20210419', '143513', 'OT01_123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '14351995002', '143519', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T14:35:19', '20210419106180114351900530002000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '14351995002', '20210419', '143519', 'OT01_123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '14352695003', '143526', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T14:35:26', '20210419106180114352600530003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '14352695003', '20210419', '143526', 'OT01_123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210419', '14365195004', '143651', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-19T14:36:51', '20210419106180114365100530004000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210419', NULL, 'B202104191500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '14365195004', '20210419', '143651', 'OT01_123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09150195005', '091501', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:15:01', '20210420106180109150100530005000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '09150195005', '20210420', '091502', 'OT02_12312312312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09173995006', '091739', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:17:39', '20210420106180109173900530006000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '09173995006', '20210420', '091740', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09211595007', '092115', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:21:15', '20210420106180109211500530007000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '09211595007', '20210420', '092115', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09225295008', '092252', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:22:52', '20210420106180109225200530008000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '09225295008', '20210420', '092252', 'OT02_test', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09325235001', '093252', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:32:52', '20210420106180109325200550001000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '09325235001', '20210420', '093252', 'OT02_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09331635002', '093316', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:33:16', '20210420106180109331600550002000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '09331635002', '20210420', '093316', 'OT02_12312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09332235003', '093322', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:33:22', '20210420106180109332200550003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '09332235003', '20210420', '093322', 'OT02_12312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09334635004', '093346', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:33:46', '20210420106180109334600550004000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '09334635004', '20210420', '093346', 'OT02_12312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09375135007', '093751', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:37:51', '20210420106180109375100550006000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '09375135007', '20210420', '093751', 'OT02_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '09441035008', '094410', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T09:44:10', '20210420106180109441000550007000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201000', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '09441035008', '20210420', '094411', 'OT01_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14175995020', '141759', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:17:59', '20210420106180114175900530013000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14175995020', '20210420', '141759', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14211395021', '142113', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:21:13', '20210420106180114211300530014000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14211395021', '20210420', '142113', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14380695022', '143806', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:38:06', '20210420106180114380600530015000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14380695022', '20210420', '143806', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14385995023', '143859', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:38:59', '20210420106180114385900530016000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14385995023', '20210420', '144238', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14424295024', '144242', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:42:42', '20210420106180114424200530017000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14424295024', '20210420', '144357', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14440695025', '144406', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:44:06', '20210420106180114440600530018000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '14440695025', '20210420', '144645', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14465595026', '144655', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYS09999', '其他错误', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:46:55', '20210420106180114465500530019000', '9', NULL, NULL, NULL, '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '123145', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210315000180137727331885800000', 'XSJ', NULL, '14465595026', '20210420', '144722', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14495695027', '144956', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYS09999', '其他错误', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:49:56', '20210420106180114495600530020000', '9', NULL, NULL, NULL, '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '123145', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210315000180137727331885800000', 'XSJ', NULL, '14495695027', '20210420', '145025', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '14504195028', '145041', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYS09999', '其他错误', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T14:50:41', '20210420106180114504100530021000', '9', NULL, NULL, NULL, '20210420', NULL, 'B202104201500', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '123145', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210315000180137727331885800000', 'XSJ', NULL, '14504195028', '20210420', '145432', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '15260170001', '152602', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T15:26:02', '20210420106180115260200560001000', '1', 'PR00', '000000', '交易成功', '20210420', '20210113106040120333044574013001', 'B202104201600', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '123145', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '111', '01', '01234', '01234', '11', '222', '22', 'dcep.221.001.01', NULL, '20210315000180137727331885800000', 'XSJ', NULL, '15260170001', '20210420', '152635', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '15341975001', '153419', 'S', 'PAYER', 'DRDT', 'SUCC', '0', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T15:34:19', '20210420106180115341900565001000', '0', NULL, '000000', '交易成功', '20210420', NULL, 'B202104201600', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '15341975001', '20210420', '153419', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '15494080001', '154940', 'S', 'PAYER', 'DRDT', 'SUCC', '0', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T15:49:40', '20210420106180115494100570001000', '0', NULL, '000000', '交易成功', '20210420', NULL, 'B202104201600', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '111', '01', '01234', '01234', '11', '222', '22', NULL, NULL, NULL, 'XSJ', NULL, '15494080001', '20210420', '154945', 'null_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210420', '16442865019', '164428', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20T16:44:28', '20210420106180116442800555003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210420', NULL, 'B202104201700', '1111', '2222', '20210317', '20210113000122532910308590900000', '103524', 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1234', '01', '01234', '01234', '11', '22', '2', NULL, NULL, NULL, 'XSJ', NULL, '16442865019', '20210420', '164428', 'OT01_www', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18002490001', '180026', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:00:26', '20210422106180118002600625001000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18002490001', '20210422', '180029', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18061895001', '180619', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:06:19', '20210422106180118061900630001000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18061895001', '20210422', '180621', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18061895002', '180619', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:06:19', '20210422106180118061900630002000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18061895002', '20210422', '180622', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18082795003', '180827', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:08:27', '20210422106180118082700630003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18082795003', '20210422', '180828', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18121895004', '181218', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:12:18', '20210422106180118121800630004000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18121895004', '20210422', '181218', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18170095005', '181700', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:17:00', '20210422106180118170000630005000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18170095005', '20210422', '181700', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18181795006', '181817', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:18:17', '20210422106180118181700630006000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18181795006', '20210422', '181817', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18184295007', '181842', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10018', '该报文类型不需要进行差错处理', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:18:42', '20210422106180118184200630007000', '0', NULL, 'ECNYE10018', '该报文类型不需要进行差错处理', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18184295007', '20210422', '182231', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18235995008', '182359', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:23:59', '20210422106180118235900630008000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18235995008', '20210422', '182359', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18243995009', '182439', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:24:39', '20210422106180118243900630009000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18243995009', '20210422', '182517', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18252095010', '182520', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:25:20', '20210422106180118252000630010000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18252095010', '20210422', '183118', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18320595011', '183205', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYP20003', '发起机构传输有误', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:32:05', '20210422106180118320500630011000', '0', NULL, 'ECNYP20003', '发起机构传输有误', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210414106180114031200375024000', 'XSJ', NULL, '18320595011', '20210422', '183206', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210422', '18320595012', '183205', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYP20003', '发起机构传输有误', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-22T18:32:05', '20210422106180118320500630012000', '0', NULL, 'ECNYP20003', '发起机构传输有误', '20210422', NULL, 'B202104221900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210414106180114031200375024000', 'XSJ', NULL, '18320595012', '20210422', '183206', 'OT04_123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '10023400002', '100234', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021042310023400004750010001', '20210423', '1', '000000', '成功', '2021-01-13T16:49:07', '20210413000122184595346246598765', '1', 'PR00', '000000', '交易成功', '20210423', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '10023400002', '20210423', '100237', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '15093005012', '150930', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021042315110600004800020002', '20210423', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202104231509011', '1', 'PR00', '000000', '交易成功', '20210423', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '15093005012', '20210423', '151532', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '16394860001', '163948', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', 'ECNYS01005', '网关请求错误', '2', '20210309', 'ECNY2021042316394900004850010001', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202104231639028', '7', 'PR02', 'ECNYS09999', '网关请求错误', '20210423', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '16394860001', '20210423', '163950', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '16411160002', '164111', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', 'ECNYS01005', '网关请求错误', '2', '20210309', 'ECNY2021042316411200004850020002', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202104231641011', '7', 'PR02', 'ECNYS09999', '网关请求错误', '20210423', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '16411160002', '20210423', '164112', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '16421560003', '164215', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', 'ECNYS01005', '网关请求错误', '2', '20210309', 'ECNY2021042316421500004850030003', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202104231642015', '7', 'PR02', 'ECNYS09999', '网关请求错误', '20210423', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '16421560003', '20210423', '164216', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '16503460004', '165034', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', 'ECNYS01005', '网关请求错误', '2', '20210309', 'ECNY2021042316503400004850040004', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202104231650033', '7', 'PR02', 'ECNYS09999', '网关请求错误', '20210423', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '16503460004', '20210423', '165034', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '16543265001', '165432', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021042316543300004900010001', '20210423', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202104231654031', '1', 'PR00', '000000', '交易成功', '20210423', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '16543265001', '20210423', '165433', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '17164665005', '171646', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T17:16:46', '20210423106180117164600680004000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210423', NULL, 'B202104231800', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '17164665005', '20210423', '171646', 'OT04_嗯嗯嗯', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '17210765006', '172107', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T17:21:07', '20210423106180117210700680005000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210423', NULL, 'B202104231800', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '17210765006', '20210423', '172107', 'OT04_3333', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '17240265007', '172402', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T17:24:02', '20210423106180117240200680006000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210423', NULL, 'B202104231800', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '17240265007', '20210423', '172437', 'OT04_3333', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '17442265008', '174422', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T17:44:22', '20210423106180117442200680007000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210423', NULL, 'B202104231800', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '17442265008', '20210423', '174509', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '17490165009', '174901', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T17:49:01', '20210423106180117490100680008000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210423', NULL, 'B202104231800', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '17490165009', '20210423', '180337', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18041365010', '180413', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:04:13', '20210423106180118041300680009000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210423', NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18041365010', '20210423', '180414', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18055165011', '180551', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10003', '当前状态不允许此操作', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:05:51', '20210423106180118055100680010000', '0', NULL, 'ECNYE10003', '当前状态不允许此操作', '20210423', NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18055165011', '20210423', '180551', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18091765012', '180917', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYE10009', '互联互通响应失败', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:09:17', '20210423106180118091700680011000', '9', NULL, NULL, NULL, '20210423', NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '18091765012', '20210423', '180925', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18095665013', '180956', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYE10009', '互联互通响应失败', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:09:56', '20210423106180118095600680012000', '9', NULL, NULL, NULL, '20210423', NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '18095665013', '20210423', '180957', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18100265014', '181002', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYE10009', '互联互通响应失败', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:10:02', '20210423106180118100200680013000', '9', NULL, NULL, NULL, '20210423', NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '18100265014', '20210423', '181003', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18103365015', '181033', 'S', 'PAYER', 'DRDT', 'SUCC', '2', 'ECNYE10009', '互联互通响应失败', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:10:33', '20210423106180118103300680014000', '9', NULL, NULL, NULL, '20210423', NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '18103365015', '20210423', '181103', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18135685001', '181356', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:13:56', '20210423106180118135600695001000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '18135685001', '20210423', '181408', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18141985002', '181419', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:14:19', '20210423106180118141900695002000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '18141985002', '20210423', '181419', 'OT04_sdfasdf', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18144885003', '181448', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:14:48', '20210423106180118144800695003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210423', NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '18144885003', '20210423', '181448', 'OT04_yyy', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210423', '18152885004', '181528', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-23T18:15:28', '20210423106180118152800695004000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104231900', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '18152885004', '20210423', '181529', 'OT04_yyy', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09201290001', '092012', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:20:12', '20210425106180109201200700001000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09201290001', '20210425', '092025', 'OT04_12312312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09212490002', '092124', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:21:24', '20210425106180109212400700002000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09212490002', '20210425', '092124', 'OT04_12312312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09212490003', '092124', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:21:24', '20210425106180109212400700003000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09212490003', '20210425', '092124', 'OT04_12312312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09214190004', '092141', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:21:41', '20210425106180109214100700004000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09214190004', '20210425', '092141', 'OT04_12312312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09233295001', '092333', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:23:33', '20210425106180109233300705001000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09233295001', '20210425', '092339', 'OT04_12312312', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09323995003', '093239', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:32:39', '20210425106180109323900705003000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09323995003', '20210425', '093242', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09364395004', '093643', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:36:43', '20210425106180109364300705004000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09364395004', '20210425', '093644', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '09400095005', '094000', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T09:40:00', '20210425106180109400000705005000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '09400095005', '20210425', '094001', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10041895006', '100418', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:04:18', '20210425106180110041800705006000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10041895006', '20210425', '100605', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10060400001', '100604', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021042510060400004950010001', '20210425', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202104251006004', '1', 'PR00', '000000', '交易成功', '20210425', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '10060400001', '20210425', '100605', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10101495007', '101014', 'S', 'PAYER', 'DRDT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:10:14', '20210425106180110101400705007000', '9', NULL, NULL, NULL, NULL, NULL, 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10101495007', '20210425', '101436', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10161005001', '101610', 'S', 'PAYER', 'DRDT', 'SUCC', '0', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:16:10', '20210425106180110161000710001000', '0', NULL, NULL, NULL, '20210425', '2010101000001261', 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10161005001', '20210425', '101612', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10161705002', '101617', 'S', 'PAYER', 'DRDT', 'SUCC', '0', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:16:17', '20210425106180110161700710002000', '0', NULL, NULL, NULL, '20210425', '2010101000001261', 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10161705002', '20210425', '101618', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10185510001', '101857', 'S', 'PAYER', 'DRDT', 'SUCC', '0', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:18:57', '20210425106180110185700715001000', '0', NULL, NULL, NULL, '20210425', '2010101000001261', 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10185510001', '20210425', '101906', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10194310002', '101943', 'S', 'PAYER', 'DRDT', 'SUCC', '0', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:19:43', '20210425106180110194300715002000', '0', NULL, NULL, NULL, '20210425', '2010101000001261', 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10194310002', '20210425', '102351', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10252610003', '102526', 'S', 'PAYER', 'DRDT', 'SUCC', '0', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:25:26', '20210425106180110252600715003000', '0', NULL, NULL, NULL, '20210425', '2010101000001261', 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10252610003', '20210425', '103818', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10411720001', '104117', 'S', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:41:17', '20210425106180110411700720001000', '1', NULL, '000000', '交易成功', '20210425', '2010101000001261', 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', 'C1030644021075', 'C1010411000013', '1100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'CNY', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, 'dcep.221.001.01', NULL, '20210113000122184595346246598765', 'XSJ', NULL, '10411720001', '20210425', '104131', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10453220002', '104532', 'S', 'PAYER', 'DRDT', 'SUCC', '0', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:45:32', '20210425106180110453200720002000', '0', NULL, '000000', '交易成功', '20210425', NULL, 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '10453220002', '20210425', '104532', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210425', '10461420003', '104614', 'S', 'PAYER', 'DRDT', 'SUCC', '0', '000000', '交易成功', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-25T10:46:14', '20210425106180110461400720003000', '0', NULL, '000000', '交易成功', '20210425', NULL, 'B202104251100', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '10461420003', '20210425', '104614', 'OT04_123123123', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210426', '09092840004', '090928', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021042609092800005050010001', '20210426', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202104260909027', '1', 'PR00', '000000', '交易成功', '20210426', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '09092840004', '20210426', '090928', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210426', '09165040008', '091650', 'S', 'PAYER', 'DRDT', 'SUCC', '0', 'ECNYE10001', '原交易不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-26T09:16:50', '20210426106180109165000735003000', '0', NULL, 'ECNYE10001', '原交易不存在', '20210426', NULL, 'B202104261000', 'ECNY', NULL, NULL, NULL, NULL, 'dcep.801.001.01', 'E100', '08002', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '11', NULL, '173001', '173001', 'ECN', NULL, NULL, NULL, NULL, NULL, 'XSJ', NULL, '09165040008', '20210426', '091650', 'OT04_null', '单笔差错贷记调整');
INSERT INTO `pay_pay_transdtl` VALUES ('20210426', '09180440009', '091804', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021042609180400005050020002', '20210426', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202104260918004', '1', 'PR00', '000000', '交易成功', '20210426', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '09180440009', '20210426', '091804', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210428', '09341640010', '093416', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021042809341600005050030003', '20210428', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202104280934016', '1', 'PR00', '000000', '交易成功', '20210428', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '09341640010', '20210428', '093416', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210514', '16514245001', '165143', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', 'ECNYS09999', '其他错误', '2', '20210309', 'ECNY2021051416514300005100010001', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202105141537038', '7', 'PR02', 'ECNYS09999', '其他错误', '20210514', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '16514245001', '20210514', '165154', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210514', '16553145002', '165531', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', 'ECNYS01005', '网关请求错误', '2', '20210309', 'ECNY2021051416553200005100020002', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202105141655025', '7', 'PR02', 'ECNYS09999', '网关请求错误', '20210514', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '16553145002', '20210514', '165538', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210514', '22493545003', '224935', 'R', 'PAYEE', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202105141656029', '7', NULL, NULL, NULL, NULL, NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '22493545003', '20210514', '224935', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '10193350001', '101934', 'R', 'PAYEE', 'CRDT', 'EXPT', '2', 'ECNYS09999', '其他错误', '2', '20210309', 'ECNY2021051510533500005150010001', NULL, NULL, NULL, NULL, '2021-01-13T16:49:07', '00000000001120010001202105151016025', '7', 'PR02', 'ECNYS09999', '其他错误', '20210515', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '10193350001', '20210515', '110233', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '11035850002', '110358', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021051511042100005150020002', '20210515', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202105151103040', '1', 'PR00', '000000', '交易成功', '20210515', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '11035850002', '20210515', '110501', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '11072750003', '110727', 'R', 'PAYEE', 'CRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021051511074800005150030003', '20210515', '1', '000000', '成功', '2021-01-13T16:49:07', '00000000001120010001202105151107017', '1', 'PR00', '000000', '交易成功', '20210515', NULL, 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.221.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '1100', NULL, NULL, 'C1010411000013', '测试1', NULL, NULL, '101112345678916', '测试钱包', 'WL01', 'WT01', 'C1030644021075', '造数泰伯', 'AT00', '6214622121002964305', NULL, NULL, NULL, NULL, NULL, 'CNY', NULL, NULL, '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSH', NULL, '11072750003', '20210515', '110827', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '11463650004', '114636', 'R', 'PAYER', 'INIT', 'SUCC', '0', 'ECNYS20001', '协议信息不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '20210408000122532910308590900000', '0', 'PR01', 'ECNYS09999', '协议信息不存在', '20210515', '20210408000122532910308590900000', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '11463650004', '20210515', '114731', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '12541550009', '125415', 'R', 'PAYER', 'INIT', 'SUCC', '0', 'ECNYS20001', '协议信息不存在', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '00000000001120010001202105151254005', '0', 'PR01', 'ECNYS09999', '协议信息不存在', '20210515', '00000000001120010001202105151254005', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', '123456', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '12541550009', '20210515', '125511', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '12552350010', '125523', 'R', 'PAYER', 'INIT', 'SUCC', '0', 'ECNYP10001', '付款人信息不匹配', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '00000000001120010001202105151255015', '0', 'PR01', 'ECNYS09999', '付款人信息不匹配', '20210515', '00000000001120010001202105151255015', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003305144', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', 'A1062021042019361000005001', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '12552350010', '20210515', '125709', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '12573150011', '125731', 'R', 'PAYER', 'INIT', 'SUCC', '0', 'ECNYP10001', '付款人信息不匹配', '9', NULL, NULL, NULL, NULL, NULL, NULL, '2020-10-10T09:30:30', '00000000001120010001202105151257028', '0', 'PR01', 'ECNYS09999', '付款人信息不匹配', '20210515', '00000000001120010001202105151257028', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '付款人名称', 'AT03', '6214622121003509638', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', 'A1062021042019361000005001', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '12573150011', '20210515', '125912', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210515', '12592050012', '125920', 'R', 'PAYER', 'DRDT', 'SUCC', '1', '000000', '交易成功', '1', '20210309', 'ECNY2021051515550400005150040004', '20210515', '1', '000000', '成功', '2020-10-10T09:30:30', '00000000001120010001202105151259017', '1', 'PR00', '000000', '交易成功', '20210515', '00000000001120010001202105151259017', 'B202103091600', NULL, NULL, NULL, NULL, NULL, 'dcep.225.001.01', 'C201', '03011', 'C1010411000013', 'C1030644021075', '8900000', NULL, NULL, 'C1030644021075', '造数滢姬君', 'AT03', '6214622121003509638', NULL, NULL, NULL, NULL, 'C1010411000013', '收款人名称', NULL, NULL, '23534645224653442', '收款人钱包名称', 'WL01', 'WT01', 'A1062021042019361000005001', 'CNY', NULL, '173001', '173001', '173001', NULL, NULL, NULL, NULL, NULL, NULL, 'XSF', NULL, '12592050012', '20210515', '155525', NULL, '测试');
INSERT INTO `pay_pay_transdtl` VALUES ('20210527', '15012780002', '150127', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-05-27T15:01:27', '20210527106122715012700765002000', '9', NULL, NULL, NULL, NULL, NULL, 'B202105271600', NULL, NULL, '20210527', '20210527106112115012500765001000', '150127', 'dcep.121.001.01', 'OT00', 'DBIT', 'C1030644021075', 'G4001011000013', '111100', NULL, NULL, 'C2213141256762', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 'CNY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'XSG', NULL, '15012780002', '20210527', '150134', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210527', '15070885002', '150708', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-05-27T15:07:08', '20210527106122715070800770002000', '9', NULL, NULL, NULL, NULL, NULL, 'B202105271600', NULL, NULL, '20210527', '20210527106112115070700770001000', '150708', 'dcep.121.001.01', 'OT00', 'DBIT', 'C1030644021075', 'G4001011000013', '111100', NULL, NULL, 'C2213141256762', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 'CNY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'XSG', NULL, '15070885002', '20210527', '150708', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210527', '15080085004', '150800', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-05-27T15:08:00', '20210527106122715080000770004000', '9', NULL, NULL, NULL, NULL, NULL, 'B202105271600', NULL, NULL, '20210527', '20210527106112115080000770003000', '150800', 'dcep.121.001.01', 'OT00', 'DBIT', 'C1030644021075', 'G4001011000013', '111100', NULL, NULL, 'C2213141256762', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 'CNY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'XSG', NULL, '15080085004', '20210527', '150800', NULL, NULL);
INSERT INTO `pay_pay_transdtl` VALUES ('20210527', '15205785006', '152057', 'S', 'PAYER', 'INIT', 'SUCC', '2', NULL, NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, '2021-05-27T15:20:57', '20210527106122715205700770006000', '9', NULL, NULL, NULL, NULL, NULL, 'B202105271600', NULL, NULL, '20210527', '20210527106112115205700770005000', '152057', 'dcep.121.001.01', 'OT00', 'DBIT', 'C1030644021075', 'G4001011000013', '222200', NULL, NULL, 'C2213141256762', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 'CNY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'XSG', NULL, '15205785006', '20210527', '152057', NULL, NULL);

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
-- Records of pay_pay_transdtl_nonf
-- ----------------------------
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193311235900645001000', '20210423', '112400', '11235920001', 'dcep.933.001.01', 'S', '1', '20210423112400', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '112430');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193311244600645002000', '20210423', '112446', '11244620002', 'dcep.933.001.01', 'S', '1', '20210423112446', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '112446');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193311244900645003000', '20210423', '112449', '11244920003', 'dcep.933.001.01', 'S', '1', '20210423112449', 'C1030644021075', 'G4001011000013', 'OT01', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '112450');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193311252200645004000', '20210423', '112522', '11252220004', 'dcep.933.001.01', 'S', '1', '20210423112522', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '112523');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193314311100675003000', '20210423', '143111', '14311150003', 'dcep.933.001.01', 'S', '1', '20210423143111', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '143112');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193317003500680001000', '20210423', '170036', '17003665002', 'dcep.933.001.01', 'S', '1', '20210423170036', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '170036');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193317011400680002000', '20210423', '170114', '17011465003', 'dcep.933.001.01', 'S', '1', '20210423170114', 'C1030644021075', 'G4001011000013', 'OT01', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '170140');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193317022500680003000', '20210423', '170225', '17022565004', 'dcep.933.001.01', 'S', '1', '20210423170225', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210423', '170258');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210423106193317080900685001000', '20210423', '170810', '17081075001', 'dcep.933.001.01', 'S', '2', '20210423170810', 'C1030644021075', 'G4001011000013', 'OT00', 'PR01', 'ECNYC13011', '互联互通响应结果失败', '11', NULL, NULL, '20210423', '170812');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106040109340200690005000', '20210425', '093402', '09340280014', 'dcep.401.001.01', 'S', '1', '20210425093402', 'C1030644021075', 'C1030644021075', NULL, 'PR00', NULL, NULL, '11', NULL, 'test', '20210425', '093402');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106040115075800725002000', '20210425', '150758', '15075825003', 'dcep.401.001.01', 'S', '1', '20210425150758', 'C1030644021075', 'C1030644021075', NULL, 'PR00', NULL, NULL, '11', NULL, 'qweqwe', '20210425', '150758');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106192009344500690006000', '20210425', '09:34:45', '09344580015', 'dcep.920.001.01', 'S', '0', '20210425093445', 'C1030644021075', 'G4001011000013', NULL, 'PR01', 'ECNYS40001', '获取互联互通响应信息异常', '11', NULL, NULL, '20210425', '093445');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106192009344800690007000', '20210425', '09:34:48', '09344880016', 'dcep.920.001.01', 'S', '0', '20210425093448', 'C1030644021075', 'G4001011000013', NULL, 'PR01', 'ECNYS40001', '获取互联互通响应信息异常', '11', NULL, NULL, '20210425', '093448');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106192015082900725003000', '20210425', '15:08:29', '15082925004', 'dcep.920.001.01', 'S', '1', '20210425150829', 'C1030644021075', 'G4001011000013', NULL, 'PR00', NULL, NULL, '11', NULL, NULL, '20210425', '150829');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106193309285500705002000', '20210425', '092855', '09285595002', 'dcep.933.001.01', 'S', '2', '20210425092855', 'C1030644021075', 'G4001011000013', 'OT00', 'PR01', 'ECNYC13011', '互联互通响应结果失败', '11', NULL, NULL, '20210425', '092856');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106193309333700690004000', '20210425', '093337', '09333780012', 'dcep.933.001.01', 'S', '2', '20210425093337', 'C1030644021075', 'G4001011000013', 'OT01', 'PR01', 'ECNYC13011', '互联互通响应结果失败', '11', NULL, NULL, '20210425', '093337');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106193310515600720004000', '20210425', '105156', '10515620004', 'dcep.933.001.01', 'S', '1', '20210425105156', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210425', '105156');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106193310521700720005000', '20210425', '105217', '10521720005', 'dcep.933.001.01', 'S', '1', '20210425105217', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210425', '105218');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106193310522100720006000', '20210425', '105221', '10522120006', 'dcep.933.001.01', 'S', '1', '20210425105221', 'C1030644021075', 'G4001011000013', 'OT01', 'PR00', NULL, NULL, '11', NULL, NULL, '20210425', '105221');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106193315073400725001000', '20210425', '150735', '15073525001', 'dcep.933.001.01', 'S', '1', '20210425150735', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210425', '150737');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210425106193316553400730001000', '20210425', '165542', '16554130001', 'dcep.933.001.01', 'S', '1', '20210425165542', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210425', '165626');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210426106192009120100735002000', '20210426', '09:12:01', '09120140007', 'dcep.920.001.01', 'S', '1', '20210426091201', 'C1030644021075', 'G4001011000013', NULL, 'PR00', NULL, NULL, '11', NULL, NULL, '20210426', '091201');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210426106193309084700735001000', '20210426', '090847', '09084740003', 'dcep.933.001.01', 'S', '1', '20210426090847', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210426', '090848');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210525106040118191800745001000', '20210525', '181918', '18191865001', 'dcep.401.001.01', 'S', '9', '20210525181918', 'C1030644021075', 'C1030644021075', NULL, 'PR02', NULL, NULL, '11', NULL, '订单', '20210525', '181918');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210525106040118192000745002000', '20210525', '181928', '18192865005', 'dcep.401.001.01', 'S', '0', '20210525181920', 'C1030644021075', 'C1030644021075', NULL, 'PR01', NULL, NULL, '11', NULL, '订单', '20210525', '181928');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210525106193317590200740003000', '20210525', '175902', '17590260005', 'dcep.933.001.01', 'S', '1', '20210525175902', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210525', '175905');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210526106193315065100745004000', '20210526', '150651', '15065165007', 'dcep.933.001.01', 'S', '1', '20210526150651', 'C1030644021075', 'G4001011000013', 'OT01', 'PR00', NULL, NULL, '11', NULL, NULL, '20210526', '150651');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210526106193315070800745005000', '20210526', '150708', '15070865008', 'dcep.933.001.01', 'S', '1', '20210526150708', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210526', '150708');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210526106193315073900745006000', '20210526', '150739', '15073965009', 'dcep.933.001.01', 'S', '1', '20210526150739', 'C1030644021075', 'G4001011000013', 'OT01', 'PR00', NULL, NULL, '11', NULL, NULL, '20210526', '150739');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210526106193315083900745007000', '20210526', '150839', '15083965010', 'dcep.933.001.01', 'S', '1', '20210526150839', 'C1030644021075', 'G4001011000013', 'OT00', 'PR00', NULL, NULL, '11', NULL, NULL, '20210526', '150839');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210527106112114493000755001000', '20210527', '145024', '14502470001', 'dcep.121.001.01', 'S', '2', '20210527145024', 'C1030644021075', 'G4001011000013', NULL, 'PR02', NULL, NULL, '000000', NULL, NULL, '20210527', '145024');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210527106112114575400760001000', '20210527', '145755', '14575575001', 'dcep.121.001.01', 'S', '2', '20210527145755', 'C1030644021075', 'G4001011000013', NULL, 'PR02', NULL, NULL, '000000', NULL, NULL, '20210527', '145755');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210527106112115012500765001000', '20210527', '150126', '15012680001', 'dcep.121.001.01', 'S', '2', '20210527150126', 'C1030644021075', 'G4001011000013', NULL, 'PR02', NULL, NULL, '000000', NULL, NULL, '20210527', '150126');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210527106112115070700770001000', '20210527', '150708', '15070785001', 'dcep.121.001.01', 'S', '2', '20210527150708', 'C1030644021075', 'G4001011000013', NULL, 'PR02', NULL, NULL, '000000', NULL, NULL, '20210527', '150708');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210527106112115080000770003000', '20210527', '150800', '15080085003', 'dcep.121.001.01', 'S', '2', '20210527150800', 'C1030644021075', 'G4001011000013', NULL, 'PR02', NULL, NULL, '000000', NULL, NULL, '20210527', '150800');
INSERT INTO `pay_pay_transdtl_nonf` VALUES ('20210527106112115205700770005000', '20210527', '152057', '15205785005', 'dcep.121.001.01', 'S', '2', '20210527152057', 'C1030644021075', 'G4001011000013', NULL, 'PR02', NULL, NULL, '000000', NULL, NULL, '20210527', '152057');

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
INSERT INTO `pay_sign_signinfo` VALUES ('20210515', '17095950020', '170959', 'A1062021042019361000005001', 'C', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '171940');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '绑卡流水登记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_sign_signinfo_jrn
-- ----------------------------
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210425', '10224600002', '102246', '20210114000143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', '1234', '2510224600003', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114000143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '25', '102246', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210425', '10311515001', '103115', '20210114000143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', '1234', '2510311615002', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114000143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '25', '103116', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210425', '19344435001', '193444', '20210114000143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', '1234', '2519344635002', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114000143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '25', '193446', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210425', '20371635003', '203716', '20210114000143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', '1234', '2520371635004', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114000143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '25', '203716', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210426', '09104440005', '091044', '20210114000143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', '1234', '2609104440006', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114000143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '26', '091044', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '15563850013', '155638', '20210114000143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', '1234', '1515564250014', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114000143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '155645', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '16081750015', '160817', '20210114000143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT01', 'SG01', '1234', '1516385650016', NULL, '1', 'ECNY000000', 'SUCCESS', '20210114000143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '163953', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '16484850017', '164848', '20210114120143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT02', 'SG01', '', NULL, NULL, '0', 'ECNYS09999', '原身份认证交易不存在', '20210114120143363452475859800000', 'PR01', 'PR01', '原身份认证交易不存在', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '165322', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '17025650018', '170256', '20210114120143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT02', 'SG01', '', NULL, NULL, '0', 'ECNYS09999', '原身份认证交易不存在', '20210114120143363452475859800000', 'PR01', 'PR01', '原身份认证交易不存在', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '170337', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '17043650019', '170436', '20210114120143363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT02', 'SG01', '', NULL, NULL, '1', 'ECNY000000', 'SUCCESS', '20210114120143363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '171135', '测试');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '17143650021', '171436', '20210116020243363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT03', 'SG01', 'A1062021042013554500000001', NULL, NULL, '0', 'ECNYS09999', '原签约交易不存在', '20210116020243363452475859800000', 'PR01', 'PR01', '原签约交易不存在', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '171553', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '17163850022', '171638', '20210116020243363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT03', 'SG01', 'A1062021042013554500000001', NULL, NULL, '0', 'ECNYS09999', '原签约交易不存在', '20210116020243363452475859800000', 'PR01', 'PR01', '原签约交易不存在', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '171746', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '17180650023', '171806', '20210116020243363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT03', 'SG01', 'A1062021042013554500000001', NULL, NULL, '0', 'ECNYS09999', '原签约交易不存在', '20210116020243363452475859800000', 'PR01', 'PR01', '原签约交易不存在', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '171852', '备注');
INSERT INTO `pay_sign_signinfo_jrn` VALUES ('20210515', '17191850024', '171918', '20210116020243363452475859800000', 'C1010411000013', 'C1030644021075', 'RECV', 'MT03', 'SG01', 'A1062021042019361000005001', NULL, NULL, '1', 'ECNY000000', 'SUCCESS', '20210116020243363452475859800000', 'PR00', 'PR00', '成功', 'C1030644021075', 'AT01', '6214622121003509638', '造数滢姬君', 'IT01', '65420219740712927X', '18948872074', 'C1010411000013', '235347657567556', 'WT01', 'WL01', NULL, '15', '172009', '备注');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器对象表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度日历信息表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器cron表达式表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME066550ed-f2ca-445c-9869-dd31fe361011', 'DEFAULT', '0/2 * * * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME11bce381-2fd0-44ef-bc6f-5c24166b512d', 'DEFAULT', '0/2 * * * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME49ef9c73-b619-471d-80e4-826a26904da6', 'DEFAULT', '0 0 1 * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME4ad290aa-9fbe-4df0-999d-873f98b5959e', 'DEFAULT', '0/2 * * * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME507265cf-503c-4ac2-ae67-cdcc2ff8443c', 'DEFAULT', '0/2 * * * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME56a4ac39-9db4-442d-be1a-a8d9bdd25667', 'DEFAULT', '0/2 * * * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEaa1bbdb5-4457-47fc-80d9-cd840e73f73c', 'DEFAULT', '0/2 * * * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEb74d3599-ff7a-4841-bf28-373861e6794f', 'DEFAULT', '0 0 16 * * ? *', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEc28e43d6-58de-439c-94ea-85ae5a3523bc', 'DEFAULT', '0/17 * * * * ?', 'Asia/Hong_Kong');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEf63fd82d-ce0a-4e40-9c9a-85d6246409d5', 'DEFAULT', '0/2 * * * * ?', 'Asia/Hong_Kong');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器执行信息表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME066550ed-f2ca-445c-9869-dd31fe361011', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178B112266078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178B11226607874002438353438646634652D373238622D343632342D386262322D35376562336638323937363374002432363636626433302D383634632D313165622D613034302D39633563386533383332363974001372795461736B2E72794E6F506172616D73282974000744454641554C5474002430363635353065642D663263612D343435632D393836392D646433316665333631303131740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC89740001317400013374000D302F32202A202A202A202A203F74000130737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057371007E001F0000000174000131740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME11bce381-2fd0-44ef-bc6f-5c24166b512d', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178B11EF7A078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178B11EF7A07874002435383735306265632D333266322D346366612D613933652D37626663636266363138633474002432363636626433302D383634632D313165622D613034302D39633563386533383332363974001372795461736B2E72794E6F506172616D73282974000744454641554C5474002431316263653338312D326664302D343465662D626336662D356332343136366235313264740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC89740001317400013374000D302F32202A202A202A202A203F74000130737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057371007E001F0000000174000131740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME49ef9c73-b619-471d-80e4-826a26904da6', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178498358C078707400007070707400013174000B3020302031202A202A203F70707074002A6463776C7450617942617463685461736B2E737461746973746963732827797979792D4D4D2D6464272974000744454641554C5474002434396566396337332D623631392D343731642D383065342D383236613236393034646136740012E694AFE4BB98E68AA5E8A1A8E7949FE68890740001307400013374000E3020302F3130202A202A202A203F70737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057074000130740001307800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME4ad290aa-9fbe-4df0-999d-873f98b5959e', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178B1281F6078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178B1281F607874002431646536343736382D626535312D346361312D396462372D35623936333535363633633974002432363636626433302D383634632D313165622D613034302D39633563386533383332363974001372795461736B2E72794E6F506172616D73282974000744454641554C5474002434616432393061612D396662652D346466302D393939642D383733663938623539353965740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC89740001317400013374000D302F32202A202A202A202A203F74000130737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057371007E001F0000000274000131740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME507265cf-503c-4ac2-ae67-cdcc2ff8443c', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178B1373A9078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178B1373A907874002439393734323064622D616139612D343839312D616264662D34363465383134326638616674002432363636626433302D383634632D313165622D613034302D39633563386533383332363974001372795461736B2E72794E6F506172616D73282974000744454641554C5474002435303732363563662D353033632D346163322D616536372D636463633266663834343363740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC89740001317400013374000D302F32202A202A202A202A203F74000130737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057371007E001F0000000274000131740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME56a4ac39-9db4-442d-be1a-a8d9bdd25667', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178B11E0D4078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178B11E0D407874002437303833316564392D353562342D346262312D623562332D35373335623961663736333274002432363636626433302D383634632D313165622D613034302D39633563386533383332363974001372795461736B2E72794E6F506172616D73282974000744454641554C5474002435366134616333392D396462342D343432642D626531612D613864396264643235363637740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC89740001317400013374000D302F32202A202A202A202A203F74000130737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057371007E001F0000000274000131740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEaa1bbdb5-4457-47fc-80d9-cd840e73f73c', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178B12CB34078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178B12CB3407874002436386633366261622D393838362D346533632D393232302D33313263353433366133383674002432363636626433302D383634632D313165622D613034302D39633563386533383332363974001372795461736B2E72794E6F506172616D73282974000744454641554C5474002461613162626462352D343435372D343766632D383064392D636438343065373366373363740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC89740001317400013374000D302F32202A202A202A202A203F74000130737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057371007E001F0000000674000131740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEb74d3599-ff7a-4841-bf28-373861e6794f', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178CA02DEA878707400007070707400013174000E302030203136202A202A203F202A7070707400286463776C7450617942617463685461736B2E636865636B4461746128247B797979794D4D64647D2974000744454641554C5474002462373464333539392D666637612D343834312D626632382D33373338363165363739346674000CE68ABDE695B0E4BBBBE58AA1740001307400013374000F30203020302F31202A202A203F202A70737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000017074000130740001307800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEc28e43d6-58de-439c-94ea-85ae5a3523bc', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178CA2055A078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178CA2059887874002434313262326662662D313834322D346137632D626461392D36663263363138303663643974002462373464333539392D666637612D343834312D626632382D3337333836316536373934667400276463776C7450617942617463685461736B2E636865636B4461746128273230323130343133272974000744454641554C5474002463323865343364362D353864652D343339632D393465612D38356165356133353233626374000CE68ABDE695B0E4BBBBE58AA1740001317400013374000E302F3137202A202A202A202A203F74000131737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000171007E002174000131740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEf63fd82d-ce0a-4e40-9c9a-85d6246409d5', 'DEFAULT', NULL, 'com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E64636974732E6463776C742E6A6F622E646F6D61696E2E5379734A6F6200000000000000010200114C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C00086661696C54696D657400104C6A6176612F7574696C2F446174653B4C000366696471007E00094C0006666A6F62496471007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F62496471007E00094C00076A6F624E616D6571007E00094C00076A6F625479706571007E00094C000D6D697366697265506F6C69637971007E00094C0009726574727943726F6E71007E00094C000E72657472794A6F6253746174757371007E00094C000B72657472794D61784E756D7400134C6A6176612F6C616E672F496E74656765723B4C000872657472794E756D71007E000B4C000B726574727953746174757371007E00094C000673746174757371007E000978720031636F6D2E64636974732E6463776C742E636F6D6D6F6E2E636F72652E7765622E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D6571007E000A4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000178B12E12D078707400007070707400013174000E302F3330202A202A202A202A203F7371007E000F770800000178B12E12D07874002437393830393363312D396533382D343266362D383437312D34616530636536306263363874002432363636626433302D383634632D313165622D613034302D39633563386533383332363974001372795461736B2E72794E6F506172616D73282974000744454641554C5474002466363366643832642D636530612D346534302D396339612D383564363234363430396435740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC89740001317400013374000D302F32202A202A202A202A203F74000130737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000057371007E001F0000000374000131740001317800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器暂停信息表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度集群信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RuoyiScheduler', 'localhost.localhost1622102593094', 1622109406224, 15000);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器基本信息表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器参数表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME066550ed-f2ca-445c-9869-dd31fe361011', 'DEFAULT', 'TASK_CLASS_NAME066550ed-f2ca-445c-9869-dd31fe361011', 'DEFAULT', NULL, 1622102604000, -1, 5, 'PAUSED', 'CRON', 1622102604000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME11bce381-2fd0-44ef-bc6f-5c24166b512d', 'DEFAULT', 'TASK_CLASS_NAME11bce381-2fd0-44ef-bc6f-5c24166b512d', 'DEFAULT', NULL, 1622102604000, -1, 5, 'PAUSED', 'CRON', 1622102604000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME49ef9c73-b619-471d-80e4-826a26904da6', 'DEFAULT', 'TASK_CLASS_NAME49ef9c73-b619-471d-80e4-826a26904da6', 'DEFAULT', NULL, 1622134800000, -1, 5, 'WAITING', 'CRON', 1622102603000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME4ad290aa-9fbe-4df0-999d-873f98b5959e', 'DEFAULT', 'TASK_CLASS_NAME4ad290aa-9fbe-4df0-999d-873f98b5959e', 'DEFAULT', NULL, 1622102604000, -1, 5, 'PAUSED', 'CRON', 1622102604000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME507265cf-503c-4ac2-ae67-cdcc2ff8443c', 'DEFAULT', 'TASK_CLASS_NAME507265cf-503c-4ac2-ae67-cdcc2ff8443c', 'DEFAULT', NULL, 1622102604000, -1, 5, 'PAUSED', 'CRON', 1622102603000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME56a4ac39-9db4-442d-be1a-a8d9bdd25667', 'DEFAULT', 'TASK_CLASS_NAME56a4ac39-9db4-442d-be1a-a8d9bdd25667', 'DEFAULT', NULL, 1622102604000, -1, 5, 'PAUSED', 'CRON', 1622102604000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEaa1bbdb5-4457-47fc-80d9-cd840e73f73c', 'DEFAULT', 'TASK_CLASS_NAMEaa1bbdb5-4457-47fc-80d9-cd840e73f73c', 'DEFAULT', NULL, 1622102604000, -1, 5, 'PAUSED', 'CRON', 1622102604000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEb74d3599-ff7a-4841-bf28-373861e6794f', 'DEFAULT', 'TASK_CLASS_NAMEb74d3599-ff7a-4841-bf28-373861e6794f', 'DEFAULT', NULL, 1622188800000, -1, 5, 'WAITING', 'CRON', 1622102603000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEc28e43d6-58de-439c-94ea-85ae5a3523bc', 'DEFAULT', 'TASK_CLASS_NAMEc28e43d6-58de-439c-94ea-85ae5a3523bc', 'DEFAULT', NULL, 1622102614000, -1, 5, 'PAUSED', 'CRON', 1622102603000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAMEf63fd82d-ce0a-4e40-9c9a-85d6246409d5', 'DEFAULT', 'TASK_CLASS_NAMEf63fd82d-ce0a-4e40-9c9a-85d6246409d5', 'DEFAULT', NULL, 1622102604000, -1, 5, 'PAUSED', 'CRON', 1622102604000, 0, NULL, 2, '');

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
INSERT INTO `segment_allocator` VALUES ('agreement-flowno', 10001, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-04-20 19:36:11');
INSERT INTO `segment_allocator` VALUES ('corereq-flowno', 525001, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-05-15 17:47:25');
INSERT INTO `segment_allocator` VALUES ('flowno', 30001, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-05-15 15:56:42');
INSERT INTO `segment_allocator` VALUES ('msg-flowno', 775001, 5000, 99999999, 1, '2021-03-05 16:35:26', '2021-05-27 15:07:08');
INSERT INTO `segment_allocator` VALUES ('platform-flowno', 90001, 5000, 99999, 1, '2021-03-05 16:35:26', '2021-05-27 15:07:08');

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
) ENGINE = InnoDB AUTO_INCREMENT = 178 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_dict_data` VALUES (100, 1, '发送', 'S', 'drct', NULL, NULL, 'N', '0', 'admin', '2021-03-03 14:44:39', '', NULL, '发送');
INSERT INTO `sys_dict_data` VALUES (101, 2, '接收', 'R', 'drct', NULL, NULL, 'N', '0', 'admin', '2021-03-03 14:45:25', '', NULL, '接收');
INSERT INTO `sys_dict_data` VALUES (102, 1, '运营机构', 'PT00', 'partytype', NULL, NULL, 'N', '0', 'admin', '2021-03-03 15:14:02', '', NULL, '运营机构');
INSERT INTO `sys_dict_data` VALUES (103, 2, '运营机构-非银行机构', 'PT01', 'partytype', NULL, NULL, 'N', '0', 'admin', '2021-03-03 15:14:42', '', NULL, '运营机构-非银行机构');
INSERT INTO `sys_dict_data` VALUES (105, 3, '非运管机构-合作银行', 'PT02', 'partytype', NULL, NULL, 'N', '0', 'admin', '2021-03-03 15:16:10', '', NULL, '非运管机构-合作银行');
INSERT INTO `sys_dict_data` VALUES (106, 4, '非运营机构-代理清算机构', 'PT03', 'partytype', NULL, NULL, 'N', '0', 'admin', '2021-03-03 15:29:48', '', NULL, '非运营机构-代理清算机构');
INSERT INTO `sys_dict_data` VALUES (107, 1, '对平', 'SAME', 'checkstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 14:54:55', '', NULL, '对平');
INSERT INTO `sys_dict_data` VALUES (108, 2, '行内大额多', 'MORE', 'checkstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 14:55:13', '', NULL, '行内大额多');
INSERT INTO `sys_dict_data` VALUES (109, 3, '核对报文多', 'LESS', 'checkstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 14:55:49', 'admin', '2021-04-20 19:39:03', '核对报文多');
INSERT INTO `sys_dict_data` VALUES (110, 4, '要素不符', 'DIFF', 'checkstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 14:56:11', '', NULL, '要素不符');
INSERT INTO `sys_dict_data` VALUES (111, 5, '状态不符，即异常', 'EXPT', 'checkstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 14:56:31', '', NULL, '状态不符，即异常');
INSERT INTO `sys_dict_data` VALUES (112, 6, '未对账', 'INIT', 'checkstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 14:56:55', '', NULL, '未对账');
INSERT INTO `sys_dict_data` VALUES (113, 1, '成功', 'PR00', 'msgbizstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 15:09:32', '', NULL, '成功');
INSERT INTO `sys_dict_data` VALUES (114, 2, '失败', 'PR01', 'msgbizstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 15:09:45', '', NULL, '失败');
INSERT INTO `sys_dict_data` VALUES (115, 3, '处理中', 'PR02', 'msgbizstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 15:10:05', '', NULL, '处理中');
INSERT INTO `sys_dict_data` VALUES (116, 4, '推定成功', 'PR03', 'msgbizstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 15:10:24', '', NULL, '推定成功');
INSERT INTO `sys_dict_data` VALUES (117, 5, '推定失败', 'PR04', 'msgbizstatus', NULL, NULL, 'N', '0', 'admin', '2021-03-09 15:10:49', '', NULL, '推定失败');
INSERT INTO `sys_dict_data` VALUES (120, 0, '成功', '0', 'sys_excute_status', NULL, NULL, 'N', '0', 'admin', '2021-04-09 16:06:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (121, 0, '失败', '1', 'sys_excute_status', NULL, NULL, 'N', '0', 'admin', '2021-04-09 16:07:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (122, 0, '全局', '0', 'param_type', NULL, NULL, 'N', '0', 'admin', '2021-04-15 09:17:56', '', NULL, '参数全局使用');
INSERT INTO `sys_dict_data` VALUES (123, 1, '数字钱包', '1', 'param_type', NULL, NULL, 'N', '0', 'admin', '2021-04-15 09:18:27', '', NULL, '数字钱包');
INSERT INTO `sys_dict_data` VALUES (124, 0, '可用', '0', 'param_status', NULL, NULL, 'N', '0', 'admin', '2021-04-15 09:20:31', '', NULL, '可用');
INSERT INTO `sys_dict_data` VALUES (125, 1, '不可用', '1', 'param_status', NULL, NULL, 'N', '0', 'admin', '2021-04-15 09:20:51', '', NULL, '不可用');
INSERT INTO `sys_dict_data` VALUES (126, 0, '交易资金无法入账', 'DR01', 'DR', NULL, NULL, 'N', '0', 'admin', '2021-04-15 17:44:58', '', NULL, '交易资金无法入账');
INSERT INTO `sys_dict_data` VALUES (127, 0, '交易失败被扣款', 'DR02', 'DR', NULL, NULL, 'N', '0', 'admin', '2021-04-15 17:45:50', '', NULL, '交易失败被扣款');
INSERT INTO `sys_dict_data` VALUES (128, 0, '交易成功未扣款', 'DR03', 'DR', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:03:37', '', NULL, '交易成功未扣款');
INSERT INTO `sys_dict_data` VALUES (129, 0, '交易失败资金到账', 'DR04', 'DR', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:04:00', '', NULL, '交易失败资金到账');
INSERT INTO `sys_dict_data` VALUES (130, 0, '交易成功资金未到账', 'DR05', 'DR', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:04:34', '', NULL, '交易成功资金未到账');
INSERT INTO `sys_dict_data` VALUES (131, 0, '账务金额与交易金额不符', 'DR06', 'DR', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:05:06', '', NULL, '账务金额与交易金额不符');
INSERT INTO `sys_dict_data` VALUES (132, 0, '贷记调账失误', 'DR07', 'DR', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:05:40', '', NULL, '贷记调账失误');
INSERT INTO `sys_dict_data` VALUES (133, 0, '单笔核心回查', 'OT01', 'OT', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:07:06', '', NULL, '单笔核心回查');
INSERT INTO `sys_dict_data` VALUES (134, 0, '单笔核心冲正', 'OT02', 'OT', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:07:58', '', NULL, '单笔核心冲正');
INSERT INTO `sys_dict_data` VALUES (135, 0, '单笔核心补入账', 'OT03', 'OT', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:08:31', '', NULL, '单笔核心补入账');
INSERT INTO `sys_dict_data` VALUES (136, 0, '单笔差错贷记调整', 'OT04', 'OT', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:09:09', '', NULL, '单笔差错贷记调整');
INSERT INTO `sys_dict_data` VALUES (137, 0, '对账批次差错', 'OT05', 'OT', NULL, NULL, 'N', '0', 'admin', '2021-04-15 19:09:33', '', NULL, '对账批次差错');
INSERT INTO `sys_dict_data` VALUES (138, 0, '发送', 'SEND', 'direct', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:05:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (139, 0, '接收', 'RECV', 'direct', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:05:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (140, 0, '签约状态', 'N', 'signStatus', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:10:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (141, 0, '解约状态', 'C', 'signStatus', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:10:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (142, 0, '个人钱包', 'WT01', 'wallettype', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:17:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (143, 0, '子个人钱包', 'WT02', 'wallettype', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:18:10', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (144, 0, '硬件钱包', 'WT03', 'wallettype', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:18:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (145, 0, '对公钱包', 'WT09', 'wallettype', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:18:35', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (146, 0, '子对公钱包', 'WT10', 'wallettype', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:18:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (147, 0, '一类钱包', 'WL01', 'walletlevel', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:20:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (148, 0, '二类钱包', 'WL02', 'walletlevel', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:20:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (149, 0, '三类钱包', 'WL03', 'walletlevel', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:20:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (150, 0, '四类钱包', 'WL04', 'walletlevel', NULL, NULL, 'N', '0', 'admin', '2021-04-16 14:20:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (151, 0, '身份认证', 'MT01', 'manageType', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:50:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (152, 0, '身份确认', 'MT02', 'manageType', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:50:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (153, 0, '解约申请', 'MT03', 'manageType', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:50:21', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (154, 0, '不签约', 'SG00', 'signType', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:51:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (155, 0, '签约', 'SG01', 'signType', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:52:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (156, 0, '失败', '0', 'trxStatus', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:53:07', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (157, 0, '成功', '1', 'trxStatus', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:53:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (158, 0, '处理中', '2', 'trxStatus', NULL, NULL, 'N', '0', 'admin', '2021-04-16 15:53:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (159, 1, '登录', 'OT00', 'LoginOperationTpCdEnum', NULL, NULL, 'N', '0', 'admin', '2021-04-20 14:46:17', 'admin', '2021-04-25 16:19:15', NULL);
INSERT INTO `sys_dict_data` VALUES (160, 2, '退出', 'OT01', 'LoginOperationTpCdEnum', NULL, NULL, 'N', '0', 'admin', '2021-04-20 14:46:33', 'admin', '2021-04-25 16:19:33', NULL);
INSERT INTO `sys_dict_data` VALUES (161, 1, '成功', 'PR00', 'PR', NULL, NULL, 'N', '0', 'admin', '2021-04-20 17:27:49', 'admin', '2021-04-25 16:25:38', '成功');
INSERT INTO `sys_dict_data` VALUES (162, 2, '失败', 'PR01', 'PR', NULL, NULL, 'N', '0', 'admin', '2021-04-20 17:28:01', 'admin', '2021-04-25 16:25:49', '失败');
INSERT INTO `sys_dict_data` VALUES (163, 2, '登录成功', 'OT00', 'OT0', NULL, NULL, 'N', '0', 'admin', '2021-04-20 18:58:33', 'admin', '2021-04-25 16:23:03', '登录成功');
INSERT INTO `sys_dict_data` VALUES (164, 1, '退出', 'OT01', 'OT0', NULL, NULL, 'N', '0', 'admin', '2021-04-20 18:58:59', 'admin', '2021-04-25 16:23:14', '退出');
INSERT INTO `sys_dict_data` VALUES (165, 0, '设置故障', 'ST00', 'ST', NULL, NULL, 'N', '0', 'admin', '2021-04-20 19:11:02', '', NULL, '设置故障');
INSERT INTO `sys_dict_data` VALUES (166, 0, '恢复运行', 'ST01', 'ST', NULL, NULL, 'N', '0', 'admin', '2021-04-20 19:11:28', '', NULL, '恢复运行');
INSERT INTO `sys_dict_data` VALUES (167, 0, '已登录', 'ST02', 'ST', NULL, NULL, 'N', '0', 'admin', '2021-04-20 19:11:48', '', NULL, '已登录');
INSERT INTO `sys_dict_data` VALUES (168, 0, '已退出', 'ST03', 'ST', NULL, NULL, 'N', '0', 'admin', '2021-04-20 19:12:04', '', NULL, '已退出');
INSERT INTO `sys_dict_data` VALUES (169, 0, '强制退出', 'ST04', 'ST', NULL, NULL, 'N', '0', 'admin', '2021-04-20 19:12:21', '', NULL, '强制退出');
INSERT INTO `sys_dict_data` VALUES (170, 0, '签名证书', 'CS00', 'certificate_type_code', NULL, NULL, 'N', '0', 'admin', '2021-04-25 19:46:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (171, 0, '加密证书', 'CS01', 'certificate_type_code', NULL, NULL, 'N', '0', 'admin', '2021-04-25 19:47:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (172, 0, '新增', 'CC00', 'certificate_change_code', NULL, NULL, 'N', '0', 'admin', '2021-04-25 22:52:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (173, 0, '撤销', 'CC02', 'certificate_change_code', NULL, NULL, 'N', '0', 'admin', '2021-04-25 22:53:09', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (174, 0, '是', '1', 'automatic_stuts', NULL, NULL, 'N', '0', 'admin', '2021-05-25 16:56:16', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (175, 1, '否', '0', 'automatic_stuts', NULL, NULL, 'N', '0', 'admin', '2021-05-25 16:56:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (176, 0, '钱柜入库', 'OT00', 'oprTp_stuts', NULL, NULL, 'N', '0', 'admin', '2021-05-26 10:27:17', '', NULL, '钱柜入库');
INSERT INTO `sys_dict_data` VALUES (177, 1, '钱柜出库', 'OT01', 'oprTp_stuts', NULL, NULL, 'N', '0', 'admin', '2021-05-26 10:27:40', '', NULL, '钱柜出库');

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
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_dict_type` VALUES (100, '报文方向', 'drct', '0', 'admin', '2021-03-03 14:42:22', '', NULL, '报文方向列表');
INSERT INTO `sys_dict_type` VALUES (101, '机构类型', 'partytype', '0', 'admin', '2021-03-03 15:12:43', '', NULL, '机构类型列表');
INSERT INTO `sys_dict_type` VALUES (102, '对账标识', 'checkstatus', '0', 'admin', '2021-03-09 14:53:47', '', NULL, '对账标识');
INSERT INTO `sys_dict_type` VALUES (103, '业务状态', 'msgbizstatus', '0', 'admin', '2021-03-09 15:04:41', '', NULL, '业务状态');
INSERT INTO `sys_dict_type` VALUES (104, '执行状态', 'sys_excute_status', '0', 'admin', '2021-04-08 18:52:25', '', NULL, '0成功 1失败');
INSERT INTO `sys_dict_type` VALUES (105, '数字钱包参数类型', 'param_type', '0', 'admin', '2021-04-15 09:15:04', 'admin', '2021-04-15 09:23:47', '0全局 1数字钱包');
INSERT INTO `sys_dict_type` VALUES (106, '数字钱包参数配置状态', 'param_status', '0', 'admin', '2021-04-15 09:20:11', '', NULL, '0可用 1不可用');
INSERT INTO `sys_dict_type` VALUES (107, '差错贷记调整原因码', 'DR', '0', 'admin', '2021-04-15 17:44:23', '', NULL, '差错贷记调整原因码');
INSERT INTO `sys_dict_type` VALUES (108, '差错类型', 'OT', '0', 'admin', '2021-04-15 19:06:23', '', NULL, '差错类型');
INSERT INTO `sys_dict_type` VALUES (109, '往来方向', 'direct', '0', 'admin', '2021-04-16 14:04:13', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (110, '协议状态', 'signStatus', '0', 'admin', '2021-04-16 14:10:28', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (111, '钱包类型', 'wallettype', '0', 'admin', '2021-04-16 14:16:47', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (112, '钱包等级', 'walletlevel', '0', 'admin', '2021-04-16 14:19:52', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (113, '管理类型', 'manageType', '0', 'admin', '2021-04-16 15:49:45', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (114, '签约类型', 'signType', '0', 'admin', '2021-04-16 15:51:34', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (116, '业务处理状态', 'trxStatus', '0', 'admin', '2021-04-16 15:52:47', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (117, '登录退出操作类型', 'LoginOperationTpCdEnum', '0', 'admin', '2021-04-20 14:45:50', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (118, '业务处理状态', 'PR', '0', 'admin', '2021-04-20 17:27:07', 'admin', '2021-04-20 18:15:21', 'PR00成功，PR01失败');
INSERT INTO `sys_dict_type` VALUES (121, 'login', 'OT0', '0', 'admin', '2021-04-20 18:58:12', '', NULL, '    OT00(\"OT00\",\"登录\"),\n    OT01(\"OT01\",\"退出\")');
INSERT INTO `sys_dict_type` VALUES (122, '状态枚举类型', 'ST', '0', 'admin', '2021-04-20 19:10:33', '', NULL, '    ST00(\"ST00\",\"设置故障\"),\n    ST01(\"ST01\",\"恢复运行\"),\n    ST02(\"ST02\",\"已登录\"),\n    ST03(\"ST03\",\"已退出\"),\n    ST04(\"ST04\",\"强制退出\")\n');
INSERT INTO `sys_dict_type` VALUES (123, '证书类型', 'certificate_type_code', '0', 'admin', '2021-04-25 19:46:07', 'admin', '2021-04-25 19:48:39', NULL);
INSERT INTO `sys_dict_type` VALUES (124, '证书状态', 'certificate_change_code', '0', 'admin', '2021-04-25 19:48:26', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (125, '是否自动入库', 'automatic_stuts', '0', 'admin', '2021-05-25 16:55:50', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (126, '钱柜出入库', 'oprTp_stuts', '0', 'admin', '2021-05-26 10:26:42', '', NULL, NULL);

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
INSERT INTO `sys_job` VALUES ('066550ed-f2ca-445c-9869-dd31fe361011', '8548df4e-728b-4624-8bb2-57eb3f829763', '2666bd30-864c-11eb-a040-9c5c8e383269', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '1', '2021-04-08 18:41:00', '0', 1, 5, 'admin', '2021-04-08 18:41:00', '', '2021-04-08 18:41:00', '');
INSERT INTO `sys_job` VALUES ('11bce381-2fd0-44ef-bc6f-5c24166b512d', '58750bec-32f2-4cfa-a93e-7bfccbf618c4', '2666bd30-864c-11eb-a040-9c5c8e383269', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '1', '2021-04-08 18:55:00', '0', 1, 5, 'admin', '2021-04-08 18:55:00', '', '2021-04-08 18:55:00', '');
INSERT INTO `sys_job` VALUES ('49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '0 0 1 * * ?', '3', '1', '0', '0 0/10 * * * ?', '0', NULL, NULL, NULL, 5, 'admin', '2021-03-19 16:04:08', 'admin', '2021-04-20 18:30:10', '');
INSERT INTO `sys_job` VALUES ('4ad290aa-9fbe-4df0-999d-873f98b5959e', '1de64768-be51-4ca1-9db7-5b96355663c9', '2666bd30-864c-11eb-a040-9c5c8e383269', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '1', '2021-04-08 19:05:00', '0', 2, 5, 'admin', '2021-04-08 19:05:00', '', '2021-05-25 10:01:51', '');
INSERT INTO `sys_job` VALUES ('507265cf-503c-4ac2-ae67-cdcc2ff8443c', '997420db-aa9a-4891-abdf-464e8142f8af', '2666bd30-864c-11eb-a040-9c5c8e383269', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '1', '2021-04-08 19:21:30', '0', 2, 5, 'admin', '2021-04-08 19:21:30', '', '2021-04-08 19:21:32', '');
INSERT INTO `sys_job` VALUES ('56a4ac39-9db4-442d-be1a-a8d9bdd25667', '70831ed9-55b4-4bb1-b5b3-5735b9af7632', '2666bd30-864c-11eb-a040-9c5c8e383269', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '1', '2021-04-08 18:54:00', '0', 2, 5, 'admin', '2021-04-08 18:54:00', '', '2021-04-13 15:08:34', '');
INSERT INTO `sys_job` VALUES ('aa1bbdb5-4457-47fc-80d9-cd840e73f73c', '68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '1', '2021-04-08 19:10:00', '0', 6, 5, 'admin', '2021-04-08 19:10:00', '', '2021-04-13 15:45:44', '');
INSERT INTO `sys_job` VALUES ('b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '0 0 16 * * ? *', '3', '1', '0', '0 0 0/1 * * ? *', '0', NULL, NULL, NULL, 1, 'admin', '2021-04-13 14:54:49', 'admin', '2021-05-17 10:56:38', '');
INSERT INTO `sys_job` VALUES ('c28e43d6-58de-439c-94ea-85ae5a3523bc', '412b2fbf-1842-4a7c-bda9-6f2c61806cd9', 'b74d3599-ff7a-4841-bf28-373861e6794f', '1', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(\'20210413\')', '0/30 * * * * ?', '3', '1', '1', '0/17 * * * * ?', '1', '2021-04-13 15:27:01', '1', 1, 1, 'admin', '2021-04-13 15:27:00', '', '2021-04-13 15:27:01', '');
INSERT INTO `sys_job` VALUES ('f63fd82d-ce0a-4e40-9c9a-85d6246409d5', '798093c1-9e38-42f6-8471-4ae0ce60bc68', '2666bd30-864c-11eb-a040-9c5c8e383269', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '0/30 * * * * ?', '3', '1', '1', '0/2 * * * * ?', '1', '2021-04-08 19:11:30', '0', 3, 5, 'admin', '2021-04-08 19:11:30', '', '2021-04-13 16:07:10', '');

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
-- Records of sys_job_log
-- ----------------------------
INSERT INTO `sys_job_log` VALUES ('01a83642-9dae-4d7b-adb5-52304c0d0d9c', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:34:30', '2021-04-08 19:34:30', '2021-04-08 19:34:30', '0.3054489444049312');
INSERT INTO `sys_job_log` VALUES ('046c949a-ffac-4eb8-a308-43ca10343489', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：310毫秒', '0', '', '2021-04-20 22:17:49', '2021-04-20 22:17:49', '2021-04-20 22:17:46', '0');
INSERT INTO `sys_job_log` VALUES ('04754061-1994-4ddc-9ac1-a62b2a001013', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:18:30', '2021-04-08 19:18:30', '2021-04-08 19:18:30', '0.6447532434715356');
INSERT INTO `sys_job_log` VALUES ('0485db3c-dfab-4281-8160-7e644cbc7d72', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：1580毫秒', '0', '', '2021-04-25 16:00:00', '2021-04-25 16:00:02', '2021-04-25 16:00:04', '0');
INSERT INTO `sys_job_log` VALUES ('04f9e903-39e0-4c36-bf05-8eaf21286aa7', '56a4ac39-9db4-442d-be1a-a8d9bdd25667', '70831ed9-55b4-4bb1-b5b3-5735b9af7632', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 18:54:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：35毫秒', '0', '', '2021-04-08 18:54:00', '2021-04-08 18:54:00', '2021-04-08 18:54:00', '0.44553316121206166');
INSERT INTO `sys_job_log` VALUES ('059a63fe-5ebe-4df0-9167-d3a07f6adee8', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:04:30', '2021-04-08 19:04:30', '2021-04-08 19:04:30', '0.8936972947928068');
INSERT INTO `sys_job_log` VALUES ('05e91ee8-43ac-45cc-a2fa-5bca73efb4b2', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:19:00', '2021-04-08 19:19:00', '2021-04-08 19:19:00', '0.6463616197578023');
INSERT INTO `sys_job_log` VALUES ('0655af72-8e93-47f8-af5e-669387ee7074', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:09:30', '2021-04-08 19:09:30', '2021-04-08 19:09:30', '0.1991349860924828');
INSERT INTO `sys_job_log` VALUES ('095da8c7-3abf-41a5-b7a2-b6adf1822e38', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:37:00', '2021-04-08 18:37:00', '2021-04-08 18:37:00', '0.44550118083886836');
INSERT INTO `sys_job_log` VALUES ('0cfde21b-a558-4d0d-8124-72a659018b59', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:55:30', '2021-04-08 18:55:30', '2021-04-08 18:55:30', '0.7978022938552065');
INSERT INTO `sys_job_log` VALUES ('0edde27a-3590-4615-962c-c29c5cd682d5', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：1295毫秒', '0', '', '2021-04-28 16:00:00', '2021-04-28 16:00:01', '2021-04-28 16:00:02', '0');
INSERT INTO `sys_job_log` VALUES ('0fdd5128-56e4-422a-ba1a-784cd429227f', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：45毫秒', '0', '', '2021-04-25 01:00:00', '2021-04-25 01:00:00', '2021-04-25 01:00:02', NULL);
INSERT INTO `sys_job_log` VALUES ('125cadd2-567a-4717-b1c6-8fd404125e88', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：65毫秒', '0', '', '2021-05-04 01:00:00', '2021-05-04 01:00:00', '2021-05-04 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('1634b68d-a056-489f-b2dd-cf091c0c8017', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:59:00', '2021-04-08 18:59:00', '2021-04-08 18:59:00', '0.595591429049527');
INSERT INTO `sys_job_log` VALUES ('168b2d6e-3317-450c-bf47-57bedb1ba3b4', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：3毫秒', '0', '', '2021-04-08 19:11:00', '2021-04-08 19:11:00', '2021-04-08 19:11:00', '0.6319313952939701');
INSERT INTO `sys_job_log` VALUES ('16befbcd-9b3a-4cb0-bc7b-9139391dd781', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：3毫秒', '0', '', '2021-04-08 18:50:30', '2021-04-08 18:50:30', '2021-04-08 18:50:30', '0.5733830418669148');
INSERT INTO `sys_job_log` VALUES ('1c96b11e-d0e9-4735-ab14-d65d69fc43b8', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:43:00', '2021-04-08 18:43:00', '2021-04-08 18:43:00', '0.4004769859418975');
INSERT INTO `sys_job_log` VALUES ('1cddb2a1-181b-4e68-bc05-6c2488c855b5', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：3毫秒', '0', '', '2021-04-08 18:48:30', '2021-04-08 18:48:30', '2021-04-08 18:48:30', '0.3578671696762701');
INSERT INTO `sys_job_log` VALUES ('1ce6c9c5-1f80-4cb3-8384-513204ad1284', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：605毫秒', '0', '', '2021-05-24 16:00:00', '2021-05-24 16:00:01', '2021-05-24 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('1d2b848d-1adf-4257-8ccf-5313a920ca4f', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:35:30', '2021-04-08 19:35:30', '2021-04-08 19:35:30', '0.7509123928855175');
INSERT INTO `sys_job_log` VALUES ('1de64768-be51-4ca1-9db7-5b96355663c9', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '2021-04-08 19:05:00', '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:05:00', '2021-04-08 19:05:00', '2021-04-08 19:05:00', NULL);
INSERT INTO `sys_job_log` VALUES ('2030d78a-c1a1-4a37-8f0c-db63bae740c3', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：71毫秒', '0', '', '2021-04-26 16:00:00', '2021-04-26 16:00:00', '2021-04-26 16:00:02', '0');
INSERT INTO `sys_job_log` VALUES ('20e3b776-1f50-498c-bbbd-0dc319335e72', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:21:00', '2021-04-08 19:21:00', '2021-04-08 19:21:00', '0.7405206517083643');
INSERT INTO `sys_job_log` VALUES ('2217a13c-8fdc-4c95-b6cf-013840fd7e22', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：65毫秒', '0', '', '2021-04-22 01:00:00', '2021-04-22 01:00:00', '2021-04-22 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('243aa080-10cd-45f7-a106-090797575f8e', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:29:00', '2021-04-08 19:29:00', '2021-04-08 19:29:00', '0.5169619556858664');
INSERT INTO `sys_job_log` VALUES ('244c6e3d-b4ae-4d5e-a781-c83799cd1457', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:16:00', '2021-04-08 19:16:00', '2021-04-08 19:16:00', '0.8210897005979458');
INSERT INTO `sys_job_log` VALUES ('25347fdb-dd68-4ed0-bcc6-1c8b6c40b97e', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：3毫秒', '0', '', '2021-04-08 18:50:00', '2021-04-08 18:50:00', '2021-04-08 18:50:00', '0.7057508475565494');
INSERT INTO `sys_job_log` VALUES ('2581c763-46cf-452b-a20f-5100b18b3041', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：3毫秒', '0', '', '2021-04-08 18:53:30', '2021-04-08 18:53:30', '2021-04-08 18:53:30', '0.687194373561049');
INSERT INTO `sys_job_log` VALUES ('262b6068-b95b-43cc-ae5a-13023c548bb2', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：43毫秒', '0', '', '2021-05-07 01:00:00', '2021-05-07 01:00:00', '2021-05-07 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('279190c3-ce2b-4067-bd24-ddf7071c0abd', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：11187887毫秒', '0', '', '2021-05-17 16:00:01', '2021-05-17 19:06:29', '2021-05-17 19:06:26', '1');
INSERT INTO `sys_job_log` VALUES ('28926999-03f2-4a5b-a7cb-937a6b39bd1b', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:37:30', '2021-04-08 18:37:30', '2021-04-08 18:37:30', '0.5574393639785561');
INSERT INTO `sys_job_log` VALUES ('29b0e535-adad-443b-8a4b-dbacf13c5b48', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：54毫秒', '0', '', '2021-05-02 01:00:00', '2021-05-02 01:00:00', '2021-05-02 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('2d57bdc1-fbc9-4cee-a583-c3f9c3419064', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:07:30', '2021-04-08 19:07:30', '2021-04-08 19:07:30', '0.11157548884482915');
INSERT INTO `sys_job_log` VALUES ('2e3476ca-950f-40bf-a6c2-3f1fe91c318c', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:06:30', '2021-04-08 19:06:30', '2021-04-08 19:06:30', '0.5329803292549269');
INSERT INTO `sys_job_log` VALUES ('32ffb767-4235-4f3c-b914-5246c441e98b', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：17毫秒', '0', '', '2021-04-23 16:00:00', '2021-04-23 16:00:00', '2021-04-23 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('358c9186-2c6f-45b0-85a3-79945b214818', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:06:00', '2021-04-08 19:06:00', '2021-04-08 19:06:00', '0.7052455737866156');
INSERT INTO `sys_job_log` VALUES ('3590e9ef-b9e0-4d0d-b35c-4f2fcbbcfece', 'aa1bbdb5-4457-47fc-80d9-cd840e73f73c', '68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:10:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:10:00', '2021-04-08 19:10:00', '2021-04-08 19:10:00', NULL);
INSERT INTO `sys_job_log` VALUES ('35d344ae-7297-48f6-93a7-6a2f78bbd941', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：145毫秒', '0', '', '2021-04-30 01:00:00', '2021-04-30 01:00:00', '2021-04-30 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('376181f9-1e4a-4f8a-ad90-7ea2644bb55d', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:34:00', '2021-04-08 19:34:00', '2021-04-08 19:34:00', '0.17305855106502344');
INSERT INTO `sys_job_log` VALUES ('392ec022-4727-405d-ac8c-bc9d32d36004', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:52:00', '2021-04-08 18:52:00', '2021-04-08 18:52:00', '0.2754193152035066');
INSERT INTO `sys_job_log` VALUES ('3948c46b-48a7-4e59-ac03-f89ce1210a46', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:02:00', '2021-04-08 19:02:00', '2021-04-08 19:02:00', '0.7460425478991503');
INSERT INTO `sys_job_log` VALUES ('394c2ca9-c2a7-48a3-8e45-05411ec0a443', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:22:00', '2021-04-08 19:22:00', '2021-04-08 19:22:00', '0.45194774653531866');
INSERT INTO `sys_job_log` VALUES ('3ce41206-c1d8-415b-a416-89ec320edae4', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:48:00', '2021-04-08 18:48:00', '2021-04-08 18:48:00', '0.06984909072038936');
INSERT INTO `sys_job_log` VALUES ('3d7062f1-833a-4f74-962f-ac8fc48b4f7b', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:08:30', '2021-04-08 19:08:30', '2021-04-08 19:08:30', '0.4556366670987634');
INSERT INTO `sys_job_log` VALUES ('3dddd188-0067-4286-bdab-c162c231878b', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:12:30', '2021-04-08 19:12:30', '2021-04-08 19:12:30', '0.16556432975427282');
INSERT INTO `sys_job_log` VALUES ('3e3e8afa-6183-428c-a488-e884fb65f61f', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:59:30', '2021-04-08 18:59:30', '2021-04-08 18:59:30', '0.2533060354332146');
INSERT INTO `sys_job_log` VALUES ('3e9f43ee-3c63-4ad7-9faf-4e3e33124439', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:35:30', '2021-04-08 18:35:30', '2021-04-08 18:35:30', '0.27950103334086085');
INSERT INTO `sys_job_log` VALUES ('3f42bf3e-44bc-4f6b-8da0-b2a8e337b7bf', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:15:00', '2021-04-08 19:15:00', '2021-04-08 19:15:00', '0.031356261429740906');
INSERT INTO `sys_job_log` VALUES ('3f875740-63d6-4dce-b368-ae122cab27f7', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:07:00', '2021-04-08 19:07:00', '2021-04-08 19:07:00', '0.8950989386322853');
INSERT INTO `sys_job_log` VALUES ('3f8ebf9c-72d5-4531-ba0b-a775fb0f376b', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:57:30', '2021-04-08 18:57:30', '2021-04-08 18:57:30', '0.47525400573093335');
INSERT INTO `sys_job_log` VALUES ('4049f731-36d0-4371-935f-0323d183ee21', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:53:00', '2021-04-08 18:53:00', '2021-04-08 18:53:00', '0.5621577910619179');
INSERT INTO `sys_job_log` VALUES ('412b2fbf-1842-4a7c-bda9-6f2c61806cd9', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, '2021-04-13 15:27:01', '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：82毫秒', '1', 'java.lang.reflect.InvocationTargetException\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:89)\r\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\r\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\r\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\r\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\r\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\r\nCaused by: java.lang.Exception: {\"invokeTarget\":\"dcwltPayBatchTask.checkData(\'20210413\')\",\"success\":false}\r\n	at com.dcits.dcwlt.job.task.DcwltPayBatchTask.checkData(DcwltPayBatchTask.java:79)\r\n	... 10 more\r\n', '2021-04-13 15:27:01', '2021-04-13 15:27:01', '2021-04-13 15:27:00', NULL);
INSERT INTO `sys_job_log` VALUES ('4347f99c-3bd9-442e-9510-37ef171dede1', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：1631毫秒', '0', '', '2021-05-27 01:00:00', '2021-05-27 01:00:02', '2021-05-27 01:00:01', NULL);
INSERT INTO `sys_job_log` VALUES ('44275f53-e594-4b4f-baf8-0d5920eac8d7', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:08:00', '2021-04-08 19:08:00', '2021-04-08 19:08:00', '0.8640616514652176');
INSERT INTO `sys_job_log` VALUES ('44600a13-a95b-47c0-8d8d-8bc7a5d23a87', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：29毫秒', '0', '', '2021-04-24 16:00:00', '2021-04-24 16:00:00', '2021-04-24 16:00:02', '0');
INSERT INTO `sys_job_log` VALUES ('44d0f637-df31-420e-9b49-55acb33b5a00', 'f63fd82d-ce0a-4e40-9c9a-85d6246409d5', '798093c1-9e38-42f6-8471-4ae0ce60bc68', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:11:30', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：1毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:11:30', '2021-04-08 19:11:30', '2021-04-08 19:11:30', NULL);
INSERT INTO `sys_job_log` VALUES ('45927717-32dd-4c36-9043-3dd2e20f69da', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:32:30', '2021-04-08 19:32:30', '2021-04-08 19:32:30', '0.6606683068388556');
INSERT INTO `sys_job_log` VALUES ('45a6e08c-b5e3-4258-aa2c-1d7cf68a8ffc', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:40:00', '2021-04-08 18:40:00', '2021-04-08 18:40:00', '0.04160261940593457');
INSERT INTO `sys_job_log` VALUES ('4c6d163a-3342-4b19-a027-f102a08d7fff', '507265cf-503c-4ac2-ae67-cdcc2ff8443c', '997420db-aa9a-4891-abdf-464e8142f8af', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:21:30', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：17毫秒', '0', '', '2021-04-08 19:21:32', '2021-04-08 19:21:32', '2021-04-08 19:21:32', '0.6450460811985713');
INSERT INTO `sys_job_log` VALUES ('4dccc460-46e8-46ba-be1f-72a508ef5288', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：59毫秒', '0', '', '2021-05-03 01:00:00', '2021-05-03 01:00:00', '2021-05-03 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('4e2e12d2-2bc8-42e5-b189-b966361a9631', 'aa1bbdb5-4457-47fc-80d9-cd840e73f73c', '68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:10:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：23毫秒', '0', '', '2021-04-08 19:10:08', '2021-04-08 19:10:08', '2021-04-08 19:10:08', '0.2992646308479515');
INSERT INTO `sys_job_log` VALUES ('52b94f18-e475-4171-8c8f-8712a9800d54', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:13:00', '2021-04-08 19:13:00', '2021-04-08 19:13:00', '0.698911756624828');
INSERT INTO `sys_job_log` VALUES ('53373b13-a132-4ada-93dd-c5fb39b06480', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:45:00', '2021-04-08 18:45:00', '2021-04-08 18:45:00', '0.08104177524862222');
INSERT INTO `sys_job_log` VALUES ('53488a16-d861-4691-a411-163ef0db92c3', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：15毫秒', '0', '', '2021-05-03 16:00:00', '2021-05-03 16:00:00', '2021-05-03 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('5482b14e-11f1-4582-804b-7ec57e8eae1b', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：112毫秒', '0', '', '2021-04-27 01:00:00', '2021-04-27 01:00:00', '2021-04-27 01:00:03', NULL);
INSERT INTO `sys_job_log` VALUES ('55d78315-7beb-4ff4-b79c-5918aa8a450e', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：51毫秒', '0', '', '2021-04-28 01:00:00', '2021-04-28 01:00:00', '2021-04-28 00:59:59', NULL);
INSERT INTO `sys_job_log` VALUES ('58750bec-32f2-4cfa-a93e-7bfccbf618c4', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '2021-04-08 18:55:00', '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 18:55:00', '2021-04-08 18:55:00', '2021-04-08 18:55:00', NULL);
INSERT INTO `sys_job_log` VALUES ('5bb63188-a9bc-48e1-8d9e-c63201ec9d88', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:24:00', '2021-04-08 19:24:00', '2021-04-08 19:24:00', '0.538177144115015');
INSERT INTO `sys_job_log` VALUES ('5c68d012-59a2-4fa3-b30c-532a5e28cdc5', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:31:30', '2021-04-08 19:31:30', '2021-04-08 19:31:30', '0.8402713494708108');
INSERT INTO `sys_job_log` VALUES ('5cbea4e3-4b32-43e2-829a-ae6103c47925', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：69毫秒', '0', '', '2021-04-08 18:35:00', '2021-04-08 18:35:00', '2021-04-08 18:35:00', '0.1323419433574795');
INSERT INTO `sys_job_log` VALUES ('5f23d5a3-8478-485f-8655-917a565b1402', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:51:00', '2021-04-08 18:51:00', '2021-04-08 18:51:00', '0.6547872096788717');
INSERT INTO `sys_job_log` VALUES ('612c45b9-dfb5-4ca5-8a10-c185112eb713', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：138毫秒', '0', '', '2021-04-29 01:00:00', '2021-04-29 01:00:00', '2021-04-29 01:00:01', NULL);
INSERT INTO `sys_job_log` VALUES ('61c10f50-fbef-4632-bc80-3d521c1782f6', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：223毫秒', '0', '', '2021-04-20 18:30:44', '2021-04-20 18:30:44', '2021-04-20 18:30:43', NULL);
INSERT INTO `sys_job_log` VALUES ('62635055-67be-4d0a-b5fb-6d6c2e22d517', '56a4ac39-9db4-442d-be1a-a8d9bdd25667', '70831ed9-55b4-4bb1-b5b3-5735b9af7632', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 18:54:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：26毫秒', '0', '', '2021-04-13 15:08:35', '2021-04-13 15:08:35', '2021-04-13 15:08:34', '0.05316990185044501');
INSERT INTO `sys_job_log` VALUES ('62863385-280a-4c3c-b520-c7cfe5959f2d', 'aa1bbdb5-4457-47fc-80d9-cd840e73f73c', '68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:10:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：16毫秒', '0', '', '2021-04-13 15:45:44', '2021-04-13 15:45:44', '2021-04-13 15:45:44', '0.30407533313233737');
INSERT INTO `sys_job_log` VALUES ('62bf2849-87e9-4999-8221-284cd9eafaec', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:01:30', '2021-04-08 19:01:30', '2021-04-08 19:01:30', '0.6280114155378281');
INSERT INTO `sys_job_log` VALUES ('65627d3f-418f-4f33-88bb-521314221156', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:30:00', '2021-04-08 19:30:00', '2021-04-08 19:30:00', '0.7637270464628939');
INSERT INTO `sys_job_log` VALUES ('662ffe3b-2017-4903-afd3-e5e129d9742a', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：877毫秒', '0', '', '2021-04-30 16:00:00', '2021-04-30 16:00:01', '2021-04-30 16:00:01', '0');
INSERT INTO `sys_job_log` VALUES ('66aad4a2-0c92-4f9a-b4e2-f76d5950de71', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:30:30', '2021-04-08 19:30:30', '2021-04-08 19:30:30', '0.6053928092168294');
INSERT INTO `sys_job_log` VALUES ('68f13f93-55ca-42ae-a1d9-373d49ec4483', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：1411毫秒', '0', '', '2021-05-24 01:00:00', '2021-05-24 01:00:01', '2021-05-24 00:59:57', NULL);
INSERT INTO `sys_job_log` VALUES ('68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '2021-04-08 19:10:00', '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:10:00', '2021-04-08 19:10:00', '2021-04-08 19:10:00', NULL);
INSERT INTO `sys_job_log` VALUES ('6d4a161b-4df7-46df-afac-d38d46020c20', 'f63fd82d-ce0a-4e40-9c9a-85d6246409d5', '798093c1-9e38-42f6-8471-4ae0ce60bc68', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:11:30', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：25毫秒', '0', '', '2021-04-08 19:11:32', '2021-04-08 19:11:32', '2021-04-08 19:11:32', '0.5100504871498698');
INSERT INTO `sys_job_log` VALUES ('6da14044-fdc3-42d1-8bb7-e2902a97a8c9', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:46:00', '2021-04-08 18:46:00', '2021-04-08 18:46:00', '0.20323516535300445');
INSERT INTO `sys_job_log` VALUES ('6dc4c818-4e21-4311-91a6-e36e2f4e27b1', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:19:30', '2021-04-08 19:19:30', '2021-04-08 19:19:30', '0.43248077171646104');
INSERT INTO `sys_job_log` VALUES ('70831ed9-55b4-4bb1-b5b3-5735b9af7632', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '2021-04-08 18:54:00', '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 18:54:00', '2021-04-08 18:54:00', '2021-04-08 18:54:00', NULL);
INSERT INTO `sys_job_log` VALUES ('71261505-92d1-4936-a0bd-1a74996d4506', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:03:00', '2021-04-08 19:03:00', '2021-04-08 19:03:00', '0.22082320945609757');
INSERT INTO `sys_job_log` VALUES ('757a44a6-247d-4894-8196-78870066b818', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：3毫秒', '0', '', '2021-04-08 19:14:30', '2021-04-08 19:14:30', '2021-04-08 19:14:30', '0.6323295719803412');
INSERT INTO `sys_job_log` VALUES ('77e028f2-3aeb-460b-bce8-d77598a89008', 'aa1bbdb5-4457-47fc-80d9-cd840e73f73c', '68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:10:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：1毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:10:04', '2021-04-08 19:10:04', '2021-04-08 19:10:04', NULL);
INSERT INTO `sys_job_log` VALUES ('78aafd60-6c9c-4cbc-8551-18a24f49aeff', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:36:00', '2021-04-08 19:36:00', '2021-04-08 19:36:00', '0.7054962618838133');
INSERT INTO `sys_job_log` VALUES ('78c0b62b-59c1-4c59-9c17-f33f1ae7de30', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:56:30', '2021-04-08 18:56:30', '2021-04-08 18:56:30', '0.8923445279008306');
INSERT INTO `sys_job_log` VALUES ('793afc03-fe88-4f8e-baf4-eb6b67a6c596', 'f63fd82d-ce0a-4e40-9c9a-85d6246409d5', '798093c1-9e38-42f6-8471-4ae0ce60bc68', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:11:30', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：23毫秒', '0', '', '2021-04-13 16:07:10', '2021-04-13 16:07:10', '2021-04-13 16:07:10', '0.0022482552902556296');
INSERT INTO `sys_job_log` VALUES ('793fa240-6577-45b1-9009-fe5a00ca79b0', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:26:30', '2021-04-08 19:26:30', '2021-04-08 19:26:30', '0.678677894714502');
INSERT INTO `sys_job_log` VALUES ('798093c1-9e38-42f6-8471-4ae0ce60bc68', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '2021-04-08 19:11:30', '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:11:30', '2021-04-08 19:11:30', '2021-04-08 19:11:30', NULL);
INSERT INTO `sys_job_log` VALUES ('798a9846-217d-4960-af09-09d9bf9436c7', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：167毫秒', '0', '', '2021-04-20 22:53:39', '2021-04-20 22:53:39', '2021-04-20 22:53:38', '0');
INSERT INTO `sys_job_log` VALUES ('79a1a98b-4c29-48f9-a3d6-057117c3b394', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:35:00', '2021-04-08 19:35:00', '2021-04-08 19:35:00', '0.7301306268914375');
INSERT INTO `sys_job_log` VALUES ('7a697c75-6ac6-405b-9a1d-d24d5017a3fc', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：14毫秒', '0', '', '2021-05-07 16:00:00', '2021-05-07 16:00:00', '2021-05-07 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('7ac4af7d-6293-47ad-bc09-99561e807672', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 18:49:30', '2021-04-08 18:49:30', '2021-04-08 18:49:30', '0.7719867095706442');
INSERT INTO `sys_job_log` VALUES ('7cef81f9-6c23-43b0-add7-6989dcca4f44', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：14毫秒', '0', '', '2021-04-27 16:00:00', '2021-04-27 16:00:00', '2021-04-27 15:59:59', '0');
INSERT INTO `sys_job_log` VALUES ('7d494424-fddd-40e7-b1e8-bd38fe275197', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:47:30', '2021-04-08 18:47:30', '2021-04-08 18:47:30', '0.8576900362878257');
INSERT INTO `sys_job_log` VALUES ('80dad143-09cc-4f44-8477-3cc9b58086c5', 'aa1bbdb5-4457-47fc-80d9-cd840e73f73c', '68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:10:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:10:06', '2021-04-08 19:10:06', '2021-04-08 19:10:06', NULL);
INSERT INTO `sys_job_log` VALUES ('8105d3b5-ad80-4825-ba42-88d2d72f0a33', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：14毫秒', '0', '', '2021-05-04 16:00:00', '2021-05-04 16:00:00', '2021-05-04 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('8196a24c-c14a-4778-bee2-84f235f022d3', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:52:30', '2021-04-08 18:52:30', '2021-04-08 18:52:30', '0.7495498723259549');
INSERT INTO `sys_job_log` VALUES ('852626f2-2bab-422e-b6f4-cc0f6477b7a4', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：8毫秒', '0', '', '2021-04-20 23:07:27', '2021-04-20 23:07:27', '2021-04-20 23:07:26', '0');
INSERT INTO `sys_job_log` VALUES ('8548df4e-728b-4624-8bb2-57eb3f829763', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '2021-04-08 18:41:00', '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 10 more\n', '2021-04-08 18:41:00', '2021-04-08 18:41:00', '2021-04-08 18:41:00', NULL);
INSERT INTO `sys_job_log` VALUES ('86f8cebf-8e83-484d-ac5a-092c79208b4e', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:47:00', '2021-04-08 18:47:00', '2021-04-08 18:47:00', '0.8829236925085034');
INSERT INTO `sys_job_log` VALUES ('8b9c7d73-bf4d-44d7-ae70-9b0fad646ec8', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:58:30', '2021-04-08 18:58:30', '2021-04-08 18:58:30', '0.6092290367406701');
INSERT INTO `sys_job_log` VALUES ('8c5684e7-0cd4-4c38-bb68-8fbab7049004', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:57:00', '2021-04-08 18:57:00', '2021-04-08 18:57:00', '0.22350470058784222');
INSERT INTO `sys_job_log` VALUES ('8c642a0c-d484-4738-ad24-3b26ee68514f', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:22:30', '2021-04-08 19:22:30', '2021-04-08 19:22:30', '0.6353427340121011');
INSERT INTO `sys_job_log` VALUES ('8f202a86-55be-445e-a732-3157b713160b', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:00:00', '2021-04-08 19:00:00', '2021-04-08 19:00:00', '0.6616012481253458');
INSERT INTO `sys_job_log` VALUES ('8f47854b-fa92-4879-b02c-24095593e515', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 18:41:30', '2021-04-08 18:41:30', '2021-04-08 18:41:30', '0.29473356401008133');
INSERT INTO `sys_job_log` VALUES ('90df5407-be2d-4c5d-9140-ab987a17ef4d', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:14:00', '2021-04-08 19:14:00', '2021-04-08 19:14:00', '0.8041700511684705');
INSERT INTO `sys_job_log` VALUES ('94928617-8d4c-4525-b12f-6573a359ed16', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:51:30', '2021-04-08 18:51:30', '2021-04-08 18:51:30', '0.25415896462921883');
INSERT INTO `sys_job_log` VALUES ('951a9c83-c97d-4b51-9584-ad0c939e1dfe', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:23:00', '2021-04-08 19:23:00', '2021-04-08 19:23:00', '0.5449220927017899');
INSERT INTO `sys_job_log` VALUES ('9533453a-e345-4051-b8e5-14ed023280d9', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:25:30', '2021-04-08 19:25:30', '2021-04-08 19:25:30', '0.2740071484503771');
INSERT INTO `sys_job_log` VALUES ('96be17f6-6b17-4a1e-932d-ff058d93e8b9', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：16毫秒', '0', '', '2021-04-22 14:27:32', '2021-04-22 14:27:32', '2021-04-22 14:27:31', '0');
INSERT INTO `sys_job_log` VALUES ('98eab63d-ee0b-4f10-9c0f-054a0bcbc90d', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:46:30', '2021-04-08 18:46:30', '2021-04-08 18:46:30', '0.6422562702945414');
INSERT INTO `sys_job_log` VALUES ('997420db-aa9a-4891-abdf-464e8142f8af', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, '2021-04-08 19:21:30', '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:21:30', '2021-04-08 19:21:30', '2021-04-08 19:21:30', NULL);
INSERT INTO `sys_job_log` VALUES ('9a80b109-47ee-4ef3-9501-b395d590ea04', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 18:44:30', '2021-04-08 18:44:30', '2021-04-08 18:44:30', '0.5409182079800114');
INSERT INTO `sys_job_log` VALUES ('9dc55986-291e-4b7d-8379-9cb01b798adb', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:32:00', '2021-04-08 19:32:00', '2021-04-08 19:32:00', '0.5661477277900667');
INSERT INTO `sys_job_log` VALUES ('a15bc8e9-ced4-4aaf-aa1a-e8c85607cc44', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:56:00', '2021-04-08 18:56:00', '2021-04-08 18:56:00', '0.8693794569413514');
INSERT INTO `sys_job_log` VALUES ('a1f62fac-c9f4-4142-8394-c9e4fc735d6f', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：11毫秒', '0', '', '2021-05-02 16:00:00', '2021-05-02 16:00:00', '2021-05-02 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('a2c98b7f-d314-47ec-b561-27b9d3b9be6f', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:13:30', '2021-04-08 19:13:30', '2021-04-08 19:13:30', '0.034106488572439586');
INSERT INTO `sys_job_log` VALUES ('a2d2f6bc-ff37-40d4-947c-1c5d4622d08a', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:04:00', '2021-04-08 19:04:00', '2021-04-08 19:04:00', '0.714398730897634');
INSERT INTO `sys_job_log` VALUES ('a5603cfe-e6cd-41a9-8e69-53282f05dcc2', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：1064154毫秒', '0', '', '2021-05-17 10:37:05', '2021-05-17 10:54:49', '2021-05-17 10:54:46', '1');
INSERT INTO `sys_job_log` VALUES ('a596be8a-07fc-413d-bafc-58317a08e7e3', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:33:30', '2021-04-08 19:33:30', '2021-04-08 19:33:30', '0.002171158471357848');
INSERT INTO `sys_job_log` VALUES ('a5f4fab9-f065-4c4c-bf76-aee618c79d3a', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:20:00', '2021-04-08 19:20:00', '2021-04-08 19:20:00', '0.8102413760491847');
INSERT INTO `sys_job_log` VALUES ('a6785e8e-3596-44de-8195-17af6b71ad7c', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:12:00', '2021-04-08 19:12:00', '2021-04-08 19:12:00', '0.38306215224221396');
INSERT INTO `sys_job_log` VALUES ('aa0d4ea5-b8cd-4fb3-b8fd-36d760c172ba', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:44:00', '2021-04-08 18:44:00', '2021-04-08 18:44:00', '0.4515200490795106');
INSERT INTO `sys_job_log` VALUES ('aabf8aba-ecc5-4431-9375-088391549d4f', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:45:30', '2021-04-08 18:45:30', '2021-04-08 18:45:30', '0.894247595284214');
INSERT INTO `sys_job_log` VALUES ('ab620b2a-5ccd-48e2-bf80-64889206369c', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:39:30', '2021-04-08 18:39:30', '2021-04-08 18:39:30', '0.35519578140616226');
INSERT INTO `sys_job_log` VALUES ('adb1c0dc-753e-4070-8391-cd6c3ced4cbf', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:17:30', '2021-04-08 19:17:30', '2021-04-08 19:17:30', '0.004388843156870381');
INSERT INTO `sys_job_log` VALUES ('ae643dfb-38e9-4217-a6fb-258d41943c70', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:42:30', '2021-04-08 18:42:30', '2021-04-08 18:42:30', '0.6207679702693867');
INSERT INTO `sys_job_log` VALUES ('af34615e-c70f-4c04-9666-63187c4dc564', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:26:00', '2021-04-08 19:26:00', '2021-04-08 19:26:00', '0.24741818983789454');
INSERT INTO `sys_job_log` VALUES ('b1c61463-4737-46bc-9c96-95ca5a7abb2a', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:17:00', '2021-04-08 19:17:00', '2021-04-08 19:17:00', '0.8190521585138677');
INSERT INTO `sys_job_log` VALUES ('b21562c6-f862-4a77-935c-b3fa59ea5135', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：52毫秒', '0', '', '2021-04-23 01:00:00', '2021-04-23 01:00:00', '2021-04-23 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('b7e95ce5-91ed-4a2c-b87a-08ce4be4e0fb', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:24:30', '2021-04-08 19:24:30', '2021-04-08 19:24:30', '0.15589707151789978');
INSERT INTO `sys_job_log` VALUES ('b80e6588-a946-4c59-af1c-11ddf9546825', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：939毫秒', '0', '', '2021-04-26 01:00:00', '2021-04-26 01:00:01', '2021-04-26 01:00:03', NULL);
INSERT INTO `sys_job_log` VALUES ('b88fb8bc-78e9-46e9-8e20-b8be2317156d', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 18:39:00', '2021-04-08 18:39:00', '2021-04-08 18:39:00', '0.6153582921615155');
INSERT INTO `sys_job_log` VALUES ('baa6a868-e26c-4c48-b374-78ace5d88e60', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:02:30', '2021-04-08 19:02:30', '2021-04-08 19:02:30', '0.37257771357002145');
INSERT INTO `sys_job_log` VALUES ('bb3d29e6-ea05-4a99-b38b-cb3919c51c0b', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：7毫秒', '0', '', '2021-04-08 19:33:00', '2021-04-08 19:33:00', '2021-04-08 19:33:00', '0.7227684337031803');
INSERT INTO `sys_job_log` VALUES ('bba83a03-6404-4f35-8490-3fa242981f33', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:43:30', '2021-04-08 18:43:30', '2021-04-08 18:43:30', '0.8165129200279111');
INSERT INTO `sys_job_log` VALUES ('bbcd1868-7ac1-4a31-b258-372ac72e4779', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:20:30', '2021-04-08 19:20:30', '2021-04-08 19:20:30', '0.04109368273571712');
INSERT INTO `sys_job_log` VALUES ('bdd87172-ac26-429a-8a71-36e9dee6c710', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:49:00', '2021-04-08 18:49:00', '2021-04-08 18:49:00', '0.6114435948805116');
INSERT INTO `sys_job_log` VALUES ('bdf87ed4-83d0-4816-99fa-b66ea0a3f341', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：17毫秒', '0', '', '2021-05-25 16:00:00', '2021-05-25 16:00:00', '2021-05-25 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('be16743e-213e-4413-92ae-17bc36393291', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：19毫秒', '0', '', '2021-05-05 16:00:00', '2021-05-05 16:00:00', '2021-05-05 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('c2ed118f-9c53-46ca-8c02-6901cb1cf53a', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:36:30', '2021-04-08 18:36:30', '2021-04-08 18:36:30', '0.6739294282085501');
INSERT INTO `sys_job_log` VALUES ('c3bbb053-465c-4a55-bda2-d8e01f104e58', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:31:00', '2021-04-08 19:31:00', '2021-04-08 19:31:00', '0.5680621303950175');
INSERT INTO `sys_job_log` VALUES ('c503f54f-c170-4936-a103-1713ef22ffd2', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:29:30', '2021-04-08 19:29:30', '2021-04-08 19:29:30', '0.33818144384705773');
INSERT INTO `sys_job_log` VALUES ('c53d6583-bba9-4bc5-b21b-b0a118da4bfc', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:36:00', '2021-04-08 18:36:00', '2021-04-08 18:36:00', '0.06420221446954355');
INSERT INTO `sys_job_log` VALUES ('c6e4ddfe-96c8-4edb-a26e-7e4f80b27799', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:28:00', '2021-04-08 19:28:00', '2021-04-08 19:28:00', '0.3432511336877466');
INSERT INTO `sys_job_log` VALUES ('c9a0fa79-7ac1-4c96-870b-ea6ee0cfc631', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：57毫秒', '0', '', '2021-05-06 01:00:00', '2021-05-06 01:00:00', '2021-05-06 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('cc36703b-c239-43b4-80b0-1b35a6d184b7', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:10:30', '2021-04-08 19:10:30', '2021-04-08 19:10:30', '0.7146563791144632');
INSERT INTO `sys_job_log` VALUES ('ccb2b55a-67c3-4988-9496-706624d759ef', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 18:40:30', '2021-04-08 18:40:30', '2021-04-08 18:40:30', '0.28648451576925715');
INSERT INTO `sys_job_log` VALUES ('cd2c3b31-3468-4ba3-b570-4aa053c2466e', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:27:00', '2021-04-08 19:27:00', '2021-04-08 19:27:00', '0.6719432982194838');
INSERT INTO `sys_job_log` VALUES ('ce007ae4-fe46-4fb7-b92f-893d5645fef4', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:25:00', '2021-04-08 19:25:00', '2021-04-08 19:25:00', '0.13701119145303187');
INSERT INTO `sys_job_log` VALUES ('d3283574-933c-4ac6-bd44-44806fc179ac', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:03:30', '2021-04-08 19:03:30', '2021-04-08 19:03:30', '0.8258152719201922');
INSERT INTO `sys_job_log` VALUES ('d62be300-d597-4a0a-a1e6-f9440477875f', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:28:30', '2021-04-08 19:28:30', '2021-04-08 19:28:30', '0.22845550066681886');
INSERT INTO `sys_job_log` VALUES ('d688611f-682d-4628-aafb-e42681396930', '066550ed-f2ca-445c-9869-dd31fe361011', '8548df4e-728b-4624-8bb2-57eb3f829763', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 18:41:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：24毫秒', '0', '', '2021-04-08 18:41:00', '2021-04-08 18:41:00', '2021-04-08 18:41:00', '0.36534799373551496');
INSERT INTO `sys_job_log` VALUES ('d768638f-85e5-4f11-9d44-746627084e29', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 18:38:00', '2021-04-08 18:38:00', '2021-04-08 18:38:00', '0.6689595191587612');
INSERT INTO `sys_job_log` VALUES ('d849d976-4ea6-4ac3-93fa-319f5d0c5cf2', '11bce381-2fd0-44ef-bc6f-5c24166b512d', '58750bec-32f2-4cfa-a93e-7bfccbf618c4', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 18:55:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：30毫秒', '0', '', '2021-04-08 18:55:00', '2021-04-08 18:55:00', '2021-04-08 18:55:00', '0.8497388039965471');
INSERT INTO `sys_job_log` VALUES ('d8769fde-aa5b-46a0-885f-eb733791b406', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：1423毫秒', '0', '', '2021-04-29 16:00:00', '2021-04-29 16:00:01', '2021-04-29 16:00:01', '0');
INSERT INTO `sys_job_log` VALUES ('d91fb2a1-68d9-4782-b896-b21840315bb4', '507265cf-503c-4ac2-ae67-cdcc2ff8443c', '997420db-aa9a-4891-abdf-464e8142f8af', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:21:30', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:21:30', '2021-04-08 19:21:30', '2021-04-08 19:21:30', NULL);
INSERT INTO `sys_job_log` VALUES ('daf00ee0-7304-4636-ac13-803240c590e5', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：830毫秒', '0', '', '2021-05-26 01:00:00', '2021-05-26 01:00:01', '2021-05-26 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('dcb31e6e-f55e-495a-8230-8f1239762ecc', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：86毫秒', '0', '', '2021-05-25 01:00:00', '2021-05-25 01:00:00', '2021-05-25 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('dd7fc001-8c4e-44f7-96ca-2be3b54010e8', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：42毫秒', '0', '', '2021-05-05 01:00:00', '2021-05-05 01:00:00', '2021-05-05 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('e0a311c8-1ac6-40ee-8dcb-211681b734a6', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:09:00', '2021-04-08 19:09:00', '2021-04-08 19:09:00', '0.8128068840230248');
INSERT INTO `sys_job_log` VALUES ('e2b193ef-2ca4-4868-89bb-dd8aa261c5aa', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:15:30', '2021-04-08 19:15:30', '2021-04-08 19:15:30', '0.8444504090879957');
INSERT INTO `sys_job_log` VALUES ('e44be6b2-fec0-42ef-b743-e204a3119f5c', 'aa1bbdb5-4457-47fc-80d9-cd840e73f73c', '68f36bab-9886-4e3c-9220-312c5436a386', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:10:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\n	at sun.reflect.GeneratedMethodAccessor315.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:94)\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\nCaused by: java.lang.Exception: {\"invokeTarget\":\"ryTask.ryNoParams()\",\"message\":\"test ryTask.ryNoParams Exception\",\"success\":false}\n	at com.dcits.dcwlt.job.task.RyTask.ryNoParams(RyTask.java:117)\n	... 9 more\n', '2021-04-08 19:10:02', '2021-04-08 19:10:02', '2021-04-08 19:10:02', NULL);
INSERT INTO `sys_job_log` VALUES ('e5c21e10-c27f-44c2-ba3b-7f46c06797ad', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：3毫秒', '0', '', '2021-04-08 18:54:30', '2021-04-08 18:54:30', '2021-04-08 18:54:30', '0.5906071635958605');
INSERT INTO `sys_job_log` VALUES ('e63e5f8b-2add-4892-ae6b-e5fdd73502db', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：4毫秒', '0', '', '2021-04-08 18:58:00', '2021-04-08 18:58:00', '2021-04-08 18:58:00', '0.7417345562243479');
INSERT INTO `sys_job_log` VALUES ('e66b561a-8bad-477f-b08e-aa36b4538963', '4ad290aa-9fbe-4df0-999d-873f98b5959e', '1de64768-be51-4ca1-9db7-5b96355663c9', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:05:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：35毫秒', '0', '', '2021-04-08 19:05:00', '2021-04-08 19:05:00', '2021-04-08 19:05:00', '0.1553954041219151');
INSERT INTO `sys_job_log` VALUES ('e720f8ce-dfed-44c1-9780-00fd591624e6', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：94毫秒', '0', '', '2021-04-21 01:00:00', '2021-04-21 01:00:00', '2021-04-21 00:59:59', NULL);
INSERT INTO `sys_job_log` VALUES ('e7ac6201-9b33-4032-98a9-8ef9e197ffb8', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:23:30', '2021-04-08 19:23:30', '2021-04-08 19:23:30', '0.783935049977478');
INSERT INTO `sys_job_log` VALUES ('e8f9ddf0-5639-4287-8d50-5b7b3f102b2a', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:05:30', '2021-04-08 19:05:30', '2021-04-08 19:05:30', '0.4811065527627695');
INSERT INTO `sys_job_log` VALUES ('e9bc08cd-502d-4704-8e3a-96069d74ece9', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：0毫秒', '0', '', '2021-04-08 19:27:30', '2021-04-08 19:27:30', '2021-04-08 19:27:30', '0.03772442213123839');
INSERT INTO `sys_job_log` VALUES ('eb393b3e-b160-4e57-9d0c-9fee99613cea', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：36毫秒', '0', '', '2021-05-26 16:00:00', '2021-05-26 16:00:00', '2021-05-26 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('ebe670a1-669f-4da9-9741-7d1ec052a4a3', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：185毫秒', '0', '', '2021-05-01 01:00:00', '2021-05-01 01:00:00', '2021-05-01 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('ec358421-4928-4f00-87ef-caf2c0a27533', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：46毫秒', '0', '', '2021-05-08 01:00:00', '2021-05-08 01:00:00', '2021-05-08 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('ec89d954-da7b-4a29-bbcc-3a3ed739a44d', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：10毫秒', '0', '', '2021-04-08 18:38:30', '2021-04-08 18:38:30', '2021-04-08 18:38:30', '0.055180675906471244');
INSERT INTO `sys_job_log` VALUES ('ef22a923-a835-42ba-bc4a-4f3c2fb389ed', 'c28e43d6-58de-439c-94ea-85ae5a3523bc', '412b2fbf-1842-4a7c-bda9-6f2c61806cd9', 'b74d3599-ff7a-4841-bf28-373861e6794f', '2021-04-13 15:27:01', '1', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(\'20210413\')', '抽数任务 总共耗时：0毫秒', '1', 'java.lang.reflect.InvocationTargetException\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:89)\r\n	at com.dcits.dcwlt.job.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:38)\r\n	at com.dcits.dcwlt.job.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)\r\n	at com.dcits.dcwlt.job.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:62)\r\n	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\r\n	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\r\nCaused by: java.lang.Exception: {\"invokeTarget\":\"dcwltPayBatchTask.checkData(\'20210413\')\",\"success\":false}\r\n	at com.dcits.dcwlt.job.task.DcwltPayBatchTask.checkData(DcwltPayBatchTask.java:79)\r\n	... 10 more\r\n', '2021-04-13 15:27:02', '2021-04-13 15:27:02', '2021-04-13 15:27:01', NULL);
INSERT INTO `sys_job_log` VALUES ('ef2c0530-2b19-4e12-aec6-4863f5497c63', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:16:30', '2021-04-08 19:16:30', '2021-04-08 19:16:30', '0.5455222673314158');
INSERT INTO `sys_job_log` VALUES ('f0673e0d-4da0-49e6-be71-f88459667a46', '4ad290aa-9fbe-4df0-999d-873f98b5959e', '1de64768-be51-4ca1-9db7-5b96355663c9', '2666bd30-864c-11eb-a040-9c5c8e383269', '2021-04-08 19:05:00', '1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams()', '系统默认（无参） 总共耗时：48毫秒', '0', '', '2021-05-25 10:01:51', '2021-05-25 10:01:51', '2021-05-25 10:01:51', '0.645948647839532');
INSERT INTO `sys_job_log` VALUES ('f07d05d0-ba2d-48d9-9131-9d479fd04f0a', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：30毫秒', '0', '', '2021-05-06 16:00:00', '2021-05-06 16:00:00', '2021-05-06 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('f13febe6-dea1-4d9e-9375-d7b48b222ca8', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：11毫秒', '0', '', '2021-04-22 16:00:00', '2021-04-22 16:00:00', '2021-04-22 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('f15ef0ac-22d3-448b-94b2-c0dd5d36c5ac', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：18毫秒', '0', '', '2021-04-21 16:00:00', '2021-04-21 16:00:00', '2021-04-21 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('f1a1a4dd-dbec-476a-be45-4aa6d07f272e', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：4毫秒', '0', '', '2021-04-08 18:42:00', '2021-04-08 18:42:00', '2021-04-08 18:42:00', '0.8116109621258515');
INSERT INTO `sys_job_log` VALUES ('f1f61f9a-9915-4daa-b881-75c8db0dcc50', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：13002毫秒', '0', '', '2021-05-17 10:33:21', '2021-05-17 10:33:34', '2021-05-17 10:33:30', '1');
INSERT INTO `sys_job_log` VALUES ('f3685eb9-2429-4bb4-b646-ac7f3335fcf6', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:01:00', '2021-04-08 19:01:00', '2021-04-08 19:01:00', '0.6308700672324984');
INSERT INTO `sys_job_log` VALUES ('f4463bef-05a7-4b52-8d5d-f52f63386863', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：15毫秒', '0', '', '2021-05-01 16:00:00', '2021-05-01 16:00:00', '2021-05-01 16:00:00', '0');
INSERT INTO `sys_job_log` VALUES ('f7f10c1f-28ed-4c23-a1ef-e907f507ae4e', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：1毫秒', '0', '', '2021-04-08 19:18:00', '2021-04-08 19:18:00', '2021-04-08 19:18:00', '0.5432653214487314');
INSERT INTO `sys_job_log` VALUES ('f85dfc74-e22d-4bcc-be53-a4143c0edb5b', '49ef9c73-b619-471d-80e4-826a26904da6', NULL, NULL, NULL, '0', '支付报表生成', 'DEFAULT', 'dcwltPayBatchTask.statistics(\'yyyy-MM-dd\')', '支付报表生成 总共耗时：767毫秒', '0', '', '2021-04-24 01:00:00', '2021-04-24 01:00:01', '2021-04-24 01:00:00', NULL);
INSERT INTO `sys_job_log` VALUES ('fb5faf4a-d8d1-47e2-a6a5-77250cb89303', 'b74d3599-ff7a-4841-bf28-373861e6794f', NULL, NULL, NULL, '0', '抽数任务', 'DEFAULT', 'dcwltPayBatchTask.checkData(${yyyyMMdd})', '抽数任务 总共耗时：789毫秒', '0', '', '2021-05-08 16:00:00', '2021-05-08 16:00:01', '2021-05-08 16:00:12', '0');
INSERT INTO `sys_job_log` VALUES ('ffc500d8-664f-4029-b8f8-7390a1fd8334', '2666bd30-864c-11eb-a040-9c5c8e383269', NULL, NULL, NULL, '0', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '系统默认（无参） 总共耗时：2毫秒', '0', '', '2021-04-08 19:00:30', '2021-04-08 19:00:30', '2021-04-08 19:00:30', '0.581587027883644');

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
) ENGINE = InnoDB AUTO_INCREMENT = 2098 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 61, 'system', NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', '2018-03-16 11:33:00', 'admin', '2021-05-24 13:29:41', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 76, 'monitor', NULL, 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2018-03-16 11:33:00', 'admin', '2021-05-24 13:29:33', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 86, 'tool', NULL, 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2018-03-16 11:33:00', 'admin', '2021-05-24 13:29:19', '系统工具目录');
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
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2070, 2, 'job', 'monitor/job/index', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2018-03-16 11:33:00', 'admin', '2021-04-20 18:07:21', '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, 'Sentinel控制台', 2, 3, 'http://localhost:8718', '', 1, 0, 'C', '0', '0', 'monitor:sentinel:list', 'sentinel', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '流量控制菜单');
INSERT INTO `sys_menu` VALUES (112, 'Nacos控制台', 2, 4, 'http://localhost:8848/nacos', '', 1, 0, 'C', '0', '0', 'monitor:nacos:list', 'nacos', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '服务治理菜单');
INSERT INTO `sys_menu` VALUES (113, 'Admin控制台', 2, 5, 'http://localhost:9100/login', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO `sys_menu` VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO `sys_menu` VALUES (116, '系统接口', 3, 3, 'http://localhost:8000/swagger-ui.html', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统接口菜单');
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
INSERT INTO `sys_menu` VALUES (2006, '机构信息查询', 2012, 1, 'party', 'pay-batch/party/index', 1, 0, 'C', '0', '0', 'pay-batch:party:list', 'party', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 17:51:45', '机构信息查询菜单');
INSERT INTO `sys_menu` VALUES (2012, 'DCEP支付', 0, 0, 'pay', NULL, 1, 0, 'M', '0', '0', '', 'pay', 'admin', '2021-02-26 17:25:41', 'admin', '2021-02-26 17:54:53', '');
INSERT INTO `sys_menu` VALUES (2013, '操作日志记录', 3, 1, 'log', 'system/log/index', 1, 0, 'C', '1', '0', 'system:log:list', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 17:57:48', '操作日志记录菜单');
INSERT INTO `sys_menu` VALUES (2014, '操作日志记录查询', 2013, 1, '#', '', 1, 0, 'F', '0', '0', 'system:log:query', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2015, '操作日志记录新增', 2013, 2, '#', '', 1, 0, 'F', '0', '0', 'system:log:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2016, '操作日志记录修改', 2013, 3, '#', '', 1, 0, 'F', '0', '0', 'system:log:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2017, '操作日志记录删除', 2013, 4, '#', '', 1, 0, 'F', '0', '0', 'system:log:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2018, '操作日志记录导出', 2013, 5, '#', '', 1, 0, 'F', '0', '0', 'system:log:export', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2019, '金融登记簿查询', 2012, 21, 'transdtl', 'pay-batch/transdtl/index', 1, 0, 'C', '0', '0', 'pay-batch:transdtl:list', 'excel', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 17:48:01', '金融登记簿查询菜单');
INSERT INTO `sys_menu` VALUES (2025, '登入登出查询', 2012, 5, 'loginlogout', 'pay-batch/loginlogout/index', 1, 0, 'C', '0', '0', 'pay-batch:loginlogout:list', 'checkbox', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 17:47:36', '登入登出查询菜单');
INSERT INTO `sys_menu` VALUES (2026, '自由格式查询', 2012, 8, 'freefrmt', 'pay-batch/freefrmt/index', 1, 0, 'C', '0', '0', 'pay-batch:freefrmt:list', 'code', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 17:50:30', '自由格式查询菜单');
INSERT INTO `sys_menu` VALUES (2031, '业务权限查询', 2012, 2, 'partyauth', 'pay-batch/partyauth/index', 1, 0, 'C', '0', '0', 'pay-batch:partyauth:list', 'lock', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 17:47:07', '业务权限查询菜单');
INSERT INTO `sys_menu` VALUES (2043, '对账汇总', 2069, 31, 'checkpath', 'pay-batch/checkpath/index', 1, 0, 'C', '0', '0', 'pay-batch:checkpath:list', 'checkbox', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 18:02:58', '对账汇总菜单');
INSERT INTO `sys_menu` VALUES (2044, '资金调整汇总核对', 3, 1, 'checkclear', 'pay-batch/checkclear/index', 1, 0, 'C', '1', '0', 'pay-batch:checkclear:list', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 17:55:47', '资金调整汇总核对菜单');
INSERT INTO `sys_menu` VALUES (2045, '资金调整汇总核对查询', 2044, 1, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:checkclear:query', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2046, '资金调整汇总核对新增', 2044, 2, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:checkclear:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2047, '资金调整汇总核对修改', 2044, 3, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:checkclear:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2048, '资金调整汇总核对删除', 2044, 4, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:checkclear:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2049, '资金调整汇总核对导出', 2044, 5, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:checkclear:export', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2050, '兑出兑回报表', 2068, 41, 'financereport', 'pay-batch/financereport/index', 1, 0, 'C', '0', '0', 'pay-batch:financereport:list', 'money', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 18:01:19', '金融交易统计报表菜单');
INSERT INTO `sys_menu` VALUES (2051, '金融交易统计报表查询', 2050, 1, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:financereport:query', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2052, '金融交易统计报表新增', 2050, 2, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:financereport:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2053, '金融交易统计报表修改', 2050, 3, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:financereport:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2054, '金融交易统计报表删除', 2050, 4, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:financereport:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2055, '金融交易统计报表导出', 2050, 5, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:financereport:export', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2056, '账户挂接报表', 2068, 42, 'nonfinancereport', 'pay-batch/nonfinancereport/index', 1, 0, 'C', '0', '0', 'pay-batch:nonfinancereport:list', 'log', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-20 18:01:27', '非金融交易统计报表菜单');
INSERT INTO `sys_menu` VALUES (2057, '非金融交易统计报表查询', 2056, 1, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:nonfinancereport:query', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2058, '非金融交易统计报表新增', 2056, 2, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:nonfinancereport:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2059, '非金融交易统计报表修改', 2056, 3, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:nonfinancereport:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2060, '非金融交易统计报表删除', 2056, 4, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:nonfinancereport:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2061, '非金融交易统计报表导出', 2056, 5, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:nonfinancereport:export', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2063, '应用参数配置', 2012, 61, 'paramconfig', 'pay-batch/paramconfig/index', 1, 0, 'C', '0', '0', '', 'dict', 'admin', '2021-04-14 19:49:09', 'admin', '2021-04-21 09:16:39', '');
INSERT INTO `sys_menu` VALUES (2064, '对账明细（不平）', 2069, 32, 'checkpathdetails', 'pay-batch/checkpath/checkpathdetails', 1, 0, 'C', '0', '0', '', 'build', 'admin', '2021-04-16 15:08:03', 'admin', '2021-04-20 18:03:07', '');
INSERT INTO `sys_menu` VALUES (2065, '绑卡协议信息查询', 2012, 7, 'signinfo', 'system/signinfo/index', 1, 0, 'C', '1', '0', 'system:signinfo:list', 'validCode', 'admin', '2021-04-16 15:08:47', 'admin', '2021-05-24 14:00:10', '');
INSERT INTO `sys_menu` VALUES (2066, '绑卡协议流水查询', 2012, 23, 'signjrn', 'system/signjrn/index', 1, 0, 'C', '1', '0', 'system:signjrn:list', 'log', 'admin', '2021-04-16 16:15:50', 'admin', '2021-05-24 14:00:17', '');
INSERT INTO `sys_menu` VALUES (2067, '重发申请', 2012, 12, '/resend/apply', NULL, 1, 0, 'M', '0', '0', '', 'guide', 'admin', '2021-04-16 19:28:28', 'admin', '2021-04-16 19:28:49', '');
INSERT INTO `sys_menu` VALUES (2068, '报表', 0, 51, 'report', NULL, 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2021-04-20 18:00:27', 'admin', '2021-05-24 13:30:36', '');
INSERT INTO `sys_menu` VALUES (2069, '对账', 0, 31, 'check', NULL, 1, 0, 'M', '0', '0', '', 'clipboard', 'admin', '2021-04-20 18:02:45', 'admin', '2021-05-24 13:30:27', '');
INSERT INTO `sys_menu` VALUES (2070, '调度管理', 0, 3, 'job', NULL, 1, 0, 'M', '0', '0', '', 'time-range', 'admin', '2021-04-20 18:06:33', 'admin', '2021-04-20 18:06:54', '');
INSERT INTO `sys_menu` VALUES (2071, '异常监控', 2, 1, 'exmonitor', 'pay-batch/exmonitor/index', 1, 0, 'C', '0', '0', 'monitor:exmonitor:list', 'monitor', 'admin', '2021-04-25 14:25:58', 'admin', '2021-05-11 17:48:00', '');
INSERT INTO `sys_menu` VALUES (2072, '证书管理', 2012, 71, 'certs', 'pay-batch/certs/index', 1, 0, 'C', '0', '0', 'pay-batch:certs:list', 'tool', 'admin', '2018-03-01 00:00:00', 'admin', '2021-04-25 22:13:38', '证书管理菜单');
INSERT INTO `sys_menu` VALUES (2073, '证书管理查询', 2072, 1, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:certs:query', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2074, '证书管理新增', 2072, 2, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:certs:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2075, '证书管理修改', 2072, 3, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:certs:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2076, '证书管理删除', 2072, 4, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:certs:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2077, '证书管理导出', 2072, 5, '#', '', 1, 0, 'F', '0', '0', 'pay-batch:certs:export', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES (2084, '任务管理', 0, 4, 'task', NULL, 1, 0, 'M', '0', '0', NULL, 'system', 'admin', '2021-05-06 10:01:17', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2085, '任务组管理', 2084, 1, 'taskgroupinfo', 'pay-batch/taskinfo/index', 1, 0, 'C', '0', '0', 'task:taskgroupinfo:list', 'table', 'admin', '2021-05-06 10:02:14', 'admin', '2021-05-08 10:41:31', '');
INSERT INTO `sys_menu` VALUES (2086, '任务执行日志', 2084, 2, 'taskgroupexec', 'pay-batch/taskexec/index', 1, 0, 'C', '0', '0', 'task:taskgroupexec:list', 'dashboard', 'admin', '2021-05-06 13:32:29', 'admin', '2021-05-08 10:32:37', '');
INSERT INTO `sys_menu` VALUES (2087, '任务组添加', 2085, 1, '', NULL, 1, 0, 'F', '0', '0', 'task:taskgroupinfo:add', '#', 'admin', '2021-05-08 10:10:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2088, '任务组更新', 2085, 2, '', NULL, 1, 0, 'F', '0', '0', 'task:taskgroupinfo:edit', '#', 'admin', '2021-05-08 10:10:55', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2089, '任务组删除', 2085, 3, '', NULL, 1, 0, 'F', '0', '0', 'task:taskgroupinfo:remove', '#', 'admin', '2021-05-08 10:11:19', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2090, '任务信息管理', 2084, 1, 'taskinfo', NULL, 1, 0, 'C', '1', '0', 'task:taskinfo:list', 'dict', 'admin', '2021-05-08 10:24:12', 'admin', '2021-05-08 10:32:08', '');
INSERT INTO `sys_menu` VALUES (2091, '任务信息添加', 2090, 1, '', NULL, 1, 0, 'F', '0', '0', 'task:taskinfo:add', '#', 'admin', '2021-05-08 10:29:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2092, '任务信息更新', 2090, 2, '', NULL, 1, 0, 'F', '0', '0', 'task:taskinfo:edit', '#', 'admin', '2021-05-08 10:29:20', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2093, '任务信息删除', 2090, 3, '', NULL, 1, 0, 'F', '0', '0', 'task:taskinfo:remove', '#', 'admin', '2021-05-08 10:29:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2094, '任务执行日志详情', 2084, 3, 'taskexec', NULL, 1, 0, 'C', '1', '0', 'task:taskexec:list', 'row', 'admin', '2021-05-08 10:31:24', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2095, '钱柜', 0, 34, 'process', NULL, 1, 0, 'M', '0', '0', NULL, 'date-range', 'admin', '2021-05-25 10:54:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2096, '运营机构钱柜管理', 2095, 1, 'cashparty', 'pay-batch/cashparty/index', 1, 0, 'C', '0', '0', NULL, 'pay', 'admin', '2021-05-25 16:53:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2097, '钱柜出入库管理', 2095, 2, 'process', 'pay-batch/process/index', 1, 0, 'C', '0', '0', NULL, 'example', 'admin', '2021-05-25 20:08:53', '', NULL, '');

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
