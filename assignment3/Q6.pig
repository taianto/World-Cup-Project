--load the data from HDFS and define the schema
movies = LOAD '/data/movies.csv' USING PigStorage(',') AS (movieid:INT, title:CHARARRAY, year:INT);

-- load the moviegenres data from HDFS and define the schema
--moviegenres = LOAD '/data/moviegenres.csv' USING PigStorage(',') AS (movieid:INT, genre:CHARARRAY);

-- load the ratings data from HDFS and define the schema. Note that TIMESTAMP left unconverted.
--ratings = LOAD '/data/ratings.csv' USING PigStorage(',') AS (userid:INT, movieid:INT, rating:DOUBLE, TIMESTAMP);

grpd = GROUP movies BY year;
curr_rslt = FOREACH grpd GENERATE FLATTEN(group) AS year, COUNT($1) AS count_movies;
prev_rslt = FOREACH curr_rslt GENERATE (year+1) AS year, count_movies AS count_prev_yr_movies;

jnd = JOIN curr_rslt BY year, prev_rslt BY year;

fltrd = FILTER jnd BY count_prev_yr_movies > count_movies;
fnl_rslt = FOREACH fltrd GENERATE curr_rslt::year AS year, count_movies, count_prev_yr_movies;
DUMP fnl_rslt;

