-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: kkumteuldb
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `categorys`
--

DROP TABLE IF EXISTS `categorys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorys` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `mandalart_id` int NOT NULL,
  `position` int NOT NULL,
  `category_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `mandalart_id` (`mandalart_id`),
  CONSTRAINT `categorys_ibfk_1` FOREIGN KEY (`mandalart_id`) REFERENCES `mandalarts` (`mandalart_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorys`
--

LOCK TABLES `categorys` WRITE;
/*!40000 ALTER TABLE `categorys` DISABLE KEYS */;
INSERT INTO `categorys` VALUES (74,11,0,'독서'),(75,11,1,'운동'),(76,11,2,'공부'),(77,11,3,'경제'),(78,11,5,'여행'),(79,11,6,'취미'),(80,11,7,'가족'),(81,11,8,'자기관리'),(82,12,0,'독서'),(83,12,1,'운동'),(84,12,2,'공부'),(85,12,3,'경제'),(86,12,5,'여행'),(87,12,6,'취미'),(88,12,7,'가족'),(89,12,8,'자기관리'),(90,13,0,'독서'),(91,13,1,'운동'),(92,13,2,'공부'),(93,13,3,'경제'),(94,13,5,'여행'),(95,13,6,'취미'),(96,13,7,'가족'),(97,13,8,'자기관리'),(98,14,0,'독서'),(99,14,1,'운동'),(100,14,2,'공부'),(101,14,3,'경제'),(102,14,5,'여행'),(103,14,6,'취미'),(104,14,7,'가족'),(105,14,8,'자기관리'),(106,15,0,'독서'),(107,15,1,'운동'),(108,15,2,'공부'),(109,15,3,'경제'),(110,15,5,'여행'),(111,15,6,'취미'),(112,15,7,'가족'),(113,15,8,'자기관리'),(114,16,0,'독서'),(115,16,1,'운동'),(116,16,2,'공부'),(117,16,3,'경제'),(118,16,5,'여행'),(119,16,6,'취미'),(120,16,7,'가족'),(121,16,8,'자기관리'),(122,17,0,'독서'),(123,17,1,'운동'),(124,17,2,'공부'),(125,17,3,'경제'),(126,17,5,'여행'),(127,17,6,'취미'),(128,17,7,'가족'),(129,17,8,'자기관리'),(130,18,0,'독서'),(131,18,1,'운동'),(132,18,2,'공부'),(133,18,3,'경제'),(134,18,5,'여행'),(135,18,6,'취미'),(136,18,7,'가족'),(137,18,8,'자기관리'),(138,19,0,'독서'),(139,19,1,'운동'),(140,19,2,'공부'),(141,19,3,'경제'),(142,19,5,'여행'),(143,19,6,'취미'),(144,19,7,'가족'),(145,19,8,'자기관리');
/*!40000 ALTER TABLE `categorys` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-28 23:39:54
