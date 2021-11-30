DROP TABLE IF EXISTS booking;

CREATE TABLE booking (
                      ID int IDENTITY(1,1) PRIMARY KEY,
                      BOOKING_OWNER varchar,
                      ROOM varchar,
                      DATE date,
                      START_TIME time,
                      END_TIME time,
                      PURPOSE varchar
);