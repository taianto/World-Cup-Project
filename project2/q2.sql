-- Write a SQL query that lists the name, shirt number and country
-- of all players that have played in all matches of their teams.

SELECT Table2.name, Table2.shirt_num, Table2.country
FROM
    (SELECT country, count(country) AS countrymax
     FROM Match_participants
     WHERE match_id IN (select match_id from Played_match)
     GROUP BY country) Table1
        JOIN
    (SELECT PlaysIn.country, PlaysIn.shirt_num, count(match_id) AS playermax, Player.name
     FROM PlaysIn
         JOIN Player
             ON Player.shirt_num = PlaysIn.shirt_num AND Player.country = PlaysIn.country
     GROUP BY PlaysIn.country, PlaysIn.shirt_num, Player.name) Table2
    ON Table1.country = Table2.country AND countrymax = playermax
;
