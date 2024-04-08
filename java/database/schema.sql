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
	email varchar(50),
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE movies (
	movie_id SERIAL primary key,
	api_movie_id int NOT NULL UNIQUE,
	title varchar(100),
	poster_path varchar(200),
	backdrop_path varchar(200),
	imbd_id int,
	homepage varchar(200),
	overview varchar(1000),
	release_date date,
	runtime int
);

CREATE TABLE genres (
	genre_id int UNIQUE primary key,
	gnere_name varchar(50)
);

CREATE TABLE reviews (
	review_id SERIAL primary key,
	movie_id int references movies(movie_id),
	user_id int references users(user_id),
	star_rating int,
	review varchar(1000)
);

CREATE TABLE lists (
	list_id SERIAL primary key,
	list_name varchar(50),
	description varchar(200)
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

COMMIT TRANSACTION;