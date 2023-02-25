-- Include your drop table DDL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO cs421;

-- Remember to put the drop table ddls for the tables with foreign key references
--    ONLY AFTER the parent tables has already been dropped (reverse of the creation order).

-- The following order is in reverse of the creation order (as of Feb 24th 19:40)
DROP TABLE Booking;
DROP TABLE Refereeship;
DROP TABLE Goal;
DROP TABLE PlaysIn;
DROP TABLE Referee;
DROP TABLE Coach;
DROP TABLE Player;
DROP TABLE Played_Match;
DROP TABLE Match_Participants;
DROP TABLE Team;
DROP TABLE Transaction;
DROP TABLE Receipt;
DROP TABLE Client;
DROP TABLE Ticket;
DROP TABLE Seat;
DROP TABLE Match;
DROP TABLE Stadium;
