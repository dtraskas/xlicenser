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
-- Table `xlicenser`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `xlicenser`.`customer` ;

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
  `date_added` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE UNIQUE INDEX `id_UNIQUE` ON `xlicenser`.`customer` (`id` ASC);


-- -----------------------------------------------------
-- Table `xlicenser`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `xlicenser`.`product` ;

CREATE TABLE IF NOT EXISTS `xlicenser`.`product` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(128) NOT NULL,
  `licence_params` VARCHAR(512) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE UNIQUE INDEX `id_UNIQUE` ON `xlicenser`.`product` (`id` ASC);


-- -----------------------------------------------------
-- Table `xlicenser`.`licence`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `xlicenser`.`licence` ;

CREATE TABLE IF NOT EXISTS `xlicenser`.`licence` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `generated_key` VARCHAR(512) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `issue_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expiry_date` DATETIME NULL DEFAULT NULL,
  `auth_machine_info` VARCHAR(45) NULL DEFAULT NULL,
  `auth_date` DATETIME NULL DEFAULT NULL,
  `failed_authentications` INT(11) NULL DEFAULT '50',
  `max_authentications` INT(11) NULL DEFAULT '100',
  `comments` VARCHAR(512) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `xlicenser`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE UNIQUE INDEX `id_UNIQUE` ON `xlicenser`.`licence` (`id` ASC);

CREATE INDEX `FK_product_id_idx` ON `xlicenser`.`licence` (`product_id` ASC);


-- -----------------------------------------------------
-- Table `xlicenser`.`licence_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `xlicenser`.`licence_type` ;

CREATE TABLE IF NOT EXISTS `xlicenser`.`licence_type` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE UNIQUE INDEX `id_UNIQUE` ON `xlicenser`.`licence_type` (`id` ASC);


-- -----------------------------------------------------
-- Table `xlicenser`.`provider`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `xlicenser`.`provider` ;

CREATE TABLE IF NOT EXISTS `xlicenser`.`provider` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE UNIQUE INDEX `id_UNIQUE` ON `xlicenser`.`provider` (`id` ASC);


-- -----------------------------------------------------
-- Table `xlicenser`.`registration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `xlicenser`.`registration` ;

CREATE TABLE IF NOT EXISTS `xlicenser`.`registration` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_id` INT(11) NULL DEFAULT NULL,
  `product_id` INT(11) NULL DEFAULT NULL,
  `provider_id` INT(11) NULL DEFAULT NULL,
  `licence_type` INT(11) NULL DEFAULT NULL,
  `quantity` INT(11) NULL DEFAULT '1',
  `price` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
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
DEFAULT CHARACTER SET = latin1;

CREATE UNIQUE INDEX `id_UNIQUE` ON `xlicenser`.`registration` (`id` ASC);

CREATE INDEX `FK_product_id_idx` ON `xlicenser`.`registration` (`product_id` ASC);

CREATE INDEX `FK_provider_id_idx` ON `xlicenser`.`registration` (`provider_id` ASC);

CREATE INDEX `FK_licence_type_id_idx` ON `xlicenser`.`registration` (`licence_type` ASC);


-- -----------------------------------------------------
-- Table `xlicenser`.`licence_registration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `xlicenser`.`licence_registration` ;

CREATE TABLE IF NOT EXISTS `xlicenser`.`licence_registration` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `registration_id` INT(11) NOT NULL,
  `licence_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
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
DEFAULT CHARACTER SET = latin1;

CREATE UNIQUE INDEX `id_UNIQUE` ON `xlicenser`.`licence_registration` (`id` ASC);

CREATE INDEX `FK_registration_id_idx` ON `xlicenser`.`licence_registration` (`registration_id` ASC);

CREATE INDEX `FK_licence_id_idx` ON `xlicenser`.`licence_registration` (`licence_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
