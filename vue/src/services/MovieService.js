import axios from "axios";

export default {
  getMovieByMovieId(id) {
    return axios.get("/movie_info/" + id);
  },

  getMoviesByGenreId(genreId) {
    return axios.get("/movie_info/genre/" + genreId);
  }, // "/movie_info/genre/{genre_id}"

  getMoviesByListId(listId) {
    return axios.get("/movie_info/lists/" + listId);
  }, // /movie_info/lists/{list_id}

  getMovieFromApiId(apiMovieId) {
    return axios.get("/movie_info/tmdb/" + apiMovieId);
  }, // /movie_info/tmdb/{api_movie_id} (isAdmin required)

  searchMoviesFromApi(input) {
    return axios.get("/movie_info/tmdb/search/" + input);
  }, // /movie_info/tmdb/search/{input} (isAdmin required)
};
