/*
SQLyog Professional v12.4.3 (64 bit)
MySQL - 10.1.38-MariaDB : Database - db_chatbot
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_chatbot` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_chatbot`;

/*Table structure for table `alm_producto` */

DROP TABLE IF EXISTS `alm_producto`;

CREATE TABLE `alm_producto` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(100) NOT NULL,
  `descripcion_producto` text,
  `precio_producto` decimal(20,2) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `estado_producto` enum('A','I') NOT NULL,
  `tipo_producto_id` int(11) NOT NULL,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alm_producto` */

/*Table structure for table `alm_proveedor` */

DROP TABLE IF EXISTS `alm_proveedor`;

CREATE TABLE `alm_proveedor` (
  `id_proveedor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_proveedor` varchar(150) NOT NULL,
  `ci_nit_proveedor` varchar(20) NOT NULL,
  `direccion_proveedor` text,
  `telefono_proveedor` varchar(20) NOT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alm_proveedor` */

/*Table structure for table `alm_stock_producto` */

DROP TABLE IF EXISTS `alm_stock_producto`;

CREATE TABLE `alm_stock_producto` (
  `id_stock` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad_actual` int(11) DEFAULT NULL,
  `cantidad_salida` int(11) DEFAULT NULL,
  `sucursal_id` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `producto_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_stock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alm_stock_producto` */

/*Table structure for table `alm_sucursal` */

DROP TABLE IF EXISTS `alm_sucursal`;

CREATE TABLE `alm_sucursal` (
  `id_sucursal` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_sucursal` varchar(100) NOT NULL,
  `direccion_sucursal` text,
  `estado_sucursal` enum('A','I') NOT NULL,
  PRIMARY KEY (`id_sucursal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alm_sucursal` */

/*Table structure for table `alm_tipo_producto` */

DROP TABLE IF EXISTS `alm_tipo_producto`;

CREATE TABLE `alm_tipo_producto` (
  `id_tipo_producto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(100) NOT NULL,
  `descripcion_tipo` text,
  PRIMARY KEY (`id_tipo_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alm_tipo_producto` */

/*Table structure for table `sis_usuario` */

DROP TABLE IF EXISTS `sis_usuario`;

CREATE TABLE `sis_usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(100) NOT NULL,
  `contrasenia` varchar(100) NOT NULL,
  `usuario_registro` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` enum('S','N') NOT NULL,
  `estado` enum('A','I') NOT NULL,
  `sucursal_id` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sis_usuario` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
