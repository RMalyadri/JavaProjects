CREATE TABLE if not exists EMP (EID INT, FIRST_NAME VARCHAR(255),LAST_NAME VARCHAR(100),
GENDER CHAR);
CREATE SEQUENCE HIBERNATE_SEQUENCE;

create table if not exists USER_LOGIN (user_id int primary key,username varchar(255),
	user_country varchar(244),role varchar(244),password varchar(233)
);
