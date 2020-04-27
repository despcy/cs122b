# cs122b-spring20-team-60
cs122b-spring20-team-60 created by GitHub Classroom

## DemoURL:


## deployment instr:

``sh ./deploy.sh``

## substring Design:

**Index creation:**

**Query:**

```sql
select * from movies where 1=1 AND movies.title LIKE "%t%" AND movies.year = 2003 AND movies.director like "%d%" and id in (select distinct movieId from stars_in_movies inner join stars on stars_in_movies.starId = stars.id where name like '%s%') ORDER BY movies.title asc , (select rating from ratings where ratings.movieId=movies.id) asc LIMIT 10 OFFSET 0;
```

note: ``where 1=1`` is designed for the convience of query formatting, make no sense in query.

## Api Design:

[Check API.md](./api.md)


## contribution
