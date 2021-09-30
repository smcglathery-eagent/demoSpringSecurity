DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id number,
    username varchar(100) not null,
    password varchar(256) not null
);