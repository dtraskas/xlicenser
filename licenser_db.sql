-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema xlicenser
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema xlicenser
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `xlicenser` DEFAULT CHARACTER SET latin1 ;
USE `xlicenser` ;

-- -----------------------------------------------------
-- Table `xlicenser`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`product` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(128) NOT NULL,
  `licence_params` BLOB NULL DEFAULT NULL,
  `temp` VARCHAR(512) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `xlicenser`.`licence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`licence` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `key_info` VARCHAR(512) NULL DEFAULT NULL,
  `generated_key` VARCHAR(512) NULL DEFAULT NULL,
  `customer_key` VARCHAR(512) NULL DEFAULT NULL,
  `product_id` INT(11) NOT NULL,
  `issue_date` DATETIME NOT NULL,
  `expiry_date` DATETIME NULL DEFAULT NULL,
  `comments` VARCHAR(512) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_product_id_idx` (`product_id` ASC),
  CONSTRAINT `FK_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `xlicenser`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `xlicenser`.`activation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`activation` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `licence_id` INT(11) NOT NULL,
  `date` DATETIME NOT NULL,
  `machine_code` VARCHAR(256) NULL DEFAULT NULL,
  `ipaddress` VARCHAR(45) NOT NULL,
  `success` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_licence_id_idx` (`licence_id` ASC),
  INDEX `FK_auth_licence_id_idx` (`licence_id` ASC),
  CONSTRAINT `FK_auth_licence_id`
    FOREIGN KEY (`licence_id`)
    REFERENCES `xlicenser`.`licence` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `xlicenser`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`customer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(128) NOT NULL,
  `lname` VARCHAR(128) NOT NULL,
  `company` VARCHAR(128) NULL DEFAULT NULL,
  `street` VARCHAR(128) NULL DEFAULT NULL,
  `city` VARCHAR(128) NULL DEFAULT NULL,
  `state` VARCHAR(128) NULL DEFAULT NULL,
  `country` VARCHAR(128) NULL DEFAULT NULL,
  `postcode` VARCHAR(128) NULL DEFAULT NULL,
  `telephone` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(128) NOT NULL,
  `comments` VARCHAR(128) NULL DEFAULT NULL,
  `date_added` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `xlicenser`.`licence_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`licence_type` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `xlicenser`.`provider`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`provider` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `xlicenser`.`registration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`registration` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL DEFAULT NULL,
  `customer_id` INT(11) NULL DEFAULT NULL,
  `product_id` INT(11) NULL DEFAULT NULL,
  `provider_id` INT(11) NULL DEFAULT NULL,
  `licence_type` INT(11) NULL DEFAULT NULL,
  `quantity` INT(11) NULL DEFAULT '1',
  `price` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_product_id_idx` (`product_id` ASC),
  INDEX `FK_provider_id_idx` (`provider_id` ASC),
  INDEX `FK_licence_type_id_idx` (`licence_type` ASC),
  CONSTRAINT `FK_licence_type_id`
    FOREIGN KEY (`licence_type`)
    REFERENCES `xlicenser`.`licence_type` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_product2_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `xlicenser`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_provider2_id`
    FOREIGN KEY (`provider_id`)
    REFERENCES `xlicenser`.`provider` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `xlicenser`.`licence_registration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xlicenser`.`licence_registration` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `registration_id` INT(11) NOT NULL,
  `licence_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_registration_id_idx` (`registration_id` ASC),
  INDEX `FK_licence_id_idx` (`licence_id` ASC),
  CONSTRAINT `FK_licence_id`
    FOREIGN KEY (`licence_id`)
    REFERENCES `xlicenser`.`licence` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_registration_id`
    FOREIGN KEY (`registration_id`)
    REFERENCES `xlicenser`.`registration` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
