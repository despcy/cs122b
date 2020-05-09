
/*
Add movie procedure and add star procedure
Defines the procedure for adding a movie in the database. Takes in movie info,
star info, and genre info values from the employee dashboard 
*/

-- Set delimiter for stored procedure
DELIMITER |

DROP PROCEDURE IF EXISTS add_movie |

-- Procedure definition
CREATE PROCEDURE add_movie(IN mv_title VARCHAR(100),
    IN mv_year INTEGER, IN mv_director VARCHAR(100),
    IN st_name VARCHAR(50), IN gn_name VARCHAR(32),
    OUT status INTEGER, OUT output VARCHAR(200))

BEGIN

-- 0. Declare Variables and check if star/genre provided
DECLARE mv_exists, st_provided, st_exists, gn_provided, gn_exists BOOLEAN DEFAULT 0;
DECLARE mv_id, st_id, temp VARCHAR(50);
DECLARE gn_id INTEGER DEFAULT 0;

SET status = 0;
SET output = "";

IF st_name != "" THEN
    SET st_provided = 1;
END IF;

IF gn_name != "" THEN
    SET gn_provided = 1;
END IF;

-- 1. Check if Movie exists in database
SELECT EXISTS(SELECT 1 FROM movies
    WHERE title = mv_title AND year = mv_year
    AND director = mv_director
    LIMIT 1)
    INTO mv_exists;

IF mv_exists = 1 THEN
    set output = "Movie already exists.";
    set mv_id = (select id from movies where movies.title=mv_title and movies.year=mv_year and movies.director=mv_director);
END IF;

-- 2. movie didn't exist before. add it!
    IF mv_exists = 0 THEN
        SELECT max(id) from movies INTO mv_id;
        if right(mv_id, 1)='9' then
            select concat(mv_id, '0');
        else
            SELECT CONCAT(left(mv_id, length(mv_id)-1), right(mv_id, 1)+1) into mv_id;
        end if;
        INSERT INTO movies(id, title, year, director)
        VALUES(mv_id, mv_title, mv_year, mv_director);

    END IF;

-- 3. Check if Star exists in database if it was provided
IF st_provided = 1 AND status = 0 THEN
    SELECT EXISTS(SELECT 1 FROM stars
    WHERE name = st_name
    LIMIT 1)
    INTO st_exists;

    IF st_exists = 1 THEN
        select concat(output, " Star already exists.") into output;
        set st_id = (select id from stars where stars.name=st_name);
    END IF;
END IF;

-- 4. If star does not exist and was provided, insert new star
IF st_provided = 1 AND st_exists = 0 AND status = 0 THEN
    SELECT max(id) from stars INTO st_id;
    if right(st_id, 1)='9' then
            select concat(st_id, '0');
        else
            SELECT CONCAT(left(st_id, length(st_id)-1), right(st_id, 1)+1) into st_id;
        end if;

    INSERT INTO stars(id, name)
    VALUES (st_id, st_name);
END IF;

-- 5. Check if Genre exists in database if it was provided
IF gn_provided = 1 AND status = 0 THEN
    SELECT EXISTS(SELECT 1 FROM genres
    WHERE name = gn_name
    LIMIT 1)
    INTO gn_exists;

    IF gn_exists = 1 THEN
        select concat(output, " Genre already exists.") into output;
        set gn_id = (select id from genres where genres.name=gn_name);
    END IF;
END IF;

-- 6. If genre does not exist, insert new genre
IF gn_provided = 1 AND gn_exists = 0 AND status = 0 THEN
    INSERT INTO genres(name) VALUES (gn_name);
    SET gn_id = (SELECT MAX(id) FROM genres WHERE name=gn_name);
END IF;

-- 7. Link entries via stars_in_movies if Star was provided
IF st_provided = 1 AND status = 0 THEN
    INSERT INTO stars_in_movies(starId, movieId)
    VALUES (st_id, mv_id);
END IF;

-- 8. Link entries via genres_in_movies if Genre was provided
IF gn_provided = 1 AND status = 0 THEN
    INSERT INTO genres_in_movies(genreId, movieId)
    VALUES (gn_id, mv_id);
END IF;

IF status = 0 THEN
    select concat(output, " Success. Information is added.") into output;
END IF;

END |

-- Reset delimiter to SQL default
DELIMITER ;

DELIMITER |

DROP PROCEDURE IF EXISTS add_star |

-- Procedure definition
CREATE PROCEDURE add_star(IN st_name VARCHAR(100),
    IN birth INTEGER, 
    OUT status INTEGER, OUT st_id VARCHAR(10), OUT output VARCHAR(200))

BEGIN

-- 0. Declare Variables and check if star/genre provided
DECLARE st_id VARCHAR(50);
DECLARE st_exists BOOLEAN DEFAULT 0;

SET status = 0;
SET output = "";

    SELECT EXISTS(SELECT 1 FROM stars
    WHERE name = st_name
    LIMIT 1)
    INTO st_exists;

    IF st_exists = 1 THEN
        set output=" Star already exists.";
        set st_id = (select id from stars where stars.name=st_name);
        update stars set birthYear=birth where id=st_id;
    END IF;


IF st_exists = 0 AND status = 0 THEN
    SELECT max(id) from stars INTO st_id;
    if right(st_id, 1)='9' then
            select concat(st_id, '0');
        else
            SELECT CONCAT(left(st_id, length(st_id)-1), right(st_id, 1)+1) into st_id;
        end if;

    INSERT INTO stars(id, name, birthYear)
    VALUES (st_id, st_name, birth);
END IF;

IF status = 0 THEN
    select concat(output, " Information is added.") into output;
END IF;

END |

-- Reset delimiter to SQL default
DELIMITER ;