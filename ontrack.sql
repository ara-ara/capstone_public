-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `user_id` INT NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `date_registered` DATE NULL,
  `organization` VARCHAR(100) NOT NULL,
  `height` INT NULL,
  `weight` INT NULL,
  `followers` INT NULL,
  `view_flag` INT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `password_UNIQUE` (`password` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`meet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`meet` (
  `user_id` INT NOT NULL,
  `meet_id` INT NOT NULL,
  `event_name` VARCHAR(45) NOT NULL,
  `event_location` VARCHAR(45) NULL,
  `event_date` VARCHAR(45) NULL,
  `results` VARCHAR(45) NULL,
  `video_id` INT NULL,
  PRIMARY KEY (`user_id`, `meet_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `meet_id_UNIQUE` (`meet_id` ASC),
  CONSTRAINT `fkme_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`practice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`practice` (
  `user_id` INT NOT NULL,
  `practice_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`, `practice_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `practice_id_UNIQUE` (`practice_id` ASC),
  CONSTRAINT `fkpr_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`event` (
  `practice_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `reps` INT ZEROFILL NULL,
  `weight` INT ZEROFILL NULL,
  `best` INT ZEROFILL NULL,
  UNIQUE INDEX `practice_id_UNIQUE` (`practice_id` ASC),
  PRIMARY KEY (`practice_id`, `event_id`),
  UNIQUE INDEX `event_id_UNIQUE` (`event_id` ASC),
  CONSTRAINT `fkev_practice_id`
    FOREIGN KEY (`practice_id`)
    REFERENCES `mydb`.`practice` (`practice_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`routine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`routine` (
  `user_id` INT NOT NULL,
  `routine_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `focus` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`, `routine_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `routine_id_UNIQUE` (`routine_id` ASC),
  CONSTRAINT `fkro_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`exercise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`exercise` (
  `routine_id` INT NOT NULL,
  `exercise_id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `set` INT ZEROFILL NULL,
  `reps` INT ZEROFILL NULL,
  `weight` INT ZEROFILL NULL,
  PRIMARY KEY (`routine_id`, `exercise_id`),
  UNIQUE INDEX `routine_id_UNIQUE` (`routine_id` ASC),
  UNIQUE INDEX `exercise_id_UNIQUE` (`exercise_id` ASC),
  CONSTRAINT `fkex_routine_id`
    FOREIGN KEY (`routine_id`)
    REFERENCES `mydb`.`routine` (`routine_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`max`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`max` (
  `user_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `weight` INT ZEROFILL NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  CONSTRAINT `fkma_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`feed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`feed` (
  `user_id` INT NOT NULL,
  `friend_id` INT NOT NULL,
  `status` INT ZEROFILL NULL,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  PRIMARY KEY (`user_id`, `friend_id`),
  UNIQUE INDEX `friend_id_UNIQUE` (`friend_id` ASC),
  CONSTRAINT `fkfe_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`video` (
  `user_id` INT NOT NULL,
  `video_id` INT NOT NULL,
  `length` INT NULL,
  `title` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL,
  `views` INT ZEROFILL NULL,
  PRIMARY KEY (`user_id`, `video_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `video_id_UNIQUE` (`video_id` ASC),
  CONSTRAINT `fkvi_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`photo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`photo` (
  `user_id` INT NOT NULL,
  `photo_id` INT NOT NULL,
  `title` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL,
  `views` INT ZEROFILL NULL,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  PRIMARY KEY (`user_id`, `photo_id`),
  UNIQUE INDEX `photo_id_UNIQUE` (`photo_id` ASC),
  CONSTRAINT `fkph_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
