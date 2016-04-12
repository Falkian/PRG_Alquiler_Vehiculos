CREATE DATABASE  IF NOT EXISTS `alquilervehiculos` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `alquilervehiculos`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: alquilervehiculos
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `camiones`
--

DROP TABLE IF EXISTS `camiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `camiones` (
  `matricula` varchar(7) NOT NULL,
  PRIMARY KEY (`matricula`),
  KEY `fk_camionestc_idx` (`matricula`),
  CONSTRAINT `fk_camionestc` FOREIGN KEY (`matricula`) REFERENCES `transportescarga` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camiones`
--

LOCK TABLES `camiones` WRITE;
/*!40000 ALTER TABLE `camiones` DISABLE KEYS */;
INSERT INTO `camiones` VALUES ('2222BBB'),('5555EEE');
/*!40000 ALTER TABLE `camiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `telefono` int(9) NOT NULL,
  `vip` char(9) NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES ('11111111A','Arturo','Calle Uno',999999991,'S'),('22222222B','Roberto','Calle Dos',999999992,'N'),('44444444D','Diego','Calle Tres',999999993,'N'),('66666666E','Manuela','Calle Cuatro',999999994,'S');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coches`
--

DROP TABLE IF EXISTS `coches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coches` (
  `matricula` varchar(7) NOT NULL,
  PRIMARY KEY (`matricula`),
  KEY `fk_cochetc_idx` (`matricula`),
  CONSTRAINT `fk_cochetp` FOREIGN KEY (`matricula`) REFERENCES `transportespersonas` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coches`
--

LOCK TABLES `coches` WRITE;
/*!40000 ALTER TABLE `coches` DISABLE KEYS */;
INSERT INTO `coches` VALUES ('1111AAA'),('4444DDD');
/*!40000 ALTER TABLE `coches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furgonetas`
--

DROP TABLE IF EXISTS `furgonetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `furgonetas` (
  `matricula` varchar(7) NOT NULL,
  PRIMARY KEY (`matricula`),
  KEY `fk_furgonetastc_idx` (`matricula`),
  CONSTRAINT `fk_furgonetastc` FOREIGN KEY (`matricula`) REFERENCES `transportescarga` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furgonetas`
--

LOCK TABLES `furgonetas` WRITE;
/*!40000 ALTER TABLE `furgonetas` DISABLE KEYS */;
INSERT INTO `furgonetas` VALUES ('3333CCC');
/*!40000 ALTER TABLE `furgonetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historialalquileres`
--

DROP TABLE IF EXISTS `historialalquileres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historialalquileres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `matricula` varchar(7) NOT NULL,
  `dni` varchar(9) DEFAULT NULL,
  `numdias` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_historialvehiculo_idx` (`matricula`),
  KEY `fk_historialcliente_idx` (`dni`),
  CONSTRAINT `fk_historialcliente` FOREIGN KEY (`dni`) REFERENCES `clientes` (`dni`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_historialvehiculo` FOREIGN KEY (`matricula`) REFERENCES `vehiculos` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historialalquileres`
--

LOCK TABLES `historialalquileres` WRITE;
/*!40000 ALTER TABLE `historialalquileres` DISABLE KEYS */;
INSERT INTO `historialalquileres` VALUES (1,'1111AAA','44444444D',10),(2,'2222BBB','66666666E',2);
/*!40000 ALTER TABLE `historialalquileres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `microbuses`
--

DROP TABLE IF EXISTS `microbuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `microbuses` (
  `matricula` varchar(7) NOT NULL,
  PRIMARY KEY (`matricula`),
  KEY `fk_microbustp_idx` (`matricula`),
  CONSTRAINT `fk_microbustp` FOREIGN KEY (`matricula`) REFERENCES `transportespersonas` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `microbuses`
--

LOCK TABLES `microbuses` WRITE;
/*!40000 ALTER TABLE `microbuses` DISABLE KEYS */;
INSERT INTO `microbuses` VALUES ('6666FFF');
/*!40000 ALTER TABLE `microbuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transportescarga`
--

DROP TABLE IF EXISTS `transportescarga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transportescarga` (
  `matricula` varchar(7) NOT NULL,
  `pma` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`matricula`),
  KEY `fk_tcvehiculo_idx` (`matricula`),
  CONSTRAINT `fk_tcvehiculo` FOREIGN KEY (`matricula`) REFERENCES `vehiculos` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transportescarga`
--

LOCK TABLES `transportescarga` WRITE;
/*!40000 ALTER TABLE `transportescarga` DISABLE KEYS */;
INSERT INTO `transportescarga` VALUES ('2222BBB',1550.00),('3333CCC',650.50),('5555EEE',2750.32);
/*!40000 ALTER TABLE `transportescarga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transportespersonas`
--

DROP TABLE IF EXISTS `transportespersonas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transportespersonas` (
  `matricula` varchar(7) NOT NULL,
  `numplazas` int(3) NOT NULL,
  PRIMARY KEY (`matricula`),
  KEY `fk_tpvehiculo_idx` (`matricula`),
  CONSTRAINT `fk_tpvehiculo` FOREIGN KEY (`matricula`) REFERENCES `vehiculos` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transportespersonas`
--

LOCK TABLES `transportespersonas` WRITE;
/*!40000 ALTER TABLE `transportespersonas` DISABLE KEYS */;
INSERT INTO `transportespersonas` VALUES ('1111AAA',3),('4444DDD',5),('6666FFF',20);
/*!40000 ALTER TABLE `transportespersonas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculos` (
  `matricula` varchar(7) NOT NULL,
  `alquiladoalgunavez` char(1) DEFAULT 'N',
  `clientealquilador` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`matricula`),
  KEY `fk_vehiculocliente_idx` (`clientealquilador`),
  CONSTRAINT `fk_vehiculocliente` FOREIGN KEY (`clientealquilador`) REFERENCES `clientes` (`dni`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
INSERT INTO `vehiculos` VALUES ('1111AAA','S','22222222B'),('2222BBB','N','66666666E'),('3333CCC','N',NULL),('4444DDD','N',NULL),('5555EEE','N',NULL),('6666FFF','N',NULL);
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-10 20:29:44
