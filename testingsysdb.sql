-- MySQL
USE testingsysdb;
ALTER USER site IDENTIFIED WITH mysql_native_password BY 'site';
flush privileges;

CREATE DATABASE testingsysdb;

CREATE TABLE role (
 id INT NOT NULL AUTO_INCREMENT,
 name VARCHAR(20) NOT NULL UNIQUE,
 PRIMARY KEY (id)
);
SELECT * FROM role;

CREATE TABLE user (
	id INT NOT NULL AUTO_INCREMENT,
    name varchar(32) NOT NULL unique,
    password varchar(64) not null,
    role_id int,
    PRIMARY KEY (ID),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE subject (
	id INT NOT NULL AUTO_INCREMENT,
    name varchar(32) not null unique,
    PRIMARY KEY (id)
);
SELECT * FROM subject;

CREATE TABLE test (
	id INT NOT NULL AUTO_INCREMENT,
    name varchar(255) not null unique,
    subject_id INT REFERENCES subject(id) ON DELETE CASCADE,
    deadline datetime NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE test ADD level INT NOT NULL;
ALTER TABLE test ADD popularity INT NOT NULL;
ALTER TABLE test ADD duration INT NOT NULL;
ALTER TABLE `testingsysdb`.`test` 
CHANGE COLUMN `popularity` `popularity` INT NOT NULL DEFAULT 0 ;

SELECT * FROM test;

CREATE TABLE question (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    score INT NOT NULL,
    test_id INT,
    is_single boolean,
    PRIMARY KEY (ID),
    FOREIGN KEY (test_id) REFERENCES test(id)
);
ALTER TABLE `testingsysdb`.`question` 
DROP FOREIGN KEY `question_ibfk_1`;
ALTER TABLE `testingsysdb`.`question` 
ADD CONSTRAINT `question_ibfk_1`
  FOREIGN KEY (`test_id`)
  REFERENCES `testingsysdb`.`test` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

SELECT * FROM question;

CREATE TABLE answer (
	id INT NOT NULL AUTO_INCREMENT,
    name varchar(255) not null unique,
    question_id INT REFERENCES question(id) ON DELETE CASCADE,
    is_correct boolean,
    PRIMARY KEY (id)
);

ALTER TABLE `testingsysdb`.`answer` 
DROP INDEX `name` ;
SELECT * FROM answer;

CREATE TABLE user_subject (
	user_id INT REFERENCES user(id) ON DELETE CASCADE,
    subject_id INT REFERENCES subject(id) ON DELETE CASCADE,
	UNIQUE (user_id, subject_id),
	PRIMARY KEY (user_id, subject_id)
);
SELECT * FROM user_subject;


CREATE TABLE user_test (
	user_id INT REFERENCES user(id) ON DELETE CASCADE,
    test_id INT REFERENCES test(id) ON DELETE CASCADE,
    score int,
    passed_date datetime DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (user_id, test_id),
	PRIMARY KEY (user_id, test_id)
 );
 
ALTER TABLE `testingsysdb`.`user_test` 
CHANGE COLUMN `passed_date` `passed_date` DATETIME NULL ;

 
 SELECT * FROM user_test;

--ALTER TABLE test ADD subject_id INT REFERENCES subject(id) ON DELETE CASCADE;
--ALTER TABLE test ADD deadline datetime NOT NULL;
CREATE USER 'site' IDENTIFIED BY 'site';
GRANT ALL PRIVILEGES ON * . * TO 'site';
