-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-11-2019 a las 00:36:33
-- Versión del servidor: 10.1.34-MariaDB
-- Versión de PHP: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventario_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id` int(10) UNSIGNED NOT NULL,
  `sucursal_id` int(10) UNSIGNED DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `celular` varchar(155) NOT NULL,
  `tipo` enum('ADMIN','EMPLEADO') NOT NULL,
  `fecha_reg` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id`, `sucursal_id`, `nombre`, `direccion`, `celular`, `tipo`, `fecha_reg`) VALUES
(1, 1, 'admin', 'direccion admin', '78945612', 'ADMIN', '2019-11-22'),
(2, 2, 'PEDRO CARVAJAL MAMANI', 'ZONA LOS OLIVOS CALLE 4 #22', '68432145', 'EMPLEADO', '2019-11-22'),
(3, 3, 'PEDRO GONZALES', 'ZONA LOS PEDREGALES CALLE 3 #33', '68412345', 'EMPLEADO', '2019-11-22'),
(5, 3, 'ALBERTO MAMANI', 'ZONA LOS OLIVOS CALLE 3 # 32', '78945666', 'EMPLEADO', '2019-11-22'),
(6, 2, 'PATRICIA', 'ZONA PRUEBA', '78933333', 'EMPLEADO', '2019-11-22'),
(7, 1, 'JORGE CLAVIJO', 'ZONA FERROVIARIA C.3 #22', '6788889', 'EMPLEADO', '2019-11-22');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(10) UNSIGNED NOT NULL,
  `codigo` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_reg` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `codigo`, `nombre`, `descripcion`, `fecha_reg`) VALUES
(1, 'P001', 'PRODUCTO 1', NULL, '2019-11-22'),
(2, 'P002', 'PRODUCTO 2', NULL, '2019-11-22'),
(3, 'P003', 'PRODUCTO 3', NULL, '2019-11-22');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_sucursal`
--

CREATE TABLE `producto_sucursal` (
  `id` int(10) UNSIGNED NOT NULL,
  `producto_id` int(10) UNSIGNED NOT NULL,
  `sucursal_id` int(10) UNSIGNED NOT NULL,
  `stock` int(11) NOT NULL,
  `fecha_reg` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto_sucursal`
--

INSERT INTO `producto_sucursal` (`id`, `producto_id`, `sucursal_id`, `stock`, `fecha_reg`) VALUES
(1, 1, 1, 25, '2019-11-22'),
(2, 2, 1, 20, '2019-11-22'),
(3, 3, 1, 35, '2019-11-22'),
(4, 1, 2, 35, '2019-11-22');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursales`
--

CREATE TABLE `sucursales` (
  `id` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_reg` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `sucursales`
--

INSERT INTO `sucursales` (`id`, `nombre`, `descripcion`, `fecha_reg`) VALUES
(1, 'CENTRAL', 'SUCURSAL CENTRAL', '2019-11-22'),
(2, 'SUCURSAL 1', 'PRIMERA SUCURSAL', '2019-11-22'),
(3, 'SUCURSAL 2', NULL, '2019-11-22');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sucursal_id` (`sucursal_id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo` (`codigo`);

--
-- Indices de la tabla `producto_sucursal`
--
ALTER TABLE `producto_sucursal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_id` (`producto_id`),
  ADD KEY `sucursal_id` (`sucursal_id`);

--
-- Indices de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `producto_sucursal`
--
ALTER TABLE `producto_sucursal`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD CONSTRAINT `empleados_ibfk_1` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `producto_sucursal`
--
ALTER TABLE `producto_sucursal`
  ADD CONSTRAINT `producto_sucursal_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `producto_sucursal_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `producto_sucursal_ibfk_3` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
