# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: njreader.rwlb.rds.aliyuncs.com (MySQL 5.6.16-log)
# Database: db_game
# Generation Time: 2021-09-07 06:42:58 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table qrtz_blob_triggers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `TRIGGER_NAME` varchar(50) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_calendars
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `CALENDAR_NAME` varchar(50) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_cron_triggers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `TRIGGER_NAME` varchar(50) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `CRON_EXPRESSION` varchar(50) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_fired_triggers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(50) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `INSTANCE_NAME` varchar(50) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(50) DEFAULT NULL,
  `JOB_GROUP` varchar(50) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_job_details
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(100) NOT NULL,
  `JOB_NAME` varchar(50) NOT NULL,
  `JOB_GROUP` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(50) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(550) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_locks
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_paused_trigger_grps
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_scheduler_state
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `INSTANCE_NAME` varchar(50) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_simple_triggers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `TRIGGER_NAME` varchar(50) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_simprop_triggers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `TRIGGER_NAME` varchar(50) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table qrtz_triggers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(50) NOT NULL,
  `TRIGGER_NAME` varchar(50) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `JOB_NAME` varchar(50) NOT NULL,
  `JOB_GROUP` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(50) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(50) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '??????ID',
  `balance` int(11) NOT NULL COMMENT '??????????????????',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '?????????0??????????????????1?????????',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_user` (`user_id`),
  KEY `idx_time` (`create_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_account_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_account_detail`;

CREATE TABLE `t_account_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '??????ID',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '?????????1?????????2?????????3?????? 4?????????5?????????-1 ??????',
  `credit` int(11) NOT NULL DEFAULT '0' COMMENT '??????/?????? ??????',
  `fee` int(11) DEFAULT '0' COMMENT '?????????',
  `description` varchar(50) NOT NULL DEFAULT '' COMMENT '??????',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '??????',
  `confirm` int(1) NOT NULL DEFAULT '0' COMMENT '????????????????????????confirm=1?????????',
  `success` int(11) NOT NULL DEFAULT '0',
  `balance` int(11) NOT NULL DEFAULT '0' COMMENT '??????????????????',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_type` (`type`),
  KEY `idx_confirm` (`confirm`),
  KEY `idx_success` (`success`),
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_bet_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_bet_history`;

CREATE TABLE `t_bet_history` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '??????id',
  `game_id` int(11) NOT NULL COMMENT '??????id',
  `period` bigint(20) NOT NULL COMMENT '???',
  `target` varchar(10) NOT NULL DEFAULT '' COMMENT '??????',
  `bet` varchar(200) NOT NULL DEFAULT '' COMMENT '??????',
  `fee` varchar(10) NOT NULL DEFAULT '0',
  `odds` varchar(5) NOT NULL DEFAULT '' COMMENT '??????',
  `credit` int(11) NOT NULL DEFAULT '0' COMMENT '????????????',
  `result` varchar(10) NOT NULL DEFAULT '' COMMENT '????????????',
  `description` varchar(10) NOT NULL DEFAULT '' COMMENT '????????????',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '???????????????0????????????1?????????',
  `reward` int(11) NOT NULL DEFAULT '0' COMMENT '????????????',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_game_period` (`game_id`,`period`),
  KEY `idx_user` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_bet_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_bet_item`;

CREATE TABLE `t_bet_item` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `target` varchar(3) NOT NULL DEFAULT '',
  `high` int(11) NOT NULL DEFAULT '0',
  `low` int(11) NOT NULL DEFAULT '0',
  `odd` int(11) NOT NULL DEFAULT '0',
  `even` int(11) NOT NULL DEFAULT '0',
  `num` int(11) NOT NULL DEFAULT '0',
  `hign_sum` int(11) NOT NULL DEFAULT '0',
  `low_sum` int(11) NOT NULL DEFAULT '0',
  `odd_sum` int(11) NOT NULL DEFAULT '0',
  `even_sum` int(11) NOT NULL DEFAULT '0',
  `active` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_game
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_game`;

CREATE TABLE `t_game` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `cover` varchar(200) NOT NULL DEFAULT '',
  `description` varchar(200) NOT NULL DEFAULT '',
  `link` varchar(200) NOT NULL DEFAULT '',
  `cron` varchar(30) NOT NULL DEFAULT '',
  `type` varchar(5) NOT NULL DEFAULT '',
  `prev_period` bigint(20) DEFAULT NULL COMMENT '???????????????',
  `result` varchar(10) DEFAULT NULL COMMENT '?????????????????????',
  `sum` int(11) NOT NULL DEFAULT '-1' COMMENT '??????????????????',
  `curr_period` bigint(20) NOT NULL COMMENT '????????????',
  `next_period` bigint(20) NOT NULL COMMENT '???????????????',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `enabled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_game_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_game_config`;

CREATE TABLE `t_game_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `value` varchar(10) NOT NULL DEFAULT '' COMMENT '??????',
  `description` varchar(20) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_game_odds11111
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_game_odds11111`;

CREATE TABLE `t_game_odds11111` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `name` varchar(11) NOT NULL DEFAULT '',
  `rate_field` varchar(10) NOT NULL DEFAULT '' COMMENT '?????????',
  `rate` varchar(4) NOT NULL DEFAULT '' COMMENT '?????????',
  `multi` int(11) NOT NULL DEFAULT '0' COMMENT '??????????????????',
  `priority` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_game_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_game_task`;

