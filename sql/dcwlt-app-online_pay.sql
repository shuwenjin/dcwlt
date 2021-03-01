/*
 Navicat Premium Data Transfer

 Source Server         : local_smart
 Source Server Type    : MySQL
 Source Server Version : 50710
 Source Host           : localhost:3306
 Source Schema         : ry-cloud

 Target Server Type    : MySQL
 Target Server Version : 50710
 File Encoding         : 65001

 Date: 01/03/2021 11:31:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pay_comm_core_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_core_config`;
CREATE TABLE `pay_comm_core_config` (
  `serverid` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '服务id',
  `acctmeth` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '核算方式',
  `serverclass` varchar(300) COLLATE utf8mb4_bin NOT NULL COMMENT '服务类名',
  `reverseflag` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '冲正服务使用方式',
  PRIMARY KEY (`serverid`,`acctmeth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='核心服务映射配置表';

-- ----------------------------
-- Table structure for pay_comm_coretradetypeinfo
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_coretradetypeinfo`;
CREATE TABLE `pay_comm_coretradetypeinfo` (
  `paypath` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '通道代码',
  `acctmeth` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '核算方式',
  `seq` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '序号',
  `coreaccmode` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记账方式',
  `coreprdmode` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品模型',
  `coreprdcode` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品代码',
  `coreeventcode` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '事件代码',
  `coreaccelement` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算要素',
  `coreaccbrnotype1` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构类型1',
  `coreaccbrno1` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构1',
  `coreaccbrnotype2` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构类型2',
  `coreaccbrno2` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构2',
  `coreaccbrnotype3` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构类型3',
  `coreaccbrno3` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构3',
  `coreaccbrnotype4` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构类型4',
  `coreaccbrno4` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构4',
  `coreaccbrnotype5` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构类型5',
  `coreaccbrno5` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算机构5',
  `coreaccamounttype1` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型1',
  `coreaccamountptr1` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针1',
  `coreaccamounttype2` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型2',
  `coreaccamountptr2` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针2',
  `coreaccamounttype3` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型3',
  `coreaccamountptr3` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针3',
  `coreaccamounttype4` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型4',
  `coreaccamountptr4` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针4',
  `coreaccamounttype5` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型5',
  `coreaccamountptr5` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针5',
  `coreaccamounttype6` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型6',
  `coreaccamountptr6` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针6',
  `coreaccamounttype7` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型7',
  `coreaccamountptr7` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针7',
  `coreaccamounttype8` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型8',
  `coreaccamountptr8` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针8',
  `coreaccamounttype9` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型9',
  `coreaccamountptr9` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针9',
  `coreaccamounttype10` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额类型10',
  `coreaccamountptr10` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核算金额指针10',
  `coreprtabscode` varchar(3) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '打印摘要代码',
  `corefeecode` varchar(5) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '费用代码',
  `coredrtrxtype` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易类型',
  `coremateaccno` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认对手账号',
  `corematename` varchar(12) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认对手户名',
  `coremateunitecode` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认对手联行号',
  `coremateunitename` varchar(12) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认对手联行名',
  `coremateflag` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '对手信息获取方式',
  PRIMARY KEY (`paypath`,`acctmeth`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='核算规则配置表';

-- ----------------------------
-- Table structure for pay_comm_excepteventconfig
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_excepteventconfig`;
CREATE TABLE `pay_comm_excepteventconfig` (
  `excepteventcode` varchar(30) NOT NULL COMMENT '异常事件代码',
  `exceptdealmode` varchar(50) NOT NULL COMMENT '异常处理模式',
  `excepteventtrxcode` varchar(60) NOT NULL COMMENT '异常处理交易码',
  `excepteventdealmaxcount` varchar(3) NOT NULL COMMENT '异常处理最大尝试次数',
  `excepteventdealtype` varchar(4) NOT NULL COMMENT '异常处理类型',
  `excepteventdealintervalmin` varchar(6) NOT NULL COMMENT '处理时间间隔',
  `excepteventremark` varchar(100) NOT NULL COMMENT '异常事件描述',
  PRIMARY KEY (`excepteventcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常事件配置表';

-- ----------------------------
-- Table structure for pay_comm_excepteventinfo
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_excepteventinfo`;
CREATE TABLE `pay_comm_excepteventinfo` (
  `excepteventdate` varchar(8) NOT NULL COMMENT '异常事件登记日期',
  `excepteventserno` varchar(20) NOT NULL COMMENT '异常事件登记流水',
  `excepteventtime` varchar(6) NOT NULL COMMENT '异常事件登记时间',
  `excepteventcode` varchar(30) NOT NULL COMMENT '异常事件代码',
  `excepteventseqno` varchar(40) NOT NULL COMMENT '渠道系统流水',
  `excepteventdealcount` varchar(3) NOT NULL COMMENT '异常事件当前处理次数',
  `excepteventsysstatus` varchar(4) NOT NULL COMMENT '异常事件处理状态',
  `excepteventerrorcode` varchar(10) DEFAULT NULL COMMENT '异常处理业务错误码',
  `excepteventerrormsg` varchar(300) DEFAULT NULL COMMENT '异常处理业务错误信息',
  `excepteventdealpath` varchar(40) DEFAULT NULL COMMENT '异常事件的处理路径，登记处理服务器',
  `excepteventcontext` varchar(1200) NOT NULL COMMENT '异常事件参数',
  `lastupdate` varchar(8) DEFAULT NULL COMMENT '异常事件最后处理日期',
  `lastuptime` varchar(6) DEFAULT NULL COMMENT '异常事件最后处理时间',
  PRIMARY KEY (`excepteventdate`,`excepteventserno`),
  UNIQUE KEY `idx_pay_comm_excepteventinfo` (`excepteventcode`,`excepteventseqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常事件流水表';

-- ----------------------------
-- Table structure for pay_comm_idempotent
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_idempotent`;
CREATE TABLE `pay_comm_idempotent` (
  `msgid` varchar(32) NOT NULL COMMENT '报文标识号',
  `msgtp` varchar(100) DEFAULT NULL COMMENT '报文编号',
  `sender` varchar(14) DEFAULT NULL COMMENT '发送机构',
  `receiver` varchar(14) DEFAULT NULL COMMENT '接收机构',
  `snddttm` varchar(19) DEFAULT NULL COMMENT '报文发送时间',
  `direct` varchar(1) DEFAULT NULL COMMENT '方向',
  `hostname` varchar(60) DEFAULT NULL COMMENT '主机名',
  PRIMARY KEY (`msgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易堵重表';

-- ----------------------------
-- Table structure for pay_comm_monitor
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_monitor`;
CREATE TABLE `pay_comm_monitor` (
  `exceptdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '异常登记日期',
  `exceptserno` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '异常登记流水',
  `excepttime` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT 'isodate数据',
  `exceptscenario` varchar(35) COLLATE utf8mb4_bin NOT NULL COMMENT '异常交易场景',
  `exceptparams` varchar(120) COLLATE utf8mb4_bin NOT NULL COMMENT '优先存msgid或者平台日期流水',
  `exceptcontext` varchar(1200) COLLATE utf8mb4_bin NOT NULL COMMENT '例如：汇总对账报文711入cnap_online_checkpath失败',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL,
  `lastuptime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`exceptdate`,`exceptserno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pay_comm_party
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_party`;
CREATE TABLE `pay_comm_party` (
  `partyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '机构编码',
  `partytype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '机构类型',
  `partyname` varchar(180) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '机构名称',
  `partyalias` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '机构标识',
  `partystatus` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '机构状态',
  `status` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '撤销状态',
  `contact` varchar(180) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电话',
  `mail` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `fax` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '传真号',
  `effectdate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '失效日期',
  `ineffectivedate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '失效日期',
  `changenumber` varchar(8) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '变更期数',
  `changecircletimes` varchar(8) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '变更记录条目',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`),
  KEY `pay_inst_info_idx1` (`effectdate`,`lastupdate`),
  KEY `pay_inst_info_idx2` (`partyalias`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='机构表';

-- ----------------------------
-- Table structure for pay_comm_party_tobeffect
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_party_tobeffect`;
CREATE TABLE `pay_comm_party_tobeffect` (
  `partyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '机构编码',
  `changetype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '变更类型',
  `effectivetype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '生效类型',
  `partytype` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '机构类型',
  `partyname` varchar(180) COLLATE utf8mb4_bin NOT NULL COMMENT '机构名称',
  `partyalias` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '机构标识',
  `partystatus` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '机构状态',
  `contact` varchar(180) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '电话',
  `mail` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '邮箱',
  `fax` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '传真号',
  `effectdate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生效日期',
  `ineffectivedate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '失效日期',
  `changenumber` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '变更期数',
  `changecircletimes` varchar(8) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '变更记录条目',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`),
  KEY `pay_inst_info_idx1` (`effectdate`,`lastupdate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='机构待生效表';

-- ----------------------------
-- Table structure for pay_comm_partyauth
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_partyauth`;
CREATE TABLE `pay_comm_partyauth` (
  `partyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `msgtype` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '报文类型',
  `tradectgycode` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '业务类型',
  `sendauth` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '发送权限标识',
  `recvauth` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '接收权限标识',
  `status` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '撤销状态1-生效中 0-已撤销',
  `effectdate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生效日期',
  `ineffectivedate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '失效日期',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`,`msgtype`,`tradectgycode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='业务权限变更信息表';

-- ----------------------------
-- Table structure for pay_comm_partyauth_tobeffect
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_partyauth_tobeffect`;
CREATE TABLE `pay_comm_partyauth_tobeffect` (
  `partyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '机构编码',
  `msgtype` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `tradectgycode` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '业务类型',
  `sendauth` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '发起权限标识',
  `recvauth` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '接收权限标识',
  `changetype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '变更类型',
  `effectivetype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '生效类型',
  `effectdate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生效日期',
  `ineffectivedate` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '失效日期',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`partyid`,`msgtype`,`tradectgycode`),
  KEY `pay_comm_partyauth_tobeffect_effectdate_idx` (`effectdate`,`ineffectivedate`,`lastupdate`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='业务权限变更临时表';

-- ----------------------------
-- Table structure for pay_comm_rspcodemap
-- ----------------------------
DROP TABLE IF EXISTS `pay_comm_rspcodemap`;
CREATE TABLE `pay_comm_rspcodemap` (
  `paypath` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '通道',
  `srcid` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '源系统',
  `destid` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '目标系统',
  `txntype` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '交易类别',
  `srcrspcode` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '源响应码1',
  `srcrspcode2` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '源响应码2',
  `srcrspcodedsp` varchar(80) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '源响应信息描述',
  `destrspcode` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '目标响应码1',
  `destrspcode2` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '目标响应码2',
  `rspcodedsp` varchar(80) COLLATE utf8mb4_bin NOT NULL COMMENT '响应信息描述',
  `rsv1` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`paypath`,`srcid`,`destid`,`txntype`,`srcrspcode`,`srcrspcode2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='错误码映射表';

-- ----------------------------
-- Table structure for pay_online_checkclear
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkclear`;
CREATE TABLE `pay_online_checkclear` (
  `msgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `senderdatetime` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '报文发送时间',
  `instgdrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发起机构',
  `instddrctpty` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接收机构',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `cleardate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '清算日期',
  `clearcountnum` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '清算总笔数',
  `cleardbttotamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '清算借方总金额',
  `clearcbttotamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '清算贷方总金额',
  `clearnetnum` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '清算场次编号',
  `clearmsgid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '清算报文标识号',
  `cleardrct` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '清算借贷标识',
  `clearamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '清算金额',
  `batchid` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '批次号',
  `batchdrct` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '批次借贷标识',
  `batchnetamt` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '批次扎差净额',
  `lastupdate` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`,`batchid`,`clearnetnum`),
  KEY `pay_online_checkclear_clear_msg_id_idx` (`clearmsgid`) USING BTREE,
  KEY `pay_online_checkclear_clear_date_idx` (`cleardate`,`batchid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资金调整汇总核对表';

-- ----------------------------
-- Table structure for pay_online_checkfilelist
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkfilelist`;
CREATE TABLE `pay_online_checkfilelist` (
  `msgid` varchar(35) COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识',
  `batchid` varchar(13) COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `srcfilepath` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '源文件路径',
  `filename` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '文件名',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`,`batchid`,`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='通道对账明细文件列表';

-- ----------------------------
-- Table structure for pay_online_checkpath_main
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkpath_main`;
CREATE TABLE `pay_online_checkpath_main` (
  `paydate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(11) COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `msgid` varchar(35) COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `senderdatetime` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '报文发送时间',
  `instgdrctpty` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `instddrctpty` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '接收机构',
  `batchdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '批次日期',
  `batchid` varchar(13) COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `countnum` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '总笔数',
  `countamt` varchar(18) COLLATE utf8mb4_bin NOT NULL COMMENT '总金额',
  `ccy` varchar(3) COLLATE utf8mb4_bin NOT NULL COMMENT '货币代码',
  `dbitcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款笔数',
  `dbitcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款金额',
  `crdtcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款笔数',
  `crdtcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款金额',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  `reconindex` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区块链对账号',
  `remark` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `digitalenvelope` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数字信封',
  PRIMARY KEY (`paydate`,`payserno`),
  UNIQUE KEY `pay_batch_checkpath_idx1` (`msgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pay_online_checkpath_sub
-- ----------------------------
DROP TABLE IF EXISTS `pay_online_checkpath_sub`;
CREATE TABLE `pay_online_checkpath_sub` (
  `msgid` varchar(35) COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `batchdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '批次日期',
  `batchid` varchar(13) COLLATE utf8mb4_bin NOT NULL COMMENT '交易批次号',
  `splitnum` varchar(2) COLLATE utf8mb4_bin NOT NULL COMMENT '分片编号',
  `splitcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分片总笔数',
  `splitcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分片总金额',
  `splitdbitcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分片付款笔数',
  `splitdbitcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分片付款金额',
  `splitcrdtcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分片收款笔数',
  `splitcrdtcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分片收款金额',
  `msgtype` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `msgbizstatus` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '业务状态',
  `msgcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '总笔数',
  `msgcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '总金额',
  `msgdbitcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款笔数',
  `msgdbitcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款金额',
  `msgcrdtcountnum` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款笔数',
  `msgcrdtcountamt` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款金额',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`,`splitnum`,`msgtype`,`msgbizstatus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='通道对账汇总表';

-- ----------------------------
-- Table structure for pay_pay_accflow
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_accflow`;
CREATE TABLE `pay_pay_accflow` (
  `corereqdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '请求核心日期',
  `corereqserno` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '请求核心流水',
  `booktype` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '记账类型',
  `paydate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '业务日期',
  `payserno` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '业务流水',
  `brno` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '行所号',
  `tellerno` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '柜员号',
  `acctbrno` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记账网所',
  `currency` varchar(3) COLLATE utf8mb4_bin NOT NULL COMMENT '币种',
  `amount` varchar(18) COLLATE utf8mb4_bin NOT NULL COMMENT '金额',
  `feeamount` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手续费',
  `coresysid` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心系统标识',
  `coreintflag` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心接口标志',
  `revtranflag` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '反交易标志',
  `coretime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心时间',
  `coretrxtype` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心交易类型',
  `coretrxcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心交易代码',
  `coreprocstatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心状态',
  `coreretcode` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心返回代码',
  `coreretmsg` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心返回信息',
  `coreacctdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心返回日期',
  `coreserno` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心返回流水',
  `payeracct` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '付款人账号',
  `payername` varchar(244) COLLATE utf8mb4_bin NOT NULL COMMENT '付款人名称',
  `payeeacct` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '收款人账号',
  `payeename` varchar(244) COLLATE utf8mb4_bin NOT NULL COMMENT '收款人名称',
  `origcorereqdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原请求核心日期',
  `origcorereqserno` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原请求核心流水',
  `origpaydate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原业务日期',
  `origpayserno` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原业务流水',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastmicrosecond` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新毫秒',
  PRIMARY KEY (`corereqdate`,`corereqserno`),
  KEY `pay_pay_trade_jrn_ind01` (`paydate`,`payserno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='账务流水表';

-- ----------------------------
-- Table structure for pay_pay_notify
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_notify`;
CREATE TABLE `pay_pay_notify` (
  `txenddate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知日期',
  `txendserno` varchar(11) COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知请求流水',
  `txendmsgid` varchar(39) COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知报文标识号',
  `txendmsgtype` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '终态通知报文编号',
  `txendinstgpty` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '终态通知发起机构',
  `txendinstdpty` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '终态通知接收机构',
  `processstatus` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务状态',
  `processcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务处理码',
  `rejectcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '机构业务拒绝码',
  `rejectinf` varchar(420) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务拒绝信息',
  `msgid` varchar(39) COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `msgtype` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '原报文编号',
  `instgpty` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原发起机构',
  `instdpty` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原接收机构',
  `amount` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT '交易金额',
  `ccy` varchar(3) COLLATE utf8mb4_bin NOT NULL COMMENT '币种',
  `remark` varchar(560) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '终态通知备注',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新时间',
  `lastmicrosecond` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新时间毫秒',
  PRIMARY KEY (`txenddate`,`txendserno`),
  UNIQUE KEY `pay_pay_notify_msgid_idx` (`msgid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='终态通知请求登记表';

-- ----------------------------
-- Table structure for pay_pay_transdtl
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_transdtl`;
CREATE TABLE `pay_pay_transdtl` (
  `paydate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `direct` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '往来标识',
  `payflag` varchar(5) COLLATE utf8mb4_bin NOT NULL COMMENT '收付标识',
  `operstep` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作步骤',
  `operstatus` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作状态',
  `trxstatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务状态',
  `trxretcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务处理码',
  `trxretmsg` varchar(420) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务处理信息',
  `coreprocstatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心处理状态',
  `corereqdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心请求日期',
  `corereqserno` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心请求流水',
  `coreacctdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心返回日期',
  `coreserno` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心返回流水',
  `coreretcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心拒绝码',
  `coreretmsg` varchar(420) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '核心拒绝信息',
  `paypathdatetime` varchar(19) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道日期时间',
  `paypathserno` varchar(39) COLLATE utf8mb4_bin NOT NULL COMMENT '通道流水',
  `pathprocstatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道状态',
  `paypathrspstatus` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道回执业务状态',
  `paypathretcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道返回码',
  `paypathretmsg` varchar(420) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道返回信息',
  `paypathretdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道返回日期',
  `paypathretserno` varchar(39) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道返回流水',
  `batchid` varchar(13) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易批次号',
  `busichnl` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道大类',
  `busichnl2` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道中类',
  `busisysdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道日期',
  `busisysserno` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道流水',
  `busisystime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道时间',
  `msgtype` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `busitype` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务类型',
  `busikind` varchar(5) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务种类',
  `instgpty` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发起机构',
  `instdpty` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接收机构',
  `amount` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT '交易金额',
  `tradefundsource` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易资金来源',
  `tradepurpose` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易用途',
  `payerptyid` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人钱包所属机构',
  `payername` varchar(480) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人名称',
  `payeraccttype` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人账户类型',
  `payeracct` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人账户账号',
  `payerwalletid` varchar(68) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人钱包id',
  `payerwalletname` varchar(240) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人钱包名称',
  `payerwalletlv` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人钱包等级',
  `payerwallettype` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '付款人钱包类型',
  `payeeptyid` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人账户所属机构',
  `payeename` varchar(480) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人名称',
  `payeeaccttype` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人账户类型',
  `payeeacct` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人账户账号',
  `payeewalletid` varchar(68) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人钱包id',
  `payeewalletname` varchar(240) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人钱包名称',
  `payeewalletlv` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人钱包等级',
  `payeewallettype` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收款人钱包类型',
  `protocolnum` varchar(34) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '挂接协议号',
  `ccy` varchar(3) COLLATE utf8mb4_bin NOT NULL COMMENT '币种',
  `tellerno` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '柜员号',
  `zoneno` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分行号',
  `brno` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易行所号',
  `acctbrno` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '账务行所',
  `origchnl` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '源发起渠道大类',
  `origchnl2` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '源发起渠道中类',
  `origchnldtl` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '源发起渠道细分',
  `origmsgtype` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原报文编号',
  `origpaypathdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原业务通道日期',
  `origpaypathserno` varchar(35) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原业务通道流水',
  `summary` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '摘要码',
  `endtoendid` varchar(35) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '端对端标志',
  `lastupjrnno` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新流水',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新时间',
  `narrative` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '附言',
  `remark` varchar(560) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`paydate`,`payserno`),
  UNIQUE KEY `pay_pay_transdtl_paypathserno_idx` (`paypathserno`) USING BTREE,
  KEY `pay_pay_transdtl_busisysserno_idx` (`busisysserno`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='金融交易登记表';

-- ----------------------------
-- Table structure for pay_pay_transdtl_nonf
-- ----------------------------
DROP TABLE IF EXISTS `pay_pay_transdtl_nonf`;
CREATE TABLE `pay_pay_transdtl_nonf` (
  `msgid` varchar(35) COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `paydate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `paytime` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `payserno` varchar(11) COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `pkgno` varchar(15) COLLATE utf8mb4_bin NOT NULL COMMENT '报文编号',
  `drct` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '报文方向',
  `tradestatus` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '交易状态',
  `senderdatetime` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '报文发送时间',
  `instgdrctpty` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `instddrctpty` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '接收机构',
  `opterationtype` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作类型',
  `procstatus` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '业务处理状态',
  `rejectcode` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务拒绝码',
  `rejectinfo` varchar(315) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务拒绝信息',
  `tlrno` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '柜员号',
  `remark` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `messagecontext` varchar(3072) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '信息内容',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`msgid`),
  KEY `pay_free_talk_idx1` (`paydate`,`payserno`,`pkgno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='非金融登记簿';

-- ----------------------------
-- Table structure for pay_sign_signinfo
-- ----------------------------
DROP TABLE IF EXISTS `pay_sign_signinfo`;
CREATE TABLE `pay_sign_signinfo` (
  `paydate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `signno` varchar(34) COLLATE utf8mb4_bin NOT NULL COMMENT '挂接协议号',
  `signstatus` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '协议状态N：签约状态C：解约状态',
  `acctptyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户所属机构',
  `accttype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户类型',
  `acctid` varchar(68) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户账号',
  `acctname` varchar(960) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户户名',
  `idtype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件类型',
  `idno` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件号码',
  `telephone` varchar(70) COLLATE utf8mb4_bin NOT NULL COMMENT '银行预留手机号码',
  `walletptyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包开立所属机构编码',
  `walletid` varchar(68) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包id',
  `wallettype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包',
  `walletlevel` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '钱包等级WL01：一类钱包，WL02：二类钱包，WL03：三类钱包，WL04：四类钱包',
  `lastupjrnno` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新流水',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`signno`),
  UNIQUE KEY `pay_protocol_idx01` (`walletid`,`acctid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='协议表';

-- ----------------------------
-- Table structure for pay_sign_signinfo_jrn
-- ----------------------------
DROP TABLE IF EXISTS `pay_sign_signinfo_jrn`;
CREATE TABLE `pay_sign_signinfo_jrn` (
  `paydate` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '平台日期',
  `payserno` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '平台流水',
  `paytime` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '平台时间',
  `msgid` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '报文标识号',
  `instgpty` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '发起机构',
  `instdpty` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '接收机构',
  `direct` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '往来send:发送，recv：接收',
  `managetype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '管理类型MT01：身份认证,MT02：身份确认MT03：解约申请',
  `signtype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '签约类型SG00：不签约，SG01：签约',
  `signno` varchar(34) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '挂接协议号',
  `msgsendcode` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '动态关联码：msg+应答报文流水',
  `msgverifycode` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '动态验证码sm4加密存储',
  `trxstatus` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务处理状态：0-失败,1-成功,2-处理中',
  `trxretcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务处理码',
  `trxretmsg` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务处理信息',
  `rspmsgid` varchar(35) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应答报文标识号',
  `rspstatus` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应答回执状态',
  `rspcode` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应答业务处理码',
  `rspmsg` varchar(420) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应答业务处理信息',
  `acctptyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户所属机构',
  `accttype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户类型',
  `acctid` varchar(68) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户账号',
  `acctname` varchar(960) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人银行账户户名',
  `idtype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件类型',
  `idno` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '签约人证件号码',
  `telephone` varchar(70) COLLATE utf8mb4_bin NOT NULL COMMENT '银行预留手机号码',
  `walletptyid` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包开立所属机构编码',
  `walletid` varchar(68) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包id',
  `wallettype` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包类型',
  `walletlevel` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包等级',
  `lastupjrnno` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新流水',
  `lastupdate` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新日期',
  `lastuptime` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(560) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`paydate`,`payserno`),
  KEY `pay_protocol_jrn_idx01` (`walletid`,`msgsendcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
