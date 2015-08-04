-- phpMyAdmin SQL Dump
-- version 4.4.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 18, 2015 at 11:03 PM
-- Server version: 5.6.19-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dingboyan`
--

--
-- Dumping data for table `UnionAccount`
--

INSERT INTO `UnionAccount` (`ID`, `login_name`, `ThePassword`, `Name`, `iconURL`) VALUES
(1, 'pkuacapella', '276e2a9c618f010f0b49e2d1f500dad71461df6d', '阿卡贝拉清唱社', NULL),
(2, 'pkuheart', '4b0b0f7cb99b0a7327a082a4d8a9a1bccdb36d7c', '爱心社', NULL),
(3, 'pkuopera', 'd3547ed65a2a472815df10d64777b121925dd6b5', '北大剧社', NULL),
(4, 'pkujk', '1b1f083782fc210ae54996879c7284ebf3e99b37', '京昆社', NULL),
(5, 'pkudance', '5ea64243fd70158579ef722a33b9adb43147f205', '街舞风雷社', NULL),
(6, 'pkuun', 'c121ac532e5485d1e300cdf2ec2295f232269c0c', '模拟联合国协会', NULL),
(7, 'pkuhawk', '43dff61e2d8ba579169032ad4db7c81db862fd6e', '山鹰社', NULL),
(8, 'pkubilliards', '0fd5fc5eeb9eb8238894f55b22d66b0898d2215a', '北京大学台球协会', 'http://123.57.204.246:8002/img/billiards_logo.jpg'),
(9, 'pkustudent', 'd45f0c52c2b4f077811b919aa8e702efcd04085c', '学生会', NULL),
(10, 'pkuvolunteer', 'f835f6ef79d48df157504ab8c849023f4e3f6e31', '阳光志愿者协会', NULL),
(11, 'pkufire', '8ec5ad002d829920542a1881d3d4eb7bbbd08563', '元火动漫社', NULL),
(12, 'pkubike', 'fecaf9f4ed4dfde5b4d14f40b98348b2fb101532', '自行车协会', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
