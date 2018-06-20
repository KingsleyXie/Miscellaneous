/********* Create Database & Table With Test Data (BEGIN) *********/
-- DROP DATABASE IF EXISTS trans;
CREATE DATABASE trans;
USE trans;
CREATE TABLE demo (
	id INTEGER NOT NULL AUTO_INCREMENT,
	value INTEGER,
	PRIMARY KEY(id)
) ENGINE = InnoDB;
INSERT INTO demo(value) VALUES (1), (2), (3);
/********* Create Database & Table With Test Data (END) *********/



/********* Create Test Users (BEGIN) *********/
CREATE USER 'usr1'@'localhost' IDENTIFIED BY 'pwd'; -- As Session 1
GRANT ALL PRIVILEGES ON trans.* TO 'usr1'@'localhost';

CREATE USER 'usr2'@'localhost' IDENTIFIED BY 'pwd'; -- As Session 2
GRANT ALL PRIVILEGES ON trans.* TO 'usr2'@'localhost';
/********* Create Test Users (END) *********/



/********* Test Preparation (BEGIN) *********/
-- On Session 1 & Session 2:
USE trans;
SET SESSION innodb_lock_wait_timeout = 3;

--   It is strongly recommended to reset(DROP & RE-CREATE)
--   the database before starting a new test session
SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; -- OR:
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED; -- OR:
SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ; -- OR:
SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE;
/********* Test Preparation (END) *********/



/********* Test For Lost Update (BEGIN) *********/
-- On Session 1 & Session 2:
START TRANSACTION;
-- On Session 1:
SELECT value FROM demo WHERE id = 1;
UPDATE demo SET value = value + 1 WHERE id = 1;
-- On Session 2:
UPDATE demo SET value = value + 1 WHERE id = 1;
-- On Session 1 & Session 2:
COMMIT;
SELECT value FROM demo WHERE id = 1;
/********* Test For Lost Update (END) *********/



/********* Test For Dirty Read (BEGIN) *********/
-- On Session 1 & Session 2:
START TRANSACTION;
-- On Session 1:
SELECT value FROM demo WHERE id = 2;
-- On Session 2:
UPDATE demo SET value = 233 WHERE id = 2;
-- On Session 1:
SELECT value FROM demo WHERE id = 2;
-- On Session 2:
ROLLBACK;
-- On Session 1:
SELECT value FROM demo WHERE id = 2;
/********* Test For Dirty Read (END) *********/



/********* Test For NonRepeatable Read (BEGIN) *********/
-- On Session 1 & Session 2:
START TRANSACTION;
-- On Session 1:
SELECT value FROM demo WHERE id = 3;
-- On Session 2:
UPDATE demo SET value = 233 WHERE id = 3;
COMMIT;
-- On Session 1:
SELECT value FROM demo WHERE id = 3;
/********* Test For NonRepeatable Read (END) *********/



/********* Test For Phantom Read (BEGIN) *********/
-- On Session 1 & Session 2:
START TRANSACTION;
-- On Session 1:
SELECT COUNT(*) FROM demo;
-- On Session 2:
INSERT INTO demo(value) VALUES (4), (5), (6);
COMMIT;
-- On Session 1:
SELECT COUNT(*) FROM demo;
/********* Test For Phantom Read (END) *********/
