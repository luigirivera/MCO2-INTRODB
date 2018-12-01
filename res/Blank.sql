-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: stopnshop
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `number` bigint(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `logintries` int(11) DEFAULT '0',
  `islocked` tinyint(4) DEFAULT '0',
  `registerdate` date DEFAULT NULL,
  `lastlogindate` date DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'shopee','shopeemain',NULL,NULL,NULL,'shopee@shopee.com',0,0,'2018-11-30','2018-12-01'),(2,'luigi','aaaaaa',NULL,'MALE',9995842147,NULL,0,0,'2018-11-30','2018-12-01'),(3,'ariana','aaaaaa',NULL,'FEMALE',NULL,'ariana@grande.com',0,0,'2018-11-30','2018-12-01'),(4,'jason','aaaaaa',NULL,'MALE',1472589874,NULL,0,0,'2018-11-30',NULL),(5,'joey','aaaaaa',NULL,NULL,9051423658,'joey@yahoo.com',0,0,'2018-11-30',NULL),(6,'admin','aaaaaa',NULL,NULL,1254789856,NULL,0,0,'2018-11-30','2018-11-30');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `addressid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `line1` varchar(45) NOT NULL,
  `line2` varchar(45) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `province` varchar(45) NOT NULL,
  `zipcode` int(11) NOT NULL,
  PRIMARY KEY (`addressid`),
  KEY `id_idx` (`userid`),
  CONSTRAINT `addressuser` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,3,'2401 Taft Avenue',NULL,'Manila','NCR',1004);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bankaccount`
--

DROP TABLE IF EXISTS `bankaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bankaccount` (
  `baid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `bank` varchar(45) NOT NULL,
  `accountnumber` bigint(11) NOT NULL,
  PRIMARY KEY (`baid`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `bankuser` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bankaccount`
--

LOCK TABLES `bankaccount` WRITE;
/*!40000 ALTER TABLE `bankaccount` DISABLE KEYS */;
INSERT INTO `bankaccount` VALUES (1,3,'BPI',2485748859);
/*!40000 ALTER TABLE `bankaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `cardsid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `cardnumber` bigint(11) NOT NULL,
  `expiry` date NOT NULL,
  `iscvcsaved` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`cardsid`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `carduser` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES (1,3,5264914500145897,'2022-11-01',0);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartcontent`
--

DROP TABLE IF EXISTS `cartcontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cartcontent` (
  `cartid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`cartid`),
  KEY `cartuser_idx` (`userid`),
  KEY `cartproduct_idx` (`productid`),
  CONSTRAINT `cartproduct` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cartuser` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartcontent`
--

LOCK TABLES `cartcontent` WRITE;
/*!40000 ALTER TABLE `cartcontent` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartcontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumer`
--

DROP TABLE IF EXISTS `consumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consumer` (
  `userid` int(11) NOT NULL,
  `walletbalance` decimal(11,0) DEFAULT '0',
  `income` decimal(11,0) DEFAULT '0',
  `shopeecoins` decimal(11,0) DEFAULT '0',
  `fordeletion` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`userid`),
  CONSTRAINT `consumeruser` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumer`
--

LOCK TABLES `consumer` WRITE;
/*!40000 ALTER TABLE `consumer` DISABLE KEYS */;
INSERT INTO `consumer` VALUES (2,0,0,0,0),(3,0,0,0,0),(4,0,0,0,0),(5,0,0,0,0);
/*!40000 ALTER TABLE `consumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumerorder`
--

DROP TABLE IF EXISTS `consumerorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consumerorder` (
  `orderid` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `creationdate` date NOT NULL,
  `addressID` int(11) NOT NULL,
  `cardID` int(11) DEFAULT NULL,
  `bankID` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderid`),
  KEY `orderuser_idx` (`userID`),
  KEY `orderaddress_idx` (`addressID`),
  KEY `ordercard_idx` (`cardID`),
  KEY `orderbank_idx` (`bankID`),
  CONSTRAINT `orderaddress` FOREIGN KEY (`addressID`) REFERENCES `address` (`addressid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `orderbank` FOREIGN KEY (`bankID`) REFERENCES `bankaccount` (`baid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ordercard` FOREIGN KEY (`cardID`) REFERENCES `card` (`cardsid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `orderuser` FOREIGN KEY (`userID`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumerorder`
--

LOCK TABLES `consumerorder` WRITE;
/*!40000 ALTER TABLE `consumerorder` DISABLE KEYS */;
INSERT INTO `consumerorder` VALUES (1,3,7,'2018-12-01',1,NULL,1);
/*!40000 ALTER TABLE `consumerorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corporate`
--

DROP TABLE IF EXISTS `corporate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corporate` (
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`userid`),
  CONSTRAINT `corporateuser` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corporate`
--

LOCK TABLES `corporate` WRITE;
/*!40000 ALTER TABLE `corporate` DISABLE KEYS */;
INSERT INTO `corporate` VALUES (1),(6);
/*!40000 ALTER TABLE `corporate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `product` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`user`),
  KEY `product_idx` (`product`),
  CONSTRAINT `productfavorites` FOREIGN KEY (`product`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userfavorites` FOREIGN KEY (`user`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `following`
--

DROP TABLE IF EXISTS `following`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `following` (
  `followid` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `follower` int(11) NOT NULL,
  PRIMARY KEY (`followid`),
  KEY `followingkey_idx` (`user`),
  KEY `followerkey` (`follower`),
  CONSTRAINT `followerkey` FOREIGN KEY (`follower`) REFERENCES `account` (`userid`),
  CONSTRAINT `followingkey` FOREIGN KEY (`user`) REFERENCES `account` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `following`
--

LOCK TABLES `following` WRITE;
/*!40000 ALTER TABLE `following` DISABLE KEYS */;
INSERT INTO `following` VALUES (1,1,6);
/*!40000 ALTER TABLE `following` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordercontent`
--

DROP TABLE IF EXISTS `ordercontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordercontent` (
  `orderid` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  `deliverydate` date NOT NULL,
  `total` decimal(10,0) NOT NULL,
  KEY `orderproduct_idx` (`productID`),
  KEY `contentorderid_idx` (`orderid`),
  CONSTRAINT `contentorderid` FOREIGN KEY (`orderid`) REFERENCES `consumerorder` (`orderid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `orderproduct` FOREIGN KEY (`productID`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordercontent`
--

LOCK TABLES `ordercontent` WRITE;
/*!40000 ALTER TABLE `ordercontent` DISABLE KEYS */;
INSERT INTO `ordercontent` VALUES (1,1,7,'PENDING','2018-12-03',32900);
/*!40000 ALTER TABLE `ordercontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  `brand` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `stock` bigint(11) NOT NULL,
  `sold` bigint(11) NOT NULL DEFAULT '0',
  `price` decimal(11,0) NOT NULL,
  `discount` decimal(11,0) NOT NULL DEFAULT '0',
  `shipping` decimal(11,0) NOT NULL,
  `shippingduration` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`seller`),
  CONSTRAINT `selleruser` FOREIGN KEY (`seller`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,4,'MacBook','Laptop','Apple',NULL,90,7,5000,10,200,2),(2,2,'Smart TV','Television','Samsung',NULL,50,0,100,0,20,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `product` int(11) NOT NULL,
  `rating` int(1) NOT NULL,
  `comment` varchar(200) DEFAULT NULL,
  `ratingdate` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`user`),
  KEY `ratingproduct_idx` (`product`),
  CONSTRAINT `ratingproduct` FOREIGN KEY (`product`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ratinguser` FOREIGN KEY (`user`) REFERENCES `account` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-01 16:47:12
