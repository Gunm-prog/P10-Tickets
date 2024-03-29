-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: lib7
-- ------------------------------------------------------
-- Server version	8.0.25

--
-- Dumping data for table `user`
--
-- LOCK TABLES `user` WRITE;
INSERT INTO LIB10.user VALUES
    (5,'Lomme',43,'rue laba','59160','moi@moi.moi','Ben ','CTOTO',B'1','$2a$10$P5Sv/.DHDLP98dJn4dYIZOrroMBRcqKTL0mbbUsR/0LFZAbAJNie6','ADMIN')
    ,(6,'LOMME',2,'Allée des acacias','59160','bob@bob.bob','Bob','Bob',B'1','$2a$10$P5Sv/.DHDLP98dJn4dYIZOrroMBRcqKTL0mbbUsR/0LFZAbAJNie6','CUSTOMER')
    ,(13,'Lomme',43,'rue laba','59160','CTOTO@moi.moi','Ben c''est moaaaa','CTOTO',B'1','$2a$10$P5Sv/.DHDLP98dJn4dYIZOrroMBRcqKTL0mbbUsR/0LFZAbAJNie6','CUSTOMER')
    ,(16,'ee',0,'ee','ee','ter@tre.fr','erhyz','sgz',B'1','$2a$10$VjSp7QmYr/vAMDgMcqTc1eu68ijgE/GFL/1oXl/I12lLJ1ktADW9i','CUSTOMER')
    ,(17,'ee',0,'ee','ee','tereee@tre.fr','erhyz','sgz',B'1','$2a$10$ceKoPvkVB5oVfZdiDoQS0uTwq5DTIsVYpQOWDkEHQttZxcAWeT/fu','CUSTOMER')
    ,(18,'ee',0,'ee','ee','tereeaaae@tre.fr','erhyz','sgz',B'1','$2a$10$NXCn3aJTf91xoKlEfgneKOP0SuPQNKf35TjJFmgIzj/jULufsXA9q','CUSTOMER')
    ,(19,'ee',0,'ee','ee','tereereffeaaae@tre.fr','erhyz','sgz',B'1','$2a$10$tT15FxLEn0tmjig3Ppu3DujVU.m0Yo7YgWEPCa/PZPAx3nLYXZ01y','CUSTOMER')
    ,(20,'ff',0,'ff','ff','gt@gt.fr','aezzeg','zaefzae',B'1','$2a$10$VR.5wxXk9oNZRo1wHTIdielSFVdWZ/NQr0VcA16Kz3Aed4RtYmPWK','CUSTOMER')
    ,(21,'dd',434,'dd','dd','liou@lou.lou','der','zer',B'1','$2a$10$2Q3r3zblNunIJ7T7cHBVHuvFw1Faq2irYCy/MS0nozLIxc1j3J1o.','CUSTOMER')
    ;
-- UNLOCK TABLES;

--
-- Dumping data for table `library`
--
--LOCK TABLES `library` WRITE;
INSERT INTO LIB10.library VALUES
    (9,'RoukaCity',45,'roukadour','65432','Lalibrarykiki','6688999'),(10,'RoukaCity',45,'roukadour','65432','superLiib','6688999')
    ,(14,'RoukaCity',45,'roukadour','65432','LalibaLibaaaa','6688999'),(15,'RoukaCity',45,'roukadour','65432','KesTuLi','6688999')
    ,(43,'RoukaCity',45,'roukadour','65432','Ckoi','668899923')
    ;
--UNLOCK TABLES;

--
-- Dumping data for table `author`
--
--LOCK TABLES `author` WRITE;
INSERT INTO LIB10.author VALUES
    (1,'Lecry','Vayn')
    ;
--UNLOCK TABLES;

--
-- Dumping data for table `book`
--
--LOCK TABLES `book` WRITE;
INSERT INTO LIB10.book VALUES
    (1,'SHBE456','Un livre qui blablate','Livre I',1)
    ,(2,'SHDFFR','Un livre qui blablate','Livre II',1)
    ,(4,'SHQE426','Un Deux Rois','AzoumTazief',1)
    ;
--  UNLOCK TABLES;

--
-- Dumping data for table `copy`
--
--LOCK TABLES `copy` WRITE;
INSERT INTO LIB10.copy VALUES
    (1,B'0',1,9)
    ,(4,B'0',2,10)
    ,(5,B'0',1,10)
    ,(6,B'0',2,9)
    ,(7,B'1',1,9)
    ,(8,B'1',1,9)
    ,(9,B'1',1,9)
    ,(10,B'1',2,9)
    ,(11,B'1',2,14)
    ,(12,B'1',2,14)
    ,(13,B'1',2,14)
    ,(14,B'1',2,14)
    ,(15,B'1',2,14)
    ,(16,B'1',2,14)
    ,(17,B'1',2,14)
    ,(18,B'1',1,14)
    ,(19,B'1',1,14)
    ,(20,B'1',1,14)
    ,(21,B'1',1,14)
    ,(22,B'1',1,15)
    ,(23,B'1',1,15)
    ,(24,B'1',1,15)
    ,(25,B'1',1,15)
    ,(26,B'1',1,15)
    ,(27,B'1',1,15)
    ,(28,B'1',2,15)
    ,(29,B'1',2,15)
    ,(30,B'1',2,15)
    ,(31,B'1',2,15)
    ,(32,B'0',4,15)
    ;
-- UNLOCK TABLES;



--
-- Dumping data for table `loan`
--
-- LOCK TABLES `loan` WRITE;
INSERT INTO LIB10.loan VALUES
    (1,B'1','2021-07-29 22:00:00','2021-07-28 22:00:00',1,5,B'1')
    ,(2,B'1','2021-08-25 22:00:00','2021-07-28 22:00:00',4,6,B'0')
    ,(4,B'1','2021-10-25 22:00:00','2021-08-26 22:00:00',6,6,B'0')
    ,(5,B'1','2021-10-25 22:00:00','2021-08-26 22:00:00',5,5,B'0')
    ,(6,B'0','2022-02-25 23:00:00','2022-01-26 23:00:00',32,5,B'1')
    ,(7,B'0','2022-02-25 23:00:00','2022-01-26 23:00:00',32,6,B'1')
    ,(8,B'0','2022-02-25 23:00:00','2022-01-26 23:00:00',32,13,B'1')
    ,(9,B'0','2022-02-26 23:00:00','2022-01-27 23:00:00',32,5,B'0')
    ;
-- UNLOCK TABLES;

--
-- Dumping data for table `reservation`
--
-- LOCK TABLES reservation` WRITE;
-- UNLOCK TABLES;
