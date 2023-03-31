SELECT Table2.country, countrymax AS played, COALESCE(numgoals,0) FROM
    (SELECT country, COUNT(*) AS numgoals
     FROM Goal
     WHERE is_penalty != 1
     GROUP BY country) Table1
        FULL OUTER JOIN
    (SELECT country, COUNT(*) AS countrymax
     FROM Match_Participants
     WHERE match_id IN
           (SELECT match_id from Played_Match)
     GROUP BY country) Table2
    ON Table1.country = Table2.country
;
