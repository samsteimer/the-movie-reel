BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, movies, genres, reviews, lists, movies_genres, movies_lists, users_movies, users_genres;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_name varchar(50),
	last_name varchar(50),
	bio varchar(500),
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE movies (
	movie_id SERIAL primary key,
	api_movie_id int,
	title varchar(100) NOT NULL,
	poster_path varchar(200),
	overview varchar(1000),
	release_date varchar(10),
	is_deleted boolean default false not null
);

CREATE UNIQUE INDEX idx_movies_is_deleted
ON movies (title)
WHERE is_deleted = false;

CREATE TABLE genres (
	genre_id int primary key,
	genre_name varchar(50) UNIQUE
);

CREATE TABLE reviews (
	review_id SERIAL primary key,
	movie_id int references movies(movie_id),
	user_id int references users(user_id),
	star_rating int NOT NULL,
	review varchar(1000) NOT NULL DEFAULT '',
	CONSTRAINT UQ_movie_user_rating UNIQUE (movie_id, user_id),
	CONSTRAINT CHECK_star_rating CHECK(star_rating BETWEEN 1 AND 5)
);

CREATE TABLE lists (
	list_id SERIAL primary key,
	list_name varchar(50) UNIQUE NOT NULL,
	description varchar(200) NOT NULL DEFAULT ''
);

CREATE TABLE movies_genres (
	movie_id int references movies(movie_id),
	genre_id int references genres(genre_id),
	CONSTRAINT PK_movies_genres PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE movies_lists (
	movie_id int references movies(movie_id),
	list_id int references lists(list_id),
	CONSTRAINT PK_movies_lists PRIMARY KEY (movie_id, list_id)
);

CREATE TABLE users_movies (
	user_id int references users(user_id),
	movie_id int references movies(movie_id),
	CONSTRAINT PK_users_movies PRIMARY KEY (user_id, movie_id)
);

CREATE TABLE users_genres (
	user_id int references users(user_id),
	genre_id int references genres(genre_id),
	CONSTRAINT PK_users_genres PRIMARY KEY (user_id, genre_id)
);


INSERT INTO genres (genre_id, genre_name) VALUES (1, 'Thriller');
INSERT INTO genres (genre_id, genre_name) VALUES (2, 'Sci-Fi');
INSERT INTO genres (genre_id, genre_name) VALUES (3, 'Drama');
INSERT INTO genres (genre_id, genre_name) VALUES (4, 'Comedy');
INSERT INTO genres (genre_id, genre_name) VALUES (5, 'Action');
INSERT INTO genres (genre_id, genre_name) VALUES (6, 'Documentary');
INSERT INTO genres (genre_id, genre_name) VALUES (7, 'Romance');
INSERT INTO genres (genre_id, genre_name) VALUES (8, 'Animated');
INSERT INTO genres (genre_id, genre_name) VALUES (9, 'Family');


COMMIT TRANSACTION;