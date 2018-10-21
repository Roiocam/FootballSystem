ALTER TABLE `FootballSystem`.`pypzx_team` 
ADD COLUMN `team_desc` TINYTEXT NULL AFTER `vaild_code`;

ALTER TABLE `FootballSystem`.`pypzx_team` 
CHANGE COLUMN `team_desc` `team_desc` VARCHAR(255) NULL DEFAULT NULL ;

CREATE TABLE `FootballSystem`.`pypzx_group` (
  `cup_id` VARCHAR(32) NOT NULL,
  `team_id` VARCHAR(32) NOT NULL,
  `group` VARCHAR(1) NULL,
  INDEX `fk_group_cup_idx` (`cup_id` ASC),
  INDEX `fk_group_team_idx` (`team_id` ASC),
  CONSTRAINT `fk_group_cup`
    FOREIGN KEY (`cup_id`)
    REFERENCES `FootballSystem`.`pypzx_cup` (`cup_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_team`
    FOREIGN KEY (`team_id`)
    REFERENCES `FootballSystem`.`pypzx_team` (`team_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

