/*
 Source Server         : LibraryMS
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : library-ms

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `book_id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '书籍 id',
  `book_name` varchar(100) NOT NULL COMMENT '书籍名称',
  `book_author` varchar(100) NOT NULL COMMENT '书籍作者',
  `cat_id` int UNSIGNED NULL DEFAULT NULL COMMENT '书籍所属分类 id',
  `book_total` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '书籍总馆藏量',
  `book_lent` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '书籍借阅量',
  `book_deleted` datetime NULL DEFAULT NULL COMMENT '逻辑删除标志，未删除为 NULL，删除为删除时的时间',
  UNIQUE INDEX `book_info`(`book_name`, `book_author`, `book_deleted`) USING BTREE,
  FOREIGN KEY (`cat_id`) REFERENCES `category` (`cat_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `cat_id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '分类 id',
  `cat_symbol` varchar(10) NOT NULL COMMENT '分类号',
  `cat_name` varchar(100) NOT NULL COMMENT '分类名称',
  `cat_pid` int UNSIGNED NULL DEFAULT NULL COMMENT '父级分类 id，NULL 表示该分类为一级分类',
  `cat_level` tinyint NULL DEFAULT NULL COMMENT '分类等级，0 表示一级分类，1 表示二级分类，2 表示三级分类',
  `cat_deleted` datetime NULL DEFAULT NULL COMMENT '逻辑删除标志，未删除为 NULL，删除为删除时的时间',
  UNIQUE INDEX `cat_symbol`(`cat_symbol`, `cat_deleted`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for lease
-- ----------------------------
DROP TABLE IF EXISTS `lease`;
CREATE TABLE `lease`  (
  `lease_id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '借阅信息 id',
  `stu_id` int UNSIGNED NOT NULL COMMENT '借阅学生 id',
  `book_id` int UNSIGNED NOT NULL COMMENT '借阅书籍 id',
  `lease_date` date NOT NULL COMMENT '借阅日期',
  `return_date` date NULL DEFAULT NULL COMMENT '归还日期',
  `lease_deleted` datetime NULL DEFAULT NULL COMMENT '逻辑删除标志，未删除为 NULL，删除为删除时的时间',
  FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`),
  FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '学生 id',
  `stu_num` varchar(20) NOT NULL COMMENT '学生学号',
  `stu_name` varchar(20) NOT NULL COMMENT '学生姓名',
  `stu_sex` char(4) NOT NULL COMMENT '学生性别',
  `stu_age` smallint UNSIGNED NOT NULL COMMENT '学生年龄',
  `stu_dept` varchar(20) NOT NULL COMMENT '学生专业',
  `stu_deleted` datetime NULL DEFAULT NULL COMMENT '逻辑删除标志，未删除为 NULL，删除为删除时的时间',
  UNIQUE INDEX `stu_num`(`stu_num`, `stu_deleted`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户 id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(50)  NOT NULL COMMENT '加密后的密码',
  `salt` varchar(50) NOT NULL COMMENT '加密使用的盐',
  `email` varchar(50) NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(20) NULL DEFAULT NULL COMMENT '用户电话',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '用户启用状态，0 表示未启用，1 表示启用',
  `user_deleted` datetime NULL DEFAULT NULL COMMENT '逻辑删除标志，未删除为 NULL，删除为删除时的时间',
  UNIQUE INDEX `username`(`username`, `user_deleted`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
