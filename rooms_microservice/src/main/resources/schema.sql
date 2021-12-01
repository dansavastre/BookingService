DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS building;

CREATE TABLE room (
                      ID int NOT NULL,
                      NAME varchar,
                      CAPACITY int,
                      EQUIPMENT varchar,
                      AVAILABLE varchar,
                      BUILDING_NUMBER int NOT NULL,
                      PRIMARY KEY (ID, BUILDING_NUMBER)
);

CREATE TABLE building (
                      ID int NOT NULL,
                      OPENING_TIME TIME,
                      CLOSING_TIME TIME,
                      NAME varchar,
                      PRIMARY KEY (ID)
);