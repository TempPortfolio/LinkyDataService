CREATE DATABASE IF NOT EXISTS ${database};

USE ${database};

CREATE TABLE IF NOT EXISTS Linky
(
	time BIGINT PRIMARY KEY NOT NULL,
	id VARCHAR(16) NOT NULL,
	subscribe_instensity INTEGER NOT NULL,
	base_hour_index INTEGER NOT NULL,
	full_hour_index INTEGER NOT NULL,
	offpeak_hour_index INTEGER NOT NULL,
	max_instensity INTEGER NOT NULL,
	instant_intensity INTEGER NOT NULL,
	current_tariff_option INTEGER NOT NULL
);