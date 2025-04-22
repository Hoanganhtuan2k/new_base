-- MySQL dump 10.13  Distrib 8.0.41, for Linux (aarch64)
--
-- Host: localhost    Database: cm_cim
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `order_code` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `product_code` varchar(255) NOT NULL,
  `quantity` int NOT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `unit_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,'2025-04-01 08:30:19.856420',NULL,'2025-04-01 08:30:19.856420','GP_20250401153019_652e0f71',90000.00,'HOANGTHACHPCB30',10,900000.00,'KGCEMENT'),(2,'2025-04-01 08:30:19.858441',NULL,'2025-04-01 08:30:19.858441','GP_20250401153019_652e0f71',120000.00,'POMIHOAPCB40',20,2400000.00,'KGCEMENT'),(3,'2025-04-01 08:32:16.348620',NULL,'2025-04-01 08:32:16.348620','GP_20250401153216_df888736',1928736.00,'HOANGTHACHPCB40',10,19287360.00,'KGCEMENT');
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `customer_address` varchar(255) NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `customer_phone` varchar(255) NOT NULL,
  `order_code` varchar(255) NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `order_status` tinyint NOT NULL,
  `payment_status` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdhk2umg8ijjkg4njg6891trit` (`order_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,'2025-04-01 08:30:19.850521',NULL,'2025-04-01 08:30:19.850521','Gò Pháo, Hợp Thịnh, Hiệp Hoà','DuongDT','0972915368','GP_20250401153019_652e0f71',3300000.00,1,0),(3,'2025-04-01 08:32:16.342806',NULL,'2025-04-01 08:32:16.342806','Gò Pháo, Hợp Thịnh, Hiệp Hoà','DuongDT','0972915368','GP_20250401153216_df888736',19287360.00,1,0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `type_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
INSERT INTO `product_type` VALUES (1,'2025-01-11 13:58:57.000000',NULL,'2025-01-11 13:58:57.000000','Xi măng','CEMENT'),(2,'2025-01-11 13:58:57.000000',NULL,'2025-01-11 13:58:57.000000','Sắt','STEEL');
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `product_code` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_type_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK922x4t23nx64422orei4meb2y` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2025-01-13 12:46:50.000000',NULL,'2025-01-13 12:46:50.000000','Xi măng dùng xây trát.','HOANGTHACHPCB30','Xi măng Hoàng Thạch PCB30','CEMENT'),(2,'2025-01-13 12:46:50.000000',NULL,'2025-01-13 12:46:50.000000','Xi măng dùng cho đổ bê tông.','HOANGTHACHPCB40','Xi măng Hoàng Thạch PCB40','CEMENT'),(3,'2025-01-13 12:46:50.000000',NULL,'2025-01-13 12:46:50.000000','Xi măng dùng xây trát.','POMIHOAPCB30','Xi măng Pomihoa PCB 30','CEMENT'),(4,'2025-01-13 12:46:50.000000',NULL,'2025-01-13 12:46:50.000000','Xi măng dùng cho đổ bê tông.','POMIHOAPCB40','Xi măng Pomihoa PCB 40','CEMENT'),(5,'2025-01-18 10:00:00.000000',NULL,'2025-01-18 12:00:00.000000','Sản phẩm mới, chất lượng cao','P001','Sản phẩm A','STEEL');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit_types`
--

DROP TABLE IF EXISTS `unit_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `product_type_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit_types`
--

LOCK TABLES `unit_types` WRITE;
/*!40000 ALTER TABLE `unit_types` DISABLE KEYS */;
INSERT INTO `unit_types` VALUES (1,'2025-01-11 14:07:24.000000',NULL,'2025-01-11 14:07:24.000000','KGCEMENT','Kilogram','CEMENT'),(2,'2025-01-11 14:07:24.000000',NULL,'2025-01-11 14:07:24.000000','BAGCEMENT','Bao','CEMENT'),(3,'2025-01-11 14:07:24.000000',NULL,'2025-01-11 14:07:24.000000','TACEMENT','Tạ','CEMENT'),(4,'2025-01-11 14:07:24.000000',NULL,'2025-01-11 14:07:24.000000','YENCEMENT','Yến','CEMENT'),(5,'2025-01-11 14:07:24.000000',NULL,'2025-01-11 14:07:24.000000','TONCEMENT','Tấn','CEMENT'),(6,'2025-01-11 14:10:18.000000',NULL,'2025-01-11 14:10:18.000000','KGSTEEL','Cân','STEEL'),(7,'2025-01-11 14:10:18.000000',NULL,'2025-01-11 14:10:18.000000','CAISTEEL','Cái đai','STEEL');
/*!40000 ALTER TABLE `unit_types` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-01  8:51:07
