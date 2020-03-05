CREATE SCHEMA `person_db_page` DEFAULT CHARACTER SET utf8 ;

CREATE database person_db_page2;
CREATE TABLE `person_db_page2`.`persons` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `age` INT NULL,
  `credo` VARCHAR(145) NULL,
  `password` VARCHAR(45) NOT NULL,
  `avatar_id` INT NULL,
  `role_id` INT NULL,
  `login` VARCHAR(45) NULL,
  `create_at` DATETIME NOT NULL,
  `remove_at` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `person_db_page2`.`images` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pers_id` INT NOT NULL,
  `image_name` VARCHAR(145) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_image_pers_id_idx` (`pers_id` ASC),
  CONSTRAINT `fk_image_pers_id`
    FOREIGN KEY (`pers_id`)
    REFERENCES `person_db_page2`.`persons` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `person_db_page2`.`my_story` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  `pers_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_story_pers_id_idx` (`pers_id` ASC),
  CONSTRAINT `fk_story_pers_id`
    FOREIGN KEY (`pers_id`)
    REFERENCES `person_db_page2`.`persons` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `person_db_page2`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `person_db_page2`.`persons`
ADD INDEX `fk_person_id_img_idx` (`avatar_id` ASC);
ALTER TABLE `person_db_page2`.`persons`
ADD CONSTRAINT `fk_person_id_img`
  FOREIGN KEY (`avatar_id`)
  REFERENCES `person_db_page2`.`images` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `person_db_page2`.`persons`
ADD INDEX `fk_person_id_role_idx` (`role_id` ASC);
ALTER TABLE `person_db_page2`.`persons`
ADD CONSTRAINT `fk_person_id_role`
  FOREIGN KEY (`role_id`)
  REFERENCES `person_db_page2`.`roles` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE TABLE `person_db_page2`.`friends_relations` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `person_id` INT NOT NULL,
 `message` VARCHAR(200) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_friend_id_pers_idx` (`person_id` ASC),
  CONSTRAINT `fk_friend_id_pers`
    FOREIGN KEY (`person_id`)
    REFERENCES `person_db_page2`.`persons` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
ALTER TABLE `person_db_page2`.`friends_relations`
DROP FOREIGN KEY `fk_friend_id_pers`;
ALTER TABLE `person_db_page2`.`my_friends`
ADD COLUMN `friend_id` INT NULL AFTER `message`,
ADD INDEX `fk_friend_id_pers_idx1` (`friend_id` ASC);
ALTER TABLE `person_db_page2`.`my_friends`
ADD CONSTRAINT `fk_pers_id_pers`
  FOREIGN KEY (`person_id`)
  REFERENCES `person_db_page2`.`persons` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `person_db_page2`.`friends_relations`
ADD CONSTRAINT `fk_friend_id_pers_id_reflex`
  FOREIGN KEY (`friend_id`)
  REFERENCES `person_db_page2`.`my_friends` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `person_db_page2`.`persons`
ADD COLUMN `email` VARCHAR(45) NULL AFTER `remove_at`,
ADD COLUMN `phone` VARCHAR(45) NULL AFTER `email`,
ADD COLUMN `address` VARCHAR(45) NULL AFTER `phone`;
update persons set email='fox@gmail.com' where id=1;
update persons set phone='+1 234 567-89-00' where id=1;
update persons set address='2641 Tunlaw Road, N.W. Washington, D.C. 20007' where id=1;

ALTER TABLE `person_db_page2`.`friends_relations`
ADD CONSTRAINT `fk_friend_id_fpers_id`
  FOREIGN KEY (`friend_id`)
  REFERENCES `person_db_page2`.`friends_relations` (`person_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


use person_db_page2;
DELIMITER $$
 create procedure `proc_total_reg_users` (out count int)
 begin
 select count(id) into count from persons;
 end;

DELIMITER $$
 create procedure `proc_reg_user_begin_year` (out count int)
 begin
 declare dateBegin datetime;
 select count(id) into count  from persons where create_at between MAKEDATE(year(now()), 1) and now();
 end;

DELIMITER $$
 create procedure `proc_del_user_begin_year` (out count int)
 begin
 declare dateBegin datetime;
 select count(id) into count  from persons where remove_at between MAKEDATE(year(now()), 1) and now();
 end;

DELIMITER $$
 create procedure `proc_total_del_user` (out count int) begin
 select count(id) into count  from persons where remove_at is not null;
 end;


insert into roles (name_role) value ('admin'), ('user'), ('quest');

 insert into persons(username, age, credo, password, login,  role_id, create_at) values
 ('Fox Moulder', 53, 'the truth is out there', 'fox123', 'fox', 2, '2019-02-01');

insert into persons(username, age, credo, password, login , role_id, create_at) values
('Dana Scally', 51, 'Allways ready', 'dana321', 'dana', 2, '2019-01-01');

insert into persons (username, password, role_id, login, create_at) values(
'Administrator ', 'admin', 1, 'admin', '2018-09-01');

insert into persons  (username, password, role_id, login, create_at) values(
'Sid ', 'sid', 2, 'sid', '2019-03-22');

insert into persons  (username, password, role_id, login, create_at) values(
'Alexandr Pushkin', 'Alex', 2, 'alex', '2019-02-01');

insert into my_friends (person_id, message, friend_id) values
(1, 'I love you', 2),(2, 'Do not say that!', 1),(5, 'I am Pushkin, come on!', 2),(4, 'Dana, I am Man!', 2);
insert into my_friends (person_id, message, friend_id) values
(1, 'Эээ усбагойтесь she is my women', 4), (1, 'Эээ усбагойтесь she is my women', 5);
insert into my_friends (person_id, message, friend_id) values
 (1, 'Не бойся я с тобой!', 2);

