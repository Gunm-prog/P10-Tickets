-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: lib7
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `author_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  PRIMARY KEY (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'J.R.R.','Tolkien'),(2,'J.K.','Rawling'),(3,'Marcel','Pagnol'),(4,'Katsuhiro','Otomo'),(5,'Stephen','King'),(6,'Brian','Herbert');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `isbn` varchar(30) NOT NULL,
  `summary` varchar(800) DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `author_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKklnrv3weler2ftkweewlky958` (`author_id`),
  CONSTRAINT `FKklnrv3weler2ftkweewlky958` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'1234','Le récit débute avec la naissance de l\'auteur, le 28 février 1895 à Aubagne, raconte son enfance à Marseille, ses premières années passées à l\'école primaire, ses vacances en famille au village de la Treille pendant l\'été de 1904, et prend fin sur les exploits de son père durant une partie de chasse dans les collines ...','La gloire de mon père',3),(2,'3456','This is the tale of Harry Potter (Daniel Radcliffe), an ordinary eleven-year-old boy serving as a sort of slave for his aunt and uncle who learns that he is actually a wizard and has been invited to attend the Hogwarts School for Witchcraft and Wizardry. Harry is snatched away from his mundane existence by Rubeus Hagrid (Robbie Coltrane), the groundskeeper for Hogwarts, and quickly thrown into a world completely foreign to both him and the viewer. Famous for an incident that happened at his birth, Harry makes friends easily at his new school. He soon finds, however, that the wizarding world is far more dangerous for him than he would have imagined, and he quickly learns that not all wizards are ones to be trusted.','Harry Potter and the sorcerer\'s stone',2),(3,'5246','On December 6, 1982 (1992 in the English version), a nuclear explosion destroys Tokyo and starts World War III. By 2019 (2030 in the English version), a new city called Neo-Tokyo has been built on an artificial island in Tokyo Bay. Although Neo-Tokyo is set to host the XXXII Olympic Games, the city is gripped by anti-government terrorism and gang violence. While exploring the ruins of old Tokyo, Tetsuo Shima, a member of the bōsōzoku gang led by Shōtarō Kaneda, is accidentally injured when his bike crashes after Takashi — a child Esper with wizened features — blocks his path. This incident awakens psychic powers in Tetsuo, attracting the attention of a secret government project directed by JSDF Colonel Shikishima. These increasing powers unhinge Tetsuo\'s mind, exacerbating his inferiority complex about Kaneda and leading him to assume leadership of the rival Clown Gang through violence.','Akira Volume 1: Tetsuo',4),(4,'5247','After their confrontation with the JSDF, Kaneda, Kei, and Tetsuo are taken into military custody and held in a highly secure skyscraper in Neo-Tokyo. Kei soon escapes after becoming possessed as a medium by another Esper, Kiyoko. Kei/Kiyoko briefly does battle with Tetsuo and frees Kaneda. After rapidly healing from his wounds, Tetsuo inquires about Akira, and forces Doctor Onishi, a project scientist, to take him to the other espers: Takashi, Kiyoko, and Masaru. There, a violent confrontation unfolds between Tetsuo, Kaneda, Kei, and the Espers. The Doctor decides to try to let Tetsuo harness Akira — the project\'s test subject that destroyed Tokyo — despite Tetsuo\'s disturbed personality. Upon learning that Akira is being stored in a cryogenic chamber beneath Neo-Tokyo\'s new Olympic Stadium, Tetsuo escapes the skyscraper with the intent of releasing Akira.','Akira Volume 2: Akira I',4),(5,'5248','disappears in the subsequent explosion, and Kaneda and Kei come across Akira outside of the base. Vaguely aware of who he is, they take him back into Neo-Tokyo. Both the Colonel\'s soldiers and the followers of an Esper named Lady Miyako begin scouring Neo-Tokyo in search for him. Kaneda, Kei, and a third terrorist, Chiyoko, attempt to find refuge with Akira on Nezu\'s yacht. However, Nezu betrays them and kidnaps Akira for his use, attempting to have them killed. They survive the attempt and manage to snatch Akira from Nezu\'s mansion. The Colonel, desperate to find Akira and fed up with the government\'s tepid response to the crisis, mounts a coup d\'état and puts the city under martial law. The Colonel\'s men join Lady Miyako\'s acolytes and Nezu\'s private army in chasing Kaneda, Kei, Chiyoko, and Akira through the city.','Akira Volume 3: Akira II',4),(6,'5312','The Hobbit is set within Tolkien\'s fictional universe and follows the quest of home-loving Bilbo Baggins, the titular hobbit, to win a share of the treasure guarded by Smaug the dragon. Bilbo\'s journey takes him from his light-hearted, rural surroundings into more sinister territory.','The Hobbit',1),(7,'5353','The novel begins in the midst of Mike Noonan’s writer’s block. Ever since his wife, Jo, died during pregnancy from a sudden aneurysm, Noonan has struggled to socialize and to work. He moves permanently to his vacation home, Sara Laughs, at Dark Score Lake, hoping that a change of environment will dislodge his block. One day while out in the town, Noonan meets a young mother, Mattie Devore, and her little girl, Kyra. He learns that Mattie’s husband has died and that Kyra’s wealthy grandfather-in-law, Max Devore, is vying to gain custody. Noonan sympathizes with Mattie and Kyra’s predicament and decides to help get them a custody lawyer, John Storrow.','Bag of bones',5),(8,'2266173081','Dix millénaires avant les événements relatés dans Dune, l\'humanité est soumise à la tyrannie des Machines intelligentes. Celles-ci ont formé un réseau et leur élément le plus puissant, Omnius, s\'est emparé du pouvoir. Certains humains ont également choisi de greffer leur cerveau sur des machines, devenant des cyborgs. Ils se sont baptisés eux-mêmes les Titans.\n Ce sera le déclencheur de la Guerre des Machines, connue plus tard sous le nom de Jihad Butlérien et qui mènera à l\'interdiction absolue de la création de machines à l\'image de l\'intelligence humaine.\n Humains contre machines, ce combat donnera naissance aux Grandes Familles et aux Ordres comme le Bene Gesserit ou les Mentats.','Dune, la génèse. I La guerre des machines.',6);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` bigint NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `book_id` bigint DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `FKgf90y39kxxy7de579c3hvbu2` (`book_id`),
  CONSTRAINT `FKgf90y39kxxy7de579c3hvbu2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `copy`
--

DROP TABLE IF EXISTS `copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `copy` (
  `copy_id` bigint NOT NULL AUTO_INCREMENT,
  `available` bit(1) NOT NULL,
  `book_id` bigint NOT NULL,
  `library_id` bigint NOT NULL,
  PRIMARY KEY (`copy_id`),
  KEY `FKof5k7k6c41i06j6fj3slgsmam` (`book_id`),
  KEY `FKash9j8sqy9lwd73r7laqord1f` (`library_id`),
  CONSTRAINT `FKash9j8sqy9lwd73r7laqord1f` FOREIGN KEY (`library_id`) REFERENCES `library` (`library_id`),
  CONSTRAINT `FKof5k7k6c41i06j6fj3slgsmam` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `copy`
--

LOCK TABLES `copy` WRITE;
/*!40000 ALTER TABLE `copy` DISABLE KEYS */;
INSERT INTO `copy` VALUES (1,_binary '\0',3,7),(2,_binary '\0',3,7),(3,_binary '\0',3,7),(4,_binary '',3,8),(5,_binary '',3,9),(6,_binary '',4,7),(7,_binary '\0',4,7),(8,_binary '\0',4,8),(9,_binary '\0',4,9),(10,_binary '\0',5,7),(11,_binary '\0',5,8),(12,_binary '\0',5,9),(13,_binary '',6,7),(14,_binary '',6,7),(15,_binary '',6,9),(16,_binary '',7,8),(17,_binary '',7,9),(18,_binary '',7,9),(19,_binary '',1,7),(20,_binary '',1,7),(21,_binary '',1,9),(22,_binary '',2,7),(23,_binary '',2,7),(24,_binary '',2,8),(25,_binary '',2,8),(26,_binary '',2,9),(27,_binary '',2,9),(28,_binary '',8,7);
/*!40000 ALTER TABLE `copy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `library` (
  `library_id` bigint NOT NULL,
  `city` varchar(255) NOT NULL,
  `number` int NOT NULL,
  `street` varchar(50) NOT NULL,
  `zip_code` varchar(5) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  PRIMARY KEY (`library_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
INSERT INTO `library` VALUES (7,'Dunkirk',7,'road Danton','59240','Dunkirk\'s Library','03-28-63-56-85'),(8,'Petite-Synthe',30,'avenue de Petite-Synthe','59640','Petite-Synthe\'s Library','03-28-63-49-77'),(9,'Rosendaël',6,'avenue de la Mer','59240','Rosendaël\'s Library','03-28-63-95-21');
/*!40000 ALTER TABLE `library` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `loan_id` bigint NOT NULL AUTO_INCREMENT,
  `extended` bit(1) NOT NULL,
  `loan_end_date` datetime DEFAULT NULL,
  `loan_start_date` datetime NOT NULL,
  `returned` bit(1) NOT NULL,
  `copy_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`loan_id`),
  KEY `FKw8dbi4aoljiy817dnmnpaikd` (`copy_id`),
  KEY `FKxxm1yc1xty3qn1pthgj8ac4f` (`user_id`),
  CONSTRAINT `FKw8dbi4aoljiy817dnmnpaikd` FOREIGN KEY (`copy_id`) REFERENCES `copy` (`copy_id`),
  CONSTRAINT `FKxxm1yc1xty3qn1pthgj8ac4f` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (1,_binary '\0','2022-03-22 22:00:00','2022-02-23 23:00:00',_binary '\0',1,2),(2,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',2,3),(3,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',3,4),(4,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',7,2),(5,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',8,4),(6,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',9,5),(7,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',10,2),(8,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',11,4),(9,_binary '\0','2022-04-22 22:00:00','2022-03-23 23:00:00',_binary '\0',12,5),(10,_binary '\0','2022-05-05 22:00:00','2022-04-05 22:00:00',_binary '',28,3);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `number` int NOT NULL,
  `street` varchar(50) NOT NULL,
  `zip_code` varchar(5) NOT NULL,
  `email` varchar(50) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `roles` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--
LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary '','Dunkirk',32,'street Eugène Dumez','59240','lily@hotmail.com','Emily','Balsen','$2a$10$MsK7QFw8Bxpb8f6cY2jMnOdkgTMg.6TC5VEiyFrFiUXMyTGREdvBa','ADMIN'),(2,_binary '','Dunkirk',165,'street Houchard','59240','chris@hotmail.com','Dequeker','Christine','$2a$10$UAA9/2g0RZ/2Q91qSjaNGeE.ZPszQCUuuVp.WjsEv/Qt0VuCxAHEy','CUSTOMER'),(3,_binary '','Dunkirk',7,'street Danton','59240','marthe@hotmail.com','Schelvis','Marthe','$2a$10$Fx0.v4EZ8hJHPJZQLAmeF.MDgaOUBYAeLL1b0/V5MV9q/wYZhCAJy','EMPLOYEE'),(4,_binary '','Dunkirk',1010,'street Liem','59240','neo@hotmail.com','Reeves','Keanu','$2a$10$JXbh5/GVk5DKDNguzc53VOAW/F0YwDTWcxn8mJjLXc1G2xaIDdSPO','CUSTOMER'),(5,_binary '','Petite-Synthe',28,'street Lumix','59640','tom@hotmail.com','Yorke','Thomas','$2a$10$tzImdHPe3PdEXhbCanetduvtn0uJQGZFCRbq22l5Q1vSEUIkQgv2a','CUSTOMER'),(6,_binary '','Rosendaël',3,'boulevard Delta','59140','lullaby@hotmail.com','By','Lulla','$2a$10$i3T41Bclz72MUWAkNNpHWeRh82a9qBEMwIySUMIppEEYltb31GFY.','CUSTOMER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-12 14:22:24
