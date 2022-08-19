-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
SHOW WARNINGS;
-- -----------------------------------------------------
-- Schema bank
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bank` ;

-- -----------------------------------------------------
-- Schema bank
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bank` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `bank` ;

-- -----------------------------------------------------
-- Table `bank`.`bill_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bank`.`bill_status` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `bank`.`bill_status` (
                                                    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                    `status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `bank`.`card_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bank`.`card_status` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `bank`.`card_status` (
                                                    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                    `status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `bank`.`client_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bank`.`client_status` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `bank`.`client_status` (
                                                      `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                      `status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)
    ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `bank`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bank`.`role` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `bank`.`role` (
                                             `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                             `role` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
    ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `bank`.`client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bank`.`client` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `bank`.`client` (
                                               `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                               `login` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `client_status_id` INT UNSIGNED NOT NULL,
    `role_id` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_client_client_status1_idx` (`client_status_id` ASC) VISIBLE,
    INDEX `fk_client_role1_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_client_client_status1`
    FOREIGN KEY (`client_status_id`)
    REFERENCES `bank`.`client_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_client_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `bank`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `bank`.`card`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bank`.`card` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `bank`.`card` (
                                             `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                             `name` VARCHAR(255) NOT NULL,
    `balance` INT NOT NULL,
    `card_status_id` INT UNSIGNED NOT NULL,
    `client_id` INT UNSIGNED NOT NULL,
    `client_name` VARCHAR(255) NOT NULL
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    INDEX `fk_card_card_status_idx` (`card_status_id` ASC) VISIBLE,
    INDEX `fk_card_client1_idx` (`client_id` ASC) VISIBLE,
    CONSTRAINT `fk_card_card_status`
    FOREIGN KEY (`card_status_id`)
    REFERENCES `bank`.`card_status` (`id`),
    CONSTRAINT `fk_card_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `bank`.`client` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `bank`.`bill`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bank`.`bill` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `bank`.`bill` (
                                             `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                             `sum` INT NOT NULL,
                                             `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                             `card_id` INT UNSIGNED NOT NULL,
                                             `bill_status_id` INT UNSIGNED NOT NULL,
                                             PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_bill_card1_idx` (`card_id` ASC) VISIBLE,
    INDEX `fk_bill_bill_status1_idx` (`bill_status_id` ASC) VISIBLE,
    CONSTRAINT `fk_bill_bill_status1`
    FOREIGN KEY (`bill_status_id`)
    REFERENCES `bank`.`bill_status` (`id`),
    CONSTRAINT `fk_bill_card1`
    FOREIGN KEY (`card_id`)
    REFERENCES `bank`.`card` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
