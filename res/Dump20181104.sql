CREATE DATABASE  IF NOT EXISTS `stopnshop`;
USE `stopnshop`;
-- MySQL dump 10.13  Distrib 8.0.12, for macos10.13 (x86_64)
--
-- Host: localhost    Database: stopnshop
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `accounts` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `number` bigint(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `walletbalance` decimal(11,0) DEFAULT '0',
  `income` decimal(11,0) DEFAULT '0',
  `shopeecoins` decimal(11,0) DEFAULT '0',
  `logintries` int(11) DEFAULT '0',
  `islocked` tinyint(4) DEFAULT '0',
  `fordeletion` tinyint(4) DEFAULT '0',
  `iscorporate` tinyint(4) DEFAULT '0',
  `registerdate` date DEFAULT NULL,
  `lastlogindate` date DEFAULT NULL,
  PRIMARY KEY (`userid`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'shopee','shopeemain',NULL,'MALE',NULL,'shopee@shopee.com',0,0,0,0,0,0,0,'2018-11-03','2018-11-04');
INSERT INTO `accounts` VALUES (2,'admin','aaaaaa',NULL,'MALE',9994752346,NULL,0,0,0,0,0,0,0,'2018-11-03','2018-11-04');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `addressid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `line1` varchar(45) NOT NULL,
  `line2` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `province` varchar(45) NOT NULL,
  `zipcode` int(11) NOT NULL,
  PRIMARY KEY (`addressid`),
  KEY `id_idx` (`userid`),
  CONSTRAINT `id` FOREIGN KEY (`userid`) REFERENCES `accounts` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cards`
--

DROP TABLE IF EXISTS `cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cards` (
  `cardsid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `cardnumber` bigint(11) NOT NULL,
  `expiry` date NOT NULL,
  `iscvcsaved` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`cardsid`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `accounts` (`userid`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cards`
--

LOCK TABLES `cards` WRITE;
/*!40000 ALTER TABLE `cards` DISABLE KEYS */;
/*!40000 ALTER TABLE `cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `following`
--

DROP TABLE IF EXISTS `following`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `following` (
  `followid` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `follower` int(11) NOT NULL,
  PRIMARY KEY (`followid`),
  KEY `followingkey_idx` (`user`),
  KEY `followerkey` (`follower`),
  CONSTRAINT `followerkey` FOREIGN KEY (`follower`) REFERENCES `accounts` (`userid`),
  CONSTRAINT `followingkey` FOREIGN KEY (`user`) REFERENCES `accounts` (`userid`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `following`
--

LOCK TABLES `following` WRITE;
/*!40000 ALTER TABLE `following` DISABLE KEYS */;
/*!40000 ALTER TABLE `following` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-04 23:55:13
