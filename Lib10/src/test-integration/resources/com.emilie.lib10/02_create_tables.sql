--CREATE DATABASE  IF NOT EXISTS `LIB10`;
-- USE `LIB10`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: LIB10
-- ------------------------------------------------------
-- Server version	8.0.25

--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS LIB10.user_details;
CREATE TABLE LIB10.user (
  id SERIAL NOT NULL,
  city varchar(255) NOT NULL,
  number int NOT NULL,
  street varchar(50) NOT NULL,
  zip_code varchar(5) NOT NULL,
  email varchar(50) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  active bit(1) DEFAULT NULL,
  password varchar(255) NOT NULL,
  roles varchar(255) NOT NULL,
  CONSTRAINT user_pk PRIMARY KEY (id)--,

); --ENGINE=InnoDB AUTO_INCREMENT=800 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--UNIQUE KEY UK_ob8kqyqqgmefl0aco34akdtpe (email)

-- PRIMARY KEY (id),
--CONSTRAINT ecriture_comptable_pk PRIMARY KEY (id)

--
-- Table structure for table `library`
--
DROP TABLE IF EXISTS LIB10.library;
CREATE TABLE LIB10.library (
  library_id SERIAL NOT NULL,
  city varchar(255) NOT NULL,
  number int NOT NULL,
  street varchar(50) NOT NULL,
  zip_code varchar(5) NOT NULL,
  name varchar(255) NOT NULL,
  phone_number varchar(20) NOT NULL,
  CONSTRAINT library_pk PRIMARY KEY (library_id)
); --ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `author`
--
DROP TABLE IF EXISTS LIB10.author;
CREATE TABLE LIB10.author (
  author_id SERIAL NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  CONSTRAINT author_pk PRIMARY KEY (author_id)
); -- ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `book`
--
DROP TABLE IF EXISTS LIB10.book;
CREATE TABLE LIB10.book (
  id SERIAL NOT NULL,
  isbn varchar(30) NOT NULL,
  summary varchar(800) DEFAULT NULL,
  title varchar(50) NOT NULL,
  author_id bigint NOT NULL,
  CONSTRAINT book_pk PRIMARY KEY (id),
 -- KEY `FKklnrv3weler2ftkweewlky958` (`author_id`),
  CONSTRAINT book_author_fk FOREIGN KEY (author_id) REFERENCES LIB10.author (author_id)
); -- ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `copy`
--
DROP TABLE IF EXISTS LIB10.copy;
CREATE TABLE LIB10.copy (
  copy_id SERIAL NOT NULL,
  available bit(1) NOT NULL,
  book_id bigint NOT NULL,
  library_id bigint NOT NULL,
  CONSTRAINT copy_pk PRIMARY KEY (copy_id),
 -- KEY `FKof5k7k6c41i06j6fj3slgsmam` (`book_id`),
 -- KEY `FKash9j8sqy9lwd73r7laqord1f` (`library_id`),
  CONSTRAINT copy_library_fk FOREIGN KEY (library_id) REFERENCES LIB10.library (library_id),
  CONSTRAINT copy_book_fk FOREIGN KEY (book_id) REFERENCES LIB10.book (id)
); -- ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `loan`
--
DROP TABLE IF EXISTS LIB10.loan;
CREATE TABLE LIB10.loan (
  loan_id SERIAL NOT NULL,
  extended bit(1) NOT NULL,
  loan_end_date timestamp DEFAULT NULL,
  loan_start_date timestamp NOT NULL,
  copy_id bigint NOT NULL,
  user_id bigint NOT NULL,
  returned bit(1) NOT NULL,
  CONSTRAINT loan_id PRIMARY KEY (loan_id),
 -- KEY `FKw8dbi4aoljiy817dnmnpaikd` (`copy_id`),
--  KEY `FKxxm1yc1xty3qn1pthgj8ac4f` (`user_id`),
  CONSTRAINT loan_copy_fk FOREIGN KEY (copy_id) REFERENCES LIB10.copy (copy_id),
  CONSTRAINT loan_user_fk FOREIGN KEY (user_id) REFERENCES LIB10.user_details (id)
); -- ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `reservation`
--
DROP TABLE IF EXISTS LIB10.reservation;
CREATE TABLE LIB10.reservation (
  reservation_id SERIAL NOT NULL,
  is_active bit(1) NOT NULL,
  end_date timestamp DEFAULT NULL,
  start_date timestamp NOT NULL,
  book_id bigint NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT reservation_id PRIMARY KEY (reservation_id),
--  KEY `FKirxtcw4s6lhwi6l9ocrk6bjfy` (`book_id`),
 -- KEY `FKm4oimk0l1757o9pwavorj6ljg` (`user_id`),
  CONSTRAINT reservation_book_fk FOREIGN KEY (book_id) REFERENCES LIB10.book (id),
  CONSTRAINT reservation_user_fk FOREIGN KEY (user_id) REFERENCES LIB10.user_details (id)
); -- ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

