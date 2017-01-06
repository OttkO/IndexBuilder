CREATE DATABASE  IF NOT EXISTS `indexing` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `indexing`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: indexing
-- ------------------------------------------------------
-- Server version	5.7.11



DROP TABLE IF EXISTS `file_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_information` (
  `fileId` int(11) NOT NULL,
  `line_number` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `keyword` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`fileId`,`line_number`,`position`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_information`
--
DROP TABLE IF EXISTS `filename`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filename` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filename`
--

