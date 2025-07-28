CREATE SCHEMA IF NOT EXISTS football;
USE football;
CREATE TABLE IF NOT EXISTS teams (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       balance DOUBLE NOT NULL,
                       bio VARCHAR(255),
                       commission_rate DOUBLE NOT NULL,
                       establishment_date DATE NOT NULL,
                       name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS players (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         bio VARCHAR(255),
                         date_of_birth DATE NOT NULL,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         playing_since DATE NOT NULL,
                         team_id BIGINT,
                         FOREIGN KEY (team_id) REFERENCES teams(id)
);

USE football;

INSERT INTO teams (id, balance, bio, commission_rate, establishment_date, name) VALUES (1, 100000000, 'Top football team from the city.', 10, '2010-05-13', 'Green Lions');
INSERT INTO teams (id, balance, bio, commission_rate, establishment_date, name) VALUES (2, 1, 'Bottom football team from the streets', 1, '2020-05-07', 'Purple Turtles');

INSERT INTO players (id, bio, date_of_birth, name, playing_since, team_id) VALUES (1, 'Best player ever', '1987-06-24', 'Kirito Football', '2015-10-14', 2);
INSERT INTO players (id, bio, date_of_birth, name, playing_since, team_id) VALUES (2, 'Not so good of a player', '1992-06-12', 'Pioneer Nine', '2009-10-09', 1);
INSERT INTO players (id, bio, date_of_birth, name, playing_since, team_id) VALUES (3, 'What is foot?', '1979-09-12', 'Jake Sunderlake', '2013-10-18', 1);
INSERT INTO players (id, bio, date_of_birth, name, playing_since, team_id) VALUES (4, 'I always win', '2001-06-02', 'Ada Wong', '2017-10-11', 2);



