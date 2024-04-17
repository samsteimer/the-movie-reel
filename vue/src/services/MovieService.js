import axios from "axios";

export default {
  getMovieByMovieId(id) {
    return axios.get("/movie_info/" + id);
  },

  getMoviesByGenreId(genreId) {
    return axios.get("/movie_info/genre/" + genreId);
  }, // "/movie_info/genre/{genre_id}"

  getMoviesByGenreIds(genreIds) {
    return axios.get("/movie_info/genre?genreIds=" + genreIds.join(","));
  }, // "/movie_info/genre/?genreIds={genre_ids}"

  getMoviesByListId(listId) {
    return axios.get("/movie_info/lists/" + listId);
  }, // /movie_info/lists/{list_id}

  getMovieFromApiId(apiMovieId) {
    return axios.get("/movie_info/tmdb/" + apiMovieId);
  }, // /movie_info/tmdb/{api_movie_id} (isAdmin required)

  searchMoviesFromApi(input) {
    return axios.get("/movie_info/tmdb/search/" + input);
  }, // /movie_info/tmdb/search/{input} (isAdmin required)

  createMovie(movie) {
    return axios.post("/movie_info", movie);
  },

  deleteMovieById(movieId) {
    return axios.delete("/movie_info/" + movieId);
  }, // DELETE "/movie_info/{movieId}"

  getMovies() {
    return axios.get("/movie");
  },

  addGenreToMovie(movie) {
    return axios.post("/movie_info/addgenre", movie);
  },
};
