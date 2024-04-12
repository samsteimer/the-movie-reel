import axios from "axios";
import MovieService from "./MovieService";

export default {
  getMovieListMovies(listName) {
    return axios.get("/lists?name=" + listName).then((res) => {
      const listId = res.data[0]?.list_id;
      return MovieService.getMoviesByListId(listId);
    });
  }, // /lists?name=<name> getMovieListsByName() and MovieService.getMoviesByListId()

  getMovieList(listId) {
    return axios.get("/lists/" + listId);
  }, // /lists/<id>

  addListMovie(movieId) {
    return axios.put("/lists/movies/" + movieId);
  }, // PUT /lists/movies/<movieId> (isAdmin required)

  removeListMovie(movieId) {
    return axios.delete("/lists/movies/" + movieId);
  }, // DELETE /lists/movies/<movieId> (isAdmin required)
};