CREATE TABLE `t_game_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `period` bigint(20) NOT NULL COMMENT '??????',
  `result` varchar(20) DEFAULT '',
  `sum` int(11) NOT NULL DEFAULT '-1',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_game_period` (`game_id`,`period`),
  KEY `idx_status` (`status`),
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_sinopay_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sinopay_detail`;

CREATE TABLE `t_sinopay_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '??????ID',
  `currency` varchar(10) DEFAULT '' COMMENT '??????????????????????????????????????????????????????????????? CAD???USD, CNY???IDR???THB',
  `money` decimal(11,2) DEFAULT NULL COMMENT '???????????????????????????????????? 1.25???????????????????????????????????????????????????????????????',
  `country` varchar(20) DEFAULT '' COMMENT '??????/??????????????????????????????????????????',
  `out_order_id` varchar(50) DEFAULT '' COMMENT '?????????????????????????????????????????????',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '??????????????????????????? ',
  `type` varchar(20) DEFAULT '' COMMENT '????????? WECHATPAYH5???ALIPAYH5???UNIONPAYH5???USERSELECT, ???????????????????????????',
  `order_id` varchar(50) DEFAULT '' COMMENT 'Sinopay?????????id',
  `provider_order_id` varchar(50) DEFAULT '' COMMENT '??????????????????id,????????????????????????????????????????????????',
  `received` decimal(11,0) DEFAULT '0' COMMENT '?????????????????????',
  `status` varchar(20) DEFAULT '' COMMENT '????????????',
  `trade_type` int(11) DEFAULT NULL COMMENT '????????????',
  `bank` varchar(50) DEFAULT '',
  `branch` varchar(50) DEFAULT NULL COMMENT '??????',
  `owner` varchar(20) DEFAULT '' COMMENT '???????????????',
  `account` varchar(50) DEFAULT NULL COMMENT '????????????',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(30) NOT NULL DEFAULT '' COMMENT '??????',
  `real_name` varchar(30) DEFAULT '' COMMENT '????????????',
  `avatar` varchar(200) DEFAULT '' COMMENT '??????',
  `age` int(11) DEFAULT '0',
  `gender` int(11) DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `roles` varchar(30) NOT NULL DEFAULT 'USER' COMMENT 'USER, ADMIN',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????????????',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????????????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_user_auth
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_auth`;

CREATE TABLE `t_user_auth` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `mode` varchar(10) NOT NULL DEFAULT '' COMMENT '1 ????????? 2 Facebook',
  `union_id` varchar(50) NOT NULL DEFAULT '',
  `open_id` varchar(100) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_user_bank
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_bank`;

CREATE TABLE `t_user_bank` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '??????ID',
  `bank` varchar(50) NOT NULL DEFAULT '' COMMENT '????????????',
  `branch` varchar(100) NOT NULL DEFAULT '' COMMENT '????????????',
  `real_name` varchar(30) NOT NULL DEFAULT '' COMMENT '???????????????',
  `card_no` varchar(30) NOT NULL DEFAULT '' COMMENT '??????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_group`;

CREATE TABLE `xxl_job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '?????????AppName',
  `title` varchar(12) NOT NULL COMMENT '???????????????',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '????????????????????????0=???????????????1=????????????',
  `address_list` varchar(512) DEFAULT NULL COMMENT '?????????????????????????????????????????????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_info`;

CREATE TABLE `xxl_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '???????????????ID',
  `job_cron` varchar(128) NOT NULL COMMENT '????????????CRON',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '??????',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '????????????',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '?????????????????????',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '???????????????handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '?????????????????????',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '??????????????????',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '????????????????????????????????????',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '??????????????????',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE??????',
  `glue_source` mediumtext COMMENT 'GLUE?????????',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE??????',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE????????????',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '?????????ID?????????????????????',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '???????????????0-?????????1-??????',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '??????????????????',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '??????????????????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_lock
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_lock`;

CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '?????????',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_log`;

CREATE TABLE `xxl_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '???????????????ID',
  `job_id` int(11) NOT NULL COMMENT '???????????????ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '???????????????????????????????????????',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '???????????????handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '?????????????????????',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '??????????????????????????????????????? 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '??????????????????',
  `trigger_time` datetime DEFAULT NULL COMMENT '??????-??????',
  `trigger_code` int(11) NOT NULL COMMENT '??????-??????',
  `trigger_msg` text COMMENT '??????-??????',
  `handle_time` datetime DEFAULT NULL COMMENT '??????-??????',
  `handle_code` int(11) NOT NULL COMMENT '??????-??????',
  `handle_msg` text COMMENT '??????-??????',
  `alarm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '???????????????0-?????????1-???????????????2-???????????????3-????????????',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_log_report
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_log_report`;

CREATE TABLE `xxl_job_log_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '??????-??????',
  `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '?????????-????????????',
  `suc_count` int(11) NOT NULL DEFAULT '0' COMMENT '????????????-????????????',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '????????????-????????????',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_logglue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_logglue`;

CREATE TABLE `xxl_job_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '???????????????ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE??????',
  `glue_source` mediumtext COMMENT 'GLUE?????????',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE??????',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_registry
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_registry`;

CREATE TABLE `xxl_job_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(150) NOT NULL,
  `registry_value` varchar(150) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table xxl_job_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_user`;

CREATE TABLE `xxl_job_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '??????',
  `password` varchar(50) NOT NULL COMMENT '??????',
  `role` tinyint(4) NOT NULL COMMENT '?????????0-???????????????1-?????????',
  `permission` varchar(255) DEFAULT NULL COMMENT '??????????????????ID???????????????????????????',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
