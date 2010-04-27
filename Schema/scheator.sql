-- phpMyAdmin SQL Dump
-- version 3.3.2deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 27, 2010 at 02:49 PM
-- Server version: 5.1.41
-- PHP Version: 5.3.2-1ubuntu4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `scheator`
--

-- --------------------------------------------------------

--
-- Table structure for table `Match`
--

CREATE TABLE IF NOT EXISTS `Match` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `match_no` int(11) NOT NULL,
  `season` int(11) NOT NULL,
  `hometeam` int(11) NOT NULL,
  `awayteam` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `season` (`season`),
  KEY `hometeam` (`hometeam`),
  KEY `awayteam` (`awayteam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Season`
--

CREATE TABLE IF NOT EXISTS `Season` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `series` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `series` (`series`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Series`
--

CREATE TABLE IF NOT EXISTS `Series` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Table structure for table `Team`
--

CREATE TABLE IF NOT EXISTS `Team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Match`
--
ALTER TABLE `Match`
  ADD CONSTRAINT `Match_ibfk_1` FOREIGN KEY (`season`) REFERENCES `Season` (`id`),
  ADD CONSTRAINT `Match_ibfk_2` FOREIGN KEY (`hometeam`) REFERENCES `Team` (`id`),
  ADD CONSTRAINT `Match_ibfk_3` FOREIGN KEY (`awayteam`) REFERENCES `Team` (`id`);

--
-- Constraints for table `Season`
--
ALTER TABLE `Season`
  ADD CONSTRAINT `Season_ibfk_1` FOREIGN KEY (`series`) REFERENCES `Series` (`id`);

