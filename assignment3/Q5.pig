--load the data from HDFS and define the schema
movies = LOAD '/data/movies.csv' USING PigStorage(',') AS (movieid:INT, title:CHARARRAY, year:INT);

-- load the moviegenres data from HDFS and define the schema
moviegenres = LOAD '/data/moviegenres.csv' USING PigStorage(',') AS (movieid:INT, genre:CHARARRAY);

-- load the ratings data from HDFS and define the schema. Note that TIMESTAMP left unconverted.
--ratings = LOAD '/data/ratings.csv' USING PigStorage(',') AS (userid:INT, movieid:INT, rating:DOUBLE, TIMESTAMP);

movies15 = FILTER movies BY year >= 2015;
fltrd_movies = FILTER movies15 BY year <= 2016;

jnd = JOIN fltrd_movies BY movieid, moviegenres BY movieid;
grpd = GROUP jnd BY (genre, year);
rslt = FOREACH grpd GENERATE FLATTEN(group) AS (genre,year), COUNT($1) AS count_movies;
srtd = ORDER rslt BY genre, year;
DUMP srtd;

