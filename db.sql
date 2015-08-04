-- phpMyAdmin SQL Dump
-- version 4.4.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 18, 2015 at 10:59 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `News`
--

CREATE TABLE IF NOT EXISTS `News` (
  `ID` int(11) NOT NULL,
  `UnionID` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `URL` mediumtext COLLATE utf8_unicode_ci,
  `abstract` mediumtext COLLATE utf8_unicode_ci,
  `iconURL` mediumtext COLLATE utf8_unicode_ci,
  `TheDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `UnionAccount`
--

CREATE TABLE IF NOT EXISTS `UnionAccount` (
  `ID` int(11) NOT NULL,
  `login_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `ThePassword` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `Name` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `iconURL` mediumtext COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `UnionInfo`
--

CREATE TABLE IF NOT EXISTS `UnionInfo` (
  `ID` int(11) NOT NULL,
  `UnionID` int(11) NOT NULL,
  `Content` mediumtext COLLATE utf8_unicode_ci,
  `TheDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `News`
--
ALTER TABLE `News`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `UnionID` (`UnionID`);

--
-- Indexes for table `UnionAccount`
--
ALTER TABLE `UnionAccount`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Name` (`Name`);

--
-- Indexes for table `UnionInfo`
--
ALTER TABLE `UnionInfo`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `UnionID` (`UnionID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `News`
--
ALTER TABLE `News`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `UnionAccount`
--
ALTER TABLE `UnionAccount`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `UnionInfo`
--
ALTER TABLE `UnionInfo`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `UnionInfo`
--
ALTER TABLE `UnionInfo`
  ADD CONSTRAINT `UnionInfo_ibfk_1` FOREIGN KEY (`UnionID`) REFERENCES `UnionAccount` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
