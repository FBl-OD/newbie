DROP DATABASE IF EXISTS `newbie`;
CREATE DATABASE `newbie`;
USE `newbie`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '0-普通用户; 1-管理员',
  `status` int(11) DEFAULT NULL COMMENT '0-未激活; 1-已激活;',
  `header_url` varchar(200) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_name` (`name`),
  KEY `index_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

/*
 *标签表：文档对应一个或多个标签
 */
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `catgory_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0-正常; 1-删除;',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_catgory_id` (`catgory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

/*
 *归档表：系统用户对标签进行归档，或普通用户对博客文章归档
 */
DROP TABLE IF EXISTS `catgory`;
CREATE TABLE `catgory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0-正常; 1-删除;',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` text,
  `catgory_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0-正常; 1-删除;',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_catgory_id` (`catgory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;


