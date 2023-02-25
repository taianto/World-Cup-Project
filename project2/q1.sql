Select * from
    Stadium join Match on STADIUM.STADIUM_NAME = MATCH.STADIUM_NAME
where MATCH_ID in

(Select match_id from
      Player join Goal on match_id
  Where player.name = 'Christine Sinclair'

  Group by match_id)
;
