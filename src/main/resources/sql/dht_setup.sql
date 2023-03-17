CREATE DATABASE IF NOT EXISTS ${database};

USE ${database};

CREATE TABLE IF NOT EXISTS DHT11 (
    time BIGINT(64) NOT NULL,
    humidity DOUBLE NOT NULL,
    temperature DOUBLE NOT NULL,
    PRIMARY KEY (time)
);