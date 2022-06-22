CREATE TABLE users
(id INT(20) PRIMARY KEY AUTO_INCREMENT,
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 email VARCHAR(30) UNIQUE,
 role INT(2) DEFAULT(1),
 last_login TIMESTAMP,
--  is_active BOOLEAN,
authkey VARCHAR(100) UNIQUE NULL,
 pass VARCHAR(100));
