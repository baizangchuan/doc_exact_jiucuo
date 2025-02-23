-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 111.9.47.74    Database: emr_parser
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hp_config_node`
--

DROP TABLE IF EXISTS `hp_config_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_config_node` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `config_node_key` varchar(255) DEFAULT NULL COMMENT '模板节点名称',
  `adm_column` varchar(255) DEFAULT NULL COMMENT '病历质检adm列名',
  `match_sample_num` int(8) DEFAULT NULL COMMENT '匹配样本数',
  `repeat_in_sample` int(4) DEFAULT NULL COMMENT '是否在一个样本里重复出现',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  `parse_task_id` bigint(11) DEFAULT NULL COMMENT '所属任务',
  `template_config_code` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '模板配置编码',
  `org_name` varchar(255) DEFAULT NULL COMMENT '机构',
  `rectified_time` datetime DEFAULT NULL COMMENT '纠正时间',
  `gmt_created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parse_task_template_config` (`parse_task_id`,`template_config_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=348 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='模板配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hp_emr_origin_record`
--

DROP TABLE IF EXISTS `hp_emr_origin_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_emr_origin_record` (
  `id` bigint(20) NOT NULL COMMENT '记录id',
  `content` longtext NOT NULL COMMENT '原记录内容',
  `gmt_created` datetime(6) NOT NULL,
  `gmt_modified` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hp_emr_record`
--

DROP TABLE IF EXISTS `hp_emr_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_emr_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_name` varchar(255) DEFAULT NULL COMMENT '医院',
  `content` longtext NOT NULL COMMENT '文书',
  `de_tagged` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否去标签',
  `record_type` varchar(255) DEFAULT NULL COMMENT '文书类型',
  `norm_record_type` varchar(255) DEFAULT NULL COMMENT '算法归一化的文书类型',
  `record_source` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '系统导入时要保存数据的来源表 ,文件上传时为上传文件id',
  `import_type` int(4) NOT NULL DEFAULT '0' COMMENT '0.系统导入 1.文件上传',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_record_source` (`record_source`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='解析样本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hp_parse_task`
--

DROP TABLE IF EXISTS `hp_parse_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_parse_task` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `sample_import_type` int(4) DEFAULT NULL COMMENT '样本源类型：  0、系统导入 1、手动上传',
  `sample_num` int(8) DEFAULT NULL COMMENT '样本数',
  `record_types` varchar(255) DEFAULT NULL COMMENT '文书类型',
  `org_name` varchar(255) DEFAULT NULL COMMENT '机构名称',
  `status` int(4) DEFAULT NULL COMMENT '状态',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `sample_source` varchar(64) DEFAULT NULL COMMENT '系统导入时要保存数据的来源表 ,文件上传时为上传文件id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='解析任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hp_sample_upload_log`
--

DROP TABLE IF EXISTS `hp_sample_upload_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_sample_upload_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sample_file_id` varchar(32) NOT NULL COMMENT '样本文件id',
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `file_size` bigint(20) NOT NULL COMMENT '文件大小',
  `sample_num` bigint(11) NOT NULL COMMENT '上传样本数量',
  `gmt_created` datetime NOT NULL COMMENT '上传时间',
  `gmt_modified` datetime NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hp_task_sample`
--

DROP TABLE IF EXISTS `hp_task_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_task_sample` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `parse_task_id` bigint(11) DEFAULT NULL,
  `emr_record_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='解析任务样本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hp_task_schema`
--

DROP TABLE IF EXISTS `hp_task_schema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_task_schema` (
  `parse_task_id` bigint(20) NOT NULL COMMENT '解析任务id',
  `schema_type` varchar(256) NOT NULL COMMENT '数据源类型',
  `schema_code` varchar(255) NOT NULL COMMENT '数据库shema_code',
  `table_name` varchar(255) NOT NULL COMMENT '表名',
  `record_content_column` varchar(255) NOT NULL COMMENT '记录内容列',
  `org_column` varchar(255) NOT NULL COMMENT '机构列',
  `record_type_column` varchar(255) DEFAULT NULL COMMENT '记录类型列',
  `record_tmpl_id_column` varchar(255) DEFAULT NULL COMMENT '记录模板id列',
  `record_created_time_column` varchar(255) DEFAULT NULL COMMENT '记录创建时间列',
  `record_tmpl_id` varchar(64) DEFAULT NULL COMMENT '记录模板id',
  `record_created_time` datetime DEFAULT NULL COMMENT '记录创建时间，大于这个时间的',
  `gmt_created` datetime(6) NOT NULL,
  `gmt_modified` datetime(6) NOT NULL,
  PRIMARY KEY (`parse_task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hp_template_config`
--

DROP TABLE IF EXISTS `hp_template_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hp_template_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_config_name` varchar(255) NOT NULL COMMENT '别名，可修改',
  `template_config_code` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '算法配置文件名，不可修改',
  `org_name` varchar(255) NOT NULL,
  `parse_task_id` bigint(20) NOT NULL,
  `config_type` varchar(255) DEFAULT NULL COMMENT '配置类型，当前只有配置针对样本节点作用的配置',
  `match_sample_id` text COMMENT '模板涉及到的文书id',
  `match_sample_num` bigint(20) NOT NULL,
  `record_type` varchar(255) DEFAULT NULL,
  `rectified_time` datetime DEFAULT NULL COMMENT '纠正时间',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `new_template`
--

DROP TABLE IF EXISTS `new_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `new_template` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `config_node_key` varchar(255) DEFAULT NULL COMMENT '模板节点名称',
  `adm_column` varchar(255) DEFAULT NULL COMMENT '病历质检adm列名',
  `match_sample_num` int(8) DEFAULT NULL COMMENT '匹配样本数',
  `repeat_in_sample` int(4) DEFAULT NULL COMMENT '是否在一个样本里重复出现',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  `parse_task_id` bigint(11) DEFAULT NULL COMMENT '所属任务',
  `template_config_code` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '模板配置编码',
  `org_name` varchar(255) DEFAULT NULL COMMENT '机构',
  `rectified_time` datetime DEFAULT NULL COMMENT '纠正时间',
  `gmt_created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `first_node` varchar(255) DEFAULT NULL,
  `is_regular_expression` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parse_task_template_config` (`parse_task_id`,`template_config_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='模板配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schema_name_all`
--

DROP TABLE IF EXISTS `schema_name_all`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schema_name_all` (
  `id` int(11) NOT NULL,
  `config_node_key` varchar(255) NOT NULL,
  `schema_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_datasource`
--

DROP TABLE IF EXISTS `sys_datasource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_datasource` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(4) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `id` int(11) DEFAULT NULL,
  `config_node_key` varchar(255) DEFAULT NULL,
  `adm_column` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `try`
--

DROP TABLE IF EXISTS `try`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `try` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_node_key` varchar(255) NOT NULL,
  `adm_column` varchar(255) NOT NULL,
  `match_sample_num` int(11) DEFAULT NULL,
  `repeat_in_sample` int(11) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `parse_task_id` bigint(20) NOT NULL,
  `template_config_node` varchar(255) NOT NULL,
  `org_name` varchar(255) NOT NULL,
  `rectified_time` datetime NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `first_node` varchar(255) NOT NULL,
  `is_regular_expression` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'emr_parser'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-21 18:41:14
