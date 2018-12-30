ALTER TABLE `pypzx_team` 
ADD COLUMN `team_desc` TINYTEXT NULL AFTER `vaild_code`;

ALTER TABLE `pypzx_team` 
CHANGE COLUMN `team_desc` `team_desc` VARCHAR(255) NULL DEFAULT NULL ;

CREATE TABLE `pypzx_group` (
  `cup_id` VARCHAR(32) NOT NULL,
  `team_id` VARCHAR(32) NOT NULL,
  `group` VARCHAR(1) NULL,
  INDEX `fk_group_cup_idx` (`cup_id` ASC),
  INDEX `fk_group_team_idx` (`team_id` ASC),
  CONSTRAINT `fk_group_cup`
    FOREIGN KEY (`cup_id`)
    REFERENCES `pypzx_cup` (`cup_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_team`
    FOREIGN KEY (`team_id`)
    REFERENCES `pypzx_team` (`team_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	
CREATE TABLE `pypzx_wechat_account` (
  `openid` varchar(55) NOT NULL,
  `nick_name` varchar(45) DEFAULT NULL,
  `img` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`openid`),
  UNIQUE KEY `openid_UNIQUE` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `pypzx_cup` 
CHANGE COLUMN `cup_name` `cup_name` VARCHAR(64) NOT NULL ,
ADD COLUMN `is_group` TINYINT(4) NOT NULL DEFAULT '0' AFTER `cup_name`;


ALTER TABLE `pypzx_wechat_account` 
ADD COLUMN `team_id` VARCHAR(32) NULL AFTER `img`,
ADD COLUMN `player_id` VARCHAR(32) NULL AFTER `team_id`;

ALTER TABLE `pypzx_group` 
CHANGE COLUMN `group` `team_group` VARCHAR(1) NULL DEFAULT NULL ;

ALTER TABLE `pypzx_admin`  RENAME TO  .`pypzx_user` ;

