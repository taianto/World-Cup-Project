SELECT Match.stadium_name, location, date
FROM Stadium
    JOIN Match
        ON Stadium.stadium_name = Match.stadium_name AND match_id IN
(SELECT match_id
 FROM Player JOIN Goal
     ON match_id AND Player.name = 'Christine Sinclair' AND Player.country = Goal.country
  GROUP BY match_id)
;