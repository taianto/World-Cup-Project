SELECT country AS country1, Table2.country2 AS country2
FROM Match_Participants AS MP,
     (SELECT country AS country2, MATCH_ID
      FROM MATCH_PARTICIPANTS AS MP2) Table2
WHERE MP.MATCH_ID = TABLE2.MATCH_ID AND country != Table2.country2
;

SELECT (DISTINCT MATCH_ID), COUNTRY
FROM MATCH_PARTICIPANTS
;