/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.0.24-community-nt : Database - duomeiti
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`duomeiti` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `duomeiti`;

/*Table structure for table `bbs` */

DROP TABLE IF EXISTS `bbs`;

CREATE TABLE `bbs` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `pubtime` varchar(30) default NULL,
  `uid` int(11) default NULL,
  `bid` int(11) default NULL,
  `sta` varchar(10) default NULL,
  `note` text,
  `note2` text,
  `htime` varchar(30) default NULL,
  `btype` varchar(10) default NULL,
  `hid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bbs` */

insert  into `bbs`(`id`,`name`,`pubtime`,`uid`,`bid`,`sta`,`note`,`note2`,`htime`,`btype`,`hid`) values (1,'测试标题','2019-03-16 15:32:56',6,NULL,'待回复','这里是留言的内容',NULL,NULL,'留言',NULL);

/*Table structure for table `ftype` */

DROP TABLE IF EXISTS `ftype`;

CREATE TABLE `ftype` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `btype` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ftype` */

insert  into `ftype`(`id`,`name`,`btype`) values (1,'分类1',NULL),(2,'分类2',NULL),(3,'分类03',NULL);

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) NOT NULL auto_increment,
  `fid` int(11) default NULL,
  `img` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `price` double default NULL,
  `note` text,
  `ctype` varchar(50) default NULL,
  `pubtime` varchar(100) default NULL,
  `iscommend` varchar(20) default NULL,
  `uid` varchar(20) default NULL,
  `btype` varchar(20) default NULL,
  `upload` varchar(255) default NULL,
  `dnum` int(11) default NULL,
  `xnum` int(11) default NULL,
  `files` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_flower` (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `goods` */

insert  into `goods`(`id`,`fid`,`img`,`name`,`price`,`note`,`ctype`,`pubtime`,`iscommend`,`uid`,`btype`,`upload`,`dnum`,`xnum`,`files`) values (1,1,'u=1289930917,3560328119&fm=26&gp=0.jpg','资源1',NULL,'说明',NULL,'2019-03-16 15:11:36','推荐','5',NULL,'ziyuan.docx',0,NULL,'201712241822240002.mp4'),(2,1,'u=1814885686,1231277900&fm=15&gp=0.jpg','资源2',NULL,'内容',NULL,'2019-03-16 15:12:18','非推荐','5',NULL,'ziyuan.docx',0,NULL,'canvas3.mp4'),(3,2,'u=3882592583,2615522414&fm=15&gp=0.jpg','资源3',NULL,'<img src=\"/duomeiti/attached/image/20190316/20190316151258_190.jpg\" alt=\"\" />',NULL,'2019-03-16 15:13:00','推荐','5',NULL,'ziyuan.docx',4,NULL,'canvas4.mp4'),(4,1,'u=2571199800,1706884455&fm=15&gp=0.jpg','资源4',NULL,'内容',NULL,'2019-03-16 15:13:33','推荐','5',NULL,'ziyuan.docx',1,NULL,'201712241822240002.mp4'),(5,1,'u=1359870357,2597471756&fm=15&gp=0.jpg','资源5',NULL,'内容',NULL,'2019-03-16 15:14:05','非推荐','5',NULL,'ziyuan.docx',3,NULL,'canvas4.mp4'),(6,3,'u=2571199800,1706884455&fm=15&gp=0.jpg','资源88',NULL,'说明',NULL,'2019-03-16 15:36:57','非推荐','7',NULL,'ziyuan.docx',1,NULL,'201712241822240002.mp4');

/*Table structure for table `keep` */

DROP TABLE IF EXISTS `keep`;

CREATE TABLE `keep` (
  `id` int(11) NOT NULL auto_increment,
  `uid` int(11) default NULL,
  `fid` int(11) default NULL,
  `status` varchar(30) default NULL,
  `isdel` varchar(50) default NULL,
  `pubtime` varchar(50) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_keep` (`fid`),
  CONSTRAINT `FK_keep` FOREIGN KEY (`fid`) REFERENCES `goods` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `keep` */

insert  into `keep`(`id`,`uid`,`fid`,`status`,`isdel`,`pubtime`) values (1,6,3,'收藏','1','2019-03-16 15:31:52');

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `ggid` int(11) NOT NULL auto_increment,
  `ggpic` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `note` text,
  `isdel` varchar(20) default NULL,
  `gtype` varchar(50) default NULL,
  `pubtime` varchar(30) default NULL,
  PRIMARY KEY  (`ggid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `news` */

insert  into `news`(`ggid`,`ggpic`,`name`,`note`,`isdel`,`gtype`,`pubtime`) values (1,'u=2163852381,1866063931&fm=26&gp=0.jpg','新闻22','<img src=\"/duomeiti/attached/image/20190316/20190316151430_717.jpg\" alt=\"\" />','1','新闻动态','2019-03-16 15:14:31'),(2,'u=2571199800,1706884455&fm=15&gp=0.jpg','新闻2','<img src=\"/duomeiti/attached/image/20190316/20190316151455_347.jpg\" alt=\"\" />','1','新闻动态','2019-03-16 15:14:57'),(3,'u=3709224650,312648939&fm=26&gp=0.jpg','新闻3','内容','1','新闻动态','2019-03-16 15:15:15'),(4,'u=3882592583,2615522414&fm=15&gp=0.jpg','新闻4','<img src=\"/duomeiti/attached/image/20190316/20190316151540_496.jpg\" alt=\"\" />','1','新闻动态','2019-03-16 15:15:41');

/*Table structure for table `sysuser` */

DROP TABLE IF EXISTS `sysuser`;

CREATE TABLE `sysuser` (
  `uid` int(11) NOT NULL auto_increment,
  `uname` varchar(20) default NULL,
  `sex` varchar(10) default NULL,
  `address` varchar(100) default NULL,
  `pwd` varchar(20) default NULL,
  `utype` varchar(30) default NULL,
  `tel` varchar(20) default NULL,
  `age` varchar(10) default NULL,
  `mbanswer` varchar(30) default NULL,
  `question` varchar(30) default NULL,
  `email` varchar(30) default NULL,
  `pubtime` varchar(30) default NULL,
  `tname` varchar(50) default NULL,
  `phone` varchar(30) default NULL,
  `qq` varchar(255) default NULL,
  `weixin` varchar(255) default NULL,
  `isdel` varchar(10) default NULL,
  `ctype` varchar(20) default NULL,
  `img` varchar(255) default NULL,
  PRIMARY KEY  (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sysuser` */

insert  into `sysuser`(`uid`,`uname`,`sex`,`address`,`pwd`,`utype`,`tel`,`age`,`mbanswer`,`question`,`email`,`pubtime`,`tname`,`phone`,`qq`,`weixin`,`isdel`,`ctype`,`img`) values (1,'admin','男','武汉343号','123','管理员','14584845215',NULL,'我是谁？','admin','21521515@qq.com',NULL,NULL,NULL,NULL,'weixin1',NULL,NULL,NULL),(2,'hy01','男','武汉343号','123456','会员','14584845215',NULL,'我是谁？','hy01','21521515@qq.com','2019-03-16 14:50:12.408','会员01',NULL,NULL,'weixin1','1',NULL,'book4.jpg'),(3,'hy02','女','','123456','会员','14584845215',NULL,'我是谁？','hy02','21521515@qq.com','2019-03-16 14:50:12.408','会员02',NULL,NULL,'weixin1','1',NULL,'book12.jpg'),(4,'test','女','地址1','123456','会员','15687925688',NULL,'我是谁？','test','18542154@qq.com','2019-03-16 14:50:12.408','测试会员',NULL,NULL,'weixin2','1',NULL,'book7.jpg'),(5,'ls01','男','地址1','123456','老师','15362319588',NULL,'我是谁？','ls01','','2019-03-16 14:50:12.408','老师01',NULL,NULL,'','1',NULL,NULL),(6,'yh01','男','地址1','123456','学生','15362319599',NULL,'我是谁？','yh01','56212211512@qq.com','2019-03-16 15:31:16','用户1',NULL,NULL,'weixin1','1',NULL,'u=3581410264,3904543251&fm=15&gp=0.jpg'),(7,'ls02','女','地址1','123456','老师','15362319598',NULL,'我是谁？','ls02',NULL,'2019-03-16 15:35:44.023','老师2',NULL,NULL,NULL,'1',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
