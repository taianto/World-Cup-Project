--Create a view playerinfo that shows for each player
-- the name,
-- shirt number,
-- DOB,
-- their country,
-- the national association their team belongs to,
-- and the group name of the group the team is part of during group round
CREATE VIEW playerinfo(PLAYER_NAME, PLAYER_SHIRT_NUM, PLAYER_DOB, PLAYER_COUNTRY,
    TEAM_NAME, TEAM_GROUP) AS
(SELECT PLAYER.NAME, PLAYER.SHIRT_NUM, PLAYER.DOB,PLAYER.COUNTRY, TEAM.NAME, TEAM.GROUP
FROM (TEAM JOIN PLAYER ON TEAM.COUNTRY = PLAYER.COUNTRY)) ;

DROP VIEW playerinfo;

SELECT * FROM PLAYERINFO LIMIT 5;

SELECT * FROM PLAYERINFO WHERE TEAM_GROUP = 'A' LIMIT 5;

INSERT INTO PLAYERINFO
(PLAYER_NAME, PLAYER_SHIRT_NUM, PLAYER_DOB, PLAYER_COUNTRY, TEAM_NAME, TEAM_GROUP) VALUES
('ANTON ST',1,'01/01/2000','Canada','A SQUAD','A');