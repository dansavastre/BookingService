DROP TABLE IF EXISTS user;

CREATE TABLE user (
                      ID varchar NOT NULL,
                      PASSWORD varchar NOT NULL,
                      FIRST_NAME varchar,
                      LAST_NAME varchar,
                      USER_TYPE varchar,
                      PRIMARY KEY (ID)
);