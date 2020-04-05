create SCHEMA moviedb;
USE moviedb;

create table if not exists stars(
           id varchar(10) primary key not null ,
           name varchar(100) not null,
           birthYear integer
       );
create table if not exists movies(
			id varchar(10) primary key not null,
			title varchar(100) not null,
			year integer not null,
			director varchar(100)
);
create table if not exists stars_in_movies(
			starId varchar(10) not null,
			movieId varchar(10) not null,
            FOREIGN KEY (starId) REFERENCES stars(id)
			ON DELETE CASCADE,	
			FOREIGN KEY (movieId) REFERENCES movies(id)
			ON DELETE CASCADE
);
create table if not exists genres(
			id integer primary key AUTO_INCREMENT not null, 
			name varchar(32) not null
);
create table if not exists genres_in_movies(
			genreId integer not null, 
			movieId varchar(10) not null,
            FOREIGN KEY (genreId) REFERENCES genres(id)
			ON DELETE CASCADE,
			FOREIGN KEY (movieId) REFERENCES movies(id)
			ON DELETE CASCADE
);
create table if not exists creditcards(
			id varchar(20) primary key not null,
			firstName varchar(50) not null,
			lastName varchar(50) not null,
			expiration date not null
);
create table if not exists customers(
			id integer primary key AUTO_INCREMENT not null,
			firstName varchar(50) not null,
			lastName varchar(50) not null,
			ccId varchar(20) not null,
			address varchar(200) not null,
			email varchar(50) not null,
			password varchar(20) not null,
            FOREIGN KEY (ccId) REFERENCES creditcards(id) on DELETE CASCADE
);
create table if not exists sales(
			id integer primary key AUTO_INCREMENT not null,
			customerId integer not null, 
			movieId varchar(10) not null, 
			saleDate date not null,
            FOREIGN KEY (customerId) REFERENCES customers(id) on DELETE CASCADE,
			FOREIGN KEY (movieId) REFERENCES movies(id)
);
create table if not exists ratings(
			movieId varchar(10) not null, 
			rating float not null,
			numVotes integer not null,
            FOREIGN KEY (movieId) REFERENCES movies(id)
);


