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
  `user_id` INT NOT NULL AUTO_INCREMENT,
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
  CONSTRAINT `fkm_user_id`
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
  `practice_name` VARCHAR(45) NULL,
  `practice_date` VARCHAR(45) NULL,
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
  `event_name` VARCHAR(45) NULL,
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
-- Table `mydb`.`exercise_routine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`exercise_routine` (
  `user_id` INT NOT NULL,
  `routine_id` INT NOT NULL,
  `routine_name` VARCHAR(45) NULL,
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
  `exercise_name` VARCHAR(45) NULL,
  `sets` INT ZEROFILL NULL,
  `reps` INT ZEROFILL NULL,
  `weight` INT ZEROFILL NULL,
  PRIMARY KEY (`routine_id`, `exercise_id`),
  UNIQUE INDEX `routine_id_UNIQUE` (`routine_id` ASC),
  UNIQUE INDEX `exercise_id_UNIQUE` (`exercise_id` ASC),
  CONSTRAINT `fkex_routine_id`
    FOREIGN KEY (`routine_id`)
    REFERENCES `mydb`.`exercise_routine` (`routine_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`max`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`max` (
  `user_id` INT NOT NULL,
  `max_name` VARCHAR(45) NULL,
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
  CONSTRAINT `fkme_user_id`
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
  `video_date` VARCHAR(45) NULL,
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
  `create_date` VARCHAR(45) NULL,
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

USE `mydb` ;

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_profile` (`username` INT, `firstname` INT, `lastname` INT, `email` INT, `date_registered` INT, `organization` INT, `height` INT, `weight` INT, `followers` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_event` (`event_name` INT, `reps` INT, `weight` INT, `best` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_exercise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_exercise` (`exercise_name` INT, `sets` INT, `reps` INT, `weight` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_max`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_max` (`max_name` INT, `weight` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_meet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_meet` (`event_name` INT, `event_location` INT, `event_date` INT, `results` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_photo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_photo` (`title` INT, `create_date` INT, `views` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_practice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_practice` (`practice_name` INT, `practice_date` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_exercise_routine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_exercise_routine` (`routine_name` INT, `focus` INT);

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view_video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view_video` (`length` INT, `title` INT, `video_date` INT, `views` INT);

-- -----------------------------------------------------
-- View `mydb`.`view_profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_profile`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_profile` AS
SELECT u.firstname, u.lastname, u.email, u.date_registered, u.organization, u.height, u.weight, u.followers
FROM user AS u
WHERE u.view_flag <= 1;

-- -----------------------------------------------------
-- View `mydb`.`view_event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_event`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_event` AS
-- SELECT event.event_name, event.reps, event.weight, event.best
SELECT e.event_name, e.reps, e.weight, e.best
FROM event AS e
JOIN practice AS p ON p.practice_id = e.practice_id
JOIN user AS u ON p.user_id = u.user_id
WHERE u.view_flag <=1;

-- -----------------------------------------------------
-- View `mydb`.`view_exercise`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_exercise`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_exercise` AS
SELECT ex.exercise_name, ex.sets, ex.reps, ex.weight
FROM exercise AS ex
JOIN exercise_routine AS er ON ex.routine_id = er.routine_id
JOIN user AS u ON er.user_id = u.user_id
WHERE u.view_flag <= 1;

-- -----------------------------------------------------
-- View `mydb`.`view_max`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_max`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_max` AS
SELECT m.max_name, m.weight
FROM max AS m
JOIN user AS u ON m.user_id=u.user_id
WHERE u.view_flag <=1;

-- -----------------------------------------------------
-- View `mydb`.`view_meet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_meet`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_meet` AS
SELECT m.event_name, m.event_location, m.event_date, m.results
FROM meet AS m
JOIN user AS u ON m.user_id=u.user_id
WHERE u.view_flag <= 1;

-- -----------------------------------------------------
-- View `mydb`.`view_photo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_photo`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_photo` AS
SELECT p.title, p.create_date, p.views
FROM photo AS p
JOIN user AS u ON p.user_id = u.user_id
WHERE u.view_flag <= 1;

-- -----------------------------------------------------
-- View `mydb`.`view_practice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_practice`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_practice` AS
SELECT p.practice_name, p.practice_date
FROM practice AS p
JOIN user AS u ON p.user_id = u.user_id
WHERE u.view_flag <= 1;

-- -----------------------------------------------------
-- View `mydb`.`view_exercise_routine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_exercise_routine`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_exercise_routine` AS
SELECT er.routine_name, er.focus
FROM exercise_routine AS er
JOIN user AS u ON er.user_id = u.user_id
WHERE u.view_flag <=1;

-- -----------------------------------------------------
-- View `mydb`.`view_video`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view_video`;
USE `mydb`;
CREATE  OR REPLACE VIEW `view_video` AS
SELECT length, title, video_date, views
FROM video;
-- WHERE user.view_flag <= 1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
