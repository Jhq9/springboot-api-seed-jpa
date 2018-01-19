/*
 Navicat Premium Data Transfer

 Source Server         : May
 Source Server Type    : MySQL
 Source Server Version : 50635
 Source Host           : localhost:3306
 Source Schema         : springboot-jpa

 Target Server Type    : MySQL
 Target Server Version : 50635
 File Encoding         : 65001

 Date: 19/01/2018 10:11:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_role
-- ----------------------------
DROP TABLE IF EXISTS `m_role`;
CREATE TABLE `m_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of m_role
-- ----------------------------
BEGIN;
INSERT INTO `m_role` VALUES (3, 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `name` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_74lxcl4brqh72rle4t89ktf76` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of m_user
-- ----------------------------
BEGIN;
INSERT INTO `m_user` VALUES (2, '2018-01-19 09:45:00', '1044038055@qq.com', '2018-01-19 09:45:00', '金华权', '$2a$10$gqiSyLYicxkEnyx9o3dlJOJR8ZHnhjaiQ0Yab4jGHHvbKAA5P/85C', '15700084691');
COMMIT;

-- ----------------------------
-- Table structure for user_role_link
-- ----------------------------
DROP TABLE IF EXISTS `user_role_link`;
CREATE TABLE `user_role_link` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FK9f7qkwswag7s2s469mjl927q9` (`role_id`),
  KEY `FK2xi1ptvbns7u9osfl0b9phb0o` (`user_id`),
  CONSTRAINT `FK2xi1ptvbns7u9osfl0b9phb0o` FOREIGN KEY (`user_id`) REFERENCES `m_user` (`id`),
  CONSTRAINT `FK9f7qkwswag7s2s469mjl927q9` FOREIGN KEY (`role_id`) REFERENCES `m_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_role_link
-- ----------------------------
BEGIN;
INSERT INTO `user_role_link` VALUES (2, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
