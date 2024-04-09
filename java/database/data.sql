BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO movies (api_movie_id,title,overview) VALUES ('99999','Gill Bill Vol. 2', 'Gill is back and you better watch out');

INSERT INTO movies (api_movie_id,title, poster_path, backdrop_path, imbd_id, homepage, overview, release_date, runtime)
    VALUES ('99999','Gill Bill Vol. 2', 'Gill is back and you better watch out');


COMMIT TRANSACTION;
