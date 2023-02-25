SELECT MATCH.STADIUM_NAME, LOCATION, DATE FROM
    Stadium JOIN Match ON STADIUM.STADIUM_NAME = MATCH.STADIUM_NAME
WHERE MATCH_ID IN

(SELECT match_id FROM
      Player JOIN Goal ON match_id
  WHERE player.name = 'Christine Sinclair'

  GROUP BY match_id)
;
