CREATE DATABASE  IF NOT EXISTS `pmb_ewallet` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pmb_ewallet`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: pmb_ewallet
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `transaction_date` date NOT NULL,
  `transaction_sender` int NOT NULL,
  `transaction_receiver` int NOT NULL,
  `transaction_amount` float NOT NULL,
  `transaction_fee` float NOT NULL,
  `transaction_description` text,
  PRIMARY KEY (`transaction_id`),
  KEY `transaction_sender` (`transaction_sender`),
  KEY `transaction_receiver` (`transaction_receiver`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`transaction_sender`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`transaction_receiver`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (259,'2023-09-27',593,593,50,0,'You have credit your account.'),(260,'2023-09-27',594,594,100,0,'You have credit your account.'),(261,'2023-09-27',595,595,20,0,'You have credit your account.'),(262,'2023-09-27',596,596,120,0,'You have credit your account.'),(263,'2023-09-27',597,597,50,0,'You have credit your account.'),(264,'2023-09-27',597,596,30,0.15,'Happy birthday !'),(265,'2023-09-27',597,597,-19.85,0,'You have closed your PayMyBuddyAccount.'),(266,'2023-09-27',598,598,50,0,'You have credit your account.'),(267,'2023-09-27',598,598,50,0,'You have credit your account.'),(268,'2023-09-27',598,598,110,0,'You have credit your account.'),(269,'2023-09-27',598,598,49.99,0,'You have credit your account.'),(270,'2023-09-27',598,593,145.26,0.73,'For your travel expenses. Peace, dude. You\'re the best :X'),(271,'2023-09-27',598,594,64,0.32,''),(272,'2023-09-27',593,596,100,0.5,'Faithfully yours.'),(273,'2023-09-27',593,593,50,0,'You have credit your account.'),(274,'2023-09-27',593,595,100,0.5,'Thanks again for your help !'),(275,'2023-09-27',593,593,-44.26,0,'You have withdraw your money.'),(276,'2023-09-27',593,593,20,0,'You have credit your account.');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_firstname` varchar(100) NOT NULL,
  `user_lastname` varchar(100) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_amount` float NOT NULL DEFAULT '0',
  `user_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=599 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (593,'John','Wick','john.wick@continental.ny','1000:634c38d1cd6fdfce44b641e31600a560:bd62bbfe54353a6e048819659031e7b4bb041bbbb5c1b06efca9548a516dd5e5bfa795e3309522f8c761ff85a3034e26ff0e73f66eab04f0c76688bf1237ea23',20,1),(594,'Winston','Scott','winston.scott@continental.ny','1000:3c4de068cd1c37fc7de1a870fbf99449:86987411a316ffe7e259740a66c8422b7dd0e7f6cf33ed60a44c63473ffb206dc5609a748b4dbc9ec4aab9332816b2e0f9fbf7aa7c6cec36fbdc1dbea66b3e09',164,1),(595,'Sofia','Al-Azwar','sofia.alazwar@continental.cas','1000:66604fa6ca0e3b1829297b380e933f2f:576edbe8793e1d42d195e36aef429c1e603729a087b9dc96ddc265be494423fd3f6c7bd20be0acea34313621c6a4e43dd21c25380c444d07a3fda31e350ef26c',120,1),(596,'Akira','Shimazu','akira.shimazu@continental.osa','1000:33f9c4f1559a5bf19699d75708a33713:669a1484f2ab6894a4e24e37b7bb93e1a238c78259893cf27fd26c6363399b1e6aa5bb7b3f567f3efa0e43a7405be1829971b67c564afd9b59f563e5ff695d30',250,1),(597,'Koji','Shimazu','koji.shimazu@continental.osa','1000:5f564974137fb1336c6cd147a9b43835:aaa4753513e0cab13ea014967c095f0cabde5e18dda356ba9677a37b723212514292813cb5ba41ae23a49260a139ea99632d64e702c653cf8ebc2f1b33ecbf37',0,0),(598,'Abram','Tarasov','abram.tarasov@tarasov.mob','1000:8b834e5c03d23c539c16d6c25de0a54c:77dcc6f1e183e76293926738f412446cb83728755b54ac80c4f9cc78c03fa27a80c3af755c4cc9a9e95adf8bcf7881b8bed667dd58ea16eea7cf28b4e630aa82',49.68,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_user`
--

DROP TABLE IF EXISTS `user_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_user` (
  `buddies_user_id` int NOT NULL,
  `buddies_buddy_id` int NOT NULL,
  PRIMARY KEY (`buddies_user_id`,`buddies_buddy_id`),
  KEY `buddies_buddy_id` (`buddies_buddy_id`),
  CONSTRAINT `user_user_ibfk_1` FOREIGN KEY (`buddies_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_user_ibfk_2` FOREIGN KEY (`buddies_buddy_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_user`
--

LOCK TABLES `user_user` WRITE;
/*!40000 ALTER TABLE `user_user` DISABLE KEYS */;
INSERT INTO `user_user` VALUES (594,593),(597,593),(598,593),(597,594),(598,594),(593,595),(596,595),(593,596),(597,596);
/*!40000 ALTER TABLE `user_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-27 19:21:08
