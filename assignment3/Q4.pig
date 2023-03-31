--load the data from HDFS and define the schema
movies = LOAD '/data/movies.csv' USING PigStorage(',') AS (movieid:INT, title:CHARARRAY, year:INT);

-- load the moviegenres data from HDFS and define the schema
--moviegenres = LOAD '/data/moviegenres.csv' USING PigStorage(',') AS (movieid:INT, genre:CHARARRAY);

-- load the ratings data from HDFS and define the schema. Note that TIMESTAMP left unconverted.
ratings = LOAD '/data/ratings.csv' USING PigStorage(',') AS (userid:INT, movieid:INT, rating:DOUBLE, TIMESTAMP);
 
jnd = JOIN movies BY movieid, ratings BY movieid;
grpd = GROUP jnd BY movies::movieid;
rslt = FOREACH grpd GENERATE FLATTEN(group) AS movieid, COUNT($1) AS count_ratings;
srtd = ORDER rslt BY count_ratings DESC;
top10 = LIMIT srtd 10;

jn_top10_movie = JOIN movies BY movieid, top10 BY movieid;
final_rslt = FOREACH jn_top10_movie GENERATE movies::title as title, top10::count_ratings AS count_ratings;
final_srtd = ORDER final_rslt BY count_ratings DESC;
DUMP final_srtd;