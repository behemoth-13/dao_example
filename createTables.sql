CREATE DATABASE  IF NOT EXISTS `training`;
USE `training`;

DROP TABLE IF EXISTS `owner`;
CREATE TABLE `owner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birth_date` DATE NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `owner_id` INT NOT NULL,
  `manufacture_date` DATE NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_owner_id_idx` (`owner_id`),
  CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
);