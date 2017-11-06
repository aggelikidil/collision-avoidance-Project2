-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema database
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `database` ;

-- -----------------------------------------------------
-- Schema database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `database` DEFAULT CHARACTER SET utf8 ;
USE `database` ;

-- -----------------------------------------------------
-- Table `database`.`collisions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `database`.`collisions` (
  `LINE_id` INT NOT NULL AUTO_INCREMENT,
  `terminal_id` VARCHAR(45) NULL,
  `sensortype1` VARCHAR(45) NULL,
  `no_of_values1` INT NULL,
  `sensorvalue1` FLOAT NULL,
  `unit_sensor1` VARCHAR(45) NULL,
  `sensortype2` VARCHAR(45) NULL,
  `no_of_values2` INT NULL,
  `sensorvalue2x` FLOAT NULL,
  `sensorvalue2y` FLOAT NULL,
  `sensorvalue2z` FLOAT NULL,
  `unit_sensor2` VARCHAR(45) NULL,
  `date_time` DATETIME(6) NULL,
  `latitude` FLOAT NULL,
  `longtitude` FLOAT NULL,
  `confirmed_collision` TINYINT(1) NULL,
  PRIMARY KEY (`LINE_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `database`.`collisions`
-- -----------------------------------------------------
START TRANSACTION;
USE `database`;
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (1, 'LENOVOTAB', 'PROXIMITY', 1, 4, 'CM', 'ACCELEROMETER', 3, -0.160, -0.877, 0.499, 'M/S^2', '2016-12-25 10:01:03', 39.02, -119.02, FALSE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (2, 'HUAWEIG750', 'PROXIMITY', 1, 4.0, 'CM', 'ACCELEROMETER', 3, 0.493, -0.878, 0.489, 'M/S^2', '2016-12-25 10:02:22', 27.56, -82.68, TRUE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (3, 'SAMSUNGJ5', 'PROXIMITY', 1, 8.0, 'CM', 'ACCELEROMETER', 3, -0.102, -0.901, 0.450, 'M/S^2', '2016-12-25 10:02:23', 27.56, -82.68, TRUE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (4, 'LENOVOTAB', 'PROXIMITY', 1, 2.0, 'CM', 'ACCELEROMETER', 3, 0.157, -0.854, 0.266, 'M/S^2', '2016-12-25 10:05:45', 46.20, -123.42, FALSE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (5, 'HUAWEIG750', 'PROXIMITY', 1, 1.0, 'CM', 'ACCELEROMETER', 3, 0.000, -1.261, -0.011, 'M/S^2', '2016-12-25 10:09:58', 40.17, -76.25, FALSE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (6, 'SAMSUNGJ5', 'PROXIMITY', 1, 0.0, 'CM', 'ACCELEROMETER', 3, -0.043, -0.852, 9.81, 'M/S^2', '2016-12-25 11:04:22', 38.91, -77.05, FALSE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (7, 'HUAWEIG750', 'PROXIMITY', 1, 0.0, 'CM', 'ACCELEROMETER', 3, 0.016, -0.675, -0.124, 'M/S^2', '2016-12-25 11:08:34', 39.49, -74.34, FALSE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (8, 'LENOVOTAB', 'PROXIMITY', 1, 6.0, 'CM', 'ACCELEROMETER', 3, 0.008, -2.000, 0.755, 'M/S^2', '2016-12-25 12:01:45', 42.50, -70.84, FALSE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (9, 'SAMSUNGJ5', 'PROXIMITY', 1, 5.0, 'CM', 'ACCELEROMETER', 3, -0.031, -0.329, -9.81, 'M/S^2', '2016-12-25 13:45:22', 44.02, -88.55, FALSE);
INSERT INTO `database`.`collisions` (`LINE_id`, `terminal_id`, `sensortype1`, `no_of_values1`, `sensorvalue1`, `unit_sensor1`, `sensortype2`, `no_of_values2`, `sensorvalue2x`, `sensorvalue2y`, `sensorvalue2z`, `unit_sensor2`, `date_time`, `latitude`, `longtitude`, `confirmed_collision`) VALUES (10, 'HUAWEIG750', 'PROXIMITY', 1, 1.0, 'CM', 'ACCELEROMETER', 3, -0.016, -1.261, 0.443, 'M/S^2', '2016-12-25 10:01:22', 41.15, 170.59, FALSE);

COMMIT;

