CREATE DATABASE  IF NOT EXISTS `tourism-management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tourism-management`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tourism-management
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `touristcompany`
--

DROP TABLE IF EXISTS `touristcompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `touristcompany` (
  `branchId` int NOT NULL AUTO_INCREMENT,
  `branchName` varchar(45) DEFAULT NULL,
  `place` varchar(45) DEFAULT NULL,
  `website` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `dateAdded` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`branchId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `touristcompany`
--

LOCK TABLES `touristcompany` WRITE;
/*!40000 ALTER TABLE `touristcompany` DISABLE KEYS */;
INSERT INTO `touristcompany` VALUES (2,'andamanTours','ANDAMAN','www.andamanTours.com','9988776655','info@andamanTours.com','2024-06-02 09:19:11'),(3,'andamanTours','ANDAMAN','www.andamanTours.com','9988776655','info@andamanTours.com','2024-06-02 11:16:49'),(4,'malasiyaBranch','MALAYSIA','www.malasiyaBranch.com','9988778899','contact@malasiyaBranch.com','2024-06-02 16:18:11'),(5,'andamanTours','ANDAMAN','www.andamanTours.com','9988776655','info@andamanTours.com','2024-06-02 16:26:54'),(6,'andamanTours','ANDAMAN','www.andamanTours.com','9988776655','info@andamanTours.com','2024-06-02 18:27:37'),(10,'andamanTours','ANDAMAN','www.andamanTours.com','9988776655','info@andamanTours.com','2024-06-02 19:01:27'),(19,'andamanTours','ANDAMAN','www.andamanTours.com','9988776655','info@andamanTours.com','2024-06-04 20:58:07');
/*!40000 ALTER TABLE `touristcompany` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-09 11:22:39
