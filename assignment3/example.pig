--This script generates the title and year for all movies produced before 1920
--The output is then ordered by the ascending order of the year.

--load the data from HDFS and define the schema
movies = LOAD '/data/movies.csv' USING PigStorage(',') AS (movieid:INT, title:CHARARRAY, year:INT);
--Other load data statements for the other two data sets for your reference
-- load the moviegenres data from HDFS and define the schema
--moviegenres = LOAD '/data/moviegenres.csv' USING PigStorage(',') AS (movieid:INT, genre:CHARARRAY);

-- load the ratings data from HDFS and define the schema. Note that TIMESTAMP left unconverted.
--ratings = LOAD '/data/ratings.csv' USING PigStorage(',') AS (userid:INT, movieid:INT, rating:DOUBLE, TIMESTAMP);
 

-- Limit ourselves to moves before 1920
movies1949 = FILTER movies BY year < 1920;

-- Read only the attributes we are interested in.
titles = FOREACH movies1949 GENERATE title, year;

-- Order that by year.
ordertitles = ORDER titles BY year;

-- Send the output to the screen.
dump ordertitles;
