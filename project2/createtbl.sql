-- Include your create table DDL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO cs421;

-- Remember to put the create table ddls for the tables with foreign key references
--    ONLY AFTER the parent tables has already been created.

-- This is only an example of how you add create table ddls to this file.
--   You may remove it.
CREATE TABLE Stadium
(stadium_name VARCHAR(30) NOT NULL,
 location VARCHAR(30) NOT NULL,
 capacity INTEGER,
 PRIMARY KEY (stadium_name));

CREATE TABLE Match
(match_id INTEGER NOT NULL,
 date DATE NOT NULL,
 time VARCHAR(10) NOT NULL,
 round VARCHAR(15) NOT NULL,
 stadium_name VARCHAR(30) NOT NULL,
 PRIMARY KEY (match_id),
 FOREIGN KEY (stadium_name)
     REFERENCES Stadium);

CREATE TABLE Seat
(seat_id VARCHAR(5) NOT NULL,
 stadium_name VARCHAR(30) NOT NULL,
 PRIMARY KEY (seat_id, stadium_name),
 FOREIGN KEY  (stadium_name)
     REFERENCES Stadium);

CREATE TABLE Ticket
(seat_id VARCHAR(5) NOT NULL,
 stadium_name VARCHAR(30) NOT NULL,
 match_id INTEGER NOT NULL,
 price FLOAT,
 status VARCHAR(10) NOT NULL,
 PRIMARY KEY (seat_id, stadium_name, match_id),
 FOREIGN KEY  (seat_id, stadium_name)
     REFERENCES Seat,
 FOREIGN KEY (match_id)
     REFERENCES Match);

CREATE TABLE Client
(email VARCHAR(30) NOT NULL,
 first_name VARCHAR(30) NOT NULL,
 last_name VARCHAR(15) NOT NULL,
 PRIMARY KEY (email));

CREATE TABLE Receipt
(transaction_id INTEGER NOT NULL,
 total_cost FLOAT NOT NULL,
 email VARCHAR(30) NOT NULL,
 PRIMARY KEY (transaction_id),
 FOREIGN KEY (email)
     REFERENCES Client);

CREATE TABLE Transaction
(seat_id VARCHAR(5) NOT NULL,
 stadium_name VARCHAR(30) NOT NULL,
 match_id INTEGER NOT NULL,
 transaction_id INTEGER NOT NULL,
 PRIMARY KEY (seat_id, stadium_name,match_id),
 FOREIGN KEY (seat_id, stadium_name,match_id)
     REFERENCES Ticket,
 FOREIGN KEY (transaction_id)
     REFERENCES Receipt);

CREATE TABLE Team
(country VARCHAR(15) NOT NULL,
 name VARCHAR(70) NOT NULL,
 website_url VARCHAR(30) NOT NULL,
 group VARCHAR(16) NOT NULL,
 PRIMARY KEY (country));

CREATE TABLE Match_Participants
(match_id INTEGER NOT NULL,
 country VARCHAR(15) NOT NULL,
 PRIMARY KEY (match_id, country),
 FOREIGN KEY (match_id)
     REFERENCES Match,
 FOREIGN KEY (country)
     REFERENCES Team);

CREATE TABLE Played_Match
(match_id INTEGER NOT NULL,
 duration TIME NOT NULL,
 PRIMARY KEY (match_id),
 FOREIGN KEY (match_id)
     REFERENCES Match);

CREATE TABLE Player
(country VARCHAR(15) NOT NULL,
 shirt_num INTEGER NOT NULL,
 name VARCHAR(30) NOT NULL,
 dob DATE NOT NULL,
 general_position VARCHAR(15) NOT NULL,
 PRIMARY KEY (country, shirt_num),
 FOREIGN KEY (country)
     REFERENCES Team);

CREATE TABLE Coach
(country VARCHAR(15) NOT NULL,
 role VARCHAR(15) NOT NULL,
 name VARCHAR(30) NOT NULL,
 dob DATE NOT NULL,
 PRIMARY KEY (country, role),
 FOREIGN KEY (country)
     REFERENCES Team);

CREATE TABLE Referee
(referee_id INTEGER NOT NULL,
 name VARCHAR(30) NOT NULL,
 experience INTEGER,
 nationality VARCHAR(15),
 PRIMARY KEY (referee_id));

CREATE TABLE PlaysIn
(country VARCHAR(15) NOT NULL,
 shirt_num INTEGER NOT NULL,
 match_id INTEGER NOT NULL,
 specific_pos VARCHAR(15) NOT NULL,
 entry_time TIME NOT NULL,
 exit_time TIME,
 PRIMARY KEY (country, shirt_num, match_id),
 FOREIGN KEY (country, shirt_num)
     REFERENCES Player,
 FOREIGN KEY (match_id)
     REFERENCES Played_Match);

CREATE TABLE Goal
(occurrence INTEGER NOT NULL,
 match_id INTEGER NOT NULL,
 country VARCHAR(15) NOT NULL,
 shirt_num INTEGER NOT NULL,
 minute INTEGER NOT NULL,
 is_penalty INTEGER NOT NULL,
 PRIMARY KEY (match_id, occurrence),
 FOREIGN KEY (match_id)
     REFERENCES Played_Match,
 FOREIGN KEY (country, shirt_num)
     REFERENCES Player);

CREATE TABLE Refereeship
(referee_id INTEGER NOT NULL,
 match_id INTEGER NOT NULL,
 role VARCHAR(30) NOT NULL,
 PRIMARY KEY (referee_id, match_id),
 FOREIGN KEY (referee_id)
     REFERENCES Referee,
 FOREIGN KEY (match_id)
     REFERENCES Played_Match);

CREATE TABLE Booking
(match_id INTEGER NOT NULL,
 occurrence INTEGER NOT NULL,
 country VARCHAR(15) NOT NULL,
 shirt_num INTEGER NOT NULL,
 referee_id INTEGER NOT NULL,
 is_red INTEGER NOT NULL,
 yellow_occurrance INTEGER,
 PRIMARY KEY (match_id, occurrence),
 FOREIGN KEY (country, shirt_num)
     REFERENCES Player,
 FOREIGN KEY (referee_id)
     REFERENCES Referee,
 FOREIGN KEY (match_id)
     REFERENCES Played_Match);

