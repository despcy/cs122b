# cs122b-spring20-team-60
cs122b-spring20-team-60 created by GitHub Classroom

## DemoURL:

Website: http://18.188.106.209:8080/
Video: https://youtu.be/lAAsxAHhy08

## deployment instr:

``sh ./deploy.sh``

## substring Design:

**Index creation:**

```sql
use moviedb;
CREATE INDEX movietitle ON movies (title);
CREATE INDEX moviedirector ON movies (director);
CREATE INDEX starname ON stars (name);
```

**Query:**

```sql
select * from movies where 1=1 AND movies.title LIKE "%t%" AND movies.year = 2003 AND movies.director like "%d%" and id in (select distinct movieId from stars_in_movies inner join stars on stars_in_movies.starId = stars.id where name like '%s%') ORDER BY movies.title asc , (select rating from ratings where ratings.movieId=movies.id) asc LIMIT 10 OFFSET 0;
```

note: ``where 1=1`` is designed for the convience of query formatting, make no sense in query.

## Api Design:
This is our api design document. Our front end and back end communicate base on this.

[Check API.md](./api.md)


## contribution
In this project 2,
Jingwen Mo:
1. Refactoring the back end stucture from original Spring MVC(project 1) to Spring boot & RESTful
2. Design api and implement the back end using Spring boot, Spring MVC and REST and also develop all the database operations
3. Debug and test

Chenxi Yang:
1. Design api and implement the front end using Vue.js framework.
2. Participate in developing the "search" database operations and improve the performance of backend database operations
3. Debug and test

