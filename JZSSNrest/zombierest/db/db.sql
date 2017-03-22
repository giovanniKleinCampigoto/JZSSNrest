-- MySQL Script generated by MySQL Workbench
-- Ter 21 Mar 2017 19:38:29 BRT
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema JZSSN
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema JZSSN
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `JZSSN` DEFAULT CHARACTER SET utf8 ;
USE `JZSSN` ;

-- -----------------------------------------------------
-- Table `JZSSN`.`survivor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JZSSN`.`survivor` (
  `idsurvivor` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `age` INT NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `lonx` INT NOT NULL,
  `lony` INT NOT NULL,
  `infected` TINYINT(1) NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lostpoints` BIGINT NULL,
  PRIMARY KEY (`idsurvivor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `JZSSN`.`inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JZSSN`.`inventory` (
  `idinventory` INT(11) NOT NULL,
  `water` INT NOT NULL,
  `food` INT NOT NULL,
  `meds` INT NOT NULL,
  `ammo` INT NOT NULL,
  `idsurvivor` INT(11) NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `fk_inventory_survivor_idx` (`idsurvivor` ASC),
  UNIQUE INDEX `idsurvivor_UNIQUE` (`idsurvivor` ASC),
  PRIMARY KEY (`idinventory`),
  CONSTRAINT `fk_inventory_survivor`
    FOREIGN KEY (`idsurvivor`)
    REFERENCES `JZSSN`.`survivor` (`idsurvivor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;